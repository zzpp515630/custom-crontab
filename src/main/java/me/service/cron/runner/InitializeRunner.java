package me.service.cron.runner;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.service.cron.dynamic.DynamicClassFactory;
import me.service.cron.mapper.SystemMapper;
import me.service.cron.model.entity.AppEntity;
import me.service.cron.model.entity.SystemEntity;
import me.service.cron.schema.SchemaComponent;
import me.service.cron.service.AppService;
import me.service.cron.task.DynamicScheduleTask;
import me.service.cron.util.CommandProcess;
import me.service.cron.util.DynamicLoadUtils;
import me.zzpp.dynamic.core.handler.DefaultDynamicClassHandlerImpl;
import me.zzpp.dynamic.core.handler.DynamicClassHandler;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

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

    private final AppService applyService;

    private final DynamicClassFactory dynamicClassFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //创建sql表格
        //数据库版本控制
        schemaComponent.execute();
        //初始化编译器
        initDynamic();
        //初始化任务
        initTask();
        //装载lib
//        initLib();
        //初始化class
        initClass();
    }

    private void initDynamic() {
        SystemEntity systemEntity = systemMapper.selectOne(Wrappers.lambdaQuery(SystemEntity.class));
        if (null == systemEntity || StringUtils.isBlank(systemEntity.getCmdPath())) {
            return;
        }
        String cmdPath = systemEntity.getCmdPath();
        dynamicClassFactory.setDynamicClassHandler(new DefaultDynamicClassHandlerImpl(DynamicClassHandler.CompilerType.Cmd,cmdPath));
    }

    private void initLib() {
        SystemEntity systemEntity = systemMapper.selectOne(Wrappers.lambdaQuery(SystemEntity.class));
        if (systemEntity == null || systemEntity.getCodePath() == null) {
            return;
        }
        String codePath = systemEntity.getCodePath();
        File jarPath = new File(codePath, "lib");
        DynamicLoadUtils.loadPath(jarPath);
    }

    private void initClass() {
        try {
            List<AppEntity> list = applyService.list(Wrappers.emptyWrapper());
            for (AppEntity entity : list) {
                String code = applyService.loadCode(entity);
                applyService.load(code, entity);

            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void initTask() {
        CommandProcess commandProcess = new CommandProcess();
        String commandPrefix = String.join(" ", commandProcess.getCommandPrefix());
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
