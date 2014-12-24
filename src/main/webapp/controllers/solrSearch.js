var app = angular.module('sportsWebSearch', []);

app.controller('SolrSearchCtrl', [ '$scope', '$http', '$state', function($scope, $http, $state) {            	
	$scope.articles = [];
	
	$scope.submit = function() {
		if ($scope.query) {
			var params = {
				query : encodeURIComponent($scope.query)
			};

			$http.get('/solr/search', {
				params : params
			}).success(function(data) {
				$scope.articles = data.articles;
			});

			$scope.query = '';
		}
	};            	
} ]);