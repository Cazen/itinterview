(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('WrongAnswerDialogController', WrongAnswerDialogController);

    WrongAnswerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WrongAnswer', 'CommonCode', 'QuestionMaster'];

    function WrongAnswerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WrongAnswer, CommonCode, QuestionMaster) {
        var vm = this;

        vm.wrongAnswer = entity;
        vm.clear = clear;
        vm.save = save;
        vm.commoncodes = CommonCode.query();
        vm.questionmasters = QuestionMaster.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.wrongAnswer.id !== null) {
                WrongAnswer.update(vm.wrongAnswer, onSaveSuccess, onSaveError);
            } else {
                WrongAnswer.save(vm.wrongAnswer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('itinterviewApp:wrongAnswerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
