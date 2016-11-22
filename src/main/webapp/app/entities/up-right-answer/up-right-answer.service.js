(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('UpRightAnswer', UpRightAnswer);

    UpRightAnswer.$inject = ['$resource'];

    function UpRightAnswer ($resource) {
        var resourceUrl =  'api/up-right-answers/:id';

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
