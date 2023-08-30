package me.service.cron.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.service.cron.model.entity.LogEntity;
import me.service.cron.model.entity.SystemEntity;
import me.service.cron.service.GlobalService;
import me.service.cron.service.LogService;
import me.service.cron.service.SystemService;
import me.service.cron.socker.LogSocketServer;
import me.service.cron.util.CommandProcess;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

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
                .le(LogEntity::getStartTime, toEpochMilli));
        log.info("clean logs retain days:{},remove:{}", logRetainDays, remove);
        new CommandProcess(systemEntity.getCommandPrefix().split(" ")).execute("echo > /var/log/crontab.log");
    }

    @SneakyThrows
    @Override
    public void print() {
        SystemEntity systemEntity = systemService.lambdaQuery().one();
        if (null == systemEntity) {
            return;
        }
//        Pair<Integer, String> execute = new CommandProcess(systemEntity.getCommandPrefix().split(" "))
//                .execute("cat /var/log/crontab.log");
        FileInputStream fileInputStream = new FileInputStream("/var/log/crontab.log");
        List list = IOUtils.readLines(fileInputStream);
        IOUtils.closeQuietly(fileInputStream);
//        LogSocketServer.sendMessage(execute.getValue());
        LogSocketServer.sendMessage(String.join("\n",list));
    }
}
