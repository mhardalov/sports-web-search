// app.js
var routerApp = angular.module('sportsWebSearch', ['ui.router', 'ui.bootstrap']);

routerApp.config(function($stateProvider, $urlRouterProvider) {
    
    $urlRouterProvider.otherwise('/home');
    
    $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: '/views/solrSearch.html',
            controller: 'SolrSearchCtrl'
        })
        .state('category', {
        	url: '/category',
        	templateUrl: '/views/categoryFinder.html',
        	controller: 'CategoryFinderCtrl'
        })
        .state('about', {
            // we'll get to this in a bit       
        });
        
});

