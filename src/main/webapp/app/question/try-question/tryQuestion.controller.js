(function() {
    'use strict';

    angular
        .module('itinterviewApp')
        .controller('TryQuestionController', TryQuestionController);


    TryQuestionController.$inject = ['$scope', '$state', 'TryQuestion', 'ParseLinks', 'AlertService'];

    function TryQuestionController ($scope, $state, TryQuestion, ParseLinks, AlertService) {
        var vm = this;

        vm.category123CommonCodeList = [];
        vm.loadPage = loadPage;
        vm.page = 0;
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        loadAll();
        selectBoxInit('category1Selectbox');
        selectBoxInit('category2Selectbox');
        selectBoxInit('category3Selectbox');

        function loadAll () {
            TryQuestion.query({
                page: vm.page,
                size: 20,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                for (var i = 0; i < data.length; i++) {
                    vm.category123CommonCodeList.push(data[i]);
                }

                initCategorySelectBox($('#category1Selectbox'), 'QSTN_SEC1', null, null);
                initCategorySelectBox($('#category2Selectbox'), 'QSTN_SEC2', null, null);
                initCategorySelectBox($('#category3Selectbox'), 'QSTN_SEC3', null, null);

            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function initCategorySelectBox(selectbox, cdTp, selectedVal, parentId) {
            selectbox.find("option").remove();

            for (var i = 0; i < vm.category123CommonCodeList.length; i++) {
                if(vm.category123CommonCodeList[i].cdTp == cdTp) {
                    if(parentId == null || parentId == vm.category123CommonCodeList[i].hardCodedParentId) {
                        selectbox.append('<option value="' + vm.category123CommonCodeList[i].cdNm + '">' + vm.category123CommonCodeList[i].cdNm + '</option>');

                        if(selectedVal == vm.category123CommonCodeList[i].id) {
                            selectbox.val(vm.category123CommonCodeList[i].cdNm);
                        }

                    }
                }
            }

            if(selectedVal == null) {
                selectbox.val(null);
            }
            selectbox.mobileSelect('destroy');
            selectBoxInit(selectbox[0].id);

        }

        function reset () {
            vm.page = 0;
            vm.tryQuestions = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

        $scope.popoverInit = function() {
            return $scope.downloadSize > 100;
        };

        function selectBoxInit (selectBoxId) {
            if(selectBoxId == 'category1Selectbox') {
                $('#category1Selectbox').mobileSelect({
                    title: "대분류를 선택해 주세요",
                    onClose: function () {
                        callbackCategory1SelectBox($(this).val());
                    }
                });
            }

            if(selectBoxId == 'category2Selectbox') {
                $('#category2Selectbox').mobileSelect({
                    title: "중분류를 선택해 주세요",
                    onClose: function () {
                        callbackCategory2SelectBox($(this).val());
                    }
                });
            }

            if(selectBoxId == 'category3Selectbox') {
                $('#category3Selectbox').mobileSelect({
                    title: "소분류를 선택해 주세요",
                    onClose: function () {
                        callbackCategory3SelectBox($(this).val());
                    }
                });
            }
        };



        function getCommonCdFromcdNm(cdNm) {
            for (var i = 0; i < vm.category123CommonCodeList.length; i++) {
                if(vm.category123CommonCodeList[i].cdNm == cdNm) {
                    return vm.category123CommonCodeList[i];
                }
            }
        };

        function callbackCategory1SelectBox(cdNm) {
            if(cdNm == null) {
                return;
            }
            var category1SelectBox = getCommonCdFromcdNm(cdNm);

            initCategorySelectBox($('#category2Selectbox'), 'QSTN_SEC2', null, category1SelectBox.id);
            initCategorySelectBox($('#category3Selectbox'), 'QSTN_SEC3', null, null);

            category3SelectboxChanged();
        };

        function callbackCategory2SelectBox(cdNm) {
            if(cdNm == null) {
                return;
            }
            var category2SelectBox = getCommonCdFromcdNm(cdNm);

            initCategorySelectBox($('#category1Selectbox'), 'QSTN_SEC1', category2SelectBox.hardCodedParentId, null);
            initCategorySelectBox($('#category3Selectbox'), 'QSTN_SEC3', null, category2SelectBox.id);

            category3SelectboxChanged();
        };

        function callbackCategory3SelectBox(cdNm) {
            if(cdNm == null) {
                return;
            }
            var category3SelectBox = getCommonCdFromcdNm(cdNm);
            initCategorySelectBox($('#category1Selectbox'), 'QSTN_SEC1', category3SelectBox.hardCodedParentParentId, null);
            initCategorySelectBox($('#category2Selectbox'), 'QSTN_SEC2', category3SelectBox.hardCodedParentId, null);

            category3SelectboxChanged(cdNm);
        };

        //Temporary function -> will change with ng-directive
        function category3SelectboxChanged(cdNm) {
            if(cdNm != null) {
                $('#submitButton').removeAttr("disabled");
            } else {
                $('#submitButton').attr("disabled", "disabled");
            }
        }
    }
})();
