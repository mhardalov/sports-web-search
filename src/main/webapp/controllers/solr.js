var app = angular.module('sportsWebSearch', []);

app.controller('SolrController', [ '$scope', '$http', function($scope, $http) {
	$scope.submit = function() {
		if ($scope.query) {
			var url = '${solr.url}';
			var data = {
				query : $scope.query
			};

			$http.get('/solr/search', {
				params : data
			}).success(function(data) {
				$scope.greeting = data;
			});

			$scope.query = '';
		}
	};
} ]);