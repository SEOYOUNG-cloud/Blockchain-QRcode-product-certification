'use strict';
var app = angular.module('application', []);
app.controller('AppCtrl', function($scope, appFactory){
        $scope.getSerial = function(){
          var option = document.getElementsByClassName("label");
          $scope.search.option = option[0].innerHTML;

                appFactory.getSerial($scope.search, function(data){
                        var array = [];
                        for (var i = 0; i < data.length; i++){
                                data[i].productid = $scope.search.searchWord;
		       data[i].name = data[i].Name;
		       data[i].brand = data[i].Brand;
		       data[i].userid = data[i].UserID;
                                array.push(data[i]);
                        }
                $scope.allProduct = array;
                });
        }
});
 app.factory('appFactory', function($http){
        var factory = {};
        factory.getSerial = function(data, callback){
            $http.get('/api/getSerial?option='+data.option+'&searchWord='+data.searchWord).success(function(output){
                        callback(output)
                });
        }
        return factory;
});
