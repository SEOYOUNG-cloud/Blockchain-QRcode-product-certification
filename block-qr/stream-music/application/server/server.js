var mysql = require('mysql');
var express       = require('express');
var app           = express();
var bodyParser    = require('body-parser');
var http          = require('http')
var fs            = require('fs');
var Fabric_Client = require('fabric-client');
var path          = require('path');
var util          = require('util');
var os            = require('os');
var session = require("express-session");
var ejs = require('ejs');
var app = express();

//app.use(bodyParser.json());
//app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

require('./controller.js')(app);
app.use(express.static(path.join(__dirname, '../client')));
var port = process.env.PORT || 8000;


var connection = mysql.createConnection({
    host: 'localhost',
    user: 'manager',
    database: 'member',
    password: 'root',
    port: '3306'
});

app.use(session({ 
    secret : 'keyboard cat', 
    resave: false, 
    saveUninitialized: true 
}));

app.engine('html', ejs.renderFile);
app.set('view engine', 'html');
//app.set('views','./views');
app.set('views', path.join(__dirname, '../client'));

app.get('/',function(req,res){
  if (req.session.user) {
    res.render('product-search', {user :req.session.user});
  } else {
    res.render('product-search', {user :''});
  }
});

app.get('/product-create',function(req,res){
  if (req.session.user) {
    res.render('product-create', {user :req.session.user});
  } else {
    res.send("<script>alert('로그인 후에 이용하실 수 있습니다.'); location.href='/login';</script>");
  }
});

app.get('/full-list',function(req,res){
  if (req.session.user) {
    res.render('full-list', {user :req.session.user, bName :req.session.bName});
  } else {
    res.send("<script>alert('로그인 후에 이용하실 수 있습니다.'); location.href='/login';</script>");
  }
});

app.get('/product-search',function(req,res){
  if (req.session.user) {
    res.render('product-search', {user :req.session.user});
  } else {
    res.render('product-search', {user :''});
  }
});

app.get('/login',function(req,res){
    res.render('login');
});

app.get('/logout',function(req,res){
    req.session.destroy(function () {
        req.session;
    });
    res.redirect('/');
});

app.post('/product-create', function(req, res){
    var id = req.body.id;
    var pwd = req.body.pwd;
    var sql = 'SELECT * FROM manufacturer WHERE id = ? AND pwd = ?';
    var params = [id, pwd];

    connection.query(sql, params, function(err, result){

 if(err){
          console.log(err);
       } else{
           if(result.length === 0){
		res.send("<script>alert('아이디와 비밀번호를 정확히 입력해 주세요.'); history.back();</script>");
		res.render('login');
       } else{
	req.session.user = result[0].Admin_name;
	req.session.bName = result[0].B_name;
	res.render('product-create', {user :req.session.user});
        }
     }    
   })
});

app.listen(port,function(){
  console.log("Live on port: " + port);
});
