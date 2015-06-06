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
.config(function ($httpProvider) {
    $httpProvider.defaults.withCredentials = true;
});