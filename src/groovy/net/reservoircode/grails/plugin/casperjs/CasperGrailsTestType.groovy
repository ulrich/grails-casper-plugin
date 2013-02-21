/**
 * Copyright (c) 2013 Ulrich VACHON for Reservoir Code.
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package net.reservoircode.grails.plugin.casperjs

import static groovy.io.FileType.FILES
import grails.build.logging.GrailsConsole

import org.codehaus.groovy.grails.test.GrailsTestTypeResult
import org.codehaus.groovy.grails.test.event.GrailsTestEventPublisher
import org.codehaus.groovy.grails.test.support.GrailsTestTypeSupport

/**
 * Grails CasperJS Tests Runner.
 */
class CasperGrailsTestType extends GrailsTestTypeSupport {

    // the tests files extensions collection
    static final List SUFFIXES = ['coffee', 'js']

    // the instance of grails console
    GrailsConsole grailsConsole

    // the CasperJS files registered to be execute
    def casperFiles = []

    CasperGrailsTestType(testTypeName, testDirectory) {
        super(testTypeName, testDirectory)

        grailsConsole = GrailsConsole.getInstance()
    }

    /**
     * Return the tests files extension manageable by the plugin.
     *
     * @return the tests files extension.
     */
    protected List<String> getTestExtensions() {
        SUFFIXES.asImmutable()
    }

    /**
     * Scans recursively the tests directories to find, count and register tests files.
     *
     * @return the number of tests files.
     */
    protected int doPrepare() {
        sourceDir.eachFileRecurse(FILES, { file ->
            SUFFIXES.each {
                if (file.name.endsWith(it)) {
                    casperFiles << file
                }
            }
        })
        int numberOfTest = casperFiles.size()

        if (numberOfTest == 0) {
            grailsConsole.addStatus('No CasperJS test to execute')
        } else {
            grailsConsole.addStatus("Executing following CasperJS test(s) file(s): ${casperFiles.collect { it.name }}")
        }
        numberOfTest
    }

    /**
     * Run all found CasperJS tests files.
     */
    protected GrailsTestTypeResult doRun(GrailsTestEventPublisher eventPublisher) {
        int totalOfFailureTest = 0
        int totalOfSuccessTest = 0

        // run all found Casper tests
        casperFiles.each { casperFile ->
            // build xunit file name
            String xunitFileName = "target/TESTS-casperjs-${casperFile.name}.xml"

            // build casperjs process for current file
            def casperProcess = "casperjs test $casperFile --xunitFileName=$xunitFileName".execute()

            // wait for the termination of execution
            casperProcess.waitFor()

            // get the content of xunit file
            String resultFileContent = new File(xunitFileName).text

            // parse the content file to build status
            def testSuite = new XmlSlurper().parseText(resultFileContent)

            int numberOfTest = testSuite.testcase.size()
            int numberOfFailureTest = testSuite.testcase.failure.size()
            int numberOfSuccessTest = numberOfTest - numberOfFailureTest

            totalOfFailureTest += numberOfFailureTest
            totalOfSuccessTest += numberOfSuccessTest

            grailsConsole.addStatus("Executed ${numberOfTest} test(s) for ${casperFile.name}: ${numberOfFailureTest} failure, ${numberOfSuccessTest} success")
        }
        new GrailsTestTypeResult() {
            int getPassCount() {
                return totalOfSuccessTest
            }

            int getFailCount() {
                return totalOfFailureTest
            }
        }
    }
}
