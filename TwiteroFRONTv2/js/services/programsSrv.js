angular.module('mainModule')
	.service('programsService', function($http){
		var urlBase = 'http://localhost:8080/QVTwittero/programas';
		var urlBase2 = 'http://localhost:8080/QVTwittero/canales';
		

		this.getPrograms = function(){
			return $http.get(urlBase);
		};
		this.getCanales = function(){
			return $http.get(urlBase2);
		};
		this.getProgram= function(id){
			return $http.get('http://localhost:8080/QVTwittero/programas/'+id);
		};

		this.getTweet = function(id){
			return $http.get('http://localhost:8080/QVTwittero/programas/tweetMencionado/'+id);
		}
	});