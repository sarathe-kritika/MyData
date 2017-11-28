////<reference path="angular.min.js"/>

var myApp = angular.module("myApp",[]);


var myControllerReport = function($scope,$http){
	$http.get(window.location.origin+'/policyApp/rest/getData/getReport','')
	.then(function(response){
		$scope.result = response.data;	
	})
	
}
	
myApp.controller("myControllerReport",myControllerReport);
