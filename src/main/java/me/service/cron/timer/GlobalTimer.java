package me.service.cron.timer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.service.cron.service.GlobalService;
import me.service.cron.socker.LogSocketServer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 描述：
 * 2021/12/16 15:29.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class GlobalTimer {

    private final GlobalService globalService;

    /**
     * 每天零点执行器
     */
    @Scheduled(cron = "0 0 0 * * ? ")
    public void beforeDawn() {
        globalService.cleanLog();
    }


    @Scheduled(cron = "0/5 * * * * ?")
    public void print(){
        globalService.print();
    }




}
