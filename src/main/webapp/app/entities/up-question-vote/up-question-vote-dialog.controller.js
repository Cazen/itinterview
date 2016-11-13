(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpQuestionVoteDialogController', UpQuestionVoteDialogController);

    UpQuestionVoteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UpQuestionVote', 'UpQuestionMaster', 'User', 'CommonCode'];

    function UpQuestionVoteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UpQuestionVote, UpQuestionMaster, User, CommonCode) {
        var vm = this;

        vm.upQuestionVote = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.upquestionmasters = UpQuestionMaster.query();
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
            if (vm.upQuestionVote.id !== null) {
                UpQuestionVote.update(vm.upQuestionVote, onSaveSuccess, onSaveError);
            } else {
                UpQuestionVote.save(vm.upQuestionVote, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('itinterviewApp:upQuestionVoteUpdate', result);
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
