/**
 * Created by caojx on 17-4-18.
 */
$(document).ready(function () {

    var userInfo = {};
    var selectArray = new Array();

    var initMultiSelect = function () {
        $('.multiselect').multiselect({
            enableClickableOptGroups: true,
            enableFiltering: true,
            includeSelectAllOption: true,
            enableCollapsibleOptGroups: true,
        });
    }

    var initView = function () {
        initMultiSelect();
    };

    var initEvent = function () {

    };

    var init = function () {
        initView();
        initEvent();
    };

    init();

});