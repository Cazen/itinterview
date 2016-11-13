(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('right-answer', {
            parent: 'entity',
            url: '/right-answer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RightAnswers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/right-answer/right-answers.html',
                    controller: 'RightAnswerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('right-answer-detail', {
            parent: 'entity',
            url: '/right-answer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RightAnswer'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/right-answer/right-answer-detail.html',
                    controller: 'RightAnswerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RightAnswer', function($stateParams, RightAnswer) {
                    return RightAnswer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'right-answer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('right-answer-detail.edit', {
            parent: 'right-answer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/right-answer/right-answer-dialog.html',
                    controller: 'RightAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RightAnswer', function(RightAnswer) {
                            return RightAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('right-answer.new', {
            parent: 'right-answer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/right-answer/right-answer-dialog.html',
                    controller: 'RightAnswerDialogController',
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
                    $state.go('right-answer', null, { reload: 'right-answer' });
                }, function() {
                    $state.go('right-answer');
                });
            }]
        })
        .state('right-answer.edit', {
            parent: 'right-answer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/right-answer/right-answer-dialog.html',
                    controller: 'RightAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RightAnswer', function(RightAnswer) {
                            return RightAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('right-answer', null, { reload: 'right-answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('right-answer.delete', {
            parent: 'right-answer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/right-answer/right-answer-delete-dialog.html',
                    controller: 'RightAnswerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RightAnswer', function(RightAnswer) {
                            return RightAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('right-answer', null, { reload: 'right-answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
