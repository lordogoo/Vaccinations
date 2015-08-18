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