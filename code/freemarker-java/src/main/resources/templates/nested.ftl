<#macro border>
  <table border=4 cellspacing=0 cellpadding=4><tr><td>
    <#nested>
  </td></tr></table>
</#macro>

<@border>表格中的内容！</@border> <#--  表格中的内容会替换 <#nested>  -->