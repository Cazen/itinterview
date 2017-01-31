(function() {
    'use strict';

    angular
        .module('itinterviewApp', [
            'ngStorage',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            'ngAnimate',
            'timer',
            'angular-google-analytics'
        ]).config(['AnalyticsProvider', function (AnalyticsProvider) {
            AnalyticsProvider.setAccount('UA-91127218-1');  //UU-XXXXXXX-X should be your tracking code
            AnalyticsProvider.setPageEvent('$stateChangeSuccess');

        }]).run(['Analytics', function(Analytics) { }])
          .run(run);

    run.$inject = ['stateHandler'];

    function run(stateHandler) {
        stateHandler.initialize();
    }

})();
