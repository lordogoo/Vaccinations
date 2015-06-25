'use strict';

/**
 * @ngdoc overview
 * @name vaccinations
 * @description
 * # vaccinations
 *
 * Main module of the application.
 */

// Constants for this instance of the app
angular.module('vaccinations')
.service('appConstants', function ($http, $location) {
    self = this;
    self.bodySitesAdministered;
    self.routes;
    self.dosingUnits;

    var exports = {
        // Set url for testing
        URL: 'http://208.77.196.178:64000',
        PATH:'/openmrs/ws/rest/v2/vaccinationsmodule/vaccinations',

        // Retrive patient ID from window.location;
        getPatientId: function () {
            var patientId = $location.search().patientId;
            if (!patientId) {
                throw new Error('The patient ID could not be parsed from the URL');
            } else {
                return patientId;
            }
        },

        // Set body site administered
        setRoutes: function (routes) {
            self.routes = routes;
        },

        getRoutes: function() {
            return self.routes;
        },

        setDosingUnits: function (dosingUnits) {
            self.dosingUnits = dosingUnits;
        },

        getDosingUnits: function () {
            return self.dosingUnits;
        },

        setBodySitesAdministered: function (bodySites) {
            self.bodySitesAdministered = bodySites
        },

        getBodySitesAdministered: function () {
            return self.bodySitesAdministered;
        }
    };

    return exports;
});
