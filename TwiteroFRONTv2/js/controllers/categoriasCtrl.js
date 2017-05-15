angular.module('mainModule')	
	.controller('categoriasCtrl', function($scope, categoriaService){
		$scope.categorias = [];
		function getCategorias(){
			categoriaService.getCategorias()
			.success(function(data){
				$scope.categorias = data;
			})
			.error(function(error){
				$scope.message = "FAIL";
			});
		}
		getCategorias();

	})