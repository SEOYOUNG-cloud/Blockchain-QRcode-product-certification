const express = require('express');
const app = express();

var path = require('path');
var sdk = require('./sdk');

const PORT = 8080;
const HOST = 'localhost';

/*
app.get('/api/getWallet', function (req, res) {
    var walletid = req.query.walletid;

    let args = [walletid];

    sdk.send(false, 'getWallet', args, res);
});
*/
app.get('/api/setProduct', function (req, res) {
    var serialnum = req.query.serialnum;
    var name = req.query.name;
    var brand = req.query.brand;

    let args = [serialnum, name, brand];
    sdk.send(true, 'setProduct', args, res);
});
app.get('/api/getAllProduct', function (req, res) {
    var serialnum = req.query.serialnum;

    let args = [serialnum];
    sdk.send(false, 'getAllProduct', args, res);
});
/*
app.get('/api/purchaseMusic', function (req, res) {
    var walletid = req.query.walletid;
    var key = req.query.musickey;
    
    let args = [walletid, key];
    sdk.send(true, 'purchaseMusic', args, res);
});
*/
app.use(express.static(path.join(__dirname, './client')));

app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);
