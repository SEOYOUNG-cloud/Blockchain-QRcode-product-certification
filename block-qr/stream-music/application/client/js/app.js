'use strict';
var app = angular.module('application', []);
app.controller('AppCtrl', function($scope, appFactory){
        $("#success_setproduct").hide();
        $("#success_getallproduct").hide();
       $scope.getAllProduct = function(){
                appFactory.getAllProduct(function(data){
                        var array = [];
                        for (var i = 0; i < data.length; i++){
                                parseInt(data[i].Key);
                                data[i].Record.Key = data[i].Key;
                                array.push(data[i].Record);
                                $("#success_getallproduct").hide();
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
        factory.getAllProduct = function(callback){
            $http.get('/api/getAllProduct/').success(function(output){
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
