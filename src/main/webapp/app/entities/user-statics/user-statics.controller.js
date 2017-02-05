(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UserStaticsController', UserStaticsController);

    UserStaticsController.$inject = ['$scope', '$state', 'UserStatics'];

    function UserStaticsController ($scope, $state, UserStatics) {
        var vm = this;

        vm.userStatics = [];

        loadAll();

        function loadAll() {
            UserStatics.query(function(result) {
                vm.userStatics = result;
                vm.searchQuery = null;
            });
        }
    }
})();
