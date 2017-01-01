(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('WrongAnswerDeleteController',WrongAnswerDeleteController);

    WrongAnswerDeleteController.$inject = ['$uibModalInstance', 'entity', 'WrongAnswer'];

    function WrongAnswerDeleteController($uibModalInstance, entity, WrongAnswer) {
        var vm = this;

        vm.wrongAnswer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WrongAnswer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
