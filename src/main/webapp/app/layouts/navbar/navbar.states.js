(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('menu', {
            //parent: 'admin',
            url: '#menu',
            data: {
                authorities: ['ROLE_ANONYMOUS'],
                pageTitle: 'MENUS'
            },
            views: {
                'content@': {
                    templateUrl: 'layout/navbar/navbar.html',
                    controller: 'JhiMetricsMonitoringController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
