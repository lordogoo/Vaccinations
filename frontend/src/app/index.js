'use strict';

/**
 * @ngdoc overview
 * @name vaccinationsApp
 * @description
 * # vaccinationsApp
 *
 * Main module of the application.
 */
 angular
 .module('vaccinations', [
    'ngAnimate',
    'ngCookies',
    'ngSanitize',
    'ngResource',
    'angular.filter'
])
// Top level angular configs
.config(function ($httpProvider, $locationProvider) {
    $httpProvider.defaults.withCredentials = true;
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
});