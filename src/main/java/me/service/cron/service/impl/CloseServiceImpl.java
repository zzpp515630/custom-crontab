package me.service.cron.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.service.cron.service.CloseService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 描述：
 * 2021/12/21 16:17.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Service
@Slf4j
public class CloseServiceImpl implements CloseService {
    @Override
    @Async
    public void close() {
        //TODO code
        System.exit(0);
    }
}
