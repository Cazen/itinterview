(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('RightAnswerDetailController', RightAnswerDetailController);

    RightAnswerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RightAnswer', 'CommonCode', 'QuestionMaster'];

    function RightAnswerDetailController($scope, $rootScope, $stateParams, previousState, entity, RightAnswer, CommonCode, QuestionMaster) {
        var vm = this;

        vm.rightAnswer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('itinterviewApp:rightAnswerUpdate', function(event, result) {
            vm.rightAnswer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
