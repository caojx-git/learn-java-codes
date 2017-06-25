<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 17-4-19
  Time: 下午1:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>文件上传</title>
    <meta charset="UTF-8">
    <link rel="icon" href="/images/xnxy.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="/iconfont/iconfont.css"/>
    <script type="text/javascript" src="/js/common/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/bootstrap/js/prettify.js"></script>
    <!-- Include the plugin's CSS and JS: -->
    <script type="text/javascript" src="/bootstrap/js/bootstrap-multiselect.js"></script>
    <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="/bootstrap/css/bootstrap-multiselect.css" type="text/css"/>
    <link rel="stylesheet" href="/bootstrap/css/prettify.css" type="text/css"/>
</head>
<body>
<h3 class="form-title col-sm-offset-2">文件上传</h3>
<hr/>
<form class="form-horizontal" enctype="multipart/form-data" action="<%=basePath%>/filter/file/fileUpload.do" method="post" id="fileUploadForm">

    <input type="text" hidden="hidden" id="userId" name="userId" value="${sessionScope.userInfo.userId}">
    <input type="text" hidden="hidden" id="collegeId" name="collegeId" value="${sessionScope.userInfo.collegeId}">

    <div class="form-group">
        <label class="sr-only col-sm-2 control-label" for="file">文件输入</label>
        <input type="file" id="file" name="file">
    </div>
    <%--<div class="form-group form-inline">
        <label class="col-sm-2 control-label">不给谁看</label>
        <div class="col-sm-offset-2">
            <select class="multiselect" multiple="multiple" id="userId">
                <c:forEach items="${requestScope.userInfoList}" var="userInfo">
                    <c:forEach items="${requestScope.userCollegeList}" var="userCollege">
                        <optgroup label="${userCollege.codeName}">
                            <c:if test="${userCollege.codeId == userInfo.collegeId}">
                                <option value="${userInfo.userId}">${userInfo.userName}</option>
                            </c:if>
                        </optgroup>
                    </c:forEach>
                </c:forEach>
            </select>
        </div>
    </div>--%>
    <div class="form-group">
        <label class="col-sm-2 control-label">描述</label>
        <div class="col-sm-4">
            <textarea class="form-control" rows="3" id="notes" name="notes"></textarea>
        </div>
    </div>
    <button type="button" class="btn btn-primary col-sm-offset-2" id="uploadBtn">上传</button>
</form>
</body>
<script type="text/javascript" src="/js/fileupload.js"></script>
</html>
