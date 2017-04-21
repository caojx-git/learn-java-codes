<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 17-4-20
  Time: 上午10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>湘南云库</title>
    <link rel="icon" href="/images/xnxy.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/iconfont/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="/js/common/jquery-1.8.3.min.js"></script>
  <%--  <script type="text/javascript" src="/js/userManager.js"></script>--%>
</head>
<body>
<div class="home">
    <div class="left-side">
        <div class="menu-lists">
            <ul>
                <ul>
                    <a>
                        <div class="icon iconfont icon-touxiang"></div>
                        <div class="name">${sessionScope.userInfo.userName}</div>
                    </a>
                </ul>
                <li>
                    <a href="myCollection.html">
                        <div class="icon iconfont icon-shoucang"></div>
                        <div class="name">收藏</div>
                    </a>
                </li>
                <li>
                    <a href="index.html">
                        <div class="icon iconfont icon-file"></div>
                        <div class="name">文件</div>
                    </a>
                </li>
                <li>
                    <a href="/userManager/userManagerPage.do">
                        <div class="icon iconfont icon-yonghuguanli1"></div>
                        <div class="name">用户管理</div>
                    </a>
                </li>
                <li style="margin-top: 140px;">
                    <a href="#">
                        <div style="font-size: 25px;padding-left: 10px" class="icon iconfont icon-gengduo-copy"></div>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <div class="file-list-side">
        <div class="file-search-box">
            <form class="form form-inline" action="/userManager/userInfoList.do">
                <div class="row">
                    <div class="form-group">
                        <label for="userId">用户编号</label>
                        <input type="text" class="form-control" id="userId" name="userId" placeholder="请输入用户编号">
                    </div>
                    <div class="form-group">
                        <label for="userName">用户名</label>
                        <input type="text" class="form-control" id="userName" name="userName" placeholder="请输入用户名称">
                    </div>
                    <div class="form-group">
                        <label for="collegeId">学院</label>
                        <select id="collegeId" name="collegeId">
                            <option value="">所有</option>
                            <c:forEach items="${sessionScope.collegeList}" var="item">
                                <option value="${item.codeId}">${item.codeName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="manager">管理员</label>
                        <select id="manager" name="manager">
                            <option value="">所有</option>
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <input type="submit" class="btn btn-primary" value="查询"/>
                        <a class="btn btn-primary" href="/userManager/addUserPage.do">新增</a>
                    </div>
                </div>
            </form>
        </div>
        <br/>
        <div class="file-list-box">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="col-md-2 text-left"></th>
                    <th class="col-md-3 text-left">用户编号</th>
                    <th class="col-md-1 text-center">用户名</th>
                    <th class="col-md-1 text-center">学院</th>
                    <th class="col-md-1 text-center">管理员</th>
                    <th class="col-md-1 text-center">编辑</th>
                    <th class="col-md-1 text-center">查看</th>
                    <th class="col-md-1 text-center">删除</th>
                </tr>
                </thead>
                <tbody id="tbody">
                <c:forEach items="${requestScope.userInfoList}" var="userInfo">
                    <tr>
                        <td class="text-left">
                            <div class="icon iconfont icon-touxiang1"></div>
                        </td>
                        <td class="text-left">
                              ${userInfo.userId}
                        </td>
                        <td class="text-center">
                                ${userInfo.userName}
                        </td>
                        <td class="text-center">
                            <c:forEach items="${sessionScope.collegeList}" var="item">
                               <c:if test="${item.codeId == userInfo.collegeId}">${item.codeName}</c:if>
                            </c:forEach>
                        </td>
                        <td class="text-center">
                          <c:if test="${userInfo.manager == 0}">否</c:if>
                          <c:if test="${userInfo.manager == 1}">是</c:if>
                        </td>
                        <td class="text-center">
                            <a href="/userManager/editUserInfo.do?userId=${userInfo.userId}">
                                <div class="icon iconfont icon-bianji"></div>
                            </a>
                        </td>
                        <td class="text-center">
                            <a href="/userManager/viewUserPage.do?userId=${userInfo.userId}">
                                <div class="icon iconfont icon-shanchu"></div>
                            </a>
                        </td>
                        <td class="text-center">
                            <a href="/userManager/removeUserInfo.do?userId=${userInfo.userId}">
                                <div class="icon iconfont icon-shanchu"></div>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class='page fix'>
            共 <b>4</b> 条
            <a href='###' class='first'>首页</a>
            <a href='###' class='pre'>上一页</a>
            当前第<span>1/1</span>页
            <a href='###' class='next'>下一页</a>
            <a href='###' class='last'>末页</a>
            跳至&nbsp;<input type='text' value='1' class='allInput w28'/>&nbsp;页&nbsp;
            <a href='###' class='go'>GO</a>
        </div>
    </div>
</div>
</body>
</html>
