<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<s:form action="/user/convertor.action">
		<s:textfield name="student" label="个人信息"/>
		<span>按照提供格式输入tom:20:male</span>
		<s:submit type="image" src="123.png"></s:submit>
	</s:form>
	<s:debug/>
</body>
</html>