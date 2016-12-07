(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UploadQuestionDialogController', UploadQuestionDialogController);

    UploadQuestionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'question', 'UploadQuestion', 'UpRightAnswer', 'UpWrongAnswer'];

    function UploadQuestionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, question, UploadQuestion, UpRightAnswer, UpWrongAnswer) {
        var vm = this;

        $scope.upRightAnswers = [0];
        $scope.upWrongAnswers = [0, 1, 2, 3];

        vm.uploadQuestion = question;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.save = save;

        //Temporary disabled for creating UploadQuestion
        //vm.upRightAnswers = UpRightAnswer.query();
        //vm.upWrongAnswers = UpWrongAnswer.query();

        vm.upRightArray = {};


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
                $scope.upRightAnswersArray = [];
                $scope.upWrongAnswersArray = [];

                for (var i = 0; i < $scope.upRightAnswers.length; i++) {
                    $scope.upRightAnswersArray.push(vm.upRightAnswers[i]);
                }
                vm.uploadQuestion.upRightAnswers = $scope.upRightAnswersArray;

                for (var i = 0; i < $scope.upWrongAnswers.length; i++) {
                    $scope.upWrongAnswersArray.push(vm.upWrongAnswers[i]);
                }
                vm.uploadQuestion.upWrongAnswers = $scope.upWrongAnswersArray;

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

        $scope.addUpRightAnswerInputBox = function() {
            $scope.upRightAnswers.push({
            });
        };

        $scope.removeUpRightAnswerInputBox = function() {
            var lastItem = $scope.upRightAnswers.length-1;
            $scope.upRightAnswers.splice(lastItem);
        };

        $scope.addUpWrongAnswerInputBox = function() {
            $scope.upWrongAnswers.push({
            });
        };

        $scope.removeUpWrongAnswerInputBox = function() {
            var lastItem = $scope.upWrongAnswers.length-1;
            $scope.upWrongAnswers.splice(lastItem);
        };

        $scope.scrollBarInit = function() {
            $("#difficulty-slider").ionRangeSlider({
                min: 0,
                max: 100,
                from: 50,
                step: 10,
                grid: true,
                grid_num: 10
            });
        };
    }
})();
