(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('up-wrong-answer', {
            parent: 'entity',
            url: '/up-wrong-answer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UpWrongAnswers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/up-wrong-answer/up-wrong-answers.html',
                    controller: 'UpWrongAnswerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('up-wrong-answer-detail', {
            parent: 'entity',
            url: '/up-wrong-answer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UpWrongAnswer'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/up-wrong-answer/up-wrong-answer-detail.html',
                    controller: 'UpWrongAnswerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UpWrongAnswer', function($stateParams, UpWrongAnswer) {
                    return UpWrongAnswer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'up-wrong-answer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('up-wrong-answer-detail.edit', {
            parent: 'up-wrong-answer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-wrong-answer/up-wrong-answer-dialog.html',
                    controller: 'UpWrongAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UpWrongAnswer', function(UpWrongAnswer) {
                            return UpWrongAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('up-wrong-answer.new', {
            parent: 'up-wrong-answer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-wrong-answer/up-wrong-answer-dialog.html',
                    controller: 'UpWrongAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                optionText: null,
                                delYn: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('up-wrong-answer', null, { reload: 'up-wrong-answer' });
                }, function() {
                    $state.go('up-wrong-answer');
                });
            }]
        })
        .state('up-wrong-answer.edit', {
            parent: 'up-wrong-answer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-wrong-answer/up-wrong-answer-dialog.html',
                    controller: 'UpWrongAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UpWrongAnswer', function(UpWrongAnswer) {
                            return UpWrongAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('up-wrong-answer', null, { reload: 'up-wrong-answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('up-wrong-answer.delete', {
            parent: 'up-wrong-answer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-wrong-answer/up-wrong-answer-delete-dialog.html',
                    controller: 'UpWrongAnswerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UpWrongAnswer', function(UpWrongAnswer) {
                            return UpWrongAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('up-wrong-answer', null, { reload: 'up-wrong-answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
