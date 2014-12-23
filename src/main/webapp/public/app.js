var myApp = angular.module('sportsWebSearch', [ 'ui.router' ]);

myApp.config(function($stateProvider, $urlRouterProvider) {
	//
	// For any unmatched url, redirect to /state1
	$urlRouterProvider.otherwise("dashboard");
	//
	// Now set up the states
	$stateProvider.state('dashboard', {
		url : "dashboard",
		templateUrl : "/views/solrSearch.html",
		controller: "SolrSearchController"
	})
});