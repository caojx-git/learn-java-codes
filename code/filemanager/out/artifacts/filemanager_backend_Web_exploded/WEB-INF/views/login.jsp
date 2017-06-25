<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 17-4-11
  Time: 下午10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <meta charset="utf-8"/>
    <title></title>
</head>
<link rel="stylesheet" href="/css/style.css"/>
<link rel="stylesheet" href="/iconfont/iconfont.css"/>
<script type="text/javascript" src="/js/common/jquery.min.js"></script>
<script type="text/javascript" src="/js/login.js"></script>
<body>
<div class="box">
    <div class="nav">e</div>
    <div class="boxside">

        <div class="login-box">
            <div class="con">
                <span class="iconfont icon-touxiang"></span>
                <input type="text" placeholder="请输入用户名"/>
            </div>
            <div class="con">
                <span class="iconfont icon-suo"></span>
                <input type="text" placeholder="请输入用户名"/>
            </div>
            <p class="forget" onclick="">忘记登录密码</p>
            <div class="btn-blue" onclick="">
                <p>登录</p>
            </div>
            <a href="/login/registerPage">
            <p class="forget btn-blue text-center">免费dsasd注册</p>
            </a>
        </div>

    </div>
</div>

</body>
</html>

