<%--
  Created by IntelliJ IDEA.
  User: caojx
  Date: 17-4-16
  Time: 上午10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="UTF-8">
    <title>湘南云库</title>
    <link rel="icon" href="/images/xnxy.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/iconfont/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="/bootstrap/css/bootstrap.min.css">
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
                    <a href="login.jsp">
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
            <form class="form form-inline">
                <div class="row">
                    <div class="form-group">
                        <label for="fileName">文件名</label>
                        <input type="text" class="form-control" id="fileName" placeholder="请输入文件名称">
                    </div>
                    <div class="form-group">
                        <label for="fileType">文件名</label>
                        <input type="text" class="form-control" id="fileType" placeholder="请输入文件名称">
                    </div>
                    <div class="form-group">
                        <label for="startTime">开始时间</label>
                        <input type="date" class="form-control" id="startTime" placeholder="请输入起始时间">
                    </div>
                    <div class="form-group">
                        <label for="endTime">结束时间</label>
                        <input type="date" class="form-control" id="endTime" placeholder="请输入结束时间">
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label for="name">文件类型</label>
                        <select>
                            <option>小明</option>
                            <option>小红</option>
                            <option>小王</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="name">上传者</label>
                        <select>
                            <option>小明</option>
                            <option>小红</option>
                            <option>小王</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <button class="btn btn-primary">查询</button>
                    </div>
                </div>

            </form>
        </div>
        <br/>
        <div class="operator-box row form-inline">
            <div class="form-group">
                <a href="/file/fileUploadPage.do">
                    <button class="btn-primary icon iconfont icon-shangchuan"></button>
                    <label for="name">上传</label>
                </a>
            </div>
            <div class="form-group">
                <a>
                    <button class="btn-primary icon iconfont icon-refresh"></button>
                    <label for="name">刷新</label>
                </a>
            </div>
        </div>
        <br/>
        <div class="file-list-box">
            <table class="table">
                <thead>
                <tr>
                    <th class="col-md-2 text-left">图片</th>
                    <th class="col-md-3 text-left">文件名</th>
                    <th class="col-md-1 text-center">上传者</th>
                    <th class="col-md-1 text-center">时间</th>
                    <th class="col-md-1 text-center">下载</th>
                    <th class="col-md-1 text-center">发送</th>
                    <th class="col-md-1 text-center">阅读</th>
                    <th class="col-md-1 text-center">收藏</th>
                    <th class="col-md-1 text-center">删除</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td class="text-left">
                        <div class="icon iconfont icon-word"></div>
                    </td>
                    <td class="text-left">
                        <a>
                            xxxx文件
                        </a>
                    </td>
                    <td class="text-center">
                        小明
                    </td>
                    <td class="text-center">
                        2017/04/02
                    </td>
                    <td class="text-center">
                        <a>
                            <div class="icon iconfont icon-xiazai"></div>
                        </a>
                    </td>
                    <td class="text-center">
                        <a>
                            <div class="icon iconfont icon-jinlingyingcaiwangtubiao17"></div>
                        </a>
                    </td>
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
                <tr>
                    <td class="text-left">
                        <div class="icon iconfont icon-word"></div>
                    </td>
                    <td class="text-left">
                        xxxx文件
                    </td>
                    <td class="text-center">
                        小明
                    </td>
                    <td class="text-center">
                        2017/04/02
                    </td>
                    <td class="text-center">
                        <a>
                            <div class="icon iconfont icon-xiazai"></div>
                        </a>
                    </td>
                    <td class="text-center">
                        <a>
                            <div class="icon iconfont icon-jinlingyingcaiwangtubiao17"></div>
                        </a>
                    </td>
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
