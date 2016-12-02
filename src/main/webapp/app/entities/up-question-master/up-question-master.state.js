(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('up-question-master', {
            parent: 'entity',
            url: '/up-question-master',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UpQuestionMasters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/up-question-master/up-question-masters.html',
                    controller: 'UpQuestionMasterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('up-question-master-detail', {
            parent: 'entity',
            url: '/up-question-master/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UpQuestionMaster'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/up-question-master/up-question-master-detail.html',
                    controller: 'UpQuestionMasterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UpQuestionMaster', function($stateParams, UpQuestionMaster) {
                    return UpQuestionMaster.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'up-question-master',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('up-question-master-detail.edit', {
            parent: 'up-question-master-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-question-master/up-question-master-dialog.html',
                    controller: 'UpQuestionMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UpQuestionMaster', function(UpQuestionMaster) {
                            return UpQuestionMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('up-question-master.new', {
            parent: 'up-question-master',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-question-master/up-question-master-dialog.html',
                    controller: 'UpQuestionMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                delYn: null,
                                cTime: null,
                                author: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('up-question-master', null, { reload: 'up-question-master' });
                }, function() {
                    $state.go('up-question-master');
                });
            }]
        })
        .state('up-question-master.edit', {
            parent: 'up-question-master',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-question-master/up-question-master-dialog.html',
                    controller: 'UpQuestionMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UpQuestionMaster', function(UpQuestionMaster) {
                            return UpQuestionMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('up-question-master', null, { reload: 'up-question-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('up-question-master.delete', {
            parent: 'up-question-master',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-question-master/up-question-master-delete-dialog.html',
                    controller: 'UpQuestionMasterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UpQuestionMaster', function(UpQuestionMaster) {
                            return UpQuestionMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('up-question-master', null, { reload: 'up-question-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
