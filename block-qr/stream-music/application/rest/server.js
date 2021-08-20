const express = require('express');
const app = express();

var path = require('path');
var sdk = require('./sdk');

const PORT = 8080;
const HOST = 'localhost';

app.get('/api/getSupply', function (req, res) {
    var serialnum = req.query.serialnum;

    let args = [serialnum];

    sdk.send(false, 'getSupply', args, res);
});
app.get('/api/setProduct', function (req, res) {
    var serialnum = req.query.serialnum;
    var name = req.query.name;
    var brand = req.query.brand;

    let args = [serialnum, name, brand];
    sdk.send(true, 'setProduct', args, res);
});
app.get('/api/getProduct', function (req, res) {
    var userid = req.query.userid;

    let args = [userid];
    sdk.send(false, 'getProduct', args, res);
});
app.get('/api/setSupply', function (req, res) {
    var productid = req.query.productid;
    var factory = req.query.factory;
    var userid = req.query.userid;
    
    let args = [productid, factory, userid];
    sdk.send(true, 'setSupply', args, res);
});
app.get('/api/getAllProduct', function (req, res) {

    let args = [];
    sdk.send(false, 'getAllProduct', args, res);
});
app.use(express.static(path.join(__dirname, './client')));

app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);
