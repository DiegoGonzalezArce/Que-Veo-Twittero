angular.module('mainModule')
	.controller('programaCtrl', function($scope, programsService, $location, $routeParams){
		$scope.programas = [];
		//$scope.prueba = $location.absUrl().slice(-1); //obtener el ultimo elemento -> pasarselo al scope???
		$scope.prueba = $routeParams.programaId;
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


		//Llamo al servicio
		//$scope.programaId = $scope.programas[programaId].programaId;
		//this.programaId = $scope.programaId;

		console.log($scope);



	});