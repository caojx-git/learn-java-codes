<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>随机图片</title>
<script type="text/javascript">
function reloadImage() {
	//设置点击按钮时,按钮可以被点击
	document.getElementById('btn').disabled=true;
	//设置图片的路径，其中的ts='+new Date().getTime()必须设置，主要是不同的时间点产生不同的图片。
	document.getElementById("random").src='RandomServlet?ts='+new Date().getTime();
}
</script>
</head>
<body>
<img alt="图片没有找到" src="identity" id="random" onload="btn.disabled=false;">
<input type="button" value="换一个图片" onclick="reloadImage()" id="btn">
</body>
</html>