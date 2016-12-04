(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpRightAnswerDialogController', UpRightAnswerDialogController);

    UpRightAnswerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UpRightAnswer', 'UpQuestionMaster'];

    function UpRightAnswerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UpRightAnswer, UpQuestionMaster) {
        var vm = this;

        vm.upRightAnswer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.upquestionmasters = UpQuestionMaster.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.upRightAnswer.id !== null) {
                UpRightAnswer.update(vm.upRightAnswer, onSaveSuccess, onSaveError);
            } else {
                UpRightAnswer.save(vm.upRightAnswer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('itinterviewApp:upRightAnswerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
