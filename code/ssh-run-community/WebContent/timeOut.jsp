<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Refresh" content="5;url=login.jsp"><!--  5秒后跳转刷新-->

<title>注册成功</title>
<script language="javascript">
	var times = 6;
	clock();
	function clock() {
		window.setTimeout('clock()', 1000);
		times = times - 1;
		time.innerHTML = times;
	}
</script>
<style type="text/css">
.STYLE1 {
	color: #FF0000
}
</style>
</head>
<body>
	注册成功，该页面将在<span class="STYLE1" id= "time" >5</span>秒后跳转到登陆页面。。。等不了了，<a href="login.jsp">直接跳转</a>
</body>
</html>