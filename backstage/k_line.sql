/*
 Navicat Premium Data Transfer

 Source Server         : mysql8.0.25
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : k_line

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 01/07/2021 11:42:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_account_info
-- ----------------------------
DROP TABLE IF EXISTS `t_account_info`;
CREATE TABLE `t_account_info`  (
  `ACCOUNT_ID` int NOT NULL AUTO_INCREMENT COMMENT '账户ID',
  `USER_ID` int NOT NULL COMMENT '用户ID',
  `ACCOUNT_MONEY` decimal(20, 10) NOT NULL DEFAULT 0.0000000000 COMMENT '账户资金',
  `ACCOUNT_STATUS` tinyint(1) NOT NULL DEFAULT 0 COMMENT '账户状态 0-正常 1-禁止提现 ',
  `ADD_DATE` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `EDIT_DATE` datetime NULL DEFAULT NULL COMMENT '修改日期',
  `ADD_USER_ID` int NULL DEFAULT NULL COMMENT '创建人',
  `EDIT_USER_ID` int NULL DEFAULT NULL COMMENT '修改人',
  `DEL` tinyint(1) NULL DEFAULT 1 COMMENT '数据状态',
  PRIMARY KEY (`ACCOUNT_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户账户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_account_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_account_record
-- ----------------------------
DROP TABLE IF EXISTS `t_account_record`;
CREATE TABLE `t_account_record`  (
  `RECORD_ID` int NOT NULL COMMENT '账户变动记录ID',
  `ACCOUNT_ID` int NOT NULL COMMENT '账户ID',
  `RECORD_TYPE` tinyint NULL DEFAULT NULL COMMENT '变动类型 1-充值 2-提现 3-买入 4-卖出',
  `CHANGE_MONEY` decimal(20, 10) NOT NULL COMMENT '变动金额',
  `BEFORE_MONEY` decimal(20, 10) NULL DEFAULT NULL COMMENT '变动前金额',
  `AFTER_MONEY` decimal(20, 10) NULL DEFAULT NULL COMMENT '变动后金额',
  `ADD_DATE` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `EDIT_DATE` datetime NULL DEFAULT NULL COMMENT '修改日期',
  `ORDER_ID` int NULL DEFAULT NULL COMMENT '关联订单',
  `SERVICE_CHARGE` decimal(20, 10) NULL DEFAULT NULL COMMENT '手续费',
  `ADD_USER_ID` int NULL DEFAULT NULL COMMENT '创建人',
  `EDIT_USER_ID` int NULL DEFAULT NULL COMMENT '修改人',
  `DEL` tinyint(1) NULL DEFAULT 1 COMMENT '数据状态',
  PRIMARY KEY (`RECORD_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户资金账户变动记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_account_record
-- ----------------------------

-- ----------------------------
-- Table structure for t_bank_card_info
-- ----------------------------
DROP TABLE IF EXISTS `t_bank_card_info`;
CREATE TABLE `t_bank_card_info`  (
  `BANK_CARD_ID` int NOT NULL,
  `USER_ID` int NULL DEFAULT NULL COMMENT '用户ID',
  `BANK_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '银行名称',
  `PROVINCE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '省份',
  `CITY` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '城市名',
  `SUB_BRANCH` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '支行',
  `CARD_OWNER_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '开户名',
  `CARD_NO` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '卡号',
  `CARD_OWNER_NO` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '身份证号',
  `CARD_PHONE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '预留手机号',
  `CARD_STATUS` tinyint(1) NULL DEFAULT NULL COMMENT '卡状态 0-可用 1-禁用',
  `ADD_DATE` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `EDIT_DATE` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `ADD_USER_ID` int NULL DEFAULT NULL COMMENT '创建人',
  `EDIT_USER_ID` int NULL DEFAULT NULL COMMENT '修改人',
  `DEL` tinyint(1) NULL DEFAULT 1 COMMENT '数据状态',
  PRIMARY KEY (`BANK_CARD_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '银行卡信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_bank_card_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_cash_out_in
-- ----------------------------
DROP TABLE IF EXISTS `t_cash_out_in`;
CREATE TABLE `t_cash_out_in`  (
  `CASH_ID` int NOT NULL AUTO_INCREMENT COMMENT '提现/充值ID',
  `ACCOUNT_ID` int NOT NULL COMMENT '账户ID',
  `CASH_TYPE` tinyint(1) NOT NULL COMMENT '类型 1-提现 2-充值',
  `CASH_MONEY` decimal(20, 10) NOT NULL COMMENT '金额',
  `CHECK_STATUS` tinyint(1) NULL DEFAULT NULL COMMENT '审核状态 0-无需审核 1-待审核 2-审核中 3-审核通过 4-审核拒绝',
  `CHECK_DATE` datetime NULL DEFAULT NULL COMMENT '审核时间',
  `CHECK_MAN_USER_ID` int NULL DEFAULT NULL COMMENT '审核人员ID',
  `ADD_DATE` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `EDIT_DATE` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `BANK_CARD_ID` int NULL DEFAULT NULL COMMENT '充值银行卡',
  `EXCHANGE_RATE` decimal(20, 10) NULL DEFAULT NULL COMMENT '充值汇率',
  `ADD_USER_ID` int NULL DEFAULT NULL COMMENT '创建人',
  `EDIT_USER_ID` int NULL DEFAULT NULL COMMENT '修改人',
  `DEL` tinyint(1) NULL DEFAULT 1 COMMENT '数据状态',
  PRIMARY KEY (`CASH_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户资金充值或提现记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_cash_out_in
-- ----------------------------

-- ----------------------------
-- Table structure for t_order_info
-- ----------------------------
DROP TABLE IF EXISTS `t_order_info`;
CREATE TABLE `t_order_info`  (
  `ORDER_ID` int NOT NULL COMMENT '订单ID',
  `ORDER_TYPE` tinyint(1) NULL DEFAULT NULL COMMENT '订单类型 1-买入 2-卖出',
  `USER_ID` int NULL DEFAULT NULL COMMENT '用户ID',
  `ORDER_STATUS` tinyint(1) NULL DEFAULT NULL COMMENT '订单状态 0-预生成 1-已成交 2-已完成',
  `SKU_ID` int NULL DEFAULT NULL COMMENT '商品ID',
  `SKU_CODE` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '商品代码',
  `SKU_QTY` decimal(20, 10) NULL DEFAULT NULL COMMENT '商品数量',
  `SKU_PRICE` decimal(20, 10) NULL DEFAULT NULL COMMENT '商品价格',
  `ORDER_AMOUNT` decimal(20, 10) NULL DEFAULT NULL COMMENT '订单金额',
  `ORDER_CHARGE` decimal(20, 10) NULL DEFAULT NULL COMMENT '订单手续费',
  `ADD_DATE` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `EDIT_DATE` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `ORDER_CYCLE` int NULL DEFAULT NULL COMMENT '订单周期(单位：秒)',
  `INVEST_AMOUNT` decimal(20, 10) NULL DEFAULT NULL COMMENT '投资金额',
  `INVEST_TYPE` tinyint(1) NULL DEFAULT NULL COMMENT '投资方向 1-买涨 2-买亏',
  `EXPECTED_RETURN` decimal(20, 10) NULL DEFAULT NULL COMMENT '预期收益',
  `GUARANTEED_AMOUNT` decimal(20, 10) NULL DEFAULT NULL COMMENT '保底金额',
  `ADD_USER_ID` int NULL DEFAULT NULL COMMENT '创建人',
  `EDIT_USER_ID` int NULL DEFAULT NULL COMMENT '修改人',
  `DEL` tinyint(1) NULL DEFAULT 1 COMMENT '数据状态',
  PRIMARY KEY (`ORDER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '订单信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_order_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_position_info
-- ----------------------------
DROP TABLE IF EXISTS `t_position_info`;
CREATE TABLE `t_position_info`  (
  `POSITION_ID` int NOT NULL COMMENT '持仓ID',
  `USER_ID` int NULL DEFAULT NULL COMMENT '用户ID',
  `POSITION_STATUS` tinyint(1) NULL DEFAULT NULL COMMENT '持仓状态 1-持有 2-已卖出',
  `SKU_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '商品名称',
  `INVEST_TYPE` tinyint(1) NULL DEFAULT NULL COMMENT '投资方向 1-买涨 2-买亏',
  `BEAGIN_PRICE` decimal(20, 10) NULL DEFAULT NULL COMMENT '买入价格',
  `NOW_PRICE` decimal(20, 10) NULL DEFAULT NULL COMMENT '当前价格',
  `END_PRICE` decimal(20, 10) NULL DEFAULT NULL COMMENT '卖出价格',
  `INVEST_AMOUNT` decimal(20, 10) NULL DEFAULT NULL COMMENT '投资金额',
  `BEGIN_DATE` datetime NULL DEFAULT NULL COMMENT '买入时间',
  `END_DATE` datetime NULL DEFAULT NULL COMMENT '卖出时间',
  `INCOME_AMOUNT` decimal(20, 10) NULL DEFAULT NULL COMMENT '收益金额',
  `INCOME_FLAGE` tinyint(1) NULL DEFAULT NULL COMMENT '是否盈利 1-是 0-否',
  `ADD_DATE` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `EDIT_DATE` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `END_AMOUT` decimal(20, 10) NULL DEFAULT NULL COMMENT '结算金额',
  `ADD_USER_ID` int NULL DEFAULT NULL COMMENT '创建人',
  `EDIT_USER_ID` int NULL DEFAULT NULL COMMENT '修改人',
  `DEL` tinyint(1) NULL DEFAULT 1 COMMENT '数据状态',
  PRIMARY KEY (`POSITION_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户持仓信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_position_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_sku_info
-- ----------------------------
DROP TABLE IF EXISTS `t_sku_info`;
CREATE TABLE `t_sku_info`  (
  `SKU_ID` int NOT NULL COMMENT '商品ID',
  `SKU_CODE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '商品代码',
  `SKU_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '商品名称',
  `SKU_STATUS` tinyint(1) NULL DEFAULT NULL COMMENT '商品状态 1-在售 0-停售',
  `NOW_PRICE` decimal(20, 10) NULL DEFAULT NULL COMMENT '现价',
  `MIN_PRICE` decimal(20, 10) NULL DEFAULT NULL COMMENT '最低价',
  `MAX_PRICE` decimal(20, 10) NULL DEFAULT NULL COMMENT '最高价',
  `OPEN_PRICE` decimal(20, 10) NULL DEFAULT NULL COMMENT '开盘价',
  `ADD_DATE` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `EDIT_DATE` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `SKU_TYPE` int NULL DEFAULT NULL COMMENT '订单规格',
  `SKU_CYCLE` int NULL DEFAULT NULL COMMENT '周期(单位：秒)',
  `SKU_INCOME_RATE` decimal(10, 5) NULL DEFAULT NULL COMMENT '收益利率',
  `ADD_USER_ID` int NULL DEFAULT NULL COMMENT '创建人',
  `EDIT_USER_ID` int NULL DEFAULT NULL COMMENT '修改人',
  `DEL` tinyint(1) NULL DEFAULT 1 COMMENT '数据状态',
  PRIMARY KEY (`SKU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '商品信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sku_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_cash_check
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_cash_check`;
CREATE TABLE `t_sys_cash_check`  (
  `ID` int NOT NULL COMMENT '编号',
  `CASH_ID` int NULL DEFAULT NULL COMMENT '提现编号',
  `CASH_RESULT` tinyint(1) NULL DEFAULT NULL COMMENT '审核结果 1-通过 2-拒绝',
  `ADD_DATE` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `ADD_USER_ID` int NULL DEFAULT NULL COMMENT '创建人',
  `EDIT_DATE` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `EDIT_USER_ID` int NULL DEFAULT NULL COMMENT '修改人',
  `DEL` tinyint(1) NULL DEFAULT 1 COMMENT '数据状态',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '后台管理系统提现审核记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_cash_check
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_menu_info
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu_info`;
CREATE TABLE `t_sys_menu_info`  (
  `MENU_ID` int NOT NULL COMMENT '菜单id',
  `MENU_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单名',
  `MENU_DESC` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
  `MENU_URL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路径',
  `MENU_TYPE` tinyint(1) NULL DEFAULT NULL COMMENT '菜单类型',
  `MENU_STATUS` tinyint(1) NULL DEFAULT NULL COMMENT '菜单状态',
  `ADD_DATE` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `ADD_USER_ID` int NULL DEFAULT NULL COMMENT '创建人',
  `EDIT_DATE` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `EDIT_USER_ID` int NULL DEFAULT NULL COMMENT '修改人',
  `SUPER_ID` int NULL DEFAULT NULL COMMENT '上级id',
  `MENU_INDEX` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单结构 ‘-’分割',
  `MENU_LEVEL` tinyint(1) NULL DEFAULT NULL COMMENT '菜单等级',
  `DEL` tinyint(1) NULL DEFAULT 1 COMMENT '数据状态',
  PRIMARY KEY (`MENU_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '后台管理系统菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_menu_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_power
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_power`;
CREATE TABLE `t_sys_power`  (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `ROLE_ID` int NULL DEFAULT NULL COMMENT '角色id',
  `MENU_ID` int NULL DEFAULT NULL COMMENT '菜单id',
  `ADD_DATE` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `ADD_USER_ID` int NULL DEFAULT NULL COMMENT '创建人',
  `EDIT_DATE` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `EDIT_USER_ID` int NULL DEFAULT NULL COMMENT '修改人',
  `DEL` int NULL DEFAULT 0 COMMENT '数据状态',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '后台管理系统角色与菜单对应关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_power
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_rechage_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_rechage_config`;
CREATE TABLE `t_sys_rechage_config`  (
  `ID` int NOT NULL,
  `RECHAGE_URL` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '充值地址',
  `RECHAGE_DESC` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '充值描述',
  `RECHAGE_STATUS` tinyint(1) NULL DEFAULT NULL COMMENT '启用状态 1-可用 0-停用',
  `ADD_DATE` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `ADD_USER_ID` int NULL DEFAULT NULL COMMENT '创建人',
  `EDIT_DATE` datetime NULL DEFAULT NULL COMMENT '修改人',
  `EDIT_USER_ID` int NULL DEFAULT NULL COMMENT '修改时间',
  `DEL` tinyint(1) NULL DEFAULT 1 COMMENT '数据状态',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '后台管理系统充值路径配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_rechage_config
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_role_info
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_info`;
CREATE TABLE `t_sys_role_info`  (
  `ROLE_ID` int NOT NULL COMMENT '角色id',
  `ROLE_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色名称',
  `ROLE_LEVEL` tinyint(1) NULL DEFAULT NULL COMMENT '角色等级',
  `ADD_DATE` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `ADD_USER_ID` int NULL DEFAULT NULL COMMENT '创建人',
  `EDIT_DATE` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `EDIT_USER_ID` int NULL DEFAULT NULL COMMENT '修改人',
  `DEL` tinyint(1) NULL DEFAULT 1 COMMENT '数据状态',
  PRIMARY KEY (`ROLE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '后台管理系统角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_role_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_info`;
CREATE TABLE `t_sys_user_info`  (
  `SYS_USER_ID` int NOT NULL COMMENT '后台用户id',
  `SYS_USER_CODE` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录账号',
  `SYS_USER_PASSWORD` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录密码',
  `SYS_USER_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户名',
  `SYS_USER_PHONE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电话',
  `SYS_ROLE_ID` int NULL DEFAULT NULL COMMENT '角色编号',
  `SYS_USER_IMG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
  `ADD_DATE` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `EDIT_DATE` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `ADD_USER_ID` int NULL DEFAULT NULL COMMENT '创建人',
  `EDIT_USER_ID` int NULL DEFAULT NULL COMMENT '修改人',
  `DEL` tinyint(1) NULL DEFAULT 1 COMMENT '数据状态',
  PRIMARY KEY (`SYS_USER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '后台管理系统用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_sys_user_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info`  (
  `USER_ID` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `USER_NICK_NAME` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户昵称',
  `USER_REAL_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户真实姓名',
  `USER_GENDER` tinyint(1) NOT NULL DEFAULT 1 COMMENT '性别 1-男 0-女 -1未知',
  `USER_PASSWORD` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录密码',
  `USER_HEAD_IMG` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
  `USER_PHONE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号',
  `USER_REFEREE_ID` int NULL DEFAULT NULL COMMENT '推荐人ID',
  `USER_TYPE` tinyint NOT NULL DEFAULT 1 COMMENT '用户类别 1-普通用户 2-代理用户',
  `USER_LEVEL` tinyint NOT NULL DEFAULT 1 COMMENT '用户等级 1-普通级',
  `USER_CARD_NO` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户身份证号',
  `USER_REGISTER_DATE` datetime NULL DEFAULT NULL COMMENT '用户注册时间',
  `USER_FORBID_FLAG` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否禁用 0-未禁用 1-已禁用',
  `USER_REFEREE_CODE` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '推荐码',
  `USER_REFEREE_ABLE` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有推荐资格 1-有 0-无',
  `ADD_DATE` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `ADD_USER_ID` int NULL DEFAULT NULL COMMENT '创建人',
  `EDIT_DATE` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `EDIT_USER_ID` int NULL DEFAULT NULL COMMENT '修改人',
  `LAST_LOGIN_DATE` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `DEL_STATUS` tinyint(1) NOT NULL DEFAULT 0 COMMENT '注销状态 0-未注销 1-主动注销 2-强制注销',
  `DEL` tinyint(1) NULL DEFAULT 1 COMMENT '数据状态',
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_log
-- ----------------------------
DROP TABLE IF EXISTS `t_user_log`;
CREATE TABLE `t_user_log`  (
  `ID` int NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `USER_ID` int NULL DEFAULT NULL COMMENT '用户ID',
  `DESCRIPTION` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作描述',
  `IP` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求IP',
  `REQUEST_URL` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求地址',
  `MODEL_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块名称',
  `ACTION_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作名称',
  `ORIGINAL_PARAMS` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '原始请求参数',
  `PARAMS` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求参数',
  `EXECUTE_TIME` decimal(10, 0) NOT NULL COMMENT '执行时长-毫秒',
  `RESPONSE_RESULT` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请求结果',
  `EXECUTE_STATUS` tinyint(1) NOT NULL COMMENT '1-成功/0-失败',
  `CREATE_TIME` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
