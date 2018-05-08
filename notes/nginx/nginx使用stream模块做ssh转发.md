# nginx使用stream模块做ssh转发

## 一、前言

nginx从1.9.0开始，新增加了一个stream模块，用来实现四层协议的转发、代理或者负载均衡等。这完全就是抢HAproxy份额的节奏，鉴于nginx在7层负载均衡和web service上的成功，和nginx良好的框架，stream模块前景一片光明。

## 二、stream 模块编译

stream模块默认没有编译到nginx， 编译nginx时候 ./configure –with-stream 即可

官网：<http://nginx.org/en/docs/stream/ngx_stream_core_module.html>



## 三、使用stream模块做ssh转发

使用案例：

公司的测试主机在内网，不能直接访问，我们可以使用nginx做个tcp代理(使用stream模块)做个端口映射达到可以在本地直接访问内网测试主机的目的，这里主要做ssh映射。

注意：如果是nginx1.9之前的版本可以需要自己下载tcp插件，可以参考[nginx添加tcp代理模块](https://blog.csdn.net/lingdaz/article/details/3964000)

### 3.1 安装nginx

```shell
#.1下载nginx，注意需要1.9以上
wget http://nginx.org/download/nginx-1.12.1.tar.gz  
#.2安装一些依赖包
sudo yum -y install gcc gcc-c++ automake pcre pcre-devel zlib zlib-devel openssl openssl-devel  
#3解压
tar -zxvf nginx-1.12.1.tar.gz
#.4创建nginx安装目录
mkdir nginx
#.5配置 加入tcp（stream）模块，https模块和状态监控模块一起编译
./configure --prefix=/home/caojx/nginx --with-stream --with-stream_ssl_module --with-http_stub_status_module 
#.6安装
make && make install
```

### 3.2 配置ssh内网转发

```conf
worker_processes  1;

events {
    worker_connections  1024;
}

stream { #stream模块，就跟http模块一样  
        upstream ssh {
                server 119.29.234.71:22;
        }
        server { #里面可以有多个监听服务，配置监听端口和代理的ip和端口就可以进行tcp代理了。  
                listen 80;
                proxy_pass ssh;
                proxy_connect_timeout 1h;
                proxy_timeout 1h;
        }
}
```

### 3.3 测试环境启动nginx

```shell
#1.启动nginx
sudo ./nginx
```

### 3.4 本地通过80端口连接内网主机

```shell
#使用代理端口连接测试环境
ssh -p 80 caojx@119.29.234.71
```

## 四、参考文章

[centos7.2 源码编译安装nginx，实现tcp反向代理，不中断服务新增编译模块](https://blog.csdn.net/weixin_41004350/article/details/78492251)