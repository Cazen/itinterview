(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('QuestionMasterDialogController', QuestionMasterDialogController);

    QuestionMasterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'QuestionMaster', 'RightAnswer', 'WrongAnswer', 'CommonCode', 'User', 'QuestionMasterStatics'];

    function QuestionMasterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, QuestionMaster, RightAnswer, WrongAnswer, CommonCode, User, QuestionMasterStatics) {
        var vm = this;

        vm.questionMaster = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.rightanswers = RightAnswer.query();
        vm.wronganswers = WrongAnswer.query();
        vm.commoncodes = CommonCode.query();
        vm.users = User.query();
        vm.questionmasterstatics = QuestionMasterStatics.query({filter: 'questionmaster-is-null'});
        $q.all([vm.questionMaster.$promise, vm.questionmasterstatics.$promise]).then(function() {
            if (!vm.questionMaster.questionMasterStatics || !vm.questionMaster.questionMasterStatics.id) {
                return $q.reject();
            }
            return QuestionMasterStatics.get({id : vm.questionMaster.questionMasterStatics.id}).$promise;
        }).then(function(questionMasterStatics) {
            vm.questionmasterstatics.push(questionMasterStatics);
        });

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
