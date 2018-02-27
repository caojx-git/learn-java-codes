<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>fileUp.jsp</title>
</head>
<body>
	<form action="/xnxy_struts/user/fileUp2.action" method="post" enctype="multipart/form-data">
		上传文件：<input type="file" name="file"/><!--  后台action中必须有File file--name=file, String  fileFileName  -->
		上传文件：<input type="file" name="file"/><!--  后台action中必须有File file--name=file, String  fileFileName  -->
		上传文件：<input type="file" name="file"/><!--  后台action中必须有File file--name=file, String  fileFileName  -->
		上传文件：<input type="file" name="file"/><!--  后台action中必须有File file--name=file, String  fileFileName  -->
		上传文件：<input type="file" name="file"/><!--  后台action中必须有File file--name=file, String  fileFileName  -->
		上传文件：<input type="file" name="file"/><!--  后台action中必须有File file--name=file, String  fileFileName  -->
		上传文件：<input type="file" name="file"/><!--  后台action中必须有File file--name=file, String  fileFileName  -->
		上传文件：<input type="file" name="file"/><!--  后台action中必须有File file--name=file, String  fileFileName  -->
		<input type="submit" value="上传"/>
	</form>
</body>
</html>