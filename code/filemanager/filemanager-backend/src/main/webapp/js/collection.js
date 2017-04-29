/**
 * Created by caojx on 17-4-26.
 */

$(document).ready(function () {
    var initView = function () {
        var currentPage = $("#currentPage").val();
        if (currentPage == "" || currentPage == null || currentPage == undefined) {
            $("#currentPage").val(1);
        }
    };

    var init = function () {
        initView();
    };

    init();
});

/**
 * 删除收藏的文件
 */
function deleteCollectionFile(userId,fileId) {
    if(window.confirm("是否删除这条收藏")){
        $.ajax({
            url:"/filter/fileCollection/removeFileCollection.do",
            type:"post",
            data:{
                "userId":userId,
                "fileId":fileId,
            },
            success:function (data) {
                if(data.status == 0){
                    alert("删除成功");
                    location.href="/filter/fileCollection/listCollectionInfo.do";
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
function changeCurrentPage(currentPage) {
    $("#currentPage").val(currentPage).val();
    $("#collectionForm").submit();
}