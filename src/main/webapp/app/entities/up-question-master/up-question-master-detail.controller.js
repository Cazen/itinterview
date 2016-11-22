(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpQuestionMasterDetailController', UpQuestionMasterDetailController);

    UpQuestionMasterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UpQuestionMaster', 'UpRightAnswer', 'UpWrongAnswer', 'User', 'CommonCode'];

    function UpQuestionMasterDetailController($scope, $rootScope, $stateParams, previousState, entity, UpQuestionMaster, UpRightAnswer, UpWrongAnswer, User, CommonCode) {
        var vm = this;

        vm.upQuestionMaster = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('itinterviewApp:upQuestionMasterUpdate', function(event, result) {
            vm.upQuestionMaster = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
