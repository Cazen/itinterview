(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('QuestionVote', QuestionVote);

    QuestionVote.$inject = ['$resource', 'DateUtils'];

    function QuestionVote ($resource, DateUtils) {
        var resourceUrl =  'api/question-votes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.cTime = DateUtils.convertDateTimeFromServer(data.cTime);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
