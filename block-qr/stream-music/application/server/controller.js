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
}
