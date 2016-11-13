(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('RightAnswer', RightAnswer);

    RightAnswer.$inject = ['$resource'];

    function RightAnswer ($resource) {
        var resourceUrl =  'api/right-answers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
