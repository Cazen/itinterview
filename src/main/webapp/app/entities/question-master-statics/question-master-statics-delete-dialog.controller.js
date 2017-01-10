(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('QuestionMasterStaticsDeleteController',QuestionMasterStaticsDeleteController);

    QuestionMasterStaticsDeleteController.$inject = ['$uibModalInstance', 'entity', 'QuestionMasterStatics'];

    function QuestionMasterStaticsDeleteController($uibModalInstance, entity, QuestionMasterStatics) {
        var vm = this;

        vm.questionMasterStatics = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            QuestionMasterStatics.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
