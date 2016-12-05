(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('wrong-answer', {
            parent: 'entity',
            url: '/wrong-answer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WrongAnswers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/wrong-answer/wrong-answers.html',
                    controller: 'WrongAnswerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('wrong-answer-detail', {
            parent: 'entity',
            url: '/wrong-answer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'WrongAnswer'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/wrong-answer/wrong-answer-detail.html',
                    controller: 'WrongAnswerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'WrongAnswer', function($stateParams, WrongAnswer) {
                    return WrongAnswer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'wrong-answer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('wrong-answer-detail.edit', {
            parent: 'wrong-answer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wrong-answer/wrong-answer-dialog.html',
                    controller: 'WrongAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WrongAnswer', function(WrongAnswer) {
                            return WrongAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('wrong-answer.new', {
            parent: 'wrong-answer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wrong-answer/wrong-answer-dialog.html',
                    controller: 'WrongAnswerDialogController',
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
                    $state.go('wrong-answer', null, { reload: 'wrong-answer' });
                }, function() {
                    $state.go('wrong-answer');
                });
            }]
        })
        .state('wrong-answer.edit', {
            parent: 'wrong-answer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wrong-answer/wrong-answer-dialog.html',
                    controller: 'WrongAnswerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WrongAnswer', function(WrongAnswer) {
                            return WrongAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('wrong-answer', null, { reload: 'wrong-answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('wrong-answer.delete', {
            parent: 'wrong-answer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/wrong-answer/wrong-answer-delete-dialog.html',
                    controller: 'WrongAnswerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WrongAnswer', function(WrongAnswer) {
                            return WrongAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('wrong-answer', null, { reload: 'wrong-answer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
