import org.codehaus.groovy.grails.plugins.GrailsPluginManager
import org.codehaus.groovy.grails.test.GrailsTestTypeResult
import org.codehaus.groovy.grails.test.event.GrailsTestEventPublisher
import org.codehaus.groovy.grails.test.support.GrailsTestTypeSupport

import java.lang.reflect.Modifier

import static org.codehaus.groovy.grails.plugins.GrailsPluginUtils.getPluginDirForName

class CasperGrailsTestType extends GrailsTestTypeSupport {

    static final SUFFIXES = ["Test", "Tests"].asImmutable()

    protected suite

    protected mode

    CasperGrailsTestType(String name, String sourceDirectory) {
        super(name, sourceDirectory)
    }

    protected List<String> getTestSuffixes() { SUFFIXES }

    protected int doPrepare() {
        1
    }

    protected getTestClasses() {
        def classes = []
        eachSourceFile { testTargetPattern, sourceFile ->
            def testClass = sourceFileToClass(sourceFile)
            if (!Modifier.isAbstract(testClass.modifiers)) {
                classes << testClass
            }
        }
        classes
    }

    protected createRunnerBuilder() {
    }

    protected createSuite(classes) {
    }

    protected createJUnitReportsFactory() {
    }

    protected createListener(eventPublisher) {
    }

    protected createNotifier(eventPublisher) {
    }

    GrailsPluginManager grailsPluginManager

    /**
     *
     * @param eventPublisher
     * @return
     */
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
