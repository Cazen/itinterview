(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('TryQuestion', TryQuestion);

    TryQuestion.$inject = ['$resource', 'DateUtils'];

    function TryQuestion ($resource, DateUtils) {
        var resourceUrl =  'api/question/tryquestion/:id';

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
