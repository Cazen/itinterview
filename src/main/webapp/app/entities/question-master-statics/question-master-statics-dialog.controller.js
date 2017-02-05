(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('QuestionMasterStaticsDialogController', QuestionMasterStaticsDialogController);

    QuestionMasterStaticsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'QuestionMasterStatics', 'QuestionMaster'];

    function QuestionMasterStaticsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, QuestionMasterStatics, QuestionMaster) {
        var vm = this;

        vm.questionMasterStatics = entity;
        vm.clear = clear;
        vm.save = save;
        vm.questionmasters = QuestionMaster.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.questionMasterStatics.id !== null) {
                QuestionMasterStatics.update(vm.questionMasterStatics, onSaveSuccess, onSaveError);
            } else {
                QuestionMasterStatics.save(vm.questionMasterStatics, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('itinterviewApp:questionMasterStaticsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
