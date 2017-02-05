(function () {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    //angular.module("itinterviewApp").requires.push('ngAnimate');

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider) {
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
                resolve: {}
            })
            .state('tryQuestionNew', {
                parent: 'question',
                url: '/tryQuestionNew',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '문제풀기 - Main'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/question/try-question/tryQuestion-new.html',
                        controller: 'TryQuestionNewController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    category3SelectboxVal: {}
                },
                resolve: {
                    category3SelectboxVal: ['$stateParams', function($stateParams) {
                        return $stateParams.category3SelectboxVal;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'tryQuestion',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }

            })
            .state('tryQuestionNew.one', {
                parent: 'tryQuestionNew',
                url: '/tryQuestionNew-one',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '문제풀기 - 1번문제'
                },
                templateUrl: 'app/question/try-question/tryQuestion-one.html'
            })
            .state('tryQuestionNew.two', {
                parent: 'tryQuestionNew',
                url: '/tryQuestionNew-two',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '문제풀기 - 2번문제'
                },
                templateUrl: 'app/question/try-question/tryQuestion-two.html'
            })
            .state('tryQuestionNew.three', {
                parent: 'tryQuestionNew',
                url: '/tryQuestionNew-three',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '문제풀기 - 3번문제'
                },
                templateUrl: 'app/question/try-question/tryQuestion-three.html'
            })
            .state('tryQuestionNew.four', {
                parent: 'tryQuestionNew',
                url: '/tryQuestionNew-four',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '문제풀기 - 4번문제'
                },
                templateUrl: 'app/question/try-question/tryQuestion-four.html'
            })
            .state('tryQuestionNew.five', {
                parent: 'tryQuestionNew',
                url: '/tryQuestionNew-five',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '문제풀기 - 5번문제'
                },
                templateUrl: 'app/question/try-question/tryQuestion-five.html'
            })
            .state('tryQuestionNew.six', {
                parent: 'tryQuestionNew',
                url: '/tryQuestionNew-six',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '문제풀기 - 6번문제'
                },
                templateUrl: 'app/question/try-question/tryQuestion-six.html'
            })
            .state('tryQuestionNew.seven', {
                parent: 'tryQuestionNew',
                url: '/tryQuestionNew-seven',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '문제풀기 - 7번문제'
                },
                templateUrl: 'app/question/try-question/tryQuestion-seven.html'
            })
            .state('tryQuestionNew.result', {
                parent: 'tryQuestionNew',
                url: '/tryQuestionNew-result',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: '문제 풀이 결과 보기'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/question/try-question/tryQuestion-result.html',
                        controller: 'TryQuestionResultController',
                        controllerAs: 'vm'
                    }
                },
                params: {resultData: null},
                resolve: {
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'tryQuestionNew.seven',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
        ;
    }

})();
