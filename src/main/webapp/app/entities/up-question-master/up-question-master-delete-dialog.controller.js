(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpQuestionMasterDeleteController',UpQuestionMasterDeleteController);

    UpQuestionMasterDeleteController.$inject = ['$uibModalInstance', 'entity', 'UpQuestionMaster'];

    function UpQuestionMasterDeleteController($uibModalInstance, entity, UpQuestionMaster) {
        var vm = this;

        vm.upQuestionMaster = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UpQuestionMaster.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
