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
                formattedVaccineName += 'Dose Number: ' + vaccine.dose_number + ' ';
            }
            if (typeof vaccine.dose !== 'undefined' && vaccine.dose !== null) {
                formattedVaccineName += 'Dose: ' + vaccine.dose + ' ';
            }
            if (typeof vaccine.dosing_unit !== 'undefined' && vaccine.dosing_unit !== null) {
                formattedVaccineName += 'Unit: ' + vaccine.dosing_unit + ' ';
            }
            if (typeof vaccine.route !== 'undefined' && vaccine.route !== null) {
                formattedVaccineName += 'Route: ' + vaccine.route + ' ';
            }
            return formattedVaccineName;
        },

        // Find the index of an object with a given id.
        findObjectIndexByAttribute: function(attribute, attributeValue, array){
            for (var i = 0; i < array.length; i++) {
                if (array[i][attribute] === attributeValue){
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