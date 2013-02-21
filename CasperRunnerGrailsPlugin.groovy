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

/**
 * Grails CasperJS Tests Runner plugin descriptor.
 */
class CasperRunnerGrailsPlugin {

    def version = "0.3-SNAPSHOT"

    def grailsVersion = "2.0 > *"

    def title = "CasperJS Tests Runner Plugin"

    def description = '''\
Runs functional CapserJS tests in Grails application. The results of these tests are reported in xUnit XML files compatible. To run the tests, you must enter the following command: grails test-app casper:
'''
    def documentation = "https://github.com/ulrich/grails-casper-plugin"

    def license = "MIT"

    def organization = [name: "Reservoir Code", url: "http://www.reservoircode.net/"]

    def developers = [[name: "Ulrich VACHON", email: "uvachon@reservoircode.net"]]

    def issueManagement = [system: "Github", url: "https://github.com/ulrich/grails-casper-plugin/issues"]

    def scm = [url: "https://github.com/ulrich/grails-casper-plugin"]
}
