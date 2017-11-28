////<reference path="angular.min.js"/>

var myApp = angular.module("my-empModule",[]);


var myEmpController = function($scope,$http){
	
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
	 $scope.showSucessMessage = false;
	 $scope.showWarningMessage = false;
	$scope.addEmp = function(employee){	
		console.log($scope.employee);
		var data = $scope.employee;
		console.log("Data: "+JSON.stringify(data));
		$http({
			method:'POST',
			url:window.location.origin+'/policyApp/rest/getData/addEmployee',
			contentType:'application/json',
			data:data,
			headers:{'Content-Type' : 'application/json'}
		}).then(function(response){
			$scope.res = response.data;
			console.log("Data: "+$scope.res);
			if($scope.res!=null && $scope.res!="Employee Already Exist"){
				 $scope.showSucessMessage = true;
			}
			else if($scope.res=="Employee Already Exist"){
				$scope.showWarningMessage = true;
			}
		});
	};
}

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
myApp.controller("myEmpController",myEmpController);


