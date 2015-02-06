var app = angular.module('sportsWebSearch');

app.controller('EntitiesCtrl', [ '$scope', '$http', '$state',
		'$stateParams', function($scope, $http, $state, $stateParams) {	
	$scope.quotes = [];
	$scope.results = [];
	$scope.url = $stateParams.url;

	$scope.submit = function(url) {
		if (url) {
			var params = {
					url : encodeURIComponent(url)
				};
			
			$http.get('/solr/entities', {
				//params: params
			}).success(function(data) {				
				$scope.quotes = data.quotes;
				$scope.quotes = data.results;
			});
		}
	};

	$scope.submit("test");
} ]);