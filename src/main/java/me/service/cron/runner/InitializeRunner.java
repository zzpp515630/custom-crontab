package me.service.cron.runner;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.service.cron.mapper.SystemMapper;
import me.service.cron.model.entity.SystemEntity;
import me.service.cron.schema.SchemaComponent;
import me.service.cron.task.DynamicScheduleTask;
import me.service.cron.util.CommandProcess;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 描述：
 * 2021/12/17 17:17.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class InitializeRunner implements ApplicationRunner {

    private final SystemMapper systemMapper;


    private final SchemaComponent schemaComponent;

    private final DynamicScheduleTask dynamicScheduleTask;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        CommandProcess commandProcess = new CommandProcess();
        String commandPrefix = String.join(" ", commandProcess.getCommandPrefix());
        schemaComponent.execute();
        SystemEntity systemEntity = systemMapper.selectOne(Wrappers.lambdaQuery(SystemEntity.class));
        if (null == systemEntity) {
            systemEntity = new SystemEntity();
            systemEntity.setLogRetainDays(7);
            systemEntity.setCommandPrefix(commandPrefix);
            systemEntity.setSubject("crontab");
            systemMapper.insert(systemEntity);
        }
        dynamicScheduleTask.init();
    }
}
