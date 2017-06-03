angular.module('mainModule')
	.controller('programaCtrl', function($scope, programsService, $location, $routeParams){
		$scope.programa = [];
		$scope.prueba = $routeParams.programaId;
		function getPrograma(id){
			programsService.getProgram(id)
			.success(function(data){
				$scope.programa = data;
			})
			.error(function(error){
				$scope.message = "FAIL";

			});
		}
		getPrograma($scope.prueba);

		//Aqui deberia transformar para graficos


		console.log($scope.programa[0]);

		//var json = JSON.parse($scope.programa);

	});