## Git 查看提交历史
在使用 Git 提交了若干更新之后，又或者克隆了某个项目，想回顾下提交历史，我们可以使用 git log 命令查看。
针对我们前一章节的操作，使用 git log 命令列出历史提交记录如下：
>$ git log  
commit 88afe0e02adcdfea6844bb627de97da21eb10af1  
Merge: 14b4dca d7e7346  
Author: w3cschool <w3c@w3cschool.cc>  
Date:   Sun Mar 1 15:03:42 2015 +0800  
    Merge branch 'change_site'  
    	Conflicts:  
        test.txt  
commit 14b4dcadbdc847207651d5a9fae0d315057f346e  
Author: w3cschool <w3c@w3cschool.cc>  
Date:   Sun Mar 1 14:53:15 2015 +0800  
    新增加一行  
commit d7e734640da06055e107eaf29cf350b3f1de1c2c  
Author: w3cschool <w3c@w3cschool.cc>  
Date:   Sun Mar 1 14:48:57 2015 +0800  
    changed the site  
commit 556f0a0637978097b82287ac665a717623b21f3f    
Author: w3cschool <w3c@w3cschool.cc>  
Date:   Sun Mar 1 14:40:34 2015 +0800  
    removed test2.txt  

我们可以用 --oneline 选项来查看历史记录的简洁的版本。
>$ git log --oneline  
88afe0e Merge branch 'change_site'  
14b4dca 新增加一行  
d7e7346 changed the site  
556f0a0 removed test2.txt  
2e082b7 add test2.txt  
048598f add test.txt  
85fc7e7 test comment from w3cschool.cc  

这告诉我们的是，此项目的开发历史。
我们还可以用 --graph 选项，查看历史中什么时候出现了分支、合并。以下为相同的命令，开启了拓扑图选项：
>$ git log --oneline --graph  
\*   88afe0e Merge branch 'change_site'  
|\  
| * d7e7346 changed the site  
\ * | 14b4dca 新增加一行  
|/  
\* 556f0a0 removed test2.txt  
\* 2e082b7 add test2.txt  
\* 048598f add test.txt  
\* 85fc7e7 test comment from w3cschool.cc    

现在我们可以更清楚明了地看到何时工作分叉、又何时归并。
你也可以用 '--reverse'参数来逆向显示所有日志。
>$ git log --reverse --oneline  
85fc7e7 test comment from w3cschool.cc    
048598f add test.txt  
2e082b7 add test2.txt  
556f0a0 removed test2.txt  
d7e7346 changed the site  
14b4dca 新增加一行  
88afe0e Merge branch 'change_site'    

如果只想查找指定用户的提交日志可以使用命令：git log --author , 例如，比方说我们要找 Git 源码中 Linus 提交的部分：

>$ git log --author=Linus --oneline -5  
81b50f3 Move 'builtin-*' into a 'builtin/' subdirectory    
3bb7256 make "index-pack" a built-in  
377d027 make "git pack-redundant" a built-in    
b532581 make "git unpack-file" a built-in    
112dd51 make "mktag" a built-in    

如果你要指定日期，可以执行几个选项：--since 和 --before，但是你也可以用 --until 和 --after。
例如，如果我要看 Git 项目中三周前且在四月十八日之后的所有提交，我可以执行这个（我还用了 --no-merges 选项以隐藏合并提交）：
>$ git log --oneline --before={3.weeks.ago} --after={2010-04-18} --no-merges    
5469e2d Git 1.7.1-rc2  
d43427d Documentation/remote-helpers: Fix typos and improve language      
272a36b Fixup: Second argument may be any arbitrary string  
b6c8d2d Documentation/remote-helpers: Add invocation section  
5ce4f4e Documentation/urls: Rewrite to accomodate transport::address  
00b84e9 Documentation/remote-helpers: Rewrite description  
03aa87e Documentation: Describe other situations where -z affects git diff      
77bc694 rebase-interactive: silence warning when no commits rewritten  
636db2c t3301: add tests to use --format="%N"  