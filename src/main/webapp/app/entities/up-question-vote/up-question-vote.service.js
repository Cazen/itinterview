(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('UpQuestionVote', UpQuestionVote);

    UpQuestionVote.$inject = ['$resource', 'DateUtils'];

    function UpQuestionVote ($resource, DateUtils) {
        var resourceUrl =  'api/up-question-votes/:id';

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
