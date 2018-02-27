<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="杰普软件(www.briup.com)" />
<meta name="description" content="杰普软件(www.briup.com" />
<title>杰普——跑步社区</title>
<link rel="stylesheet" type="text/css" id="css" href="style/main.css" />
<link rel="stylesheet" type="text/css" id="css" href="style/style1.css" />
<script src="js/main.js" type="text/javascript"></script>
<script src="js/base.js" type="text/javascript"></script>
<script src="js/common.js" type="text/javascript"></script>
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
    <div id="logo"><h1>跑步社区</h1></div>
    <div id="mainnav"> <ul>
      <li class="current"><a href="#">首页</a></li>
      <li><a href="other/musicrun.html">音乐跑不停</a></li>
      <li><a href="other/equip.html">跑步装备库</a></li>
      <li><a href="other/guide.html">专业跑步指南</a></li>
      <li><a href="other/bbs.html">跑步论坛</a></li>

      </ul><span></span> </div>
   </div>
  
  <div align="center" id="content">
     
     <div id="center">
      
       <div id="ltd">
         <h2>新会员注册</h2>
	     <TABLE cellSpacing=0 cellPadding=0 border=0>
	  			<TR>
    				<TD width="100%">
     					<FORM onSubmit="return validateRegisterForm(this);" action="/SSHRunCommunity/register.do" method="post">
     						<TABLE width="100%">
        						<thead>
        						<TR>
         							<TH colSpan=3>
										<font face="Arial, Helvetica, sans-serif"><b>
										请填写个人资料：（注意带有<FONT color=#ff0000>*</FONT>的项目必须填写）</b></font>
									</TH>
								</TR>
								</thead>
								<TBODY>
       							<TR>
         							<TH width="25%">
            							<DIV align="right"><FONT color=#ff0000>*</FONT>昵称： </DIV>
									</TH>
          							<TD width="30%"><INPUT type="text" maxLength=14 name="memberinfo.nickname" style="width:200px"></TD>
         							<TH width="45%"><FONT color=#ff0000>昵称可使用长度为0-16的任何字符</FONT></TH>
								</TR>
								<TR>
									<TH>
									<DIV align="right"><FONT color=#ff0000>*</FONT> 密码： </DIV></TH>
								 	<TD><INPUT type=password maxLength=14 style="width:200px" name="memberinfo.password"></TD>
								  	<TH><FONT color=#ff0000>密码可使用长度为6-14的任何字符，并区分英文字母大小写</FONT> </TH>
								</TR>
								<TR>
								  <TH>
									<DIV align=right><FONT color=#ff0000>*</FONT>密码确认： </DIV></TH>
								  <TD><INPUT type=password maxLength=14 style="width:200px"
									name=passwdre /> </TD>
								  <TH><FONT color=#ff0000>请再输入一次密码</FONT> </TH></TR>
								<TR>
								  <TH>
									<DIV align=right><FONT color=#ff0000>*</FONT>电子邮箱： </DIV></TH>
								  <TD><INPUT maxLength=30 style="width:200px" name="memberinfo.email" /> </TD>
								  <TH><FONT color=#ff0000>请输入您常用的其它电子邮箱</FONT> </TH></TR>
								<TR>
								  <TH>
									<DIV align=right><FONT color=#ff0000>*</FONT> 密码提示问题： </DIV></TH>
								  <TD><INPUT style="width:200px" name="memberinfo.passwordquestion" /> </TD>
								  <TH><FONT color=#ff0000>例如：我的哥哥是谁？</FONT> </TH></TR>
								<TR>
								  <TH>
									<DIV align=right><FONT color=#ff0000>*</FONT> 密码提示答案： </DIV></TH>
								  <TD><INPUT style="width:200px" name="memberinfo.passwordanswer"/> </TD>
								  <TH><FONT color=#ff0000>注意：密码提示问题答案长度不少于6位</FONT> </TH></TR>
								<TR>
								  <TH>
									<DIV align=right><FONT color=#ff0000>*</FONT> 性别： </DIV></TH>
								  <TD><INPUT type=radio CHECKED value=0 name="memberinfo.gender"/>男 
									<INPUT type=radio value=1 name="memberinfo.gender" />女 </TD>
								  <TH>&nbsp;</TH></TR>
								<TR>
								  <TH>
									<DIV align=right>是否有推荐人： </DIV></TH>
								  <TD><INPUT onclick=javascript:changeText(); 
									type=checkbox name=re><input type="text" style="width:130px; display:none" id="retext" name="memberinfo.recommender" /></TD>
								  <TH><FONT color=#ff0000>如果有推荐人，请选择。并输入推荐人的会员ID</FONT> </TH></TR>
								
								<TR>
								  <TH>
									<DIV align=right>所在省份/城市： </DIV></TH>
								  <TD><SELECT name="memberinfo.provincecity"> <OPTION value=0 
									  selected>请选择</OPTION> <OPTION value=1>北京</OPTION> <OPTION 
									  value=2>上海</OPTION> <OPTION value=3>天津</OPTION> <OPTION 
									  value=4>重庆</OPTION> <OPTION value=5>辽宁</OPTION> <OPTION 
									  value=6>广东</OPTION> <OPTION value=7>浙江</OPTION> <OPTION 
									  value=8>江苏</OPTION> <OPTION value=9>山东</OPTION> <OPTION 
									  value=10>四川</OPTION> <OPTION value=11>黑龙江</OPTION> <OPTION 
									  value=12>湖南</OPTION> <OPTION value=13>湖北</OPTION> <OPTION 
									  value=14>福建</OPTION> <OPTION value=15>陕西</OPTION> <OPTION 
									  value=16>河南</OPTION> <OPTION value=17>安徽</OPTION> <OPTION 
									  value=18>河北</OPTION> <OPTION value=19>吉林</OPTION> <OPTION 
									  value=20>江西</OPTION> <OPTION value=21>广西</OPTION> <OPTION 
									  value=22>山西</OPTION> <OPTION value=23>内蒙古</OPTION> <OPTION 
									  value=24>甘肃</OPTION> <OPTION value=25>贵州</OPTION> <OPTION 
									  value=26>新疆</OPTION> <OPTION value=27>云南</OPTION> <OPTION 
									  value=28>宁夏</OPTION> <OPTION value=29>海南</OPTION> <OPTION 
									  value=30>青海</OPTION> <OPTION value=31>西藏</OPTION> <OPTION 
									  value=32>港澳台</OPTION> <OPTION value=33>海外</OPTION> <OPTION 
									  value=34>其它</OPTION></SELECT> </TD>
								  <TH>&nbsp; </TH></TR>
								<TR>
								  <TH>
									<DIV align=right><FONT color=#ff0000>*</FONT>年龄： </DIV></TH>
								  <TD><INPUT style="width:200px" name="memberinfo.age" /> </TD>
								  <TH><FONT color=#ff0000>请输入年龄（必须为数字）</FONT> </TH></TR>
								<TR>
								  <TH>
									<DIV align=right>联系电话： </DIV></TH>
								  <TD><INPUT style="width:200px" name="memberinfo.phone" /> </TD>
								  <TH><FONT color=#ff0000>请输入区号和真实的电话，以便我们与您联系</FONT> 
								</TH></TR>
								<TR>
								  <TH>
									<DIV align=right>详细地址： </DIV></TH>
								  <TD><INPUT style="width:200px" name="memberinfo.address" /> </TD>
								  <TH>&nbsp;</TH></TR>
								<TR>
								  <TH>
									<DIV align=right>验证码：</DIV></TH>
								  <TD><INPUT style="width:200px" type="text" name="authCode"/> </TD>
								  <TH><IMG alt=authCode src="/SSHRunCommunity/images/authimg.jpg" /> 
								</TH></TR>
								<tfoot>
								<TR>
								  <TD colSpan=3>
									<DIV align=center><INPUT style="CURSOR: hand" type=submit value=提交 /> 
									&nbsp;&nbsp; <INPUT style="CURSOR: hand" type=reset value=重置 /> 
								  </DIV></TD></TR>
								    </tfoot>
								  </TABLE></FORM></TD>
						  </TR>	
						</TABLE>
     
       </div>
              
     </div>
     
     <div class="clear"></div>
     
  </div>
  
  <div id="footer">
    <div id="copyright">
       <div id="copy">
       <p>CopyRight&copy;2009</p>
       <p>跑步社区(www.irun.com)</p>
        </div>
    </div>
    <div id="bgbottom"></div>
  </div>
  
</div>
</div>
</body>
</html>
