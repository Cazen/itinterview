(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpQuestionMasterDialogController', UpQuestionMasterDialogController);

    UpQuestionMasterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UpQuestionMaster', 'UpRightAnswer', 'UpWrongAnswer'];

    function UpQuestionMasterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UpQuestionMaster, UpRightAnswer, UpWrongAnswer) {
        var vm = this;

        vm.upQuestionMaster = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.uprightanswers = UpRightAnswer.query();
        vm.upwronganswers = UpWrongAnswer.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.upQuestionMaster.id !== null) {
                UpQuestionMaster.update(vm.upQuestionMaster, onSaveSuccess, onSaveError);
            } else {
                UpQuestionMaster.save(vm.upQuestionMaster, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('itinterviewApp:upQuestionMasterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.cTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
