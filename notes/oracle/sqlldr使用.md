# sqlldr 
由于公司有一批数据没有入库，数据保存到csv文件中，每个文件大约有4~5万的数据量，普通的insert语句需要消耗很长的时间才能将数据导入，后来使用
oracle的sqlldr很快的将数据导入到oracle数据库中。

## 一、特点 
 
sqlldr这个命令可以将文本中的数据 大批量的 导入到oracle数据库表中。  
SQL*LOADER是ORACLE的数据加载工具，通常用来将操作系统文件迁移到ORACLE数据库中。SQL*LOADER是大型数据仓库选择使用的加载方法，因为它提供了
最快速的途径（DIRECT，PARALLEL）

## 二、sqlldr帮助
```text
C:>sqlldr

SQL*Loader: Release 11.2.0.2.0 - Production on 星期日 10月 22 21:34:08 2017

Copyright (c) 1982, 2009, Oracle and/or its affiliates.  All rights reserved.


用法: SQLLDR keyword=value [,keyword=value,...]

有效的关键字:

    userid -- ORACLE 用户名/口令
   control -- 控制文件名
       log -- 日志文件名
       bad -- 错误文件名
      data -- 数据文件名
   discard -- 废弃文件名
discardmax -- 允许废弃的文件的数目         (全部默认)
      skip -- 要跳过的逻辑记录的数目  (默认 0)
      load -- 要加载的逻辑记录的数目  (全部默认)
    errors -- 允许的错误的数目         (默认 50)
      rows -- 常规路径绑定数组中或直接路径保存数据间的行数
               (默认: 常规路径 64, 所有直接路径)
  bindsize -- 常规路径绑定数组的大小 (以字节计)  (默认 256000)
    silent -- 运行过程中隐藏消息 (标题,反馈,错误,废弃,分区)
    direct -- 使用直接路径                     (默认 FALSE)
   parfile -- 参数文件: 包含参数说明的文件的名称
  parallel -- 执行并行加载                    (默认 FALSE)
      file -- 要从以下对象中分配区的文件
skip_unusable_indexes -- 不允许/允许使用无用的索引或索引分区  (默认 FALSE)
skip_index_maintenance -- 没有维护索引, 将受到影响的索引标记为无用  (默认 FALSE)

commit_discontinued -- 提交加载中断时已加载的行  (默认 FALSE)
  readsize -- 读取缓冲区的大小               (默认 1048576)
external_table -- 使用外部表进行加载; NOT_USED, GENERATE_ONLY, EXECUTE  (默认 NO
T_USED)
columnarrayrows -- 直接路径列数组的行数  (默认 5000)
streamsize -- 直接路径流缓冲区的大小 (以字节计)  (默认 256000)
multithreading -- 在直接路径中使用多线程
 resumable -- 对当前会话启用或禁用可恢复  (默认 FALSE)
resumable_name -- 有助于标识可恢复语句的文本字符串
resumable_timeout -- RESUMABLE 的等待时间 (以秒计)  (默认 7200)
date_cache -- 日期转换高速缓存的大小 (以条目计)  (默认 1000)
no_index_errors -- 出现任何索引错误时中止加载  (默认 FALSE)

PLEASE NOTE: 命令行参数可以由位置或关键字指定
。前者的例子是 'sqlldr
scott/tiger foo'; 后一种情况的一个示例是 'sqlldr control=foo
userid=scott/tiger'。位置指定参数的时间必须早于
但不可迟于由关键字指定的参数。例如,
允许 'sqlldr scott/tiger control=foo logfile=log', 但是
不允许 'sqlldr scott/tiger control=foo log', 即使
参数 'log' 的位置正确。
```

## 三、案例演示  
假如我们有源文件 WH_SEND20170703153100_2420877.csv文件格式如下，都是以固定符号","分割的。 我们需要将号码字段导入到数据库表wh_send。  

1. WH_SEND20170703153100_2420877.csv原文件
提示，源文件主要导出的时候导出csv文件，可以使用PL/SQL Developer工具导出，当然也可以使用普通的文件文件如txt，有固定格式一般可以处理。
```text
姓名,品牌,级别,营业厅,号码,联系地址,邮编,入网时间,月均消费,集团单位,推荐活动,备注8,备注9,备注10
13636452211,不详,不详,不详,13636452211,,,不详,不详,,,,,
13636515519,全球通,不详,上海移动公司,13636515519,,,200612,132.5,,,,,
13636519020,全球通,不详,上海移动公司,13636519020,,,201006,88.4,,,,,
```

2. wh_send表结构
```sql
SQL> desc wh_send
Name         Type          Nullable Default Comments 
------------ ------------- -------- ------- -------- 
WH_NAME      VARCHAR2(100) Y                         
WH_MONTH     VARCHAR2(30)  Y                         
WH_BILL      VARCHAR2(30)  Y                         
WH_DONE_DATE DATE          Y                         
OP_ID        NUMBER(20)    Y                         
ORG_ID       NUMBER(20)    Y                         
EXT1         VARCHAR2(30)  Y                         
EXT2         VARCHAR2(30)  Y    
```

3. WH_SEND20170703153100_2420877.ctl控制器文件
```text
load data  --控制文件标识
infile 'c:/wh/data/WH_SEND20170703153100_2420877.csv' --数据源文件
append into table wh_send --向 wh_send表中追加记录
fields terminated by ',' --指定分割符，终止字段值 
trailing nullcols --代表表的字段没有值时，允许为空
( --定义列对应顺序
    C1 FILLER, --跳过由PL/SQL Developer生成的第一列序号，即跳过姓名字段
    C2 FILLER, --跳过由PL/SQL Developer生成的第二列序号，即跳过品牌字段
    C3 FILLER, --跳过由PL/SQL Developer生成的第三列序号，即跳过级别字段
    C4 FILLER, --跳过由PL/SQL Developer生成的第四列序号，即跳过联系地址字段
    WH_BILL, --将号码字段插入到数据库表wh_send
    WH_NAME constant 'WH_SEND20170703153100_2420877.csv',
    WH_MONTH constant '20170703',
    WH_DONE_DATE "sysdate"
)
```

4. 执行命令
```text
sqlldr username/password@ip:port/shoth control="c:/wh/ctl/WH_SEND20170703153100_2420877.ctl" log="./sqlldr.log" bad="./sqlldr_err.log" bindsize=8024000 readsize=8024000 errors=999999 rows=200000 skip=1;
```

5. 使用Java代码生成控制文件和命令执行文件

如果文件比较多可以考虑使用代码来生成对应的文件，如下边使用Java代码来生成对应的控制文件和执行命令脚本。  
```java
package com.test.util;

import java.io.*;

public class Process {

    public static void main(String args[]) throws Exception {
        createCtl();  //生成控制器文件
        doInput();  //生成命令执行脚本
    }

    public static void createCtl() throws Exception {
        String path = "c:\\wh\\data";
        File files = new File(path);
        File[] listFiles = files.listFiles();
        for (File file : listFiles) {
            String fileName = file.getName();
            if (fileName != null && fileName.endsWith(".csv")) {
                String nextDate = fileName.substring(7, 15);
                FileWriter fw = new FileWriter( "c:\\wh\\ctl\\" + fileName.substring(0, fileName.lastIndexOf(".")) + ".ctl");
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("load data");
                bw.newLine();
                bw.write("infile '" + "c:\\wh\\ctl\\" + fileName + "'");
                bw.newLine();
                bw.write("append");
                bw.newLine();
                bw.write("into table wh_send");
                bw.newLine();
                bw.write("fields terminated by ','");
                bw.newLine();
                bw.write("trailing nullcols");
                bw.newLine();
                bw.write("(");
                bw.newLine();
                bw.write("    C1 FILLER,");
                bw.write("    C2 FILLER,");
                bw.write("    C3 FILLER,");
                bw.write("    C4 FILLER,");
                bw.write("    WH_BILL,");
                bw.newLine();
                bw.write("    WH_NAME constant '" + fileName + "',");
                bw.newLine();
                bw.write("    WH_MONTH constant '" + nextDate + "',");
                bw.newLine();
                bw.write("    WH_DONE_DATE \"sysdate\"");
                bw.newLine();
                bw.write(")");
                bw.newLine();
                bw.flush();
                bw.close();
                fw.close();
            }
        }
    }

    public static void doInput() {
        String path = "E:\\wh\\ctl";
        File files = new File(path);
        File[] listFiles = files.listFiles();
        for (File file : listFiles) {
            String fileName = file.getName();
            if (fileName != null && fileName.endsWith(".ctl")) {
                try {
                    String cmd = "sqlldr newqdgl/aiKc%087@10.10.100.123:1521/shoth control=\"" + "c:\\wh\\ctl\\"  + fileName + "\" log=\"./sqlldr.log\" bad=\"./sqlldr_err.log\" bindsize=8024000 readsize=8024000 errors=999999 rows=200000 skip=1";
                    System.out.println(cmd + ";");
                } catch (Exception e) {
                    System.out.println(fileName);
                    e.printStackTrace();
                }
            }
        }
    }
}

```

参考文章  
[http://www.cnblogs.com/flish/archive/2010/05/31/1748221.html](http://www.cnblogs.com/flish/archive/2010/05/31/1748221.html)  
[https://www.2cto.com/database/201211/168283.html](https://www.2cto.com/database/201211/168283.html)  
























































