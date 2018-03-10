测试数据类型


#########boolean##########
<#assign b=true  />

<#--
在模板中${}的方式只能输出基本数据类型如int与String
这里的boolean用${b}就会报错，而必须使用${b?string}
-->
布尔值：${b?string}

##########三木运算#########
<#--
模板中的三木运算 这里就是b=true 结果为Yes, b=false结果为No
-->
三木运算：${b?string("Yes","No")}

##########数字############
<#assign b=123/>
数字：${b}

##########字符串##########
<#assign b="abc"/>
字符串：${b}

#########集合##############
1.int类型集合
<#assign b=1..5/>
int集合1..5第0号元素：${b[0]}

<#assign b=[2,3,5]/>
int集合 ${b[0]}

2.string类型集合
<#assign b=["a","b","c","d"]/>
字符集合需要[]:${b[0]}

########list#######
item_index:当前变量的索引值
item_has_next:是否存在下一个对象

1.list遍历集合
<#list userList as user>
    ${user.userId}
</#list>

2.list 升序
<#list userList?sort as user>
    ${user.userId}
</#list>

3.list 降序
<#list userList?reverse as user>
    ${user.userId}
</#list>-->

4. sort_by 按照指定的变量值排序
sort_by有一个参数,该参数用于指定想要排序的子变量，排序是按照变量对应的值进行排序,如：
<#list userList?sort_by("age") as user>
    ${user.age}
</#list>
age是User对象的属性，排序是按age的值进行的。

5.遍历list并应用list隐含变量item_index
<#list userList as user>
  第${user_index+1}个用户
  用户编号：${user.userId}
  用户名：${user.userName}
  年  龄: ${user.age}
</#list>

6.遍历list并应用list隐含变量item_has_next
item_has_next,size使用：
<#list userList as user>
    用户编号:${user.userId}
    用户名：${user.userName}
    年  龄: ${user.age}
    <#if !user_has_next>
        共有${userList?size}最后一个用户是:${user.userName}
    </#if>
</#list>


###########map key-value#############
1.定义map
<#--创建一个map,注意在freemarker中,map的key只能是字符串来作为key-->
<#assign userMap={"1":"刘德华","2":"张学友"}/>
<#--通过key获取map中的值-->
${userMap["1"]}

2.获取map的keys
<#assign keys=userMap?keys/>

3.遍历map 首选获取key的集合
<#list keys as key>
  key:${key}-value:${userMap["${key}"]}
</#list>

4.直接遍历map的第二种方式
<#list userMap?keys as key>
  key:${key}--value:${userMap["${key}"]}
</#list>


5.直接遍历map的values
<#list userMap?values as value>
    ${value}
</#list>

#############时间################

获取当前时间：${.now}
模板中的日期格式转换:${.now?string("yyyy-MM-dd HH:mm:ss")}


#############javabean###############

${"userId:"+user.userId+" userName:"+user.userName+" age:"+user.age}



