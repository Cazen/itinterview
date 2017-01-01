(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('TryQuestionController', TryQuestionController);

    TryQuestionController.$inject = ['$scope', '$state', 'TryQuestion', 'ParseLinks', 'AlertService'];

    function TryQuestionController ($scope, $state, TryQuestion, ParseLinks, AlertService) {
        var vm = this;

        vm.tryQuestions = [];
        vm.loadPage = loadPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        loadAll();

        function loadAll () {
            TryQuestion.query({
                page: vm.page,
                size: 20,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.tryQuestions.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.tryQuestions = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

        $scope.popoverInit = function() {
            return $scope.downloadSize > 100;
        };


    }
})();
