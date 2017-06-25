/**
 * Created by caojx on 17-4-29.
 */
$(document).ready(function () {

    var userInfo = {};

    /**
     * 初始化事件
     */
    var initEvent = function () {
        $("#sendBtn").click(function () {
            $.ajax({
                url:"/findPassword/sendMail.do",
                data:{
                    "userId":$("#userId").val(),
                    "userEmail":$("#userEmail").val()
                },
                type:"post",
                success:function (data) {
                    if(data.status == 0){
                        alert(data.message);
                    }else {
                        alert("邮件发送失败");
                    }
                }
            });
        });
    };

    var init = function () {
        initEvent();
    };
    init();
});