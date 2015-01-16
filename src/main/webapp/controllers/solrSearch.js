var app = angular.module('sportsWebSearch');

app.controller('SolrSearchCtrl', [ '$scope', '$http', '$state', 'anchorSmoothScroll', function($scope, $http, $state, anchorSmoothScroll) {            	
	$scope.articles = [];
	$scope.resultCount = 0;
	$scope.elapsedTime = 0;
    $scope.currentPage = 1;
    $scope.maxSize = 10;
    $scope.shouldBeOpen = true;
    $scope.quering = false;
    $scope.querySuggestion = '';
    
    $scope.setPage = function (pageNo) {
      $scope.currentPage = pageNo;      
    };

    $scope.pageChanged = function() {
      $scope.submit();
    };
    
    $scope.currectQuery = function() {
    	$scope.query = $scope.querySuggestion;
    	$scope.submit();
    }

	$scope.submit = function() {
		if ($scope.query) {
			$scope.quering = true;
			
			var params = {
				query: encodeURIComponent($scope.query),
				page: $scope.currentPage
			};

			$http.get('/solr/search', {
				params : params
			}).success(function(data) {
				$scope.articles = data.queryData.articles;
				$scope.resultCount = data.queryData.resultCount;
				$scope.elapsedTime = data.queryData.elapsedTime;
				$scope.querySuggestion = data.spellSuggestion.suggestionQuery;	
				$scope.setPage(1);
			}).finally(function() {
				$scope.quering = false;
			});
		}
		
		anchorSmoothScroll.scrollTo("container");
	};            	
} ]);