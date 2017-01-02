<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 16-12-28
  Time: 下午11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>HelloWorldController</title>
</head>
<body>
    hello springmvc

    <h1>返回的数据</h1>
    <c:forEach items="${map}" var="m">
        ${m.key}----${m.value}<br/>
    </c:forEach>
</body>
</html>
