var app = angular.module('sportsWebSearch');

app.controller('ArticleViewCtrl', [ '$scope', '$http', '$state',
		'$stateParams', function($scope, $http, $state, $stateParams) {
	$scope.article = {};
	$scope.quotes = [];
	$scope.results = [];
	
	$scope.url = $stateParams.url;

	$scope.submit = function(url) {
		if (url) {
			var params = {
					url : encodeURIComponent(url)
				};
			
			$http.get('/solr/article', {
				params: params
			}).success(function(data) {
				$scope.article = data;
				$scope.quotes = data.ontoResult.quotes;
				$scope.results = data.ontoResult.results;
			});
		}
	};

	$scope.submit($scope.url);
} ]);