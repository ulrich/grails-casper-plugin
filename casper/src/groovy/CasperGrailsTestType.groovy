import grails.build.logging.GrailsConsole
import org.codehaus.groovy.grails.test.GrailsTestTypeResult
import org.codehaus.groovy.grails.test.event.GrailsTestEventPublisher
import org.codehaus.groovy.grails.test.support.GrailsTestTypeSupport

import static groovy.io.FileType.FILES

class CasperGrailsTestType extends GrailsTestTypeSupport {

    static final SUFFIXE = 'coffee'

    def casperFiles = []

    GrailsConsole grailsConsole

    CasperGrailsTestType(String name, String sourceDirectory) {
        super(name, sourceDirectory)

        grailsConsole = GrailsConsole.getInstance()
    }

    protected List<String> getTestSuffixes() {
        [SUFFIXE].asImmutable()
    }

    protected int doPrepare() {
        sourceDir.eachFileRecurse(FILES, { casperFile ->
            if (casperFile.name.endsWith('coffee')) {
                casperFiles << casperFile
            }
        })
        grailsConsole.addStatus("Found Following Casper Test(s) File(s): " + casperFiles.collect { it.name })

        casperFiles.size()
    }

    /**
     * This method aims to run all found tests Casper files.
     *
     * @param eventPublisher
     * @return
     */
    protected GrailsTestTypeResult doRun(GrailsTestEventPublisher eventPublisher) {
        def totalOfTests = 0
        def totalOfFailureTests = 0
        def totalOfSuccessTests = 0

        // run all found Casper tests
        casperFiles.each { casperFile ->
            // build xunit file name
            def xunitFileName = "TESTS-casperjs-${casperFile.name}.xml"

            // build casperjs process for current file
            def casperProcess = "casperjs test $casperFile --xunitFileName=$xunitFileName".execute()

            // wait for the termination of execution
            casperProcess.waitFor()

            // get the content of xunit file
            String resultFileContent = new File("$xunitFileName").text

            // parse the content file to build status
            def testSuite = new XmlSlurper().parse(new StringReader(resultFileContent))

            totalOfTests += testSuite.testcase.size()
            totalOfFailureTests += testSuite.testcase.failure.size()
            totalOfSuccessTests += totalOfTests - totalOfFailureTests
        }
        grailsConsole.addStatus("Total of test(s): " + totalOfTests + ", number of failure: " + totalOfFailureTests + ", number of success: " + totalOfSuccessTests)

        new GrailsTestTypeResult() {
            int getPassCount() {
                return totalOfSuccessTests
            }

            int getFailCount() {
                return totalOfFailureTests
            }
        }
    }
}
