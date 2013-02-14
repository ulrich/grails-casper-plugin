import net.reservoircode.grails.plugin.casperjs.CasperGrailsTestType

eventAllTestsStart = {
    phasesToRun << "casper"
}

def testTypeName = "casper"

def testDirectory = "casper"

def casperTestType = new CasperGrailsTestType(testTypeName, testDirectory)

casperTests = [casperTestType]

eventTestPhaseStart = { phase ->
    //println "[LOG DEBUG : _Events.groovy -> eventTestPhaseStart] Start \"$phase\" phase test..."
}

casperTestPhasePreparation = {
    //println "[LOG DEBUG : _Events.groovy -> casperTestPhasePreparation] Prepare Casper phase test..."

    integrationTestPhasePreparation()
}

casperTestPhaseCleanUp = {
    //println "[LOG DEBUG : _Events.groovy -> casperTestPhaseCleanUp] Cleanup Casper phase test..."

    integrationTestPhaseCleanUp()
}

eventTestSuiteStart = { typeName ->
    if (typeName == 'casper') {
        //println "[LOG DEBUG : _Events.groovy -> eventTestSuiteStart] Entering Casper phase test..."
    }
}

eventTestSuiteEnd = { typeName ->
    if (typeName == 'casper') {
        //println "[LOG DEBUG : _Events.groovy -> eventTestSuiteEnd] Leaving Casper phase test..."
    }
}
