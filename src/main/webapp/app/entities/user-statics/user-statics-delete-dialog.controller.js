(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UserStaticsDeleteController',UserStaticsDeleteController);

    UserStaticsDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserStatics'];

    function UserStaticsDeleteController($uibModalInstance, entity, UserStatics) {
        var vm = this;

        vm.userStatics = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserStatics.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
