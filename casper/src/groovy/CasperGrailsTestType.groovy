import grails.build.logging.GrailsConsole
import org.codehaus.groovy.grails.test.GrailsTestTypeResult
import org.codehaus.groovy.grails.test.event.GrailsTestEventPublisher
import org.codehaus.groovy.grails.test.event.GrailsTestRunNotifier
import org.codehaus.groovy.grails.test.support.GrailsTestTypeSupport

import java.lang.reflect.Modifier

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

    protected GrailsTestTypeResult doRun(GrailsTestEventPublisher eventPublisher) {
        // build the casperjs process
        def casperProcess = "casperjs $sourceDir/origine-test.coffee".execute()

        // wait for the termination of execution
        casperProcess.waitFor()

        // get the content of xunit file
        String resultFileContent = new File('TESTS-Casperjs-TestSuites.xml').text

        // parse the content file to build status
        def testSuite = new XmlSlurper().parse(new StringReader(resultFileContent))

        def totalOfTests = testSuite.testcase.size()
        def totalOfFailureTests = testSuite.testcase.failure.size()
        def totalOfSuccessTests = totalOfTests - totalOfFailureTests

        println "Total of test(s): " + totalOfTests + ", number of failure: " + totalOfFailureTests + ", number of success: " + totalOfSuccessTests

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
