<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>ognl输出字符串</h2>
	<s:property value="hello world"/><!-- "这里是变量或表达式" -->
	<s:property value="'helloworld'"/><!--  "'这里是字符串'"-->
	<s:property value="1+1"/>
	<s:property value="1+1*2+-3"/>
	<s:property value="1>1"/>
	<s:property value="1==1 || 1 > 1"/>
	<s:property value="1>1?'yes':'no'"/>
	<s:property value="128>>2"/>
	<s:property value="32<<2"/>
	<hr/>
	<h2>从ValueStack中取值</h2>
	<s:property value="student"/>
	<s:property value="student.name"/>
	<s:property value="student.age"/>
	<s:property value="student.getGender()"/>
	<h2>调用类中的构造器创建对象,并且调用对象的方法</h2>
	<s:property value="new com.briup.bean.Student('rose').getName()"/><!-- 可以使用全类名调用构造器 -->
	<h2>直接调用字符串对象的方法</h2>
	<s:property value="'abc'.equals('abc')"/><!--可以使用String的API  -->
	<s:property value="'abc'.substring(1,2)"/>
	<hr/>
	<h2>从ActionContext中取值</h2>
	<s:property value="#student"/>
	<s:property value="#student.name"/>
	<s:property value="#student.age"/>
	<s:property value="#student.getGender()"/>
	<s:property value="#action.student"/><!-- action指向我们的OgnlAction  -->
	<hr/>
	<h2>parameters和attr</h2>
	<s:property value="#parameters.score"/><!-- paramerters从request范围取值 -->
	<s:property value="#session.name"/>
	<s:property value="#attr.name"/><!-- attr依次从pageContext request session application ActionContext -->

	<hr/>
	<h2>@@表达式</h2>
	<s:property value="@java.lang.Integer@parseInt(#parameters.score)>9"/>
	<s:property value="@java.lang.Math@abs(-1)"/>
	<s:property value="@java.lang.Math@random()"/>
	<hr/>
	<h2>创建List集合/Map集合</h2>
	<s:property value="{'hello','world','hellworld'}"/><!-- List -->
	<s:property value="#{1:'hello',2:'world',3:'hellworld'}"/><!-- Map -->
	<hr/>
	<s:if test="@java.lang.Integer@parseInt(#parameters.score)>=90">
		优秀
	</s:if>
	<s:elseif test="@java.lang.Integer@parseInt(#parameters.score)>=80">
		良好
	</s:elseif>
	<s:elseif test="@java.lang.Integer@parseInt(#parameters.score)>=70">
		中等
	</s:elseif>
	<s:elseif test="@java.lang.Integer@parseInt(#parameters.score)>=60">
		及格
	</s:elseif>
	<s:else>
		不及格	
	</s:else>
	<hr/>
	<s:property value="'集合遍历'"/><br/>
	<s:property value="list"/><!-- 输出数组 -->
	<s:property value="list.size"/>
	<s:property value="list[0]"/><br/><!-- 0是索引 -->
	<s:property value="map"/>
	<s:property value="map.size"/>
	<s:property value="map[1]"/><br/><!-- 1是key值 -->
	<s:property value="map.keys"/>
	<s:property value="map.values"/>
	<hr/>
	<h2>iterator遍历集合</h2>
	<s:iterator value="list">
		<s:property value="name"/>
		<s:property value="gender"/>
		<s:property value="age"/>
	</s:iterator>
	<s:iterator id="stu" value="list"><!--  使用id-->
		<s:property value="#stu.name"/>
		<s:property value="#stu.gender"/>
		<s:property value="#stu.age"/>
	</s:iterator>
	<hr/>
	<s:iterator value="map">
		<s:property value="key"/>
		<s:property value="value.name"/>
		<s:property value="value.gender"/>
		<s:property value="value.age"/>
	</s:iterator>
	<s:iterator id="stu" value="map"><!--  使用id-->
		<s:property value="#stu.key"/>
		<s:property value="#stu.value.name"/>
		<s:property value="#stu.value.gender"/>
		<s:property value="#stu.value.age"/>
	</s:iterator>
	<hr>
	<h2>set标签</h2>
	<s:set name="myName" value="'tom'"/><!-- 给变量myName赋值tom -->
	<s:set name="my.name" value="'jack'"/>
	<s:set name="name" value="'rose'" scope="request"/><!-- 将name变量存放在request范围中 -->
	<s:set name="myList" value="{'hello','world','helloworld'}"/>
	<s:set name="myMap" value="#{1:'hello',2:'world',3:'helloworld'}"/>
	<s:property value="#myName"/>
	<s:property value="#attr['my.name']"/>
	<s:property value="#request.name"/>
	<s:property value="#myList"/>
	<s:property value="#myList.size"/>
	<s:property value="#myList[0]"/><!--0代表索引  -->
	<s:property value="#myMap"/>
	<s:property value="#myMap[1]"/> <!-- 1代表key值 -->
	<s:property value="#myMap.keys"/>
	<s:property value="#myMap.values"/>
	<s:debug/>
</body>
</html>