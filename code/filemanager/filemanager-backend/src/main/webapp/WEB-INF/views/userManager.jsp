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

</head>
<body>
<div class="home">
    <div class="left-side">
        <div class="menu-lists">
            <ul>
                <ul>
                    <a href="/userManager/editUserInfo.do?userId=${sessionScope.userInfo.userId}">
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
            <form class="form form-inline" action="/userManager/userInfoList.do" id="userManagerForm" method="post">
                <input type="hidden" name="currentPage" id="currentPage" value="${page.currentPage}"/>
                <div class="row">
                    <div class="form-group">
                        <label for="userId">用户编号</label>
                        <input type="text" class="form-control" id="userId" name="userId" value="${requestScope.userId}" placeholder="请输入用户编号">
                    </div>
                    <div class="form-group">
                        <label for="userName">用户名</label>
                        <input type="text" class="form-control" id="userName" name="userName" value="${requestScope.userName}" placeholder="请输入用户名称">
                    </div>
                    <c:if test="${sessionScope.userInfo.managerType == 1}">
                        <div class="form-group">
                            <label for="collegeId">学院</label>
                            <select id="collegeId" name="collegeId">
                                <option value="">所有</option>
                                <c:forEach items="${sessionScope.collegeList}" var="item">
                                    <c:choose>
                                        <c:when test="${collegeId == item.codeId}">
                                            <option value="${item.codeId}" selected>${item.codeName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${item.codeId}">${item.codeName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="manager">管理员</label>
                            <select id="manager" name="manager">
                                <c:choose>
                                    <c:when test="${manager == 0}">
                                        <option value="">所有</option>
                                        <option value="0" selected>否</option>
                                        <option value="1">是</option>
                                    </c:when>
                                    <c:when test="${manager == 1}">
                                        <option value="">所有</option>
                                        <option value="0">否</option>
                                        <option value="1" selected>是</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="" selected>所有</option>
                                        <option value="0">否</option>
                                        <option value="1">是</option>
                                    </c:otherwise>
                                </c:choose>
                            </select>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <input type="submit" class="btn btn-primary" value="查询"/>
                        <a class="btn btn-primary" href="/userManager/addUserPage.do">新增</a>
                    </div>
                </div>
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
                                    <c:if test="${userInfo.manager == 0 || userInfo.manager == null}">否</c:if>
                                    <c:if test="${userInfo.manager == 1|| userInfo.manager == 2}">是</c:if>
                                </td>
                                <td class="text-center">
                                    <a href="/userManager/editUserInfo.do?userId=${userInfo.userId}">
                                        <div class="icon iconfont icon-bianji"></div>
                                    </a>
                                </td>
                                <td class="text-center">
                                    <a href="/userManager/viewUserPage.do?userId=${userInfo.userId}">
                                        <div class="icon iconfont icon-chakan"></div>
                                    </a>
                                </td>
                                <td class="text-center">
                                    <c:if test="${sessionScope.userInfo.userId != userInfo.userId}">
                                        <a href="/userManager/removeUserInfo.do?userId=${userInfo.userId}">
                                            <div class="icon iconfont icon-shanchu"></div>
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class='page fix'>
                    共 <b>${page.totalCount}</b> 条
                    共<b>${page.totalPage }</b>页
                    <c:if test="${page.currentPage != 1}">
                        <a href="javascript:changeCurrentPage('1')" class='first'>首页</a>
                        <a href="javascript:changeCurrentPage('${page.currentPage-1}')" class='pre'>上一页</a>
                    </c:if>
                    当前第<b>${page.currentPage}/${page.totalPage}</b>页
                    <c:if test="${page.currentPage != page.totalPage}">
                        <a href="javascript:changeCurrentPage('${page.currentPage+1}')" class='next'>下一页</a>
                        <a href="javascript:changeCurrentPage('${page.totalPage}')" class='last'>末页</a>
                    </c:if>
                    跳至&nbsp;<input id="currentPageText" type='text' value='${page.currentPage}' class='allInput w28'/>&nbsp;页&nbsp;
                    <a href="javascript:changeCurrentPage($('#currentPageText').val())" class='go'>GO</a>
                </div>
            </form>
        </div>
        <br/>
    </div>
</div>
<script type="text/javascript" src="/js/userManager.js"></script>
</body>
</html>
