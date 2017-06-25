<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 17-4-20
  Time: 上午9:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <title>编辑用户</title>
    <link rel="icon" href="/images/xnxy.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="/iconfont/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="/js/common/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="/js/common/jquery.validate.min.js"></script>
</head>
<body>
<h3 class="form-title col-sm-offset-1">编辑用户</h3>
<hr/>
<form class="form-horizontal" role="form" id="signupForm">
    <div class="form-group">
        <label class="col-sm-2 control-label">用户编号</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="userId" name="userId" readonly="true"
                   value="${requestScope.userInfo.userId}"
                   placeholder="请输入用户编号">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">姓名</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="userName" name="userName" readonly="true"
                   value="${requestScope.userInfo.userName}" placeholder="请输入用户姓名">
        </div>
    </div>
<%--        <div class="form-group">
            <label class="col-sm-2 control-label">密码</label>
            <div class="col-sm-4">
                <input type="password" class="form-control" id="userPassword" name="userPassword"
                       value="${requestScope.userInfo.userPassword}"
                       placeholder="密码">
            </div>
        </div>--%>
    <div class="form-group">
        <label class="col-sm-2 control-label">年龄</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="userAge" name="userAge"
                   value="${requestScope.userInfo.userAge}"
                   placeholder="年龄">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">性别</label>
        <div class="col-sm-offset-1">
            <div class="checkbox">
                <label>
                    <c:if test="${requestScope.userInfo.userGender == 0}">
                        <input type="radio" name="gender" value="0" checked="checked"> 男
                        <input type="radio" name="gender" value="1"> 女
                    </c:if>
                    <c:if test="${requestScope.userInfo.userGender == 1}">
                        <input type="radio" name="gender" value="0"> 男
                        <input type="radio" name="gender" value="1" checked="checked"> 女
                    </c:if>
                </label>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">邮箱</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="userEmail" name="userEmail"
                   value="${requestScope.userInfo.userEmail}"
                   placeholder="邮箱">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">住址</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="userAddress" name="userAddress"
                   value="${requestScope.userInfo.userAddress}"
                   placeholder="住址">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">手机号</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="userPhoneNumber" name="userPhoneNumber"
                   value="${requestScope.userInfo.userPhoneNumber}"
                   placeholder="手机号">
        </div>
    </div>
        <div class="from-group form-inline">
            <label class="col-sm-2 control-label">学院</label>
            <select class="col-sm-1 form-control" id="collegeId" name="collegeId">
                <c:forEach items="${applicationScope.collegeList}" var="item">
                    <c:if test="${requestScope.userInfo.collegeId == item.codeId}">
                        <option value="${item.codeId}" selected>${item.codeName}</option>
                    </c:if>
                </c:forEach>
            </select>
            <label class=" col-sm-1 control-label">管理员</label>
            <select class="col-sm-1 form-control" id="manager" name="manager">
                <c:choose>
                    <c:when test="${sessionScope.userInfo.managerType ==1 }">
                        <option value="1" selected>是</option>
                        <option value="0">否</option>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${requestScope.userInfo.manager == 1}">
                            <option value="1" selected>是</option>
                        </c:if>
                        <c:if test="${requestScope.userInfo.manager == 0}">
                            <option value="0" selected>否</option>
                        </c:if>
                    </c:otherwise>
                </c:choose>

            </select>
        </div>
    <div class="form-group form-inline col-sm-12">
        <div class="btn-register">
            <button type="button" class="btn btn-primary" id="cancelBtn">返回</button>
            <button type="submit" class="btn btn-primary" id="saveBtn">保存</button>
        </div>
    </div>
</form>
</body>
<script type="text/javascript" src="/js/editUser.js"></script>
</html>
