(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('QuestionMasterStatics', QuestionMasterStatics);

    QuestionMasterStatics.$inject = ['$resource'];

    function QuestionMasterStatics ($resource) {
        var resourceUrl =  'api/question-master-statics/:id';

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
