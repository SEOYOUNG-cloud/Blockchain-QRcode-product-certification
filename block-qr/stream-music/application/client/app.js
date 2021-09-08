'use strict';
var app = angular.module('application', []);
app.controller('AppCtrl', function($scope, appFactory){
        $scope.getSearch = function(){
          var option = document.getElementsByClassName("label");
          // var searchWord = document.getElementsByClassName("serch-input-text-style");

          $scope.search.option = option[0].innerHTML;
          // $scope.search.searchWord = searchWord[0].value;

                appFactory.getList($scope.search, function(data){
                        var array = [];
                        for (var i = 0; i < data.length; i++){
                                parseInt(data[i].Key);
                                data[i].Record.Key = data[i].Key;
                                array.push(data[i].Record);
                        }
                array.sort(function(a, b) {
                        return parseFloat(a.Key) - parseFloat(b.Key);
                });
                $scope.allProduct = array;
                });
        }
        $scope.setProduct = function(){
            appFactory.setProduct($scope.product, function(data){
                        $scope.create_product = data;
                        $("#success_setproduct").show();
            });
        }
});
 app.factory('appFactory', function($http){
        var factory = {};
        factory.getSearch = function(data, callback){
            $http.get('/api/getSearch?option='+data.option+'&searchWord='+data.searchWord).success(function(output){
                        callback(output)
                });
        }
        factory.setProduct = function(data, callback){
            $http.get('/api/setProduct?serialnum='+data.serialnum+'&name='+data.name+'&brand='+data.brand).success(function(output){
                        callback(output)
                });
        }
        return factory;
});
