'use strict';

describe('Controller Tests', function() {

    describe('QuestionMasterStatics Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockQuestionMasterStatics, MockQuestionMaster;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockQuestionMasterStatics = jasmine.createSpy('MockQuestionMasterStatics');
            MockQuestionMaster = jasmine.createSpy('MockQuestionMaster');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'QuestionMasterStatics': MockQuestionMasterStatics,
                'QuestionMaster': MockQuestionMaster
            };
            createController = function() {
                $injector.get('$controller')("QuestionMasterStaticsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'itinterviewApp:questionMasterStaticsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
