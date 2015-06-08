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
        enteredAdminFormDataCopy.scheduled_date = null;
        // This field is the vaccine_id and needs to be removed on posts to create new vaccinations.
        enteredAdminFormDataCopy.id = null;
        vaccinationsManager.submitVaccination(enteredAdminFormDataCopy);
    };

    $scope.scheduleVaccination = function (enteredAdminFormData) {
        // When scheduling remove date properties that are setup for
        // administration.
        var enteredAdminFormDataCopy = angular.copy(enteredAdminFormData);
        enteredAdminFormDataCopy.administration_date = null;
        enteredAdminFormDataCopy.manufacture_date = null;
        enteredAdminFormDataCopy.expiry_date = null;
        enteredAdminFormDataCopy.id = null;
        vaccinationsManager.submitVaccination(enteredAdminFormDataCopy);
    };

    $scope.resetFormDataToDefaults();
}]);
