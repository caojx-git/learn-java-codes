<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ognlForm.jsp</title>
</head>
<body>
	<s:form action="/user/form.action">
		<table border="1" borderColor="#cc4444" cellspacing="2" cellpadding="3">	
			<s:textfield name="name" label="用户名"></s:textfield>
			<s:password name="password" label="密码"></s:password>
			<s:radio list="#{1:'男',2:'女' }" name="gender" label="性别"></s:radio>
			<s:checkboxlist list="#{1:'足球',2:'篮球' }" name="hobby" label="兴趣爱好"/>
			<s:select list="#{1:'上海',2:'北京' }" name="city" label="所在城市" headerKey="-1" headerValue="请选择"></s:select>
			<tr>
				<td colspan="2" align="center">
					<s:submit type="image" src="123.png" theme="simple"></s:submit><!-- 使用简单布局 -->
					<s:reset value="重置" theme="simple"></s:reset>	
				</td>
			</tr>
		</table>
	</s:form>
	<s:debug/>
</body>
</html>