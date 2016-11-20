(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('CommonCodeDetailController', CommonCodeDetailController);

    CommonCodeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CommonCode'];

    function CommonCodeDetailController($scope, $rootScope, $stateParams, previousState, entity, CommonCode) {
        var vm = this;

        vm.commonCode = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('itinterviewApp:commonCodeUpdate', function(event, result) {
            vm.commonCode = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
