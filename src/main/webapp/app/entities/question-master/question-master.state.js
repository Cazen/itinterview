(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('question-master', {
            parent: 'entity',
            url: '/question-master',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'QuestionMasters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-master/question-masters.html',
                    controller: 'QuestionMasterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('question-master-detail', {
            parent: 'entity',
            url: '/question-master/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'QuestionMaster'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-master/question-master-detail.html',
                    controller: 'QuestionMasterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'QuestionMaster', function($stateParams, QuestionMaster) {
                    return QuestionMaster.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'question-master',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('question-master-detail.edit', {
            parent: 'question-master-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-master/question-master-dialog.html',
                    controller: 'QuestionMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionMaster', function(QuestionMaster) {
                            return QuestionMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-master.new', {
            parent: 'question-master',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-master/question-master-dialog.html',
                    controller: 'QuestionMasterDialogController',
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
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('question-master', null, { reload: 'question-master' });
                }, function() {
                    $state.go('question-master');
                });
            }]
        })
        .state('question-master.edit', {
            parent: 'question-master',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-master/question-master-dialog.html',
                    controller: 'QuestionMasterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionMaster', function(QuestionMaster) {
                            return QuestionMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-master', null, { reload: 'question-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-master.delete', {
            parent: 'question-master',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-master/question-master-delete-dialog.html',
                    controller: 'QuestionMasterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['QuestionMaster', function(QuestionMaster) {
                            return QuestionMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-master', null, { reload: 'question-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
