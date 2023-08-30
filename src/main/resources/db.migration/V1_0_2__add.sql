ALTER TABLE "main"."log" RENAME TO "_log_old_20220612";

CREATE TABLE "main"."log" (
                              "id" integer (64) NOT NULL,
                              "name" TEXT (64),
                              "description" TEXT (512),
                              "cron" TEXT (64),
                              "task_id" INTEGER(64),
                              "task_name" TEXT(64),
                              "task_type" TEXT (128),
                              "command" TEXT (32),
                              "timeout" integer(1) DEFAULT 0,
                              "execute_timeout" integer (20),
                              "compare_type" TEXT (32),
                              "compare_ignore_case" integer (20),
                              "expected_value" TEXT,
                              "email" TEXT,
                              "email_success_message" TEXT,
                              "email_error_message" TEXT,
                              "start_time" integer (20),
                              "end_time" integer (20),
                              "running_timer" integer (20),
                              "execute_code" integer (20),
                              "execute_result" TEXT,
                              "email_result" TEXT,
                              PRIMARY KEY ("id")
);

INSERT INTO "main"."log" ("id", "name", "description", "cron", "task_id", "task_name", "task_type", "command", "timeout", "execute_timeout", "compare_type", "compare_ignore_case", "expected_value", "email", "email_success_message", "start_time", "end_time", "running_timer", "execute_code", "execute_result", "email_result")
SELECT "id", "name", "description", "cron", "task_id", "task_name", "task_type", "command", "timeout", "execute_timeout", "compare_type", "compare_ignore_case", "expected_value", "email", "email_message", "start_time", "end_time", "running_timer", "execute_code", "execute_result", "email_result" FROM "main"."_log_old_20220612";


DROP TABLE main._log_old_20220612;