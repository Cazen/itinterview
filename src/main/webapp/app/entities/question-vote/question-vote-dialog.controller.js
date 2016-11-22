(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('QuestionVoteDialogController', QuestionVoteDialogController);

    QuestionVoteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'QuestionVote', 'QuestionMaster', 'User', 'CommonCode'];

    function QuestionVoteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, QuestionVote, QuestionMaster, User, CommonCode) {
        var vm = this;

        vm.questionVote = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.questionmasters = QuestionMaster.query();
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
            if (vm.questionVote.id !== null) {
                QuestionVote.update(vm.questionVote, onSaveSuccess, onSaveError);
            } else {
                QuestionVote.save(vm.questionVote, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('itinterviewApp:questionVoteUpdate', result);
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
