/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : atest

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-08-27 11:47:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for asserts
-- ----------------------------
DROP TABLE IF EXISTS `asserts`;
CREATE TABLE `asserts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL DEFAULT '0',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `requestcontent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `responsecontent` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `assertresult` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for environment
-- ----------------------------
DROP TABLE IF EXISTS `environment`;
CREATE TABLE `environment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for interface
-- ----------------------------
DROP TABLE IF EXISTS `interface`;
CREATE TABLE `interface` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `environment_id` int(11) NOT NULL,
  `api` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `interface_environment_id` (`environment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for product_testsuite
-- ----------------------------
DROP TABLE IF EXISTS `product_testsuite`;
CREATE TABLE `product_testsuite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `testsuite_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for replaceinfo
-- ----------------------------
DROP TABLE IF EXISTS `replaceinfo`;
CREATE TABLE `replaceinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `replace_url` varchar(255) DEFAULT NULL,
  `replace_data` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `beforeTask_id` int(11) NOT NULL DEFAULT '0',
  `isLoop` int(11) NOT NULL DEFAULT '0',
  `starttime` bigint(15) NOT NULL DEFAULT '0',
  `replaceInfo_id` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for task_testsuite
-- ----------------------------
DROP TABLE IF EXISTS `task_testsuite`;
CREATE TABLE `task_testsuite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL,
  `testsuite_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for testcase
-- ----------------------------
DROP TABLE IF EXISTS `testcase`;
CREATE TABLE `testcase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `interface_id` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `method` varchar(255) NOT NULL,
  `headers` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `asserts` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `variables` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for testsuite
-- ----------------------------
DROP TABLE IF EXISTS `testsuite`;
CREATE TABLE `testsuite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for testsuite_testcase
-- ----------------------------
DROP TABLE IF EXISTS `testsuite_testcase`;
CREATE TABLE `testsuite_testcase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `testsuite_id` int(11) NOT NULL,
  `testcase_id` int(11) NOT NULL,
  `priority` int(10) NOT NULL DEFAULT '0',
  `timeout` int(10) NOT NULL DEFAULT '0',
  `retry` int(10) NOT NULL DEFAULT '0',
  `intervaltime` int(10) NOT NULL DEFAULT '0',
  `delay` int(10) NOT NULL DEFAULT '0',
  `bindvariables` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
