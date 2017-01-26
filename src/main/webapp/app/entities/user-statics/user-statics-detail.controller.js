(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UserStaticsDetailController', UserStaticsDetailController);

    UserStaticsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserStatics', 'User'];

    function UserStaticsDetailController($scope, $rootScope, $stateParams, previousState, entity, UserStatics, User) {
        var vm = this;

        vm.userStatics = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('itinterviewApp:userStaticsUpdate', function(event, result) {
            vm.userStatics = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
