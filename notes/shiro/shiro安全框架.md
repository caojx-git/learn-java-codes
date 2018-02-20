# shiro安全框架

Apache Shiro是一个强大且易用的Java安全框架,执行身份验证、授权、密码学和会话管理。使用Shiro的易于理解的API,您可以快速、轻松地获得任何应用程序,从最小的移动应用程序到最大的网络和企业应用程序。

**主要功能**

三个核心组件：Subject, SecurityManager 和 Realms.

1、Subject ：当前用户的操作  
2、SecurityManager：用于管理所有的Subject  
3、Realms：用于进行权限信息的验证  

**Subject：**即“当前操作用户”。但是，在Shiro中，Subject这一概念并不仅仅指人，也可以是第三方进程、后台帐户（Daemon Account）或其他类似事物。它仅仅意味着“当前跟软件交互的东西”。但考虑到大多数目的和用途，你可以把它认为是Shiro的“用户”概念。
　　Subject代表了当前用户的安全操作，SecurityManager则管理所有用户的安全操作。
**SecurityManager：**它是Shiro框架的核心，典型的Facade模式，Shiro通过SecurityManager来管理内部组件实例，并通过它来提供安全管理的各种服务。
**Realm：** Realm充当了Shiro与应用安全数据间的“桥梁”或者“连接器”。也就是说，当对用户执行认证（登录）和授权（访问控制）验证时，Shiro会从应用配置的Realm中查找用户及其权限信息。
　　从这个意义上讲，Realm实质上是一个安全相关的DAO：它封装了数据源的连接细节，并在需要时将相关数据提供给Shiro。当配置Shiro时，你必须至少指定一个Realm，用于认证和（或）授权。配置多个Realm是可以的，但是至少需要一个。
　　Shiro内置了可以连接大量安全数据源（又名目录）的Realm，如LDAP、关系数据库（JDBC）、类似INI的文本配置资源以及属性文件等。如果缺省的Realm不能满足需求，你还可以插入代表自定义数据源的自己的Realm实现。



## 一、shiro HelloWorld

### 1.1 shiro.ini配置文件

```ini
#用户组
#用户名=密码
[users]
tom=12345
jack=12345
```

### 1.2 maven依赖

```xml
<!-- https://mvnrepository.com/artifact/org.apache.shiro/shiro-core -->
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-core</artifactId>
    <version>1.4.0</version>
</dependency>

<!-- https://mvnrepository.com/artifact/commons-logging/commons-logging -->
<dependency>
    <groupId>commons-logging</groupId>
    <artifactId>commons-logging</artifactId>
    <version>1.2</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.25</version>
</dependency>
```

### 1.3 log4j.properties

```properties
log4j.rootLogger=INFO, stdout

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] - %m %n

# General Apache libraries
log4j.logger.org.apache=WARN

# Spring
log4j.logger.org.springframework=WARN

# Default Shiro logging
log4j.logger.org.apache.shiro=TRACE

# Disable verbose logging
log4j.logger.org.apache.shiro.util.ThreadContext=WARN
log4j.logger.org.apache.shiro.cache.ehcache.EhCache=WARN
```

### 1.4 HelloWorld.java

```java
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * @author caojx
 * Created on 2018/2/18 下午10:17
 */
public class HelloWorld {

    public static void main(String[] args) {
        //1.读取配置文件，初始化SecurityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2.获取SecurityManager实例
        SecurityManager securityManager = factory.getInstance();
        //把SecurityManager实例绑定到SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);

        //得到当前执行的用户
        Subject currentUser = SecurityUtils.getSubject();
        //创建token令牌， 用户名/密码
        UsernamePasswordToken token = new UsernamePasswordToken("tom","12345");

        try{
            //身份认证
            currentUser.login(token);
            System.out.println("身份认证成功");
        }catch (AuthenticationException e){
            System.out.println("身份认证失败");
            e.printStackTrace();
        }

        currentUser.logout();
    }
}
```



结果：

```txt
2018-02-18 22:53:37,763 DEBUG [org.apache.shiro.io.ResourceUtils] - Opening resource from class path [shiro.ini] 
2018-02-18 22:53:37,797 DEBUG [org.apache.shiro.config.Ini] - Parsing [users] 
2018-02-18 22:53:37,798 TRACE [org.apache.shiro.config.Ini] - Discovered key/value pair: tom = 12345 
2018-02-18 22:53:37,798 TRACE [org.apache.shiro.config.Ini] - Discovered key/value pair: jack = 12345 
2018-02-18 22:53:37,898 TRACE [org.apache.shiro.util.ClassUtils] - Unable to load clazz named [org.apache.commons.configuration2.interpol.ConfigurationInterpolator] from class loader [sun.misc.Launcher$AppClassLoader@18b4aac2] 
2018-02-18 22:53:37,898 TRACE [org.apache.shiro.util.ClassUtils] - Unable to load class named [org.apache.commons.configuration2.interpol.ConfigurationInterpolator] from the thread context ClassLoader.  Trying the current ClassLoader... 
2018-02-18 22:53:37,899 TRACE [org.apache.shiro.util.ClassUtils] - Unable to load clazz named [org.apache.commons.configuration2.interpol.ConfigurationInterpolator] from class loader [sun.misc.Launcher$AppClassLoader@18b4aac2] 
2018-02-18 22:53:37,899 TRACE [org.apache.shiro.util.ClassUtils] - Unable to load class named [org.apache.commons.configuration2.interpol.ConfigurationInterpolator] from the current ClassLoader.  Trying the system/application ClassLoader... 
2018-02-18 22:53:37,900 TRACE [org.apache.shiro.util.ClassUtils] - Unable to load clazz named [org.apache.commons.configuration2.interpol.ConfigurationInterpolator] from class loader [sun.misc.Launcher$AppClassLoader@18b4aac2] 
2018-02-18 22:53:37,910 DEBUG [org.apache.shiro.config.IniFactorySupport] - Creating instance from Ini [sections=users] 
2018-02-18 22:53:37,911 TRACE [org.apache.shiro.config.Ini] - Specified name was null or empty.  Defaulting to the default section (name = "") 
2018-02-18 22:53:37,950 DEBUG [org.apache.shiro.realm.text.IniRealm] - Discovered the [users] section.  Processing... 
2018-02-18 22:53:37,957 TRACE [org.apache.shiro.mgt.DefaultSecurityManager] - Context already contains a SecurityManager instance.  Returning. 
2018-02-18 22:53:37,957 TRACE [org.apache.shiro.mgt.DefaultSecurityManager] - No identity (PrincipalCollection) found in the context.  Looking for a remembered identity. 
2018-02-18 22:53:37,957 TRACE [org.apache.shiro.mgt.DefaultSecurityManager] - No remembered identity found.  Returning original context. 
2018-02-18 22:53:37,960 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = true; session has id = false 
2018-02-18 22:53:37,960 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = true; session has id = false 
2018-02-18 22:53:37,960 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = true; session has id = false 
2018-02-18 22:53:37,960 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = true; session has id = false 
2018-02-18 22:53:37,960 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = true; session has id = false 
2018-02-18 22:53:37,960 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = true; session has id = false 
2018-02-18 22:53:37,960 TRACE [org.apache.shiro.authc.AbstractAuthenticator] - Authentication attempt received for token [org.apache.shiro.authc.UsernamePasswordToken - tom, rememberMe=false] 
2018-02-18 22:53:37,961 DEBUG [org.apache.shiro.realm.AuthenticatingRealm] - Looked up AuthenticationInfo [tom] from doGetAuthenticationInfo 
2018-02-18 22:53:37,961 DEBUG [org.apache.shiro.realm.AuthenticatingRealm] - AuthenticationInfo caching is disabled for info [tom].  Submitted token: [org.apache.shiro.authc.UsernamePasswordToken - tom, rememberMe=false]. 
2018-02-18 22:53:37,961 DEBUG [org.apache.shiro.authc.credential.SimpleCredentialsMatcher] - Performing credentials equality check for tokenCredentials of type [[C and accountCredentials of type [java.lang.String] 
2018-02-18 22:53:37,961 DEBUG [org.apache.shiro.authc.credential.SimpleCredentialsMatcher] - Both credentials arguments can be easily converted to byte arrays.  Performing array equals comparison 
2018-02-18 22:53:37,962 DEBUG [org.apache.shiro.authc.AbstractAuthenticator] - Authentication successful for token [org.apache.shiro.authc.UsernamePasswordToken - tom, rememberMe=false].  Returned account [tom] 
2018-02-18 22:53:37,962 DEBUG [org.apache.shiro.subject.support.DefaultSubjectContext] - No SecurityManager available in subject context map.  Falling back to SecurityUtils.getSecurityManager() lookup. 
2018-02-18 22:53:37,962 TRACE [org.apache.shiro.mgt.DefaultSecurityManager] - Context already contains a SecurityManager instance.  Returning. 
2018-02-18 22:53:37,962 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = true; session has id = false 
2018-02-18 22:53:37,962 DEBUG [org.apache.shiro.subject.support.DefaultSubjectContext] - No SecurityManager available in subject context map.  Falling back to SecurityUtils.getSecurityManager() lookup. 
2018-02-18 22:53:37,962 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = true; session has id = false 
2018-02-18 22:53:37,962 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = true; session has id = false 
2018-02-18 22:53:37,962 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = true; session has id = false 
2018-02-18 22:53:37,962 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = true; session has id = false 
2018-02-18 22:53:37,962 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = true; session has id = false 
2018-02-18 22:53:37,962 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = true; session has id = false 
2018-02-18 22:53:37,962 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = true; session is null = true; session has id = false 
2018-02-18 22:53:37,962 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - Starting session for host null 
2018-02-18 22:53:37,963 DEBUG [org.apache.shiro.session.mgt.AbstractValidatingSessionManager] - No sessionValidationScheduler set.  Attempting to create default instance. 
2018-02-18 22:53:37,964 TRACE [org.apache.shiro.session.mgt.AbstractValidatingSessionManager] - Created default SessionValidationScheduler instance of type [org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler]. 
2018-02-18 22:53:37,964 INFO [org.apache.shiro.session.mgt.AbstractValidatingSessionManager] - Enabling session validation scheduler... 
2018-02-18 22:53:37,972 TRACE [org.apache.shiro.session.mgt.DefaultSessionManager] - Creating session for host null 
2018-02-18 22:53:37,972 DEBUG [org.apache.shiro.session.mgt.DefaultSessionManager] - Creating new EIS record for new session instance [org.apache.shiro.session.mgt.SimpleSession,id=null] 
2018-02-18 22:53:37,986 TRACE [org.apache.shiro.session.mgt.AbstractValidatingSessionManager] - Attempting to retrieve session with key org.apache.shiro.session.mgt.DefaultSessionKey@6108b2d7 
2018-02-18 22:53:37,986 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = false; session has id = true 
2018-02-18 22:53:37,987 TRACE [org.apache.shiro.session.mgt.AbstractValidatingSessionManager] - Attempting to retrieve session with key org.apache.shiro.session.mgt.DefaultSessionKey@6108b2d7 
2018-02-18 22:53:37,987 TRACE [org.apache.shiro.session.mgt.AbstractValidatingSessionManager] - Attempting to retrieve session with key org.apache.shiro.session.mgt.DefaultSessionKey@6108b2d7 
2018-02-18 22:53:37,987 TRACE [org.apache.shiro.mgt.DefaultSecurityManager] - This org.apache.shiro.mgt.DefaultSecurityManager instance does not have a [org.apache.shiro.mgt.RememberMeManager] instance configured.  RememberMe services will not be performed for account [tom]. 
2018-02-18 22:53:37,987 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = false; session has id = true 
身份认证成功
2018-02-18 22:53:37,987 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = false; session has id = true 
2018-02-18 22:53:37,987 TRACE [org.apache.shiro.session.mgt.AbstractValidatingSessionManager] - Attempting to retrieve session with key org.apache.shiro.session.mgt.DefaultSessionKey@6108b2d7 
2018-02-18 22:53:37,987 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = false; session has id = true 
2018-02-18 22:53:37,987 TRACE [org.apache.shiro.session.mgt.AbstractValidatingSessionManager] - Attempting to retrieve session with key org.apache.shiro.session.mgt.DefaultSessionKey@6108b2d7 
2018-02-18 22:53:37,987 DEBUG [org.apache.shiro.mgt.DefaultSecurityManager] - Logging out subject with primary principal tom 
2018-02-18 22:53:37,988 TRACE [org.apache.shiro.realm.CachingRealm] - Cleared cache entries for account with principals [tom] 
2018-02-18 22:53:37,988 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = false; session has id = true 
2018-02-18 22:53:37,988 TRACE [org.apache.shiro.session.mgt.AbstractValidatingSessionManager] - Attempting to retrieve session with key org.apache.shiro.session.mgt.DefaultSessionKey@6108b2d7 
2018-02-18 22:53:37,988 TRACE [org.apache.shiro.session.mgt.AbstractValidatingSessionManager] - Attempting to retrieve session with key org.apache.shiro.session.mgt.DefaultSessionKey@6108b2d7 
2018-02-18 22:53:37,988 TRACE [org.apache.shiro.subject.support.DelegatingSubject] - attempting to get session; create = false; session is null = false; session has id = true 
2018-02-18 22:53:37,988 TRACE [org.apache.shiro.session.mgt.AbstractValidatingSessionManager] - Attempting to retrieve session with key org.apache.shiro.session.mgt.DefaultSessionKey@6108b2d7 
2018-02-18 22:53:37,988 DEBUG [org.apache.shiro.session.mgt.AbstractSessionManager] - Stopping session with id [b413cd13-c270-4bbe-91b5-3a7d7af16587] 

```

## 二、身份认证

### 2.1 Subject 认证主体

Subject 认证主体包含两个信息:
Principals:身份，可以是用户名，邮件，手机号码等等，用来标识一个登录主体身份;
Credentials:凭证，常见有密码，数字证书等等;

### 2.2 身份认证流程

http://shiro.apache.org/authentication.html#authentication-sequence

![](../images/shiro/shiro-1.png)  

推荐：

http://shiro.apache.org/authentication.html

http://blog.csdn.net/daybreak1209/article/details/51193855

### 2.3 Realm&JDBC Reaml

Realm:意思是域，Shiro 从 Realm 中获取验证数据;

Realm 有很多种类，例如常见的 jdbc realm，jndi realm，text realm。

### 2.4 JDBC Reaml 案例演示

1. 创建数据库db_shiro

   ```mysql
   create database db_shiro charset utf8;
   ```

2. 创建表users

   注意，表名必须为users，结构必须如下，因为shiro需要读取该表中的数据。

   ```mysql
   CREATE TABLE `users` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `userName` varchar(20) DEFAULT NULL,
     `password` varchar(20) DEFAULT NULL,
     PRIMARY KEY (`id`)
   ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
   ```

3. 插入用户

   ```mysql
   insert  into `users`(`id`,`userName`,`password`) values (1,'java1234','123456');
   ```

4. pom.xml配置

   ```xml
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
       <modelVersion>4.0.0</modelVersion>
       <groupId>caojx.learn</groupId>
       <artifactId>shiro-java</artifactId>
       <packaging>war</packaging>
       <version>1.0-SNAPSHOT</version>
       <name>shiro-java Maven Webapp</name>
       <url>http://maven.apache.org</url>
       <dependencies>
           <dependency>
               <groupId>junit</groupId>
               <artifactId>junit</artifactId>
               <version>4.12</version>
               <scope>compile</scope>
           </dependency>

           <!-- https://mvnrepository.com/artifact/org.apache.shiro/shiro-core -->
           <dependency>
               <groupId>org.apache.shiro</groupId>
               <artifactId>shiro-core</artifactId>
               <version>1.4.0</version>
           </dependency>

           <!-- https://mvnrepository.com/artifact/commons-logging/commons-logging -->
           <dependency>
               <groupId>commons-logging</groupId>
               <artifactId>commons-logging</artifactId>
               <version>1.2</version>
           </dependency>

           <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12 -->
           <dependency>
               <groupId>org.slf4j</groupId>
               <artifactId>slf4j-log4j12</artifactId>
               <version>1.7.25</version>
           </dependency>

           <!-- https://mvnrepository.com/artifact/c3p0/c3p0 -->
           <dependency>
               <groupId>c3p0</groupId>
               <artifactId>c3p0</artifactId>
               <version>0.9.1.2</version>
           </dependency>

           <!-- https://mvnrepository.com/artifact/org.mariadb.jdbc/mariadb-java-client -->
           <dependency>
               <groupId>org.mariadb.jdbc</groupId>
               <artifactId>mariadb-java-client</artifactId>
               <version>2.2.0</version>
           </dependency>

       </dependencies>
       <build>
           <finalName>shiro-java</finalName>
       </build>
   </project>
   ```

   5. jdbc_realm.ini

   ```ini
   #代码配置
   [main]
   #jdbcRealm实例
   jdbcRealm=org.apache.shiro.realm.jdbc.JdbcRealm
   #数据源实例配置
   dataSource=com.mchange.v2.c3p0.ComboPooledDataSource
   dataSource.driverClass=org.mariadb.jdbc.Driver
   dataSource.jdbcUrl=jdbc:mariadb://192.168.46.131:3306/db_shiro
   dataSource.user=root
   dataSource.password=root
   #shiro引用数据源和jdbcRealm
   jdbcRealm.dataSource=$dataSource
   securityManager.realms=$jdbcRealm
   ```

   6. JdbcRealmTest.java

   ```java
   package jdbcRealm;

   import org.apache.shiro.SecurityUtils;
   import org.apache.shiro.authc.AuthenticationException;
   import org.apache.shiro.authc.UsernamePasswordToken;
   import org.apache.shiro.config.IniSecurityManagerFactory;
   import org.apache.shiro.mgt.SecurityManager;
   import org.apache.shiro.subject.Subject;
   import org.apache.shiro.util.Factory;

   /**
    * @author caojx
    * Created on 2018/2/20 下午9:31
    */
   public class JdbcRealmTest {

       public static void main(String[] args) {
           //1.读取配置文件，初始化SecurityManager工厂,引用jdbc_realm.ini
           Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:jdbc_realm.ini");
           //2.获取SecurityManager实例
           SecurityManager securityManager = factory.getInstance();
           //把SecurityManager实例绑定到SecurityUtils
           SecurityUtils.setSecurityManager(securityManager);

           //得到当前执行的用户
           Subject currentUser = SecurityUtils.getSubject();
           //创建token令牌， 用户名/密码
           UsernamePasswordToken token = new UsernamePasswordToken("java1234","123456");
           try{
               //身份认证
               currentUser.login(token);
               System.out.println("身份认证成功");
           }catch (AuthenticationException e){
               System.out.println("身份认证失败");
               e.printStackTrace();
           }
           currentUser.logout();
       }
   }
   ```

## 三、权限认证(授权)

### 3.1 权限认证核心要素

权限认证，也就是访问控制，即在应用中控制谁能访问哪些资源。 

在权限认证中，最核心的三个要素是:权限，角色和用户; 

**权限**，即操作资源的权利，比如访问某个页面，以及对某个模块的数据的添加，修改，删除，查看的权利;   
**角色**，是权限的集合，一中角色可以包含多种权限;  
**用户**，在 Shiro 中，代表访问系统的用户，即 Subject;  

### 3.2 授权
- 编程式授权
  - 基于角色的访问控制
  - 基于权限的访问控制

- 注解式授权（后边整合spring讲解）

  http://shiro.apache.org/authorization.html#Authorization-AnnotationbasedAuthorization

  - @RequiresAuthentication 要求当前 Subject 已经在当前的 session 中被验证通过才能被访问或调用。
  - @RequiresGuest 要求当前的 Subject 是一个"guest"，也就是说，他们必须是在之前的 session 中没有被验证或被记住才 能被访问或调用。
  - @RequiresPermissions("account:create") 要求当前的 Subject 被允许一个或多个权限，以便执行注解的方法。 
  - @RequiresRoles("administrator") 要求当前的 Subject 拥有所有指定的角色。如果他们没有，则该方法将不会被执行，而 且 AuthorizationException 异常将会被抛出。
  - @RequiresUser RequiresUser 注解需要当前的 Subject 是一个应用程序用户才能被注解的类/实例/方法访问或调用。一个“应 用程序用户”被定义为一个拥有已知身份，或在当前 session 中由于通过验证被确认，或者在之前 session 中的'RememberMe' 服务被记住。

- Jsp 标签授权（后边讲解）

  http://shiro.apache.org/web.html#Web-taglibrary

  <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

  - Guest 标签:用户没有身份验证时显示相应信息，即游客访问信息;
  - User 标签:用户已经身份验证/记住我登录后显示相应的信息;
  - Authenticated 标签:用户已经身份验证通过，即 Subject.login 登录成功，不是记住我登录的。 
  - notAuthenticated 标签:用户没有身份验证通过，即没有调用 Subject.login 进行登录，包括记住我自动登录 的也属于未进行身份验证。
  - principal 标签 显示用户身份信息，默认调用 Subject.getPrincipal()获取，即 Primary Principal。 hasRole标签 如果当前Subject有角色将显示body体内容。
  - lacksRole标签 如果当前Subject没有角色将显示body体内容。
  - hasAnyRoles 标签 如果当前 Subject 有任意一个角色(或的关系)将显示 body 体内容。
  - hasPermission 标签 如果当前 Subject 有权限将显示 body 体内容。
  - lacksPermission 标签 如果当前 Subject 没有权限将显示 body 体内容。



下边了解编程式授权

1. 基于角色的访问控制

**shiro_role.ini**

```ini
#基于角色的访问控制

#配置用户和角色
[users]
#用户名=密码，角色1，角色2，。。。
java1234=123456,role1,role2
jack=123456,role1
```

**ShiroUtil.java**

```java
package authorization;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * @author caojx
 * Created on 2018/2/20 下午10:00
 * 工具类
 */
public class ShiroUtil {

    public static Subject login(String configFile, String userName,String password){
        //1.读取配置文件，初始化SecurityManager工厂,引用jdbc_realm.ini
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        //2.获取SecurityManager实例
        SecurityManager securityManager = factory.getInstance();
        //把SecurityManager实例绑定到SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);

        //得到当前执行的用户
        Subject currentUser = SecurityUtils.getSubject();
        //创建token令牌， 用户名/密码
        UsernamePasswordToken token = new UsernamePasswordToken(userName,password);

        try{
            //身份认证
            currentUser.login(token);
            System.out.println("身份认证成功");
        }catch (AuthenticationException e){
            System.out.println("身份认证失败");
            e.printStackTrace();
        }
        return currentUser;
    }
}
```

**RoleTest.java**

```java
package authorization;

import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author caojx
 * Created on 2018/2/20 下午10:06
 * 基于角色的访问控制,验证用户角色
 */
public class RoleTest {

    /**
     * 角色验证，返回boolean类型
     */
    @Test
    public void hasRoleTest() {
        Subject currentUser = ShiroUtil.login("classpath:shiro_role.ini", "jack", "123456");
        //hasRole判断用户是否有某个角色
        if (currentUser.hasRole("role2")) {
            System.out.println("有role2这个角色");
        } else {
            System.out.println("没有role2这个角色");
        }

        //hasRoles判断用户是否有某个角色
        boolean[] results = currentUser.hasRoles(Arrays.asList("role1", "role2"));
        System.out.println(results[0] ? "有role1这个角色" : "没有role1这个角色");
        System.out.println(results[1] ? "有role2这个角色" : "没有role2这个角色");

        //hasAllRoles 判断是否都有某些权限
        System.out.println(currentUser.hasAllRoles(Arrays.asList("role1", "role2")) ? "role1,role2这两个角色都有" : "role1,role2这两个角色不全有");

        currentUser.logout();
    }

    /**
     * 角色验证不通过抛出异常
     */
    @Test
    public void testCheckRole() {
        Subject currentUser=ShiroUtil.login("classpath:shiro_role.ini", "java1234", "123456");
        // Subject currentUser=ShiroUtil.login("classpath:shiro_role.ini", "jack", "123");
        //是否有某个权限
        currentUser.checkRole("role1");

        //是否有某些权限,集合
        currentUser.checkRoles(Arrays.asList("role1","role2"));
        //是否有某些权限，多参数
        currentUser.checkRoles("role1","role2","role3");

        currentUser.logout();
    }
}
```

2. 基于权限的访问控制

**shiro_permission.ini**

```ini
#基于权限的访问控制

#配置用户和角色
[users]
java1234=123456,role1,role2
jack=123,role1

#配置角色的权限
[roles]
role1=user:select
role2=user:add,user:update,user:delete
```

**PermissionTest.java**

```java
package authorization;

import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @author caojx
 * Created on 2018/2/20 下午10:30
 * 基于权限的访问控制，验证用户权限
 */
public class PermissionTest {

    /**
     * 判断是否有某些权限，返回boolean类型
     */
    @Test
    public void testIsPermitted() {

        Subject currentUser=ShiroUtil.login("classpath:shiro_permission.ini", "java1234", "123456");
        // Subject currentUser=ShiroUtil.login("classpath:shiro_permission.ini", "jack", "123");
        System.out.println(currentUser.isPermitted("user:select")?"有user:select这个权限":"没有user:select这个权限");
        System.out.println(currentUser.isPermitted("user:update")?"有user:update这个权限":"没有user:update这个权限");
        boolean results[]=currentUser.isPermitted("user:select","user:update","user:delete");
        System.out.println(results[0]?"有user:select这个权限":"没有user:select这个权限");
        System.out.println(results[1]?"有user:update这个权限":"没有user:update这个权限");
        System.out.println(results[2]?"有user:delete这个权限":"没有user:delete这个权限");
        System.out.println(currentUser.isPermittedAll("user:select","user:update")?"有user:select,update这两个权限":"user:select,update这两个权限不全有");

        currentUser.logout();
    }

    /**
     * 检查权限，验证不通过抛出异常
     */
    @Test
    public void testCheckPermitted() {
        Subject currentUser=ShiroUtil.login("classpath:shiro_permission.ini", "java1234", "123456");
        // Subject currentUser=ShiroUtil.login("classpath:shiro_permission.ini", "jack", "123");
        currentUser.checkPermission("user:select");
        currentUser.checkPermissions("user:select","user:update","user:delete");
        currentUser.logout();
    }
}
```

### 3.3 Permissions 对权限深入理解

单个权限 query
单个资源多个权限 user:query user:add 多值 user:query,add 
单个资源所有权限 user:query,add,update,delete user:* 
所有资源某个权限 *:view

实例级别的权限控制
单个实例的单个权限 printer:query:lp7200 printer:print:epsoncolor
所有实例的单个权限 printer:print:*
所有实例的所有权限 printer:*:*
单个实例的所有权限 printer:*:*
单个实例的多个权限 printer:query,print:lp7200

提示：实际开发的时候很少会使用到实例级别的权限控制

### 3.4 授权流程

http://shiro.apache.org/authorization.html#authorization-sequence

![](../images/shiro/shiro_authorization_3.png)  