(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('CommonCode', CommonCode);

    CommonCode.$inject = ['$resource'];

    function CommonCode ($resource) {
        var resourceUrl =  'api/common-codes/:id';

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
