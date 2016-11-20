(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('WrongAnswerDetailController', WrongAnswerDetailController);

    WrongAnswerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WrongAnswer', 'CommonCode', 'QuestionMaster'];

    function WrongAnswerDetailController($scope, $rootScope, $stateParams, previousState, entity, WrongAnswer, CommonCode, QuestionMaster) {
        var vm = this;

        vm.wrongAnswer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('itinterviewApp:wrongAnswerUpdate', function(event, result) {
            vm.wrongAnswer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
