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
    <script type="text/javascript" src="/js/common/jquery-1.7.1.min.js"></script>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8">
    <script type="text/javascript">
        function del(id){
            $.get("/user3/delUser3?id=" + id,function(data){
                if("success" == data.result){
                    alert("删除成功!");
                    window.location.reload();//刷新界面
                }else{
                    alert("删除失败!")
                }
            });
        }
    </script>
</head>
<body>
    <table border="1">
        <tbody>
            <tr>
                <th>姓名</th>
                <th>年龄</th>
                <th>编辑</th>
            </tr>
        <c:if test="${!empty users}">
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.userName}</td>
                    <td>${user.age}</td>
                    <td>
                        <a href="/user3/getUser3?id=${user.id}">编辑</a>
                        <a href="javascript:del('${user.id}')">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</body>
</html>
