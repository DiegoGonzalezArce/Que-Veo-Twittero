angular.module('mainModule')
	.service('programsService', function($http){
		var urlBase = 'http://localhost:8080/QVTwittero/programas';
		var urlBase2 = 'http://localhost:8080/QVTwittero/canales';
		this.getPrograms = function(){
			return $http.get(urlBase);
		};
		this.getCanales = function(){
			return $http.get(urlBase2);
		}
	});