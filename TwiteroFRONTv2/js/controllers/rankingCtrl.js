angular.module('mainModule')
	.controller('rankingCtrl', function($scope, programsService){
		$scope.programas = [];
		$scope.canales = [];
		$scope.message = "";
		function getProgramas(){
			programsService.getPrograms()
			.success(function(data){
				$scope.programas = data;
			})
			.error(function(error){
				$scope.message = "FAIL";

			});
		}
		getProgramas();
		console.log($scope.programas);

	});
