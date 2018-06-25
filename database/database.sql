/*
Navicat MySQL Data Transfer

Source Server         : heart
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : database

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2018-06-25 08:39:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_phone` char(255) DEFAULT NULL,
  `admin_pwd` char(255) DEFAULT NULL,
  `admin_name` char(255) DEFAULT NULL,
  `admin_header` char(255) DEFAULT NULL,
  `admin_validatecode` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', '11111111111', '6666', '张三', '15级6班实践图片(2).jpg', null);
INSERT INTO `admin` VALUES ('2', '18227397826', '1111', '闫佳成', 'u=281049605,2870971905&fm=27&gp=0.jpg', null);

-- ----------------------------
-- Table structure for `collect`
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect` (
  `collect_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `Ivtn_id` int(11) NOT NULL,
  `collect_time` varchar(333) NOT NULL,
  PRIMARY KEY (`collect_id`),
  KEY `collect_ibfk_1` (`user_id`),
  KEY `collect_ibfk_2` (`Ivtn_id`),
  CONSTRAINT `collect_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `collect_ibfk_2` FOREIGN KEY (`Ivtn_id`) REFERENCES `invitation` (`ivtn_id`) ON DELETE CASCADE ON UPDATE CASCADE
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
  `user_id` int(11) DEFAULT NULL,
  `ivtn_id` int(11) DEFAULT NULL,
  `comment_content` varchar(5555) DEFAULT NULL,
  `comment_state` char(255) CHARACTER SET utf8 DEFAULT NULL,
  `comment_image` char(255) CHARACTER SET utf8 DEFAULT NULL,
  `comment_time` varchar(333) CHARACTER SET utf8 DEFAULT NULL,
  `comment_praiseNum` int(11) DEFAULT NULL,
  `comment_namexu` varchar(333) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `user_id` (`user_id`),
  KEY `Ivtn_id` (`ivtn_id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`ivtn_id`) REFERENCES `invitation` (`ivtn_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '1', '3', '生活是需要历练的，我们能做的就是接受现在的自己，把握当下，一点一点提高自己，自信一点，勇敢一点，你会遇见更好的自己', '1', '', '2018-6-19 14:26:56', '3', '');
INSERT INTO `comment` VALUES ('2', '1', '3', '一时的不顺不代表一世的不顺，我们只是还需要时间去学会长大而已。', '1', null, '2018-6-20 14:26:56', '2', null);
INSERT INTO `comment` VALUES ('3', '2', '3', '与其抱怨命运，不如冷静思考，给自己一个目标，每天朝着目标努力，你会发现原来生活是这么美好。', '1', null, '2018-6-30 14:26:56', '4', null);
INSERT INTO `comment` VALUES ('4', '1', '3', '什么都不会不害怕，害怕的是什么都不做，加油！', null, null, '2018-06-19 14:26:56', '0', null);
INSERT INTO `comment` VALUES ('5', '1', '3', '学会忍耐，学会坚持，学会自制', null, null, '2018-06-19 14:27:03', '0', null);
INSERT INTO `comment` VALUES ('12', '1', '4', '相信你一定能考上！', null, null, '2018-06-19 14:40:05', '0', null);
INSERT INTO `comment` VALUES ('14', '1', '4', '祝你心想事成', null, null, '2018-06-19 14:41:59', '0', null);
INSERT INTO `comment` VALUES ('15', '1', '4', '和你一起努力，加油！', null, null, '2018-06-19 14:42:07', '0', null);
INSERT INTO `comment` VALUES ('22', '1', '5', '爱情和婚姻从来不是靠外貌来维系的！', null, null, '2018-06-19 15:14:02', '0', null);
INSERT INTO `comment` VALUES ('23', '1', '5', '真正懂得珍惜你的人，在意的是你的优点，而不是外貌', null, null, '2018-06-19 15:14:53', '0', null);
INSERT INTO `comment` VALUES ('24', '1', '5', '自信的女人最美', null, null, '2018-06-19 15:15:03', '0', null);
INSERT INTO `comment` VALUES ('27', '1', '9', '既是在乎的人更应该理智，学会理解，学会宽容。', null, null, '2018-06-19 16:07:15', '0', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of follow_type
-- ----------------------------
INSERT INTO `follow_type` VALUES ('1', '1', '1');
INSERT INTO `follow_type` VALUES ('7', '1', '3');
INSERT INTO `follow_type` VALUES ('8', '1', '2');

-- ----------------------------
-- Table structure for `get_letter`
-- ----------------------------
DROP TABLE IF EXISTS `get_letter`;
CREATE TABLE `get_letter` (
  `gl_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `letter_id` int(11) NOT NULL,
  `gl_time` varchar(333) NOT NULL,
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
-- Table structure for `invitation`
-- ----------------------------
DROP TABLE IF EXISTS `invitation`;
CREATE TABLE `invitation` (
  `ivtn_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `ivtn_time` varchar(333) NOT NULL,
  `ivtn_content` varchar(5555) NOT NULL,
  `ivtn_title` char(255) DEFAULT NULL,
  `ivtn_PraiseNum` int(11) DEFAULT NULL,
  `ivtn_state` char(255) DEFAULT NULL,
  `ivtn_isPublic` char(255) DEFAULT NULL,
  `ivtn_lookNum` int(11) DEFAULT NULL,
  `image` varchar(344) NOT NULL,
  PRIMARY KEY (`ivtn_id`),
  KEY `user_id` (`user_id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `invitation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `invitation_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `invt_type` (`type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of invitation
-- ----------------------------
INSERT INTO `invitation` VALUES ('3', '1', '5', '2018-06-13 16:28:26', '人生不如意十之八九，我总算是见识到了，一天时间起起伏伏，逗我呢，怪不得别人，全都是自己不行，谁也帮不了，过不了线，我就惨了，死的比谁都难看，啊~真的想出去浪，放空，然额，看着论文还没写完就默默的打开电脑，接着做，感觉自己好无能啊，能力能力不行，办事办事也不行，就算找工作也不知道干什么，我~哎 ', null, '17', null, null, null, 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\WEB-INF\\3.jpeg');
INSERT INTO `invitation` VALUES ('4', '1', '5', '2018-06-13 16:29:54', '一直不学无术的我，如今面临就业，学历成了阻碍，平时吊儿郎当的我，现在必须拿出真正的干劲来学习，自考本科是一次“考验”，希望和我一样要考试的朋友们千万不要泄气，好好加油，把握机会。 ', null, '3', null, null, null, 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\WEB-INF\\grade.jpg');
INSERT INTO `invitation` VALUES ('5', '2', '5', '2018-06-13 17:31:37', '因为撞倒啤酒瓶额头上有一个缝了7针的疤痕，小时候倒还觉得没什么，后来就一直留齐刘海了，其实我还是很介意被别人看到，尽管别人说没什么的，也会有自卑。     现在是遮住了疤痕，如果有一天恋爱了，会不会很介意？  也很需要勇气是的', null, '4', null, null, null, 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\WEB-INF\\2.jpg');
INSERT INTO `invitation` VALUES ('6', '2', '2', '2018-06-13 17:31:37', '我老公百般反对希望我留在北方，说自己去浙江找不到工作我的家乡房价太贵等等天天和我争吵希望我留在他的家乡找一份平淡的工作可是我想好好发展自己的事业这样有错吗 ', null, '7', null, null, null, 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\WEB-INF\\5.jpg');
INSERT INTO `invitation` VALUES ('7', '1', '1', '2018-06-19 15:36:27', '感谢上天让我们在最美丽的年纪相遇，那年我13岁，你14岁，我们是同桌，同月同日生，青春的气息刚刚在我们的内心萌动，缘开始于此，我们互生欢喜，却又保持着少男少女特有的羞涩与矜持。不久，你就转学了，我们开始通信，也开始了对彼此的思念。还记得每年过生日，都会收到你的礼物，最喜欢的是那个水晶的小屋，里面住着一朵火红的玫瑰。这也是少女时的我最美丽的梦，希望若干年后我们真的能有情人终成眷属。呵呵。。。。。。。。', null, '0', null, null, null, 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\WEB-INF\\6.jpg');
INSERT INTO `invitation` VALUES ('8', '1', '4', '2018-06-19 15:43:08', '清平乐\r\n三日凉雨，寒意袭我胸；意思念缕游成综，何能屯毛理清(毛理清)；\r\n本欲翻江乘龙，金箍当头萦胸；也罢浮云蔽空，早知一生不平。', null, '0', null, null, null, 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\WEB-INF\\1.jpg');
INSERT INTO `invitation` VALUES ('9', '1', '3', '2018-06-19 16:02:20', '能伤害你的，只有那些你在乎的人。\r\n\r\n长大意味着什么？就是即使内心波澜壮阔，表面上也要风平浪静。一切的喜怒哀乐，埋到心底最深处，自己消化，即使面对家人，也不可以。\r\n\r\n这座熟悉又陌生的城市，我不舍但是又急切地想要逃离，因为它让我觉得压抑。', null, '2', null, null, null, 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\WEB-INF\\7.png');
INSERT INTO `invitation` VALUES ('10', '1', '3', '2018-06-19 16:08:19', '烦死了哎当父母不理解自己一直以来就他们想按自己的方法来感觉好累好压抑所以干事情也没有动力本来自己上大学出来住了结果他们又跟过了真烦希望能快点逃离这种感觉希望能赶快经济独立 不想和他们天天住一起老一辈人总想把自己的精神加在我身上 ', null, '0', null, null, null, 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\WEB-INF\\8.jpg');

-- ----------------------------
-- Table structure for `invt_type`
-- ----------------------------
DROP TABLE IF EXISTS `invt_type`;
CREATE TABLE `invt_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` char(255) NOT NULL,
  `type_image` varchar(677) NOT NULL,
  `type_desc` char(255) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of invt_type
-- ----------------------------
INSERT INTO `invt_type` VALUES ('1', '初恋那些事', 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\WEB-INF\\love.jpeg', '初恋那些事');
INSERT INTO `invt_type` VALUES ('2', '温暖你的世界', 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\WEB-INF\\warm.jpeg', '温暖你的世界');
INSERT INTO `invt_type` VALUES ('3', '就是不开心', 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\WEB-INF\\upset.jpeg', '就是不开心');
INSERT INTO `invt_type` VALUES ('4', '烦恼事情', 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\WEB-INF\\fannao.jpeg', '烦恼');
INSERT INTO `invt_type` VALUES ('5', '日常发帖', 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\WEB-INF\\richang.jpg', '纪录日常');

-- ----------------------------
-- Table structure for `joke`
-- ----------------------------
DROP TABLE IF EXISTS `joke`;
CREATE TABLE `joke` (
  `joke_id` int(11) NOT NULL AUTO_INCREMENT,
  `joke_content` varchar(5555) NOT NULL,
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
  `user_id` int(255) DEFAULT NULL,
  `letter_id` int(11) NOT NULL,
  `letter_content` varchar(5555) NOT NULL,
  `letter_time` varchar(333) NOT NULL,
  PRIMARY KEY (`letter_reply_id`),
  KEY `letter_id` (`letter_id`),
  CONSTRAINT `letterreply_ibfk_1` FOREIGN KEY (`letter_id`) REFERENCES `treeholes` (`letter_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of letterreply
-- ----------------------------
INSERT INTO `letterreply` VALUES ('5', '1', '6', '人生如戏，全靠演技。很久以前就想建立一个可以的脱下戏服用心交流的平台，直到后来通过某电影认识了心灵捕手。让我惊讶的是几乎所有来这里的人都有些负面情绪。亲爱的路人，虽然你我并不相识，能看到这条消息就是一种缘分。无论你遇到什么困难，或者不开心，希望你能坚强勇敢。活着不为了出名也不为了光宗耀祖，只愿今天所做对得起自己的心。善良的人运气总不会差。 ', '2018_6_24  13:36:00');
INSERT INTO `letterreply` VALUES ('6', '2', '2', '越努力越幸运，加油', '2018_6_22  14:40:00');
INSERT INTO `letterreply` VALUES ('7', '2', '2', '压力是必要的，有压力才会有动力吗，但是要把握好自己的情绪，化被动为主动，你就是生活的赢家，人生的主人。', '2018_6_23   07:00:00');

-- ----------------------------
-- Table structure for `music`
-- ----------------------------
DROP TABLE IF EXISTS `music`;
CREATE TABLE `music` (
  `music_id` int(255) NOT NULL,
  `music_name` varchar(322) NOT NULL,
  `music_url` varchar(444) NOT NULL,
  `music_auth` varchar(333) NOT NULL,
  `music_img` varchar(555) NOT NULL,
  PRIMARY KEY (`music_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of music
-- ----------------------------
INSERT INTO `music` VALUES ('1', '邂逅', 'http://up.mcyt.net/?down/47169.mp3', '刘蓝溪', 'http://oeff2vktt.bkt.clouddn.com/image/35.jpg');
INSERT INTO `music` VALUES ('3', '成都', 'http://up.mcyt.net/?down/35925.mp3', '赵雷', 'http://oeff2vktt.bkt.clouddn.com/image/27.jpg');
INSERT INTO `music` VALUES ('4', '花游记 - 如果你是我', 'http://up.mcyt.net/?down/47166.mp3', '智珉', 'http://oeff2vktt.bkt.clouddn.com/image/75.jpg');
INSERT INTO `music` VALUES ('5', '花火', 'http://up.mcyt.net/?down/31273.mp3', '贝贝', 'http://oeff2vktt.bkt.clouddn.com/image/81.jpg');
INSERT INTO `music` VALUES ('6', '彩虹天堂', 'http://up.mcyt.net/?down/47138.mp3', '刘畊宏', 'http://oeff2vktt.bkt.clouddn.com/image/64.jpg');
INSERT INTO `music` VALUES ('7', '下完这场雨', 'http://up.mcyt.net/?down/38862.mp3', '后弦', 'http://oeff2vktt.bkt.clouddn.com/image/40.jpg');
INSERT INTO `music` VALUES ('8', '月光', 'http://up.mcyt.net/?down/33689.mp3', '胡彦斌 ', 'http://oeff2vktt.bkt.clouddn.com/image/91.jpg');
INSERT INTO `music` VALUES ('9', '暗香', 'http://up.mcyt.net/?down/34206.mp3', '沙宝亮', 'http://oeff2vktt.bkt.clouddn.com/image/77.jpg');

-- ----------------------------
-- Table structure for `penfriend`
-- ----------------------------
DROP TABLE IF EXISTS `penfriend`;
CREATE TABLE `penfriend` (
  `PF_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id_A` int(11) NOT NULL,
  `user_id_B` int(11) NOT NULL,
  PRIMARY KEY (`PF_id`),
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
  `comment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `reply_content` char(255) DEFAULT NULL,
  PRIMARY KEY (`reply_id`),
  KEY `comment _id` (`comment_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `reply_ibfk_1` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`comment_id`) ON DELETE CASCADE ON UPDATE CASCADE,
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
  `textContent` varchar(5555) NOT NULL,
  PRIMARY KEY (`textShout_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `textshout_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of textshout
-- ----------------------------
INSERT INTO `textshout` VALUES ('1', '1', '你这个负心汉，有多远滚多远');
INSERT INTO `textshout` VALUES ('2', '1', '好烦呀啊啊啊啊啊啊啊');
INSERT INTO `textshout` VALUES ('3', '1', '你怎么不去吃屎呀');
INSERT INTO `textshout` VALUES ('4', '1', '啊啊啊啊啊啊啊啊');
INSERT INTO `textshout` VALUES ('5', '1', '啊啊啊啊啊啊啊啊');
INSERT INTO `textshout` VALUES ('6', '1', '地地道道的点点滴滴滴滴答答');

-- ----------------------------
-- Table structure for `treeholes`
-- ----------------------------
DROP TABLE IF EXISTS `treeholes`;
CREATE TABLE `treeholes` (
  `letter_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `letter_content` varchar(5555) NOT NULL,
  `letter_time` varchar(255) NOT NULL,
  `isShowInTheTree` char(255) DEFAULT NULL,
  PRIMARY KEY (`letter_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `treeholes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of treeholes
-- ----------------------------
INSERT INTO `treeholes` VALUES ('1', '1', '不知道为什么，越来越无法表达自己的情绪了。有太多话想说，但是往往到最后还是通通收了回来。', '2018-06-20 18:47:29', '1');
INSERT INTO `treeholes` VALUES ('2', '1', '压力好大啊考研的压力找工作的压力各种各样的压力都让我无法喘气 ', '2018-05-27 18:43:07', '1');
INSERT INTO `treeholes` VALUES ('3', '1', '令人伤心的毕业设计成绩我大三下学期花了一个学期的时间做实验，风雨无阻，而且基本上没有人带，基本是自己做的，师姐就是给了我一些资料和口头指导，我自己第一次做这种可能没结果的实验，一次次失败了又重来，终于做完了收集了很多数据。大多数同学都大四下这一学期断断续续做的实验，有的甚至根本没做。答辩的时候，可能我自我感觉比较良好吧，我觉得我答的是我们这组比较好的，有个老师问得明显是刁难的问题我也回答上了啊。结果最后出来我的成绩太靠后了，我自己说实话真的不是很能接收。', '2018-05-27 18:43:55', '1');
INSERT INTO `treeholes` VALUES ('4', '1', '不想写代码啊啊啊啊啊啊啊啊啊啊啊啊啊啊', '2018-05-27 18:47:29', '1');
INSERT INTO `treeholes` VALUES ('5', '2', '令人伤心的毕业设计成绩我大三下学期花了一个学期的时间做实验，风雨无阻，而且基本上没有人带，基本是自己做的，师姐就是给了我一些资料和口头指导，我自己第一次做这种可能没结果的实验，一次次失败了又重来，终于做完了收集了很多数据。大多数同学都大四下这一学期断断续续做的实验，有的甚至根本没做。答辩的时候，可能我自我感觉比较良好吧，我觉得我答的是我们这组比较好的，有个老师问得明显是刁难的问题我也回答上了啊。结果最后出来我的成绩太靠后了，我自己说实话真的不是很能接收。', '2018-05-28 18:47:29', '1');
INSERT INTO `treeholes` VALUES ('6', '2', '承认自己不行很难吗？还是在别人期许的目光下，你终究是不能承认自己的不足？\r\n\r\n我不敢承认我在法国待了5年了，很多时候我还是听不懂别人说的话\r\n\r\n我不敢承认，我35岁了，我工作5年了，我还是又好多东西不知道的\r\n\r\n我不敢承认，我真的不具备博士的能力，我不会做科研，我根本没有那个水平\r\n\r\n我不敢承认，我花了5年的时间，我一事无成，我浪费了时间，我浪费的金钱，但这一切都是bullshit\r\n\r\n我不敢承认，我不行，我真的不行，我写不出东西，我分析不了数据\r\n\r\n承认这些，真的很难吗\r\n\r\n承认这些，我是不是就会失去所有', '2018-05-29 18:47:29', '1');
INSERT INTO `treeholes` VALUES ('7', '1', '用公司手机看你的朋友圈，和用私人手机看你朋友圈，发现你将我分组可见。我在你心里是怎样的存在？会是怎样的分组？让你能在某时点赞？我告诉别人不喜欢谈恋爱，才发现，因为那里没有你。因为太喜欢你，所以决定远离你。', '2018-05-29 10:03:11', '1');
INSERT INTO `treeholes` VALUES ('8', '1', '不要一直这么丧下去了，没什么意义，明天又是新的一天，好好工作，好好生活，主要是好好生活，晚安', '2018-06-20 17:29:02', '1');
INSERT INTO `treeholes` VALUES ('9', '2', '今天向喜欢的人表白了，偷偷喜欢了很久，决定告诉他的时候，并不是为了得到一个答案，而是因为放下了，25岁的我，31岁的他，在这个地球上不同的角落忙碌着彼此的生活，很幸运曾经遇见了……虽然那么短暂笑着祝福他在未来的某一天遇到自己喜欢的人，诚挚的说着感谢地话。成年人的感情，再不向小孩子一般张狂放肆，理智与克制。 ', '2018-06-20 17:29:04', '1');
INSERT INTO `treeholes` VALUES ('10', '2', '说实话，我很孤独，可是哪有那么多感同身受？我想永远不要将自己的喜怒哀乐寄托在别人身上！人活着，努力的向上，也不就为了让自己过的更舒坦，自然吗，你何必为了别人的一句话，一个表情，而委屈自己呢？开心也是一天，不开心也是一天，所以放弃那些令你生活更糟的事情吧！我认为你真的可以过的很好。 ', '2018-06-22 10:42:21', '1');
INSERT INTO `treeholes` VALUES ('11', '1', '我又来烦你了，我终于毕业了，心里有好多感慨，到处充斥着悲\r\r\n伤，离别。突然发现自己变了许多，心态，性格，人生观等等吧\r\r\n，不过我依旧那么懦弱，胆怯，自私，人性的部分可能这辈子无\r\r\n法改变了吧。', '2018-06-22 10:42:23', '1');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `user_name` char(255) NOT NULL,
  `user_pwd` char(255) NOT NULL,
  `user_phone` varchar(50) NOT NULL,
  `user_desc` char(255) DEFAULT NULL,
  `user_image` varchar(445) NOT NULL,
  `user_state` char(255) DEFAULT NULL,
  `master_profile` char(255) DEFAULT NULL,
  `detailintroduction` varchar(5555) DEFAULT NULL,
  `user_validatecode` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'tony', '6666', '13666419209', null, 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\upload\\user\\13666419209\\1.jpg', 'senior', '首席婚恋专家', '资深情感导师，首席婚恋专家，知名电台情感主播，2009年开始全职从事婚恋情感咨询与恋爱情商培训工作，在大量的实战基础与情感案例中，积累大量成功经验，并提炼总结出:应对不同情感问题的有效的实操方法，原创的两性情感体系。8年情感咨询生涯，服务过8000+情感患者，并成功给予帮助，累计超过1万小时的情感答疑工作经验。将情感指导、两性魅力蜕变作为终身事业，帮助更多情感困惑的朋友实现情感自由！', null);
INSERT INTO `user` VALUES ('2', 'bob', '2222', '15733191531', null, 'F:\\apache-tomcat-9.0.0.M26\\webapps\\AndroidApp_Backstage_design\\upload\\user\\15733191531\\2.jpg', 'senior', '国家二级心理咨询师', '医学/法律双学历、国家二级心理咨询师、执业医师、情感分析师、催眠治疗师、性治疗师(高级)、遗传基因咨询师。\r\n国际人类基因变异组项目中国区专家、中国心理学会会员、著名情感/心理机构首席咨询师。全国优秀心理工作者。娱乐圈成功人士[私人心理顾问]多年。\r\n咨询实践二十年，咨询个案8000多例。医学院校《医学心理学》教学、临床内科与心理医生工作经历，数百场团体心理辅导实战经验。\r\n医学/法律双学历、国家二级心理咨询师、执业医师、情感分析师、催眠治疗师、性治疗师(高级)、遗传基因咨询师。\r\n国际人类基因变异组项目中国区专家、中国心理学会会员、著名情感/心理机构首席咨询师。全国优秀心理工作者。娱乐圈成功人士[私人心理顾问]多年。\r\n咨询实践二十年，咨询个案8000多例。医学院校《医学心理学》教学、临床内科与心理医生工作经历，数百场团体心理辅导实战经验。\r\n医学/法律双学历、国家二级心理咨询师、执业医师、情感分析师、催眠治疗师、性治疗师(高级)、遗传基因咨询师。\r\n国际人类基因变异组项目中国区专家、中国心理学会会员、著名情感/心理机构首席咨询师。全国优秀心理工作者。娱乐圈成功人士[私人心理顾问]多年。\r\n咨询实践二十年，咨询个案8000多例。医学院校《医学心理学》教学、临床内科与心理医生工作经历，数百场团体心理辅导实战经验。\r\n', '');

-- ----------------------------
-- Table structure for `video`
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `video_id` int(11) NOT NULL,
  `video_fileName` char(255) NOT NULL,
  `video_resource` char(255) NOT NULL,
  `video_time` varchar(333) NOT NULL,
  PRIMARY KEY (`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO `video` VALUES ('1', '腾讯视频-PAPI酱', 'https://v.qq.com/iframe/player.html?vid=f065078dp1j&tiny=0&auto=0', '2018-05-23');
INSERT INTO `video` VALUES ('2', '腾讯视频-宝贝是超人', 'https://v.qq.com/iframe/player.html?vid=a0698xqhih9&tiny=0&auto=0', '2018-05-25');
INSERT INTO `video` VALUES ('3', '腾讯视频-PAPI酱', 'https://v.qq.com/iframe/player.html?vid=b01942ournd&tiny=0&auto=0', '2018-05-26');
INSERT INTO `video` VALUES ('4', '腾讯视频-PAPI酱', 'https://v.qq.com/iframe/player.html?vid=u0533dasi5d&tiny=0&auto=0', '2018-05-27 ');
INSERT INTO `video` VALUES ('5', '腾讯视频-王者荣耀周边', 'https://v.qq.com/iframe/player.html?vid=w0612qmwiev&tiny=0&auto=0', '2018-05-29');

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

-- ----------------------------
-- Table structure for `word`
-- ----------------------------
DROP TABLE IF EXISTS `word`;
CREATE TABLE `word` (
  `word_id` int(255) NOT NULL,
  `word_imgResource` varchar(355) DEFAULT NULL,
  `word_musicResource` varchar(355) DEFAULT NULL,
  `time` varchar(355) DEFAULT NULL,
  PRIMARY KEY (`word_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of word
-- ----------------------------
INSERT INTO `word` VALUES ('1', 'http://imgsrc.baidu.com/forum/pic/item/f7096b63f6246b60453236afedf81a4c510fa229.jpg', 'http://up.mcyt.net/?down/47169.mp3', '2018-06-24');
