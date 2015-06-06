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
.service('appConstants', function ($http) {
    var exports = {
        // Set url for testing
        URL: 'http://208.77.196.178:64000',
        PATH:'/openmrs/ws/rest/v2/vaccinationsmodule/vaccinations',


        // Should only be used for testing purposes
        setPatiendId: function (id) {
            this.patientId = id;
        },

        // TODO: Get the session ID if its not auto appended to requests.
        getSessionId: function () {

        },

        getURL: function (url) {

        },

        // Retrive patient ID from window.location;
        getPatientId: function (url) {
            return 3;

            if (this.patientId) {
                return this.patientId;
            }
            var patientId = /[0-9]+$/.exec(url);
            if (patientId === undefined || patientId === null) {
                throw new Error('The patient ID cannot be parsed from the URL');
            }
            if (patientId.length > 1 || patientId.length === 0) {
                throw new Error('The patient ID URL matched regex more than once.');
            }
            if (patientId[0] === undefined) {
                throw new Error('The patient ID could not be parsed from URL.');
            }
            // TODO: remove patientId and return actual
            return patientId[0];
        }
    };

    return exports;
});