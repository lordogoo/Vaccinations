'use strict';

// Manages a vaccination instance on each vaccination
// directive.
angular.module('vaccinations')
.controller('AdminVaccinationController', ['$scope', 'vaccinationsManager', function($scope, vaccinationsManager){
    // Form data inits.
    $scope.enteredEditFormData = {};
    $scope.enteredAdverseFormData = {};

    // Form states and methods.
    $scope.state = {};
    $scope.state.editFormOpen = false;
    $scope.state.adverseFormOpen = false;

    $scope.toggleReactionForm = function(){
        $scope.state.editFormOpen = false;
        $scope.state.adverseFormOpen = !$scope.state.adverseFormOpen;
    };

    $scope.toggleEditForm = function(){
        $scope.state.adverseFormOpen = false;
        $scope.state.editFormOpen = !$scope.state.editFormOpen;
    };

    $scope.resetFormDataToDefaults = function () {
        var vaccination = angular.copy($scope.getVaccination());
        vaccination = angular.copy($scope.getVaccination());
        vaccination.administration_date = new Date(vaccination.administration_date);
        vaccination.manufacture_date = new Date(vaccination.manufacture_date);
        vaccination.expiry_date = new Date(vaccination.expiry_date);
        $scope.enteredEditFormData = vaccination;

        if (vaccination.adverse_reaction_observed) {
            $scope.enteredAdverseFormData = vaccination.reaction_details;
            $scope.enteredAdverseFormData.date = new Date(vaccination.reaction_details.date);
        } else {
            $scope.enteredAdverseFormData.date = new Date();
        }
    };

    $scope.addReaction = function (reaction, enteredAdminFormData) {
        vaccinationsManager.submitReaction(reaction, enteredAdminFormData);
    };

    $scope.removeReaction = function (reaction) {
        vaccinationsManager.removeReaction(reaction);
    };

    // Available for all administered vaccinations.
    // Deletes a vaccination of type unscheduled, vaccinations of type
    // scheduled become unadministered instead of being removed.
    $scope.deleteVaccination = function(vaccination) {
        vaccinationsManager.deleteVaccination(vaccination);
    };

    $scope.updateVaccination = function (vaccination) {
        vaccinationsManager.submitVaccination(vaccination);
    };

    $scope.unadministerVaccination = function (vaccination) {
        vaccinationsManager.deleteVaccination(vaccination);

        // Remove all information pertaining to administration.
        // vaccination.administered = false;
        // vaccination.adverse_reaction_observed = null;
        // vaccination.reaction_details = null;
        // vaccination.administration_date = null;
        // vaccination.lot_number = null;
        // vaccination.manufacture_date = null;
        // vaccination.expiry_date = null;
        // vaccination.manufacturer = null;
        // vaccinationsManager.submitVaccination(vaccination);
    };

    $scope.resetFormDataToDefaults();

}]);
