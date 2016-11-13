(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpWrongAnswerDialogController', UpWrongAnswerDialogController);

    UpWrongAnswerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UpWrongAnswer', 'CommonCode', 'UpQuestionMaster'];

    function UpWrongAnswerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UpWrongAnswer, CommonCode, UpQuestionMaster) {
        var vm = this;

        vm.upWrongAnswer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.commoncodes = CommonCode.query();
        vm.upquestionmasters = UpQuestionMaster.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.upWrongAnswer.id !== null) {
                UpWrongAnswer.update(vm.upWrongAnswer, onSaveSuccess, onSaveError);
            } else {
                UpWrongAnswer.save(vm.upWrongAnswer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('itinterviewApp:upWrongAnswerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
