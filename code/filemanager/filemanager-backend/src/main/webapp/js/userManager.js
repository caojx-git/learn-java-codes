/**
 * Created by caojx on 17-4-20.
 */

/*
* 实现翻页功能
*/
function changeCurrentPage(currentPage){
    $("#currentPage").val(currentPage).val();
    $("#userManagerForm").submit();
}



