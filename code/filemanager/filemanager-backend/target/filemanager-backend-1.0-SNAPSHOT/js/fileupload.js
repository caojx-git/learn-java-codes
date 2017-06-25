/**
 * Created by caojx on 17-4-18.
 */
$(document).ready(function () {

    /**
     * 验证表单信息
     */
    var validate = function () {
        if ($("#file").val() == "" || $("#file").val() == null) {
            alert("请选择需要上传的文件");
            return false;
        }
        if ($("#notes").val() == "" || $("#notes").val() == null) {
            alert("请输入文件的描述信息");
            return false;
        }
        return true;
    };
/*
    var initMultiSelect = function () {
        $('.multiselect').multiselect({
            enableClickableOptGroups: true,
            enableFiltering: true,
            includeSelectAllOption: true,
            enableCollapsibleOptGroups: true,
        });
    }

    /!**
     * 初始化视图
     *!/
    var initView = function () {
        initMultiSelect();
    };*/

    /**
     * 初始化事件
     */
    var initEvent = function () {
        $("#uploadBtn").click(function () {
           if(validate()){
               $("#fileUploadForm").submit();
           }
        });
    };

    /**
     * 初始化
     */
    var init = function () {
        //initView();
        initEvent();
    };

    init();

});