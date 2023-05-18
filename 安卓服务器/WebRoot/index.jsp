<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%> <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<title>D454基于安卓平台便利店销售及备餐服务APP的设计与实现-首页</title>
<link href="<%=basePath %>css/index.css" rel="stylesheet" type="text/css" />
 </head>
<body>
<div id="container">
	<div id="banner"><img src="<%=basePath %>images/logo.gif" /></div>
	<div id="globallink">
		<ul>
			<li><a href="<%=basePath %>index.jsp">首页</a></li>
			<li><a href="<%=basePath %>UserInfo/UserInfo_FrontQueryUserInfo.action" target="OfficeMain">用户</a></li> 
			<li><a href="<%=basePath %>Seller/Seller_FrontQuerySeller.action" target="OfficeMain">商家</a></li> 
			<li><a href="<%=basePath %>ProductClass/ProductClass_FrontQueryProductClass.action" target="OfficeMain">商品类别</a></li> 
			<li><a href="<%=basePath %>Product/Product_FrontQueryProduct.action" target="OfficeMain">商品</a></li> 
			<li><a href="<%=basePath %>ShopCart/ShopCart_FrontQueryShopCart.action" target="OfficeMain">购物车</a></li> 
			<li><a href="<%=basePath %>OrderInfo/OrderInfo_FrontQueryOrderInfo.action" target="OfficeMain">订单</a></li> 
			<li><a href="<%=basePath %>OrderItem/OrderItem_FrontQueryOrderItem.action" target="OfficeMain">订单条目</a></li> 
			<li><a href="<%=basePath %>Collection/Collection_FrontQueryCollection.action" target="OfficeMain">宝贝收藏</a></li> 
		</ul>
		<br />
	</div> 
	<div id="main">
	 <iframe id="frame1" src="<%=basePath %>desk.jsp" name="OfficeMain" width="100%" height="100%" scrolling="yes" marginwidth=0 marginheight=0 frameborder=0 vspace=0 hspace=0 >
	 </iframe>
	</div>
	<div id="footer">
		<p>版权所有 &nbsp;&nbsp;<a href="<%=basePath%>login/login_view.action"><font color=red>后台登陆</font></a></p>
	</div>
</div>
</body>
</html>
