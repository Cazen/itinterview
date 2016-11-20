'use strict';

describe('Controller Tests', function() {

    describe('RightAnswer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRightAnswer, MockCommonCode, MockQuestionMaster;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRightAnswer = jasmine.createSpy('MockRightAnswer');
            MockCommonCode = jasmine.createSpy('MockCommonCode');
            MockQuestionMaster = jasmine.createSpy('MockQuestionMaster');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'RightAnswer': MockRightAnswer,
                'CommonCode': MockCommonCode,
                'QuestionMaster': MockQuestionMaster
            };
            createController = function() {
                $injector.get('$controller')("RightAnswerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'itinterviewApp:rightAnswerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
