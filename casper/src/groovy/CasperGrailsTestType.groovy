import org.codehaus.groovy.grails.test.GrailsTestTypeResult
import org.codehaus.groovy.grails.test.event.GrailsTestEventPublisher
import org.codehaus.groovy.grails.test.event.GrailsTestRunNotifier
import org.codehaus.groovy.grails.test.support.GrailsTestTypeSupport

import java.lang.reflect.Modifier

import static groovy.io.FileType.FILES

class CasperGrailsTestType extends GrailsTestTypeSupport {

    static final SUFFIXES = ["test.coffee"].asImmutable()

    protected suite

    protected mode

    CasperGrailsTestType(String name, String sourceDirectory) {
        super(name, sourceDirectory)
    }

    protected List<String> getTestSuffixes() {
        SUFFIXES
    }

    protected int doPrepare() {
        def casperFiles = []

        sourceDir.eachFileRecurse(FILES) { file ->
            casperFiles << file
        }
        println "casperFiles= " + casperFiles
        casperFiles.size()
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
        int total = 0
        if (suite.hasProperty("children")) {
            total = suite.children.collect {
                it.hasProperty("children") ? it.children.size() : 0
            }.sum()
        }
        def notifier = new GrailsTestRunNotifier(total)
        notifier.addListener(createListener(eventPublisher))
        notifier
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
