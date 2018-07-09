#JSP 学习笔记
作者：何金达

[TOC]

### 一、JSP

参考博文

https://blog.csdn.net/qq_28334041/article/details/70153825#t1



### 二、JSTL

什么是jstl

jstl是java中的一个定制标签库集

为什么要使用JSTL

1.实现了jsp页面中的代码复用，重复使用功能相同的代码

2.书写jsp页面时可读性更强，以标签对的形式出现和前端代码更加相似



#### 2.1 jstl的环境搭建

下载jstl的jar包，添加到web目录下

jstl的使用

先使用<%@ page  url=""  prefic="c" %>引入你需要的标签包

#### 2.2 jstl的四大分类

核心标签  格式化标签 SQL标签 XML标签

EL表达式：与JSTL搭配使用，用于获取程序中的变量

EL表达式的格式：用美元符号‘$’定界，内容包括在花括号'{}'中   

${表达式}

例子： .  与 [] 通常情况下可以通用

```jsp
"$ {users.sex}"  <===> "${user["sex"]"
```

特殊情况下不能通用：

1.包含特殊字符

${users.first-name}   这是错误的

${user["first-name"]} 这才是正确的

2.通过变量动态取值：只能写--->${users[param]}

EL的类型转换(count为字符串会自动转换为int)："${param.count+20}"

EL判断对象是否为空的方法："${empty  username}" 这个判空无论对象为空还是内容为空，都会返回true

#### 2.3 jstl标签的使用

##### 1. out标签

```jsp
<!-- 使用out标签输出常量-->
<c:out values="this is a const values"></c:out>
<!-- 使用out标签输出变量-->
<%  session.setAttribute("name","Jessica")  %>
<c:out values="${name}"></c:out>
<!-- 当变量不存在时，输出default中的内容-->
<c:out values="${name2}" default="" ></c:out>
<!-- 当输出的内容中存在需要转义的特殊符号时-->
<c:out values="&ltout标签&gt" escapeXml="false" ></c:out>
```

##### 2. set标签

```jsp
<!--存值到scope中-01-->
<c:set values="todays" var="day" scope="session"></c:set>
<!--存值到scope中-02-->
<c:set  var="day" scope="application">today</c:set>
<!--存值都javaBean中-->
<jsp:useBean id="对象名" class="包括包名具体类的地址">
<c:set  target="${对象名}" property="属性名" values="变量值" ></c:set>
<c:set  target="${对象名}" property="属性名"  >变量值</c:set>
```

##### 3. remove标签

```jsp
<c:set values="todays" var="day" scope="session"></c:set>
<c:remove var="day" /> 
```

##### 4. catch标签

catch标签中可以包含那些容易出错的JSTL标签

```jsp
<!-- 将错误信息存到变量error中-->
<c:catch var="error">
<c:set target="aa" property="bb" values="cc"></c:set>
</c:catch>
<c:out values="${error}"></c:out>

```

##### 5. if标签

同程序中的if作用相同，用来实现分支条件控制

1.test属性用于存放判断的条件，一般使用EL表达式来编写

2.var指定名称用来存放判断的结果类型为true或false

3.scope用来存放var属性存放的范围

```jsp
<input type="text" value="${param.score}"/>

<c:if test="${param.scope>=90}" var="result" scope="">
    <c:out value=恭喜你的成绩大于90""></c:out>
    </c:if>
<c:out values="${result}"></c:out>
```

##### 6. choose when otherwise标签  类似switch..case

通常这三个标签被放在一起配合使用

<c:choose>标签嵌套在<c:when>和<c:otherwise>的外面，作为他们的父标签使用

其中choose和when也可以一起组合使用

```jsp
<!-- choose、when、otherwise -->
<input type="text" value="${param.score}"/>
<c:choose>
	<c:when test="${param.score>=90 && param.score<=100}">
		<c:out value="优秀"></c:out>
	</c:when>
	<c:when test="${param.score>=80 && param.score<90}">
		<c:out value="良好"></c:out>
	</c:when>
	<c:when test="${param.score>=70 && param.score<80}">
		<c:out value="中等"></c:out>
	</c:when>
	<c:when test="${param.score>=60 && param.score<70}">
		<c:out value="及格"></c:out>
	</c:when>
	<c:when test="${param.score<60 && param.score>=0}">
		<c:out value="不及格"></c:out>
	</c:when>
    <!-- otherwise可以不写，相当于default-->
	<c:otherwise>
		<c:out value="您的输入有问题"></c:out>
	</c:otherwise>
</c:choose>
```

##### 7. foreach标签

根据循环条件遍历集合（Collection）中的元素

var设定变量名用于存储从集合中取出元素（必须无默认值）

items指定要遍历的集合（必须无默认值）

begin,end用于知道遍历的起始位置和终止位置（有默认）

step指定循环的步长（有默认）

varStatus通过index,count,first,last几个状态值，描述begin和end子集中的元素的状态

```jsp
<!-- forEach标签的用法 -->
<%
	List animals = new ArrayList();
	animals.add("tiger");
	animals.add("dog");
	animals.add("elephant");
	animals.add("lion");
	animals.add("bird");
	animals.add("fish");
	animals.add("bear");
	request.setAttribute("animals", animals);
%>

<!--全部遍历-->
<c:forEach var="ani" items="${animals}">
	<c:out value="${ani}"></c:out><br>
</c:forEach>
<c:out value="-------------------------"></c:out><br>
<!--部分遍历-->
<c:forEach var="ani" items="${animals}" begin="2" end="4">
	<c:out value="${ani}"></c:out><br>
</c:forEach>
<c:out value="-------------------------"></c:out><br>
<!--部分遍历并指定步长-->
<c:forEach var="ani" items="${animals}" begin="2" end="4" step="2">
	<c:out value="${ani}"></c:out><br>
</c:forEach>
<c:out value="-------------------------"></c:out><br>
<!--部分遍历并描述状态-->
<c:forEach var="ani" items="${animals}" begin="2" end="4" varStatus="a">
	<c:out value="原集合中的索引值${a.index}"></c:out><br>
	<c:out value="总共已迭代的次数${a.count}"></c:out><br>
	<c:out value="判断当前元素是否是子集中的第一个值${a.first}"></c:out><br>
	<c:out value="判断当前元素是否是子集中的最后一个值${a.last}"></c:out><br>
	<c:out value="======"></c:out><br>
</c:forEach><br>
```

##### 8. fortokens标签，相当于split()

用于浏览字符串，并根据指定字符串进行截取

items指定被迭代的字符串

delims指定使用的分隔符

var指定用来存放遍历到的成员

```jsp
<c:forTokens items="010-88668543-1033" delims="-" var="num">
	<c:out value="${num}"></c:out><br>
</c:forTokens>
```

##### 9. import标签的用法

可以把其他静态或动态文件包含到当前jsp页面中

同<jsp:include>的区别为：只能包含同一个web应用中的文件

而<c:import>可以包含其他web应用中的文件，甚至是网咯上的资源

```jsp
<!-- import标签的用法 -->
<!-- 导入网络上的绝对路径 -->
<c:catch var="error09">
	<c:import url="http://www.imooc.com"></c:import>
</c:catch>
<c:out value="${error09}"></c:out><br>
<!-- 导入相对路径文件 -->
<c:catch var="error08">
	<c:import url="tt.txt" charEncoding="gbk"></c:import>
</c:catch>
<c:out value="${error08}"></c:out><br>

<!--pageScope、requestScope、sessionScope和applicationScope都是EL 的隐含对象，由它们的名称可以很容易猜出它们所代表的意思，例如：${sessionScope.username}是取出Session范围的username 变量。-->
<!-- var及scope属性的用法 -->
<c:catch var="error07">
	<c:import url="tt.txt" var="tt" scope="session" charEncoding="gbk"></c:import>
</c:catch>
<c:out value="${error07}"></c:out><br>
<c:out value="import标签存储的tt字符串变量值为：${sessionScope.tt}"></c:out><br>



```

##### 10. redirect标签

该标签用来实现请求的重定向，同时可以在URL中加入指定的参数

url指定重定向页面的地址

```jsp
<c:redirect url="first.jsp">
<c:param  name="username">admin</c:param>
 <c:param  name="password">123</c:param>
</c:redirect>
```

##### 11. url标签

value 表示url路径值

var 将url路径存储在变量中

scope  var变量的范围

```jsp
<c:set var="parturl"> aa</c:set>
<c:url value="http://localhost:8080/${parturl}" var="newUrl" scope="session"></c:url>
<a href="${newUrl}">型的url</a>

```

#### 2.4 jstl函数

使用JSTL需要先引入

```jsp
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
fn:contains 判断字符串是否包含另外一个字符串 <c:if test="${fn:contains(name, searchString)}"> 
fn:containsIgnoreCase 判断字符串是否包含另外一个字符串(大小写无关) <c:if test="${fn:containsIgnoreCase(name, searchString)}"> 
fn:endsWith 判断字符串是否以另外字符串结束 <c:if test="${fn:endsWith(filename, ".txt")}"> 
fn:escapeXml 把一些字符转成XML表示，例如<字符应该转为< ${fn:escapeXml(param:info)} 
fn:indexOf 子字符串在母字符串中出现的位置 ${fn:indexOf(name, "-")} 
fn:join 将数组中的数据联合成一个新字符串，并使用指定字符格开 ${fn:join(array, ";")} 
```



