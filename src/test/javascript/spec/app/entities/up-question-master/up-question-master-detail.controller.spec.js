'use strict';

describe('Controller Tests', function() {

    describe('UpQuestionMaster Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUpQuestionMaster, MockUpRightAnswer, MockUpWrongAnswer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUpQuestionMaster = jasmine.createSpy('MockUpQuestionMaster');
            MockUpRightAnswer = jasmine.createSpy('MockUpRightAnswer');
            MockUpWrongAnswer = jasmine.createSpy('MockUpWrongAnswer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UpQuestionMaster': MockUpQuestionMaster,
                'UpRightAnswer': MockUpRightAnswer,
                'UpWrongAnswer': MockUpWrongAnswer
            };
            createController = function() {
                $injector.get('$controller')("UpQuestionMasterDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'itinterviewApp:upQuestionMasterUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
