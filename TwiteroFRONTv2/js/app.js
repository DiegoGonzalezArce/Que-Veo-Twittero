var app = angular.module('mainModule', [
	'ngRoute'
	]);
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

	.otherwise({
		redirectTo: '/home'
	});
});