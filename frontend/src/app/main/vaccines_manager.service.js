'use strict';

angular.module('vaccinations')
.service('vaccinesManager', ['$http', 'appConstants', function($http, appConstants) {
    var self = this;
    var promise = $http.get(
        appConstants.URL +
        '/openmrs/ws/rest/v2/vaccinationsmodule/' +
        '/vaccines/unscheduled')

    .success( function(data) {
        self.vaccines = data;
    });

    var exports = {
        getVaccines: function() {
            return promise;
        }
    };

    return exports;

}]);