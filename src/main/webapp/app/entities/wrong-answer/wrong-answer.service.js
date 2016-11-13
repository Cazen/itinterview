(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('WrongAnswer', WrongAnswer);

    WrongAnswer.$inject = ['$resource'];

    function WrongAnswer ($resource) {
        var resourceUrl =  'api/wrong-answers/:id';

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
