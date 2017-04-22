<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 17-4-11
  Time: 下午10:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <link rel="icon" href="/images/xnxy.ico" type="image/x-icon"/>
    <title>登录</title>
</head>
<link rel="stylesheet" href="/css/style.css"/>
<link rel="stylesheet" href="/iconfont/iconfont.css"/>
<script type="text/javascript" src="/js/common/jquery-1.8.3.min.js"></script>
<body>
<div class="box">
    <div class="boxside">
        <form id="sigininForm">
            <div class="login-box">
                <div class="con">
                    <span class="iconfont icon-touxiang"></span>
                    <input type="text" id="userId" name="userId" placeholder="用户编号"/>
                </div>
                <div class="con">
                    <span class="iconfont icon-suo"></span>
                    <input type="password" id="userPassword" name="userPassword" placeholder="请输入密码"/>
                </div>
                <p class="forget" id="forgetBtn">忘记登录密码</p>
                <div class="btn-blue" id="loginBtn">登录</div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="/js/login.js"></script>
</body>
</html>

