<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 17-4-26
  Time: 下午9:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>收藏夹</title>
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
                    <a href="<%=basePath%>/filter/userManager/editUserInfoPage.do?userId=${sessionScope.userInfo.userId}">
                        <div class="icon iconfont icon-touxiang"></div>
                        <div class="name">${sessionScope.userInfo.userName}</div>
                    </a>
                </ul>
                <li>
                    <a href="#">
                        <div class="icon iconfont icon-shoucang"></div>
                        <div class="name">收藏</div>
                    </a>
                </li>
                <li>
                    <a href="<%=basePath%>/filter/file/listFileInfo.do">
                        <div class="icon iconfont icon-file"></div>
                        <div class="name">文件</div>
                    </a>
                </li>
                <c:if test="${sessionScope.userInfo.manager == 1}">
                    <li>
                        <a href="<%=basePath%>/filter/userManager/userManagerPage.do">
                            <div class="icon iconfont icon-yonghuguanli1"></div>
                            <div class="name">用户管理</div>
                        </a>
                    </li>
                </c:if>
                <li style="margin-top: 140px;">
                    <a href="<%=basePath%>/filter/user/logout.do">
                        <div style="font-size: 25px;padding-left: 10px" class="icon iconfont icon-tuichu"></div>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <div class="file-list-side">
        <div class="file-search-box">
            <form class="form form-inline" action="<%=basePath%>/filter/fileCollection/listCollectionInfo.do"
                  method="post" id="collectionForm">
                <input type="hidden" name="currentPage" id="currentPage" value="${page.currentPage}"/>
                <input type="hidden" name="userId" id="userId" value="${sessionScope.userInfo.userId}"/>
                <div class="row">
                    <div class="form-group">
                        <label for="fileName">文件名</label>
                        <input type="text" class="form-control" id="fileName" name="fileName"
                               value="${requestScope.fileCollectionInfo.fileName}" placeholder="文件名称">
                    </div>
                    <div class="form-group">
                        <label for="uploader">上传者</label>
                        <input type="text" class="form-control" id="uploader" name="uploader"
                               value="${requestScope.fileCollectionInfo.uploader}" placeholder="文件上传者">
                    </div>
                    <div class="form-group">
                        <label for="collegeId">学院</label>
                        <select id="collegeId" name="collegeId">
                            <option value="">所有</option>
                            <c:forEach items="${applicationScope.collegeList}" var="item">
                                <c:choose>
                                    <c:when test="${requestScope.fileCollectionInfo.collegeId == item.codeId}">
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
                        <button type="submit" class="btn btn-primary">查询</button>
                    </div>
                </div>
                <div class="file-list-box">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th class="col-md-1 text-left"></th>
                            <th class="col-md-3 text-center">文件名称</th>
                            <th class="col-md-2 text-center">学院</th>
                            <th class="col-md-1 text-center">上传者</th>
                            <th class="col-md-1 text-center">上传时间</th>
                            <th class="col-md-1 text-center">下载</th>
                            <th class="col-md-1 text-center">预览</th>
                            <th class="col-md-1 text-center">删除</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.fileCollectionInfoList}" var="item">
                            <tr>
                                <td class="text-left">
                                    <div class="icon iconfont icon-touxiang1"></div>
                                </td>
                                <td class="text-center">
                                        ${item.fileName}
                                </td>
                                <td class="text-center">
                                        ${item.collegeId}
                                </td>
                                <td class="text-center">
                                        ${item.uploader}
                                </td>
                                <th class="text-center">
                                    <fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"></fmt:formatDate>
                                </th>
                                <td class="text-center">
                                    <a href="<%=basePath%>/filter/file/downloadFile.do?fileId=${item.fileId}">
                                        <div class="icon iconfont icon-xiazai"></div>
                                    </a>
                                </td>
                                <td class="text-center">
                                    <a href="<%=basePath%>/filter/file/preview.do?fileId=${item.fileId}">
                                        <div class="icon iconfont icon-yuedu"></div>
                                    </a>
                                </td>
                                <td class="text-center">
                                    <a href="javascript:deleteCollectionFile(${sessionScope.userInfo.userId},${item.fileId})">
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
</body>
<script type="text/javascript" src="/js/collection.js"></script>
</html>
