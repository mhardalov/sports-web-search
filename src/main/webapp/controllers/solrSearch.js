var app = angular.module('sportsWebSearch');

app.controller('SolrSearchCtrl', [ '$scope', '$http', '$state', function($scope, $http, $state) {            	
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
	};            	
} ]);

app.directive('focusMe', function($timeout, $parse) {
  return {
    link: function(scope, element, attrs) {
      var model = $parse(attrs.focusMe);
      scope.$watch(model, function(value) {
        console.log('value=',value);
        if(value === true) { 
          $timeout(function() {
            element[0].focus(); 
          });
        }
      });
      element.bind('blur', function() {
        console.log('blur')
        scope.$apply(model.assign(scope, false));
      })
    }
  };
});