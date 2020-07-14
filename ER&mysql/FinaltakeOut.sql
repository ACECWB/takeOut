/*
Navicat MySQL Data Transfer

Source Server         : 222
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2020-07-12 23:39:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_Id` varchar(20) NOT NULL DEFAULT '',
  `admin_name` varchar(20) NOT NULL,
  `admin_pwd` varchar(20) NOT NULL,
  PRIMARY KEY (`admin_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for business
-- ----------------------------
DROP TABLE IF EXISTS `business`;
CREATE TABLE `business` (
  `business_Id` varchar(20) NOT NULL DEFAULT '',
  `business_name` varchar(20) NOT NULL,
  `stars` float NOT NULL,
  `avg_consume` float NOT NULL,
  `sales_volume` int(11) NOT NULL,
  `createtime` datetime NOT NULL,
  `removetime` datetime DEFAULT NULL,
  `pwd` varchar(30) NOT NULL,
  `location` varchar(30) NOT NULL,
  PRIMARY KEY (`business_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `user_Id` varchar(30) NOT NULL,
  `com_Id` varchar(30) NOT NULL,
  `business_Id` varchar(30) NOT NULL,
  `counts` int(11) NOT NULL,
  `price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for collectorders
-- ----------------------------
DROP TABLE IF EXISTS `collectorders`;
CREATE TABLE `collectorders` (
  `user_Id` varchar(20) NOT NULL,
  `business_Id` varchar(20) NOT NULL,
  `alreadycounts` varchar(20) NOT NULL,
  PRIMARY KEY (`user_Id`,`business_Id`),
  KEY `FK_collectOrders2` (`business_Id`),
  CONSTRAINT `FK_collectOrders2` FOREIGN KEY (`business_Id`) REFERENCES `business` (`business_Id`),
  CONSTRAINT `FK_collectOrders` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for com2bus
-- ----------------------------
DROP TABLE IF EXISTS `com2bus`;
CREATE TABLE `com2bus` (
  `com_Id` varchar(20) NOT NULL,
  `business_Id` varchar(20) NOT NULL,
  `counts` int(11) NOT NULL,
  `each_price` float NOT NULL,
  `vipprice` float NOT NULL,
  KEY `com_Id` (`com_Id`),
  KEY `business_Id` (`business_Id`),
  CONSTRAINT `com2bus_ibfk_1` FOREIGN KEY (`com_Id`) REFERENCES `commodity` (`com_Id`),
  CONSTRAINT `com2bus_ibfk_2` FOREIGN KEY (`business_Id`) REFERENCES `business` (`business_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for commodity
-- ----------------------------
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity` (
  `com_Id` varchar(20) NOT NULL DEFAULT '',
  `category_Id` varchar(20) NOT NULL,
  `com_name` varchar(20) NOT NULL,
  `createtime` datetime NOT NULL,
  `removetime` datetime DEFAULT NULL,
  PRIMARY KEY (`com_Id`),
  KEY `FK_belong_to` (`category_Id`),
  CONSTRAINT `FK_belong_to` FOREIGN KEY (`category_Id`) REFERENCES `commoditycategory` (`category_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for commoditycategory
-- ----------------------------
DROP TABLE IF EXISTS `commoditycategory`;
CREATE TABLE `commoditycategory` (
  `category_Id` varchar(20) NOT NULL DEFAULT '',
  `category_name` varchar(20) NOT NULL,
  `createtime` datetime NOT NULL,
  `removetime` datetime DEFAULT NULL,
  PRIMARY KEY (`category_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon` (
  `coupon_Id` varchar(20) NOT NULL DEFAULT '',
  `business_Id` varchar(20) DEFAULT NULL,
  `discount_money` float NOT NULL,
  `need_orders` int(11) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `effect_days` int(11) NOT NULL,
  `removetime` datetime DEFAULT NULL,
  PRIMARY KEY (`coupon_Id`),
  KEY `FK_own1` (`business_Id`),
  CONSTRAINT `FK_own1` FOREIGN KEY (`business_Id`) REFERENCES `business` (`business_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for deliver
-- ----------------------------
DROP TABLE IF EXISTS `deliver`;
CREATE TABLE `deliver` (
  `deliver_Id` varchar(20) NOT NULL DEFAULT '',
  `deliver_name` varchar(20) NOT NULL,
  `employ_time` datetime NOT NULL,
  `identity` varchar(20) NOT NULL,
  `quit_time` datetime DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `pwd` varchar(30) NOT NULL,
  PRIMARY KEY (`deliver_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for deliver_income
-- ----------------------------
DROP TABLE IF EXISTS `deliver_income`;
CREATE TABLE `deliver_income` (
  `deliver_Id` varchar(20) DEFAULT NULL,
  `order_Id` varchar(20) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `review` varchar(30) DEFAULT NULL,
  `each_bonus` float DEFAULT NULL,
  KEY `FK_Reference_24` (`order_Id`),
  KEY `FK_Relationship_13` (`deliver_Id`),
  CONSTRAINT `FK_Reference_24` FOREIGN KEY (`order_Id`) REFERENCES `orders` (`order_Id`),
  CONSTRAINT `FK_Relationship_13` FOREIGN KEY (`deliver_Id`) REFERENCES `deliver` (`deliver_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for fullreduction
-- ----------------------------
DROP TABLE IF EXISTS `fullreduction`;
CREATE TABLE `fullreduction` (
  `reduct_Id` varchar(20) NOT NULL DEFAULT '',
  `require_amount` float NOT NULL,
  `discount_amount` float NOT NULL,
  `with_coupon` varchar(3) NOT NULL,
  `removetime` datetime DEFAULT NULL,
  `business_Id` varchar(30) NOT NULL,
  PRIMARY KEY (`reduct_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for location
-- ----------------------------
DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `loca_Id` int(11) NOT NULL AUTO_INCREMENT,
  `user_Id` varchar(20) DEFAULT NULL,
  `loca` varchar(20) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `conn_user` varchar(10) NOT NULL,
  PRIMARY KEY (`loca_Id`),
  KEY `FK_live` (`user_Id`),
  CONSTRAINT `FK_live` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for orderinfo
-- ----------------------------
DROP TABLE IF EXISTS `orderinfo`;
CREATE TABLE `orderinfo` (
  `order_Id` varchar(20) NOT NULL,
  `com_Id` varchar(20) NOT NULL,
  `count` int(11) NOT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`order_Id`,`com_Id`),
  KEY `FK_orderInfo2` (`com_Id`),
  CONSTRAINT `FK_orderInfo2` FOREIGN KEY (`com_Id`) REFERENCES `commodity` (`com_Id`),
  CONSTRAINT `FK_orderInfo` FOREIGN KEY (`order_Id`) REFERENCES `orders` (`order_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_Id` varchar(20) NOT NULL,
  `user_Id` varchar(20) DEFAULT NULL,
  `loca_Id` int(11) NOT NULL,
  `coupon_Id` varchar(20) DEFAULT NULL,
  `deliver_Id` varchar(20) DEFAULT NULL,
  `business_Id` varchar(20) DEFAULT NULL,
  `origin_amount` float NOT NULL,
  `final_amount` float NOT NULL,
  `order_time` datetime NOT NULL,
  `req_time` datetime NOT NULL,
  `status` varchar(10) NOT NULL,
  `receive_time` datetime DEFAULT NULL,
  `couponorder` int(11) NOT NULL,
  `isreviewed` int(11) NOT NULL,
  PRIMARY KEY (`order_Id`),
  KEY `FK_Relationship_10` (`deliver_Id`),
  KEY `FK_businessId` (`business_Id`),
  KEY `FK_couponId` (`coupon_Id`),
  KEY `FK_locaId` (`loca_Id`),
  KEY `FK_userId` (`user_Id`),
  CONSTRAINT `FK_businessId` FOREIGN KEY (`business_Id`) REFERENCES `business` (`business_Id`),
  CONSTRAINT `FK_locaId` FOREIGN KEY (`loca_Id`) REFERENCES `location` (`loca_Id`),
  CONSTRAINT `FK_Relationship_10` FOREIGN KEY (`deliver_Id`) REFERENCES `deliver` (`deliver_Id`),
  CONSTRAINT `FK_userId` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ownedcoupons
-- ----------------------------
DROP TABLE IF EXISTS `ownedcoupons`;
CREATE TABLE `ownedcoupons` (
  `user_Id` varchar(20) NOT NULL,
  `business_Id` varchar(20) NOT NULL,
  `coupon_Id` varchar(20) NOT NULL,
  `ineffect_time` datetime NOT NULL,
  `ownorder` int(11) NOT NULL AUTO_INCREMENT,
  `removetime` datetime DEFAULT NULL,
  PRIMARY KEY (`ownorder`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for review
-- ----------------------------
DROP TABLE IF EXISTS `review`;
CREATE TABLE `review` (
  `content` varchar(30) DEFAULT NULL,
  `review_date` datetime NOT NULL,
  `stars` int(11) NOT NULL,
  `pictures` longblob,
  `order_Id` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_Id` varchar(20) NOT NULL DEFAULT '',
  `user_name` varchar(20) NOT NULL,
  `sex` varchar(3) DEFAULT NULL,
  `pwd` varchar(20) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(20) DEFAULT NULL,
  `city` varchar(10) NOT NULL,
  `isvip` tinyint(1) NOT NULL,
  `vip_start_time` datetime DEFAULT NULL,
  `vip_end_time` datetime DEFAULT NULL,
  `createtime` datetime NOT NULL,
  `removetime` datetime DEFAULT NULL,
  PRIMARY KEY (`user_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- View structure for busreview
-- ----------------------------
DROP VIEW IF EXISTS `busreview`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`  VIEW `busreview` AS (
select o.business_Id, o.user_Id, content, stars, review_date, o.order_Id, o.deliver_Id, d.deliver_name,
di.review  from review r, orders o, deliver d, deliver_income di
where  o.order_Id = r.order_Id  and o.deliver_Id = d.deliver_Id and d.deliver_Id = di.deliver_Id
and di.order_Id = o.order_Id and o.deliver_Id = di.deliver_Id
) ;

-- ----------------------------
-- View structure for deliverget
-- ----------------------------
DROP VIEW IF EXISTS `deliverget`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`  VIEW `deliverget` AS (
select o.order_Id, b.business_name, b.location, l.conn_user, l.loca, l.phone_number,
o.order_time, o.req_time,o.status from orders o, business b, location l
where (o.status = '等待配送' or o.status = '正在配送中') and o.business_Id = b.business_Id and l.loca_Id = o.loca_Id
) ;

-- ----------------------------
-- View structure for recomgoods
-- ----------------------------
DROP VIEW IF EXISTS `recomgoods`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`  VIEW `recomgoods` AS (
select o.business_Id, b.business_name, oi.com_Id, c.com_name, sum(count) sales, counts, each_price, vipprice  from 
orderinfo oi, orders o, business b, commodity c, com2bus cb
where o.status = '已送达' and oi.order_Id = o.order_Id and o.business_Id = b.business_Id and c.com_Id = oi.com_Id
and cb.com_Id = oi.com_Id and cb.business_Id = b.business_Id
GROUP BY o.business_Id,com_Id
order by sales desc
) ;

-- ----------------------------
-- View structure for searchcommodity
-- ----------------------------
DROP VIEW IF EXISTS `searchcommodity`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`  VIEW `searchcommodity` AS (
select  cb.com_Id, c.com_name, cc.category_Id, cc.category_name,
cb.business_Id, b.business_name, cb.counts, cb.each_price, cb.vipprice
from com2bus cb, business b, commoditycategory cc, commodity c
where cb.com_Id = c.com_Id AND
c.category_Id = cc.category_Id and cb.business_Id = b.business_Id
) ;

-- ----------------------------
-- View structure for userownedcollects
-- ----------------------------
DROP VIEW IF EXISTS `userownedcollects`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`  VIEW `userownedcollects` AS (
select user_Id, c.business_Id, b.business_name, c.coupon_Id, c.end_time, c.need_orders, co.alreadycounts,
c.discount_money, effect_days from coupon c, collectorders co, business b
where  co.business_Id = b.business_Id and co.business_Id = c.business_Id

) ;
