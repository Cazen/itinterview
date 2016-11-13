(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('UpQuestionMaster', UpQuestionMaster);

    UpQuestionMaster.$inject = ['$resource', 'DateUtils'];

    function UpQuestionMaster ($resource, DateUtils) {
        var resourceUrl =  'api/up-question-masters/:id';

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
