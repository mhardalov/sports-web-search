var app = angular.module('sportsWebSearch');

app.controller('SolrSearchCtrl', [ '$scope', '$http', '$state', 'anchorSmoothScroll', function($scope, $http, $state, anchorSmoothScroll) {            	
	$scope.articles = [];
	$scope.resultCount = 0;
	$scope.elapsedTime = 0;
    $scope.currentPage = 1;
    $scope.maxSize = 10;
    $scope.shouldBeOpen = true;
    
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
		
		anchorSmoothScroll.scrollTo("container");
	};            	
} ]);