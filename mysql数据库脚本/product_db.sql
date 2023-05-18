/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50051
Source Host           : localhost:3306
Source Database       : product_db

Target Server Type    : MYSQL
Target Server Version : 50051
File Encoding         : 65001

Date: 2019-08-22 12:14:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) default NULL,
  PRIMARY KEY  (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `collection`
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `collectionId` int(11) NOT NULL auto_increment,
  `productObj` int(11) default NULL,
  `userObj` varchar(20) default NULL,
  `collectionTime` varchar(20) default NULL,
  PRIMARY KEY  (`collectionId`),
  KEY `FKF078ABEC80FC67` (`userObj`),
  KEY `FKF078ABE1D6F5E55` (`productObj`),
  CONSTRAINT `FKF078ABE1D6F5E55` FOREIGN KEY (`productObj`) REFERENCES `product` (`productId`),
  CONSTRAINT `FKF078ABEC80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collection
-- ----------------------------
INSERT INTO `collection` VALUES ('1', '2', 'user1', '2019-08-21 22:29:23');

-- ----------------------------
-- Table structure for `orderinfo`
-- ----------------------------
DROP TABLE IF EXISTS `orderinfo`;
CREATE TABLE `orderinfo` (
  `orderNo` varchar(20) NOT NULL,
  `userObj` varchar(20) default NULL,
  `totalMoney` float default NULL,
  `payWay` int(11) default NULL,
  `orderStateObj` varchar(20) default NULL,
  `orderTime` varchar(20) default NULL,
  `receiveName` varchar(20) default NULL,
  `telephone` varchar(20) default NULL,
  `address` varchar(80) default NULL,
  `sendWayObj` int(11) default NULL,
  `orderMemo` longtext,
  `sellerObj` varchar(20) default NULL,
  PRIMARY KEY  (`orderNo`),
  KEY `FK601628FC2BE10D19` (`sellerObj`),
  KEY `FK601628FC96B5D335` (`sendWayObj`),
  KEY `FK601628FC45787EF0` (`payWay`),
  KEY `FK601628FCC80FC67` (`userObj`),
  CONSTRAINT `FK601628FCC80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`),
  CONSTRAINT `FK601628FC2BE10D19` FOREIGN KEY (`sellerObj`) REFERENCES `seller` (`sellUserName`),
  CONSTRAINT `FK601628FC45787EF0` FOREIGN KEY (`payWay`) REFERENCES `payway` (`payWayId`),
  CONSTRAINT `FK601628FC96B5D335` FOREIGN KEY (`sendWayObj`) REFERENCES `sendway` (`sendWayId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderinfo
-- ----------------------------
INSERT INTO `orderinfo` VALUES ('user1201908212158291', 'user1', '12', '1', '已发货', '2019-08-21 21:58:29', '用户1', '13508312432', '四川成都红星路', '1', '你可以来取货了', 'seller1');
INSERT INTO `orderinfo` VALUES ('user1201908212158292', 'user1', '56', '1', '待付款', '2019-08-21 21:58:29', '用户1', '13508312432', '四川成都红星路', '1', '快点配货哦', 'seller2');

-- ----------------------------
-- Table structure for `orderitem`
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem` (
  `itemId` int(11) NOT NULL auto_increment,
  `orderObj` varchar(20) default NULL,
  `productObj` int(11) default NULL,
  `price` float default NULL,
  `orderNumer` int(11) default NULL,
  PRIMARY KEY  (`itemId`),
  KEY `FK60163F61B2A6D843` (`orderObj`),
  KEY `FK60163F611D6F5E55` (`productObj`),
  CONSTRAINT `FK60163F611D6F5E55` FOREIGN KEY (`productObj`) REFERENCES `product` (`productId`),
  CONSTRAINT `FK60163F61B2A6D843` FOREIGN KEY (`orderObj`) REFERENCES `orderinfo` (`orderNo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderitem
-- ----------------------------
INSERT INTO `orderitem` VALUES ('2', 'user1201908212158291', '1', '12', '1');
INSERT INTO `orderitem` VALUES ('3', 'user1201908212158292', '2', '28', '2');

-- ----------------------------
-- Table structure for `payway`
-- ----------------------------
DROP TABLE IF EXISTS `payway`;
CREATE TABLE `payway` (
  `payWayId` int(11) NOT NULL auto_increment,
  `payWayName` varchar(20) default NULL,
  PRIMARY KEY  (`payWayId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of payway
-- ----------------------------
INSERT INTO `payway` VALUES ('1', '支付宝');
INSERT INTO `payway` VALUES ('2', '微信');

-- ----------------------------
-- Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `productId` int(11) NOT NULL auto_increment,
  `productClassObj` int(11) default NULL,
  `productName` varchar(50) default NULL,
  `mainPhoto` varchar(50) default NULL,
  `price` float default NULL,
  `productDesc` longtext,
  `sellerObj` varchar(20) default NULL,
  `addTime` varchar(30) default NULL,
  PRIMARY KEY  (`productId`),
  KEY `FK50C664CF2BE10D19` (`sellerObj`),
  KEY `FK50C664CF16092339` (`productClassObj`),
  CONSTRAINT `FK50C664CF16092339` FOREIGN KEY (`productClassObj`) REFERENCES `productclass` (`classId`),
  CONSTRAINT `FK50C664CF2BE10D19` FOREIGN KEY (`sellerObj`) REFERENCES `seller` (`sellUserName`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', '1', '好吃点饼干', 'upload/5BBBDB764ECC186DDB3D0D8BE9341B88.jpg', '12', '生产许可证编号：SC12742098400010厂名：湖北达利食品有限公司厂址：湖北省孝感市汉川市新河镇老街厂家联系方式：4001686669配料表：见包装储藏方法：置阴凉干燥处保质期：300 天食品添加剂：详见包装品牌: 好吃点系列: 2斤装高纤口味: 一斤装粗粮味 一斤装蔬菜味 一斤装消化味 一斤装随机混合味 2斤装粗粮味 2斤装消化味 2斤装蔬菜味 2斤装随机混合味 3斤装消化味 3斤装粗粮味 3斤装蔬菜味 3斤装随机混合味产地: 中国大陆省份: 湖北省包装方式: 包装净含量: 1000g', 'seller1', '2019-08-15 14:11:13');
INSERT INTO `product` VALUES ('2', '2', '青椒肉丝炒饭', 'upload/F258F801FD3531BAEB22FDD3B78DCF8E.jpg', '28', '精选上等肉丝，精炒而成，口感香甜，还送米饭一碗！', 'seller2', '2019-08-14 15:11:13');
INSERT INTO `product` VALUES ('4', '1', '嘉华鲜花饼经典玫瑰饼10枚', 'upload/927B13227AA0E09FD25A3B14CB2F4ADC.jpg', '30', '嘉华鲜花饼经典玫瑰饼10枚云南特产零食小吃传统糕点饼干送便携袋，热销爆款，工厂直发，新鲜味美', 'seller1', '2019-08-21 22:54:48');
INSERT INTO `product` VALUES ('5', '2', '麻婆豆腐', 'upload/2B302D04527C3BF581257DB1A2FC7DA1.jpg', '16', '主要原料为配料和豆腐，材料主要有豆腐、牛肉末（也可以用猪肉）、辣椒和花椒等。麻来自花椒，辣来自辣椒，这道菜突出了川菜“麻辣”的特点。其口味独特，口感顺滑。', 'seller2', '2019-08-21 12:13:13');

-- ----------------------------
-- Table structure for `productclass`
-- ----------------------------
DROP TABLE IF EXISTS `productclass`;
CREATE TABLE `productclass` (
  `classId` int(11) NOT NULL auto_increment,
  `className` varchar(40) default NULL,
  `classDesc` longtext,
  PRIMARY KEY  (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of productclass
-- ----------------------------
INSERT INTO `productclass` VALUES ('1', '普通商品', '零食饮料，和一般便利店差不多，泡面也在其中');
INSERT INTO `productclass` VALUES ('2', '备餐商品', '多为熟食饭菜快餐和方便面');

-- ----------------------------
-- Table structure for `seller`
-- ----------------------------
DROP TABLE IF EXISTS `seller`;
CREATE TABLE `seller` (
  `sellUserName` varchar(20) NOT NULL,
  `password` varchar(30) default NULL,
  `sellerName` varchar(20) default NULL,
  `telephone` varchar(20) default NULL,
  `address` varchar(80) default NULL,
  `regTime` varchar(30) default NULL,
  PRIMARY KEY  (`sellUserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seller
-- ----------------------------
INSERT INTO `seller` VALUES ('seller1', '123', '多多商品超市', '13080831234', '四川成都建设路', '2019-08-14 15:12:34');
INSERT INTO `seller` VALUES ('seller2', '123', '王大姐餐饮店', '13410831234', '四川成都市万福路', '2019-08-14 15:12:34');

-- ----------------------------
-- Table structure for `sendway`
-- ----------------------------
DROP TABLE IF EXISTS `sendway`;
CREATE TABLE `sendway` (
  `sendWayId` int(11) NOT NULL auto_increment,
  `sendWayName` varchar(20) default NULL,
  PRIMARY KEY  (`sendWayId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sendway
-- ----------------------------
INSERT INTO `sendway` VALUES ('1', '到店自取');
INSERT INTO `sendway` VALUES ('2', '送货上门');

-- ----------------------------
-- Table structure for `shopcart`
-- ----------------------------
DROP TABLE IF EXISTS `shopcart`;
CREATE TABLE `shopcart` (
  `cartId` int(11) NOT NULL auto_increment,
  `productObj` int(11) default NULL,
  `userObj` varchar(20) default NULL,
  `price` float default NULL,
  `buyNum` int(11) default NULL,
  PRIMARY KEY  (`cartId`),
  KEY `FKEF3DC356C80FC67` (`userObj`),
  KEY `FKEF3DC3561D6F5E55` (`productObj`),
  CONSTRAINT `FKEF3DC3561D6F5E55` FOREIGN KEY (`productObj`) REFERENCES `product` (`productId`),
  CONSTRAINT `FKEF3DC356C80FC67` FOREIGN KEY (`userObj`) REFERENCES `userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shopcart
-- ----------------------------
INSERT INTO `shopcart` VALUES ('2', '1', 'user2', '12', '1');
INSERT INTO `shopcart` VALUES ('3', '2', 'user2', '28', '2');
INSERT INTO `shopcart` VALUES ('6', '2', 'user1', '28', '1');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `user_name` varchar(20) NOT NULL,
  `password` varchar(30) default NULL,
  `name` varchar(20) default NULL,
  `gender` varchar(4) default NULL,
  `birthDate` datetime default NULL,
  `userPhoto` varchar(50) default NULL,
  `telephone` varchar(20) default NULL,
  `email` varchar(50) default NULL,
  `address` varchar(80) default NULL,
  `regTime` varchar(30) default NULL,
  PRIMARY KEY  (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('user1', '123', '张艺兴', '男', '2019-08-06 00:00:00', 'upload/3415F1410D9F21680AE93187FDE85DDF.jpg', '13508312432', 'yixinga@126.com', '四川成都红星路13号', '2019-08-06 14:11:12');
INSERT INTO `userinfo` VALUES ('user2', '123', '王晓婷', '女', '2019-08-21 00:00:00', 'upload/7FF92A9DF54D102B4805959A5926789C.jpg', '13508324212', 'mingbo@126.com', '四川成都总府路10号', '2019-08-21 17:46:40');
