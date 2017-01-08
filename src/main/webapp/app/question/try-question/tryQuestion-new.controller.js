(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('TryQuestionNewController', TryQuestionNewController);


    TryQuestionNewController.$inject = ['$scope', 'TryQuestionNew'];

    function TryQuestionNewController ($scope, TryQuestionNew) {
        var vm = this;

        //vm.QuestionMasterList = TryQuestionNew.getQuestionListbyCategory3();

        // we will store all of our form data in this object
        $scope.formData = {};
        // function to process the form
        $scope.processForm = function() {
            alert('awesome!');
        };
    }
})();
