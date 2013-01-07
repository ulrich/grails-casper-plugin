casper = require('casper').create({
  verbose: true,
  logLevel: 'error'
})

casper.start "http://www.google.fr", ->
  casper.viewport 1024, 768
  @echo "I'm in Hypercal"

casper.then ->
  @test.assertTrue (true), "True 1"

casper.then ->
  @test.assertTrue (true), "True 2"

casper.then ->
  @test.assertTrue (true), "False 1"

casper.run ->
  @test.renderResults true, 0, this.cli.get('xunitFileName') || false
