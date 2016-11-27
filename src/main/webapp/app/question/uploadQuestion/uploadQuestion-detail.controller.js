(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpQuestionDetailController', UpQuestionDetailController);

    UpQuestionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UpQuestion', 'UpRightAnswer', 'UpWrongAnswer', 'User', 'CommonCode'];

    function UpQuestionDetailController($scope, $rootScope, $stateParams, previousState, entity, UpQuestion, UpRightAnswer, UpWrongAnswer, User, CommonCode) {
        var vm = this;

        vm.uploadQuestion = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('itinterviewApp:uploadQuestionUpdate', function(event, result) {
            vm.uploadQuestion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
