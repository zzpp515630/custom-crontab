/*
 Navicat Premium Data Transfer

 Source Server         : cron
 Source Server Type    : SQLite
 Source Server Version : 3030001
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3030001
 File Encoding         : 65001

 Date: 21/12/2021 10:32:08
*/
-- ----------------------------
-- Table structure for task
-- ----------------------------
CREATE TABLE "task" (
  "id" INTEGER NOT NULL,
  "name" TEXT(64),
  "description" TEXT(512),
  "cron" TEXT(128) NOT NULL,
  "task_type" TEXT(128),
  "command" TEXT,
  "params" TEXT,
  "timeout" integer(1) DEFAULT 0,
  "execute_timeout" integer(10) DEFAULT 0,
  "status" TEXT(32),
  "compare_type" TEXT(32),
  "compare_ignore_case" integer(10),
  "expected_value" TEXT,
  "email" TEXT,
  "email_message" TEXT,
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Table structure for log
-- ----------------------------
CREATE TABLE "log" (
  "id" integer (64) NOT NULL,
  "name" TEXT (64),
  "description" TEXT (512),
  "cron" TEXT (64),
  "task_id" INTEGER(64),
  "task_name" TEXT(64),
  "task_type" TEXT (128),
  "command" TEXT (32),
  "params" TEXT,
  "timeout" integer(1) DEFAULT 0,
  "execute_timeout" integer (20),
  "compare_type" TEXT (32),
  "compare_ignore_case" integer (20),
  "expected_value" TEXT,
  "email" TEXT,
  "email_message" TEXT,
  "start_time" integer (20),
  "end_time" integer (20),
  "running_timer" integer (20),
  "execute_code" integer (20),
  "execute_result" TEXT,
  "email_result" TEXT,
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Table structure for system
-- ----------------------------
DROP TABLE IF EXISTS "system";
CREATE TABLE "system" (
  "id" INTEGER(64) NOT NULL,
  "subject" TEXT(64),
  "log_retain_days" integer(20),
  "command_prefix" TEXT,
  "email_host" TEXT,
  "email_from_address" TEXT,
  "email_account" TEXT,
  "email_password" TEXT,
  "email_port" TEXT,
  PRIMARY KEY ("id")
);
