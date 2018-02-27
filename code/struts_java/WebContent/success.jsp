<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@taglib prefix="s" uri="/struts-tags" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>success.jsp</title>
</head>
<body>
this is success jsp
	<s:property value="student.name"/>
	<img alt="图片找不到" src="/xnxy_struts/file/<s:property value="fileFileName"/>">
	<hr/>
	<img alt="图片找不到" src="/xnxy_struts/file/<s:property value="#request.fileFileName"/>">
	<%
		String msg = (String)request.getAttribute("msg");
		if(msg!=null){
	%>
		<%=msg %>
	<%
		}
	%>
	<s:property value="request.msg"/>
	<s:debug/>
</body>
</html>