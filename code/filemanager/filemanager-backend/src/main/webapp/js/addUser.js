$(document).ready(function () {

    var userInfo = {};

    /**
     * 获取表单数据
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
    }

    /**
     * 清理表单数据
     */
    var clearFormValue = function () {
        $("#userId").val("");
        $("#userName").val("");
        $("#userPassword").val("");
        $("#confirm_userPassword").val("");
        $("#userAge").val("");
        $("input[type=radio]:checked").val("");
        $("#userEmail").val("");
        $("#userAddress").val("");
        $("#userPhoneNumber").val("");
        $("#collegeId").val("");
        $("#manager").val("");
    }

    /**
     * 验证表单数据
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
                userPhoneNumber: "required",
                collegeId: "required",
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
                userPhoneNumber: "请输入您的联系方式",
                collegeId: "请选择所属的学院"
            },
            submitHandler: function () {
                if (window.confirm("是否新增用户?")) {
                    $.ajax({
                        url: "/filter/userManager/addUser.do",
                        type: "post",
                        data: userInfo,
                        success: function (data) {
                            if (data.status == "0") {
                                alert("添加成功");
                                clearFormValue();
                            } else {
                                alert(data.message);
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 初始化事件
     */
    var initEvent = function () {
        //保存用户
        $("#saveBtn").bind("click", function () {
            getDate();
            validate();
        });

        //取消
        $("#cancelBtn").click(function () {
            history.back(-1);
        });
    };

    /**
     * 初始化
     */
    var init = function () {
        initEvent();
    }
    init();
});