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
app.get('/api/setFactory', function (req, res) {
    var productid = req.query.productid;
    var factory = req.query.factory;
    
    let args = [productid, factory];
    sdk.send(true, 'setFactory', args, res);
});
app.get('/api/setDelivery', function (req, res) {
    var productid = req.query.productid;
    var delivery = req.query.delivery;
    
    let args = [productid, delivery];
    sdk.send(true, 'setDelivery', args, res);
});
app.get('/api/setStore', function (req, res) {
    var productid = req.query.productid;
    var store = req.query.store;
    
    let args = [productid, store];
    sdk.send(true, 'setStore', args, res);
});
app.get('/api/setStatusId', function (req, res) {
    var productid = req.query.productid;
    var status = req.query.status;
    var userid = req.query.userid;
    
    let args = [productid, status, userid];
    sdk.send(true, 'setStatusId', args, res);
});
app.get('/api/getAllProduct', function (req, res) {

    let args = [];
    sdk.send(false, 'getAllProduct', args, res);
});
app.use(express.static(path.join(__dirname, './client')));

app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);
