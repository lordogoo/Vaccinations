'use strict';

angular.module('vaccinations')
.controller('StagedVaccinationController', ['$scope', '$filter', 'vaccinationsManager',
    function ($scope, $filter, vaccinationsManager) {
    $scope.state = {};
    $scope.state.administerFormOpen = true;

    $scope.removeStagedVaccination = function () {
        vaccinationsManager.removeStagedVaccination();
    };

    $scope.resetFormDataToDefaults = function () {
         var vaccination = angular.copy($scope.getVaccination());
         vaccination.administration_date = new Date();
         vaccination.manufacture_date = new Date();
         vaccination.expiry_date = new Date();
         vaccination.scheduled_date = new Date();
         $scope.enteredAdminFormData = vaccination;
    };

    $scope.saveVaccination = function (enteredAdminFormData) {
        // When saving an administered vaccination ensure no scheduled
        // date is saved.
        var enteredAdminFormDataCopy = angular.copy(enteredAdminFormData);
        delete enteredAdminFormDataCopy.scheduled_date;
        enteredAdminFormDataCopy.name = $filter('uppercase')(enteredAdminFormDataCopy.name);
        vaccinationsManager.submitVaccination(enteredAdminFormDataCopy);
    };

    $scope.scheduleVaccination = function (enteredAdminFormData) {
        // When scheduling remove date properties that are setup for
        // administration.
        var enteredAdminFormDataCopy = angular.copy(enteredAdminFormData);
        delete enteredAdminFormDataCopy.administration_date;
        delete enteredAdminFormDataCopy.manufacture_date;
        delete enteredAdminFormDataCopy.expiry_date;
        enteredAdminFormDataCopy.name = $filter('uppercase')(enteredAdminFormDataCopy.name);
        vaccinationsManager.submitVaccination(enteredAdminFormDataCopy);
    };

    $scope.resetFormDataToDefaults();
}]);
