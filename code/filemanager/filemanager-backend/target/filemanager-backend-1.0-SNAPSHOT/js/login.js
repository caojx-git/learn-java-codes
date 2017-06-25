$(document).ready(function () {

    var userInfo = {};

    /**
     * 获取登录表单数据
     */
    var getDate = function () {
        userInfo = {
            userId: $("#userId").val(),
            userPassword: $("#userPassword").val(),
        }
    }

    /**
     * 验证数据
     */
    var validate = function () {
        if (userInfo.userId == "" || userInfo.userId == null) {
            alert("请输入用户编号");
            return false;
        }
        if (userInfo.userPassword == "" || userInfo.userPassword == null) {
            alert("请输入密码");
            return false;
        }
        return true;
    };

    /**
     * 初始化事件
     */
    var initEvent = function () {
        //登录
        $("#loginBtn").bind("click", function () {
            getDate();
            if (validate()) {
                $.ajax({
                    url: "/user/login.do",
                    type: "post",
                    data: userInfo,
                    success: function (data) {
                        if (data.status == 0) {
                            location.href = "/filter/file/listFileInfo.do";
                        } else {
                            alert(data.message);
                        }
                    }
                });
            }
        });

        ///忘记密码
        $("#forgetBtn").bind("click",function () {
            var userId = $("#userId").val();
            if(userId == "" || userId==null || userId ==undefined){
                alert("请填写用户账号");
            }else {
                location.href="/findPassword/viewMailPage.do?userId="+userId;
            }
        })
    };

    /**
     * 初始化
     */
    var init = function () {
        initEvent();
    };

    init();
});