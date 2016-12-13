(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('UploadQuestion', UploadQuestion);

    UploadQuestion.$inject = ['$resource', 'DateUtils'];

    function UploadQuestion ($resource, DateUtils) {
        var resourceUrl =  'api/question/uploadquestion/:id';

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
