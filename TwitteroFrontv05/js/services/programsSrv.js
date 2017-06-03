angular.module('mainModule')
	.service('programsService', function($http){
		var urlBase = 'http://localhost:8080/QVTwittero/programas';
		var urlBase2 = 'http://localhost:8080/QVTwittero/canales';
		var urlBase3 = 'http://localhost:8080/QVTwittero/programas/1';

		this.getPrograms = function(){
			return $http.get(urlBase);
		};
		this.getCanales = function(){
			return $http.get(urlBase2);
		};
		this,getProgram= function(){
			return $http.get(urlBase3);
		};
	});