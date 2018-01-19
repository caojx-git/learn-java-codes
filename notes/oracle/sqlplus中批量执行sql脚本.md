# sqlplus中批量执行sql脚本
[TOC]

假如我们有很多sql脚本，如果一个一个sql脚本文件执行显然过于麻烦，下边我们演示sql脚本批量执行。

1.数据库环境  
- Oracle  
- sqlplus  

2.单个脚本的执行  
新建test1.sql脚本，内容如下  
```sql
select 1+0 from dual;  
```
sqlplus中执行  
```sql
SQL> @D:\Users\caojx\Desktop\testsql\test1.sql  
       1+0  
----------  
         1  
```

3.批量脚本执行  
新建test2.sql脚本，内容如下  
```sql
select 1+0 from dual;  
```
新建step.sql脚本，内容如下  
```sql
@D:\Users\caojx\Desktop\testsql\test1.sql;  
@D:\Users\caojx\Desktop\testsql\test2.sql;  
```
sqlplus中执行
```sql
SQL> @D:\Users\caojx\Desktop\testsql\step.sql;

       1+0
----------
         1
       1+1
----------
         2
```





