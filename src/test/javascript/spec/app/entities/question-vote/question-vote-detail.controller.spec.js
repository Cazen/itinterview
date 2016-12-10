'use strict';

describe('Controller Tests', function() {

    describe('QuestionVote Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockQuestionVote, MockQuestionMaster, MockUser, MockCommonCode;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockQuestionVote = jasmine.createSpy('MockQuestionVote');
            MockQuestionMaster = jasmine.createSpy('MockQuestionMaster');
            MockUser = jasmine.createSpy('MockUser');
            MockCommonCode = jasmine.createSpy('MockCommonCode');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'QuestionVote': MockQuestionVote,
                'QuestionMaster': MockQuestionMaster,
                'User': MockUser,
                'CommonCode': MockCommonCode
            };
            createController = function() {
                $injector.get('$controller')("QuestionVoteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'itinterviewApp:questionVoteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
