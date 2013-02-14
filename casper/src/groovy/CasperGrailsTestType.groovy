import grails.build.logging.GrailsConsole
import org.codehaus.groovy.grails.test.GrailsTestTypeResult
import org.codehaus.groovy.grails.test.event.GrailsTestEventPublisher
import org.codehaus.groovy.grails.test.support.GrailsTestTypeSupport

import static groovy.io.FileType.FILES

class CasperGrailsTestType extends GrailsTestTypeSupport {

    static final SUFFIXES = ['coffee', 'js']

    def casperFiles = []

    GrailsConsole grailsConsole

    CasperGrailsTestType(String name, String sourceDirectory) {
        super(name, sourceDirectory)

        grailsConsole = GrailsConsole.getInstance()
    }

    protected List<String> getTestExtensions() {
        [SUFFIXES].asImmutable()
    }

    protected int doPrepare() {
        sourceDir.eachFileRecurse(FILES, { file ->
            SUFFIXES.findAll {
                if (file.name.endsWith(it)) {
                    casperFiles << file
                }
            }
        })
        grailsConsole.addStatus("Executing following CasperJS test(s) file(s): " + casperFiles.collect { it.name })

        casperFiles.size()
    }

    /**
     * This method aims to run all found CasperJS tests files.
     */
    protected GrailsTestTypeResult doRun(GrailsTestEventPublisher eventPublisher) {
        def totalOfFailureTest = 0

        def totalOfSuccessTest = 0

        // run all found Casper tests
        casperFiles.each { casperFile ->
            // build xunit file name
            def xunitFileName = "target/TESTS-casperjs-${casperFile.name}.xml"

            // build casperjs process for current file
            def casperProcess = "casperjs test $casperFile --xunitFileName=$xunitFileName".execute()

            // wait for the termination of execution
            casperProcess.waitFor()

            // get the content of xunit file
            String resultFileContent = new File("$xunitFileName").text

            // parse the content file to build status
            def testSuite = new XmlSlurper().parse(new StringReader(resultFileContent))

            def numberOfTest = testSuite.testcase.size()
            def numberOfFailureTest = testSuite.testcase.failure.size()
            def numberOfSuccessTest = numberOfTest - numberOfFailureTest

            totalOfFailureTest += numberOfFailureTest
            totalOfSuccessTest += numberOfSuccessTest

            grailsConsole.addStatus("Executed ${numberOfTest} test(s) for ${casperFile.name}: number of failure: ${numberOfFailureTest}, number of success: ${numberOfSuccessTest}")
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
