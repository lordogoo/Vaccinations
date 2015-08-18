'use strict';

/**
 * @ngdoc function
 * @name vaccinations.controller:VaccinationsController
 * @description
 * # VaccinationsController
 * Controller of the vaccinations
 */
angular.module('vaccinations')
.controller('MainController', ['$scope', '$filter', 'vaccinationsManager', 'vaccinesManager', 'helperFunctions',
    function($scope, $filter, vaccinationsManager, vaccinesManager, helperFunctions){

    // Get list of patient vaccinations.
    vaccinationsManager.getVaccinations().success(function(data) {
        $scope.vaccinations = data[0];
        $scope.dropDownData = {};
        $scope.dropDownData.routes = data[1];
        $scope.dropDownData.dosingUnits = data[2];
        $scope.dropDownData.bodySites = data[3];
        $scope.dropDownData.manufacturers = data[4];
        $scope.dropDownData.changeReasons = data[5];
        $scope.dropDownData.routeMaps = data[6];
    });


    // Get list of staged vaccinations.
    $scope.stagedVaccinations = vaccinationsManager.getStagedVaccinations();

    // Get list of vaccines.
    vaccinesManager.getVaccines().success( function(data) {
        $scope.vaccines = data;
    });

    $scope.stageVaccination = function (vaccine, scheduled) {
        var stagedVaccination = angular.copy(vaccine);
        stagedVaccination._staged = true;
        if (scheduled) {
            stagedVaccination._scheduling = true;
        } else {
            stagedVaccination._administering = true;
        }
        vaccinationsManager.addStagedVaccination(stagedVaccination);
        $scope.newVaccine = '';
    };

    $scope.formatVaccine = function (vaccine) {
        return helperFunctions.formatVaccineName(vaccine);
    };
}]);
