<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 17-4-16
  Time: 上午10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="UTF-8">
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
                    <a href="#">
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
            <form class="form form-inline" id="indexForm" action="/file/listFileInfo.do" method="post">
                <input  hidden="hidden" name="currentPage" id="currentPage" value="${page.currentPage}"/>
                <div class="row">
                    <div class="form-group">
                        <label for="fileName">文件名</label>
                        <input type="text" class="form-control" id="fileName" name="fileName" value="${requestScope.fileName}" placeholder="请输入文件名称">
                    </div>
                    <div class="form-group">
                        <label for="userName">上传者</label>
                        <input type="text" class="form-control" id="userName" name="userName" value="${requestScope.userName}" placeholder="请输用户名称">
                    </div>
                    <div class="form-group">
                        <label for="startDate">开始时间</label>
                        <input type="date" class="form-control" id="startDate" name="startDate" value="${requestScope.startDate}" placeholder="请输入起始时间">
                    </div>
                    <div class="form-group">
                        <label for="endDate">结束时间</label>
                        <input type="date" class="form-control" id="endDate" name="endDate" value="${requestScope.endDate}" placeholder="请输入结束时间">
                    </div>
                    <div class="form-group">
                        <label for="collegeId">学院</label>
                        <select id="collegeId" name="collegeId">
                            <option value="">所有</option>
                            <c:forEach items="${sessionScope.collegeList}" var="item">
                                <c:choose>
                                    <c:when test="${requestScope.collegeId == item.codeId}">
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
                        <button class="btn btn-primary">查询</button>
                    </div>
                </div>
                <div class="form-group" style="margin-left: 35px">
                    <a href="/file/fileUploadPage.do">
                        <label>上传</label>
                        <button class="btn-primary icon iconfont icon-shangchuan"></button>
                    </a>
                </div>
                <div class="form-group">
                    <a>
                        <button class="btn-primary icon iconfont icon-refresh"></button>
                        <label>刷新</label>
                    </a>
                </div>
                <div class="file-list-box">
                    <table class="table">
                        <thead>
                        <tr>
                            <th class="col-md-2 text-left">图片</th>
                            <th class="col-md-3 text-left">文件名</th>
                            <th class="col-md-1 text-center">上传者</th>
                            <th class="col-md-1 text-center">时间</th>
                            <th class="col-md-1 text-center">下载</th>
                          <%--  <th class="col-md-1 text-center">发送</th>--%>
                            <th class="col-md-1 text-center">阅读</th>
                            <th class="col-md-1 text-center">收藏</th>
                            <th class="col-md-1 text-center">删除</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.fileInfoList}" var="fileInfo">
                        <tr>
                            <td class="text-left">
                                <div class="icon iconfont icon-word"></div>
                            </td>
                            <td class="text-left">
                                <a>
                                    ${fileInfo.fileName}
                                </a>
                            </td>
                            <td class="text-center">
                                ${fileInfo.userId}
                            </td>
                            <td class="text-center">
                                ${fileInfo.createDate}
                            </td>
                            <td class="text-center">
                                <a href="/file/downloadFile.do?fileId=${fileInfo.fileId}">
                                    <div class="icon iconfont icon-xiazai"></div>
                                </a>
                            </td>
                      <%--      <td class="text-center">
                                <a>
                                    <div class="icon iconfont icon-jinlingyingcaiwangtubiao17"></div>
                                </a>
                            </td>--%>
                            <td class="text-center">
                                <a>
                                    <div class="icon iconfont icon-yuedu"></div>
                                </a>
                            </td>
                            <td class="text-center">
                                <a>
                                    <div class="icon iconfont icon-shouc01"></div>
                                </a>
                            </td>
                            <td class="text-center">
                                <a>
                                    <div class="icon iconfont icon-shanchu"></div>
                                </a>
                            </td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
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
    </div>
</div>
<script type="text/javascript" src="/js/index.js"></script>
</body>
</html>
