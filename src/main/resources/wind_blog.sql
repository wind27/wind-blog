/*
 Navicat Premium Data Transfer

 Source Server         : dev.wind.com
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : dev.wind.com
 Source Database       : wind_blog

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : utf-8

 Date: 08/28/2018 20:14:45 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `blog`
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tags` varchar(64) DEFAULT '' COMMENT '标签：多个之间用,隔开',
  `source` int(11) NOT NULL DEFAULT '1' COMMENT '文章来源：1、阿里云',
  `title` varchar(64) NOT NULL DEFAULT '' COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `summary` varchar(255) NOT NULL DEFAULT '' COMMENT '摘要',
  `uid` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id：0、表示网上爬取',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `publish_time` timestamp NULL DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `link`
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source` int(4) NOT NULL DEFAULT '1' COMMENT '来源：1、阿里云',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '链接URL',
  `is_parse` int(4) NOT NULL DEFAULT '0' COMMENT '是否已解析：1、是；0、否',
  `blog_id` bigint(20) DEFAULT NULL COMMENT 'blog id ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
