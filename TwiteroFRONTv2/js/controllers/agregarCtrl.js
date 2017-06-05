angular.module('mainModule')
	.controller('agregarCtrl', function($scope, $window){

		$scope.doalert = function(){
			$window.alert("¡Programa Agregado!");
		};
		console.log($scope);
	});