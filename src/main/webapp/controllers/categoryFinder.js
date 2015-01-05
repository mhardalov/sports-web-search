var app = angular.module('sportsWebSearch');

app.controller('CategoryFinderCtrl', [ '$scope', '$http', '$state', function($scope, $http, $state) {            	
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
		
		anchorSmoothScroll.scrollTo("container");
	};            	
} ]);