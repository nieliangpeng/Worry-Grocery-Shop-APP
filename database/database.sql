/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : database

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2018-05-16 08:56:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_phone` char(255) NOT NULL,
  `admin_pwd` char(255) NOT NULL,
  `admin_name` char(255) NOT NULL,
  `admin_header` char(255) DEFAULT NULL,
  `admin_validatecode` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------

-- ----------------------------
-- Table structure for `collect`
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `collect_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `Ivtn_id` int(11) NOT NULL,
  `collect_time` datetime NOT NULL,
  PRIMARY KEY (`collect_id`),
  KEY `collect_ibfk_1` (`user_id`),
  KEY `collect_ibfk_2` (`Ivtn_id`),
  CONSTRAINT `collect_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `collect_ibfk_2` FOREIGN KEY (`Ivtn_id`) REFERENCES `invitation` (`Ivtn_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collect
-- ----------------------------

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `Ivtn_id` int(11) NOT NULL,
  `comment _content` char(255) DEFAULT NULL,
  `comment _state` char(255) DEFAULT NULL,
  `comment _isRealName
comment_isRealNam` char(255) NOT NULL,
  `comment_image` char(255) NOT NULL,
  `comment_time` date NOT NULL,
  `comment_praiseNum` int(11) NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `user_id` (`user_id`),
  KEY `Ivtn_id` (`Ivtn_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`Ivtn_id`) REFERENCES `invitation` (`Ivtn_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for `follow`
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
  `FL_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id_A` int(11) NOT NULL,
  `user_id_B` int(11) NOT NULL,
  PRIMARY KEY (`FL_id`),
  KEY `user_id_A` (`user_id_A`),
  KEY `user_id_B` (`user_id_B`),
  CONSTRAINT `follow_ibfk_1` FOREIGN KEY (`user_id_A`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of follow
-- ----------------------------

-- ----------------------------
-- Table structure for `follow_type`
-- ----------------------------
DROP TABLE IF EXISTS `follow_type`;
CREATE TABLE `follow_type` (
  `FT_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  PRIMARY KEY (`FT_id`),
  KEY `type_id` (`type_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `follow_type_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `follow_type_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `invt_type` (`type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of follow_type
-- ----------------------------

-- ----------------------------
-- Table structure for `get_letter`
-- ----------------------------
DROP TABLE IF EXISTS `get_letter`;
CREATE TABLE `get_letter` (
  `gl_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `letter_id` int(11) NOT NULL,
  `gl_time` datetime NOT NULL,
  PRIMARY KEY (`gl_id`),
  KEY `user_id` (`user_id`),
  KEY `letter_id` (`letter_id`),
  CONSTRAINT `get_letter_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `get_letter_ibfk_2` FOREIGN KEY (`letter_id`) REFERENCES `treeholes` (`letter_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of get_letter
-- ----------------------------

-- ----------------------------
-- Table structure for `image`
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
  `image_id` int(11) NOT NULL AUTO_INCREMENT,
  `Ivtn_id` int(11) NOT NULL,
  `image_name` char(255) NOT NULL,
  PRIMARY KEY (`image_id`),
  KEY `Ivtn_id` (`Ivtn_id`),
  CONSTRAINT `image_ibfk_1` FOREIGN KEY (`Ivtn_id`) REFERENCES `invitation` (`Ivtn_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of image
-- ----------------------------

-- ----------------------------
-- Table structure for `invitation`
-- ----------------------------
DROP TABLE IF EXISTS `invitation`;
CREATE TABLE `invitation` (
  `Ivtn_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `Ivtn_time` datetime NOT NULL,
  `Ivtn_content` char(255) NOT NULL,
  `Ivtn_title` char(255) NOT NULL,
  `Ivtn_PraiseNum` int(11) NOT NULL,
  `Ivtn_state` char(255) NOT NULL,
  `Ivtn_isPublic` char(255) NOT NULL,
  `Ivtn_lookNum` int(11) NOT NULL,
  PRIMARY KEY (`Ivtn_id`),
  KEY `user_id` (`user_id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `invitation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `invitation_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `invt_type` (`type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of invitation
-- ----------------------------

-- ----------------------------
-- Table structure for `invt_type`
-- ----------------------------
DROP TABLE IF EXISTS `invt_type`;
CREATE TABLE `invt_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` char(255) NOT NULL,
  `type_image` char(255) NOT NULL,
  `type_desc` char(255) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of invt_type
-- ----------------------------

-- ----------------------------
-- Table structure for `joke`
-- ----------------------------
DROP TABLE IF EXISTS `joke`;
CREATE TABLE `joke` (
  `joke_id` int(11) NOT NULL AUTO_INCREMENT,
  `joke_content` char(255) NOT NULL,
  PRIMARY KEY (`joke_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of joke
-- ----------------------------

-- ----------------------------
-- Table structure for `letterreply`
-- ----------------------------
DROP TABLE IF EXISTS `letterreply`;
CREATE TABLE `letterreply` (
  `letter_reply_id` int(11) NOT NULL AUTO_INCREMENT,
  `letter_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `letter_content` char(255) NOT NULL,
  `letter_time` datetime NOT NULL,
  PRIMARY KEY (`letter_reply_id`),
  KEY `letter_id` (`letter_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `letterreply_ibfk_1` FOREIGN KEY (`letter_id`) REFERENCES `treeholes` (`letter_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `letterreply_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of letterreply
-- ----------------------------

-- ----------------------------
-- Table structure for `penfriend`
-- ----------------------------
DROP TABLE IF EXISTS `penfriend`;
CREATE TABLE `penfriend` (
  `PF-id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id_A` int(11) NOT NULL,
  `user_id_B` int(11) NOT NULL,
  PRIMARY KEY (`PF-id`),
  KEY `user_id_A` (`user_id_A`),
  KEY `user_id_B` (`user_id_B`),
  CONSTRAINT `penfriend_ibfk_1` FOREIGN KEY (`user_id_A`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of penfriend
-- ----------------------------

-- ----------------------------
-- Table structure for `reply`
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply` (
  `reply_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment _id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `reply_content` char(255) DEFAULT NULL,
  PRIMARY KEY (`reply_id`),
  KEY `comment _id` (`comment _id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `reply_ibfk_1` FOREIGN KEY (`comment _id`) REFERENCES `comment` (`comment_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reply_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reply
-- ----------------------------

-- ----------------------------
-- Table structure for `textshout`
-- ----------------------------
DROP TABLE IF EXISTS `textshout`;
CREATE TABLE `textshout` (
  `textShout_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `textContent` char(255) NOT NULL,
  PRIMARY KEY (`textShout_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `textshout_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of textshout
-- ----------------------------

-- ----------------------------
-- Table structure for `treeholes`
-- ----------------------------
DROP TABLE IF EXISTS `treeholes`;
CREATE TABLE `treeholes` (
  `letter_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `letter_content` char(255) NOT NULL,
  `letter_time` datetime NOT NULL,
  `isShowInTheTree` char(255) NOT NULL,
  PRIMARY KEY (`letter_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `treeholes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of treeholes
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` char(255) NOT NULL,
  `user_pwd` char(255) NOT NULL,
  `user_phone` int(11) NOT NULL,
  `user_desc` char(255) DEFAULT NULL,
  `user_image` char(255) NOT NULL,
  `user_state` char(255) DEFAULT NULL,
  `master_profile` char(255) DEFAULT NULL,
  `detailintroduction` varchar(200) DEFAULT NULL,
  `user_validatecode` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for `video`
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `video_id` int(11) NOT NULL AUTO_INCREMENT,
  `video_fileName` char(255) NOT NULL,
  `video_resource` char(255) NOT NULL,
  `video_time` datetime NOT NULL,
  `good_num` int(11) NOT NULL,
  PRIMARY KEY (`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video
-- ----------------------------

-- ----------------------------
-- Table structure for `voicescream`
-- ----------------------------
DROP TABLE IF EXISTS `voicescream`;
CREATE TABLE `voicescream` (
  `voiceScream_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `voice_fileName` char(255) NOT NULL,
  PRIMARY KEY (`voiceScream_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `voicescream_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of voicescream
-- ----------------------------
