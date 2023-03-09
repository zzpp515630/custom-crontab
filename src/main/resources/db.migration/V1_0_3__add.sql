CREATE TABLE "app" (
                         "id" INTEGER NOT NULL,
                         "name" TEXT(64) NOT NULL,
                         "description" TEXT(512),
                         "java_name" TEXT(128) NOT NULL,
                         "quote" integer(1),
                         PRIMARY KEY ("id")
);


ALTER TABLE "main"."system" RENAME TO "_log_old_20230307";

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
                          "code_path" TEXT,
                          PRIMARY KEY ("id")
);

INSERT INTO "main"."system" ("id", "subject", "log_retain_days", "command_prefix", "email_host", "email_from_address", "email_account", "email_password", "email_port")
SELECT "id", "subject", "log_retain_days", "command_prefix", "email_host", "email_from_address", "email_account", "email_password", "email_port" FROM "main"."_log_old_20230307";


DROP TABLE main._log_old_20230307;