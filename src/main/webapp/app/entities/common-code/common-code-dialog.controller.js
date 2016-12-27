(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('CommonCodeDialogController', CommonCodeDialogController);

    CommonCodeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CommonCode'];

    function CommonCodeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CommonCode) {
        var vm = this;

        vm.commonCode = entity;
        vm.clear = clear;
        vm.save = save;
        vm.commoncodes = CommonCode.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.commonCode.id !== null) {
                CommonCode.update(vm.commonCode, onSaveSuccess, onSaveError);
            } else {
                CommonCode.save(vm.commonCode, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('itinterviewApp:commonCodeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
