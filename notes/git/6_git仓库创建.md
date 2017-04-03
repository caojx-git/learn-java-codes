## Git 创建仓库

[原文链接](http://www.runoob.com/git/git-create-repository.html)

本章节我们将为大家介绍如何创建一个 Git 仓库。
你可以使用一个已经存在的目录作为Git仓库。
>git init

Git 使用 git init 命令来初始化一个 Git 仓库，Git 的很多命令都需要在 Git 的仓库中运行，所以 git init 是使用 Git 的第一个命令。
在执行完成 git init 命令后，Git 仓库会生成一个 .git 目录，该目录包含了资源的所有元数据，其他的项目目录保持不变（不像 SVN 会在每个子目录生成 .svn 目录，Git 只在仓库的根目录生成 .git 目录）。

### 使用方法
**方式一，**使用当前目录作为Git仓库，我们只需使它初始化。
>git init

该命令执行完后会在当前目录生成一个 .git 目录。
**方式二，** 使用指定目录作为Git仓库。
>git init 指定目录

初始化后，会在 指定 目录下会出现一个名为 .git 的目录，所有 Git 需要的数据和资源都存放在这个目录中。
如果当前目录下有几个文件想要纳入版本控制，需要先用 git add 命令告诉 Git 开始对这些文件进行跟踪，然后提交：
>$ git add *.c  
$ git add README  
$ git commit -m '初始化项目版本'  
以上命令将目录下以 .c 结尾及 README 文件提交到仓库中。  

### git clone
我们使用 git clone 从现有 Git 仓库中拷贝项目（类似 svn checkout）。
克隆仓库的命令格式为：
>git  clone  < repo >

如果我们需要克隆到指定的目录，可以使用以下命令格式：
>git clone  <repo>   < directory >

参数说明：
repo:Git 仓库。
directory:本地目录。

比如，要克隆 Ruby 语言的 Git 代码仓库 Grit，可以用下面的命令：
>$ git clone git://github.com/schacon/grit.git

执行该命令后，会在当前目录下创建一个名为grit的目录，其中包含一个 .git 的目录，用于保存下载下来的所有版本记录。
如果要自己定义要新建的项目目录名称，可以在上面的命令末尾指定新的名字：
>$ git clone git://github.com/schacon/grit.git mygrit