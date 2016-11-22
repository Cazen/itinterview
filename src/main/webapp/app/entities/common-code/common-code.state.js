(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('common-code', {
            parent: 'entity',
            url: '/common-code',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CommonCodes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/common-code/common-codes.html',
                    controller: 'CommonCodeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('common-code-detail', {
            parent: 'entity',
            url: '/common-code/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CommonCode'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/common-code/common-code-detail.html',
                    controller: 'CommonCodeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CommonCode', function($stateParams, CommonCode) {
                    return CommonCode.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'common-code',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('common-code-detail.edit', {
            parent: 'common-code-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/common-code/common-code-dialog.html',
                    controller: 'CommonCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CommonCode', function(CommonCode) {
                            return CommonCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('common-code.new', {
            parent: 'common-code',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/common-code/common-code-dialog.html',
                    controller: 'CommonCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cdTp: null,
                                cdId: null,
                                cdNm: null,
                                delYn: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('common-code', null, { reload: 'common-code' });
                }, function() {
                    $state.go('common-code');
                });
            }]
        })
        .state('common-code.edit', {
            parent: 'common-code',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/common-code/common-code-dialog.html',
                    controller: 'CommonCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CommonCode', function(CommonCode) {
                            return CommonCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('common-code', null, { reload: 'common-code' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('common-code.delete', {
            parent: 'common-code',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/common-code/common-code-delete-dialog.html',
                    controller: 'CommonCodeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CommonCode', function(CommonCode) {
                            return CommonCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('common-code', null, { reload: 'common-code' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
