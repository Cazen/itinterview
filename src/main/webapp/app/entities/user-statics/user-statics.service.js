(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('UserStatics', UserStatics);

    UserStatics.$inject = ['$resource'];

    function UserStatics ($resource) {
        var resourceUrl =  'api/user-statics/:id';

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
