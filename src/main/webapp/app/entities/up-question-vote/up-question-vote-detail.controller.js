(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpQuestionVoteDetailController', UpQuestionVoteDetailController);

    UpQuestionVoteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UpQuestionVote', 'UpQuestionMaster', 'User', 'CommonCode'];

    function UpQuestionVoteDetailController($scope, $rootScope, $stateParams, previousState, entity, UpQuestionVote, UpQuestionMaster, User, CommonCode) {
        var vm = this;

        vm.upQuestionVote = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('itinterviewApp:upQuestionVoteUpdate', function(event, result) {
            vm.upQuestionVote = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
