(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('up-question-vote', {
            parent: 'entity',
            url: '/up-question-vote',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UpQuestionVotes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/up-question-vote/up-question-votes.html',
                    controller: 'UpQuestionVoteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('up-question-vote-detail', {
            parent: 'entity',
            url: '/up-question-vote/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UpQuestionVote'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/up-question-vote/up-question-vote-detail.html',
                    controller: 'UpQuestionVoteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UpQuestionVote', function($stateParams, UpQuestionVote) {
                    return UpQuestionVote.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'up-question-vote',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('up-question-vote-detail.edit', {
            parent: 'up-question-vote-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-question-vote/up-question-vote-dialog.html',
                    controller: 'UpQuestionVoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UpQuestionVote', function(UpQuestionVote) {
                            return UpQuestionVote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('up-question-vote.new', {
            parent: 'up-question-vote',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-question-vote/up-question-vote-dialog.html',
                    controller: 'UpQuestionVoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('up-question-vote', null, { reload: 'up-question-vote' });
                }, function() {
                    $state.go('up-question-vote');
                });
            }]
        })
        .state('up-question-vote.edit', {
            parent: 'up-question-vote',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-question-vote/up-question-vote-dialog.html',
                    controller: 'UpQuestionVoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UpQuestionVote', function(UpQuestionVote) {
                            return UpQuestionVote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('up-question-vote', null, { reload: 'up-question-vote' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('up-question-vote.delete', {
            parent: 'up-question-vote',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/up-question-vote/up-question-vote-delete-dialog.html',
                    controller: 'UpQuestionVoteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UpQuestionVote', function(UpQuestionVote) {
                            return UpQuestionVote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('up-question-vote', null, { reload: 'up-question-vote' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
