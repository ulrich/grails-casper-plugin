import org.codehaus.groovy.grails.test.GrailsTestTypeResult
import org.junit.runner.Result

class CasperResultGrailsTestTypeResultAdapter implements GrailsTestTypeResult {

    private final Result result

    CasperResultGrailsTestTypeResultAdapter(Result result) {
        this.result = result
    }

    int getPassCount() {
        result.runCount - failCount
    }

    int getFailCount() {
        result.failureCount
    }
}
