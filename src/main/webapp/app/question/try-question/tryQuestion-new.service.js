(function() {
    'use strict';
    angular
        .module('itinterviewApp')
        .factory('TryQuestionNew', TryQuestionNew);

    TryQuestionNew.$inject = ['$resource', "$stateParams", 'DateUtils'];

    function TryQuestionNew ($resource, $stateParams, DateUtils) {
        var resourceUrl =  'api/question/tryquestionnew/';

        return $resource(resourceUrl, {}, {

            'postQuestionListbyCategory3': {
                url: 'api/question/tryquestionnew/',
                method: 'POST',
                headers: { 'Content-Type': 'application/json; charset=UTF-8'},
                data: {category3SelectboxVal: '@category3SelectboxVal'},
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startTime = DateUtils.convertDateTimeFromServer(data.startTime);
                    }
                    return data;
                }
            }
        });

    }
})();
