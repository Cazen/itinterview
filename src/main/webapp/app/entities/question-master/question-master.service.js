(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('QuestionMaster', QuestionMaster);

    QuestionMaster.$inject = ['$resource', 'DateUtils'];

    function QuestionMaster ($resource, DateUtils) {
        var resourceUrl =  'api/question-masters/:id';

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
