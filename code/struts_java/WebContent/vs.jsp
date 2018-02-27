<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>vs.jsp</title>
</head>
<body>
this is vs.jsp
<c:debug/>
<c:property value="locale"/>
<c:property value="name"/><!-- name是VsAndAc.java文件中的成员变量名，提供get set方法后就可以用c:property获取其value值 -->
<c:property value="student.name"/>
<c:property value="[1].name"/><!-- 如果值栈区出现同名的property，则使用数组来取，默认是取第一个  name--[0].name -->
<c:property value="#name"/><!--ac.put放置的东西需要#获取  -->
<c:property value="#request.name"/>
<c:property value="#student2.name"/>
</body>
</html>