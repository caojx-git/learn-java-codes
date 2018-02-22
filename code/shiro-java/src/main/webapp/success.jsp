<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 2018/2/21
  Time: 下午7:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--使用Shiro标签--%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>登录成功</title>
</head>
<body>
登录成功，欢迎

<%--测试shiro标签--%>

<%--hasRole标签 如果当前Subject有角色将显示body体内容--%>
<shiro:hasRole name="admin">
    hasRole标签 如果当前Subject有角色将显示body体内容<br>
    欢迎有admin角色的用户！<shiro:principal/>
</shiro:hasRole>

<%--hasPermission标签 如果当前 Subject 有权限将显示 body 体内容--%>
<shiro:hasPermission name="student:create">
    hasPermission标签 如果当前 Subject 有权限将显示 body 体内容<br>
    欢迎有student:create权限的用户！<shiro:principal/>
</shiro:hasPermission>

</body>
</html>
