(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpRightAnswerDeleteController',UpRightAnswerDeleteController);

    UpRightAnswerDeleteController.$inject = ['$uibModalInstance', 'entity', 'UpRightAnswer'];

    function UpRightAnswerDeleteController($uibModalInstance, entity, UpRightAnswer) {
        var vm = this;

        vm.upRightAnswer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UpRightAnswer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
