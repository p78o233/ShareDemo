/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : shares

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-08-28 17:33:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `buy_sell_record`
-- ----------------------------
DROP TABLE IF EXISTS `buy_sell_record`;
CREATE TABLE `buy_sell_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '所有投资收益记录表',
  `buyPrice` float DEFAULT NULL COMMENT '买入单价',
  `buyTime` date DEFAULT NULL,
  `sellPrice` float DEFAULT NULL COMMENT '卖出单价',
  `sellTime` date DEFAULT NULL,
  `profitOrLoss` float DEFAULT NULL COMMENT '盈亏，盈利正数亏损负数',
  `stockNum` varchar(10) DEFAULT NULL COMMENT '编号',
  `stockName` varchar(10) DEFAULT NULL COMMENT '名称',
  `category` int(11) DEFAULT NULL COMMENT '1 股票 2基金 3黄金 4期货 5彩票',
  `stockId` int(11) DEFAULT NULL COMMENT '彩票这个为0',
  `isFinish` bit(11) DEFAULT b'0' COMMENT '是否已经完成了',
  `buyNum` int(11) DEFAULT NULL COMMENT '买入数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of buy_sell_record
-- ----------------------------

-- ----------------------------
-- Table structure for `stock`
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `stockNum` varchar(10) DEFAULT NULL COMMENT '编号',
  `stockName` varchar(10) DEFAULT NULL COMMENT '名称',
  `createTime` date DEFAULT NULL COMMENT '创建时间',
  `category` int(11) DEFAULT NULL COMMENT '1 股票 2基金 3黄金 4期货',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES ('1', 'sh600022', '山东钢铁', '2019-08-27', '1');
INSERT INTO `stock` VALUES ('2', 'sh600698', '*ST天雁', '2019-08-27', '1');
INSERT INTO `stock` VALUES ('3', 'sh600879', '航天电子', '2019-08-27', '1');
INSERT INTO `stock` VALUES ('4', 'sh600497', '驰宏锌锗', '2019-08-27', '1');
INSERT INTO `stock` VALUES ('5', 'sh600747', '*ST大控', '2019-08-27', '1');
INSERT INTO `stock` VALUES ('6', 'sh600614', '*ST鹏起', '2019-08-27', '1');
INSERT INTO `stock` VALUES ('7', 'sh603077', '和邦生物', '2019-08-27', '1');
INSERT INTO `stock` VALUES ('8', 'sh600740', '山西焦化', '2019-08-27', '1');
INSERT INTO `stock` VALUES ('9', 'sh601558', 'ST锐电', '2019-08-27', '1');
INSERT INTO `stock` VALUES ('10', 'sh600240', '*ST华业', '2019-08-27', '1');

-- ----------------------------
-- Table structure for `stock_record`
-- ----------------------------
DROP TABLE IF EXISTS `stock_record`;
CREATE TABLE `stock_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '股票基金黄金期货每天收报价格记录表',
  `beginPrice` float DEFAULT NULL,
  `endPrice` float DEFAULT NULL,
  `highPrice` float DEFAULT NULL,
  `lowPrice` float DEFAULT NULL,
  `stockId` int(11) DEFAULT NULL,
  `stockNum` varchar(10) DEFAULT NULL COMMENT '编号',
  `stockName` varchar(10) DEFAULT NULL COMMENT '名称',
  `category` int(11) DEFAULT NULL COMMENT '1 股票 2基金 3黄金 4期货',
  `recordTime` datetime DEFAULT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of stock_record
-- ----------------------------
INSERT INTO `stock_record` VALUES ('7', '1.49', '1.5', '1.51', '1.48', '1', 'sh600022', '山东钢铁', '1', '2019-08-27 00:00:00');
INSERT INTO `stock_record` VALUES ('8', '2.74', '2.75', '2.79', '2.74', '2', 'sh600698', '*ST天雁', '1', '2019-08-27 00:00:00');
INSERT INTO `stock_record` VALUES ('9', '6.23', '6.3', '6.33', '6.12', '3', 'sh600879', '航天电子', '1', '2019-08-27 00:00:00');
INSERT INTO `stock_record` VALUES ('10', '4.52', '4.57', '4.62', '4.52', '4', 'sh600497', '驰宏锌锗', '1', '2019-08-27 00:00:00');
INSERT INTO `stock_record` VALUES ('11', '0.91', '0.92', '0.94', '0.9', '5', 'sh600747', '*ST大控', '1', '2019-08-27 00:00:00');
INSERT INTO `stock_record` VALUES ('12', '1.16', '1.2', '1.22', '1.16', '6', 'sh600614', '*ST鹏起', '1', '2019-08-27 00:00:00');
INSERT INTO `stock_record` VALUES ('13', '1.59', '1.62', '1.62', '1.58', '7', 'sh603077', '和邦生物', '1', '2019-08-27 00:00:00');
INSERT INTO `stock_record` VALUES ('14', '7.93', '7.99', '8.03', '7.89', '8', 'sh600740', '山西焦化', '1', '2019-08-27 00:00:00');
INSERT INTO `stock_record` VALUES ('15', '1.15', '1.18', '1.2', '1.14', '9', 'sh601558', 'ST锐电', '1', '2019-08-27 00:00:00');
INSERT INTO `stock_record` VALUES ('16', '0.95', '0.95', '0.96', '0.94', '10', 'sh600240', '*ST华业', '1', '2019-08-27 00:00:00');
INSERT INTO `stock_record` VALUES ('17', '1.5', '1.52', '1.54', '1.49', '1', 'sh600022', '山东钢铁', '1', '2019-08-28 00:00:00');
INSERT INTO `stock_record` VALUES ('18', '2.76', '2.78', '2.82', '2.74', '2', 'sh600698', '*ST天雁', '1', '2019-08-28 00:00:00');
INSERT INTO `stock_record` VALUES ('19', '6.26', '6.28', '6.4', '6.23', '3', 'sh600879', '航天电子', '1', '2019-08-28 00:00:00');
INSERT INTO `stock_record` VALUES ('20', '4.55', '4.55', '4.59', '4.55', '4', 'sh600497', '驰宏锌锗', '1', '2019-08-28 00:00:00');
INSERT INTO `stock_record` VALUES ('21', '0.9', '0.93', '0.96', '0.88', '5', 'sh600747', '*ST大控', '1', '2019-08-28 00:00:00');
INSERT INTO `stock_record` VALUES ('22', '1.18', '1.15', '1.19', '1.15', '6', 'sh600614', '*ST鹏起', '1', '2019-08-28 00:00:00');
INSERT INTO `stock_record` VALUES ('23', '1.62', '1.65', '1.68', '1.6', '7', 'sh603077', '和邦生物', '1', '2019-08-28 00:00:00');
INSERT INTO `stock_record` VALUES ('24', '7.97', '8.05', '8.09', '7.88', '8', 'sh600740', '山西焦化', '1', '2019-08-28 00:00:00');
INSERT INTO `stock_record` VALUES ('25', '1.17', '1.17', '1.18', '1.16', '9', 'sh601558', 'ST锐电', '1', '2019-08-28 00:00:00');
INSERT INTO `stock_record` VALUES ('26', '0.94', '0.94', '0.95', '0.93', '10', 'sh600240', '*ST华业', '1', '2019-08-28 00:00:00');
