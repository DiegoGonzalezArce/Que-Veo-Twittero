angular.module('mainModule')
	.service('categoriaService', function($http){
		var urlBase = 'http://localhost:8080/QVTwittero/categorias';
		this.getCategorias = function(){
			return $http.get(urlBase);
		};
	});