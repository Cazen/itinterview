(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tryQuestion', {
            parent: 'question',
            url: '/tryQuestion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '문제풀기 - Category 선택'
            },
            views: {
                'content@': {
                    templateUrl: 'app/question/try-question/tryQuestion.html',
                    controller: 'TryQuestionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('tryQuestion.new', {
            parent: 'tryQuestion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: '문제풀기 - Main'
            },
            views: {
                'content@': {
                    templateUrl: 'app/question/try-question/tryQuestion-one.html',
                    controller: 'TryQuestionOneController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });


    }

})();
