'use strict';

// Manages the retrival of vaccinations from the server and the
// removal and entry of vaccinations for a patient.
angular.module('vaccinations')
.service('vaccinationsManager', ['$http', '$filter', '$rootScope', 'appConstants', 'helperFunctions',
    function($http, $filter, $rootScope, appConstants, helperFunctions){
    var self = this;
    self.stagedVaccinations = [];

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
            '/patient/' +
            appConstants.getPatientId(window.location.href))

        .success(function(data, status, headers, config){
            setVaccinations(data);
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

        submitVaccination: function(vaccination, vaccsOrigCopy) {
            var that = this;
            $rootScope.$broadcast('waiting');
            // Prevent unintentional sending of reaction details
            // modifications.
            var vaccination = angular.copy(vaccination);
            try {
                delete vaccination.reaction_details;
            } catch(err) {
                console.log(err);
            }
            // Check whether we are updating an existing vaccination
            // or adding new vaccination.
            if (vaccination.id !== null) {
                // Vaccination exists, modify on server.
                $http.put(
                    appConstants.URL +
                    appConstants.PATH + '/' +
                    vaccination.id +
                    '/patient/' +
                    appConstants.getPatientId(window.location.href),
                    vaccination)

                .success( function (data) {
                    // Remove the old version and add the new version
                    $rootScope.$broadcast('success');
                    that.removeVaccination(vaccination.id);
                    that.addVaccination(data); })

                .error( function (data) {
                    $rootScope.$broadcast('failure');
                    alert("The vaccination was not saved. Please try again.");
                });
            } else {
                // Vaccination does not exist on server. Post to server.
                // Removed internal staged field before sending.
                delete vaccination.staged;
                // Set administered flag.
                vaccination.administered = true;

                $http.post(
                    appConstants.URL +
                    appConstants.PATH +
                    '/patient/' +
                    appConstants.getPatientId(window.location.href),
                    vaccination)

                .success( function (data) {
                    // This catches new, unscheduled vaccinations
                    // that have not been saved to the patients records.
                    $rootScope.$broadcast('success');
                    if (vaccination._staged) {
                        that.removeStagedVaccination();
                    // This catches scheduled vaccinations that
                    // have yet to be saved to the patients records.
                    // Since scheduled unadministered vaccs have no id
                    // remove old version using object equality.
                    } else {
                        var idx = helperFunctions.findObjectIndexByEquality(vaccsOrigCopy, self.vaccinations);
                        self.vaccinations.splice(idx, 1);
                    }
                    that.addVaccination(data); })
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
                appConstants.getPatientId(window.location.href))

            .success( function (data) {
                that.removeVaccination('uuid', vaccination.uuid);
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
            debugger;
            // Get the vaccination from the array.
            // Adding reaction details to the vaccination passed
            // into the function will only change the copy.
            $rootScope.$broadcast('waiting');
            var that = this;
            if (vaccination.adverse_reaction) {
                $http.put(
                    appConstants.URL +
                    appConstants.PATH +
                    '/openmrs/ws/rest/v2/vaccinationsmodule/' +
                    '/adverseReactions/' +
                    reaction.id +
                    'patient/' +
                    appConstants.getPatientId(window.location.href) + '/' +
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
                    appConstants.PATH +
                    '/openmrs/ws/rest/v2/vaccinationsmodule/' +
                    '/adverseReactions/' +
                    'patient/' +
                    appConstants.getPatientId(window.location.href) + '/' +
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
            }
        },

        removeReaction: function (reaction) {
            var that = this;
            $rootScope.$broadcast('waiting');
            $http.delete(
                    appConstants.URL +
                    appConstants.PATH +
                    '/adverseReactions/' +
                    reaction.id +
                    'patient/' +
                    appConstants.getPatientId(window.location.href))

            .success( function (data) {
                $rootScope.$broadcast('success');
                that.removeVaccination(data.id);
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
                console.log(self.vaccinations);
                self.vaccinations.splice(index, 1);
                console.log(self.vaccinations);
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
