'use strict';

describe('Controller Tests', function() {

    describe('UpRightAnswer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUpRightAnswer, MockUpQuestionMaster;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUpRightAnswer = jasmine.createSpy('MockUpRightAnswer');
            MockUpQuestionMaster = jasmine.createSpy('MockUpQuestionMaster');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UpRightAnswer': MockUpRightAnswer,
                'UpQuestionMaster': MockUpQuestionMaster
            };
            createController = function() {
                $injector.get('$controller')("UpRightAnswerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'itinterviewApp:upRightAnswerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
