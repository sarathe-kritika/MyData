////<reference path="angular.min.js"/>

var myApp = angular.module("myApp",['angularSpinners']);


var myControllerPolicy = function($scope,$http,$timeout, spinnerService){
	
	$scope.saveStatusPolicy = false;
	
	var username = getParameterByName("username");
	$scope.username = username;
	console.log("user name"+username);
	var full_name = getParameterByName("full_name");
	$scope.full_name = full_name;
	console.log("full name"+full_name);
	
	
	$scope.ndaStatus = false;
	$scope.policyStatus = false;
	
	$scope.changeNda = function() {
		$scope.ndaStatus=true;
      };
      
      $scope.changePolicy = function() {
    	  $scope.policyStatus=true;
       }; 
      
      
   	
         
	   $scope.saveStatus = function(confirmedNda,confirmedPolicy){	
    		console.log("ndapolicy:: "+$scope.confirmedNda);
    		console.log("confirmedPolicy ::"+$scope.confirmedPolicy);
    			var statusNda = $scope.confirmedNda;
    			var statusPolicy=$scope.confirmedPolicy;
    			/* Spinner Code*/
    			spinnerService.show('html5spinner');
    			$timeout(function () {        
    				spinnerService.hide('html5spinner');        
    				$scope.loggedIn = true;      }, 30000); 
    			
		$http({
			method:'POST',
			url:window.location.origin+'/policyApp/rest/getData/updateStatus/'+username+'/'+statusNda+'/'+statusPolicy,
			contentType:'application/json',
			data:status,
			headers:{'Content-Type' : 'application/json'}
		}).then(function(response){
			console.log(response);
			console.log("response"+response);
			$scope.res = response.data;
			console.log("response"+$scope.res);
			window.location = 'sucess.html?username='+$scope.res;
		
		});
		
		
	};
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

myApp.controller("myControllerPolicy",myControllerPolicy);



function caller()
{
  alert("please wait for a while");
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

/*function customAlert(msg,duration)
{
 var styler = document.createElement("div");
  styler.setAttribute("style","border: solid 5px Red;width:200px;height100px:;top:50%;left:40%;background-color:#444;color:Silver");
 styler.innerHTML = "<h1>"+msg+"</h1>";
 setTimeout(function()
 {
   styler.parentNode.removeChild(styler);
 },duration);
 document.body.appendChild(styler);
}
*/