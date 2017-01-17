(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('TryQuestionNewController', TryQuestionNewController);


    TryQuestionNewController.$inject = ['$scope', 'TryQuestionNew', 'category3SelectboxVal', 'Principal'];

    function TryQuestionNewController ($scope, TryQuestionNew, category3SelectboxVal, Principal) {
        var vm = this;
        vm.account = null;
        vm.isAuthenticated = null;

        vm.questionMasterForUser = TryQuestionNew.postQuestionListbyCategory3({category3SelectboxVal : category3SelectboxVal});

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

        // we will store all of our form data in this object
        $scope.formData = {};
        // function to process the form
        $scope.submitTryQuestionForm = function() {
            $scope.formData.startTime = vm.questionMasterForUser.startTime;
            $scope.formData.generatedId = vm.questionMasterForUser.generatedId;

            TryQuestionNew.postSubmitTryQuestionForm($scope.formData);
        };

        $scope.timerFinished = function (){
            alert("finished");
        };

    }
})();
