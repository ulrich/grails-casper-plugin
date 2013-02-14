var casper = require('casper').create({
  verbose: true,
  logLevel: 'error'
});

casper.start("http://www.google.fr", function() {
  this.test.assertTrue (true, "True 1");
});

casper.then(function() {
  this.test.assertTrue (false, "False 1");
});

casper.run(function() {
  this.test.renderResults(true, 0, this.cli.get('xunitFileName'));
});
