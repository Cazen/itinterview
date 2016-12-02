(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UploadQuestionDialogController', UploadQuestionDialogController);

    UploadQuestionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'question', 'UploadQuestion', 'UpRightAnswer', 'UpWrongAnswer'];

    function UploadQuestionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, question, UploadQuestion, UpRightAnswer, UpWrongAnswer) {
        var vm = this;

        vm.uploadQuestion = question;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
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
            if (vm.uploadQuestion.id !== null) {
                UploadQuestion.update(vm.uploadQuestion, onSaveSuccess, onSaveError);
            } else {
                vm.uploadQuestion.uprightanswers = vm.uprightanswers;
                UploadQuestion.save(vm.uploadQuestion, onSaveSuccess, onSaveError);

            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('itinterviewApp:uploadQuestionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

    }
})();
