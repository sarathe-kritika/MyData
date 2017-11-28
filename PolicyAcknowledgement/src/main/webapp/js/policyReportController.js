////<reference path="angular.min.js"/>

var app=angular.module('myApp', []);



app.controller('MyCtrl', ['$scope', '$filter','$http', function ($scope, $filter,$http) {
	
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
	
	var username = getParameterByName("username");
	 $scope.username = username;
	 console.log("user name"+username);
	 var full_name = getParameterByName("full_name");
	 $scope.full_name = full_name;
	 console.log("full name"+full_name);
	 
    $scope.currentPage = 0;
    $scope.pageSize = 10;
    $scope.data = [];
    $scope.q = '';
    
    $http.get(window.location.origin+'/policyApp/rest/getData/getReport','')
	.then(function(response){
		 $scope.data = response.data;	
		 
		 console.log($scope.data);
		 $scope.totalData = $scope.getData().length;
		
		 
	})
    
    $scope.getData = function () {
        return $filter('filter')($scope.data, $scope.q)
    }
    
    $scope.numberOfPages=function(){
    	
        return Math.ceil($scope.getData().length/$scope.pageSize);                
    
    }
    
    
    
}]);

//We already have a limitTo filter built-in to angular,
//let's make a startFrom filter
app.filter('startFrom', function() {
    return function(input, start) {
        start = +start; //parse to int
        return input.slice(start);
    }
});



function getParameterByName(name) {
	 console.log("hvfhbv");
	    var url= window.location.href;
	    name = name.replace(/[\[\]]/g, "\\$&");
	    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
	        results = regex.exec(url);
	    if (!results) return null;
	    if (!results[2]) return '';
	    return decodeURIComponent(results[2].replace(/\+/g, " "));
	}