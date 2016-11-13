(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpWrongAnswerDetailController', UpWrongAnswerDetailController);

    UpWrongAnswerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UpWrongAnswer', 'CommonCode', 'UpQuestionMaster'];

    function UpWrongAnswerDetailController($scope, $rootScope, $stateParams, previousState, entity, UpWrongAnswer, CommonCode, UpQuestionMaster) {
        var vm = this;

        vm.upWrongAnswer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('itinterviewApp:upWrongAnswerUpdate', function(event, result) {
            vm.upWrongAnswer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
