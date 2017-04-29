$(document).ready(function () {

    /**
     * 用户信息对象
     */
    var userInfo = {};

    /**
     * 获取用户信息
     */
    var getDate = function () {
        userInfo = {
            userId: $("#userId").val(),
            userName: $("#userName").val(),
            userPassword: $("#userPassword").val(),
            userAge: $("#userAge").val(),
            userGender: $("input[type=radio]:checked").val(),
            userEmail: $("#userEmail").val(),
            userAddress: $("#userAddress").val(),
            userPhoneNumber: $("#userPhoneNumber").val(),
            collegeId: $("#collegeId").val(),
            manager: $("#manager").val(),
        }
    };

    /**
     * 验证用户信息
     */
    var validate = function () {
        $("#signupForm").validate({
            rules: {
                userId: "required",
                userName: "required",
                userPassword: "required",
                confirm_userPassword: {
                    required: true,
                    equalTo: "#userPassword"
                },
                userAge: {
                    required: true,
                    digits: true,
                    max: 140
                },
                userGender: "required",
                userEmail: {
                    email: true
                },
                userAddress: "required",
                userPhoneNumber:  {
                    required:true,
                    maxlength:11,
                    digits:true
                }
            },

            messages: {
                userId: "用户编号不能为空",
                userName: "用户名不能为空",
                userPassword: "密码不能为空",
                confirm_userPassword: {
                    required: "密码不能为空",
                    equalTo: "两次输入密码不一致"
                },
                userAge: "用户年龄不能为空",
                userGender: "请选择性别",
                userEmail: "请输入正确的邮箱地址格式",
                userAddress: "请输入住址",
                userPhoneNumber: "联系方式不合法或为空",
            },
            submitHandler: function () {
                if (window.confirm("是否要修改用户信息?")) {
                    $.ajax({
                        url: "/filter/userManager/updateUserInfo.do",
                        type: "post",
                        data: userInfo,
                        success: function (data) {
                            if (data.status == "0") {
                                alert("保存成功");
                            } else {
                                alert(data.message);
                            }
                        }
                    });
                }
            }
        });
    };

    /**
     * 初始化事件
     */
    var initEvent = function () {
        $("#saveBtn").bind("click", function () {
            getDate();
            validate();
        });

        $("#cancelBtn").click(function () {
            location.href = "/filter/file/listFileInfo.do";
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