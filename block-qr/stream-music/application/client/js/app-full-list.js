'use strict';
var app = angular.module('application', []);
app.controller('AppCtrl', function($scope, appFactory){
        
    var getKeyboardEventResult = function (keyEvent) {
      return (window.event ? keyEvent.keyCode : keyEvent.which);
    };

    appFactory.getAllProduct(function(data){
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

    $(function () {	//화면 로딩후 시작
        var searchSource = ["Gucci", "Chanel"]; // 배열 형태로
		$("#searchInput").autocomplete({  //오토 컴플릿트 시작
			source: searchSource,	// source는 data.js파일 내부의 List 배열
			focus : function(event, ui) { // 방향키로 자동완성단어 선택 가능하게 만들어줌	
				return false;
			},
            open: function (event, ui) { 
                $(this).autocomplete("widget").css({ 
                    "width": 628, //width 조절 
                    
                }); 
            },
			minLength: 1,// 최소 글자수
			delay: 100,	//autocomplete 딜레이 시간(ms)
			//disabled: true, //자동완성 기능 끄기
            // position : { my : 'right top', at : 'right bottom'}, // 제안 메뉴의 위치를 식별
		});
	});
        
    $scope.getSearch = function($event){
        if(getKeyboardEventResult($event) == 13) {
            var option = document.getElementsByClassName("label");
            $scope.search.option = option[0].innerHTML;

            appFactory.getSearch($scope.search, function(data){
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
        factory.getAllProduct = function(callback){
            $http.get('/api/getAllProduct/').success(function(output){
                        callback(output)
                });
        }
        factory.getSearch = function(data, callback){
            $http.get('/api/getSearch?option='+data.option+'&searchWord='+data.searchWord).success(function(output){
                        callback(output)
                });
        }
        return factory;
});
