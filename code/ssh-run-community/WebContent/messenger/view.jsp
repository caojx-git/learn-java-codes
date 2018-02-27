<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="杰普软件(www.briup.com)" />
<meta name="description" content="杰普软件(www.briup.com)" />
<title>杰普――跑步社区</title>
<link rel="stylesheet" type="text/css" id="css" href="../style/main.css" />
<link rel="stylesheet" type="text/css" id="css" href="../style/style1.css" />
<script src="../js/main.js" type="text/javascript"></script>
<script type="text/javascript">
	<%String var = (String)request.getAttribute("message");%>
	var code = "<%=var%>";
	if(code!="null"){
		window.alert(code);
	}
	<%request.removeAttribute("message");%>
</script>
<style type="text/css">
<!--
table{border-spacing:1px; border:1px solid #A2C0DA;}
td, th{padding:2px 5px;border-collapse:collapse;text-align:left; font-weight:normal;}
thead tr th{background:#B0D1FC;border:1px solid white;}
thead tr th.line1{background:#D3E5FD;}
thead tr th.line4{background:#C6C6C6;}
tbody tr td{height:50px;background:#CBE2FB;border:1px solid white; vertical-align:top;}
tbody tr td.line4{background:#D5D6D8;}
tbody tr th{height:50px;background: #DFEDFF;border:1px solid white; vertical-align:top;}
caption,tfoot.th{height:30px; border:0px;}
-->
</style>

</head>
<body>
<div id="btm">
<div id="main">

  <div id="header">
    <div id="top"></div>
    <div id="logo">
      <h1>跑步社区</h1></div>
	  <div id="logout">
	<a href="../login.html">注  销</a> | 收  藏
	 </div>
    <div id="mainnav">
      <ul>
      <li><a href="../member/activity.jsp">首页</a></li>
      <li><a href="../other/musicrun.html">音乐跑不停</a></li>
      <li><a href="../other/equip.html">跑步装备库</a></li>
      <li><a href="../other/guide.html">专业跑步指南</a></li>
      <li><a href="../other/bbs.html">跑步论坛</a></li>
	
      </ul>
      <span></span>
    </div>
   </div>
   
  <div id="tabs1">
  <ul>
    <li><a href="sendInfo.jsp" title="写纸条"><span>写纸条</span></a></li>
    <li><a href="/runCommunity/messenger/listReciverMessage.do" title="收件箱"><span><b>收件箱</b></span></a></li>
    <li><a href="/runCommunity/messenger/listMessage.do" title="发件箱"><span>发件箱</span></a></li>
  </ul>
</div>
<br /><br />

  <div id="content" align="center"> 
     <div id="center">
		<form action="result_Flow.htm" method="post" name="form1">
			<table width="500" border="0" style="margin-top:20px;" align="center" cellpadding="0" cellspacing="0">
				
				<thead>
					<tr>
						<td colspan="2" height="40">
							<h4>消息详细内容</h4>
						</td>
					</tr>
				
				</thead>
				<tbody>
					<tr>
						<th class="line1" scope="col" width="20%">发信人</th>
						<td><s:property value="#request.message.sender"/></td>
					</tr>
					<tr>
						<th>主题</th>
						<td>
							<s:property value="#request.message.title"/>
						</td>
					</tr>
					<tr>
						<th>消息内容</th>
						<td>
							<s:property value="#request.message.content"/>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<th colspan="2" align="center" classo="line4" style="text-align:center">
							<input type="button" value="返回" onclick="self.location='../messenger/listReciverMessage.do'" />
						</th>
					</tr>
				</tfoot>
			</table>
		</form>
	        <br /><br />
        <div id="hots">
          <h2>我的地盘</h2>
          <ul>
          <li>
           <div>
            <img src="../images/a.gif" />
            <a href="../member/modify.html">基本信息</a>
            <p>可修改自己的基本信息</p>
           </div>
          </li>
          <li>
           <div>
            <img src="../images/b.gif" />
            <a href="inbox.html">我的信箱</a>
            <p>写信息、收件箱、发件箱</p>
           </div>
          </li>
          <li>
           <div>
            <img src="../images/c.gif" />
            <a href="buddyList.html">我的好友</a>
            <p>好友管理及黑名单</p>
           </div>
          </li>
          <li>
           <div>
            <img src="../images/d.gif" />
            <a href="../member/noSpace.html">个性空间</a>
            <p>创建自己的个性空间</p>
           </div>
          </li>
          </ul>
        </div> 
         
     </div>
     <div id="right">
       <h2>&nbsp;</h2>
      <ul><li></li>
      </ul>
     </div>
     <div class="clear"></div>
    
     
  </div>
  
  <div id="footer">
    <div id="copyright">
       <div id="copy">
       <p>CopyRight&copy;2009</p>
       <p>跑步社区(www.irun.com) </p>
        </div>
    </div>
    <div id="bgbottom"></div>
  </div>
  
</div>
</div>
</body>
</html>
