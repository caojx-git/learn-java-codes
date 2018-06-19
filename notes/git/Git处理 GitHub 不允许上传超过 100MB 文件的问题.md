# Git处理 GitHub 不允许上传超过 100MB 文件的问题

参考原文：http://www.liuxiao.org/2017/02/git-%E5%A4%84%E7%90%86-github-%E4%B8%8D%E5%85%81%E8%AE%B8%E4%B8%8A%E4%BC%A0%E8%B6%85%E8%BF%87-100mb-%E6%96%87%E4%BB%B6%E7%9A%84%E9%97%AE%E9%A2%98/



其他解决方案：

http://jingpin.jikexueyuan.com/article/36116.html



最近在使用 Github 时候遇到一个问题，提示文件过大不允许上传：

```shell
remote: error: GH001: Large files detected. You may want to try Git Large File Storage - https://git-lfs.github.com.
remote: error: Trace: 150be0ada58c003160b0798a30b48c46
remote: error: See http://git.io/iEPt8g for more information.
remote: error: File notes/spring/Spring+Cloud与Docker微服务架构实战.pdf is 100.71 MB; this exceeds GitHub's file size limit of 100.00 MB
To https://github.com/caojx-git/learn.git
 ! [remote rejected]   master -> master (pre-receive hook declined)
error: failed to push some refs to 'https://github.com/caojx-git/learn.git'
```

但有的时候我们还是需要上传这些大文件，这时候怎样做呢？

## 一、移除错误重新提交

### 1.1 移除错误缓存

首先应该移除所有错误的 cache，对于文件：

```shell
git rm --cached path_of_a_giant_file
```

对于文件夹：

```shell
git rm --cached -r path_of_a_giant_dir
```

例如对于我的例子就是这样的：

```shell
cd /Users/caojx/code/learn/notes/spring/
git rm --cached Spring+Cloud与Docker微服务架构实战.pdf
```

###1.2 重新提交

执行下边命令大文件将会从你的commit记录里移除。

```shell
git commit --amend -CHEAD
```

重新提交：

```shell
git push
```

## 二、支持大文件支持

### 2.1 将大文件加入 Git Large File Storage

1）首先安装 git-lfs（下边是mac版的安装方式，其他方式见官网https://git-lfs.github.com/）

```shell
brew install git-lfs
```

2）将想要保存的大文件 “路径” 或者 “类型” 添加进 track：

```shell
git lfs track "name_of_a_giant_file"
```


例如对于我的例子就是这样的：

```shell
git lfs track "*.pdf"
```

3）确保.gitattributes文件被追踪到

```shell
git add .gitattributes
```

**需要注意的是这里面仅能添加类型的扩展名或者文件名作为跟踪方式，不可以添加路径或者目录进行跟踪**

### 2.2 将想要保存的大文件正常添加进 git

```shell
git add path_of_a_giant_file
```

或者：

```shell
git add extension_name_of_giant_files
```

例如对于我的例子就是这样的：

```shell
git add Examples/iOSDemo/Pods/dependency/libg2o.a
```

### 2.3 正常进行提交&推送

```shell
git commit -m "Add design file"
git push origin master
```

### 2.4 补充技巧

提交以后出错再进行上面的步骤可能比较麻烦，如果你已知自己提交的版本库中确实存在一些大于 100MB 的文件，不妨先搜索：

```shell
find ./ -size +100M
```

然后将这些文件移除，等待其他文件提交完后再复制回来，这样只需要从步骤3的操作开始就可以了。

### 2.5 常见问题

1、错误：fatal error: unexpected signal during runtime execution

`goroutine 23 [chan receive]:github.com/github/git-lfs/lfs.ScanRefsToChan.func2(0xc8200d4540, 0xc8200c6000, 0xc8200d45a0)/Users/rick/go/src/github.com/github/git-lfs/lfs/scanner.go:153 +0x4ecreated by github.com/github/git-lfs/lfs.ScanRefsToChan/Users/rick/go/src/github.com/github/git-lfs/lfs/scanner.go:160 +0x30c`

出现这个问题通常是由于 go 引擎未安装或者版本太老（1.5.1及以下版本在 Mac 上面有未知错误），或者 git-lfs 版本太老。如果没有安装 go，可使用如下命令安装：

```shell
brew install git-lfs
```

然后使用如下命令升级：

```shell
brew update
brew upgrade go
brew upgrade git-lfs
```


然后使用如下命令查看：

```shell
git-lfs version
```

我这里的版本号如下，如果你比我的版本高就对了，否则可以尝试卸载之前安装的 go 和 git-lfs 重新安装：
`git-lfs/1.5.5 (GitHub; darwin amd64; go 1.7.4)`

## 三、参考文献

[1] <https://help.github.com/enterprise/11.10.340/user/articles/working-with-large-files/>
[2] <https://git-lfs.github.com/>