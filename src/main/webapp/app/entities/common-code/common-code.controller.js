(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('CommonCodeController', CommonCodeController);

    CommonCodeController.$inject = ['$scope', '$state', 'CommonCode'];

    function CommonCodeController ($scope, $state, CommonCode) {
        var vm = this;
        
        vm.commonCodes = [];

        loadAll();

        function loadAll() {
            CommonCode.query(function(result) {
                vm.commonCodes = result;
            });
        }
    }
})();
