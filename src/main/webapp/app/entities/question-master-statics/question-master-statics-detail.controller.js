(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('QuestionMasterStaticsDetailController', QuestionMasterStaticsDetailController);

    QuestionMasterStaticsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'QuestionMasterStatics', 'QuestionMaster'];

    function QuestionMasterStaticsDetailController($scope, $rootScope, $stateParams, previousState, entity, QuestionMasterStatics, QuestionMaster) {
        var vm = this;

        vm.questionMasterStatics = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('itinterviewApp:questionMasterStaticsUpdate', function(event, result) {
            vm.questionMasterStatics = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
