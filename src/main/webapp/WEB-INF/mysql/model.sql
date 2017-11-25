-- --------------------------------------------------------
-- 主机:                           192.168.0.103
-- 服务器版本:                        5.7.20 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 model 的数据库结构
CREATE DATABASE IF NOT EXISTS `model` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `model`;

-- 导出  表 model.admin 结构
CREATE TABLE IF NOT EXISTS `admin` (
  `account` varchar(255) CHARACTER SET utf8 NOT NULL,
  `passwork` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。
-- 导出  表 model.annunciate 结构
CREATE TABLE IF NOT EXISTS `annunciate` (
  `vid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `work_type` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '通告类型',
  `deadline` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '发布时间',
  `abortTime` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '截止时间',
  `theme` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '标题',
  `work_time` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT 'work_time     ',
  `site` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '城市',
  `workplace` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '工作地点',
  `information` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '详细地址',
  `asex` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '性别',
  `number` int(11) NOT NULL COMMENT '人数',
  `interview` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '是否面试',
  `price` varchar(225) CHARACTER SET utf8 NOT NULL COMMENT '价格',
  `work_finish` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '截止时间',
  `details` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '详情说明',
  `uid` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '发布人',
  `audit` int(11) DEFAULT '0' COMMENT '审批',
  `report` int(11) DEFAULT '0' COMMENT '举报次数',
  `top` varchar(255) CHARACTER SET utf8 DEFAULT '0' COMMENT '置顶',
  `hit` int(10) DEFAULT '0' COMMENT '查看次数',
  `official` varchar(255) CHARACTER SET utf8 DEFAULT '非官方' COMMENT '是否官方',
  `update_time` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新时间',
  `addrmark` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '标记',
  `remove` varchar(10) DEFAULT '0' COMMENT '删除',
  `await` varchar(10) NOT NULL DEFAULT '0' COMMENT '待置顶',
  `contact` varchar(30) CHARACTER SET utf8 NOT NULL COMMENT '联系方式',
  `enough` int(1) DEFAULT '0' COMMENT '0否/1是 人数是否满员',
  PRIMARY KEY (`vid`),
  KEY `uid` (`uid`),
  KEY `work_type` (`work_type`),
  KEY `vid` (`vid`),
  CONSTRAINT `FK_annunciate_annunciatetype` FOREIGN KEY (`work_type`) REFERENCES `annunciatetype` (`antype`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_annunciate_model` FOREIGN KEY (`uid`) REFERENCES `model` (`nickname`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=966979214 DEFAULT CHARSET=latin1 COMMENT='通告';

-- 数据导出被取消选择。
-- 导出  表 model.annunciatetype 结构
CREATE TABLE IF NOT EXISTS `annunciatetype` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `antype` varchar(255) DEFAULT NULL COMMENT '通告类型',
  `picture` varchar(255) DEFAULT NULL COMMENT '图片url',
  PRIMARY KEY (`id`),
  KEY `antype` (`antype`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='通告类型';

-- 数据导出被取消选择。
-- 导出  表 model.banner 结构
CREATE TABLE IF NOT EXISTS `banner` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `BannerPicture` varchar(255) NOT NULL COMMENT '图片url',
  `BannerURL` varchar(255) NOT NULL COMMENT '链接url',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8 COMMENT='首页标题';

-- 数据导出被取消选择。
-- 导出  表 model.conversation 结构
CREATE TABLE IF NOT EXISTS `conversation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sender` varchar(50) NOT NULL DEFAULT '0' COMMENT '发送者',
  `recipient` varchar(50) NOT NULL DEFAULT '0' COMMENT '接受者',
  `StartTime` varchar(50) NOT NULL DEFAULT '0' COMMENT '开始时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='聊天会话';

-- 数据导出被取消选择。
-- 导出  表 model.dialogue 结构
CREATE TABLE IF NOT EXISTS `dialogue` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cid` int(11) NOT NULL DEFAULT '0' COMMENT '外键',
  `user` varchar(50) NOT NULL DEFAULT '0' COMMENT '用户',
  `content` varchar(300) NOT NULL DEFAULT '0' COMMENT '内容',
  `SendTime` varchar(20) NOT NULL DEFAULT '0' COMMENT '发送时间',
  `View` varchar(5) DEFAULT '未查看' COMMENT '是否查看',
  PRIMARY KEY (`id`),
  KEY `FK__conversation` (`cid`),
  CONSTRAINT `FK__conversation` FOREIGN KEY (`cid`) REFERENCES `conversation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8 COMMENT='聊天记录';

-- 数据导出被取消选择。
-- 导出  表 model.enjoy 结构
CREATE TABLE IF NOT EXISTS `enjoy` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `wid` int(11) DEFAULT NULL COMMENT '名片主键',
  `oneself` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '被点赞人',
  `nickname` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '点赞人',
  `headline` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '类型',
  `message` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '信息',
  `type` int(11) DEFAULT NULL COMMENT '1/2   点赞消息/留言消息',
  `look` int(11) DEFAULT '0' COMMENT '是否查看',
  `identifying` int(11) DEFAULT '0' COMMENT '是否删除',
  `timestamp` varchar(50) CHARACTER SET utf8 DEFAULT '0' COMMENT '时间',
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=latin1 COMMENT='点赞和留言消息';

-- 数据导出被取消选择。
-- 导出  表 model.model 结构
CREATE TABLE IF NOT EXISTS `model` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nickname` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT 'openid',
  `hurl` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '头像url',
  `name` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '名字',
  `sex` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `area` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '地区',
  `wx_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '联系方式',
  `member` varchar(255) CHARACTER SET utf8 DEFAULT '0',
  `mask` varchar(255) CHARACTER SET utf8 DEFAULT '0',
  `praise` int(11) DEFAULT '0' COMMENT '0/1   开启点赞提醒/关闭提醒',
  `Guestbook` int(11) DEFAULT '0' COMMENT '0/1   开启私信提醒/关闭提醒',
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `nickname` (`nickname`),
  KEY `nickname_2` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=3426 DEFAULT CHARSET=latin1 COMMENT='个人信息';

-- 数据导出被取消选择。
-- 导出  表 model.model_an 结构
CREATE TABLE IF NOT EXISTS `model_an` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mid` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '收藏人openid',
  `aid` int(11) NOT NULL COMMENT '通告主键',
  PRIMARY KEY (`id`),
  KEY `mid` (`mid`),
  KEY `aid` (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=267 DEFAULT CHARSET=latin1 COMMENT='通告收藏';

-- 数据导出被取消选择。
-- 导出  表 model.model_mo 结构
CREATE TABLE IF NOT EXISTS `model_mo` (
  `mo_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mid` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '收藏人',
  `moid` int(11) NOT NULL COMMENT '名片id',
  PRIMARY KEY (`mo_id`),
  KEY `mo_id` (`mo_id`),
  KEY `FK_model_mo_model` (`mid`),
  CONSTRAINT `FK_model_mo_model` FOREIGN KEY (`mid`) REFERENCES `model` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1 COMMENT='名片收藏';

-- 数据导出被取消选择。
-- 导出  表 model.mooke 结构
CREATE TABLE IF NOT EXISTS `mooke` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `nickname` varchar(255) CHARACTER SET utf8 DEFAULT '0' COMMENT 'openid',
  `type` int(11) DEFAULT '0' COMMENT '模卡类型',
  `subscript` int(11) DEFAULT '0' COMMENT '模卡占位',
  `path` varchar(255) CHARACTER SET utf8 DEFAULT '0' COMMENT '图片类型',
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。
-- 导出  表 model.official 结构
CREATE TABLE IF NOT EXISTS `official` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `vid` int(11) NOT NULL COMMENT '通告id',
  `message` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '详细信息',
  `out_trade_no` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '信息',
  `nickname` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '用户',
  `look` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '0' COMMENT '0/1未看/已看',
  `identification` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '0' COMMENT '0/1未删除/已删除',
  `headline` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '充值' COMMENT '标题',
  `timestamp` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='系统消息';

-- 数据导出被取消选择。
-- 导出  表 model.overall 结构
CREATE TABLE IF NOT EXISTS `overall` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `price` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `expire` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COMMENT='微信信息';

-- 数据导出被取消选择。
-- 导出  表 model.photo 结构
CREATE TABLE IF NOT EXISTS `photo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '0',
  `url` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '0',
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。
-- 导出  表 model.reports 结构
CREATE TABLE IF NOT EXISTS `reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vid` int(10) unsigned zerofill NOT NULL,
  `Message` varchar(255) CHARACTER SET utf8 NOT NULL,
  `headline` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '举报信息',
  `nickname` varchar(255) CHARACTER SET utf8 NOT NULL,
  `uid` varchar(255) CHARACTER SET utf8 NOT NULL,
  `time` varchar(255) CHARACTER SET utf8 NOT NULL,
  `audit` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '未审核',
  `examine` varchar(255) CHARACTER SET utf8 DEFAULT '0',
  `re_time` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='举报表';

-- 数据导出被取消选择。
-- 导出  表 model.re_view 结构
CREATE TABLE IF NOT EXISTS `re_view` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reid` int(11) NOT NULL,
  `url` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='通告举报表图片';

-- 数据导出被取消选择。
-- 导出  表 model.serve 结构
CREATE TABLE IF NOT EXISTS `serve` (
  `se_id` int(11) NOT NULL AUTO_INCREMENT,
  `se_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `details` varchar(2000) CHARACTER SET utf8 DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  `time` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`se_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1 COMMENT='付费信息';

-- 数据导出被取消选择。
-- 导出  表 model.stick 结构
CREATE TABLE IF NOT EXISTS `stick` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vid` int(11) DEFAULT NULL,
  `out_trade_no` varchar(255) DEFAULT NULL,
  `nowTime` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `stopTime` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `identifying` varchar(255) CHARACTER SET utf8 DEFAULT '0',
  `nickname` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  `sitz` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `payment` varchar(255) DEFAULT '0' COMMENT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='通告付费信息';

-- 数据导出被取消选择。
-- 导出  表 model.view 结构
CREATE TABLE IF NOT EXISTS `view` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `v_id` int(11) NOT NULL COMMENT '通告id',
  `p_url` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT 'url',
  PRIMARY KEY (`id`),
  KEY `v_id` (`v_id`),
  KEY `v_id_2` (`v_id`),
  CONSTRAINT `view_ibfk_1` FOREIGN KEY (`v_id`) REFERENCES `annunciate` (`vid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=543 DEFAULT CHARSET=latin1 COMMENT='通告图片';

-- 数据导出被取消选择。
-- 导出  表 model.work 结构
CREATE TABLE IF NOT EXISTS `work` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `wid` varchar(255) CHARACTER SET utf8 COMMENT '模特openid',
  `name` varchar(255) CHARACTER SET utf8 COMMENT 'name',
  `sex` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '性别',
  `age` varchar(255) CHARACTER SET utf8 COMMENT '年龄',
  `region` varchar(255) CHARACTER SET utf8 COMMENT '地区',
  `city` varchar(255) CHARACTER SET utf8 COMMENT '城市',
  `stature` varchar(255) CHARACTER SET utf8 COMMENT '身高',
  `weight` varchar(255) CHARACTER SET utf8 COMMENT '体重',
  `surround` varchar(255) CHARACTER SET utf8 COMMENT '三围',
  `shoe` varchar(255) CHARACTER SET utf8 COMMENT '鞋码',
  `workJob` varchar(255) CHARACTER SET utf8 COMMENT '风格标签',
  `workType` varchar(255) CHARACTER SET utf8 COMMENT '工作标签',
  `work` varchar(255) CHARACTER SET utf8 COMMENT '工作简历',
  `offer` varchar(255) CHARACTER SET utf8 COMMENT '工作报价',
  `describ` varchar(255) CHARACTER SET utf8 COMMENT '个人描述',
  `relation` varchar(255) CHARACTER SET utf8 COMMENT '联系方式',
  `QQ` varchar(255) CHARACTER SET utf8 COMMENT 'QQ',
  `times` varchar(255) CHARACTER SET utf8 COMMENT '发布时间',
  `Stick` int(11) DEFAULT '0' COMMENT '0/1     没有置顶/正在置顶',
  `wStick` int(11) DEFAULT '0' COMMENT '1        待置顶',
  `examine` int(11) DEFAULT '0' COMMENT '查看次数',
  `exist` int(11) DEFAULT '0' COMMENT '是否存在',
  `attention` int(11) DEFAULT '0' COMMENT '点赞次数',
  `etime` varchar(255) CHARACTER SET utf8 COMMENT '通过时间',
  PRIMARY KEY (`id`),
  KEY `wid` (`wid`),
  CONSTRAINT `FK_work_model` FOREIGN KEY (`wid`) REFERENCES `model` (`nickname`)
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=latin1 COMMENT='名片信息';

-- 数据导出被取消选择。
-- 导出  表 model.workpicture 结构
CREATE TABLE IF NOT EXISTS `workpicture` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `wid` int(11) NOT NULL DEFAULT '0' COMMENT '名片id',
  `picture` varchar(255) NOT NULL DEFAULT '0' COMMENT 'url',
  `type` int(11) DEFAULT NULL COMMENT '1封面/2模卡',
  `variety` int(11) DEFAULT NULL COMMENT '模卡类型',
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `FK_workpicture_work` (`wid`),
  CONSTRAINT `FK_workpicture_work` FOREIGN KEY (`wid`) REFERENCES `work` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8 COMMENT='名片图片';

-- 数据导出被取消选择。
-- 导出  表 model.worktype 结构
CREATE TABLE IF NOT EXISTS `worktype` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(50) NOT NULL DEFAULT '0' COMMENT '类型',
  `mold` int(11) DEFAULT NULL COMMENT '0/1/2',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='名片类型';

-- 数据导出被取消选择。
-- 导出  表 model.wreports 结构
CREATE TABLE IF NOT EXISTS `wreports` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `vid` int(10) NOT NULL COMMENT '名片',
  `Message` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '详细信息',
  `headline` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '举报信息' COMMENT '标题',
  `nickname` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '举报人',
  `uid` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '被举报人',
  `time` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '举报时间',
  `audit` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '未审核' COMMENT '未审核',
  `examine` varchar(255) CHARACTER SET utf8 DEFAULT '0' COMMENT '审核',
  `re_time` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1 COMMENT='举报表';

-- 数据导出被取消选择。
-- 导出  表 model.wstick 结构
CREATE TABLE IF NOT EXISTS `wstick` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `vid` int(11) DEFAULT NULL COMMENT '名片id',
  `out_trade_no` varchar(255) DEFAULT NULL COMMENT '点单号',
  `nowTime` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '开始时间',
  `stopTime` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '到期时间',
  `identifying` varchar(255) CHARACTER SET utf8 DEFAULT '0' COMMENT '0等待/1开始',
  `nickname` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户',
  `money` int(11) DEFAULT NULL COMMENT '金额',
  `payment` varchar(255) DEFAULT '0' COMMENT '0订单未成功/1订单成功',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COMMENT='名片付费信息';

-- 数据导出被取消选择。
-- 导出  表 model.wview 结构
CREATE TABLE IF NOT EXISTS `wview` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reid` int(11) NOT NULL,
  `url` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `FK_wview_wreports` (`reid`),
  CONSTRAINT `FK_wview_wreports` FOREIGN KEY (`reid`) REFERENCES `wreports` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1 COMMENT='举报图片';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
