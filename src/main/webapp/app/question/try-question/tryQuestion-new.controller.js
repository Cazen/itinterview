(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('TryQuestionNewController', TryQuestionNewController);


    TryQuestionNewController.$inject = ['$scope', '$http', '$state', 'previousState', 'TryQuestionNew', 'category3SelectboxVal', 'Principal'];

    function TryQuestionNewController ($scope, $http, $state, previousState, TryQuestionNew, category3SelectboxVal, Principal) {
        var vm = this;
        vm.account = null;
        vm.isAuthenticated = null;

        if(previousState.name != 'tryQuestion') {
            alert("잘못된 접근입니다");
            $state.go('tryQuestion');
        }

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
            $scope.formData.questionOne = vm.questionMasterForUser.questionMasterList[0].id;
            $scope.formData.questionTwo = vm.questionMasterForUser.questionMasterList[1].id;
            $scope.formData.questionThree = vm.questionMasterForUser.questionMasterList[2].id;
            $scope.formData.questionFour = vm.questionMasterForUser.questionMasterList[3].id;
            $scope.formData.questionFive = vm.questionMasterForUser.questionMasterList[4].id;
            $scope.formData.questionSix = vm.questionMasterForUser.questionMasterList[5].id;
            $scope.formData.questionSeven = vm.questionMasterForUser.questionMasterList[6].id;

            $http.post('api/question/tryQuestionAnswer/', $scope.formData, {
                headers: { 'Content-Type': 'application/json; charset=UTF-8'},
            }).success(function (response) {
                $state.go("tryQuestionNew.result", {resultData: response});
            });

            //var resultData = TryQuestionNew.postSubmitTryQuestionForm($scope.formData);
            //$state.go("tryQuestionNew.result", {resultData: resultData});
        };

        $scope.timerFinished = function (){
            //alert("finished");
        };

    }
})();
