<#--定义指令m1 -->
<#macro m1>
	<b>aaabbbccc</b>
	<b>dddeeefff</b>
</#macro>

<#--调用上面的宏指令 -->
<@m1 />
<@m1 />


<#--带参数的宏指令定义-->
<#macro m2 a b c >
    ${a}--${b}--${c}
</#macro>

<#--  调用带参数的宏指令 -->
<@m2 a="老高" b="老张" c="老马" />