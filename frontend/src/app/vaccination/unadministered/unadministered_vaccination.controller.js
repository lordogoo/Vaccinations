'use strict';

// Manages a vaccination instance on each vaccination
// directive.
angular.module('vaccinations')
.controller('UnAdminVaccinationController', ['$scope', 'vaccinationsManager',
    function($scope, vaccinationsManager){

    // Form states and methods.
    $scope.state = {};
    $scope.state.administerFormOpen = false;

    $scope.resetFormDataToDefaults = function(){

        var vaccination = angular.copy($scope.getVaccination());
        vaccination.administration_date = new Date();
        // vaccination.manufacture_date = new Date();
        // vaccination.expiry_date = new Date();
        vaccination.scheduled_date = new Date(vaccination.scheduled_date);
        $scope.enteredAdminFormData = vaccination;
        if ($scope.enteredAdminFormData.scheduled_date <= (new Date())) {
            $scope.due = true;
        }
    };

    $scope.toggleAdministerForm = function(){
        $scope.resetFormDataToDefaults();
        $scope.state.administerFormOpen = !$scope.state.administerFormOpen;
    };

    $scope.toggleAuditLog = function() {
        $scope.state.auditLogOpen = !$scope.state.auditLogOpen;
    };

    // Called when vaccination data from form has been validated
    // and ready to create a new vaccination event.
    $scope.submitVaccination = function(vaccination) {
        var vaccsOrigCopy = angular.copy($scope.getVaccination());
        var vaccination = angular.copy(vaccination);
        vaccination.administered = true;
        vaccinationsManager.submitVaccination(vaccination, vaccsOrigCopy);
    };

    // Only available if the vaccination is of type unscheduled.
    $scope.deleteVaccination = function(vaccination) {
        vaccinationsManager.deleteVaccination(vaccination);
    };

    // Form data inits.
    $scope.resetFormDataToDefaults();
}]);
