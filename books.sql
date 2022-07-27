/*
 Navicat Premium Data Transfer

 Source Server         : 0506
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : books

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 27/07/2022 10:08:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `b_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `b_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `b_publisher` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `b_ISBN` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `b_price` decimal(10, 2) NULL DEFAULT NULL,
  `c_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`b_id`) USING BTREE,
  INDEX `fk_b_c`(`c_id`) USING BTREE,
  CONSTRAINT `fk_b_c` FOREIGN KEY (`c_id`) REFERENCES `classify` (`c_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, 'javaScript高级程序设计', '人民邮电出版社', '9787115152091', 59.00, 1);
INSERT INTO `book` VALUES (2, 'c语言程序设计', '清华大学出版社', '9000302000778', 78.00, 1);
INSERT INTO `book` VALUES (3, '元素的盛宴', '接力出版社', '9787544841658 ', 45.00, 2);
INSERT INTO `book` VALUES (4, '未完成的进化', '中信前沿出版社', '9787508681757', 51.80, 3);
INSERT INTO `book` VALUES (5, '现代分子生物学技术与实验技巧', '化学工业出版社', '9787122245021', 113.20, 3);

-- ----------------------------
-- Table structure for classify
-- ----------------------------
DROP TABLE IF EXISTS `classify`;
CREATE TABLE `classify`  (
  `c_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `c_cname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `u_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`c_id`) USING BTREE,
  INDEX `fk_c_u`(`u_id`) USING BTREE,
  CONSTRAINT `fk_c_u` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of classify
-- ----------------------------
INSERT INTO `classify` VALUES (1, '计算机类', 1);
INSERT INTO `classify` VALUES (2, '化学类', 1);
INSERT INTO `classify` VALUES (3, '生物类', 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `u_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `u_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `u_pwd` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`u_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '123');
INSERT INTO `user` VALUES (2, 'test', '456');

SET FOREIGN_KEY_CHECKS = 1;
