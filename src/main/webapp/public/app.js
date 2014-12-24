// app.js
var routerApp = angular.module('sportsWebSearch', ['ui.router']);

routerApp.config(function($stateProvider, $urlRouterProvider) {
    
    $urlRouterProvider.otherwise('/home');
    
    $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: '/views/solrSearch.html',
            controller: function($scope, $http, $state) {            	
            	$scope.articles = [];
            	$scope.resultCount = 0;
            	
            	$scope.submit = function() {
            		if ($scope.query) {
            			var params = {
            				query : encodeURIComponent($scope.query)
            			};

            			$http.get('/solr/search', {
            				params : params
            			}).success(function(data) {
            				$scope.articles = data.articles;
            				$scope.resultCount = data.resultCount;
            			});
            		}
            	};
            	
            }
        })
        .state('about', {
            // we'll get to this in a bit       
        });
        
});

