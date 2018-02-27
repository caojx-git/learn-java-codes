<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp" session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jsp页面 </title>
</head>
<body>
<font size="5" color="red"> 1)脚本元素</font><br/>
<%-- 
	  a)声明(Declaration)
	    语法: <%!声明内容%>
	    作用: 在servlet中声明一些成员变量、成员方法和内部类
	    特点: 声明被翻译进servlet后变成了成员变量、成员方法和内部类
	    注意: 不能使用表达式和隐含对象
  --%>
	<%!
		private String s="hello,word";
		public int getNum() {
			return 1;
		}
		
		class Test {
			
			public int a = 10;
			public String show() {
				return "hello,jsp";
			}
		}
	%>
<%-- 	b) 表达式(Expression)
	    语法: <%=expression%>
	    作用: 将expression输出到out(输出流)中，expression可以是算术、逻辑、关系表达式、变量、有返回值的方法、jsp中的9种隐含对象。
	    9种隐含对象: 
	      page pageContext request response session application out config exception
	    
	    特点: 表达式翻译进servlet后变成了out.print(expression),该代码处于
	    _jspService()方法中;

	    注意: expression都一律被转换成字符串后再写到输出流out(JspWriter)中。另外，expression中不能有分号(;)。 
--%>
	<%=page %><br/>
	<%=pageContext %><br/>
	<%=request %><br/>
	<%=response %><br/>
	<%=session %><br/>
	<%=application %><br/>
	<%=out %><br/>
	<%=config %><br/>
	<%-- <%=exception %> --%>
	
	
<%-- 	c)脚本(Scriptlet)
	     语法: <%java code%>
	     作用: 在jsp中嵌入java代码，不能嵌入成员变量、成员方法。
	     特点: 脚本被翻译进servlet的_jspService()方法中。 
	     
--%>
	<%
		//int a = 10/0;//异常跳转到error.jsp页面
		for(int i = 0; i < 10; i++) {
			out.println("briup"+i+"<br/>");
		}
	%>
	<%=getNum()+"<br/>"%>
	<%=new Test().show() %><br/>
	
	<font size="5" color="red"> 2) 指令元素</font><br/>
<%-- 	
	b) include指令
	     语法: <%@ include file="url"%>，称为静态导入(静态包含)
	     作用: 在一个页面中导入另一个页面的内容(这些内容一般不会有变化，如公司的标题和版权等信息)。
	     特点: 在jsp翻译成servlet后就将被导入的页面内容嵌入到servlet中。
	           导入时间发生在翻译阶段。
	     被导入的资源: html、xml、jsp等

	     优点: 执行效率高
	     缺点: 当被导入页面的内容发生变化，那么jsp必须要重新被翻译。
	     
 --%>
	<font size="5" color="red">第一个jsp页面</font>
	<%@include file="first.jsp" %>
	
	<!-- 动态导入  -->
	<jsp:include page="first.jsp">
		<jsp:param value="zhangsan" name="name"/>
	</jsp:include>
		<!-- 内部跳转到jsp -->
	<%-- <jsp:forward page="first.jsp">
		<jsp:param value="20" name="age"/>
	</jsp:forward> --%>
</body>
</html>