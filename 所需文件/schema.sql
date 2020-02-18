/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : schema

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2020-02-19 06:41:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `direction`
-- ----------------------------
DROP TABLE IF EXISTS `direction`;
CREATE TABLE `direction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `subject_id` int(11) NOT NULL,
  `status` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `subject_id` (`subject_id`),
  CONSTRAINT `direction_ibfk_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of direction
-- ----------------------------
INSERT INTO `direction` VALUES ('1000000', '软件开发', '2', 'active');
INSERT INTO `direction` VALUES ('1000001', '软件测试', '2', 'active');

-- ----------------------------
-- Table structure for `reset_pwd`
-- ----------------------------
DROP TABLE IF EXISTS `reset_pwd`;
CREATE TABLE `reset_pwd` (
  `email` varchar(32) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  `pwdkey` varchar(255) DEFAULT '',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(1) DEFAULT 1,
  `pwdtime` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reset_pwd
-- ----------------------------
INSERT INTO `reset_pwd` VALUES ('943490180@qq.com', '1009', '625c2767efec4b1fb64ea4ad094bfaf1', '59', '0', '2020-01-11 21:55:17');
INSERT INTO `reset_pwd` VALUES ('943490180@qq.com', '1009', '16c7caf650914c9c814981bdb0a8d4c0', '60', '1', '2020-01-11 21:56:40');
INSERT INTO `reset_pwd` VALUES ('1234567@qq.com', '100001', '439680cd03f1452388b489933bfd3f3f', '61', '1', '2020-01-14 09:22:44');
INSERT INTO `reset_pwd` VALUES ('943490180@qq.com', '1009', 'a1435bb77a4946fb8a48784ed0c5c964', '62', '0', '2020-01-14 09:22:55');

-- ----------------------------
-- Table structure for `staff`
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `staff_no` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL,
  `telephone` varchar(32) DEFAULT NULL,
  `gender` varchar(32) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `academic_rank` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`staff_no`),
  CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`staff_no`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES ('100001', '秦老师', '15806675105', '男', '1976-01-01', '副教授');
INSERT INTO `staff` VALUES ('100002', '王老师', '15806675105', '女', '1978-01-01', '副教授');
INSERT INTO `staff` VALUES ('100003', '赵老师', '15806675105', '女', '1976-01-01', '副教授');
INSERT INTO `staff` VALUES ('1009', 'wang ', '111', '男', '2019-11-25', '9090');
INSERT INTO `staff` VALUES ('110101', '李恒斐', '15806675105', '男', '1984-01-01', '讲师');
INSERT INTO `staff` VALUES ('18189', '问问', '1898989', '男', '2019-12-02', '1898');

-- ----------------------------
-- Table structure for `student`
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `student_no` varchar(32) NOT NULL,
  `name` varchar(255) NOT NULL,
  `telephone` varchar(32) DEFAULT NULL,
  `gender` varchar(32) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `class_id` int(11) NOT NULL,
  PRIMARY KEY (`student_no`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `student_class` (`id`),
  CONSTRAINT `student_ibfk_2` FOREIGN KEY (`student_no`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('20141113003', '巩同学', '13812345678', '女', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113004', '沈同学', '13812345678', '女', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113005', '郑', '13812345678', '女', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113006', '陈', '13812345678', '女', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113007', '苏珊', '13812345678', '女', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113008', '苗', '13812345678', '女', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113009', '荣', '13812345678', '女', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113010', '高', '13812345678', '女', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113011', '张同学', '13812345678', '男', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113012', '李同学', '13812345678', '男', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113013', '王同学', '13812345678', '男', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113014', '许同学', '13812345678', '男', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113015', '刘成', '13812345678', '男', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113016', '陈刚', '13812345678', '男', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113017', '苏阳', '13812345678', '男', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113018', '张兴', '13812345678', '男', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113019', '荣', '13812345678', '男', '1999-01-01', '1');
INSERT INTO `student` VALUES ('20141113020', '高睦', '13812345678', '男', '1999-01-01', '1');
INSERT INTO `student` VALUES ('201611105057', '王冠锐', '15628931723', '男', '2020-01-14', '3');

-- ----------------------------
-- Table structure for `student_class`
-- ----------------------------
DROP TABLE IF EXISTS `student_class`;
CREATE TABLE `student_class` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `enroll_year` varchar(32) NOT NULL,
  `subject_id` int(11) NOT NULL,
  `direction_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `subject_id` (`subject_id`),
  KEY `direction_id` (`direction_id`),
  CONSTRAINT `student_class_ibfk_1` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`),
  CONSTRAINT `student_class_ibfk_2` FOREIGN KEY (`direction_id`) REFERENCES `direction` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_class
-- ----------------------------
INSERT INTO `student_class` VALUES ('1', '计科151', '2015', '1', null);
INSERT INTO `student_class` VALUES ('2', '软件141', '2014', '2', '1');
INSERT INTO `student_class` VALUES ('3', '软测141', '2014', '2', '2');
INSERT INTO `student_class` VALUES ('4', '软测142', '2014', '2', '2');
INSERT INTO `student_class` VALUES ('5', '软测143', '2014', '2', '2');

-- ----------------------------
-- Table structure for `subject`
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `status` varchar(32) NOT NULL,
  `director_no` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `director_no` (`director_no`),
  CONSTRAINT `subject_ibfk_1` FOREIGN KEY (`director_no`) REFERENCES `staff` (`staff_no`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES ('1', '计算机科学与技术', 'active', '100001');
INSERT INTO `subject` VALUES ('2', '软件工程', 'active', '100002');
INSERT INTO `subject` VALUES ('3', '网络工程', 'active', '100003');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` varchar(32) NOT NULL,
  `password` varchar(256) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('100001', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '1234567@qq.com', '1');
INSERT INTO `user` VALUES ('100002', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '44903464@qq.com', '1');
INSERT INTO `user` VALUES ('100003', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '44903464@qq.com', '1');
INSERT INTO `user` VALUES ('1009', '263DY2f2', '943490180@qq.com', '1');
INSERT INTO `user` VALUES ('110101', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '44903464@qq.com', '1');
INSERT INTO `user` VALUES ('18189', '$2a$10$RZJl/9Fya/kaEcko0IMumeHwzHdPGzor5tCSL9fyr7hIn73b6IYry', '9434901180@qq.com', '1');
INSERT INTO `user` VALUES ('20141113003', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113003@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113004', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113004@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113005', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113005@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113006', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113006@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113007', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113007@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113008', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113008@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113009', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113009@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113010', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113010@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113011', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113011@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113012', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113012@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113013', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113013@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113014', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113014@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113015', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113015@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113016', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113016@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113017', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113017@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113018', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113018@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113019', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113019@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('20141113020', '$2a$10$yKvUWmiBlcQTzqeRZeZf4OSQe6GjmHJYhL3Y1Crm0YE10Rf2G075K', '20141113020@sdjzu.edu.cn', '1');
INSERT INTO `user` VALUES ('201611105057', '$2a$10$7cIzUchqiO.cI26M4QCwoexM9po/V0r4vOLXvkDCYk1pO52dBCb0O', '94349011180@qq.com', '1');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `username` varchar(32) DEFAULT NULL,
  `role` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('20141113001', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113001', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113001', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113002', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113003', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113004', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113005', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113006', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113007', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113008', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113009', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113010', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113011', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113012', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113013', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113014', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113015', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113016', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113017', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113018', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113019', 'STUDENT');
INSERT INTO `user_role` VALUES ('20141113020', 'STUDENT');
INSERT INTO `user_role` VALUES ('110101', 'STAFF');
INSERT INTO `user_role` VALUES ('100001', 'STAFF');
INSERT INTO `user_role` VALUES ('100002', 'STAFF');
INSERT INTO `user_role` VALUES ('100003', 'STAFF');
