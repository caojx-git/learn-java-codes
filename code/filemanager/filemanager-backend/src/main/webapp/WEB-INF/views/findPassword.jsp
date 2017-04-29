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
    <title>找回密码</title>
    <link rel="icon" href="/images/xnxy.ico" type="image/x-icon"/>
    <script type="text/javascript" src="/js/common/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
    <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h4 class="control-label col-sm-offset-5" style="margin-top: 20px" >找回密码</h4>
<hr/>
<form class="form-horizontal col-sm-offset-3" role="form" style="margin-top: 100px">
    <div class="form-group">
        <label for="userId" class="col-sm-2 control-label">用户编号</label>
        <div class="col-sm-3">
            <input type="text" class="form-control" id="userId" value="${requestScope.userInfo.userId}" readonly="readonly">
        </div>
    </div>
    <div class="form-group">
        <label for="newPassword" class="col-sm-2 control-label">新密码</label>
        <div class="col-sm-3">
            <input type="password" class="form-control" id="newPassword">
        </div>
    </div>
    <div class="form-group">
        <label for="confirm_password" class="col-sm-2 control-label">确认密码</label>
        <div class="col-sm-3">
            <input type="password" class="form-control" id="confirm_password">
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="button" class="btn btn-primary" id="savaBtn">确定</button>
        </div>
    </div>
</form>
</body>
<script type="text/javascript" src="<%=basePath%>/js/findPassword.js"></script>
</html>
