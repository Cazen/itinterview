(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UserStaticsDialogController', UserStaticsDialogController);

    UserStaticsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserStatics', 'User', 'CommonCode'];

    function UserStaticsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserStatics, User, CommonCode) {
        var vm = this;

        vm.userStatics = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.commoncodes = CommonCode.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userStatics.id !== null) {
                UserStatics.update(vm.userStatics, onSaveSuccess, onSaveError);
            } else {
                UserStatics.save(vm.userStatics, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('itinterviewApp:userStaticsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
