/**
 * Created by caojx on 17-4-20.
 */

$(document).ready(function () {

    var initView = function () {
        var currentPage = $("#currentPage").val();
        if(currentPage == "" || currentPage==null || currentPage == undefined){
            $("#currentPage").val(1);
        }
    };

    var init = function () {
        initView();
    };

    init();
});

/**
 * 删除用户信息
 */
function removeUser(userId) {
    if(window.confirm("是否删除该用户?")){
        $.ajax({
            url:"/filter/userManager/removeUserInfo.do",
            data:{
                "userId":userId
            },
            type:"post",
            success:function (data) {
                if(data.status==0){
                    alert("删除成功");
                    location.href="/filter/userManager/userManagerPage.do"
                }else {
                    alert(data.message);
                }
            }
        });
    };
}

/*
* 实现翻页功能
*/
function changeCurrentPage(currentPage){
    $("#currentPage").val(currentPage).val();
    $("#userManagerForm").submit();
}



