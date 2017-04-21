/**
 * Created by caojx on 17-4-20.
 */
$(document).ready(function () {

    var selectObj = {};

    var initParams = function () {
      /*  $.ajax({
            url: "/userManager/loadCollegeList.do",
            type: "post",
            success: function (data) {
                if (data.status == 0) {
                    var collegeList = data.collegeList;
                    $.each(data.collegeList, function (key, value) {
                        var id = value.codeId;
                        var name = value.codeName;
                        var opt = "<option value=" + id + ">" + name + "</option>";
                        $("#collegeId").append(opt);
                    });
                }
            }
        });*/
    };

    var initView = function () {
        initParams();
    };

    var initEvent = function () {

       /* $("#queryBtn").click(function () {
            $.ajax({
                url: "/userManager/userInfoList.do",
                type: "post",
                data: {
                    userId: $("#userId").val(),
                    userName: $("#userName").val(),
                    collegeId: $("#collegeId").val(),
                },
                success: function (data) {
                    if (data.status == 0) {
                        var userInfoList = data.userInfoList;
                        $.each(userInfoList, function (key, value) {
                            var item = "<tr >" +
                                "<td class='text-left'><div class='icon iconfont icon-touxiang1'></div></td>" +
                                "<td class='text-left'>" + value.userId + "</td>" +
                                "<td class='text-center'>" + value.userName + "</td>" +
                                "<td class='text-center'>" + value.collegeId + "</td>" +
                                "<td class='text-center'>" +
                                " <a href='/userManager/editUserInfo.do?userId='"+value.userId+"> " +
                                "<div class='icon iconfont icon-bianji'></div></a>" +
                                "</td > " +
                               "<td class='text-center'>" +
                                "<a><div class='icon iconfont icon-shanchu'></div></a>" +
                                "</td>" +
                            "</tr>"
                            $("#tbody").append(item);
                        });
                    } else {
                        alert(data.message);
                    }
                }
            });
        });*/

    };

    var init = function () {
        initView()
        initEvent();
    };
    init();
});