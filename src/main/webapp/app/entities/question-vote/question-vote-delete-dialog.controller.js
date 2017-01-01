(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('QuestionVoteDeleteController',QuestionVoteDeleteController);

    QuestionVoteDeleteController.$inject = ['$uibModalInstance', 'entity', 'QuestionVote'];

    function QuestionVoteDeleteController($uibModalInstance, entity, QuestionVote) {
        var vm = this;

        vm.questionVote = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            QuestionVote.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
