(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('DIscourseSsoController', DIscourseSsoController);

    DIscourseSsoController.$inject = ['$state'];

    function DIscourseSsoController ($state) {
        $state.go("discourseSso.goDiscourse");
    }
})();
