## log4j日志框架使用

注意：本文是从[mybatis入门实现增删改查.md](../mybatis/mybatis入门实现增删改查.md)中截取出来的文段，可能前后有点突兀，不过对于log4j的使用足够了。  



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

> 语法
> 其语法为：  
> log4j.rootLogger = [ level ] , appenderName1, appenderName2, …  
> level: 是日志记录的优先级，分为OFF、FATAL、ERROR、WARN、INFO、DEBUG、TRACE、ALL或  
> 者您定义的级别。Log4j建议只使用四个级别，优 先级从高到低分别是ERROR、WARN、INFO、DEBUG。通过在这里定义的级别，您可以控制到应用程序中相应级别的日志信息的开关。比如在这里定 义了INFO级别，则应用程序中所有DEBUG级别的日志信息将不被打印出来。  
> appenderName: 就是指定日志信息输出到哪个地方。您可以同时指定多个输出目的地。  
> 例如：log4j.rootLogger＝info,A1,B2,C3

#### 1. 日志的输出级别

log4j.rootLogger=DEBUG,Console,file 用于说明日志输出的级别，位置

其中
log4j.rootLogger为根logger，这个必须配置,用于指定默认的输出级别，和定义输出位置

DEBUG表示输出级别

级别有8中，级别从小到大 ALL<TRACE<DEBUG<INFO<WARN<ERROR<FATAL<OFF，只有大于和等于设置的级别的日志才会输出
这里设置DEBUG级别，表示DEBUG，INFO，WARN，ERROR，FATAL级别的信息都会被输出

各个级别表示的含义

> OFF 、FATAL 、ERROR、WARN、INFO、DEBUG、TRACE 、ALL  
>   OFF 为最高等级 关闭了日志信息  
>   FATAL  为可能导致应用中止的严重事件错误  
>   ERROR 为严重错误 主要是程序的错误  
>   WARN 为一般警告，比如session丢失  
>   INFO 为一般要显示的信息，比如登录登出  
>   DEBUG 为程序的调试信息  
>   TRACE 为比DEBUG更细粒度的事件信息  
>   ALL 为最低等级，将打开所有级别的日志 

#### 2. 日志的输出位置

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

> 1.org.apache.log4j.ConsoleAppender（控制台）  
>  2.org.apache.log4j.FileAppender（文件）  
>  3.org.apache.log4j.DailyRollingFileAppender（每天产生一个日志文件）  
>  4.org.apache.log4j.RollingFileAppender（文件大小到达指定尺寸的时候产生一个新的文件）  
>  5.org.apache.log4j.WriterAppender（将日志信息以流格式发送到任意指定的地方）

#### 3. 日志的输出格式

log4j.appender.Console.layout=org.apache.log4j.PatternLayout是布局类

log4j.appender.Console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n 自定义输出格式
其中空格 []都会被原样输出

> %d 表示日志产生的时间<br/>
>  %t 表示产生日志的线程名称<br/>
>  %p 表示日志的级别，%5p,表示级别信息会占用5个字符位置，不足5位字符使用空格补齐，%-5p，其中表示右对齐。<<br/>
>  %c 表示出书日志的全类名<br/>
>  %m 表示输出的附加信息<br/>
>  %n 表示换行<br/>
> 其他：<br/>
> %p: 输出日志信息优先级，即DEBUG，INFO，WARN，ERROR，FATAL,  
> %d: 输出日志时间点的日期或时间，默认格式为ISO8601，也可以在其后指定格式，比如：%d{yyyy MM dd HH:mm:ss,SS}，输出类似：2002年10月18日 22：10：28，921  
> %r: 输出自应用启动到输出该log信息耗费的毫秒数  
> %c: 输出日志信息所属的类，通常就是所在类的全名  
> %t: 输出产生该日志事件的线程名  
> %l: 输出日志事件的发生位置，相当于%C.%M(%F:%L)的组合,包括类目名、发生的线程，以及在代码中的行数。举例：Testlog4.main (TestLog4.java:10)  
> %x: 输出和当前线程相关联的NDC(嵌套诊断环境),尤其用到像java servlets这样的多客户多线程的应用中。  
> %%: 输出一个"%"字符  
> %F: 输出日志消息产生时所在的文件名称  
> %L: 输出代码中的行号  
> %m: 输出代码中指定的消息,产生的日志具体信息  
> %n: 输出一个回车换行符，Windows平台为"/r/n"，Unix平台为"/n"输出日志信息换行  
> 可以在%与模式字符之间加上修饰符来控制其最小宽度、最大宽度、和文本的对齐方式。如：  
> 1)%20c：指定输出category的名称，最小的宽度是20，如果category的名称小于20的话，默认的情况下右对齐。  
> 2)%-20c:指定输出category的名称，最小的宽度是20，如果category的名称小于20的话，"-"号指定左对齐。  
> 3)%.30c:指定输出category的名称，最大的宽度是30，如果category的名称大于30的话，就会将左边多出的字符截掉，但小于30的话也不会有空格。  
> 4)%20.30c:如果category的名称小于20就补空格，并且右对齐，如果其名称长于30字符，就从左边较远输出的字符截掉。 



#### 4. 指定的特别包的输出级别

配置某个包下的特殊的输出级别
log4j.logger.org.apache=INFO，这里log4j.logger是固定的，org.apache表示需要特殊处理的包的输出级别为INFO

#### 5.  配中多个输出模式

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

#### 6.  使用

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

#### 7. 日志输出

运行项目后我们将会在控制台  
![](../images/mybatis/mybatis-log4j.png)  
在文件中看日志输出  
![](../images/mybatis/mybatis-log4j2.png)    

推荐文章：  
http://blog.csdn.net/edward0830ly/article/details/8250412  
http://blog.csdn.net/hu_shengyang/article/details/6754031  
http://swiftlet.net/archives/683    