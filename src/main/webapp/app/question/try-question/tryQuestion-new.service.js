(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('TryQuestionNew', TryQuestionNew);

    TryQuestionNew.$inject = ['$resource', 'DateUtils'];

    function TryQuestionNew ($resource, DateUtils) {
        var resourceUrl =  'api/question/tryquestionnew/';

        return $resource(resourceUrl, {}, {

            'getQuestionListbyCategory3': { method: 'GET', isArray: true}
        });

    }
})();
