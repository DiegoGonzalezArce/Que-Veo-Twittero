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
	.when('/login', {
		templateUrl: 'views/login.html',
		controller: 'loginCtrl'
	})
	.when('/programa/:programaId', {
		templateUrl: 'views/Programas.html',
		controller: 'programaCtrl'
	})
	.when('/grafo', {
		templateUrl: 'views/grafo.html',
		controller: 'grafoCtrl'
	})
	.when('/benjamin/admin', {
		templateUrl: 'views/administrador.html',
		controller: 'adminCtrl'
	})
	.when('/benjamin/agregar', {
		templateUrl: 'views/agregar.html',
		controller: 'agregarCtrl'
	})
	.when('/benjamin/modificar', {
		templateUrl: 'views/modificar.html',
		controller: 'modificarCtrl'
	})
	.otherwise({
		redirectTo: '/home'
	});
});
