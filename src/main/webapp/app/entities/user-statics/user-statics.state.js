(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-statics', {
            parent: 'entity',
            url: '/user-statics',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserStatics'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-statics/user-statics.html',
                    controller: 'UserStaticsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('user-statics-detail', {
            parent: 'entity',
            url: '/user-statics/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserStatics'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-statics/user-statics-detail.html',
                    controller: 'UserStaticsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UserStatics', function($stateParams, UserStatics) {
                    return UserStatics.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-statics',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-statics-detail.edit', {
            parent: 'user-statics-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-statics/user-statics-dialog.html',
                    controller: 'UserStaticsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserStatics', function(UserStatics) {
                            return UserStatics.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-statics.new', {
            parent: 'user-statics',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-statics/user-statics-dialog.html',
                    controller: 'UserStaticsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                eloRating: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-statics', null, { reload: 'user-statics' });
                }, function() {
                    $state.go('user-statics');
                });
            }]
        })
        .state('user-statics.edit', {
            parent: 'user-statics',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-statics/user-statics-dialog.html',
                    controller: 'UserStaticsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserStatics', function(UserStatics) {
                            return UserStatics.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-statics', null, { reload: 'user-statics' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-statics.delete', {
            parent: 'user-statics',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-statics/user-statics-delete-dialog.html',
                    controller: 'UserStaticsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserStatics', function(UserStatics) {
                            return UserStatics.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-statics', null, { reload: 'user-statics' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
