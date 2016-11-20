(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('QuestionVoteDetailController', QuestionVoteDetailController);

    QuestionVoteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'QuestionVote', 'QuestionMaster', 'User', 'CommonCode'];

    function QuestionVoteDetailController($scope, $rootScope, $stateParams, previousState, entity, QuestionVote, QuestionMaster, User, CommonCode) {
        var vm = this;

        vm.questionVote = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('itinterviewApp:questionVoteUpdate', function(event, result) {
            vm.questionVote = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
