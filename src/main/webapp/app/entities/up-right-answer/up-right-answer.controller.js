(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('UpRightAnswerController', UpRightAnswerController);

    UpRightAnswerController.$inject = ['$scope', '$state', 'UpRightAnswer', 'ParseLinks', 'AlertService'];

    function UpRightAnswerController ($scope, $state, UpRightAnswer, ParseLinks, AlertService) {
        var vm = this;
        
        vm.upRightAnswers = [];
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
            UpRightAnswer.query({
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
                    vm.upRightAnswers.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.upRightAnswers = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }
    }
})();
