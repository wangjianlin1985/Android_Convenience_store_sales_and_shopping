# Android_Convenience_store_sales_and_shopping
安卓Android多商家便利店销售购物商城APP设计毕业源码案例设计

## 开发环境: Myclipse/Eclipse/Idea(服务器端) + Eclipse或Android Studio(手机客户端) + mysql数据库
## 系统客户端和服务器端架构技术: 界面层，业务逻辑层，数据层3层分离技术，MVC设计思想！
## 服务器和客户端数据通信格式： json数据格式

①顾客可以浏览主页、订单页面、个人信息、收藏、购物车、店铺列表、商品列表

②商品就分为两类：1.普通商品（普通商品多为零食饮料，和一般便利店差不多，泡面也在其中），2.备餐商品（备餐商品多为熟食饭菜快餐和方便面）。

③顾客将商品添加到购物车，也可以将商品从购物车删除。在点击购买时可以添加备注（就像美团买外卖时备注一样），之后可以选择到店自取或者送货上门，这个可以打勾进行选择。之后选择支付方式进行支付。

④顾客查看个人信息，个人信息除了基本性别，号码，名称，加一个收货地址。顾客还可以收藏店铺或者商品，这两项信息可以在收藏中看到。顾客可以编辑自己信息。

⑤顾客可以根据文字搜索和条件筛选来获取店铺和商品信息

⑥店家可以管理自己的店铺信息，以及商品信息，以及查看自家店铺的订单信息。店家还可以管理个人信息

⑦店家和顾客进行注册和登录

⑧主页按照排版什么的按照设计者思路来吧，功能的话主要把功能模块叙述的做出来或上述几点的做出来就行。

## 实体ER属性：
用户: 用户名,登录密码,姓名,性别,出生日期,用户照片,联系电话,邮箱,送货地址,注册时间

商家: 商家账号,登录密码,商家名称,联系电话,商家地址,入住时间

商品类别: 类别id,类别名称,类别描述

商品: 商品id,商品类别,商品名称,商品主图,商品价格,商品描述,发布商家,发布时间

购物车: 购物车id,商品,用户,单价,购买数量

订单: 订单编号,下单用户,订单总金额,支付方式,订单状态,下单时间,收货人,收货人电话,收货人地址,送货方式,订单备注,订单卖家

订单条目: 条目id,所属订单,订单商品,商品单价,购买数量

支付方式: 支付方式id,支付方式名称

送货方式: 送货方式id,送货方式名称

宝贝收藏: 收藏id,收藏宝贝,收藏用户,收藏时间
