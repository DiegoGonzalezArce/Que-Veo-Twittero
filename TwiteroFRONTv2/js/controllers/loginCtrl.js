angular.module('mainModule')
	.controller('loginCtrl', function($scope,$http){
		$scope.username = "";
		$scope.password = "";	

		console.log($scope.data);
	});