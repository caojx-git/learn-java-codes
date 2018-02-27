<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="bean.User,java.util.*" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>	
<%
		session.setAttribute("name", "briup");
		User u=new User();
		request.setAttribute("user", u);
		pageContext.setAttribute("score", 90);
		String[] arr=new String[]{"arr1","arr2","arr3","arr4"};
		List list=new ArrayList();
		list.add("list1");
		list.add("list2");
		list.add("list3");
		list.add("list4");
	
		List list2=new ArrayList();
		list2.add(new User(1,"zhangsan1"));
		list2.add(new User(2,"zhangsan2"));
		list2.add(new User(3,"zhangsan3"));
		list2.add(new User(4,"zhangsan4"));
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("key1","value1");
		map.put("key2","value2");
		map.put("key3","value3");
		map.put("key4","value4");
		Map<String,User> map2=new HashMap<String,User>();
		map2.put("1", new User(1,"lisi1"));
		map2.put("2", new User(2,"lisi2"));
		map2.put("3", new User(3,"lisi3"));
		map2.put("4", new User(4,"lisi4"));
		
		session.setAttribute("arr", arr);
		session.setAttribute("list", list);
		session.setAttribute("map", map);
		session.setAttribute("list2",list2);
		session.setAttribute("map2",map2);
	%>
	<h2>jstl标签演示 </h2>
	<c:out value="hello,jstl  &amp el" escapeXml="false" default="haha"></c:out>
	<br>
	<c:out value="${name }"></c:out>
	<!-- 如果没有指明任何的范围根据key来查找对应的value分别从page request session 
	和 application中去查找name的值
	(scope.getAttribute("name")),scope为上面四种范围。
	-->
	<br>
	<c:set var="test" value="haha" scope="application"></c:set>
	<br>
	<c:out value="${test }"></c:out>
	<br>
	<c:set target="${user }" property="name" value="zhangsan"></c:set>
	<c:out value="${user.name }"></c:out>
	<br>
	<c:remove var="user" scope="request"/>
	<%-- <c:out value="${user }"></c:out> --%>
	<c:catch var="e">
		<%
			int  x=10/0;
		%>
	</c:catch>
	<c:out value="${e }"></c:out>
	<br>
	<c:if test="${1<2 }">
		条件为真
	</c:if>
	<br>
	<c:if test="${score >=90 && score<=100}">
		优秀
	</c:if>
	<c:if test="${score>=80 && score<90 }">
		良好
	</c:if>
	<c:if test="${score>=60 }">
		及格
	</c:if>
	<c:if test="${score<60 }">
			不及格
</c:if>
	<br>
	<c:choose>
		<c:when test="${score>=90 }">
			优秀
		</c:when>
		<c:when test="${score>=80 }">
			良好
		</c:when>
		<c:when test="${score>=60 }">
			及格
		</c:when>
		<c:otherwise>
			不及格
		</c:otherwise>
	</c:choose>
	<br>
	<c:forEach items="${arr }" var="ar" begin="2" end="3" varStatus="num">
		${ar }===${num.count }<br>
	</c:forEach>
		<c:forEach items="${sessionScope.list }" var="li">
		${li }<br>
	</c:forEach>
	<c:forEach items="${sessionScope.map }" var="m">
		${m.key }===${m.value }<br>
	</c:forEach>
<c:forEach items="${sessionScope.list2 }" var="li2" varStatus="number">
		${li2.id }===${li2.name }===${number.count }===${number.index }===${number.first }===${number.last }<br>
	</c:forEach>
	<c:forEach items="${sessionScope.map2 }" var="m2">
		${m2.key }===${m2.value.name }<br>
	</c:forEach>
	<c:forTokens items="briup|briup2|briup3|sssss|" delims="|" var="a">
		${a }<br>
	</c:forTokens>
	<c:import url="../index.html" var="path" scope="session">
		<c:param name="name">briup</c:param>
	</c:import>
	<c:out value="${path }"></c:out>
	<c:url value="../index.html" var="myurl" scope="request"></c:url>
	<a href="${myurl }">超链接</a>
	<br>
<%-- 	<c:redirect url="../index.html">
		<c:param name="name" value="zhangsan"></c:param>
	</c:redirect> --%>
	${1+1 }
	${1>2 && false }
	<%
		String s=null;
	session.setAttribute("str", s);
	%>
	${empty s }
	${pageContext.servletContext}
	<br>
	${pageContext.session.id}
	<br>
	${param.name }
	<br>
	
	<c:forEach items="${paramValues.hobby }" var="s">
		${s }<br>
	</c:forEach>
	<br>
	${header.host }
	<%
	Cookie cookie = new Cookie("height","100");
	Cookie cookie2 = new Cookie("width","200");
	response.addCookie(cookie);
	response.addCookie(cookie2);
%>
	${cookie.height }<!-- 获取cookie对象 -->
	${cookie.height.name }
	${cookie.height.value }
	${initParam.test }
</body>
</html>







