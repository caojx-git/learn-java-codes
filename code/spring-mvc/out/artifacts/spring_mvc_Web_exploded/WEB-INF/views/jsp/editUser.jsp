<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 16-12-30
  Time: 下午5:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>编辑用户</title>
</head>
<body>
<h1>添加用户</h1>
<form action="/user3/updateUser3">
    <input type="hidden" name="id" value="${user.id}">
    姓名：<input type="text" name="userName" value="${user.userName}"/>
    年龄：<input type="text" name="age" value="${user.age}"/>
    <input type="submit" value="更新">
</form>
</body>
</html>
