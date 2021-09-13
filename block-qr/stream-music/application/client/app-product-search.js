'use strict';
var app = angular.module('application', []);
app.controller('AppCtrl', function($scope, appFactory){

    var getKeyboardEventResult = function (keyEvent) {
      return (window.event ? keyEvent.keyCode : keyEvent.which);
    };

    $scope.getSerial = function($event){
        if(getKeyboardEventResult($event) == 13) {
            var option = document.getElementsByClassName("label");
            $scope.search.option = option[0].innerHTML;

            appFactory.getSerial($scope.search, function(data){
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
