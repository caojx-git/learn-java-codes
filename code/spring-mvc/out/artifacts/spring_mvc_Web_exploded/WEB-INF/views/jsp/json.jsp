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
    <title>json的数据传递</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#add").click(function () {
                var userName = $("#userName").attr("value")
                var age = $("#age").attr("value");
                var user = {
                    "userName":userName,
                    "age":age
                };

                $.ajax({
                    url:"/data/addUserJson",
                    type:"post",
                    data:user,
                    success:function (data) {
                        alert("userName"+data.userName+"-----age:"+data.age);
                    }
                });
            });
        });
    </script>
</head>
<body>
    <h1>添加用户</h1>
    姓名：<input type="text" id="userName"/>
    年龄：<input type="text" id="age"/>
    <input type="button" id="add" value="添加">
</body>
</html>
