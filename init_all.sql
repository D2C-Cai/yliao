/*
 Navicat Premium Data Transfer

 Source Server         : shop146
 Source Server Type    : MySQL
 Source Server Version : 50642
 Source Host           : 192.168.0.146:3306
 Source Schema         : store

 Target Server Type    : MySQL
 Target Server Version : 50642
 File Encoding         : 65001

 Date: 31/05/2019 15:31:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for BASE
-- ----------------------------
DROP TABLE IF EXISTS `BASE`;
CREATE TABLE `BASE`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for BASE_DEL
-- ----------------------------
DROP TABLE IF EXISTS `BASE_DEL`;
CREATE TABLE `BASE_DEL`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `deleted` tinyint(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for CORE_P2P
-- ----------------------------
DROP TABLE IF EXISTS `CORE_P2P`;
CREATE TABLE `CORE_P2P`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `deleted` tinyint(2) NULL DEFAULT NULL,
  `secret` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密钥',
  `name` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `company_name` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '企业名称',
  `website` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '网站地址',
  `mobile` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系方式',
  `banner` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '首页轮播',
  `notify_url` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '回调地址',
  `sales_amount` decimal(20, 2) NULL DEFAULT NULL COMMENT '销售金额',
  `min_amount` decimal(20, 2) NULL DEFAULT NULL COMMENT '最低消费金额',
  `diff_amount` decimal(20, 2) NULL DEFAULT NULL COMMENT '允许偏差金额',
  `oauth_time` int(10) NULL DEFAULT NULL COMMENT '授权有效期-小时',
  `customer_id` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '法大大客户编号',
  `credit_code` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '信用代码',
  `credit_code_file` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '信用代码电子版',
  `power_attorney_file` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '委托书电子版',
  `legal_name` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '法人名字',
  `address` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `sign_id` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '签章图片',
  `legal_customer_id` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '法人客户编号',
  `evidence_no` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '企业存证编号',
  `identity` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '法人身份证',
  `template_id` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '模板ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for LOG_SMS
-- ----------------------------
DROP TABLE IF EXISTS `LOG_SMS`;
CREATE TABLE `LOG_SMS`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `mobile` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机号',
  `code` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '验证码',
  `ip` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `deadline` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  `status` tinyint(2) NULL DEFAULT NULL COMMENT '状态 0,1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_unique`(`mobile`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for M_ACCOUNT
-- ----------------------------
DROP TABLE IF EXISTS `M_ACCOUNT`;
CREATE TABLE `M_ACCOUNT`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `deleted` tinyint(2) NULL DEFAULT NULL,
  `p2p_id` bigint(20) NULL DEFAULT NULL COMMENT '平台ID',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT '会员ID',
  `oauth_amount` decimal(20, 2) NULL DEFAULT NULL COMMENT '授权金额',
  `deadline` datetime(0) NULL DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for M_ADDRESS
-- ----------------------------
DROP TABLE IF EXISTS `M_ADDRESS`;
CREATE TABLE `M_ADDRESS`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT '会员ID',
  `member_account` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员账号',
  `code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '行政编码',
  `province` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '城市',
  `district` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '区县',
  `address` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `name` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机',
  `defaults` tinyint(2) NULL DEFAULT NULL COMMENT '默认',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_member_id`(`member_id`) USING BTREE,
  INDEX `idx_defaults`(`defaults`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for M_MEMBER
-- ----------------------------
DROP TABLE IF EXISTS `M_MEMBER`;
CREATE TABLE `M_MEMBER`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `deleted` tinyint(2) NULL DEFAULT NULL,
  `account` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '昵称',
  `identity` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '身份证',
  `avatar` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `access_token` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '令牌',
  `access_expired` datetime(0) NULL DEFAULT NULL COMMENT '令牌时效',
  `register_ip` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '注册IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `login_ip` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  `status` tinyint(2) NULL DEFAULT NULL COMMENT '状态 1,0',
  `sex` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '性别',
  `customer_id` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '法大大客户编号',
  `evidence_no` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '个人存证编号',
  `sign_id` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '签章ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_unique`(`account`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for O_CART_ITEM
-- ----------------------------
DROP TABLE IF EXISTS `O_CART_ITEM`;
CREATE TABLE `O_CART_ITEM`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `p2p_id` bigint(20) NULL DEFAULT NULL COMMENT '平台ID',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT '会员ID',
  `member_account` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员账号',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  `product_sku_id` bigint(20) NULL DEFAULT NULL COMMENT '商品SKU的ID',
  `quantity` int(10) NULL DEFAULT NULL COMMENT '商品数量',
  `standard` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品规格',
  `product_name` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品名称',
  `product_pic` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品图片',
  `product_price` decimal(16, 2) NULL DEFAULT NULL COMMENT '商品单价',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_member_shop_id`(`p2p_id`, `member_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for O_ORDER
-- ----------------------------
DROP TABLE IF EXISTS `O_ORDER`;
CREATE TABLE `O_ORDER`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `deleted` tinyint(2) NULL DEFAULT NULL,
  `p2p_id` bigint(20) NULL DEFAULT NULL COMMENT '平台ID',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT '会员ID',
  `member_account` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员账号',
  `province` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '城市',
  `district` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '区县',
  `address` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `name` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '手机',
  `mark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `sn` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单号',
  `type` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  `status` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '状态',
  `product_amount` decimal(16, 2) NULL DEFAULT NULL COMMENT '商品总价',
  `freight_amount` decimal(16, 2) NULL DEFAULT NULL COMMENT '运费价格',
  `pay_amount` decimal(16, 2) NULL DEFAULT NULL COMMENT '实际支付',
  `contract_id` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '合同编号',
  `mem_sign_date` datetime(0) NULL DEFAULT NULL COMMENT '用户签约时间',
  `p2p_sign_date` datetime(0) NULL DEFAULT NULL COMMENT 'P2P签约时间',
  `cus_sign_date` datetime(0) NULL DEFAULT NULL COMMENT '电商签约时间',
  `close_date` datetime(0) NULL DEFAULT NULL COMMENT '订单关闭时间',
  `send_p2p` tinyint(1) NULL DEFAULT NULL COMMENT '是否推送p2p',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_sn`(`sn`) USING BTREE,
  INDEX `idx_type`(`type`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for O_ORDER_ITEM
-- ----------------------------
DROP TABLE IF EXISTS `O_ORDER_ITEM`;
CREATE TABLE `O_ORDER_ITEM`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `deleted` tinyint(2) NULL DEFAULT NULL,
  `p2p_id` bigint(20) NULL DEFAULT NULL COMMENT '平台ID',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT '会员ID',
  `member_account` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '会员账号',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  `product_sku_id` bigint(20) NULL DEFAULT NULL COMMENT '商品SKU的ID',
  `quantity` int(10) NULL DEFAULT NULL COMMENT '商品数量',
  `standard` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品规格',
  `product_name` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品名称',
  `product_pic` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品图片',
  `sku_sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'SKU条码',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单ID',
  `order_sn` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单号',
  `brand_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌ID',
  `supplier_id` bigint(20) NULL DEFAULT NULL COMMENT '供应商ID',
  `cost_price` decimal(16, 2) NULL DEFAULT NULL COMMENT '成本价',
  `ratio` decimal(16, 2) NULL DEFAULT NULL COMMENT '结算比例',
  `type` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  `status` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '状态',
  `product_price` decimal(16, 2) NULL DEFAULT NULL COMMENT '商品单价',
  `real_price` decimal(16, 2) NULL DEFAULT NULL COMMENT '实时单价',
  `freight_amount` decimal(16, 2) NULL DEFAULT NULL COMMENT '运费价格',
  `pay_amount` decimal(16, 2) NULL DEFAULT NULL COMMENT '实际支付',
  `logistics_com` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物流公司',
  `logistics_num` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物流单号',
  `delivered_date` datetime(0) NULL DEFAULT NULL COMMENT '发货时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_sn`(`order_sn`) USING BTREE,
  INDEX `idx_type`(`type`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for P_BRAND
-- ----------------------------
DROP TABLE IF EXISTS `P_BRAND`;
CREATE TABLE `P_BRAND`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for P_FREIGHT
-- ----------------------------
DROP TABLE IF EXISTS `P_FREIGHT`;
CREATE TABLE `P_FREIGHT`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `formula` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '公式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for P_P2P_PRODUCT
-- ----------------------------
DROP TABLE IF EXISTS `P_P2P_PRODUCT`;
CREATE TABLE `P_P2P_PRODUCT`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `p2p_id` bigint(20) NULL DEFAULT NULL COMMENT '平台ID',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_unique`(`p2p_id`, `product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for P_PRODUCT
-- ----------------------------
DROP TABLE IF EXISTS `P_PRODUCT`;
CREATE TABLE `P_PRODUCT`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `deleted` tinyint(2) NULL DEFAULT NULL,
  `sn` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '款号',
  `name` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `pic` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片',
  `price` decimal(16, 2) NULL DEFAULT NULL COMMENT '销售价',
  `market_price` decimal(16, 2) NULL DEFAULT NULL COMMENT '市场价',
  `stock` int(10) NULL DEFAULT NULL COMMENT '商品库存',
  `sales` int(10) NULL DEFAULT NULL COMMENT '商品销量',
  `freight` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '运费模板',
  `ratio` decimal(16, 2) NULL DEFAULT NULL COMMENT '结算比例',
  `brand_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌ID',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '类目ID',
  `supplier_id` bigint(20) NULL DEFAULT NULL COMMENT '供应商ID',
  `description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `status` int(10) NULL DEFAULT NULL COMMENT '状态 1,0',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category_id`(`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for P_PRODUCT_CATEGORY
-- ----------------------------
DROP TABLE IF EXISTS `P_PRODUCT_CATEGORY`;
CREATE TABLE `P_PRODUCT_CATEGORY`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `pic` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片',
  `icon` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '首页图标',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父级ID',
  `level` int(10) NULL DEFAULT NULL COMMENT '级别',
  `sort` int(10) NULL DEFAULT NULL COMMENT '排序',
  `display` tinyint(1) NULL DEFAULT NULL COMMENT '首页显示 0,1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for P_PRODUCT_DETAIL
-- ----------------------------
DROP TABLE IF EXISTS `P_PRODUCT_DETAIL`;
CREATE TABLE `P_PRODUCT_DETAIL`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for P_PRODUCT_SKU
-- ----------------------------
DROP TABLE IF EXISTS `P_PRODUCT_SKU`;
CREATE TABLE `P_PRODUCT_SKU`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `deleted` tinyint(2) NULL DEFAULT NULL,
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  `brand_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌ID',
  `supplier_id` bigint(20) NULL DEFAULT NULL COMMENT '供应商ID',
  `sn` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '条码',
  `standard` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '规格',
  `cost_price` decimal(16, 2) NULL DEFAULT NULL COMMENT '成本价',
  `sell_price` decimal(16, 2) NULL DEFAULT NULL COMMENT '销售价',
  `market_price` decimal(16, 2) NULL DEFAULT NULL COMMENT '市场价',
  `status` tinyint(2) NULL DEFAULT NULL COMMENT '状态 1,0',
  `stock` int(10) NULL DEFAULT NULL COMMENT '库存',
  `freight` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '运费',
  `ratio` decimal(16, 2) NULL DEFAULT NULL COMMENT '结算比例',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id`(`product_id`) USING BTREE,
  INDEX `idx_stock`(`stock`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for P_SUPPLIER
-- ----------------------------
DROP TABLE IF EXISTS `P_SUPPLIER`;
CREATE TABLE `P_SUPPLIER`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `mobile` varchar(180) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for SYS_MENU
-- ----------------------------
DROP TABLE IF EXISTS `SYS_MENU`;
CREATE TABLE `SYS_MENU`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `name` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `path` varchar(180) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `route` varchar(180) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `logo` varchar(180) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `assembly` varchar(180) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(60) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `sort` int(10) NULL DEFAULT NULL,
  `status` tinyint(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_unique`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for SYS_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE`;
CREATE TABLE `SYS_ROLE`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `code` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `name` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_unique`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for SYS_ROLE_MENU
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ROLE_MENU`;
CREATE TABLE `SYS_ROLE_MENU`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `role_id` bigint(20) NULL DEFAULT NULL,
  `menu_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_unique`(`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for SYS_USER
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER`;
CREATE TABLE `SYS_USER`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `deleted` tinyint(2) NULL DEFAULT NULL,
  `username` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `password` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `status` tinyint(2) NULL DEFAULT NULL,
  `access_token` varchar(180) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `access_expired` datetime(0) NULL DEFAULT NULL,
  `register_ip` varchar(180) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `login_date` datetime(0) NULL DEFAULT NULL,
  `login_ip` varchar(180) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `p2p_id` bigint(20) NULL DEFAULT NULL,
  `supplier_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_unique`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for SYS_USER_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `SYS_USER_ROLE`;
CREATE TABLE `SYS_USER_ROLE`  (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `create_man` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `modify_date` datetime(0) NULL DEFAULT NULL,
  `modify_man` varchar(120) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `role_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_unique`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
