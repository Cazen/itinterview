(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpWrongAnswerDeleteController',UpWrongAnswerDeleteController);

    UpWrongAnswerDeleteController.$inject = ['$uibModalInstance', 'entity', 'UpWrongAnswer'];

    function UpWrongAnswerDeleteController($uibModalInstance, entity, UpWrongAnswer) {
        var vm = this;

        vm.upWrongAnswer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UpWrongAnswer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
