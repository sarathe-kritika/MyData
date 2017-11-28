////<reference path="angular.min.js"/>

var myApp = angular.module("myNg-Module",[]);


var myControllerNG = function($scope,$http){
	 $scope.showErrorMessageInvalidUser = false;
	 $scope.achnowledgedUser = false;
	 $scope.showErrorMessageInvalidRole = false;
	$scope.connect = function(user){	
		//console.log($scope.user);
		var data = $scope.user;
		//console.log("Data: "+JSON.stringify(data));
		//alert($scope.user.username);
		//alert($scope.user.role);
		$http({
			method:'POST',
			url:window.location.origin+'/policyApp/rest/getData/login',
			contentType:'application/json',
			data:data,
			headers:{'Content-Type' : 'application/json'}
		}).then(function(response){
			$scope.res = response.data;
			$scope.res.username;
			$scope.res.full_name;
			$scope.res.role;
			//alert("hi..."+$scope.res.role);
			
			
			if($scope.res.username =='Invalid User' ||$scope.res.username =='You have already submitted'){
				
				if($scope.res.username =='Invalid User'){
				 $scope.showErrorMessageInvalidUser = true;
				}
				else{
					$scope.achnowledgedUser = true;
				}
				 
			}
			else if($scope.res.username =='You have already submitted'){
				$scope.achnowledgedUser = true;
				
			}
			
			else if($scope.res.role=='admin'){
				//alert("hi..."+$scope.res.role);
				window.location = 'admin.html?username='+$scope.res.username+'&full_name='+$scope.res.full_name;
			}
			
			else if($scope.res.role=='You are not admin...'){
				$scope.showErrorMessageInvalidRole = true;
			}
			
			else{
				window.location = 'DemoPolicy.html?username='+$scope.res.username+'&full_name='+$scope.res.full_name;
			}
			
		});
		
		
	};
	

}

myApp.controller("myControllerNG",myControllerNG);

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


