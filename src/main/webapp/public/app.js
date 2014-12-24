var myApp = angular.module('sportsWebSearch', [ 
	'SolrSearchController',
	'ui.router',
	'ngAnimate' 
]);

myApp.run(
		[ '$rootScope', '$state', '$stateParams',
				function($rootScope, $state, $stateParams) {
					// It's very handy to add references to $state and
					// $stateParams to the $rootScope
					// so that you can access them from any scope within
					// your applications.For example,
					// <li ng-class="{ active:
					// $state.includes('contacts.list') }"> will set the
					// <li>
					// to active whenever 'contacts.list' or one of its
					// decendents is active.
					$rootScope.$state = $state;
					$rootScope.$stateParams = $stateParams;
				} ]).config(function($stateProvider, $urlRouterProvider) {
	//
	// For any unmatched url, redirect to /
	$urlRouterProvider.otherwise("/");

	//
	// Now set up the states
	$stateProvider.state('dashboard', {
		url : "dashboard",
		template : "<p> somethings here.</p>",
		controller : "SolrSearchController"
	})
});