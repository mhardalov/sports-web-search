// app.js
var routerApp = angular.module('sportsWebSearch', ['ui.router', 'ui.bootstrap']);

routerApp.config(function($stateProvider, $urlRouterProvider) {
    
    $urlRouterProvider.otherwise('/home');
    
    $stateProvider
        .state('home', {
            url: '/home',
            templateUrl: '/views/solrSearch.html',
            controller: function($scope, $http, $state) {            	
            	$scope.articles = [];
            	$scope.resultCount = 0;
            	$scope.elapsedTime = 0;
        	    $scope.currentPage = 1;
        	    $scope.maxSize = 10;
        	    
        	    $scope.setPage = function (pageNo) {
        	      $scope.currentPage = pageNo;
        	    };

        	    $scope.pageChanged = function() {
        	      $scope.submit();
        	    };

            	$scope.submit = function() {
            		if ($scope.query) {
            			var params = {
            				query: encodeURIComponent($scope.query),
            				page: $scope.currentPage
            			};

            			$http.get('/solr/search', {
            				params : params
            			}).success(function(data) {
            				$scope.articles = data.articles;
            				$scope.resultCount = data.resultCount;
            				$scope.elapsedTime = data.elapsedTime;
            			});
            		}
            	};            	
            }
        })
        .state('category', {
        	url: '/category',
        	templateUrl: '/views/categoryFinder.html',
        	controller: function($scope, $http, $state) {            	
            	$scope.articles = [];
            	$scope.maxScore = 0;
            	
            	$scope.submit = function() {
            		if ($scope.content) {
            			var query = {content:$scope.content};

            			$http.post('/solr/category', {
            				content : encodeURIComponent($scope.content)

            			}).success(function(data) {
            				$scope.articles = data.articles;
            				$scope.resultCount = data.maxScore;
            			});
            		}
            	};            	
            }
        })
        .state('about', {
            // we'll get to this in a bit       
        });
        
});

