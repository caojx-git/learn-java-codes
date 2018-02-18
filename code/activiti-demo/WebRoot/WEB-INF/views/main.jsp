<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<frameset rows="100px,*" cols="*">
	<noframes>
		<body>﻿ ﻿ ﻿很抱歉，馈下使用的浏览器不支持框架功能，请转用新的浏览器。 ﻿ ﻿
		</body>
	</noframes>
	
	<frame name="top" src="loginAction_top.action" scrolling="No" noresize="noresize" >
	<frameset cols="150,*">
		 <frame name="left" src="loginAction_left.action" scrolling="No" noresize="noresize" >
		 <frame src="loginAction_welcome.action" name="main" title="mainframe" scrolling="Yes" noresize="noresize"  />
	</frameset>
</frameset>

</html>