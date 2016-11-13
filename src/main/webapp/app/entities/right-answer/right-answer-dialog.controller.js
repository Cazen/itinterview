(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('RightAnswerDialogController', RightAnswerDialogController);

    RightAnswerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RightAnswer', 'CommonCode', 'QuestionMaster'];

    function RightAnswerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RightAnswer, CommonCode, QuestionMaster) {
        var vm = this;

        vm.rightAnswer = entity;
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
            if (vm.rightAnswer.id !== null) {
                RightAnswer.update(vm.rightAnswer, onSaveSuccess, onSaveError);
            } else {
                RightAnswer.save(vm.rightAnswer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('itinterviewApp:rightAnswerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
