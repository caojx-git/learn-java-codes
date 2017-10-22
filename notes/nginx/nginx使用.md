# nginx使用

## 一、Nginx简介
1.1 简介  
Nginx是一款轻量级的Web服务器/反向代理服务器以及电子邮件（IMAP/POP3）代理服务器，最早由俄罗斯程序设计师Igor Sysoev所开发，并在BSD-like
写一下发行。其特点是轻量级占有内存少，并发能力强，目前发展势头强劲，web领域最经典的lamp组合已经变成了lnmp组合。

1.2 谁在用nginx  
目前国内的一些门户网站，如网易、搜狐、腾讯，新浪以及大量的新兴网站，如自学it网，豆瓣等。

1.3 web服务器市场占有率  
https://news.netcraft.com/archives/category/web-server-survey/  
![](../images/nginx/nginx_1.png)

## 二、Nginx编译与启动

官网：http://nginx.org/
安装准备：nginx依赖于pcre库要先安转pcre，因为nginx要在rewrite时要解析正则表达式，pcre是正则解析库。


2.1 下载源码包
```text
a)  http://downloads.sourceforge.net/project/pcre/pcre/8.35/pcre-8.35.tar.gz
b) http://nginx.org/download/nginx-1.8.0.tar.gz
```

PCRE 作用是让 Ngnix 支持 Rewrite 功能,没有该包无法安装
   
   
2.2 准备    
安装nginx前，我们首先要确保系统安装了g++、gcc、openssl-devel、pcre-devel和zlib-devel软件
```shell
yum -y install pcre-devel
yum -y install gcc-c++
yum -y install openssl-devel
yum -y install zlib-devel
```
 
2.3 将下载好的源码包解压到/usr/local/src目录下，编译安装  
 
安装pcre  
```shell
[root@localhost local]# tar -zxvf pcre-8.31.tar.gz -C /usr/local/src
[root@localhost src]# cd pcre-8.31/
[root@localhost pcre-8.31]#./configure --prefix=/usr/local/pcre-8.31
[roott@localhost pcre-8.31]# make
[root@localhost pcre-8.31]# make install
[root@localhost pcre-8.31]# pcre-config --version
8.32
```
  
2.4 安装nginx  
```shell
[root@localhost local]# tar -zxvf nginx-1.8.0.tar.gz -C /usr/local/src/
[root@localhost src]# cd nginx-1.8.0/
[root@localhost nginx-1.8.0]#./configure --prefix=/usr/local/nginx-1.8.0 --with-http_stub_status_module --with-http_ssl_module --with-pcre=/usr/local/src/pcre-8.31

[roott@localhost nginx-1.8.0# make
[root@localhost  nginx-1.8.0# make install
[root@localhost sbin]# /usr/local/nginx-1.8.0/sbin/nginx -v
nginx version: nginx/1.8.0
```
  
2.5 配置环境变量  
```shell
vi  /etc/profile
 
末尾添加：
#-------nginx环境变量设置-----------
NGINX_HOME=/usr/local/nginx-1.8.0
PATH=$NGINX_HOME/sbin:$PATH
export NGINX_HOME PATH 
```
source  /etc/profile 

 2.6 相关操作   
```shell
启动操作
/usr/local/nginx-1.8.0/sbin/nginx (/usr/nginx/sbin/nginx -t 查看配置信息是否正确)
 
停止操作
停止操作是通过向nginx进程发送信号（什么是信号请参阅linux文 章）来进行的
步骤1：查询nginx主进程号
ps -ef | grep nginx
在进程列表里 面找master进程，它的编号就是主进程号了。
步骤2：发送信号
从容停止Nginx：
kill -QUIT 主进程号
快速停止Nginx：
kill -TERM 主进程号
强制停止Nginx：
pkill -9 nginx

其他操作
nginx -t 测试配置是否正确
nginx -s reload 重新加载配置
nginx -s stop 立即停止
nginx -s quit 优雅停止
nginx -s reopen 重新打开日志
```

2.7 相关目录介绍  
```text
cd /usr/local/nginx 看到如下4个目录
-- conf配置文件
-- html 网页文件
-- logs 日志文件
-- sbin 主要二进制程序  
```

## 三、Nginx配置

```text
#用户
#user  nobody;

#工作进程数量，一般是CPU数量*核数
worker_processes  1;

#错误日志
#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;

events {
    worker_connections  1024;#单个进程最大连接数（最大连接数=连接数*进程数）
}


http {
    include       mime.types;#文件扩张名与文件类型映射表
    default_type  application/octet-stream;#默认文件类型

    #日志格式
    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';
    
    #access_log  logs/access.log  main;

    sendfile        on; #开启高效文件传输模式，sendfile指令指定nginx是否调用sendfile函数来输出文件，对于普通应用设为 on，如果用来进行下载等应用磁盘IO重负载应用，可设置为off，以平衡磁盘与网络I/O处理速度，降低系统的负载。注意：如果图片显示不正常把这个改成off。  
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65; #长连接超时时间，单位是秒

    #gzip  on; #启用gzip压缩
    
    upstream testserver.com { #服务器集群名字
        server 127.0.0.1:18080 weight=1; 服务器配置，weight是权重，权重越大，分配概率越大
        server 127.0.0.1:28080 weight=2;
    
    }

    #当前nginx的配置，基于域名的虚拟主机，可以配置多个
    server {
        #监听端口，可以改成其他端口
        listen       8080;
        
        #当前服务器的域名或ip
        server_name  localhost;
        
        #编码
        #charset koi8-r;

        #虚拟主机日志
        #access_log  logs/host.access.log  main;
        
        #代理要与服务器集群名字一致
        location / {  
            proxy_pass http://testserver.com;  
            proxy_redirect default;  
        }  

        #
        location / {
            root   html;
            index  index.html index.htm;
        }
        

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }


    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}
    include servers/*;
}
```

## 四、Nginx日志管理

