(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('TryQuestionNewController', TryQuestionNewController);


    TryQuestionNewController.$inject = ['$scope'];

    function TryQuestionNewController ($scope) {
        var vm = this;

        // we will store all of our form data in this object
        $scope.formData = {};
        // function to process the form
        $scope.processForm = function() {
            alert('awesome!');
        };
    }
})();
