(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('up-right-answer', {
            parent: 'entity',
            url: '/up-right-answer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UpRightAnswers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/up-right-answer/up-right-answers.html',
                    controller: 'UpRightAnswerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('up-right-answer-detail', {
            parent: 'entity',
            url: '/up-right-answer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UpRightAnswer'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/up-right-answer/up-right-answer-detail.html',
                    controller: 'UpRightAnswerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UpRightAnswer', function($stateParams, UpRightAnswer) {
                    return UpRightAnswer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'up-right-answer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('up-right-answer-detail.edit', {
            parent: 'up-right-answer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-right-answer/up-right-answer-dialog.html',
                    controller: 'UpRightAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UpRightAnswer', function(UpRightAnswer) {
                            return UpRightAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('up-right-answer.new', {
            parent: 'up-right-answer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-right-answer/up-right-answer-dialog.html',
                    controller: 'UpRightAnswerDialogController',
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
                    $state.go('up-right-answer', null, { reload: 'up-right-answer' });
                }, function() {
                    $state.go('up-right-answer');
                });
            }]
        })
        .state('up-right-answer.edit', {
            parent: 'up-right-answer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-right-answer/up-right-answer-dialog.html',
                    controller: 'UpRightAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UpRightAnswer', function(UpRightAnswer) {
                            return UpRightAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('up-right-answer', null, { reload: 'up-right-answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('up-right-answer.delete', {
            parent: 'up-right-answer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-right-answer/up-right-answer-delete-dialog.html',
                    controller: 'UpRightAnswerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UpRightAnswer', function(UpRightAnswer) {
                            return UpRightAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('up-right-answer', null, { reload: 'up-right-answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
