(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('QuestionMasterDetailController', QuestionMasterDetailController);

    QuestionMasterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'QuestionMaster', 'RightAnswer', 'WrongAnswer', 'User', 'CommonCode'];

    function QuestionMasterDetailController($scope, $rootScope, $stateParams, previousState, entity, QuestionMaster, RightAnswer, WrongAnswer, User, CommonCode) {
        var vm = this;

        vm.questionMaster = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('itinterviewApp:questionMasterUpdate', function(event, result) {
            vm.questionMaster = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
