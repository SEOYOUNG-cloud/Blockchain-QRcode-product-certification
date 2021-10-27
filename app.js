var mysql = require('mysql');
var express = require('express');
var bodyParser = require('body-parser');
var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.set('port', process.env.PORT || 3333);

var connection = mysql.createConnection({
    host: 'localhost',
    user: 'manager',
    database: 'member',
    password: 'root',
    port: '3306'
});

app.post('/user/checkId', function (req, res){
    var id = req.body.id;
    var sql = 'select * from customer where id = ?';
    connection.query(sql, id, function(err, result){
        if(err)
           console.log(err);
        else{
           if(result.length === 0){
              res.json({
                 result : false,
                 message : '사용가능한 아이디입니다.'
              });
           } else{
                 res.json({
                    result: true,
                    message: '중복된 아이디가 존재합니다'
                 });
           }
        }
    })
});

app.post('/user/mcheckId', function (req, res){
    var id = req.body.id;
    var sql = 'select * from manufacturer where id = ?';
    connection.query(sql, id, function(err, result){
        if(err)
           console.log(err);
        else{
           if(result.length === 0){
              res.json({
                 result : false,
                 message : '사용 가능한 아이디입니다.'
              });
           } else{
                 res.json({
                    result: true,
                    message: '중복된 아이디가 존재합니다'
                 });
           }
        }
    })
});

app.post('/user/dcheckId', function (req, res){
    var id = req.body.id;
    var sql = 'select * from distribution where id = ?';
    connection.query(sql, id, function(err, result){
        if(err)
           console.log(err);
        else{
           if(result.length === 0){
              res.json({
                 result : false,
                 message : '사용 가능한 아이디입니다.'
              });
           } else{
                 res.json({
                    result: true,
                    message: '중복된 아이디가 존재합니다'
                 });
           }
        }
    })
});

app.post('/user/scheckId', function (req, res){
    var id = req.body.id;
    var sql = 'select * from shop where id = ?';
    connection.query(sql, id, function(err, result){
        if(err)
           console.log(err);
        else{
           if(result.length === 0){
              res.json({
                 result : false,
                 message : '사용 가능한 아이디입니다.'
              });
           } else{
                 res.json({
                    result: true,
                    message: '중복된 아이디가 존재합니다'
                 });
           }
        }
    })
});



app.post('/user/join', function(req, res){
    console.log(req.body);
    var id = req.body.id;
    var pwd = req.body.pwd;
    var name = req.body.name;
    var tel = req.body.tel;
    var email = req.body.email;
    var addr = req.body.addr;

    var sql = 'INSERT INTO customer(id, pwd, name, tel, email, addr) VALUES(?, ?, ?, ?, ?, ?)';
    var params = [id, pwd, name, tel, email, addr];


    connection.query(sql, params, function(err, result){
      var resultCode = 404;
      var message = '에러 발생';

       if(err){
          console.log(err);
      } else{
          res.json({
          resultCode : 200,
          message : '회원가입 성공!'
         });
      }
    });
});

app.post('/user/mjoin', function(req, res){
    console.log(req.body);
    var id = req.body.id;
    var pwd = req.body.pwd;
    var B_name = req.body.B_name;
    var Admin_name = req.body.Admin_name;
    var tel = req.body.tel;
    var email = req.body.email;
    var addr = req.body.addr;

    var sql = 'INSERT INTO manufacturer(id, pwd, B_name, Admin_name,  tel, email, addr) VALUES(?, ?, ?, ?, ?, ?)';
    var params = [id, pwd, B_name, Admin_name, tel, email, addr];


    connection.query(sql, params, function(err, result){
      var resultCode = 404;
      var message = '에러 발생';

       if(err){
          console.log(err);
      } else{
          res.json({
          resultCode : 200,
          message : '회원가입 성공!'
         });
      }
    });
});

app.post('/user/djoin', function(req, res){
    console.log(req.body);
    var id = req.body.id;
    var pwd = req.body.pwd;
    var B_name = req.body.B_name;
    var Admin_name = req.body.Admin_name;
    var tel = req.body.tel;
    var email = req.body.email;
    var addr = req.body.addr;

    var sql = 'INSERT INTO distribution(id, pwd, B_name, Admin_name,  tel, email, addr) VALUES(?, ?, ?, ?, ?, ?)';
    var params = [id, pwd, B_name, Admin_name, tel, email, addr];


    connection.query(sql, params, function(err, result){
      var resultCode = 404;
      var message = '에러 발생';

       if(err){
          console.log(err);
      } else{
          res.json({
          resultCode : 200,
          message : '회원가입 성공!'
         });
      }
    });
});

app.post('/user/sjoin', function(req, res){
    console.log(req.body);
    var id = req.body.id;
    var pwd = req.body.pwd;
    var B_name = req.body.B_name;
    var Admin_name = req.body.Admin_name;
    var tel = req.body.tel;
    var email = req.body.email;
    var addr = req.body.addr;

    var sql = 'INSERT INTO shop(id, pwd, B_name, Admin_name,  tel, email, addr) VALUES(?, ?, ?, ?, ?, ?)';
    var params = [id, pwd, B_name, Admin_name, tel, email, addr];


    connection.query(sql, params, function(err, result){
      var resultCode = 404;
      var message = '에러 발생';

       if(err){
          console.log(err);
      } else{
          res.json({
          resultCode : 200,
          message : '회원가입 성공!'
         });
      }
    });
});


app.post('/user/clogin', function(req, res){
    var id = req.body.id;
    var pwd = req.body.pwd;
    var sql = 'SELECT * FROM customer WHERE id = ? AND pwd = ?';
    var params = [id, pwd];

    connection.query(sql, params, function(err, result){

 if(err){
          console.log(err);
       } else{
           if(result.length === 0){
             res.json({
               result : false,
               message : '존재 않는 계정입니다.'
             });
       } else if(pwd !== result[0].pwd){
             res.json({
             result : false,
             message : '비밀번호가 틀렸습니다.'
            });
        } else{
             res.json({
          result : true,
          message : '로그인 성공'
          id : result[0].id,
          pwd : result[0].pwd,
          name : result[0].name

             });
        }
     }

   })

});

app.post('/user/mlogin', function(req, res){
    var id = req.body.id;
    var pwd = req.body.pwd;
    var sql = 'SELECT * FROM manufacturer WHERE id = ? AND pwd = ?';
    var params = [id, pwd];

    connection.query(sql, params, function(err, result){

 if(err){
          console.log(err);
       } else{
           if(result.length === 0){
             res.json({
               result : false,
               message : '존재 않는 계정입니다.'
             });
       } else if(pwd !== result[0].pwd){
             res.json({
             result : false,
             message : '비밀번호가 틀렸습니다.'
            });
        } else{
             res.json({
          result : true,
          message : '로그인 성공',
          id : result[0].id,
          pwd : result[0].pwd,
          B_name : result[0].B_name,
          Admin_name : result[0].Admin_name,
          addr : result[0].addr
             });
        }
     }
   })
});

app.post('/user/dlogin', function(req, res){
    var id = req.body.id;
    var pwd = req.body.pwd;
    var sql = 'SELECT * FROM distribution WHERE id = ? AND pwd = ?';
    var params = [id, pwd];

    connection.query(sql, params, function(err, result){

 if(err){
          console.log(err);
       } else{
           if(result.length === 0){
             res.json({
               result : false,
               message : '존재 않는 계정입니다.'
             });
       } else if(pwd !== result[0].pwd){
             res.json({
             result : false,
             message : '비밀번호가 틀렸습니다.'
            });
        } else{
             res.json({
          result : true,
          message : '로그인 성공',
          id : result[0].id,
          pwd : result[0].pwd,
          B_name : result[0].B_name,
          Admin_name : result[0].Admin_name,
          addr : result[0].addr
             });
        }
     }
   })
});

app.post('/user/slogin', function(req, res){
    var id = req.body.id;
    var pwd = req.body.pwd;
    var sql = 'SELECT * FROM shop WHERE id = ? AND pwd = ?';
    var params = [id, pwd];

    connection.query(sql, params, function(err, result){

 if(err){
          console.log(err);
       } else{
           if(result.length === 0){
             res.json({
               result : false,
               message : '존재 않는 계정입니다.'
             });
       } else if(pwd !== result[0].pwd){
             res.json({
             result : false,
             message : '비밀번호가 틀렸습니다.'
            });
        } else{
             res.json({
          result : true,
          message : '로그인 성공',
          id : result[0].id,
          pwd : result[0].pwd,
          B_name : result[0].B_name,
          Admin_name : result[0].Admin_name,
          addr : result[0].addr
             });
        }
     }
   })
});

app.listen(app.get('port'), ()=>{
 console.log('Express server listening on port: ' + app.get('port'));
});


