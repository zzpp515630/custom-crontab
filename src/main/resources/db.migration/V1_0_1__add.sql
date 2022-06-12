ALTER TABLE "main"."task" RENAME TO "_task_old_20220612";

CREATE TABLE "main"."task" (
                               "id" INTEGER NOT NULL,
                               "name" TEXT(64),
                               "description" TEXT(512),
                               "cron" TEXT(128) NOT NULL,
                               "task_type" TEXT(128),
                               "command" TEXT,
                               "timeout" integer(1) DEFAULT 0,
                               "execute_timeout" integer(10) DEFAULT 0,
                               "status" TEXT(32),
                               "compare_type" TEXT(32),
                               "compare_ignore_case" integer(10),
                               "expected_value" TEXT,
                               "email" TEXT,
                               "email_success_message" TEXT,
                               "email_error_message" TEXT,
                               PRIMARY KEY ("id")
);

INSERT INTO "main"."task" ("id", "name", "description", "cron", "task_type", "command", "timeout", "execute_timeout", "status", "compare_type", "compare_ignore_case", "expected_value", "email", "email_success_message")
SELECT "id", "name", "description", "cron", "task_type", "command", "timeout", "execute_timeout", "status", "compare_type", "compare_ignore_case", "expected_value", "email", "email_message" FROM "main"."_task_old_20220612";

DROP TABLE main._task_old_20220612;