<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 16-12-30
  Time: 下午5:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户管理</title>
</head>
<body>
本次请求的方法是${result}
    <table>
        <tbody>
            <tr>
                <th>姓名</th>
                <th>年龄</th>
            </tr>

        <c:if test="${!empty users}">
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.userName }</td>
                    <td>${user.age }</td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</body>
</html>
