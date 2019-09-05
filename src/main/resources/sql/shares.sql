/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : shares

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-09-05 16:36:46
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
  `sendTimes` tinyint(4) DEFAULT '0' COMMENT '发送次数等于1次的就不发了',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of buy_sell_record
-- ----------------------------
INSERT INTO `buy_sell_record` VALUES ('1', '2.282', '2019-08-28', null, '2019-08-30', null, 'sh600022', '山东钢铁', '1', '1', '', '500', '1');
INSERT INTO `buy_sell_record` VALUES ('2', '7.87', '2019-08-28', null, '2019-08-30', null, 'sh600497', '驰宏锌锗', '1', '4', '', '100', '1');
INSERT INTO `buy_sell_record` VALUES ('3', '1.168', '2019-08-28', '1.04', '2019-08-30', '-76.8', 'sh600614', '*ST鹏起', '1', '6', '\0', '600', '1');
INSERT INTO `buy_sell_record` VALUES ('4', '0.945', '2019-08-28', null, '2019-08-30', null, 'sh600747', '*ST大控', '1', '5', '', '200', '1');
INSERT INTO `buy_sell_record` VALUES ('5', '9.31', '2019-08-28', null, '2019-08-30', null, 'sh600879', '航天电子', '1', '3', '', '2000', '1');

-- ----------------------------
-- Table structure for `low_record`
-- ----------------------------
DROP TABLE IF EXISTS `low_record`;
CREATE TABLE `low_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '建议买入表',
  `stockId` int(11) DEFAULT NULL,
  `stockNum` varchar(10) DEFAULT NULL COMMENT '编号',
  `stockName` varchar(10) DEFAULT NULL COMMENT '名称',
  `category` int(11) DEFAULT NULL COMMENT '1 股票 2基金 3黄金 4期货',
  `recordDay` int(11) DEFAULT NULL COMMENT '记录天数',
  `minPrice` float DEFAULT NULL COMMENT '最低价',
  `recordPrice` float DEFAULT NULL COMMENT '记录时的价格',
  `recordTime` datetime DEFAULT NULL,
  `isSend` bit(1) DEFAULT b'0' COMMENT '是否已经发送',
  `trend` tinyint(1) DEFAULT NULL COMMENT '在最低价监视日子内趋势 -1跌的日子比较多  0 震荡  1上涨的日子比较多',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of low_record
-- ----------------------------
INSERT INTO `low_record` VALUES ('1', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.51', '2019-08-29 14:19:08', '', '0');
INSERT INTO `low_record` VALUES ('2', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.1', '2019-08-29 14:19:08', '', '0');
INSERT INTO `low_record` VALUES ('3', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 14:19:59', '', '0');
INSERT INTO `low_record` VALUES ('4', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 14:19:59', '', '0');
INSERT INTO `low_record` VALUES ('5', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.51', '2019-08-29 14:21:02', '', '0');
INSERT INTO `low_record` VALUES ('6', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 14:21:02', '', '0');
INSERT INTO `low_record` VALUES ('7', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.51', '2019-08-29 14:22:50', '', '0');
INSERT INTO `low_record` VALUES ('8', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 14:22:50', '', '0');
INSERT INTO `low_record` VALUES ('9', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.51', '2019-08-29 14:23:05', '', '0');
INSERT INTO `low_record` VALUES ('10', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 14:23:05', '', '0');
INSERT INTO `low_record` VALUES ('11', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.51', '2019-08-29 14:24:30', '', '0');
INSERT INTO `low_record` VALUES ('12', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 14:24:30', '', '0');
INSERT INTO `low_record` VALUES ('13', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.51', '2019-08-29 14:25:28', '', '0');
INSERT INTO `low_record` VALUES ('14', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 14:25:28', '', '0');
INSERT INTO `low_record` VALUES ('15', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 14:25:46', '', '0');
INSERT INTO `low_record` VALUES ('16', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 14:25:46', '', '0');
INSERT INTO `low_record` VALUES ('17', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 14:58:46', '', '0');
INSERT INTO `low_record` VALUES ('18', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 14:58:46', '', '0');
INSERT INTO `low_record` VALUES ('19', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 14:59:03', '', '0');
INSERT INTO `low_record` VALUES ('20', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 14:59:04', '', '0');
INSERT INTO `low_record` VALUES ('21', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 15:00:28', '', '0');
INSERT INTO `low_record` VALUES ('22', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 15:00:28', '', '0');
INSERT INTO `low_record` VALUES ('23', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 15:00:58', '', '0');
INSERT INTO `low_record` VALUES ('24', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 15:00:58', '', '0');
INSERT INTO `low_record` VALUES ('25', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 15:01:58', '', '0');
INSERT INTO `low_record` VALUES ('26', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 15:01:58', '', '0');
INSERT INTO `low_record` VALUES ('27', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 15:02:25', '', '0');
INSERT INTO `low_record` VALUES ('28', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 15:02:25', '', '0');
INSERT INTO `low_record` VALUES ('29', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 15:06:07', '', '0');
INSERT INTO `low_record` VALUES ('30', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 15:06:07', '', '0');
INSERT INTO `low_record` VALUES ('31', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 15:08:56', '', '0');
INSERT INTO `low_record` VALUES ('32', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 15:08:57', '', '0');
INSERT INTO `low_record` VALUES ('33', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 15:09:07', '', '0');
INSERT INTO `low_record` VALUES ('34', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 15:09:07', '', '0');
INSERT INTO `low_record` VALUES ('35', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 15:11:30', '', '0');
INSERT INTO `low_record` VALUES ('36', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 15:11:30', '', '0');
INSERT INTO `low_record` VALUES ('37', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 15:13:22', '', '0');
INSERT INTO `low_record` VALUES ('38', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 15:13:23', '', '0');
INSERT INTO `low_record` VALUES ('39', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 15:14:24', '', '0');
INSERT INTO `low_record` VALUES ('40', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 15:14:25', '', '0');
INSERT INTO `low_record` VALUES ('41', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 15:20:14', '', '0');
INSERT INTO `low_record` VALUES ('42', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 15:20:15', '', '0');
INSERT INTO `low_record` VALUES ('43', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 15:24:33', '', '0');
INSERT INTO `low_record` VALUES ('44', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 15:24:33', '', '0');
INSERT INTO `low_record` VALUES ('45', '4', 'sh600497', '驰宏锌锗', '1', '1', '4.55', '4.52', '2019-08-29 15:24:58', '', '0');
INSERT INTO `low_record` VALUES ('46', '6', 'sh600614', '*ST鹏起', '1', '1', '1.15', '1.09', '2019-08-29 15:24:58', '', '0');

-- ----------------------------
-- Table structure for `stock`
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
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
  `recordTime` date DEFAULT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of stock_record
-- ----------------------------
INSERT INTO `stock_record` VALUES ('7', '1.49', '1.5', '1.51', '1.48', '1', 'sh600022', '山东钢铁', '1', '2019-08-27');
INSERT INTO `stock_record` VALUES ('8', '2.74', '2.75', '2.79', '2.74', '2', 'sh600698', '*ST天雁', '1', '2019-08-27');
INSERT INTO `stock_record` VALUES ('9', '6.23', '6.3', '6.33', '6.12', '3', 'sh600879', '航天电子', '1', '2019-08-27');
INSERT INTO `stock_record` VALUES ('10', '4.52', '4.57', '4.62', '4.52', '4', 'sh600497', '驰宏锌锗', '1', '2019-08-27');
INSERT INTO `stock_record` VALUES ('11', '0.91', '0.92', '0.94', '0.9', '5', 'sh600747', '*ST大控', '1', '2019-08-27');
INSERT INTO `stock_record` VALUES ('12', '1.16', '1.2', '1.22', '1.16', '6', 'sh600614', '*ST鹏起', '1', '2019-08-27');
INSERT INTO `stock_record` VALUES ('13', '1.59', '1.62', '1.62', '1.58', '7', 'sh603077', '和邦生物', '1', '2019-08-27');
INSERT INTO `stock_record` VALUES ('14', '7.93', '7.99', '8.03', '7.89', '8', 'sh600740', '山西焦化', '1', '2019-08-27');
INSERT INTO `stock_record` VALUES ('15', '1.15', '1.18', '1.2', '1.14', '9', 'sh601558', 'ST锐电', '1', '2019-08-27');
INSERT INTO `stock_record` VALUES ('16', '0.95', '0.95', '0.96', '0.94', '10', 'sh600240', '*ST华业', '1', '2019-08-27');
INSERT INTO `stock_record` VALUES ('17', '1.5', '1.52', '1.54', '1.49', '1', 'sh600022', '山东钢铁', '1', '2019-08-28');
INSERT INTO `stock_record` VALUES ('18', '2.76', '2.78', '2.82', '2.74', '2', 'sh600698', '*ST天雁', '1', '2019-08-28');
INSERT INTO `stock_record` VALUES ('19', '6.26', '6.28', '6.4', '6.23', '3', 'sh600879', '航天电子', '1', '2019-08-28');
INSERT INTO `stock_record` VALUES ('20', '4.55', '4.55', '4.59', '4.55', '4', 'sh600497', '驰宏锌锗', '1', '2019-08-28');
INSERT INTO `stock_record` VALUES ('21', '0.9', '0.93', '0.96', '0.88', '5', 'sh600747', '*ST大控', '1', '2019-08-28');
INSERT INTO `stock_record` VALUES ('22', '1.18', '1.15', '1.19', '1.15', '6', 'sh600614', '*ST鹏起', '1', '2019-08-28');
INSERT INTO `stock_record` VALUES ('23', '1.62', '1.65', '1.68', '1.6', '7', 'sh603077', '和邦生物', '1', '2019-08-28');
INSERT INTO `stock_record` VALUES ('24', '7.97', '8.05', '8.09', '7.88', '8', 'sh600740', '山西焦化', '1', '2019-08-28');
INSERT INTO `stock_record` VALUES ('25', '1.17', '1.17', '1.18', '1.16', '9', 'sh601558', 'ST锐电', '1', '2019-08-28');
INSERT INTO `stock_record` VALUES ('26', '0.94', '0.94', '0.95', '0.93', '10', 'sh600240', '*ST华业', '1', '2019-08-28');
INSERT INTO `stock_record` VALUES ('27', '1.52', '1.5', '1.52', '1.49', '1', 'sh600022', '山东钢铁', '1', '2019-08-29');
INSERT INTO `stock_record` VALUES ('28', '2.78', '2.86', '2.92', '2.78', '2', 'sh600698', '*ST天雁', '1', '2019-08-29');
INSERT INTO `stock_record` VALUES ('29', '6.35', '6.38', '6.44', '6.32', '3', 'sh600879', '航天电子', '1', '2019-08-29');
INSERT INTO `stock_record` VALUES ('30', '4.55', '4.52', '4.56', '4.5', '4', 'sh600497', '驰宏锌锗', '1', '2019-08-29');
INSERT INTO `stock_record` VALUES ('31', '0.91', '0.92', '0.94', '0.9', '5', 'sh600747', '*ST大控', '1', '2019-08-29');
INSERT INTO `stock_record` VALUES ('32', '1.15', '1.09', '1.17', '1.09', '6', 'sh600614', '*ST鹏起', '1', '2019-08-29');
INSERT INTO `stock_record` VALUES ('33', '1.65', '1.61', '1.65', '1.61', '7', 'sh603077', '和邦生物', '1', '2019-08-29');
INSERT INTO `stock_record` VALUES ('34', '8.05', '7.92', '8.07', '7.91', '8', 'sh600740', '山西焦化', '1', '2019-08-29');
INSERT INTO `stock_record` VALUES ('35', '1.17', '1.2', '1.21', '1.17', '9', 'sh601558', 'ST锐电', '1', '2019-08-29');
INSERT INTO `stock_record` VALUES ('36', '0.94', '0.93', '0.95', '0.92', '10', 'sh600240', '*ST华业', '1', '2019-08-29');
INSERT INTO `stock_record` VALUES ('37', '1.51', '1.49', '1.51', '1.49', '1', 'sh600022', '山东钢铁', '1', '2019-08-30');
INSERT INTO `stock_record` VALUES ('38', '2.87', '2.83', '2.87', '2.8', '2', 'sh600698', '*ST天雁', '1', '2019-08-30');
INSERT INTO `stock_record` VALUES ('39', '6.4', '6.59', '6.6', '6.35', '3', 'sh600879', '航天电子', '1', '2019-08-30');
INSERT INTO `stock_record` VALUES ('40', '4.55', '4.49', '4.55', '4.45', '4', 'sh600497', '驰宏锌锗', '1', '2019-08-30');
INSERT INTO `stock_record` VALUES ('41', '0.91', '0.93', '0.93', '0.89', '5', 'sh600747', '*ST大控', '1', '2019-08-30');
INSERT INTO `stock_record` VALUES ('42', '1.07', '1.04', '1.08', '1.04', '6', 'sh600614', '*ST鹏起', '1', '2019-08-30');
INSERT INTO `stock_record` VALUES ('43', '1.63', '1.62', '1.66', '1.6', '7', 'sh603077', '和邦生物', '1', '2019-08-30');
INSERT INTO `stock_record` VALUES ('44', '7.97', '7.74', '8', '7.74', '8', 'sh600740', '山西焦化', '1', '2019-08-30');
INSERT INTO `stock_record` VALUES ('45', '1.2', '1.16', '1.21', '1.15', '9', 'sh601558', 'ST锐电', '1', '2019-08-30');
INSERT INTO `stock_record` VALUES ('46', '0.93', '0.94', '0.95', '0.93', '10', 'sh600240', '*ST华业', '1', '2019-08-30');
INSERT INTO `stock_record` VALUES ('47', '1.49', '1.51', '1.52', '1.49', '1', 'sh600022', '山东钢铁', '1', '2019-09-02');
INSERT INTO `stock_record` VALUES ('48', '2.82', '2.91', '2.93', '2.81', '2', 'sh600698', '*ST天雁', '1', '2019-09-02');
INSERT INTO `stock_record` VALUES ('49', '6.61', '6.78', '6.87', '6.57', '3', 'sh600879', '航天电子', '1', '2019-09-02');
INSERT INTO `stock_record` VALUES ('50', '4.49', '4.59', '4.6', '4.49', '4', 'sh600497', '驰宏锌锗', '1', '2019-09-02');
INSERT INTO `stock_record` VALUES ('51', '0.91', '0.92', '0.93', '0.91', '5', 'sh600747', '*ST大控', '1', '2019-09-02');
INSERT INTO `stock_record` VALUES ('52', '1.04', '1.09', '1.09', '1.03', '6', 'sh600614', '*ST鹏起', '1', '2019-09-02');
INSERT INTO `stock_record` VALUES ('53', '1.61', '1.63', '1.63', '1.59', '7', 'sh603077', '和邦生物', '1', '2019-09-02');
INSERT INTO `stock_record` VALUES ('54', '7.75', '7.98', '8.02', '7.75', '8', 'sh600740', '山西焦化', '1', '2019-09-02');
INSERT INTO `stock_record` VALUES ('55', '1.16', '1.18', '1.18', '1.15', '9', 'sh601558', 'ST锐电', '1', '2019-09-02');
INSERT INTO `stock_record` VALUES ('56', '0.91', '0.95', '0.95', '0.91', '10', 'sh600240', '*ST华业', '1', '2019-09-02');
INSERT INTO `stock_record` VALUES ('57', '1.52', '1.51', '1.52', '1.5', '1', 'sh600022', '山东钢铁', '1', '2019-09-03');
INSERT INTO `stock_record` VALUES ('58', '2.91', '2.89', '2.92', '2.86', '2', 'sh600698', '*ST天雁', '1', '2019-09-03');
INSERT INTO `stock_record` VALUES ('59', '6.74', '6.75', '6.79', '6.65', '3', 'sh600879', '航天电子', '1', '2019-09-03');
INSERT INTO `stock_record` VALUES ('60', '4.59', '4.59', '4.62', '4.56', '4', 'sh600497', '驰宏锌锗', '1', '2019-09-03');
INSERT INTO `stock_record` VALUES ('61', '0.92', '0.93', '0.94', '0.91', '5', 'sh600747', '*ST大控', '1', '2019-09-03');
INSERT INTO `stock_record` VALUES ('62', '1.14', '1.14', '1.14', '1.14', '6', 'sh600614', '*ST鹏起', '1', '2019-09-03');
INSERT INTO `stock_record` VALUES ('63', '1.62', '1.62', '1.63', '1.6', '7', 'sh603077', '和邦生物', '1', '2019-09-03');
INSERT INTO `stock_record` VALUES ('64', '7.98', '7.92', '7.98', '7.88', '8', 'sh600740', '山西焦化', '1', '2019-09-03');
INSERT INTO `stock_record` VALUES ('65', '1.17', '1.16', '1.18', '1.16', '9', 'sh601558', 'ST锐电', '1', '2019-09-03');
INSERT INTO `stock_record` VALUES ('66', '0.95', '0.96', '0.97', '0.94', '10', 'sh600240', '*ST华业', '1', '2019-09-03');
INSERT INTO `stock_record` VALUES ('67', '1.51', '1.53', '1.53', '1.5', '1', 'sh600022', '山东钢铁', '1', '2019-09-04');
INSERT INTO `stock_record` VALUES ('68', '2.88', '2.88', '2.91', '2.84', '2', 'sh600698', '*ST天雁', '1', '2019-09-04');
INSERT INTO `stock_record` VALUES ('69', '6.7', '6.74', '6.77', '6.65', '3', 'sh600879', '航天电子', '1', '2019-09-04');
INSERT INTO `stock_record` VALUES ('70', '4.61', '4.76', '4.87', '4.61', '4', 'sh600497', '驰宏锌锗', '1', '2019-09-04');
INSERT INTO `stock_record` VALUES ('71', '0.9', '0.92', '0.93', '0.9', '5', 'sh600747', '*ST大控', '1', '2019-09-04');
INSERT INTO `stock_record` VALUES ('72', '1.2', '1.2', '1.2', '1.2', '6', 'sh600614', '*ST鹏起', '1', '2019-09-04');
INSERT INTO `stock_record` VALUES ('73', '1.61', '1.61', '1.62', '1.6', '7', 'sh603077', '和邦生物', '1', '2019-09-04');
INSERT INTO `stock_record` VALUES ('74', '7.93', '8.08', '8.08', '7.89', '8', 'sh600740', '山西焦化', '1', '2019-09-04');
INSERT INTO `stock_record` VALUES ('75', '1.16', '1.17', '1.18', '1.16', '9', 'sh601558', 'ST锐电', '1', '2019-09-04');
INSERT INTO `stock_record` VALUES ('76', '0.95', '0.99', '0.99', '0.94', '10', 'sh600240', '*ST华业', '1', '2019-09-04');
INSERT INTO `stock_record` VALUES ('77', '1.53', '1.54', '1.56', '1.53', '1', 'sh600022', '山东钢铁', '1', '2019-09-05');
INSERT INTO `stock_record` VALUES ('78', '2.88', '2.87', '2.91', '2.86', '2', 'sh600698', '*ST天雁', '1', '2019-09-05');
INSERT INTO `stock_record` VALUES ('79', '6.78', '6.68', '6.82', '6.68', '3', 'sh600879', '航天电子', '1', '2019-09-05');
INSERT INTO `stock_record` VALUES ('80', '4.85', '4.75', '4.87', '4.74', '4', 'sh600497', '驰宏锌锗', '1', '2019-09-05');
INSERT INTO `stock_record` VALUES ('81', '0.94', '0.92', '0.94', '0.91', '5', 'sh600747', '*ST大控', '1', '2019-09-05');
INSERT INTO `stock_record` VALUES ('82', '1.26', '1.26', '1.26', '1.26', '6', 'sh600614', '*ST鹏起', '1', '2019-09-05');
INSERT INTO `stock_record` VALUES ('83', '1.62', '1.63', '1.66', '1.61', '7', 'sh603077', '和邦生物', '1', '2019-09-05');
INSERT INTO `stock_record` VALUES ('84', '8.09', '8.04', '8.19', '8.01', '8', 'sh600740', '山西焦化', '1', '2019-09-05');
INSERT INTO `stock_record` VALUES ('85', '1.18', '1.18', '1.19', '1.17', '9', 'sh601558', 'ST锐电', '1', '2019-09-05');
INSERT INTO `stock_record` VALUES ('86', '0.99', '0.99', '0.99', '0.97', '10', 'sh600240', '*ST华业', '1', '2019-09-05');
