<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 16-12-30
  Time: 下午6:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="/js/common/jquery-1.7.1.min.js"></script>
    <title>springmvc文件上传</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>
    <h1>文件上传</h1>
    <%--
    上传文件的时候，需要设置一下form表单的enctype：有下边3个值
    multipart/form-data 在发送前编码所有字符（默认）
    application/x-www-form-urlencoded 不对字符编码，在使用包含文件上传空间的表单时，必须使用该值
    text/plain 空格转换为“+”加号，但不对特殊字符编码
    --%>
    <form  name="userForm" enctype="multipart/form-data" action="/file/upload2" method="post">
        选择文件：<input type="file" name="file"/>
        <input type="submit" value="上传">
    </form>
</body>
</html>
