(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('uploadQuestion', {
            parent: 'question',
            url: '/uploadQuestion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UploadQuestion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/question/upload-question/uploadQuestion.html',
                    controller: 'UploadQuestionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('uploadQuestion-detail', {
            parent: 'uploadQuestion',
            url: '/uploadQuestion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UploadQuestion'
            },
            views: {
                'content@': {
                    templateUrl: 'app/question/upload-question/uploadQuestion-detail.html',
                    controller: 'UploadQuestionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                question: ['$stateParams', 'UploadQuestion', function($stateParams, UploadQuestion) {
                    return UploadQuestion.get({id : $stateParams.id}).$promise;
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
                    templateUrl: 'app/question/upload-question/uploadQuestion-dialog.html',
                    controller: 'UploadQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        question: ['UploadQuestion', function(UploadQuestion) {
                            return UploadQuestion.get({id : $stateParams.id}).$promise;
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
                    templateUrl: 'app/question/upload-question/uploadQuestion-dialog.html',
                    controller: 'UploadQuestionDialogController',
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
                    templateUrl: 'app/question/upload-question/uploadQuestion-dialog.html',
                    controller: 'UploadQuestionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        question: ['UploadQuestion', function(UploadQuestion) {
                            return UploadQuestion.get({id : $stateParams.id}).$promise;
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
                    templateUrl: 'app/question/upload-question/uploadQuestion-delete-dialog.html',
                    controller: 'UploadQuestionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        question: ['UploadQuestion', function(UploadQuestion) {
                            return UploadQuestion.get({id : $stateParams.id}).$promise;
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
