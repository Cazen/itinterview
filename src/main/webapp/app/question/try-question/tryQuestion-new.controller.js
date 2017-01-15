(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('TryQuestionNewController', TryQuestionNewController);


    TryQuestionNewController.$inject = ['$scope', 'Principal', 'question'];

    function TryQuestionNewController ($scope, Principal, question) {
        var vm = this;
        vm.account = null;
        vm.isAuthenticated = null;

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

        vm.questionList = question;

        // we will store all of our form data in this object
        $scope.formData = {};
        // function to process the form
        $scope.processForm = function() {
            alert('awesome!');
        };
    }
})();
