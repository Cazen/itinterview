(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('question-master-statics', {
            parent: 'entity',
            url: '/question-master-statics',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'QuestionMasterStatics'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-master-statics/question-master-statics.html',
                    controller: 'QuestionMasterStaticsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('question-master-statics-detail', {
            parent: 'entity',
            url: '/question-master-statics/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'QuestionMasterStatics'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-master-statics/question-master-statics-detail.html',
                    controller: 'QuestionMasterStaticsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'QuestionMasterStatics', function($stateParams, QuestionMasterStatics) {
                    return QuestionMasterStatics.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'question-master-statics',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('question-master-statics-detail.edit', {
            parent: 'question-master-statics-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-master-statics/question-master-statics-dialog.html',
                    controller: 'QuestionMasterStaticsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionMasterStatics', function(QuestionMasterStatics) {
                            return QuestionMasterStatics.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-master-statics.new', {
            parent: 'question-master-statics',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-master-statics/question-master-statics-dialog.html',
                    controller: 'QuestionMasterStaticsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                rightCount: null,
                                wrongCount: null,
                                upVoteCount: null,
                                downVoteCount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('question-master-statics', null, { reload: 'question-master-statics' });
                }, function() {
                    $state.go('question-master-statics');
                });
            }]
        })
        .state('question-master-statics.edit', {
            parent: 'question-master-statics',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-master-statics/question-master-statics-dialog.html',
                    controller: 'QuestionMasterStaticsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionMasterStatics', function(QuestionMasterStatics) {
                            return QuestionMasterStatics.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-master-statics', null, { reload: 'question-master-statics' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-master-statics.delete', {
            parent: 'question-master-statics',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-master-statics/question-master-statics-delete-dialog.html',
                    controller: 'QuestionMasterStaticsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['QuestionMasterStatics', function(QuestionMasterStatics) {
                            return QuestionMasterStatics.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-master-statics', null, { reload: 'question-master-statics' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
