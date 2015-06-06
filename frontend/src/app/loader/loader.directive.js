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
                $timeout( function () { $scope.state.success = false; }, 1000);
            });

            $scope.$on('failure', function () {
                $scope.state.loading = false;
                $scope.state.success = false;
            });
        }
    };

    return ddo;
});
