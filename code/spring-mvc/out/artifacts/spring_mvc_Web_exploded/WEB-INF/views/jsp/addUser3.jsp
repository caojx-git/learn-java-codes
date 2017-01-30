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
    <title>springmvc参数传递添加用户</title>
    <script type="text/javascript">
        function addUser() {
            var form = document.forms[0];
            form.action = "/user3/addUser3";
            form.method = "post";
            form.submit();
        }
    </script>
</head>
<body>
<h1>添加用户</h1>
<form action="">
    姓名：<input type="text" name="userName"/>
    年龄：<input type="text" name="age"/>

    <input type="submit" value="添加" onclick="addUser()">
</form>
</body>
</html>
