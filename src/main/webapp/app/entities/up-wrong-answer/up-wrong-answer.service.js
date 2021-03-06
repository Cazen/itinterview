(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('UpWrongAnswer', UpWrongAnswer);

    UpWrongAnswer.$inject = ['$resource'];

    function UpWrongAnswer ($resource) {
        var resourceUrl =  'api/up-wrong-answers/:id';

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
