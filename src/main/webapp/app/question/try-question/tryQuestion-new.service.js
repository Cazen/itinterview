(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('TryQuestionNew', TryQuestionNew);

    TryQuestionNew.$inject = ['$resource', "$stateParams", 'DateUtils'];

    function TryQuestionNew ($resource, $stateParams, DateUtils) {
        var resourceUrl =  'api/question/tryquestionnew/';

        return $resource(resourceUrl, {}, {

            'getQuestionListbyCategory3': { url: 'api/question/tryquestionnew/:category3SelectboxVal', method: 'GET'}
        });

    }
})();
