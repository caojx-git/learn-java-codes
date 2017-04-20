/**
 * Created by caojx on 17-4-20.
 */
$(document).read(function () {

    var initEvent = function () {
       /* $("#queryBtn").click(function () {
            $.ajax({
                url:"/userManager/userInfoList.do",
                type:"post",
                data:{
                    userId:$("#userId").val(),
                    userName:$("#userName").val(),
                    collegeId:$("#collegeId").val(),
                },
                success:function (data) {
                    if(data.status == 0){

                    }else {
                        alert(data.message);
                    }
                }
            });
        });*/
    };

    var init = function () {
      initEvent();
    };
});