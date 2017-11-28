////<reference path="angular.min.js"/>

var myApp = angular.module("logout-module",[]);


var myControllerLogout = function($scope,$http){
	$scope.SignOut = function(){	
		$http({
			method:'POST',
			url:window.location.origin+'/policyApp/rest/getData/logout',
			contentType:'application/json',
			headers:{'Content-Type' : 'application/json'}
		}).then(function(response){
			$scope.res = response.data;
			console.log($scope.res)
			window.location = 'home.html';
		});
		
		
	};
	

}

myApp.controller("myControllerLogout",myControllerLogout);

