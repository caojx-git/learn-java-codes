$(document).ready(function () {

    var loginObj = {};

    var validate = function () {
        if ($("#userId").val() == "" || $("#userId").val() == null) {
            alert("请输入用户编号");
            return false;
        }
        if ($("#userPassword").val() == "" || $("#userPassword").val() == null) {
            alert("请输入密码");
            return false;
        }
        return true;
    };


    /**
     * 登录
     * */
    var login = function () {
        $("#sigininForm").submit();
    };

    /**
     * 忘记密码
     */
    var forget = function () {

    };

    var initEvent = function () {
        $("#loginBtn").bind("click", function () {
            if (validate()) {
                login();
            }
        });
    };

    var init = function () {
        initEvent();
    };

    init();
})
;