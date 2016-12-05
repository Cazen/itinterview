(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpRightAnswerDetailController', UpRightAnswerDetailController);

    UpRightAnswerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UpRightAnswer', 'UpQuestionMaster'];

    function UpRightAnswerDetailController($scope, $rootScope, $stateParams, previousState, entity, UpRightAnswer, UpQuestionMaster) {
        var vm = this;

        vm.upRightAnswer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('itinterviewApp:upRightAnswerUpdate', function(event, result) {
            vm.upRightAnswer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
