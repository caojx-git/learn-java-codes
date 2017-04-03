##Git 标签

[原文链接](http://www.runoob.com/git/git-tag.html)

如果你达到一个重要的阶段，并希望永远记住那个特别的提交快照，你可以使用 git tag 给它打上标签。
比如说，我们想为我们的 w3cschoolcc 项目发布一个"1.0"版本。 我们可以用 git tag -a v1.0 命令给最新一次提交打上（HEAD）"v1.0"的标签。
-a 选项意为"创建一个带注解的标签"。 不用 -a 选项也可以执行的，但它不会记录这标签是啥时候打的，谁打的，也不会让你添加个标签的注解。 我推荐一直创建带注解的标签。
>$ git tag -a v1.0 

当你执行 git tag -a 命令时，Git 会打开你的编辑器，让你写一句标签注解，就像你给提交写注解一样。
现在，注意当我们执行 git log --decorate 时，我们可以看到我们的标签了：
>$ git log --oneline --decorate --graph  
*   88afe0e (HEAD, tag: v1.0, master) Merge branch 'change_site'    
|\    
| * d7e7346 (change_site) changed the site    
* | 14b4dca 新增加一行  
|/  
* 556f0a0 removed test2.txt  
* 2e082b7 add test2.txt  
* 048598f add test.txt  
* 85fc7e7 test comment from w3cschool.cc  

如果我们忘了给某个提交打标签，又将它发布了，我们可以给它追加标签。
例如，假设我们发布了提交 85fc7e7(上面实例最后一行)，但是那时候忘了给它打标签。 我们现在也可以：
>$ git tag -a v0.9 85fc7e7  
$ git log --oneline --decorate --graph  
*   88afe0e (HEAD, tag: v1.0, master) Merge branch 'change_site'    
|\    
| * d7e7346 (change_site) changed the site  
* | 14b4dca 新增加一行  
|/    
* 556f0a0 removed test2.txt  
* 2e082b7 add test2.txt  
* 048598f add test.txt  
* 85fc7e7 (tag: v0.9) test comment from w3cschool.cc  

如果我们要查看所有标签可以使用以下命令：
>$ git tag  
v0.9  
v1.0  

指定标签信息命令：
>git tag -a <tagname> -m "w3cschool.cc标签"

PGP签名标签命令：
>git tag -s <tagname> -m "w3cschool.cc标签"