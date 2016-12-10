'use strict';

describe('Controller Tests', function() {

    describe('WrongAnswer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWrongAnswer, MockCommonCode, MockQuestionMaster;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWrongAnswer = jasmine.createSpy('MockWrongAnswer');
            MockCommonCode = jasmine.createSpy('MockCommonCode');
            MockQuestionMaster = jasmine.createSpy('MockQuestionMaster');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'WrongAnswer': MockWrongAnswer,
                'CommonCode': MockCommonCode,
                'QuestionMaster': MockQuestionMaster
            };
            createController = function() {
                $injector.get('$controller')("WrongAnswerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'itinterviewApp:wrongAnswerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
