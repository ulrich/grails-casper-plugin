casper = require('casper').create({
	verbose: true,
	logLevel: 'error'
})

testVignette = ->
    casper.evaluate ->
      document.getElementsByClassName "offer-tag"

#url = casper.cli.get("url")
#casper.start url, ->

casper.start "http://www.google.fr", ->
  casper.viewport 1024, 768
  @echo "I'm in Hypercal"

#casper.then ->
  #@wait 100, ->
    #vignettes = @evaluate ->
      #document.getElementsByClassName "offer-tag"
    #@test.assertTrue ((vignettes.length > 0) && (vignettes.length <= 10)), "Vérification de dix vignettes au max"
    #@test.assertTrue (false), "Vérification de dix vignettes au max"
    #@test.assertTrue (false), "Vérification de dix vignettes au max"

casper.then ->
  @test.assertTrue (true), "True 1"

casper.then ->
  @test.assertTrue (true), "True 2"

casper.then ->
  @test.assertTrue (true), "False 1"

casper.then ->
  @test.assertTrue (false), "False 2"

casper.run ->
  @test.renderResults true, 0, "TESTS-Casperjs-TestSuites.xml"
