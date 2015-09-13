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
.config(["$httpProvider", "$locationProvider", function ($httpProvider, $locationProvider) {
    $httpProvider.defaults.withCredentials = true;
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
}]);
'use strict';

// Manages a vaccination instance on each vaccination
// directive.
angular.module('vaccinations')
.controller('UnAdminVaccinationController', ['$scope', 'vaccinationsManager',
    function($scope, vaccinationsManager){

    // Form states and methods.
    $scope.state = {};
    $scope.state.administerFormOpen = false;
    $scope.state.rescheduleFormOpen = false;

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

     $scope.toggleRescheduleForm = function(){
        $scope.resetFormDataToDefaults();
        $scope.state.rescheduleFormOpen = !$scope.state.rescheduleFormOpen;
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


    $scope.rescheduleVaccination = function(vaccination) {
        var vaccsOrigCopy = angular.copy($scope.getVaccination());
        var vaccination = angular.copy(vaccination);
        vaccination.administered = false;
        vaccination.administration_date = null;
        vaccinationsManager.submitVaccination(vaccination, vaccsOrigCopy);
    };

    // Only available if the vaccination is of type unscheduled.
    $scope.deleteVaccination = function(vaccination) {
        vaccinationsManager.deleteVaccination(vaccination);
    };

    // Form data inits.
    $scope.resetFormDataToDefaults();
}]);

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
        if (vaccination.custom) {
            vaccination.name = '';
        }
        vaccination.administration_date = new Date();
        vaccination.scheduled_date = new Date();
        $scope.enteredAdminFormData = vaccination;
    };

    // Transform a vaccine object into a vaccination object that
    // can be submitted to the vaccinations endpoint.
    $scope.transformVaccineToVaccination = function (vaccine) {
        var vaccination = angular.copy(vaccine);
        // Create nested objects
        debugger;
        vaccination.adverse_reaction_observed = false;
        vaccination.simpleVaccine = {};
        vaccination.simpleVaccine.uuid = vaccine.uuid;
        vaccination.simpleAdverse_reaction = null;
        delete vaccination.uuid;
        return vaccination;
    };

    $scope.saveVaccination = function (enteredAdminFormData) {
        // When saving an administered vaccination ensure no scheduled
        // date is saved.
        var enteredAdminFormDataCopy = angular.copy(enteredAdminFormData);
        var vaccination = $scope.transformVaccineToVaccination(enteredAdminFormData);
        vaccination.scheduled_date = null;
        // This field is the vaccine_id and needs to be removed on posts to create new vaccinations.
        vaccination.id = null;
        vaccinationsManager.submitVaccination(vaccination);
    };

    $scope.scheduleVaccination = function (enteredAdminFormData) {
        // When scheduling remove date properties that are setup for
        // administration.
        var enteredAdminFormDataCopy = angular.copy(enteredAdminFormData);
        var vaccination = $scope.transformVaccineToVaccination(enteredAdminFormData);
        vaccination.administration_date = null;
        vaccination.manufacture_date = null;
        vaccination.expiry_date = null;
        vaccination.id = null;
        vaccinationsManager.submitVaccination(vaccination);
    };

    $scope.resetFormDataToDefaults();
}]);

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
    $scope.state.auditLogOpen = false;
    $scope.state.minsDiff;

    $scope.lessThan5Mins = function(minsDiff) {
        return $scope.state.minsDiff < 5 && $scope.state.minsDiff >= 0;
    };

    $scope.isUnadministerable = function () {
        if ($scope.state.minsDiff) {
            return $scope.lessThan5Mins($scope.state.minsDiff);
        } else {
            var administeredTime = angular.copy(new Date($scope.getVaccination().administration_date));
            var currentTime = new Date();
            $scope.state.minsDiff = ((currentTime - administeredTime) / 1000)/60;
            console.log($scope.state.minsDiff);
            return $scope.lessThan5Mins($scope.state.minsDiff);
        }
    };

    $scope.toggleReactionForm = function() {
        $scope.resetFormDataToDefaults();
        $scope.state.editFormOpen = false;
        $scope.state.adverseFormOpen = !$scope.state.adverseFormOpen;
    };

    $scope.toggleEditForm = function(){
        $scope.state.minsDiff = undefined;
        $scope.state.adverseFormOpen = false;
        $scope.resetFormDataToDefaults();
        $scope.state.editFormOpen = !$scope.state.editFormOpen;
    };

    $scope.toggleAuditLog = function() {
        $scope.state.auditLogOpen = !$scope.state.auditLogOpen;
    };

    $scope.resetFormDataToDefaults = function () {
        var vaccination = angular.copy($scope.getVaccination());
        vaccination.administration_date = new Date(vaccination.administration_date);
        vaccination.manufacture_date = new Date(vaccination.manufacture_date);
        vaccination.expiry_date = new Date(vaccination.expiry_date);
        $scope.enteredEditFormData = vaccination;

        if (vaccination.adverse_reaction_observed) {
            $scope.enteredAdverseFormData = vaccination.simpleAdverse_reaction;
            $scope.enteredAdverseFormData.date = new Date(vaccination.simpleAdverse_reaction.date);
        } else {
            $scope.enteredAdverseFormData.date = new Date();
        }
    };

    $scope.addReaction = function (reaction, enteredAdminFormData) {
        vaccinationsManager.submitReaction(reaction, enteredAdminFormData);
    };

    $scope.removeReaction = function (reaction, vaccination) {
        vaccinationsManager.removeReaction(reaction, vaccination);
    };

    // Available for all administered vaccinations.
    // Deletes a vaccination of type unscheduled, vaccinations of type
    // scheduled become unadministered instead of being removed.
    $scope.deleteVaccination = function(vaccination) {
        vaccinationsManager.deleteVaccination(vaccination);
    };

    $scope.updateVaccination = function (vaccination) {
        vaccination.unadminister = false;
        vaccinationsManager.submitVaccination(vaccination);
    };

    $scope.unadministerVaccination = function (vaccination) {
        // Unadministereing a scheduled vaccination is slightly different than unadministering a non scheduled
        // vaccination. In the latter case, unadministering means removing any data
        // Remove all information pertaining to administration.
        vaccination.unadminister = true;
        vaccinationsManager.submitVaccination(vaccination);
    };

    $scope.resetFormDataToDefaults();

}]);

'use strict';

angular.module('vaccinations')
.service('vaccinesManager', ['$http', 'appConstants', function($http, appConstants) {
    var self = this;
    var promise = $http.get(
        appConstants.URL +
        '/openmrs/ws/rest/v2/vaccinationsmodule' +
        '/vaccines/unscheduled')

    .success( function(data) {
        // Add flag to denote this is a custom vaccine.
        // It is required because an extra field is available as 'vaccine name'
        // when entering a custom vaccine.
        for (var i = 0; i < data.length; i++) {
            if (data[i].name === 'Custom') {
                data[i].name = 'Custom vaccine::';
                data[i].custom = true;
                console.log(data[i]);
                break;
            }
        }
        self.vaccines = data;
    });

    var exports = {
        getVaccines: function() {
            return promise;
        }
    };

    return exports;

}]);
'use strict';

// Manages the retrival of vaccinations from the server and the
// removal and entry of vaccinations for a patient.
angular.module('vaccinations')
.service('vaccinationsManager', ['$http', '$filter', '$rootScope', 'appConstants', 'helperFunctions',
    function($http, $filter, $rootScope, appConstants, helperFunctions){
    var self = this;
    self.stagedVaccinations = [];
    self.constants = {};

    // Concept ID constants
    self.constants.mouth = '160240';
    self.constants.nostril = '161253';

    var setVaccinations = function (vaccinations) {
        if (self.vaccinations){
            throw new Error('Vaccinations have already been set.');
        } else {
            self.vaccinations = vaccinations;
        }
    };

    // Get all patient vaccinations
    var promise = $http.get(
            appConstants.URL +
            appConstants.PATH +
            '/enums' +
            '/patient/' +
            appConstants.getPatientId())

        .success(function(data, status, headers, config){
            setVaccinations(data[0]);
        })

        .error(function(data, status, headers, config){
            alert('Error when retrieving patient vaccinations from server.');
        });

    var exports = {

        addVaccination: function(vaccination) {
            var index = helperFunctions.findObjectIndexByAttribute('uuid', vaccination.uuid, self.vaccinations);
            if (index === undefined){
                self.vaccinations.push(vaccination);
            } else {
                throw new Error('Could not add vaccination to array, a vaccination with the id attribute already exists.');
            }
        },

        // vaccinationCopy provides a way to remove template vaccinations
        // from the vaccinations list. It is only required when calling
        // the function with a new vaccination, not when the vaccination
        // exists on the server and needs to be modified.

        submitVaccination: function(vaccination) {
            debugger;
            var that = this;
            $rootScope.$broadcast('waiting');
            // Prevent unintentional sending of reaction details
            // modifications.
            var vaccination = angular.copy(vaccination);
            delete vaccination.reaction_details;

            if (!vaccination.reason){
                vaccination.reason = "";
            }
            if (!vaccination.excuse){
                vaccination.excuse = "";
            }

            //Set the proper body site if oral or intranasal
            if (vaccination.route == self.constants.mouth) {
                vaccination.body_site_administered = 'Mouth';
            }
            if (vaccination.route == self.constants.nostril) {
                vaccination.body_site_administered = 'Nostril';
            }

            // Check whether we are updating an existing vaccination
            // or adding new vaccination.

            if (vaccination.id !== null) {
            // Vaccination exists, modify on server.
                if (vaccination.administration_date !== null) {
                    vaccination.administered = true;
                } else if (vaccination.administration_date === null) {
                    vaccination.administered = false;
                }
                $http.put(
                    appConstants.URL +
                    appConstants.PATH + '/' +
                    vaccination.id +
                    '/patient/' +
                    appConstants.getPatientId(),
                    vaccination)

                .success( function (data) {
                    // Remove the old version and add the new version
                    $rootScope.$broadcast('success');
                    that.removeVaccination(vaccination.id, 'id');
                    that.addVaccination(data);
                })

                .error( function (data) {
                    $rootScope.$broadcast('failure');
                    alert("The vaccination was not saved. Please try again.");
                });
            } else {
                // Vaccination does not exist on server. Post to server.
                // Removed internal fields before sending.
                if (vaccination._staged) {
                    // Since we can't send the internal staged field to the server
                    // remove it and set a local staged flag.
                    delete vaccination._staged;
                    var stagedVaccination = true;
                }

                if (typeof vaccination.custom !== undefined && vaccination.custom) {
                    delete vaccination.custom;
                }

                delete vaccination.numeric_indication;
                // Set administered flag.
                if (vaccination._administering) {
                    vaccination.administered = true;
                } else if (vaccination._scheduling) {
                    vaccination.administered = false;
                }

                delete vaccination._administering;
                delete vaccination._scheduling;

                $http.post(
                    appConstants.URL +
                    appConstants.PATH +
                    '/patient/' +
                    appConstants.getPatientId(),
                    vaccination)

                .success( function (data) {
                    // This catches new, unscheduled vaccinations
                    // that have not been saved to the patients records.
                    $rootScope.$broadcast('success');
                    if (typeof stagedVaccination !== undefined && stagedVaccination === true) {
                        $rootScope.$broadcast('success');
                        that.removeStagedVaccination();
                    // This catches scheduled vaccinations that
                    // have yet to be saved to the patients records.
                    // Since scheduled unadministered vaccs have no id
                    // remove old version using object equality.
                    } else {
                        $rootScope.$broadcast('success');
                        that.removeVaccination(vaccination.uuid, 'uuid');
                    }
                    that.addVaccination(data);
                })
                .error( function (data) {
                    $rootScope.$broadcast('failure');
                    alert("The vaccination was not saved. Please try again.");
                });
            }
        },

        deleteVaccination: function(vaccination) {
            $rootScope.$broadcast('waiting');
            var that = this;
            $http.delete(
                appConstants.URL +
                appConstants.PATH + '/' +
                vaccination.id +
                '/patient/' +
                appConstants.getPatientId())

            .success( function (data) {
                that.removeVaccination(vaccination.uuid, 'uuid');
                // If deleting a scheduled vaccination, add back template to the vaccinations list.
                // If the vaccination is unscheduled just remove it.
                if (vaccination.scheduled === true) {
                    that.addVaccination(data);
                }
                $rootScope.$broadcast('success');
            })

            .error( function () {
                $rootScope.$broadcast('error');
                alert("The vaccination could not be deleted. Please try again.");
            });
        },

        submitReaction: function (reaction, vaccination) {
            // Get the vaccination from the array.
            // Adding reaction details to the vaccination passed
            // into the function will only change the copy.
            $rootScope.$broadcast('waiting');
            var that = this;
            if (vaccination.adverse_reaction) {
                $http.put(
                    appConstants.URL +
                    '/openmrs/ws/rest/v2/vaccinationsmodule/' +
                    'adversereactions/' +
                    reaction.id +
                    'patient/' +
                    appConstants.getPatientId() + '/' +
                    'vaccinations/' + vaccination.id,
                    reaction)

                .success( function (data) {
                    $rootScope.$broadcast('success');
                    that.removeVaccination(vaccination.id);
                    that.addVaccination(data);
                })

                .error( function (data) {
                    $rootScope.$broadcast('failure');
                    alert("An error occured while sending information to server. Try again.");
                });
            } else {
                $http.post(
                    appConstants.URL +
                    '/openmrs/ws/rest/v2/vaccinationsmodule/' +
                    'adversereactions/' +
                    'patient/' +
                    appConstants.getPatientId() + '/' +
                    'vaccinations/' + vaccination.id,
                    reaction)

                .success( function (data) {
                    $rootScope.$broadcast('success');
                    that.removeVaccination(vaccination.id, 'id');
                    that.addVaccination(data);
                })

                .error( function (data) {
                    $rootScope.$broadcast('failure');
                    alert("An error occured while sending information to server. Try again.");
                });
            }
        },

        removeReaction: function (reaction, vaccination) {
            var that = this;
            $rootScope.$broadcast('waiting');
            $http.delete(
                    appConstants.URL +
                    '/openmrs/ws/rest/v2/vaccinationsmodule/' +
                    'adversereactions/' +
                    reaction.id +
                    '/patient/' +
                    appConstants.getPatientId())

            .success( function (data) {
                $rootScope.$broadcast('success');
                that.removeVaccination(vaccination.uuid, 'uuid');
                that.addVaccination(data);
            })

            .error( function (data) {
                $rootScope.$broadcast('failure');
                alert("An error occured while sending information to the server. Try again.");
            });
        },

        getVaccinations: function(){
            return promise;
        },

        getVaccinationById: function(id){
            var vaccination = $filter('filter')(self.vaccinations, function(vaccination, index){
                return vaccination.id === id;
            });
            return vaccination[0];
        },

        removeVaccination: function(key, value){
            var index = helperFunctions.findObjectIndexByAttribute(key, value, self.vaccinations);
            if (index !== undefined){
                self.vaccinations.splice(index, 1);
            } else {
                console.log("The index of the vaccination to be removed could not be found.");
            }
        },

        addStagedVaccination: function(vaccine) {
            // We only want 1 staged vaccination at a time to keep
            // things organized. So clear the array first.
            this.removeStagedVaccination();
            self.stagedVaccinations.push(vaccine);
        },

        getStagedVaccinations: function () {
            return self.stagedVaccinations;
        },

        removeStagedVaccination: function () {
            self.stagedVaccinations.length = 0;
        }
    };

    return exports;
 }]);

'use strict';

angular.module('vaccinations')
.directive('vaccination', [ '$timeout', 'helperFunctions', function($timeout, helperFunctions) {
    return {
        restrict: 'E',
        // The link function provides access to the scope of the vaccination
        // element. Use the vaccinations.administered property to decide on
        // which template to include administered/unadministered.
        // The controller is assigned in the html templates.
        compile: function compile (element, attrs) {
            return {
                post: function postLink(scope, element, attributes) {
                    // Add popover for staged vaccinations.
                    scope.vaccination = {};
                    if (scope.getVaccination()._staged) {
                        scope.vaccination._staged = true;
                    }
                    scope.vaccination.administered = scope.getVaccination().administered;

                    scope.getContentUrl = function(){
                        if (scope.vaccination.hasOwnProperty('_staged')) {
                            return '/app/vaccination/staged/vaccination_staged.template.html';
                        }
                        else if (scope.vaccination.administered){
                            return '/app/vaccination/administered/vaccination_administered.template.html';
                        }
                        else if (!scope.vaccination.administered){
                            return '/app/vaccination/unadministered/vaccination_unadministered.template.html';
                        }
                    };
                }
            };
        },

        template: '<div ng-include="getContentUrl()"></div>',
        scope: {
            getVaccination: '&',
            getRoutes: '&',
            getDosingUnits: '&',
            getBodySites: '&',
            getManufacturers: '&',
            getChangeReasons: '&',
            getBodySiteMapping: '&',
            getAdminStatus: '&'
        }
    };
 }]);

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

'use strict';

angular.module('vaccinations')
.service('helperFunctions', function(){
    return {
        // Format vaccine names for dropdown
        formatVaccineName: function (vaccine) {
            var formattedVaccineName = vaccine.name + ':: ';
            if (vaccine.custom) {
                return 'Custom Vaccine::';
            }
            if (typeof vaccine.dose_number !== 'undefined' && vaccine.dose_number !== null) {
                formattedVaccineName += 'Course Number: ' + vaccine.dose_number + ' ';
            }
            return formattedVaccineName;
        },

        // Find the index of an object with a given property value.
        findObjectIndexByAttribute: function(attribute, attributeValue, array){
            for (var i = 0; i < array.length; i++) {
                if (array[i][attributeValue] === attribute){
                    return i;
                }
            }
            return undefined;
        },

        // Find the index of an object in an array.
        findObjectIndexByEquality: function(obj, array) {
            for (var i = 0; i < array.length; i++) {
                if (angular.equals(obj, array[i])) {
                    return i;
                }
            }
            return undefined;
        }
    };
});
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
.service('appConstants', ["$http", "$location", function ($http, $location) {
    var tempURL;
    if (document.getElementsByTagName("title")[0].innerHTML !== "KMRI") {
        tempURL = 'http://208.77.196.178:64000';
    } else {
        tempURL = '';
    }

    var exports = {
        // Set url for testing
        // URL: 'http://208.77.196.178:64000',
        URL: tempURL,
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

       getAdminStatus: function () {
            return ('True' === $location.search().retroactive || 'true' === $location.search().retroactive);
       }
    };

    return exports;
}]);
'use strict';

angular.module('vaccinations')
.directive('loader', function () {
    var ddo = {
        replace: true,
        templateUrl: '/app/loader/loader.template.html',
        controller: function ($scope, $timeout) {
            $scope.state = {};
            $scope.state.loading = false;
            $scope.state.success = false;

            $scope.$on('waiting', function () {
                $scope.state.loading = true;
            });

            $scope.$on('success', function () {
                $scope.state.loading = false;
                $scope.state.success = true;
                $timeout( function () { $scope.state.success = false; }, 1050);
            });

            $scope.$on('failure', function () {
                $scope.state.loading = false;
                $scope.state.success = false;
            });
        }
    };

    return ddo;
});

'use strict';

angular.module('vaccinations')
.directive('feedback', [ function () {
    var ddo = {
        replace: true,
        templateUrl: '/app/feedback/feedback.template.html',
        scope: {
            // Boolean indicating whether to show or hide.
            warn: '&',
            warning: '@'
        }
    };

    return ddo;
}]);
'use strict';

angular.module('mockData', [])
.value('mockObjects', {
    vaccinations:[
    {
      "_id": "54f88ccdfb16c75e35261900",
      "_vaccine_id": "550b46047fcba390ff335aaa",
      "scheduled": true,
      "name": "Bacillus Calmette-Guerin",
      "indication_name": "Birth to 1 yo",
      "dose": 0.05,
      "dosing_unit": "ml",
      "route": "intra-dermal",
      "administered": true,
      "adverse_reaction_observed": false,
      "administration_date": "2014-10-01",
      "scheduled_date": "2014-09-05",
      "body_site_administered": "left forearm",
      "dose_number": 1,
      "manufacturer": "P. Corp.",
      "lot_number": "1000",
      "manufacture_date": "2014-01-01",
      "expiry_date": "2016-01-01"
    },
    {
      "_vaccine_id": "550b46047fcba390ff335bba",
      "scheduled": true,
      "name": "Bacillus Calmette-Guerin",
      "indication_name": "Older than 1 yo",
      "dose": 0.1,
      "dosing_unit": "ml",
      "route": "intra-dermal",
      "administered": false,
      "adverse_reaction_observed": false,
      "administration_date": "",
      "scheduled_date": "2015-09-05",
      "body_site_administered": "left forearm",
      "dose_number": 2,
    },
    {
      "_id": "54f88ccdfb16c75e35261802",
      "_vaccine_id": "550b46047fcba390ff335cca",
      "scheduled": true,
      "name": "Polio",
      "indication_name": "Birth to 2 weeks",
      "dose": 2,
      "dosing_unit": "drops",
      "route": "PO",
      "administered": true,
      "adverse_reaction_observed": false,
      "administration_date": "2014-09-01",
      "scheduled_date": "2014-09-01",
      "body_site_administered": "Site",
      "dose_number": 1,
      "manufacturer": "P. Corp.",
      "lot_number": "1000",
      "manufacture_date": "2014-01-01",
      "expiry_date": "2016-01-01"
    },
    {
      "_id": "54f88ccd6bba27070e77d3dc",
      "_vaccine_id": "4aslkjlkjaslkfjlkah234ql",
      "scheduled": false,
      "provider_id": "AAAA",
      "name": "Rotavirus",
      "dose": 0.1,
      "dosing_unit": "ml",
      "route": "",
      "administered": true,
      "adverse_reaction_observed": false,
      "administration_date": "2014-10-01",
      "scheduled_date": "2014-10-01",
      "body_site_administered": "",
      "manufacturer": "P. Corp.",
      "lot_number": "1000",
      "manufacture_date": "2014-01-01",
      "expiry_date": "2016-01-01"
    },
    {
      "_vaccine_id": "550b46047fcba390ff335fff",
      "scheduled": true,
      "name": "Measles",
      "indication_name": "9 mo",
      "dose": .5,
      "dosing_unit": "ml",
      "route": "SC",
      "administered": false,
      "adverse_reaction_observed": false,
      "administration_date": "",
      "scheduled_date": "2015-03-01",
      "body_site_administered": "right outer thigh",
      "dose_number": 1,
    },
    {
      "_id": "54f88ccddce0415f38397d33",
      "_vaccine_id": "999",
      "scheduled": false,
      "provider_id": "BBBB",
      "name": "Hepatitis B",
      "dose": 2,
      "dosing_unit": "ml",
      "route": "",
      "administered": true,
      "adverse_reaction_observed": false,
      "administration_date": "2014-10-01",
      "scheduled_date": "2014-10-01",
      "body_site_administered": "",
      "lot_number": "1000",
      "manufacture_date": "2014-01-01",
      "expiry_date": "2016-01-01"
    },
    {
      "_id": "54f88ccddce0415f38397d34",
      "_vaccine_id": 60000,
      "scheduled": false,
      "name": "Hepatitis B",
      "dose": 2,
      "dosing_unit": "ml",
      "route": "intra dermal",
      "administered": false,
      "adverse_reaction_observed": false,
      "administration_date": "",
      "scheduled_date": "2014-11-01",
      "body_site_administered": "left deltoid",
    },
    {
      "_id": "54f88ccda57c4a6954ecfe19",
      "_vaccine_id": "550b46047fcba390ff33ddda",
      "scheduled": true,
      "provider_id": "BBBB",
      "name": "Polio",
      "indication_name": "6 weeks",
      "dose": 2,
      "dosing_unit": "drops",
      "route": "PO",
      "administered": true,
      "adverse_reaction_observed": false,
      "administration_date": "2014-10-01",
      "scheduled_date": "2014-10-01",
      "body_site_administered": "left thigh",
      "dose_number": 2,
      "manufacturer": "P. Corp.",
      "lot_number": "1000",
      "manufacture_date": "2014-01-01",
      "expiry_date": "2016-01-01"
    },
    {
      "_id": "54f88ccd2b5b5bbe9ad07c59",
      "_vaccine_id": 80000,
      "scheduled": false,
      "name": "Rotavirus",
      "dose": 0.05,
      "dosing_unit": "ml",
      "route": "intra dermal",
      "administered": false,
      "adverse_reaction_observed": false,
      "administration_date": "",
      "scheduled_date": "2014-11-01",
    },
    {
      "_id": "54f88ccdefe294237482eb5f",
      "_vaccine_id": "550b4604d6f88cbkkk3587ba",
      "scheduled": false,
      "provider_id": "BBBB",
      "name": "DPT",
      "dose": 0.1,
      "dosing_unit": "ml",
      "route": "intra dermal",
      "administered": true,
      "adverse_reaction_observed": true,
      "reaction_details": {
          "_id": "458d82kd",
          "_vaccination_id":"54f88ccdefe294237482eb5f",
          "date": "2014-10-01",
          "adverse_event_description": "Something bad, but not too bad.",
          "grade": "160754",
      },
      "administration_date": "2014-10-01",
      "scheduled_date": "2014-10-01",
      "body_site_administered": "outer thigh",
      "manufacturer": "P. Corp.",
      "lot_number": "1000",
      "manufacture_date": "2014-01-01",
      "expiry_date": "2016-01-01"
    }
  ],

  non_scheduled_vaccines: [

      {
        "_vaccine_id": "550b4604d6f88cbkkk3587ba",
        "name": "Rabies",
        "scheduled": false
      },
      {
        "_vaccine_id": "550b4604d6f88cbkkk3587ba",
        "name": "DPT",
        "scheduled": false
      },
      {
        "_vaccine_id": "550b460499dc11cccf4420bb",
        "name": "Hepatitis A",
        "scheduled": false
      },
      {
        "_vaccine_id": "550b4604a8298qqqb151205d",
        "name": "Hepatitis B",
        "scheduled": false
      },
      {
        "_vaccine_id": "4aslkjlkjaslkfjlkah234ql",
        "name": "Rotavirus",
        "scheduled": false
      },
      {
        "_vaccine_id": "550b4604a829wwweb1512ccc",
        "name": "Vitamin A",
        "dose": 100000,
        "dosing_unit": "IU",
        "route": "PO",
        "indication_name": "6 mo",
        "scheduled": true
      },
      {
        "_vaccine_id": "550b4604a829wwwxxx512ccc",
        "name": "Rabies",
        "scheduled": false
      },
      {
        "_vaccine_id": "5zzz4604a829wwwxxx512ccc",
        "name": "Cholera",
        "scheduled": false
      },
      {
        "_vaccine_id": "5zzz4gg4a829wwwxxx512ccc",
        "name": "Mumps",
        "scheduled": false
      },
      {
        "_vaccine_id": "xzzz4gg4a829wwwxxx512ccc",
        "name": "Rubella",
        "scheduled": false
      },
      {
        "_vaccine_id": "xzzz4gg4ls29wwwxxx512ccc",
        "name": "Tetanus toxoid",
        "scheduled": false
      },
      {
        "_vaccine_id": "CUSTOM",
        "custom": true,
        "name": "",
        "scheduled": false,
      }

    ],

    scheduled_vaccines: [
      {
        "_vaccine_id": "550b46047fcba390ff335aaa",
        "name": "Bacillus Calmette-Guerin",
        "dose": .05,
        "dosing_unit": "ml",
        "dose_number": 1,
        "route": "Intra-dermal left forearm",
        "indication_name": "Birth to 1 yo",
        "scheduled": true
      },
      {
        "_vaccine_id": "550b46047fcba390ff335bba",
        "name": "Bacillus Calmette-Guerin",
        "dose": .1,
        "dosing_unit": "ml",
        "dose_number": 2,
        "route": "Intra-dermal left forearm",
        "indication_name": "Older than 1 yo",
        "scheduled": true
      },
      {
        "_vaccine_id": "550b46047fcba390ff335cca",
        "name": "Polio",
        "dose": 2,
        "dosing_unit": "drops",
        "dose_number": 1,
        "route": "PO",
        "indication_name": "Birth to 2 weeks",
        "scheduled": true
      },
      {
        "_vaccine_id": "550b46047fcba390ff33ddda",
        "name": "Polio",
        "dose": 2,
        "dosing_unit": "drops",
        "dose_number": 2,
        "route": "PO",
        "indication_name": "6 weeks",
        "scheduled": true
      },
      {
        "_vaccine_id": "550b46047fcba390ff33xxxa",
        "name": "Polio",
        "dose": 2,
        "dosing_unit": "drops",
        "dose_number": 3,
        "route": "PO",
        "indication_name": "10 weeks",
        "scheduled": true
      },
      {
        "_vaccine_id": "550b46047fcba390ff335ccc",
        "name": "Polio",
        "dose": 2,
        "dosing_unit": "drops",
        "dose_number": 4,
        "route": "PO",
        "indication_name": "14 weeks",
        "scheduled": true
      },
      {
        "_vaccine_id": "550b46047fcba390ff335fff",
        "name": "Measles",
        "dose": .5,
        "dosing_unit": "SC",
        "dose_number": 1,
        "route": "right upper arm",
        "indication_name": "9 mo",
        "scheduled": true
      }
    ]
})
// 'use strict';

// var mockBackend = angular.module('mockBackend', ['vaccinations', 'ngMockE2E', 'mockData']);
// mockBackend.run(function($httpBackend, $timeout, mockObjects, helperFunctions, appConstants){
//     // Get the mock json data from the mockData module.
//     var vaccinations = mockObjects.vaccinations;
//     var loaderDelay = 10000;
//     appConstants.setPatiendId(1);

//     $httpBackend.whenGET(/^\/?vaccinations\/patients\/1/).respond(mockObjects);
//     $httpBackend.whenGET(/^\/?vaccines/).respond(mockObjects);
//     $httpBackend.whenGET(/^\/?vaccines\/non_scheduled$/).respond(mockObjects);

//     $httpBackend.whenPOST(/^\/vaccinations\/patients\/1/).respond(function(method, url, data){
//         var vaccination = angular.fromJson(data).vaccination;
//         // Add a vaccination id field and remove the
//         // staged marker.
//         var time = new Date().getTime() + loaderDelay;
//         while (new Date() < time) {}

//         vaccination._id = "NEWLYADDED" + Math.floor(Math.random() * 10000000);
//         delete vaccination._staged;
//         delete vaccination._administering;
//         delete vaccination._scheduling;
//         if (vaccination.administration_date) {
//             vaccination.administered = true;
//         } else {
//             vaccination.administered = false;
//         }
//         vaccinations.push(vaccination);
//         return [200, {vaccination:vaccination}, {}];

//     });

//     $httpBackend.whenPUT(/^\/vaccinations\/[a-zA-Z0-9_]+\/patients\/[a-zA-Z0-9]+$/)
//         .respond( function (method, url, data) {
//             var time = new Date().getTime() + loaderDelay;
//             while (new Date() < time) {}

//             var vaccination = angular.fromJson(data).vaccination;
//             var index = helperFunctions.findObjectIndexByAttribute('_id', vaccination._id, vaccinations);
//             if (vaccination.administration_date) {
//                 vaccination.administered = true;
//             }

//             // Get old reaction details since they aren't sent
//             var reaction_details = vaccinations[index].reaction_details;
//             vaccination.reaction_details = reaction_details;
//             vaccinations.splice(index, 1);
//             vaccinations.push(vaccination);
//             return [200, {vaccination: vaccination}, {}];
//     });

//     $httpBackend.whenDELETE(/^\/vaccinations\/[a-zA-Z0-9]+\/patients\/[a-zA-Z0-9]+$/)
//         .respond( function (method, url, data) {
//             var time = new Date().getTime() + loaderDelay;
//             while (new Date() < time) {}

//             var vaccinationId = /[a-zA-Z0-9]+(?=\/patients\/)/.exec(url)[0];
//             var index = helperFunctions.findObjectIndexByAttribute('_id', vaccinationId, vaccinations);
//             vaccinations.splice(index, 1);

//             return [200, {}, {}];
//     });

//     $httpBackend.whenPOST(/^\/vaccinations\/[a-zA-Z0-9]+\/patients\/[a-zA-Z0-9]+\/adverse_reactions$/)
//         .respond(function (method, url, data) {
//             var time = new Date().getTime() + loaderDelay;
//             while (new Date() < time) {}

//             var reaction = angular.fromJson(data).reaction;

//             var vaccinationId = /[a-zA-Z0-9]+(?=\/patients\/)/.exec(url)[0];
//             var index = helperFunctions.findObjectIndexByAttribute('_id', vaccinationId, vaccinations);

//             var vaccination = vaccinations[index];
//             vaccination.reaction_details = reaction;
//             vaccination.reaction_details._id = "NEWLYADDED" + Math.floor(Math.random() * 10000000);
//             vaccination.adverse_reaction_observed = true;
//             vaccination.reaction_details._vaccination_id = vaccination._id;

//             return [200, {vaccination: vaccination}, {}];
//     });

//     $httpBackend.whenPUT(/^\/vaccinations\/[a-zA-Z0-9]+\/patients\/[a-zA-Z0-9]+\/adverse_reactions\/[0-9a-zA-Z]+$/)
//         .respond(function (method, url, data) {
//             var time = new Date().getTime() + loaderDelay;
//             while (new Date() < time) {}

//             var reaction = angular.fromJson(data).reaction;

//             var vaccinationId = /[a-zA-Z0-9]+(?=\/patients\/)/.exec(url)[0];
//             var index = helperFunctions.findObjectIndexByAttribute('_id', vaccinationId, vaccinations);
//             var vaccination = vaccinations[index];
//             vaccination.reaction_details = reaction;
//             vaccination.adverse_reaction_observed = true;

//             return [200, {vaccination: vaccination}, {}];
//     });

//     $httpBackend.whenDELETE(/^\/vaccinations\/[a-zA-Z0-9]+\/patients\/[a-zA-Z0-9]+\/adverse_reactions\/[0-9a-zA-Z]+$/)
//         .respond( function (method, url, data) {

//             var time = new Date().getTime() + loaderDelay;
//             while (new Date() < time) {}

//             var vaccinationId = /[a-zA-Z0-9]+(?=\/patients\/)/.exec(url)[0];
//             var index = helperFunctions.findObjectIndexByAttribute('_id', vaccinationId, vaccinations);
//             var vaccination = vaccinations[index];
//             vaccination.adverse_reaction_observed = false;
//             delete vaccination.reaction_details;
//             return [201, {vaccination: vaccination}, {}];
//         })

//     // Do not serve anything from the mock server on these routes.
//     $httpBackend.whenGET(/\/?mock_data\/.+/).passThrough();
//     $httpBackend.whenGET(/\/?app\/.+/).passThrough();
// });
// // Manually bootstrap the backend.
// angular.element(document).ready(function () {
//     angular.bootstrap(document, ['mockBackend']);
// });
