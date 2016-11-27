(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpQuestionDeleteController',UpQuestionDeleteController);

    UpQuestionDeleteController.$inject = ['$uibModalInstance', 'entity', 'UpQuestion'];

    function UpQuestionDeleteController($uibModalInstance, entity, UpQuestion) {
        var vm = this;

        vm.uploadQuestion = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UpQuestion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
