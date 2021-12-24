package me.service.cron;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.swing.*;

/**
 * 描述：
 * 2021/12/13 9:42.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@MapperScan("me.service.cron.mapper")
@EnableScheduling
@EnableAsync
@SpringBootApplication
public class CronApplication {

    public static void main(String[] args) {
        SpringApplication.run(CronApplication.class,args);
    }

}
