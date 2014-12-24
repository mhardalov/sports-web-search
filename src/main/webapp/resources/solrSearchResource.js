var app = angular.module('sportsWebSearch', []);

app.factory('solrSearchResource', ['$resource', function($resource) {
	   return $resource('/home/:queryString', {
		queryString : '@query'
	})
} ]);