= Grails CasperJS Tests Runner Plugin http://stillmaintained.com/ulrich/grails-casper-plugin.png

The purpose of this plugin is to run functionals {CapserJS}[http://casperjs.org/] tests in Grails application.
The results of these tests are reported in xUnit XML files format. To execute an functionnal CasperJS test, you must put your tests files in the <b>test/casper</b> directory.
They may can be written both Javascript and CoffeeScript language.

To more informations about {CapserJS}[http://casperjs.org/]/{PhantomJS}[http://phantomjs.org/] tools, you can read this {introduction}[http://casperjs.org/testing.html].

== Installation

To install the latest stable version of the plugin run:

  grails install-plugin casper-runner

== Using

To run the tests, you must enter the following command:

  grails test-app casper:

== Issues

Acutally, we are in the first release of plugin and of course there are many improvements possible. So, don't hesitate to report any bug/improvement in the issues tracker on the project.

== License

The plugin is distributed under MIT license, see {LICENSE}[http://en.wikipedia.org/wiki/MIT_License].
