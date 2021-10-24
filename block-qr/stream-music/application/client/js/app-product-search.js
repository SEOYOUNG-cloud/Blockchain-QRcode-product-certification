'use strict';
var app = angular.module('application', []);
app.controller('AppCtrl', function($scope, appFactory){
    var product_name;
    
    if (getParameters('serialnum').length != 0) {
        document.getElementById("allProduct").style.visibility = "visible"; 
        $scope.serialnum = getParameters('serialnum');
        appFactory.getSerial($scope.serialnum, function(data){
            var array = [];
            for (var i = 0; i < data.length; i++){
                data[i].productid = data[i].ProductID;
                data[i].name = data[i].Name;
                data[i].brand = data[i].Brand;
                data[i].userid = data[i].UserID;
                product_name = data[i].name;
                array.push(data[i]);
            }
            $scope.allProduct = array;

            switch (product_name) {
                case "583571 1X5CG 6775" :
                  $scope.imageSource = "https://media.gucci.com/style/DarkGray_Center_0_0_800x800/1613669409/583571_1X5CG_6775_001_058_0020_Light-GG.jpg";
                  break;
                case "660195 17QDT 2582" :
                  $scope.imageSource = "https://media.gucci.com/style/DarkGray_Center_0_0_650x650/1625733904/360_660195_17QDT_2582_001_100_0000_Light-.jpg";
                  break;
                case "443496 DRWAR 9022" :
                  $scope.imageSource = "https://media.gucci.com/style/DarkGray_Center_0_0_650x650/1626455703/360_443496_DRWAR_9022_001_100_0000_Light-GG-2016.jpg";
                  break;
                case "AS2696 B06364 NE798" :
                  $scope.imageSource = "https://www.chanel.com/images/q_auto,f_auto,fl_lossy,dpr_auto/w_1920/flap-bag-black-white-tweed-aged-calfskin-aged-pale-yellow-metal-tweed-aged-calfskin-aged-pale-yellow-metal-packshot-default-as2696b06364ne798-8840481177630.jpg";
                  break;
                case "AS2756 B06315 NF024" :
                  $scope.imageSource = "https://www.chanel.com/images/q_auto,f_auto,fl_lossy,dpr_auto/w_1920/e_trim:0/shopping-bag-black-white-shearling-lambskin-gold-tone-metal-shearling-lambskin-gold-tone-metal-packshot-default-as2756b06315nf024-8840469807134.jpg";
                  break;
                case "AS2785 B06505 ND365" :
                  $scope.imageSource = "https://www.chanel.com/images/q_auto,f_auto,fl_lossy,dpr_auto/w_1920/flap-bag-black-pink-gray-embroidered-wool-tweed-ruthenium-finished-metal-embroidered-wool-tweed-ruthenium-finished-metal-packshot-default-as2785b06505nd365-8840473378846.jpg";
                  break;
                default :
                  alert('제품 이미지가 존재하지 않습니다.');
                  history.back();
                  //location.href="/product-search";
                  break;
              }
        });
    }
});
 app.factory('appFactory', function($http){
    var factory = {};
    factory.getSerial = function(serial, callback){
        $http.get('/api/getSerial?serialnum='+serial).success(function(output){
            callback(output)
        });
    }
    return factory;
});
