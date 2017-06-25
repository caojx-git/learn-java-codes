/**
 * Created by caojx on 17-4-27.
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
    }

    init();
});

/**
 * 文件收藏
 */
function collection(fileId,fileName,userId,collegeId) {
    $.ajax({
        url:"/filter/fileCollection/saveCollectionFile.do",
        type:"post",
        data:{
            "fileId":fileId,
            "fileName":fileName,
            "uploadId":userId,
            "collegeId":collegeId,
        },
        success:function (data) {
            if(data.status == 0){
                alert("收藏成功");
                location.href="/filter/file/listFileInfo.do?currentPage="+$("#currentPage").val();
            }else {
                alert(data.message);
            }
        }
    });
}

/**
 * 删除文件
 */
function deleteFile(fileId) {
    if(window.confirm("是否删除该文件")){
        $.ajax({
            url:"/filter/file/removeFile.do",
            type:"post",
            data:{
                "fileId":fileId,
            },
            success:function (data) {
                if(data.status == 0){
                    alert("删除成功");
                    location.href="/filter/file/listFileInfo.do";
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
    $("#indexForm").submit();
}
