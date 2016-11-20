(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('CommonCodeDeleteController',CommonCodeDeleteController);

    CommonCodeDeleteController.$inject = ['$uibModalInstance', 'entity', 'CommonCode'];

    function CommonCodeDeleteController($uibModalInstance, entity, CommonCode) {
        var vm = this;

        vm.commonCode = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CommonCode.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
