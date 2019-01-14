# Navicat Premium 激活

时间：2019年1月11日

本文参考：

https://blog.csdn.net/u013490280/article/details/79720893

https://www.jianshu.com/p/f3ef78deadaa

[TOC]



**注意，Navicat Premium可以自己从官网下载，我用的12.0.22.0版本，如果官网最新版本不可以激活，再使用这个版本激活后再升级，注意我使用的是Mac，Windows应该也可以激活。**

**如果不想操作这么多，直接使用第5点，提供了mac版激活后的包，可以直接使用**



## 1、RSA公钥私钥对

（这里直接使用大神的密钥，也可以自己生成测试可以用）

这步与windows版破解相同，可以用open ssl工具生成，也可以使用其他工具生成，注意密钥是2048位的，PKCS#8格式
为了节省时间，可以使用我提供的一对密钥。
公钥：

-----BEGIN PUBLIC KEY-----
MIIBITANBgkqhkiG9w0BAQEFAAOCAQ4AMIIBCQKCAQB8vXG0ImYhLHvHhpi5FS3g
d2QhxSQiU6dQ04F1OHB0yRRQ3NXF5py2NNDw962i4WP1zpUOHh94/mg/KA8KHNJX
HtQVLXMRms+chomsQCwkDi2jbgUa4jRFN/6N3QejJ42jHasY3MJfALcnHCY3KDEF
h0N89FV4yGLyDLr+TLqpRecg9pkPnOp++UTSsxz/e0ONlPYrra/DiaBjsleAESZS
I69sPD9xZRt+EciXVQfybI/2SYeAdXMm1B7tHCcFlOxeUgqYV03VEqiC0jVMwRCd
+03NU3wvEmLBvGOmNGudocWIF/y3VOqyW1byXFLeZxl7s+Y/SthxOYXzu3mF+2/p
AgMBAAE=
-----END PUBLIC KEY-----


私钥：


-----BEGIN RSA PRIVATE KEY-----
MIIEogIBAAKCAQB8vXG0ImYhLHvHhpi5FS3gd2QhxSQiU6dQ04F1OHB0yRRQ3NXF
5py2NNDw962i4WP1zpUOHh94/mg/KA8KHNJXHtQVLXMRms+chomsQCwkDi2jbgUa
4jRFN/6N3QejJ42jHasY3MJfALcnHCY3KDEFh0N89FV4yGLyDLr+TLqpRecg9pkP
nOp++UTSsxz/e0ONlPYrra/DiaBjsleAESZSI69sPD9xZRt+EciXVQfybI/2SYeA
dXMm1B7tHCcFlOxeUgqYV03VEqiC0jVMwRCd+03NU3wvEmLBvGOmNGudocWIF/y3
VOqyW1byXFLeZxl7s+Y/SthxOYXzu3mF+2/pAgMBAAECggEAK5qZbYt8wenn1uZg
6onRwJ5bfUaJjApL+YAFx/ETtm83z9ByVbx4WWT7CNC7fK1nINy20/mJrOTZkgIx
x6otiNC4+DIsACJqol+RLoo8I9pk77Ucybn65ZteOz7hVZIU+8j6LzW0KDt6yowX
e75r7G/NEpfibNc3Zz81+oDd2x+bHyGbzc9QcePIVuEzkof6jgpbWrQZU14itx9l
VxEgj/fbMccvBx8brR/l9ClmDZd9Y6TWsF1rfJpF3+DPeqFkKCiD7PGz3bs4O/Zd
ZrfV21ZNVusBW49G6bU63gQVKsOf1qGo3efbAW1HVxgTQ/lExVdcMvdenZm+ADKp
L4/wUQKBgQDOfBjn3OC2IerUFu18EgCS7pSjTSibXw+TeX3D5zwszLC091G2rGlT
5DihBUhMfesNdpoZynrs4YB6Sz9C3wSGAB8AM/tNvPhtSVtbMHmrdT2DEEKCvLkO
RNBnt+8aTu2hGRanw9aL1189gzwrmXK5ZuuURfgLrB9ihrvjo4VznQKBgQCapx13
dEA1MwapBiIa3k8hVBCoGPsEPWqM33RBdUqUsP33f9/PCx00j/akwmjgQNnBlAJo
Y7LOqPCyiwOkEf40T4IlHdzYntWQQvHhfBwqSgdkTE9tKj43Ddr7JVFRL6yMSbW3
9qAp5UX/+VzOLGAlfzJ8CBnkXwGrnKPCVbnZvQKBgQCd+iof80jlcCu3GteVrjxM
LkcAbb8cqG1FWpVTNe4/JFgqDHKzPVPUgG6nG2CGTWxxv4UFKHpGE/11E28SHYjb
cOpHAH5LqsGy84X2za649JkcVmtclUFMXm/Ietxvl2WNdKF1t4rFMQFIEckOXnd8
y/Z/Wcz+OTFF82l7L5ehrQKBgFXl9m7v6e3ijpN5LZ5A1jDL0Yicf2fmePUP9DGb
ZTZbbGR46SXFpY4ZXEQ9GyVbv9dOT1wN7DXvDeoNXpNVzxzdAIt/H7hN2I8NL+4v
EjHG9n4WCJO4v9+yWWvfWWA/m5Y8JqusV1+N0iiQJ6T4btrE4JSVp1P6FSJtmWOK
W/T9AoGAcMhPMCL+N+AvWcYt4Y4mhelvDG8e/Jj4U+lwS3g7YmuQuYx7h5tjrS33
w4o20g/3XudPMJHhA3z+d8b3GaVM3ZtcRM3+Rvk+zSOcGSwn3yDy4NYlv9bdUj/4
H+aU1Qu1ZYojFM1Gmbe4HeYDOzRsJ5BhNrrV12h27JWkiRJ4F/Q=
-----END RSA PRIVATE KEY-----

## 2、下载安装

Mac版 Navicat Premium 12 v12.0.22.0 官网下载地址：
英文64位 [http://download.navicat.com/download/navicat120_premium_en.dmg](http://download.navicat.com/download/navicat120_premium_en.dmg)
中文简体64位 [http://download.navicat.com/download/navicat120_premium_cs.dmg](http://download.navicat.com/download/navicat120_premium_cs.dmg)

**注意，Navicat Premium可以自己从官网下载，我用的12.0.22.0版本，如果官网最新版本不可以激活，再使用这个版本激活后再升级，注意我使用的是Mac，Windows应该也可以激活。**



注意：安装完成后别着急打开软件, 在目录 /Applications/Navicat Premium.app/Contents/Resources下新建文本文件 `rpk`将`公钥`复制到`rpk`文件中保存

![](../images/tool/navicat/navicat-rpk.png)  



## 3、断网！！！非常重要，获取解密请求码

`中文版64位密钥序列号： NAVH-T4PX-WT8W-QBL5` 
`英文版64位密钥序列号： NAVG-UJ8Z-EVAP-JAUW` 
输入后点击“激活”因为我们之前断了网所以会有一个手动激活的提示。

![](../images/tool/navicat/navicat-2.png)

![](../images/tool/navicat/navicat-3.png)

点击手动激活会出现如下的界面：   
![](../images/tool/navicat/navicat-4.png)
在这儿激活码是需要我们填写的。

## 4、获取激活码激活

解密请求码需要我们上边所提到的私钥。解密的地址为：http://tool.chacuo.net/cryptrsaprikey 

![](../images/tool/navicat/navicat-5.png)  

解密后的内容为:

```json
{
  "K" : "NAVHT4PXWT8WQBL5",
  "P" : "Mac 10.13",
  "DI" : "ODQ2Yjg2ZDBjMTEzMjhh"
}
```

获取一个未来时间的时间戳获取地址：https://tool.lu/timestamp/

![](../images/tool/navicat/navicat-6.png)

把如上内容对应的 K P DI  T填充到如下文本中，T为时间戳，其他的内容没有罗列的不用管。

```json
{"K":"NAVHT4PXWT8WQBL5", "N":"marswill", "O":"weiyongqiang.com", "DI":"ODQ2Yjg2ZDBjMTEzMjhh", "T":1547206906}
```



使用上边的内容，再次加密，获得激活码

![](../images/tool/navicat/navicat-7.png)



激活完成

![](../images/tool/navicat/navicat-8.png)  



## 5、不成功最后方案

我是在2019年1月11号激活的，如果以上方式都失败了，我这边提供了我激活后的Mac版本的包，不用再激活，可以正常安装更新

下载链接: <https://pan.baidu.com/s/1XHeun_GkRZFQesfYV3QINQ> 提取码: u23b 复制这段内容后打开百度网盘手机App，操作更方便哦



![](../images/tool/navicat/navicat-9.png)  

