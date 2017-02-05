(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('QuestionMasterStaticsController', QuestionMasterStaticsController);

    QuestionMasterStaticsController.$inject = ['$scope', '$state', 'QuestionMasterStatics'];

    function QuestionMasterStaticsController ($scope, $state, QuestionMasterStatics) {
        var vm = this;

        vm.questionMasterStatics = [];

        loadAll();

        function loadAll() {
            QuestionMasterStatics.query(function(result) {
                vm.questionMasterStatics = result;
                vm.searchQuery = null;
            });
        }
    }
})();
