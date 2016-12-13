(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpQuestionVoteDeleteController',UpQuestionVoteDeleteController);

    UpQuestionVoteDeleteController.$inject = ['$uibModalInstance', 'entity', 'UpQuestionVote'];

    function UpQuestionVoteDeleteController($uibModalInstance, entity, UpQuestionVote) {
        var vm = this;

        vm.upQuestionVote = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UpQuestionVote.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
