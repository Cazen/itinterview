(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('question-vote', {
            parent: 'entity',
            url: '/question-vote',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'QuestionVotes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-vote/question-votes.html',
                    controller: 'QuestionVoteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('question-vote-detail', {
            parent: 'entity',
            url: '/question-vote/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'QuestionVote'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-vote/question-vote-detail.html',
                    controller: 'QuestionVoteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'QuestionVote', function($stateParams, QuestionVote) {
                    return QuestionVote.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'question-vote',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('question-vote-detail.edit', {
            parent: 'question-vote-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-vote/question-vote-dialog.html',
                    controller: 'QuestionVoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionVote', function(QuestionVote) {
                            return QuestionVote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-vote.new', {
            parent: 'question-vote',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-vote/question-vote-dialog.html',
                    controller: 'QuestionVoteDialogController',
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
                    $state.go('question-vote', null, { reload: 'question-vote' });
                }, function() {
                    $state.go('question-vote');
                });
            }]
        })
        .state('question-vote.edit', {
            parent: 'question-vote',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-vote/question-vote-dialog.html',
                    controller: 'QuestionVoteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionVote', function(QuestionVote) {
                            return QuestionVote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-vote', null, { reload: 'question-vote' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-vote.delete', {
            parent: 'question-vote',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-vote/question-vote-delete-dialog.html',
                    controller: 'QuestionVoteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['QuestionVote', function(QuestionVote) {
                            return QuestionVote.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-vote', null, { reload: 'question-vote' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
