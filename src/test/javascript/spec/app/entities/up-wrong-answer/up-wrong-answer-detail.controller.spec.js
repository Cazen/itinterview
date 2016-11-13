'use strict';

describe('Controller Tests', function() {

    describe('UpWrongAnswer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUpWrongAnswer, MockCommonCode, MockUpQuestionMaster;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUpWrongAnswer = jasmine.createSpy('MockUpWrongAnswer');
            MockCommonCode = jasmine.createSpy('MockCommonCode');
            MockUpQuestionMaster = jasmine.createSpy('MockUpQuestionMaster');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UpWrongAnswer': MockUpWrongAnswer,
                'CommonCode': MockCommonCode,
                'UpQuestionMaster': MockUpQuestionMaster
            };
            createController = function() {
                $injector.get('$controller')("UpWrongAnswerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'itinterviewApp:upWrongAnswerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
