[toc]

##一、mybatis入门实现增删改查
这里我们使用mybatis去实现一个自动回复机器人的功能的实现。


###1.1实现页面跳转
#### 1.1.1 ListServlet.java实现页面跳转 

在src下边新建com.imooc.servlet包，将ListServlet.java类放在这个包下

```java
package com.imooc.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		// 向页面跳转
		req.getRequestDispatcher("/WEB-INF/jsp/back/list.jsp").forward(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
```
项目结构图

![mybatis project](/home/caojx/learn/notes/images/mybatis/mybatis-project1.png)


#### 1.1.2 list.jsp页面

list.jsp页面用于展示页面列表，这个文件放在WEB-INF/jsp/back/list.jsp中。

```jsp
﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <html xmlns="http://www.w3.org/1999/xhtml">
 	<%
     String path = request.getContextPath();
     String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
     %>
     <head>
 		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 		<meta http-equiv="X-UA-Compatible"content="IE=9; IE=8; IE=7; IE=EDGE" />
 		<title>内容列表页面</title>
 		<link href="<%= basePath %>css/all.css" rel="stylesheet" type="text/css" />
 	</head>
 	<body style="background: #e1e9eb;">
 		<form action="" id="mainForm" method="post">
 			<div class="right">
 				<div class="current">当前位置：<a href="javascript:void(0)" style="color:#6E6E6E;">内容管理</a> &gt; 内容列表</div>
 				<div class="rightCont">
 					<p class="g_title fix">内容列表 <a class="btn03" href="#">新 增</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn03" href="#">删 除</a></p>
 					<table class="tab1">
 						<tbody>
 							<tr>
 								<td width="90" align="right">演示字段1：</td>
 								<td>
 									<input type="text" class="allInput" value=""/>
 								</td>
 								<td width="90" align="right">演示字段2：</td>
 								<td>
 									<input type="text" class="allInput" value=""/>
 								</td>
 	                            <td width="85" align="right"><input type="submit" class="tabSub" value="查 询" /></td>
 	       					</tr>
 						</tbody>
 					</table>
 					<div class="zixun fix">
 						<table class="tab2" width="100%">
 							<tbody>
 								<tr>
 								    <th><input type="checkbox" id="all" onclick="#"/></th>
 								    <th>序号</th>
 								    <th>演示字段1</th>
 								    <th>演示字段2</th>
 								    <th>操作</th>
 								</tr>
 								<tr>
 									<td><input type="checkbox" /></td>
 									<td>1</td>
 									<td>演示值1</td>
 									<td>演示值2</td>
 									<td>
 										<a href="#">修改</a>&nbsp;&nbsp;&nbsp;
 										<a href="#">删除</a>
 									</td>
 								</tr>
 								<tr style="background-color:#ECF6EE;">
 									<td><input type="checkbox" /></td>
 									<td>2</td>
 									<td>演示值1</td>
 									<td>演示值2</td>
 									<td>
 										<a href="#">修改</a>&nbsp;&nbsp;&nbsp;
 										<a href="#">删除</a>
 									</td>
 								</tr>
 								<tr>
 									<td><input type="checkbox" /></td>
 									<td>3</td>
 									<td>演示值1</td>
 									<td>演示值2</td>
 									<td>
 										<a href="#">修改</a>&nbsp;&nbsp;&nbsp;
 										<a href="#">删除</a>
 									</td>
 								</tr>
 								<tr style="background-color:#ECF6EE;">
 									<td><input type="checkbox" /></td>
 									<td>4</td>
 									<td>演示值1</td>
 									<td>演示值2</td>
 									<td>
 										<a href="#">修改</a>&nbsp;&nbsp;&nbsp;
 										<a href="#">删除</a>
 									</td>
 								</tr>
 							</tbody>
 						</table>
 						<div class='page fix'>
 							共 <b>4</b> 条
 							<a href='###' class='first'>首页</a>
 							<a href='###' class='pre'>上一页</a>
 							当前第<span>1/1</span>页
 							<a href='###' class='next'>下一页</a>
 							<a href='###' class='last'>末页</a>
 							跳至&nbsp;<input type='text' value='1' class='allInput w28' />&nbsp;页&nbsp;
 							<a href='###' class='go'>GO</a>
 						</div>
 					</div>
 				</div>
 			</div>
 	    </form>
 	</body>
 </html>
```

项目结构图
![](/home/caojx/learn/notes/images/mybatis/mybatis-project2.png)

####1.1.3 web.xml文件

```xml

<?xml version="1.0" encoding="UTF-8"?>
<web-app id="WebApp_ID" version="2.4" xmlns="http://java.sun.com/xml/ns/j2ee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd">
	<display-name>mybatis</display-name>
	<welcome-file-list>
		<welcome-file>index.html</welcome-file>
		<welcome-file>index.htm</welcome-file>
		<welcome-file>index.jsp</welcome-file>
		<welcome-file>default.html</welcome-file>
		<welcome-file>default.htm</welcome-file>
		<welcome-file>default.jsp</welcome-file>
	</welcome-file-list>
	<servlet>
		<servlet-name>ListServlet</servlet-name>
		<servlet-class>com.imooc.servlet.ListServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>ListServlet</servlet-name>
		<url-pattern>/list.action</url-pattern>
	</servlet-mapping>
```

####1.1.4 结果

下图是占时没有从数据库中获取数据，所以列表页面是空的
![](/home/caojx/learn/notes/images/mybatis/mybatis-list1.png)


###1.2 建表和连接数据库

####1.2.1 建表

新建db包，在下边建立db.sql数据库文件，文件内容如下

```sql
create table message(
	id number(10)  constraint message_pk_id primary key,
	command varchar2(20) not null,
	description varchar2(20) not null,
	content varchar2(200)
);

insert into message values(0001,'段子','精彩段子','精彩段子内容1');
insert into message values(0002,'段子','精彩段子','精彩段子内容2');
insert into message values(0003,'段子','精彩段子','精彩段子内容3');
insert into message values(0004,'段子','精彩段子','精彩段子内容3');
insert into message values(0005,'段子','精彩段子','精彩段子内容4');
insert into message values(0006,'段子','精彩段子','精彩段子内容5');
insert into message values(0007,'笑话','笑话段子','精彩段子内容1');
insert into message values(0008,'笑话','笑话段子','精彩段子内容2');
insert into message values(0009,'笑话','笑话段子','精彩段子内容3');
insert into message values(0010,'笑话','笑话段子','精彩段子内容4');
insert into message values(0011,'笑话','笑话段子','精彩段子内容5');
INSERT INTO message VALUES (0012, '查看', '精彩内容', '精彩内容');
INSERT INTO message VALUES (0013, '段子', '精彩段子', '如果你的月薪是3000块钱，请记得分成五份，一份用来买书，一份给家人，一份给女朋友买化妆品和衣服，一份请朋友们吃饭，一份作为同事的各种婚丧嫁娶的份子钱。');
INSERT INTO message VALUES (0014, '新闻', '今日头条', '7月17日，马来西亚一架载有298人的777客机在乌克兰靠近俄罗斯边界坠毁。另据国际文传电讯社消息，坠毁机型为一架波音777客机，机载约280名乘客和15个机组人员。');
INSERT INTO message VALUES (0015, '娱乐', '娱乐新闻', '昨日,邓超在微博分享了自己和孙俪的书法。夫妻同样写幸福，但差距很大。邓超自己都忍不住感慨字丑：左边媳妇写的。右边是我写的。看完我再也不幸福了。');
INSERT INTO message VALUES (0018, '电影', '近日上映大片', '《忍者神龟》[2]真人电影由美国派拉蒙影业发行，《洛杉矶之战》导演乔纳森·里贝斯曼执导');
INSERT INTO message VALUES (0017, '彩票', '中奖号码', '查啥呀查,你不会中奖的！');
```

####1.2.2 建立Message.java基础类

```java
package com.imooc.bean;

/**
 * 与消息表对应的实体类
 */
public class Message {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 指令名称
	 */
	private String command;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 内容
	 */
	private String content;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
```

项目结构图

![](/home/caojx/learn/notes/images/mybatis/mybatis-project3.png)

####1.2.3 ListServlet.java 从数据库中查询数据

```java
package com.imooc.servlet;

import com.imooc.bean.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:页面列表初始化
 * Created by caojx on 17-2-1.
 */
public class ListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "myoracle", "myoracle");
            String sql = "select id,command,description,content from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Message> messages = new ArrayList<Message>();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getString("id"));
                message.setCommand(resultSet.getString("command"));
                message.setDescription(resultSet.getString("description"));
                message.setContent(resultSet.getString("content"));
                messages.add(message);
            }
            req.setAttribute("messages", messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/jsp/back/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

```

结果
![](/home/caojx/learn/notes/images/mybatis/mybatis-list2.png)

###1.3 列表查询

####1.3.1 ListServlet.java接受页面传过来的参数，实现条件查询

```java
package com.imooc.servlet;

import com.imooc.bean.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:页面列表初始化
 * Created by caojx on 17-2-1.
 */
public class ListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //获取查询参数
            req.setCharacterEncoding("UTF-8");
            String command = req.getParameter("command");
            String description = req.getParameter("description");

            //连接数据库
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "myoracle", "myoracle");
            //String sql = "select id,command,description,content from message";
            //拼接sql
            StringBuilder sql = new StringBuilder("select id,command,description,content from message where 1=1");
            List<String> paramList = new ArrayList<String>();

            if(command!=null && !"".equals(command.trim())){
                sql.append(" and command = ?");
                paramList.add(command.trim());
            }
            if(description!=null && !"".equals(description.trim())){
                sql.append(" and description like '%' ? '%'");
                paramList.add(description.trim());
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            
            //设置参数
            for(int i = 0; i < paramList.size(); i++ ){
                preparedStatement.setString(i+1,paramList.get(i));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Message> messages = new ArrayList<Message>();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getString("id"));
                message.setCommand(resultSet.getString("command"));
                message.setDescription(resultSet.getString("description"));
                message.setContent(resultSet.getString("content"));
                messages.add(message);
            }
            req.setAttribute("command",command);
            req.setAttribute("description",description);
            req.setAttribute("messages", messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/jsp/back/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
```

结果
![](/home/caojx/learn/notes/images/mybatis/mybatis-list4.png)


###1.4 代码重构

上面的代码，没有分层，在正规的项目开发中还需要对代码进行分层

####1.4.1新建Dao层和Service业务逻辑层

1.MessageDao.java

对数据库进行操作，这里将ListServlet.java中对数据库
连接以及查询操作放到在这里。

```java
package com.imooc.dao;

import com.imooc.bean.Message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:和Message表相关的数据库操作
 * Created by caojx on 17-2-1.
 */
public class MessageDao {


    /**
     * Description:根据查询条件查询消息列表
     * @param command
     * @param description
     * */
    public List<Message> queryMessageList(String command,String description){
        List<Message> messages = null;
        try {
            //连接数据库
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "myoracle", "myoracle");
            //String sql = "select id,command,description,content from message";
            //拼接sql
            StringBuilder sql = new StringBuilder("select id,command,description,content from message where 1=1");
            List<String> paramList = new ArrayList<String>();

            if (command != null && !"".equals(command.trim())) {
                sql.append(" and command = ?");
                paramList.add(command.trim());
            }
            if (description != null && !"".equals(description.trim())) {
                sql.append(" and description like '%' ? '%'");
                paramList.add(description.trim());
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

            //设置参数
            for (int i = 0; i < paramList.size(); i++) {
                preparedStatement.setString(i + 1, paramList.get(i));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            messages = new ArrayList<Message>();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getString("id"));
                message.setCommand(resultSet.getString("command"));
                message.setDescription(resultSet.getString("description"));
                message.setContent(resultSet.getString("content"));
                messages.add(message);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return messages;
    }

}

```

2.MessageService.java

业务逻辑层，调用Dao层提供的方法，对数据库间接的操作。


```java
package com.imooc.service;

import com.imooc.bean.Message;
import com.imooc.dao.MessageDao;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Description:
 *
 * @author caojx Message业务相关功能
 * Created by caojx on 2017年02月26 下午6:56:56
 */
public class MessageService {

    private Logger logger = Logger.getLogger(MessageService.class);
    /**
     * Description:根据条件查询消息列表
     * @param command
     * @param description
     * */
    public List<Message> queryMessageList(String command, String description){
        MessageDao messageDao = new MessageDao();
        return messageDao.queryMessageList(command,description);
    }
}


```

3.ListServlet.java

这里将原来连接数据库中的功能迁移到了Dao层，直接调用service业务逻辑层中的方法对数据库进行操作。
```java
package com.imooc.servlet;

import com.imooc.bean.Message;
import com.imooc.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:页面列表初始化
 * Created by caojx on 17-2-1.
 */
public class ListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //获取查询参数
            req.setCharacterEncoding("UTF-8");
            String command = req.getParameter("command").trim();
            String description = req.getParameter("description").trim();

            //查询消息列表
            List<Message> messages = new ArrayList<Message>();
            MessageService messageService = new MessageService();
            messages = messageService.queryMessageList(command,description);

            //将参数和消息列表传回给页面
            req.setAttribute("command",command);
            req.setAttribute("description",description);
            req.setAttribute("messages", messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/jsp/back/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

```

结果：

![](/home/caojx/learn/notes/images/mybatis/mybatis-project4.png)
![](/home/caojx/learn/notes/images/mybatis/mybatis-list5.png)


##二、开始使用mybatis


###2.1.1准备jar包

![](/home/caojx/learn/notes/images/mybatis/mybatis-jar.png)


####2.1.2 使用mybatis连接数据库

使用mybatis连接数据库，会获取到一个SqlSession对象，这个对象的如下作用是

1.想sql语句中传入参数

2.执行sql语句

3.获取sql语句的结果

4.对事物进行控制

如何获取SqlSession：

1.通过配置文件获取数据库连接相关信息

2.通过配置文件构建SqlSessionFatory

3.通过SqlSessionFactory打开一个数据库会话

####2.1.3 Configuration.xml mybatis核心配置文件

mybatis与hiberante类似，也有一个核心配置文件，这个配置文件名默认为
Configuration.xml，这个文件主要配置数据库连接的相关信息，实体类映射文件，
和分页插件等。

这里先配置数据库的信息

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!-- Copyright 2009-2012 the original author or authors. Licensed under the 
	Apache License, Version 2.0 (the "License"); you may not use this file except 
	in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
	Unless required by applicable law or agreed to in writing, software distributed 
	under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES 
	OR CONDITIONS OF ANY KIND, either express or implied. See the License for 
	the specific language governing permissions and limitations under the License. -->
<!DOCTYPE configuration
    PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
	<properties>
		<property name="dialect" value="oracle" />
	</properties>

	<environments default="development">
		<environment id="development">
			<transactionManager type="JDBC"></transactionManager>
			<dataSource type="UNPOOLED">
				<property name="driver" value="oracle.jdbc.driver.OracleDriver" />
				<!-- <property name="url" value="jdbc:oracle:thin:@localhost:1521:orcl"/> -->
				<property name="url" value="jdbc:oracle:thin:@localhost:1521:xe" />
				<property name="username" value="myoracle" />
				<property name="password" value="myoracle" />
			</dataSource>
		</environment>
	</environments>
	
	<!--导入sql配置文件-->
    <mappers>
    	<mapper resource="com/imooc/config/sqlxml/Message.xml" />
    </mappers>
    	
</configuration>
```

####2.1.4 DBAccess.java连接数据库的工具类

这个类，主要用于mybatis读取配置文件，获取SqlSession，新建DBAccess.java类

```java
package com.imooc.db;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


/**
 * 获取数据库连接
 * */
public class DBAccess {
	
	public SqlSession getSqlSession(){
		SqlSession session = null;
		try{
			//读取配置文件获取数据库连接信息
			Reader reader = Resources.getResourceAsReader("com/imooc/config/Configuration.xml");
			//获取sqlSessionFactory
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			//获取session
			session = sessionFactory.openSession();
		}catch(Exception e){
			e.printStackTrace();
		}
		return session;
	}
	
	public static void main(String[] args) {
		System.out.println(new DBAccess().getSqlSession());
	}
}
```

####2.1.5 MessageDao.java

这里使用mybatis数据库进行查询，修改MessageDao.java的内容
 
 ```java
package com.imooc.dao;

import com.imooc.bean.Message;
import com.imooc.db.DBAccess;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:和Message表相关的数据库操作
 * Created by caojx on 17-2-1.
 */
public class MessageDao {
    /**
     * Description:根据查询条件查询消息列表
     * @param command
     * @param description
     * */
    public List<Message> queryMessageList(String command,String description) throws Exception{
        List<Message> messages = null;
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        try {
            //获取sqlSession
            sqlSession = dbAccess.getSqlSession();
            /*
             * 通过sqlSession执行sql语句,执行那个映射文件中的sql由传入的内容决定一般是namespace+id
             * selectList，查询的时候是可以传入查询参数的，不过只能传入一个参数，所以需要将参数封装到某个对象中
             * 第一个参数格式：namespace.id(方法名)
             * 第二个额参数：sql需要传入的参数
             */
            messages = sqlSession.selectList("com.imooc.bean.Message.queryMessageList");
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("查询Message出错",e);
        }finally {
            if(sqlSession != null){
                sqlSession.close();
            }
        }
        return messages;
    }
}
```

####2.1.6 Message.xml

使用mybatis的时候，一般每个实体类会对应一个sql配置文件，查询的sql写在配置文件中，这里与使用
hibernate不同。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--

       Copyright 2009-2012 the original author or authors.

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.

-->

<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--相同的namespace下的id是不能重复的，这个是必须要写的，这个我们一般些全类名-->
<mapper namespace="com.imooc.bean.Message">

	<!--
    		配置映射关系
    		配置数据库表中的字段，对应java实体类中的字段，如果不配置需要实体类中的属性与数据库表中的字段一致
    		type即对应的实体类名
    		id：取一个唯一的值就行，起到唯一标识的作用，不用与select中的id一致，但是需要与select标签中的resultMap的值一致
    -->
  <resultMap type="com.imooc.bean.Message" id="MessageResult">
	  <!--column 是数据库中字段的名成，property是表对应的试题类Message中的字段
	  jdbcType,可以在java.sql.Types中找到对应的常量
	  -->
    <id column="id" jdbcType="INTEGER" property="id"/><!--如果数据库的字段作为主键，则使用id标签-->
    <result column="command" jdbcType="VARCHAR" property="command"/><!--如果数据库的字段是u普通的列，则使用result标签-->
    <result column="description" jdbcType="VARCHAR" property="description"/>
    <result column="content" jdbcType="VARCHAR" property="content"/>
  </resultMap>

<!-- select标签用于书写查询select语句-->
  <select id="queryMessageList" resultMap="MessageResult">
    select id,command,description,content from message where 1=1
  </select>

</mapper>
```
项目结构图
![](/home/caojx/learn/notes/images/mybatis/mybatis-project5.png)

结果
![](/home/caojx/learn/notes/images/mybatis/mybatis-list6.png)


###2.2动态sql拼接

在前边，我们已经可以正常的使用mybatis了，不过查询条件还没有传入到配置文件的sql中,我们可以将查询
条件设置到某个对象中，在配置文件中动态的拼接出来。

####2.2.1 MessageDao.java

对这个类进行修改，将查询参数传入到sql中

```java
package com.imooc.dao;

import com.imooc.bean.Message;
import com.imooc.db.DBAccess;
import org.apache.ibatis.session.SqlSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:和Message表相关的数据库操作
 * Created by caojx on 17-2-1.
 */
public class MessageDao {


    /**
     * Description:根据查询条件查询消息列表
     * @param command
     * @param description
     * */
    public List<Message> queryMessageList(String command,String description) throws Exception{
        List<Message> messages = null;
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        try {
            //获取sqlSession
            sqlSession = dbAccess.getSqlSession();
            //将参数封装到Message对象中
            Message message = new Message();
            message.setCommand(command);
            message.setDescription(description);
            //通过sqlSession执行sql语句,执行那个映射文件中的sql由传入的内容决定一般是namespace+id
            //selectList，查询的时候是可以传入查询参数的，不过只能传入一个参数，所以需要将参数封装到某个对象中
            messages = sqlSession.selectList("com.imooc.bean.Message.queryMessageList",message);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("查询Message出错",e);
        }finally {
            if(sqlSession != null){
                sqlSession.close();
            }
        }
        return messages;
    }

}

```

#### 2.2.2 Message.xml 中动态拼接sql

mybatis中主要使用ognl表达式获取parameterType中传入的参数值，规则如下

![](/home/caojx/learn/notes/images/mybatis/mybatis-param1.png)

![](/home/caojx/learn/notes/images/mybatis/mybatis-param2.png)

Message.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--

       Copyright 2009-2012 the original author or authors.

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.

-->

<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--相同的namespace下的id是不能重复的，这个是必须要写的，这个我们一般些全类名-->
<mapper namespace="com.imooc.bean.Message">

	<!--
		配置映射关系
		配置数据库表中的字段，对应java实体类中的字段
		type即对应的实体类名
		id：取一个唯一的值就行，起到唯一标识的作用，不用与select中的id一致，但是需要与select标签中的resultMap的值一致
	-->
  <resultMap type="com.imooc.bean.Message" id="MessageResult">
	  <!--column 是数据库中字段的名成，property是表对应的试题类Message中的字段-->
    <id column="id" jdbcType="INTEGER" property="id"/><!--如果数据库的字段作为主键，则使用id标签-->
    <result column="command" jdbcType="VARCHAR" property="command"/><!--如果数据库的字段是u普通的列，则使用result标签-->
    <result column="description" jdbcType="VARCHAR" property="description"/>
    <result column="content" jdbcType="VARCHAR" property="content"/>
  </resultMap>

<!-- select标签用于书写查询select语句
	parameterType是传入的查询参数类型，如果是基本数据类，只需要写类型，不许需要写全类名
	resultMap与前边的resultMap的id对应
-->
  <select id="queryMessageList" parameterType="com.imooc.bean.Message" resultMap="MessageResult">
    select id,command,description,content from message
	  <where><!--
    		test中书写布尔表达式，如果成功则拼接下边的条件, command是parameterType中的成员变量
    		拼接sql的时候会将‘#{command}’替换成?,取值类似于ognl,拼接sql的时候不需要关注空格是否匹配
    		-->
		  <if test="command != null and !&quot;&quot;.equals(command.trim())">
			  and command=#{command}
		  </if>
		   <!-- 
		    like 查询一般会拼接concat()拼接两个字符串
          		    !&quot;&quot;.equals(description.trim()) 也可以换为 ''!=description.trim()
          	-->
		  <if test="description != null and ''!=description.trim()">
			  and description like concat(concat('%',#{description}),'%')
		  </if>
	  </where>
  </select>
</mapper>
```

查询结果

![](/home/caojx/learn/notes/images/mybatis/mybatis-list7.png)


##三、配置log4j日志输出

配置log4j的时候，我们只需要将jar包和配置文件放到相应的位置，就可以看到mybatis输出sql语句了。
我们将log4j的配置文件log4jfile.properties文件放到src目录下,mybaits就会识别出我们使用的是那种日志包，mybatis中
有LogFactory会动态的适配出对应的日志。
lo4j的配置文件有两种格式，这里简单的使用以下xxx.properties格式
还有一种是基于xml文件格式

xxx.properties都是key=value这种格式，=号两边不能有空格

log4j.properties的内容
```properties
log4j.rootLogger=DEBUG,Console,file //说明日志输出的 级别，位置1,位置2...
log4j.appender.Console=org.apache.log4j.ConsoleAppender
log4j.appender.Console.layout=org.apache.log4j.PatternLayout
log4j.appender.Console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n
log4j.logger.org.apache=INFO
```

>语法
其语法为：  
log4j.rootLogger = [ level ] , appenderName1, appenderName2, …  
level: 是日志记录的优先级，分为OFF、FATAL、ERROR、WARN、INFO、DEBUG、ALL或  
者您定义的级别。Log4j建议只使用四个级别，优 先级从高到低分别是ERROR、WARN、INFO、DEBUG。通过在这里定义的级别，您可以控制到应用程序中相应级别的日志信息的开关。比如在这里定 义了INFO级别，则应用程序中所有DEBUG级别的日志信息将不被打印出来。  
appenderName: 就是指定日志信息输出到哪个地方。您可以同时指定多个输出目的地。  
例如：log4j.rootLogger＝info,A1,B2,C3

####3.1日志的输出级别
log4j.rootLogger=DEBUG,Console,file 用于说明日志输出的级别，位置

其中
log4j.rootLogger为根logger，这个必须配置,用于指定默认的输出级别，和定义输出位置

DEBUG表示输出级别

级别有8中，级别从小到大 ALL<RACE<DEBUG<INFO<WARN<ERROR<FATAL<OFF，只有大于和等于设置的级别的日志才会输出
这里设置DEBUG级别，表示DEBUG，INFO，WARN，ERROR，FATAL级别的信息都会被输出

各个级别表示的含义
>OFF 、FATAL 、ERROR、WARN、INFO、DEBUG、TRACE 、ALL  
  OFF 为最高等级 关闭了日志信息  
  FATAL  为可能导致应用中止的严重事件错误  
  ERROR 为严重错误 主要是程序的错误  
  WARN 为一般警告，比如session丢失  
  INFO 为一般要显示的信息，比如登录登出  
  DEBUG 为程序的调试信息  
  TRACE 为比DEBUG更细粒度的事件信息  
  ALL 为最低等级，将打开所有级别的日志 

####3.2日志的输出位置
Console，表示输出的位置是控制台
不过输出位置，并不是完全由这里决定的，主要的是由类log4j.appender.Console=org.apache.log4j.ConsoleAppender决定
Console只是一个标识符号，没有什么意义，只要与log4j.appender.Console中最后一个单词对应即可，即
可以写成

log4j.rootLogger=DEBUG,A //说明日志输出的 级别，位置
log4j.appender.A=org.apache.log4j.ConsoleAppender

写成Console只是方便我们辨认

注意：输出位置可以写多个，也可以分开写比如说
log4j.rootLogger=DEBUG,A,B,C,..
后便对每种输出位置进行设置属性值

期输出位置主要有如下几种
>1.org.apache.log4j.ConsoleAppender（控制台）  
 2.org.apache.log4j.FileAppender（文件）  
 3.org.apache.log4j.DailyRollingFileAppender（每天产生一个日志文件）  
 4.org.apache.log4j.RollingFileAppender（文件大小到达指定尺寸的时候产生一个新的文件）  
 5.org.apache.log4j.WriterAppender（将日志信息以流格式发送到任意指定的地方）

####3.3日志的输出格式
log4j.appender.Console.layout=org.apache.log4j.PatternLayout是布局类

log4j.appender.Console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n 自定义输出格式
其中空格 []都会被原样输出
>%d 表示日志产生的时间<br/>
 %t 表示产生日志的线程名称<br/>
 %p 表示日志的级别，%5p,表示级别信息会占用5个字符位置，不足5位字符使用空格补齐，%-5p，其中表示又对齐。<<br/>
 %c 表示出书日志的全类名<br/>
 %m 表示输出的附加信息<br/>
 %n 表示换行<br/>
其他：<br/>
%p: 输出日志信息优先级，即DEBUG，INFO，WARN，ERROR，FATAL,  
%d: 输出日志时间点的日期或时间，默认格式为ISO8601，也可以在其后指定格式，比如：%d{yyyy MM dd HH:mm:ss,SS}，输出类似：2002年10月18日 22：10：28，921  
%r: 输出自应用启动到输出该log信息耗费的毫秒数  
%c: 输出日志信息所属的类目，通常就是所在类的全名  
%t: 输出产生该日志事件的线程名  
%l: 输出日志事件的发生位置，相当于%C.%M(%F:%L)的组合,包括类目名、发生的线程，以及在代码中的行数。举例：Testlog4.main (TestLog4.java:10)  
%x: 输出和当前线程相关联的NDC(嵌套诊断环境),尤其用到像java servlets这样的多客户多线程的应用中。  
%%: 输出一个"%"字符  
%F: 输出日志消息产生时所在的文件名称  
%L: 输出代码中的行号  
%m: 输出代码中指定的消息,产生的日志具体信息  
%n: 输出一个回车换行符，Windows平台为"/r/n"，Unix平台为"/n"输出日志信息换行  
可以在%与模式字符之间加上修饰符来控制其最小宽度、最大宽度、和文本的对齐方式。如：  
1)%20c：指定输出category的名称，最小的宽度是20，如果category的名称小于20的话，默认的情况下右对齐。  
2)%-20c:指定输出category的名称，最小的宽度是20，如果category的名称小于20的话，"-"号指定左对齐。  
3)%.30c:指定输出category的名称，最大的宽度是30，如果category的名称大于30的话，就会将左边多出的字符截掉，但小于30的话也不会有空格。  
4)%20.30c:如果category的名称小于20就补空格，并且右对齐，如果其名称长于30字符，就从左边较远输出的字符截掉。 



####3.4制定的特别包的输出级别

配置某个包下的特殊的输出级别
log4j.logger.org.apache=INFO，这里log4j.logger是固定的，org.apache表示需要特殊处理的包的输出级别为INFO

####3.5 配中多个输出模式

```properties
log4j.rootLogger=DEBUG,Console,file
log4j.appender.Console=org.apache.log4j.ConsoleAppender
log4j.appender.Console.layout=org.apache.log4j.PatternLayout
log4j.appender.Console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n
#指定org.apache这个包下的类的输出级别，特别指定某个包的输出级别
log4j.logger.org.apache=INFO 


log4j.appender.file=org.apache.log4j.RollingFileAppender
#修改指定输出位置为法file的输出级别为INFO,默认为DEBUG
log4j.appender.file.Threshold = INFO
log4j.appender.file.File=/home/caojx/temp/log.txt
log4j.appender.file.Append=true
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%d{yyyy MM dd} [%t] %-5p [%c] - %m%n
#指定文件最大为10MB，超过10MB的时候自动生成一个新的文件
log4j.appender.file.MaxFileSize=10MB
```

上边有添加一个输出级别DEBUG信息到文件中，不使用追加模式，如果要使用log4j，至少要配置一个log4j.rootLogger，类似于
log4j.logger.mylogger这种自定义日志组件的可以配置多个。
注意：=号两边不能有空格，有时候文件中不能写入内容，确认自己没有写错的话，换一个路径看一下。

####3.6 使用
```java
package com.imooc.servlet;

import com.imooc.bean.Message;
import com.imooc.service.ListService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:页面列表初始化
 * Created by caojx on 17-2-1.
 */
public class ListServlet extends HttpServlet {

    //使用log4j进行日志输出，这里简单的配置一样就好
    private static Logger logger = Logger.getLogger(ListServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //获取查询参数
            req.setCharacterEncoding("UTF-8");
            String command = req.getParameter("command");
            String description = req.getParameter("description");
            logger.info("queryMessageList开始，查询参数为"+command+","+description);
            //查询消息列表
            List<Message> messages = new ArrayList<Message>();
            ListService listService = new ListService();
            messages = listService.queryMessageList(command,description);

            //将参数和消息列表传回给页面
            req.setAttribute("command",command);
            req.setAttribute("description",description);
            req.setAttribute("messages", messages);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Message查询出错",e);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/back/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

```
####3.6 日志输出

运行项目后我们将会在控制台
![](/home/caojx/learn/notes/images/mybatis/mybatis-log4j.png)
在文件中看日志输出
![](/home/caojx/learn/notes/images/mybatis/mybatis-log4j2.png)


推荐文章：
http://blog.csdn.net/edward0830ly/article/details/8250412<br/>
http://blog.csdn.net/hu_shengyang/article/details/6754031<br/>
http://swiftlet.net/archives/683<br/>


##四、实现单条信息删除和批量删除

###4.1消息单条删除

####4.1.1Message.xml

数据单条删除先在Message.xml文件中添加删除的sql配置，	删除单条消息只需要传入messageId，即传入一个参数，为基本类型。
基本数据类型：只能传入一个。通过#{参数名}或者是#{_parameter} 即可获取传入的值,
复杂数据类型：包含JAVA实体类、Map。通过#{属性名}或#{map的KeyName}即可获取传入的值,
集合数据类型：如list，使用foreach标签遍历出list中的值。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--

       Copyright 2009-2012 the original author or authors.

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.

-->

<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--相同的namespace下的id是不能重复的，这个是必须要写的，这个我们一般些全类名-->
<mapper namespace="com.imooc.bean.Message">

	<!--
		配置映射关系
		配置数据库表中的字段，对应java实体类中的字段，如果不配置需要实体类中的属性与数据库表中的字段一致
		type即对应的实体类名
		id：取一个唯一的值就行，起到唯一标识的作用，不用与select中的id一致，但是需要与select标签中的resultMap的值一致
	-->
  <resultMap type="com.imooc.bean.Message" id="MessageResult">
	  <!--column 是数据库中字段的名成，property是表对应的试题类Message中的字段
	  	jdbcType,可以在java.sql.Types中找到对应的常量
	  -->
    <id column="id" jdbcType="INTEGER" property="id"/><!--如果数据库的字段作为主键，则使用id标签-->
    <result column="command" jdbcType="VARCHAR" property="command"/><!--如果数据库的字段是普通的列，则使用result标签-->
    <result column="description" jdbcType="VARCHAR" property="description"/>
    <result column="content" jdbcType="VARCHAR" property="content"/>
  </resultMap>

<!-- select标签用于书写查询select语句
	parameterType是传入的查询参数类型，如果是基本数据类，只需要写类型，不许需要写全类名
	resultMap与前边的resultMap的id对应
-->
  <select id="queryMessageList" parameterType="com.imooc.bean.Message" resultMap="MessageResult">
    select id,command,description,content from message
	  <where><!--
    		test中书写布尔表达式，如果成功则拼接下边的条件, command是parameterType中的成员变量
    		拼接sql的时候会将‘#{command}’替换成?,取值类似于ognl,拼接sql的时候不需要关注空格是否匹配
    		-->
		  <if test="command != null and !&quot;&quot;.equals(command.trim())">
			  and command=#{command}
		  </if>
		  <!-- like 查询一般会拼接concat()拼接两个字符串
		    !&quot;&quot;.equals(description.trim()) 也可以换为 ''!=description.trim()
		   -->
		  <if test="description != null and ''!=description.trim()">
			  and description like concat(concat('%',#{description}),'%')
		  </if>
	  </where>
  </select>

	<!--单条删除
	   基本数据类型：只能传入一个。通过#{参数名}或者是#{_parameter} 即可获取传入的值
	   复杂数据类型：包含JAVA实体类、Map。通过#{属性名}或#{map的KeyName}即可获取传入的值
	   集合数据类型：如list，使用foreach标签遍历出list中的值
	-->
	<delete id="deleteOne" parameterType="int">
		delete from message where id = #{id}
	</delete>

</mapper>
```
####4.1.2 MessageDao

添加deleteOne方法，对消息进行单条删除
```java
package com.imooc.dao;

import com.imooc.bean.Message;
import com.imooc.db.DBAccess;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:和Message表相关的数据库操作
 * Created by caojx on 17-2-1.
 */
public class MessageDao {

    private Logger logger = Logger.getLogger(MessageDao.class);
    /**
     * 根据查询条件查询消息列表
     * @param command 指令名称
     * @param description 描述
     * */
    public List<Message> queryMessageList(String command,String description) throws Exception{
        List<Message> messages = null;
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        try {
            //获取sqlSession
            sqlSession = dbAccess.getSqlSession();
            //将参数封装到Message对象中
            Message message = new Message();
            message.setCommand(command);
            message.setDescription(description);
            /*
             * 通过sqlSession执行sql语句,执行那个映射文件中的sql由传入的内容决定一般是namespace+id
             * selectList，查询的时候是可以传入查询参数的，不过只能传入一个参数，所以需要将参数封装到某个对象中
             * 第一个参数格式：namespace.id(方法名)
             * 第二个额参数：sql需要传入的参数
             */
            messages = sqlSession.selectList("com.imooc.bean.Message.queryMessageList",message);
        }catch (Exception e){
            logger.error("查询Message出错",e);
            throw new Exception("查询Message出错",e);
        }finally {
            if(sqlSession != null){
                sqlSession.close();
            }
        }
        return messages;
    }

    /**
     * Description：通过messageId对消息进行当条删除
     * @param id 消息id
     */
    public void deleteOne(Integer id) throws Exception{
        List<Message> messages = null;
        DBAccess dbAccess = new DBAccess();
        SqlSession sqlSession = null;
        try {
            //获取sqlSession
            sqlSession = dbAccess.getSqlSession();
            sqlSession.delete("com.imooc.bean.Message.deleteOne",id);
            sqlSession.commit();
        }catch (Exception e){
            logger.error("删除单条Message出错",e);
            throw new Exception("删除单条Message出错",e);
        }finally {
            if(sqlSession != null){
                sqlSession.close();
            }
        }
    }

}

```

####4.1.3MessageService.java

同样添加deleteOne方法
```java
package com.imooc.service;

import com.imooc.bean.Message;
import com.imooc.dao.MessageDao;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Description:
 *
 * @author caojx Message业务相关功能
 * Created by caojx on 2017年02月26 下午6:56:56
 */
public class MessageService {

    private Logger logger = Logger.getLogger(MessageService.class);
    /**
     * Description:根据条件查询消息列表
     * @param command
     * @param description
     * */
    public List<Message> queryMessageList(String command, String description) throws Exception{
        MessageDao messageDao = new MessageDao();
        return messageDao.queryMessageList(command,description);
    }

    /**
     * Description:实现单条数据删除
     * @param id 消息id
     */
    public void deleteOne(Integer id) throws Exception{
        MessageDao messageDao = new MessageDao();
        messageDao.deleteOne(id);
    }
}
```

####4.1.4 DeleteOneServlet.java

```java
package com.imooc.servlet;

import com.imooc.service.MessageService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author caojx 实现消息单条删除
 * Created by caojx on 2017年02月26 下午7:12:12
 */
public class DeleteOneServlet extends HttpServlet{

    private Logger logger = Logger.getLogger(DeleteOneServlet.class);

    MessageService messageService = new MessageService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try{
            //获取页面参数
            String id = req.getParameter("id");
            System.out.println(id);
            if(id!=null&&!"".equals(id)){
                messageService.deleteOne(Integer.parseInt(id));
            }
            req.setAttribute("retMsg", "删除成功");
        }catch(Exception e){
            logger.error("消息删除出错",e);
            req.setAttribute("retMsg", "删除失败");
        }finally{
            //向页面跳转list.action实现第页面进行刷新
            resp.sendRedirect("/list.action");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}

```

####4.1.5web.xml
配置deleteOne.action
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app id="WebApp_ID" version="2.4" xmlns="http://java.sun.com/xml/ns/j2ee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd">
	<display-name>mybatis</display-name>
	<welcome-file-list>
		<welcome-file>index.html</welcome-file>
		<welcome-file>index.htm</welcome-file>
		<welcome-file>index.jsp</welcome-file>
		<welcome-file>default.html</welcome-file>
		<welcome-file>default.htm</welcome-file>
		<welcome-file>default.jsp</welcome-file>
	</welcome-file-list>
	<!--Message列表-->
	<servlet>
		<servlet-name>ListServlet</servlet-name>
		<servlet-class>com.imooc.servlet.ListServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>ListServlet</servlet-name>
		<url-pattern>/list.action</url-pattern>
	</servlet-mapping>
	<!--单条删除-->
	<servlet>
		<servlet-name>deleteOne</servlet-name>
		<servlet-class>com.imooc.servlet.DeleteOneServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>deleteOne</servlet-name>
		<url-pattern>/deleteOne.action</url-pattern>
	</servlet-mapping>
</web-app>
```
####4.1.6运行
这里我们删除第9条数据，指令为查看的
![](/home/caojx/learn/notes/images/mybatis/mybatis-deleteOne1.png)
删除后
![](/home/caojx/learn/notes/images/mybatis/mybatis-deleteOne2.png)

****
###4.2消息批量删除


##五、实现自动回复功能


##六、一对一，一对多

##七、常用标签





