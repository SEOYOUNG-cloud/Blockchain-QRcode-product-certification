'use strict';
var app = angular.module('application', []);
    app.controller('AppCtrl', function($scope, appFactory) {
      $(document).ready(function(){
        var date = new Date();
        var year = date.getFullYear().toString();
        year = year.substring(2,4);
  
        var month = date.getMonth() + 1;
        month = month < 10 ? '0' + month.toString() : month.toString();
  
        var day = date.getDate();
        day = day < 10 ? '0' + day.toString() : day.toString();
  
        var date = year + month + day;
  
        var text1 = "";
        var text2 = "";
        var alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        var num = "0123456789";
  
        for( var i=0; i < 4; i++ )
        {      
          text1 += alphabet.charAt(Math.floor(Math.random() * alphabet.length));
          text2 += num.charAt(Math.floor(Math.random() * num.length));
        }
        var result = text1+text2;
  
        document.getElementById("input_qr-create-content3").value = date + result;
  
      });
      $scope.setProduct = function(){
        appFactory.setProduct($scope.product, function(data){
          var qrcode = new QRCode(document.getElementById("qr-img-src"), {
             text: '{"id":"' + document.getElementById("input_qr-create-content3").value + '"}',
            width: 416,
            height: 416,
            colorDark : "#000000",
             colorLight : "#ffffff",
            correctLevel : QRCode.CorrectLevel.H
          });
        });
      }
    });
    app.factory('appFactory', function($http){
          var factory = {};
          factory.setProduct = function(data, callback){
              $http.get('/api/setProduct?serialnum='+data.serialnum+'&name='+data.name+'&brand='+data.brand).success(function(output){
                          callback(output)
                  });
          }
          return factory;
  });
