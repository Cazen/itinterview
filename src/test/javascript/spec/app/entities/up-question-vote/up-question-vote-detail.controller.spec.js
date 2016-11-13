'use strict';

describe('Controller Tests', function() {

    describe('UpQuestionVote Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUpQuestionVote, MockUpQuestionMaster, MockUser, MockCommonCode;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUpQuestionVote = jasmine.createSpy('MockUpQuestionVote');
            MockUpQuestionMaster = jasmine.createSpy('MockUpQuestionMaster');
            MockUser = jasmine.createSpy('MockUser');
            MockCommonCode = jasmine.createSpy('MockCommonCode');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UpQuestionVote': MockUpQuestionVote,
                'UpQuestionMaster': MockUpQuestionMaster,
                'User': MockUser,
                'CommonCode': MockCommonCode
            };
            createController = function() {
                $injector.get('$controller')("UpQuestionVoteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'itinterviewApp:upQuestionVoteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
