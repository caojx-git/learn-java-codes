<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 2018/2/21
  Time: 下午7:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>登录</title>
</head>
<body>

<form action="/user/login.do" method="post">
    userName:<input type="text" name="userName"><br>
    password:<input type="password" name="password"><br>
    <input type="submit" value="登录">${errorMsg}
</form>
</body>
</html>
