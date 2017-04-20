$(document).ready(function () {
    var userInfo = {};
    var selectObj = {};

    var initParam = function () {
        userInfo = {
            userId: $('#userId').val(),
            userName: $('#userName').val(),
            userPassword: $('#userPassword').val(),
            userAge: $('#userAge').val(),
            userGender: $('#userGender').val(),
            userEmail: $('#userEmail').val(),
            userAddress: $('#userAddress').val(),
            userPhoneNumber: $('#userPhoneNumber').val(),
            userCollege: $('#userCollege').val(),
            userProfession: $('#userProfession').val(),
            userClass: $('#userClass').val(),
        }
    }

    var validate = function () {
        $("#signupForm").validate({
            rules: {
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
                userCollege: "required",
            },

            message: {
                userName: "用户名不能为空",
                userPassword: "密码不能为空",
                confirm_userPassword: {
                    required: "密码不能为空",
                    equalTo: "两次输入密码不一致"
                },
                userGender: "请选择性别",
                userEmail: "请输入正确的邮箱地址格式",
                userAddress: "请输入住址",
                userPhoneNumber: "请输入您的联系方式",
                userCollege: "请选择所属的学院"
            }
        });
    }

    var getSysBaseType = function (codeType, codeId) {
        $.ajax({
            url: "/login/getSysBaseType",
            type: "post",
            data: {
                codeType: codeType,
                codeId: codeId
            },
            success: function (data) {
                if (data.status == "0") {
                    $("#userProfession").empty();
                    selectObj.userProfessions = data.result;
                    for (var i = 0; i < selectObj.userProfessions.length; i++) {
                        var id = selectObj.userProfessions[i].codeId;
                        var name = selectObj.userProfessions[i].codeName;
                        var opt = "<option value='" + id + "'>" + name + "</option>";
                        $("#userProfession").append(opt);
                    }
                } else {
                    alert(data.message);
                }
            }
        });
    }

    var initView = function () {
        $.ajax({
            url: "/login/getSysBaseType",
            type: "post",
            data: {
                codeType: "0002",
                codeId: ""
            },
            success: function (data) {
                if (data.status == "0") {
                    $("#userCollege").empty();
                    selectObj.userColleges = data.result;
                    $.each(selectObj.userColleges, function (key, value) {
                        var opt = "<option value='" + value.codeId + "'>" + value.codeName + "</option>";
                        $("#userCollege").append(opt);
                    });
                    getSysBaseType(selectObj.userColleges[0].codeId,"");
                } else {
                    alert(data.message);
                }
            }
        });
    }

    var initEvent = function () {
        $("#registerBtn").bind("click", function () {
            initParam();
            validate();
            $.ajax({
                url: "/login/register",
                type: "post",
                data: userInfo,
                success: function (data) {
                    if (data.status == "0") {
                        alert("注册失败");
                    }
                }
            });
        });

        $("#userCollege").bind("change", function () {
            getSysBaseType($("#userCollege").val(), "");
        })
    }

    var init = function () {
        initView();
        initEvent();
    }
    init();
});