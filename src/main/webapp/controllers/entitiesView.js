var app = angular.module('sportsWebSearch');

app.controller('EntitiesCtrl', [ '$scope', '$http', '$state',
		'$stateParams', function($scope, $http, $state, $stateParams) {	
	$scope.quotes = [];
	$scope.results = [];
	$scope.url = $stateParams.url;
	$scope.quering = false;

	$scope.submit = function() {		
		$scope.quering = true;
		
		$http.get('/solr/entities', {
			 params: $scope.query
		}).success(function(data) {				
			$scope.quotes = data.quotes;
			$scope.results = data.results;
		}).finally(function() {
			$scope.quering = false;
		});		
	};	
	
	$scope.status = {
	    isResultsOpen: true,
	    isQuotesOpen: true
	  };
	
	$scope.query = {
		entity: "",
		afterDate: null,
		beforeDate: null,
	}
	
	$scope.today = function() {
	    $scope.dt = new Date();
	  };
	  
	$scope.today();
	
	$scope.clear = function () {
		$scope.dt = null;
	};
	
	// Disable weekend selection
	$scope.disabled = function(date, mode) {
		return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
	};
	
	$scope.toggleMin = function() {
		$scope.minDate = $scope.minDate ? null : new Date();
	};
	$scope.toggleMin();
	
	$scope.open = function($event) {
		$event.preventDefault();
	    $event.stopPropagation();
	
	    $scope.opened = true;
	};
	
	$scope.dateOptions = {
	    formatYear: 'yy',
	    startingDay: 1
	};
	
	$scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
	$scope.format = $scope.formats[0];
} ]);