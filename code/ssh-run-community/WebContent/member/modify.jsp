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
td, th{padding:2px 5px;border-collapse:collapse;text-align:left; font-weight:normal; text-align:left}
thead tr th{height:30px;background:#FFFFFF;border:1px solid white;}
thead tr th.line1{background:#FFFFFF;}
thead tr th.line4{background:#C6C6C6;}
tbody tr td{height:35px;background:#CBE2FB;border:1px solid white; vertical-align:middle;}
tbody tr td.line4{background:#D5D6D8;}
tbody tr th{height:35px;background: #DFEDFF;border:1px solid white; vertical-align:middle;}
tfoot tr td{height:35px;background:#FFFFFF;border:1px solid white; vertical-align:middle; text-align:center}
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
	<a href="/runCommunity/logout.do">注  销</a> | 收  藏
	 </div>
    <div id="mainnav">
      <ul>
      <li><a href="activity.jsp">首页</a></li>
      <li><a href="../other/musicrun.html">音乐跑不停</a></li>
      <li><a href="../other/equip.html">跑步装备库</a></li>
      <li><a href="../other/guide.html">专业跑步指南</a></li>
      <li><a href="../other/bbs.html">跑步论坛</a></li>
	  
      </ul>
      <span></span>
    </div>
   </div>
  
  <div id="content" align="center">
     
     <div id="center">
      
		<table width="600">
			<thead>
  			<tr>
   				<th colspan="3">
					<h4>修改会员信息</h4>
				</th>
 			</tr>
			</thead>
			
  			<tr>
				<td width="100%">
					<form action="saveModify.do" method="post" onSubmit="return validateModifyForm(this);">
					<table width="100%" border="0" style="margin:5px 0;" cellspacing="0" cellpadding="0" align="center" >
					<thead>
		 			<tr>
						<td colSpan=3>
							请填写个人资料：（注意带有<font color=#ff0000>*</font>的项目必须填写）
						</td>
					</tr>
					</thead>
					<tbody>
					<tr>
						<th width="25%" class="line1">
							<div align="right" >
								<font color="#ff0000">*</font>昵称：
							</div>
						</th>
						<td width="30%" >
							<input type="text" maxLength="14" style="width:200px" value="<s:property value='#session.memberinfo.nickname'/>" disabled="disabled"/>
						</td>
						<th width="45%" >
							<font color="#ff0000">昵称可使用长度为0-16的任何字符</font>
						</th>
					</tr>
					<tr>
						<th class="line1">
							<div align="right">
								<font color="#ff0000" >*</font> 旧密码：
							</div>
						</th>
						<td>
							<input type="password" maxLength="14" style="width:200px" name="oldPasswd" />
						</td>
						<th>&nbsp;
							
						</th>
					</tr>
					<tr>
						<th class="line1">
							<div align="right">
								<font color="#ff0000" >*</font> 新密码：
							</div>
						</th>
						<td>
							<input type="password" maxLength="14" style="width:200px" name="newPasswd" />
						</td>
						<th>
							<font color="#ff0000">密码可使用长度为6-14的任何字符，并区分英文字母大小写</font>
						</th>
					</tr>
					<tr>
						<th class="line1">
							<div align="right">
								<font color="#ff0000">*</font>密码确认：
							</div>
						</th>
						<td>
							<input type="password" maxLength="14" style="width:200px"	name="newPasswd" />
						</td>
						<th>
							<font color="#ff0000">请再输入一次密码</font>
						</th>
					</tr>
					<tr>
						<th class="line1">
							<div align="right">
								<font color="#ff0000">*</font>电子邮箱：
							</div>
						</th>
						<td>
							<input type="text" maxLength="30" style="width:200px" name="memberinfo.email" />
						</td>
						<th>
							<font color="#ff0000">请输入您常用的其它电子邮箱</font>
						</th>
					</tr>
					<tr>
						<th class="line1">
							<div align="right">
								<font color="#ff0000">*</font> 密码提示问题：
							</div>
						</th>
						<th>
							<input style="width:200px" type="text" name="memberinfo.passwordquestion" />
						</th>
						<th>
							<font color="#ff0000">例如：我的哥哥是谁？</font>
						</th>
					</tr>
					<tr>
						<th class="line1">
							<div align="right">
								<font color="#ff0000">*</font> 密码提示答案：
							</div>
						</th>
						<td>
							<input  type="text" style="width:200px" name="memberinfo.passwordanswer" />
						</td>
						<th>
							<font color="#ff0000">注意：密码提示问题答案长度不少于6位</font>
						</th>
					</tr>
					<tr>
						<th class="line1">
							<div align="right" >
								<font color=#ff0000>*</font> 性别：
							</div>
						</th>
						<td>
							<input type="radio" value="0" name="memberinfo.gender" checked="checked"/>男
							<input type="radio" value="1" name="memberinfo.gender" />女
						</td>
						<th>&nbsp;	</th>
					</tr>
					<tr>
						<th class="line1">
							<div align="right">
								所在省份/城市：
							</div>
						</th>
						<td >
							<select name="memberinfo.provincecity">
								<option value="0" selected>请选择</option>
								<option value="1">北京</option>
								<option value="2">上海</option>
								<option value="3">天津</option>
								<option value="4">重庆</option>
								<option value="5">辽宁</option>
								<option value="6">广东</option>
								<option value="7">浙江</option>
								<option value="8">江苏</option>
								<option value="9">山东</option>
								<option value="10">四川</option>
								<option value="11">黑龙江</option>
								<option value="12">湖南</option>
								<option value="13">湖北</option>
								<option value="14">福建</option>
								<option value="15">陕西</option>
								<option value="16">河南</option>
								<option value="17">安徽</option>
								<option value="18">河北</option>
								<option value="19">吉林</option>
								<option value="20">江西</option>
								<option value="21">广西</option>
								<option value="22">山西</option>
								<option value="23">内蒙古</option>
								<option value="24">甘肃</option>
								<option value="25">贵州</option>
								<option value="26">新疆</option>
								<option value="27">云南</option>
								<option value="28">宁夏</option>
								<option value="29">海南</option>
								<option value="30">青海</option>
								<option value="31">西藏</option>
								<option value="32">港澳台</option>
								<option value="33">海外</option>
								<option value="34">其它</option>
							</select>
						</td>
						<th>&nbsp;
							
						</th>
					</tr>
					<tr>
						<th class="line1">
							<div align="right">
								联系电话：
							</div>
						</th>
						<td>
							<input type="text" style="width:200px" name="memberinfo.phone" />
						</td>
						<th>
							<font color=#ff0000>请输入区号和真实的电话，以便我们与您联系</font>
						</th>
					</tr>
					<tr>
						<th class="line1">
							<div align="right">
								详细地址：
							</div>
						</th>
						<td>
							<input type="text" style="width:200px" name="memberinfo.address" />
						</td>
						<th>&nbsp;</th>
					</tr>
					<tfoot>
    				<tr>
						<td colSpan="3" align="center">
							<input type="submit" value="提交" style="cursor: hand"/>
							&nbsp;&nbsp;
							<input type="reset" value="重置" style="cursor: hand"/>
						</td>
					</tr> 
					</tfoot>
					</table>
					</form>
				</td>
  			</tr>
			</table>
	        <BR /><BR />
        <div id="hots">
          <h2>我的地盘</h2>
          <ul>
          <li>
           <div>
            <img src="../images/a.gif" />
            <a href="modify.jsp">基本信息</a>
            <p>可修改自己的基本信息</p>
           </div>
          </li>
          <li>
           <div>
            <img src="../images/b.gif" />
            <a href="../messenger/listReciverMessage.do">我的信箱</a>
            <p>写信息、收件箱、发件箱</p>
           </div>
          </li>
          <li>
           <div>
            <img src="../images/c.gif" />
            <a href="../messenger/listFriend.do">我的好友</a>
            <p>好友管理及黑名单</p>
           </div>
          </li>
          <li>
           <div>
            <img src="../images/d.gif" />
            <a href="createSpace.do">个性空间</a>
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
