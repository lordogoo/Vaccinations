'use strict';

/**
 * @ngdoc function
 * @name vaccinations.controller:VaccinationsController
 * @description
 * # VaccinationsController
 * Controller of the vaccinations
 */
angular.module('vaccinations')
.controller('MainController', ['$scope', '$filter', 'vaccinationsManager', 'vaccinesManager', 'helperFunctions', 'appConstants',
    function($scope, $filter, vaccinationsManager, vaccinesManager, helperFunctions, appConstants){

    // Get administation status.
    $scope.adminStatus = appConstants.getAdminStatus();

    // Get list of patient vaccinations.
    vaccinationsManager.getVaccinations().success(function(data) {
        $scope.vaccinations = data[0];
        $scope.dropDownData = {};
        $scope.dropDownData.routes = data[1];
        $scope.dropDownData.dosingUnits = data[2];
        $scope.dropDownData.bodySites = data[3];
        $scope.dropDownData.manufacturers = data[4];
        $scope.dropDownData.changeReasons = data[5];
        $scope.dropDownData.routeMaps = $scope.assembleBodySiteMaps(data[6]);
        var today = new Date();
        var year = today.getFullYear();
        var month = today.getMonth();
        if (String(month).length < 2) {
            month = '0' + month;
        }
        var day = today.getDate();
        if (String(day).length < 2) {
            day = '0' + day;
        }
        $scope.dropDownData.today = year + '-' + month + '-' + day;
        console.log($scope.dropDownData.today);
    });

    $scope.assembleBodySiteMaps = function(fragmentedMap) {
        var assembledMap = {};
        for (var i = 0; i < fragmentedMap.length; i++) {
            for (var key in fragmentedMap[i]) {
                assembledMap[key] =  fragmentedMap[i][key];
            }
        }
        return assembledMap;
    };

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
