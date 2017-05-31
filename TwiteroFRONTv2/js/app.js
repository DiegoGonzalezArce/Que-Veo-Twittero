var app = angular.module('mainModule', ['ngRoute', 'd3']);
//Modulo para el grafico
angular.module('d3', []);


app.config(function($routeProvider, $locationProvider){
	$locationProvider.hashPrefix('');
	$routeProvider
	.when('/home', {
		templateUrl: 'views/home.html',
		controller: 'homeCtrl'
	})
	.when('/ranking', {
		templateUrl: 'views/ranking.html',
		controller: 'rankingCtrl'
	})
	.when('/about', {
		templateUrl: 'views/about.html',
		controller: 'aboutCtrl'
	})
	.when('/categorias', {
		templateUrl: 'views/categoria.html',
		controller: 'categoriasCtrl'
	})
	.when('/programa/:programaId', {
		templateUrl: 'views/Programas.html',
		controller: 'programaCtrl'
	})
	.otherwise({
		redirectTo: '/home'
	});
});
