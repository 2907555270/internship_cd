-- MySQL dump 10.13  Distrib 8.0.29, for Linux (x86_64)
--
-- Host: mysql    Database: levelSchool
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `parent_id` bigint NOT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(64) NOT NULL COMMENT '菜单名',
  `path` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单URL',
  `perms` varchar(255) NOT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `component` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单描述',
  `type` int NOT NULL DEFAULT '-1' COMMENT '类型     0：目录   1：菜单   2：按钮',
  `icon` varchar(32) NOT NULL DEFAULT '' COMMENT '菜单图标',
  `orderNum` int NOT NULL DEFAULT '0' COMMENT '排序',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_menu_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `path`, `perms`, `component`, `type`, `icon`, `orderNum`, `created`, `updated`, `status`) VALUES (1,0,'系统工具','','sys:tools','',0,'el-icon-s-tools',2,'2021-01-15 19:06:11','2022-07-13 02:03:08',1),(2,0,'系统管理','','sys:manage','',0,'el-icon-s-operation',1,'2021-01-15 18:58:18','2022-07-13 02:03:08',1),(3,2,'用户管理','/sys/users','sys:user:list','sys/User',1,'el-icon-s-custom',1,'2021-01-15 19:03:45','2022-07-13 02:03:52',1),(4,2,'角色管理','/sys/roles','sys:role:list','sys/Role',1,'el-icon-rank',2,'2021-01-15 19:03:45','2022-07-13 02:03:52',1),(5,2,'菜单管理','/sys/menus','sys:menu:list','sys/Menu',1,'el-icon-menu',3,'2021-01-15 19:03:45','2022-07-13 02:03:52',1),(6,3,'添加用户','','sys:user:save','',2,'',1,'2021-01-17 21:48:32','2022-07-13 02:03:08',1),(7,3,'修改用户','','sys:user:update','',2,'',2,'2021-01-17 21:49:03','2022-07-13 02:03:08',1),(8,3,'重置密码','','sys:user:repass','',2,'',5,'2021-01-17 21:50:36','2022-07-13 02:03:08',1),(9,3,'删除用户','','sys:user:delete','',2,'',3,'2021-01-17 21:49:21','2022-07-13 02:03:08',1),(10,4,'添加角色','','sys:role:save','',2,'',1,'2021-01-15 23:02:25','2022-07-13 02:03:08',0),(11,4,'分配角色','','sys:user:role','',2,'',4,'2021-01-17 21:49:58','2022-07-13 02:03:08',1),(12,4,'修改角色','','sys:role:update','',2,'',2,'2021-01-17 21:51:14','2022-07-13 02:03:08',1),(13,4,'删除角色','','sys:role:delete','',2,'',3,'2021-01-17 21:51:39','2022-07-13 02:03:08',1),(14,4,'分配权限','','sys:role:perm','',2,'',5,'2021-01-17 21:52:02','2022-07-13 02:03:08',1),(15,5,'添加菜单','','sys:menu:save','',2,'',1,'2021-01-17 21:53:53','2022-07-13 02:03:08',1),(16,5,'修改菜单','','sys:menu:update','',2,'',2,'2021-01-17 21:56:12','2022-07-13 02:03:08',1),(17,5,'删除菜单','','sys:menu:delete','',2,'',3,'2021-01-17 21:56:36','2022-07-13 02:03:08',1),(18,1,'数字字典','/sys/dicts','sys:dict:list','sys/Dict',1,'el-icon-s-order',1,'2021-01-15 19:07:18','2022-07-13 02:03:08',1);
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(64) NOT NULL COMMENT '角色名',
  `code` varchar(64) NOT NULL COMMENT '角色码',
  `remark` varchar(64) NOT NULL DEFAULT '' COMMENT '角色信息标注',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '角色状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`),
  UNIQUE KEY `unique_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
INSERT INTO `sys_role` (`id`, `name`, `code`, `remark`, `created`, `updated`, `status`) VALUES (1,'超级管理员','admin','系统默认最高权限，不可以编辑和任意修改','2022-07-11 13:00:14','2022-07-13 02:08:22',1),(2,'普通用户','normal','只有基本查看功能','2022-07-11 13:00:14','2022-07-13 02:08:22',1),(3,'test1','p1','能够访问资源p1','2022-07-11 13:00:14','2022-07-11 13:00:14',1),(4,'test2','p2','能够访问资源p2','2022-07-11 13:00:14','2022-07-11 13:00:14',1);
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `role_id` bigint NOT NULL COMMENT '角色Id',
  `menu_id` bigint NOT NULL COMMENT '菜单Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色菜单信息绑定表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`) VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,9),(10,1,10),(11,1,11),(13,1,13),(14,1,14),(15,1,15),(16,1,16),(17,1,17),(18,1,18),(19,2,2),(20,2,3),(21,2,6),(22,2,7),(23,2,8),(24,2,9),(55,1,12);
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像Url',
  `email` varchar(64) NOT NULL DEFAULT '' COMMENT '邮箱',
  `city` varchar(64) NOT NULL DEFAULT '' COMMENT '所在城市',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_login` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次登录时间',
  `status` int NOT NULL DEFAULT '1' COMMENT '账号状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USERNAME` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表(账号状态默认为1)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
INSERT INTO `sys_user` (`id`, `username`, `password`, `avatar`, `email`, `city`, `created`, `updated`, `last_login`, `status`) VALUES (1,'admin','$2a$10$MsD4IFsU5MCylIr5zqn1aurd3Qqr52lA1iSNfuDPJL3OBVxRVxW/2','https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg','123@qq.com','广州','2021-01-12 22:13:53','2022-07-17 13:36:36','2022-07-17 13:36:36',1),(2,'test','$2a$10$DhxBjtDrMGkz8j7OkuVPTueDgyRIlkxPCMl9hQrKm5B6OZ96IqIlC','https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg','test@qq.com','','2021-01-30 08:20:22','2022-07-13 05:16:45','2022-07-13 05:16:45',1),(3,'zhangsan','$2a$10$MsD4IFsU5MCylIr5zqn1aurd3Qqr52lA1iSNfuDPJL3OBVxRVxW/2','','','','2022-07-11 13:07:36','2022-07-11 13:07:36','2022-07-11 13:07:36',1),(4,'lisi','$2a$10$sFVvtT261nlpgVfBQL18legm5BqCPbXdk480P5JCms3tfOB3h34oC','','','','2022-07-11 13:07:36','2022-07-11 13:07:36','2022-07-11 13:07:36',1);
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id\n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色绑定表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES (1,1,1),(2,1,2),(3,2,2),(4,3,3),(5,4,4),(10,1,3),(11,1,4);
UNLOCK TABLES;

--
-- Table structure for table `tb_process`
--

DROP TABLE IF EXISTS `tb_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_process` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(32) NOT NULL DEFAULT '' COMMENT '标题',
  `content` varchar(100) NOT NULL DEFAULT '' COMMENT '内容',
  `address` varchar(50) NOT NULL DEFAULT '' COMMENT '办理地址',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态',
  `phone` varchar(11) NOT NULL DEFAULT '' COMMENT '联系电话',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '配置类型',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `note` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `img` varchar(100) NOT NULL DEFAULT '' COMMENT '地点图片',
  `base_img_path` varchar(100) DEFAULT '' COMMENT '地点图片的根路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='流程配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_process`
--

LOCK TABLES `tb_process` WRITE;
INSERT INTO `tb_process` (`id`, `title`, `content`, `address`, `status`, `phone`, `type`, `start_time`, `end_time`, `note`, `img`, `base_img_path`) VALUES (1,'报到证领取','配置相关内容及办理时间、办理地点等','地点',1,'',1,'2022-07-12 01:51:21','2022-07-12 01:51:21','','1.jpg;2.png;3.gif','/opt/files/img'),(2,'毕业审核','提示：办理地点需上传地点图片','地点',1,'',1,'2022-07-12 01:51:21','2022-07-12 01:51:21','','1.jpg;2.png;3.gif','/opt/files/img'),(3,'欠费缴纳','配置相关内容及办理时间、办理地点等','地点',1,'',1,'2022-07-12 01:51:21','2022-07-12 01:51:21','','1.jpg;2.png;3.gif','/opt/files/img'),(4,'图书归还','提示：办理地点需上传地点图片','地点',1,'',1,'2022-07-12 01:51:21','2022-07-12 01:51:21','','1.jpg;2.png;3.gif','/opt/files/img'),(5,'档案去向确认','配置相关内容及办理时间、办理地点等','地点',1,'',1,'2022-07-12 01:51:21','2022-07-12 01:51:21','','1.jpg;2.png;3.gif','/opt/files/img'),(6,'公寓退宿','提示：办理地点需上传地点图片','地点',1,'',1,'2022-07-12 01:51:21','2022-07-12 01:51:21','','1.jpg;2.png;3.gif','/opt/files/img'),(7,'毕业证领取','配置相关内容及办理时间、办理地点等','地点',1,'',1,'2022-07-12 01:51:21','2022-07-12 01:51:21','','1.jpg;2.png;3.gif','/opt/files/img'),(8,'移动端登录界面','提示：办理地点需上传地点图片','地点',1,'',2,'2022-07-12 01:51:21','2022-07-12 01:51:21','','1.jpg;2.png;3.gif','/opt/files/img');
UNLOCK TABLES;

--
-- Table structure for table `tb_student`
--

DROP TABLE IF EXISTS `tb_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_student` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `student_id` varchar(32) NOT NULL COMMENT '学号',
  `student_name` varchar(32) NOT NULL COMMENT '姓名',
  `student_dep` varchar(32) NOT NULL COMMENT '系部',
  `student_pre` varchar(32) NOT NULL COMMENT '专业',
  `student_class` varchar(32) NOT NULL COMMENT '班级',
  `student_status` tinyint NOT NULL DEFAULT '0' COMMENT '毕业手续完成进度',
  `report_status` tinyint NOT NULL DEFAULT '0' COMMENT '报到证领取状态',
  `report_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报到证领取时间',
  `report_note` varchar(100) NOT NULL DEFAULT '' COMMENT '报到证备注',
  `report_note_status` tinyint NOT NULL DEFAULT '0' COMMENT '是否备注报到证',
  `audit_status` tinyint NOT NULL DEFAULT '0' COMMENT '毕业审核状态',
  `audit_note` varchar(100) NOT NULL DEFAULT '' COMMENT '毕业审核备注',
  `audit_note_status` tinyint NOT NULL DEFAULT '0' COMMENT '是否备注毕业审核',
  `owe_status` tinyint NOT NULL DEFAULT '0' COMMENT '欠费缴纳状态',
  `owe_amount` decimal(7,2) NOT NULL DEFAULT '0.00' COMMENT '欠费金额',
  `owe_note` varchar(100) NOT NULL DEFAULT '' COMMENT '欠费缴纳备注',
  `owe_note_status` tinyint NOT NULL DEFAULT '0' COMMENT '是否备注欠费缴纳',
  `book_status` tinyint NOT NULL DEFAULT '0' COMMENT '图书归还状态',
  `book_amount` int NOT NULL DEFAULT '0' COMMENT '图书待还数量',
  `book_money` decimal(7,2) NOT NULL DEFAULT '0.00' COMMENT '图书欠款',
  `book_note` varchar(100) NOT NULL DEFAULT '' COMMENT '图书归还备注',
  `book_note_status` tinyint NOT NULL DEFAULT '0' COMMENT '是否备注图书归还',
  `archives_status` tinyint NOT NULL DEFAULT '0' COMMENT '档案确认状态',
  `archives_address` varchar(100) NOT NULL DEFAULT '' COMMENT '档案去向',
  `archives_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '档案确认时间',
  `archives_note` varchar(100) NOT NULL DEFAULT '' COMMENT '档案确认备注',
  `archives_note_status` tinyint NOT NULL DEFAULT '0' COMMENT '是否备注档案确认',
  `dormitory_status` tinyint NOT NULL DEFAULT '0' COMMENT '公寓退宿状态',
  `dormitory_note` varchar(100) NOT NULL DEFAULT '' COMMENT '公寓退宿备注',
  `dormitory_note_status` tinyint NOT NULL DEFAULT '0' COMMENT '是否公寓退宿备注',
  `diploma_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '毕业证领取时间',
  `diploma_status` tinyint NOT NULL DEFAULT '0' COMMENT '毕业证领取状态',
  `diploma_note` varchar(100) NOT NULL DEFAULT '' COMMENT '毕业证领取备注',
  `diploma_note_status` tinyint NOT NULL DEFAULT '0' COMMENT '是否备注毕业证领取',
  `note` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_student_student_id_uindex` (`student_id`),
  KEY `tb_student_dep_pre_class` (`student_dep`,`student_pre`,`student_class`),
  KEY `tb_student_name` (`student_name`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生表(36个字段，后续可分表)\n状态码：0-未完成 1-已完成 2-备注';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_student`
--

LOCK TABLES `tb_student` WRITE;
INSERT INTO `tb_student` (`id`, `student_id`, `student_name`, `student_dep`, `student_pre`, `student_class`, `student_status`, `report_status`, `report_time`, `report_note`, `report_note_status`, `audit_status`, `audit_note`, `audit_note_status`, `owe_status`, `owe_amount`, `owe_note`, `owe_note_status`, `book_status`, `book_amount`, `book_money`, `book_note`, `book_note_status`, `archives_status`, `archives_address`, `archives_time`, `archives_note`, `archives_note_status`, `dormitory_status`, `dormitory_note`, `dormitory_note_status`, `diploma_time`, `diploma_status`, `diploma_note`, `diploma_note_status`, `note`) VALUES (1,'163001','张三','计算机','计算机','1',1,1,'2022-07-11 15:41:51','',0,1,'',0,1,0.00,'',0,1,0,0.00,'',0,1,'abc','2022-07-11 15:45:29','',0,1,'',0,'2022-07-11 15:46:01',1,'',0,'a'),(2,'163002','张三四','管理','管理','2',1,1,'2022-07-11 15:41:53','',0,1,'',0,1,0.00,'',0,1,0,0.00,'',0,1,'abc','2022-07-11 15:45:35','',0,1,'',0,'2022-07-11 15:46:00',1,'',0,'b'),(3,'163003','张三五','体育','体育','3',0,1,'2022-07-11 15:41:53','',0,1,'',0,1,0.00,'',0,1,0,0.00,'',0,1,'abc','2022-07-11 15:45:34','',0,1,'',0,'2022-07-11 12:24:04',0,'',0,'c'),(4,'163004','张三','音乐','音乐','4',0,1,'2022-07-11 15:41:53','',0,1,'',0,1,0.00,'',0,1,0,0.00,'',0,1,'abc','2022-07-11 15:45:37','',0,1,'',0,'2022-07-11 12:24:04',0,'',0,'d'),(5,'163005','张三四','中文','中文','1',0,1,'2022-07-11 15:41:53','',0,1,'',0,1,0.00,'',0,1,0,0.00,'',0,1,'abc','2022-07-11 15:46:22','',0,0,'',0,'2022-07-11 12:24:04',0,'',0,'e'),(6,'163006','张三五','计算机','计算机','2',0,1,'2022-07-11 15:41:53','',0,1,'',0,1,0.00,'',0,1,0,0.00,'',0,1,'abc','2022-07-11 15:46:24','',0,0,'',0,'2022-07-11 12:24:04',0,'',0,'f'),(7,'163007','张三','管理','管理','3',0,1,'2022-07-11 15:41:53','',0,1,'',0,1,0.00,'',0,0,0,0.00,'',0,0,'','2022-07-11 12:24:04','',0,0,'',0,'2022-07-11 12:24:04',0,'',0,'g'),(8,'163008','张三四','体育','体育','4',0,1,'2022-07-11 15:41:53','',0,1,'',0,1,0.00,'',0,0,0,0.00,'',0,1,'','2022-07-11 12:24:04','地址改派',1,0,'',0,'2022-07-11 12:24:04',0,'',0,'h'),(9,'163009','张三五','音乐','音乐','1',0,1,'2022-07-11 15:41:53','',0,1,'',0,1,0.00,'',0,0,0,0.00,'',0,1,'','2022-07-11 12:24:04','地址改派',1,0,'',0,'2022-07-11 12:24:04',0,'',0,'i'),(10,'163010','张三','中文','中文','2',0,1,'2022-07-11 15:41:53','',0,1,'',0,1,0.00,'',0,0,0,0.00,'',0,0,'','2022-07-11 12:24:04','',0,0,'',0,'2022-07-11 12:24:04',0,'',0,'j'),(11,'163011','张三四','计算机','计算机','3',0,1,'2022-07-11 15:41:53','',0,1,'',0,0,100.00,'一辈子还不起',1,1,0,0.00,'',0,0,'','2022-07-11 12:24:04','',0,0,'',0,'2022-07-11 12:24:04',0,'',0,'k'),(12,'163012','张三五','管理','管理','4',0,1,'2022-07-11 15:41:53','',0,1,'',0,0,100.00,'一辈子还不起',1,1,0,0.00,'',0,0,'','2022-07-11 12:24:04','',0,0,'',0,'2022-07-11 12:24:04',0,'',0,'l'),(13,'163013','张三','体育','体育','1',0,0,'2022-07-11 12:24:04','',0,0,'',0,0,80.00,'一辈子还不起',1,0,0,0.00,'',0,0,'','2022-07-11 12:24:04','',0,0,'',0,'2022-07-11 12:24:04',0,'',0,'m'),(14,'163014','张三四','音乐','音乐','2',0,0,'2022-07-11 12:24:04','',0,0,'',0,0,80.00,'一辈子还不起',1,0,0,0.00,'',0,0,'','2022-07-11 12:24:04','',0,0,'',0,'2022-07-11 12:24:04',0,'',0,'o');
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-18 16:39:43
