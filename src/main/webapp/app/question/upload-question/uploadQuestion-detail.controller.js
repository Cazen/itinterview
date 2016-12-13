(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UploadQuestionDetailController', UploadQuestionDetailController);

    UploadQuestionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'question', 'UploadQuestion', 'UpRightAnswer', 'UpWrongAnswer', 'User', 'CommonCode'];

    function UploadQuestionDetailController($scope, $rootScope, $stateParams, previousState, question, UploadQuestion, UpRightAnswer, UpWrongAnswer, User, CommonCode) {
        var vm = this;

        vm.uploadQuestion = question;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('itinterviewApp:uploadQuestionUpdate', function(event, result) {
            vm.uploadQuestion = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
