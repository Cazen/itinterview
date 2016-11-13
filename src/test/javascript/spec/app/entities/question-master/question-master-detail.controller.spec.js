'use strict';

describe('Controller Tests', function() {

    describe('QuestionMaster Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockQuestionMaster, MockRightAnswer, MockWrongAnswer, MockUser, MockCommonCode;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockQuestionMaster = jasmine.createSpy('MockQuestionMaster');
            MockRightAnswer = jasmine.createSpy('MockRightAnswer');
            MockWrongAnswer = jasmine.createSpy('MockWrongAnswer');
            MockUser = jasmine.createSpy('MockUser');
            MockCommonCode = jasmine.createSpy('MockCommonCode');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'QuestionMaster': MockQuestionMaster,
                'RightAnswer': MockRightAnswer,
                'WrongAnswer': MockWrongAnswer,
                'User': MockUser,
                'CommonCode': MockCommonCode
            };
            createController = function() {
                $injector.get('$controller')("QuestionMasterDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'itinterviewApp:questionMasterUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
