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
                pageTitle: 'TryQuestion'
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
        .state('tryQuestion-detail', {
            parent: 'tryQuestion',
            url: '/tryQuestion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TryQuestion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/question/try-question/tryQuestion-detail.html',
                    controller: 'TryQuestionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                question: ['$stateParams', 'TryQuestion', function($stateParams, TryQuestion) {
                    return TryQuestion.get({id : $stateParams.id}).$promise;
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
        .state('tryQuestion-detail.edit', {
            parent: 'tryQuestion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/question/try-question/tryQuestion-dialog.html',
                    controller: 'TryQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        question: ['TryQuestion', function(TryQuestion) {
                            return TryQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tryQuestion.new', {
            parent: 'tryQuestion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/question/try-question/tryQuestion-dialog.html',
                    controller: 'TryQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        question: function () {
                            return {
                                title: null,
                                delYn: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tryQuestion', null, { reload: 'tryQuestion' });
                }, function() {
                    $state.go('tryQuestion');
                });
            }]
        })
        .state('tryQuestion.edit', {
            parent: 'tryQuestion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/question/try-question/tryQuestion-dialog.html',
                    controller: 'TryQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        question: ['TryQuestion', function(TryQuestion) {
                            return TryQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tryQuestion', null, { reload: 'tryQuestion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tryQuestion.delete', {
            parent: 'tryQuestion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/question/try-question/tryQuestion-delete-dialog.html',
                    controller: 'TryQuestionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        question: ['TryQuestion', function(TryQuestion) {
                            return TryQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tryQuestion', null, { reload: 'tryQuestion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
