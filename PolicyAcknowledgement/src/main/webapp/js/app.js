

/*function convertAsArray(arrayP,username,status){
	newArray = new Array();
	var newStr;
	for(var i =0;i<arrayP.length;i++){
		newStr=	arrayP[i].username+","+arrayP[i].status;
		newArray.push(newStr);
		console.log(newArray[i]);
	}
	return newArray;
	
}
*/
var app = angular.module('pdfDemo', []);




function pdfController($scope,$http) {
	
	$scope.objectHeaders = [];
  // prepare the document definition using declarative approach
	  $http.get('http://localhost:8080/policyApp/rest/getData/getReport','')
		.then(function(response){
			 $scope.data = response.data;	
			 console.log($scope.data);
			 
			/*angular.forEach($scope.data, function (value, key){
				 $scope.objectHeaders.push(value.username);
                 //console.log(value.username);
                 //console.log($scope.objectHeaders[0]);
                  
             })*/
			 
			 /*var array = $.map( $scope.data, function(value, index) {
				    return [value];
				}); 
			 console.log("New Array is"+array); */
			 /*var arrs = {}, i;
			 for (i = 0; i <= $scope.data.length; i++) {
				    arrs['arr' + i] = [];
				    $scope.arrs[i].push($scope.data[i])
			}
				console.log( arrs[0].username);*/
		});	
                 
  var docDefinition = {
    content: [
      {
        text: 'UserName and General Policy Status'
      },
      {
        style: 'demoTable',
        table: {
          widths: ['*'],
          body: [
            [{text: 'UserName', style: 'header'}],
            
            
            /*['Kritika.sarathe','A'],
            ['Neha.Rai','B']*/
           
           
            
            /*convertAsArray($scope.objectHeaders,username,status)*/
          ]
        }
      }
    ],
    styles: {
      header: {
        bold: true,
        color: '#000',
        fontSize: 11
      },
      demoTable: {
        color: '#666',
        fontSize: 10
      }
    }
  };

  $scope.openPdf = function() {
    pdfMake.createPdf(docDefinition).open();
  };

  $scope.downloadPdf = function() {
    pdfMake.createPdf(docDefinition).download();
  };
}var controller = app.controller('pdfCtrl', pdfController);
