(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('uploadQuestion', {
            parent: 'entity',
            url: '/uploadQuestion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UpQuestion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/question/uploadquestion/uploadQuestion.html',
                    controller: 'UploadQuestionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('UploadQuestion-detail', {
            parent: 'entity',
            url: '/uploadQuestion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UpQuestion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/question/uploadquestion/uploadQuestion-detail.html',
                    controller: 'UpQuestionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UpQuestion', function($stateParams, UpQuestion) {
                    return UpQuestion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'uploadQuestion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('uploadQuestion-detail.edit', {
            parent: 'uploadQuestion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/question/uploadquestion/uploadQuestion-dialog.html',
                    controller: 'UploadQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UpQuestion', function(UpQuestion) {
                            return UpQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('uploadQuestion.new', {
            parent: 'uploadQuestion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/question/uploadquestion/uploadQuestion-dialog.html',
                    controller: 'UploadQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                delYn: null,
                                cTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('uploadQuestion', null, { reload: 'uploadQuestion' });
                }, function() {
                    $state.go('uploadQuestion');
                });
            }]
        })
        .state('uploadQuestion.edit', {
            parent: 'uploadQuestion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/question/uploadquestion/uploadQuestion-dialog.html',
                    controller: 'UpQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UpQuestion', function(UpQuestion) {
                            return UpQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('uploadQuestion', null, { reload: 'uploadQuestion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('uploadQuestion.delete', {
            parent: 'uploadQuestion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/question/uploadquestion/uploadQuestion-delete-dialog.html',
                    controller: 'UpQuestionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UpQuestion', function(UpQuestion) {
                            return UpQuestion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('uploadQuestion', null, { reload: 'uploadQuestion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
