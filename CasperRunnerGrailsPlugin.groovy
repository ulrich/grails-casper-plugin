class CasperRunnerGrailsPlugin {
    def version = "0.1"

    def grailsVersion = "2.0 > *"

    def dependsOn = [:]

    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Casper Tests Runner Plugin"

    def author = "Ulrich VACHON"

    def authorEmail = "uvachon@reservoircode.net"

    def description = '''\
The purpose of this plugin is to run functional CapserJS tests in type GRAILS application. The results of these tests are reported in xUnit XML files compatible. To run the tests, you must enter the following command: grails test-app casper:
'''
    def documentation = "http://grails.org/plugin/casper"

    def license = "MIT"

    def organization = [name: "Reservoir Code", url: "http://www.reservoircode.net/"]

    def developers = [[name: "Ulrich VACHON", email: "uvachon@reservoircode.net"]]

    def issueManagement = [ system: "Github", url: "https://github.com/ulrich/grails-casper-plugin/issues" ]

    def scm = [url: "https://github.com/ulrich/grails-casper-plugin"]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { applicationContext ->
        // TODO Implement post initialization spring config (optional)
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
