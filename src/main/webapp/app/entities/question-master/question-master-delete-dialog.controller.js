(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('QuestionMasterDeleteController',QuestionMasterDeleteController);

    QuestionMasterDeleteController.$inject = ['$uibModalInstance', 'entity', 'QuestionMaster'];

    function QuestionMasterDeleteController($uibModalInstance, entity, QuestionMaster) {
        var vm = this;

        vm.questionMaster = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            QuestionMaster.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
