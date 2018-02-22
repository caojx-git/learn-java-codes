<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
${info }
欢迎你!
<shiro:hasRole name="admin">
	欢迎有admin角色的用户！<shiro:principal/>
</shiro:hasRole>
<shiro:hasPermission name="student:create">
	欢迎有student:create权限的用户！<shiro:principal/>
</shiro:hasPermission>
</body>
</html>