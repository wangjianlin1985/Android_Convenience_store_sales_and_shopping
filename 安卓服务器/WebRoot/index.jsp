<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>D454���ڰ�׿ƽ̨���������ۼ����ͷ���APP�������ʵ��-��ҳ</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">��ҳ</a></li>
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">�û�</a></li> 
			<li><a href="<%=basePath %>Seller/Seller_FrontQuerySeller.action" target="OfficeMain">�̼�</a></li> 
			<li><a href="<%=basePath %>ProductClass/ProductClass_FrontQueryProductClass.action" target="OfficeMain">��Ʒ���</a></li> 
			<li><a href="<%=basePath %>Product/Product_FrontQueryProduct.action" target="OfficeMain">��Ʒ</a></li> 
			<li><a href="<%=basePath %>ShopCart/ShopCart_FrontQueryShopCart.action" target="OfficeMain">���ﳵ</a></li> 
			<li><a href="<%=basePath %>OrderInfo/OrderInfo_FrontQueryOrderInfo.action" target="OfficeMain">����</a></li> 
			<li><a href="<%=basePath %>OrderItem/OrderItem_FrontQueryOrderItem.action" target="OfficeMain">������Ŀ</a></li> 
			<li><a href="<%=basePath %>Collection/Collection_FrontQueryCollection.action" target="OfficeMain">�����ղ�</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>��Ȩ���� &nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>��̨��½</font></a></p>
	</div>
</div>
</body>
</html>
