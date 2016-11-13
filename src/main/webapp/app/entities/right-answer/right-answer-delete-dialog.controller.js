(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('RightAnswerDeleteController',RightAnswerDeleteController);

    RightAnswerDeleteController.$inject = ['$uibModalInstance', 'entity', 'RightAnswer'];

    function RightAnswerDeleteController($uibModalInstance, entity, RightAnswer) {
        var vm = this;

        vm.rightAnswer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RightAnswer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
