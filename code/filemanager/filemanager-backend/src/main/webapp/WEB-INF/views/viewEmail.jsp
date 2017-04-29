<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 17-4-29
  Time: 上午9:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>用户邮箱</title>
    <link rel="icon" href="/images/xnxy.ico" type="image/x-icon"/>
    <script type="text/javascript" src="/js/common/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
    <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h4 class="control-label col-sm-offset-5" style="margin-top: 20px" >发送邮件</h4>
<hr/>
<form class="form-horizontal col-sm-offset-3" role="form" style="margin-top: 100px">
    <div class="form-group">
        <label for="userEmail" class="col-sm-2 control-label">用户邮箱</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" id="userEmail" value="${requestScope.userInfo.userEmail}" readonly="readonly">
        </div>
    </div>
    <input type="hidden" class="form-control" id="userId" value="${requestScope.userInfo.userId}" readonly="readonly">
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="button" class="btn btn-primary" id="sendBtn">发送邮件</button>
        </div>
    </div>
</form>
</body>
<script type="text/javascript" src="<%=basePath%>/js/viewEmail.js"></script>
</html>
