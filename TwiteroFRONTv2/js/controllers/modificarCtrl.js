angular.module('mainModule')
	.controller('modificarCtrl', function($scope, $window, programsService){
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

		$scope.doalert = function(){
			$window.alert("¡Programa Modificado!");
		};
		console.log($scope);
	});