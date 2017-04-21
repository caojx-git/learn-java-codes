<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 17-4-20
  Time: 上午9:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>新增用户</title>
    <link rel="icon" href="/images/xnxy.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="/iconfont/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="/js/common/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/js/common/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/addUser.js"></script>
</head>
<body>
<h3 class="form-title col-sm-offset-1">新增用户</h3>
<hr/>
<form class="form-horizontal" role="form" id="signupForm">
    <div class="form-group">
        <label class="col-sm-2 control-label">用户编号</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="userId" name="userId"
                   placeholder="请输入用户编号">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">姓名</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="userName" name="userName"
                   placeholder="请输入名字">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">密码</label>
        <div class="col-sm-4">
            <input type="password" class="form-control" id="userPassword" name="userPassword"
                   placeholder="密码">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">确认密码</label>
        <div class="col-sm-4">
            <input type="password" class="form-control" id="confirm_userPassword" name="confirm_userPassword"
                   placeholder="确认密码">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">年龄</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="userAge" name="userAge"
                   placeholder="年龄">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">性别</label>
        <div class="col-sm-offset-1">
            <div class="checkbox">
                <label>
                    <input type="radio" name="gender" value="0" checked> 男
                    <input type="radio" name="gender" value="1"> 女
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">邮箱</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="userEmail" name="userEmail"
                   placeholder="邮箱">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">住址</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="userAddress" name="userAddress"
                   placeholder="住址">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">手机号</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="userPhoneNumber" name="userPhoneNumber"
                   placeholder="手机号">
        </div>
    </div>
    <div class="from-group form-inline">
        <label class="col-sm-2  control-label">学院</label>
        <select class="col-sm-1 form-control" id="collegeId" name="collegeId">
            <c:forEach items="${sessionScope.collegeList}" var="collegeInfo">
                <option value="${collegeInfo.codeId}">${collegeInfo.codeName}</option>
            </c:forEach>
        </select>
        <label class=" col-sm-1 control-label">管理员</label>
        <select class="col-sm-1 form-control" id="manager" name="manager">
            <option value="0" checked>否</option>
            <option value="1">是</option>
        </select>

    </div>
    <div class="form-group form-inline col-sm-12">
        <div class="btn-register">
            <button type="button" class="btn btn-primary" id="cancelBtn">取消</button>
            <button type="submit" class="btn btn-primary" id="saveBtn">注册</button>
        </div>
    </div>
</form>
</body>
</html>
