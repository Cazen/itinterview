(function () {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider) {
        $stateProvider
            .state('discourseSso.goDiscourse', {
                parent: 'app',
                url: '/ssoLanding',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Discourse SSO Landing Page'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/components/sso/discourse-sso.html',
                        controller: 'DIscourseSsoController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('discourseSso.goDiscourse', {
                parent: 'app',
                url: '/goDiscourse',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Discourse SSO Landing Page'
                }
            })
        ;
    }

})();
