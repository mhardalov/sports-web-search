var app = angular.module('sportsWebSearch', []);

app.controller('SolrSearchController', [ '$scope', '$http', function($scope, $http) {
	$scope.articles = [{ url: 'sdfsdf' }];
	
	$scope.submit = function() {
		if ($scope.query) {
			var params = {
				query : $scope.query
			};

			$http.get('/solr/search', {
				params : params
			}).success(function(data) {
				$scope.articles = data;
			});

			$scope.query = '';
		}
	};
} ]);