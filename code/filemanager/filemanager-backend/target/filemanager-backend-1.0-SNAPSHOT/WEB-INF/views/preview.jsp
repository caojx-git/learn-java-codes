<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 17-4-26
  Time: 上午9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>预览</title>
    <script type="text/javascript" src="/js/common/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/js/common/jquery.media.js"></script>
    <script type="text/javascript" src="/js/common/jquery.metadata.js"></script>
</head>
<body>
<input hidden="hidden" id="filePath" value="${requestScope.previewPath}">
<div class="view-box"></div>
<script type="text/javascript">
    $(document).ready(function () {
        var showPdf = function () {
            $('.view-box').media({
                width: $(window).width(),
                height: $(window).height(),
                autoplay: false,
                src:"/pdf/"+$("#filePath").val(),
            });
        };

        if ($("#filePath").val() != null && $("#filePath").val() != '' && $("#filePath").val() != undefined){
            setTimeout(showPdf(),5000);
        }else {
            alert("文件路径获取失败，请稍候再试一");
        }
    });
</script>
</body>
</html>
