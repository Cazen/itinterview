(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('QuestionMasterDialogController', QuestionMasterDialogController);

    QuestionMasterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'QuestionMaster', 'RightAnswer', 'WrongAnswer', 'User', 'CommonCode'];

    function QuestionMasterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, QuestionMaster, RightAnswer, WrongAnswer, User, CommonCode) {
        var vm = this;

        vm.questionMaster = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.rightanswers = RightAnswer.query();
        vm.wronganswers = WrongAnswer.query();
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
            if (vm.questionMaster.id !== null) {
                QuestionMaster.update(vm.questionMaster, onSaveSuccess, onSaveError);
            } else {
                QuestionMaster.save(vm.questionMaster, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('itinterviewApp:questionMasterUpdate', result);
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
