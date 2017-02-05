(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('TryQuestionResultController', TryQuestionResultController);


    TryQuestionResultController.$inject = ['$scope', '$state', 'previousState', 'Principal'];

    function TryQuestionResultController ($scope, $state, previousState, Principal) {
        var vm = this;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.resultData = $state.params.resultData;
        vm.category3SelectboxVal = $state.params.category3SelectboxVal;

        if(previousState.name.split(".")[0] != 'tryQuestionNew') {
            alert("잘못된 접근입니다");
            $state.go('tryQuestion');
        }

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }


    }
})();
