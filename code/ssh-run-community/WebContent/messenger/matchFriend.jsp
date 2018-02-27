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
<link rel="stylesheet" type="text/css" id="css" href="../style/style.css" />

<style type="text/css">
<!--
table{border-spacing:1px; border:1px solid #A2C0DA;}
td, th{padding:2px 5px;border-collapse:collapse;text-align:left; font-weight:normal;}
thead tr th{background:#B0D1FC;border:1px solid white;}
thead tr th.line1{background:#D3E5FD;}
thead tr th.line4{background:#C6C6C6;}
tbody tr td{height:35px;background:#CBE2FB;border:1px solid white; vertical-align:middle;}
tbody tr td.line4{background:#D5D6D8;}
tbody tr th{height:35px;background: #DFEDFF;border:1px solid white; vertical-align:middle;}
tfoot tr td{height:35px;background:#F5F5F5;border:1px solid white; vertical-align:middle; text-align:right}
-->
</style>

<script src="../js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="../../js/common.js" ></script>
<script type="text/javascript">
	<%String var = (String)request.getAttribute("message");%>
	var code = "<%=var%>";
	if(code!="null"){
		window.alert(code);
	}
	<%request.removeAttribute("message");%>
</script>
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
    <li><a href="matchFriend.jsp" title="好友速配"><span><b>好友速配</b></span></a></li>
    <li><a href="listFriend.do" title="好友名单"><span>好友名单</span></a></li>
    <li><a href="blackList.jsp" title="黑名单"><span>黑名单</span></a></li>
  </ul>
</div>
<br /><br />

  <div id="content" align="center"> 
     <div id="center">
	 <br /><br />
		<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td colspan="2" height="40">
						<h4>-&gt;好友速配</h4>
					</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td width="100%">
						<form action="/SSHRunCommunity/messenger/matchFriendForm.do" method="post" name="matchFriendForm">
						<table width="100%" cellspacing="1" cellpadding="3" align="center">
							<tr>
								<td colspan="4">
									<b>找到您跑步世界的另一半，您可以 <a href="/SSHRunCommunity/messenger/matchOne.do"><FONT color=#ff0000>马上速配</FONT></a> 一位跑友</b>
								</td>
							</tr>
							<tr>
								<td colspan="4"><b>更可以按要求检索</b></td>
							</tr>
							<tr>
         						 <td align="center">年龄范围：
         						 	<select name="age">
                  						<option value="1">10-19岁</option>
                  						<option value="2">20-29岁</option>
                  						<option value="3">30-39岁</option>
                  						<option value="0" selected="selected">不限</option>
                    				</select>
                    			</td>
           						<td align="center">性别：
                					<select name="gender">
               							<option value="0">男</option>
              							<option value="1">女</option>
               							<option value="unlimited" selected="selected">不限</option>
                					</select>
                				</td>
                				<td align="center">所在省/城市：
                					<select name="provinceCity">
                    					<option value="1">北京</option>
                    					<option value="2">上海</option>
                    					<option value="3">天津</option>
                    					<option value="12">湖南</option>
                    					<option selected="selected" value="unlimited">不限</option>
                    				</select> 
                    			</td>
                    			<td align="center">
            						<input type="submit" name="Submit2" value="开始检索" />                 
          						</td>
         					</tr>
						</table>
						</form>
					</td>
				</tr>
			</tbody>		
					
			<table width="100%" cellspacing="1" cellpadding="3" align="center">
				<tfoot>
				<s:iterator id="member" value="#request.members">
					<tr height="25">
						<td width="15%" align="center">
							<s:property value='#member.nickname'/>
						</td>
						<td width="15%" align="center">
							<s:if test="#member.gender==0">
								男
							</s:if>
							<s:else>
								女
							</s:else>
						</td>
						<td width="15%" align="center">
							<s:property value="#member.age"/>
						</td>
						<td width="15%" align="center">
							<s:property value="#member.address"/>
						</td>
						<td width="20%" align="center">
							<a href="/SSHRunCommunity/messenger/addFriend.do?friendname=<s:property value='#member.nickname'/>">添加好友</a>
						</td>
						<td width="20%" align="center">
							<a href="/SSHRunCommunity/messenger/sendInfo.jsp?reciver=<s:property value='#member.nickname'/>">发送短信</a>
						</td>
					</tr>
				</s:iterator>
				</tfoot>
			</table>
		</table>
	     <br /><br />
        <div id="hots">
          <h2>我的地盘</h2>
          <ul>
          <li>
           <div>
            <img src="../images/a.gif" />
            <a href="../member/modify.jsp">基本信息</a>
            <p>可修改自己的基本信息</p>
           </div>
          </li>
          <li>
           <div>
            <img src="../images/b.gif" />
            <a href="inbox.jsp">我的信箱</a>
            <p>写信息、收件箱、发件箱</p>
           </div>
          </li>
          <li>
           <div>
            <img src="../images/c.gif" />
            <a href="listFriend.do">我的好友</a>
            <p>好友管理及黑名单</p>
           </div>
          </li>
          <li>
           <div>
            <img src="../images/d.gif" />
            <a href="../member/createSpace.do">个性空间</a>
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
