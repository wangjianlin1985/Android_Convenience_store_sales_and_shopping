<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.SendWay" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    SendWay sendWay = (SendWay)request.getAttribute("sendWay");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸��ͻ���ʽ</TITLE>
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
/*��֤��*/
function checkForm() {
    var sendWayName = document.getElementById("sendWay.sendWayName").value;
    if(sendWayName=="") {
        alert('�������ͻ���ʽ����!');
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
    <TD align="left" vAlign=top ><s:form action="SendWay/SendWay_ModifySendWay.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>�ͻ���ʽid:</td>
    <td width=70%><input id="sendWay.sendWayId" name="sendWay.sendWayId" type="text" value="<%=sendWay.getSendWayId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>�ͻ���ʽ����:</td>
    <td width=70%><input id="sendWay.sendWayName" name="sendWay.sendWayName" type="text" size="20" value='<%=sendWay.getSendWayName() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
