(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpQuestionMasterDetailController', UpQuestionMasterDetailController);

    UpQuestionMasterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UpQuestionMaster', 'UpRightAnswer', 'UpWrongAnswer'];

    function UpQuestionMasterDetailController($scope, $rootScope, $stateParams, previousState, entity, UpQuestionMaster, UpRightAnswer, UpWrongAnswer) {
        var vm = this;

        vm.upQuestionMaster = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('itinterviewApp:upQuestionMasterUpdate', function(event, result) {
            vm.upQuestionMaster = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
