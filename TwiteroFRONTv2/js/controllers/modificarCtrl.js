angular.module('mainModule')
	.controller('modificarCtrl', function($scope, programsService){
		$scope.programas = [];
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

		console.log("MATENME");
		console.log($scope);
	});