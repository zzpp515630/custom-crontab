package me.service.cron.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.service.cron.model.entity.LogEntity;
import me.service.cron.model.entity.SystemEntity;
import me.service.cron.service.GlobalService;
import me.service.cron.service.LogService;
import me.service.cron.service.SystemService;
import me.service.cron.util.CommandProcess;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;

/**
 * 描述：
 * 2021/12/16 15:34.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GlobalServiceImpl implements GlobalService {

    private final SystemService systemService;

    private final LogService logService;

    @Override
    public void cleanLog() {
        SystemEntity systemEntity = systemService.lambdaQuery().one();
        if (null == systemEntity) {
            return;
        }
        Integer logRetainDays = systemEntity.getLogRetainDays();
        if (null == logRetainDays) {
            return;
        }
        LocalDate localDate = LocalDate.now();
        LocalDate lastDate = localDate.plusDays(-1 * logRetainDays);
        long toEpochMilli = lastDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        boolean remove = logService.remove(Wrappers.lambdaQuery(LogEntity.class)
                .eq(LogEntity::getStartTime, toEpochMilli));
        log.info("clean logs retain days:{},remove:{}", logRetainDays, remove);
        new CommandProcess(systemEntity.getCommandPrefix().split(" ")).execute("echo > /var/log/crontab.log");
    }
}
