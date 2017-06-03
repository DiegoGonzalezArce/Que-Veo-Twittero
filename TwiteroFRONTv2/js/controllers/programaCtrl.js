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

		var torta = [
			{"label": "Neutrales", "count": 123},
			{"label": "Positivas", "count": 100},
			{"label": "Negativas", "count": 23},
			];

		console.log(torta);
	});