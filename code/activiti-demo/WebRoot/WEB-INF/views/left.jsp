<%@ page language="java" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单</title>
<%@ include file="/js/commons.jspf" %>
<script language="JavaScript" type="text/JavaScript">
//判断浏览器的变量
NS4 = (document.layers) ? 1 : 0;
IE4 = (document.all) ? 1 : 0;
ver4 = (NS4 || IE4) ? 1 : 0;
//定义各个层的位置及显示状态
if (ver4) {
    with (document) {
        write("<STYLE TYPE='text/css'>");
        if (NS4) {
            write(".parent {position:absolute; visibility:visible}");
            write(".child {position:absolute; visibility:visible}");
            write(".regular {position:absolute; visibility:visible}")
        }
        else {
            write(".child {display:none}")
        }
        write("</STYLE>");
    }
}
//当菜单打开时，页面上菜单以下的东西的位置顺序往下推，菜单合起时，菜单以下的东西自动上移。
function arrange() {
    nextY = document.layers[firstInd].pageY +document.layers[firstInd].document.height;
    for (i=firstInd+1; i<document.layers.length; i++) {
        whichele = document.layers[i];
        if (whichele.visibility != "hide") {
            whichele.pageY = nextY;
            nextY += whichele.document.height;
        }
    }
}
//初始化菜单
function initIt(){
        divColl = document.getElementsByTagName("DIV");
        for (i=0; i<divColl.length; i++) {
            whichele = divColl(i);
            if (whichele.className == "child") whichele.style.display = "none";
        }
}
//展开菜单的方法
function expandIt(ele) {
    var layernum = 2;
	var childname = ele.substring(0,4);
	var childnum = ele.substring(4,5)
       whichele = document.getElementById(ele + "Child");
       if (whichele.style.display == "none") {
           whichele.style.display = "";
		for (i=1;i<layernum+1;i++)
		{ if (childnum != i)
		{ document.getElementById(childname + i +"Child").style.display ="none";
		}
		}
       }
       else {
           //whichele.style.display = "none";
       }
}
onload = initIt;
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE3 {
	font-size: 12px;
	color: #435255;
}
.STYLE4 {font-size: 12px}
.STYLE5 {font-size: 12px; font-weight: bold; }
a {text-decoration:none;color:black}
-->
</style></head>

<body>

<table width="147" height="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="23" background="images/main_34.gif"><div align="center" class="STYLE4" id="Menu1Parent"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="9%">&nbsp;</td>
        <td width="83%"><div align="center" class="STYLE4"><a href="#" onClick="expandIt('Menu1'); return false" >业务管理</a></div></td>
        <td width="8%">&nbsp;</td>
      </tr>
    </table></div></td>
  </tr>
  <tr>
    <td valign="top"><div align="center" id="Menu1Child">
      <table width="82%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="33" height="28"><img src="images/main_52.gif" width="28" height="28"></td>
              <td width="99"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="23" class="STYLE4" style="cursor:hand" onMouseOver="this.style.backgroundImage='url(images/tab_bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">
                    	<a href="leaveBillAction_home.action" target="main">请假管理</a>
                    </td>
                  </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
        
      </table>
    </div></td>
  </tr>
  <tr>
    <td height="23" background="images/main_34_1.gif"><div align="center" class="STYLE4" id="Menu2Parent"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="9%">&nbsp;</td>
        <td width="83%"><div align="center" class="STYLE4"><a href="#" onClick="expandIt('Menu2'); return false" >流程管理</a></div></td>
        <td width="8%">&nbsp;</td>
      </tr>
    </table></div></td>
  </tr>
   <tr>
    <td valign="top"><div align="center" id="Menu2Child" style="display:none">
      <table width="82%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="33" height="28"><img src="images/main_48.gif" width="28" height="28"></td>
              <td width="99"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="23" class="STYLE4" style="cursor:hand" onMouseOver="this.style.backgroundImage='url(images/tab_bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">
                    	<a href="workflowAction_deployHome.action" target="main">部署管理</a>
                    </td>
                  </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
        <tr>
          <td height="38"><table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="33" height="28"><img src="images/main_40.gif" width="28" height="28"></td>
              <td width="99"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td height="23" class="STYLE4" style="cursor:hand" onMouseOver="this.style.backgroundImage='url(images/tab_bg.gif)';this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">
						<a href="workflowAction_listTask.action" target="main">任务管理</a>
					</td>
                  </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
      </table></div>
    </td>
  </tr>
  
  
  <tr>
    <td height="19" background="images/main_69.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="1%">&nbsp;</td>
        <td width="99%" valign="bottom"><span class="STYLE3">刘洋(QQ:506069708)</span></td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
