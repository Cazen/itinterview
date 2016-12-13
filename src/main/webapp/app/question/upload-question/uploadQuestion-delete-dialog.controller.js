(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UploadQuestionDeleteController',UploadQuestionDeleteController);

    UploadQuestionDeleteController.$inject = ['$uibModalInstance', 'question', 'UploadQuestion'];

    function UploadQuestionDeleteController($uibModalInstance, question, UploadQuestion) {
        var vm = this;

        vm.uploadQuestion = question;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UploadQuestion.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
