 ### install haroopad on linux by tar.gz
 ***
 [原文连接](http://blog.csdn.net/lxbwolf/article/details/52804074)
 
 >Haroopad 是一款覆盖三大主流桌面系统的编辑器，支持 Windows、Mac OS X 和 Linux多个平台，安装简单，方便，对中文支持很好（很多网友还推荐ReText的，但是ReText在linux平台安装比较复杂)。
 
 ## 下载haroopad
 
 
 wget https://bitbucket.org/rhiokim/haroopad-download/downloads/haroopad-v0.13.1-x64.tar.gz
 
 ## 安装haroopad
 
 1. 解压haroopad-v0.13.1-x64.tar.gz
 cd /usr/local/src
 sudo mkdir haroopad
 sudo tar -zxvf haroopad-v0.13.1-x64.tar.gz -C haroopad
 2. 之后会出现如下内容
  control.tar.gz 
  data.tar.gz  
  debian-binary
 3. 解压 data.tar.gz，并将其子目录下边的内容拷贝到 “/” 目录下
 tar zxvf data.tar.gz
 sudo cp -r ./usr /

 4. 解压control.tar.gz，并赋予755权限
 tar zxf control.tar.gz
 chmod 755 postinst
 sudo ./postinst

### 运行haroopad

 在终端中输入命令 haroopad
 如下图所示，就是haroopad 效果图
 ![](../images/markdown/haroopad.png)

 使用haroopad打开xxx.md文件
 终端中输入：haroopad  xxx.md文件所在路径