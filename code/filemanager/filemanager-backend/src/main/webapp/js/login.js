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
     * @returns {boolean}
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
                    url:"/user/login.do",
                    type:"post",
                    data:userInfo,
                    success:function (data) {
                        if(data.status == 0){
                            location.href = "/user/indexPage.do";
                        }else {
                            alert(data.message);
                        }

                    }
                });
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