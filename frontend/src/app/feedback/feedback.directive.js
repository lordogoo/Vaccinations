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