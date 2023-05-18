<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加商家</TITLE> 
<STYLE type=text/css>
BODY {
    	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var sellUserName = document.getElementById("seller.sellUserName").value;
    if(sellUserName=="") {
        alert('请输入商家账号!');
        return false;
    }
    var password = document.getElementById("seller.password").value;
    if(password=="") {
        alert('请输入登录密码!');
        return false;
    }
    var sellerName = document.getElementById("seller.sellerName").value;
    if(sellerName=="") {
        alert('请输入商家名称!');
        return false;
    }
    var telephone = document.getElementById("seller.telephone").value;
    if(telephone=="") {
        alert('请输入联系电话!');
        return false;
    }
    var address = document.getElementById("seller.address").value;
    if(address=="") {
        alert('请输入商家地址!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>

<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top >
    <s:form action="Seller/Seller_AddSeller.action" method="post" id="sellerAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>商家账号:</td>
    <td width=70%><input id="seller.sellUserName" name="seller.sellUserName" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><input id="seller.password" name="seller.password" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>商家名称:</td>
    <td width=70%><input id="seller.sellerName" name="seller.sellerName" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="seller.telephone" name="seller.telephone" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>商家地址:</td>
    <td width=70%><input id="seller.address" name="seller.address" type="text" size="80" /></td>
  </tr>

  <tr>
    <td width=30%>入住时间:</td>
    <td width=70%><input id="seller.regTime" name="seller.regTime" type="text" size="30" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
