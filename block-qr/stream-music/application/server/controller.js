var sdk = require('./sdk.js');
module.exports = function(app){
app.get('/api/setProduct', function (req, res) {
    var serialnum = req.query.serialnum;
    var name = req.query.name;
    var brand = req.query.brand;

    let args = [serialnum, name, brand];
    sdk.send(true, 'setProduct', args, res);
});
app.get('/api/getAllProduct', function(req, res){
    let args = [];
    sdk.send(false, 'getAllProduct', args, res);
});
app.get('/api/getSearch', function(req, res){
    var option = req.query.option;
    var searchWord = req.query.searchWord;

    let args = [option, searchWord];
    sdk.send(false, 'getSearch', args, res);
});
app.get('/api/getSerial', function(req, res){
    var option = req.query.option;
    var searchWord = req.query.searchWord;

    let args = [option, searchWord];
    sdk.send(false, 'getSerial', args, res);
});
}
