/**
 * Created by caojx on 17-4-29.
 */
$(document).ready(function () {

    /**
     * 验证
     */
    var validate = function () {
        var newPassword = $("#newPassword").val();
        var confirm_password = $("#confirm_password").val();
        if (newPassword == null || newPassword == "" || newPassword == undefined) {
            alert("密码不能为空");
            return false;
        }
        if (confirm_password == null || confirm_password == "" || confirm_password == undefined) {
            alert("确认密码不能为空");
            return false;
        }
        if (newPassword != confirm_password) {
            alert("两次输入密码不一致");
            return false;
        }
        return true;
    };

    /**
     * 初始化事件
     */
    var initEvent = function () {
        $("#savaBtn").click(function () {
            if (validate()) {
                if (window.confirm("是否确认修改保存?")) {
                    $.ajax({
                        url: "/findPassword/updatePassword.do",
                        type: "post",
                        data: {
                            "userId": $("#userId").val(),
                            "newPassword": $("#newPassword").val()
                        },
                        success: function (data) {
                            if (data.status == 0) {
                                //成功跳转到登录页面
                                alert("修改成功");
                                location.href = "/user/loginPage.do"
                            } else {//出错了
                                alert(data.message);
                            }
                        }

                    });
                }
            }
        });
    };
    /**
     * 初始化
     */
    var init = function () {
        initEvent();
    };

    init();
});
