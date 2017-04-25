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
<%--<link rel="stylesheet" href="/css/style.css"/>--%>
<link rel="stylesheet" href="/iconfont/iconfont.css"/>
<script type="text/javascript" src="/js/common/jquery-1.8.3.min.js"></script>
<style type="text/css">
    * {
        margin: 0;
        padding: 0;
        font-family: "微软雅黑";
        font-size: 14px;
        font-color: #3e3e3e;
    }

    .error {
        color: red;
    }

    ul, li {
        list-style: none;
    }

    .box {
        background: url(/images/8.jpg);
        width: 100%;
        height: 800px;
    }

    .boxside {
        background: yellowgreen;
    }

    .login-box {
        background: lightblue;
        padding: 20px;
        position: fixed;
        right: 100px;
        top: 100px
    }

    .list-tab {
        border-bottom: 1px #efefef solid;
    }

    .list-tab p {
        display: inline-block;
        padding: 10px 40px;
    }

    .list-tab p:first-child {
        border-bottom: 3px #24AFDC solid;
        color: #24AFDC
    }

    .con {
        background: #24AFDC;
        padding-left: 50px;
        padding-right: 25px;
        margin-top: 20px;
        position: relative;
    }

    .con input {
        width: 100%;
        padding: 10px;
    }

    span {
        display: block;
        position: absolute;
        left: 15px;
        top: 10px;
        font-size: 20px;
        color: #fff;
    }

    .forget {
        display: block;
        text-align: right;
        padding: 20px 0px;
        color: #555;
    }

    .btn-blue {
        padding: 10px;
        margin-bottom: 15px;
        background-color: #24AFDC;
        text-align: center;
        border-radius: 6px;
        color: #fff;
        cursor: pointer;
    }

    .text-center {
        text-align: center;
        color: #666;
    }
</style>
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

