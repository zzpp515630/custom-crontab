package me.service.cron.task;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.service.cron.contents.CommonStatus;
import me.service.cron.exception.TaskException;
import me.service.cron.mapper.TaskMapper;
import me.service.cron.model.entity.TaskEntity;
import me.service.cron.util.CronUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhang peng
 * //1.主要用于标记配置类，兼备Component的效果。
 * // 2.开启定时任务
 */
@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class DynamicScheduleTask implements SchedulingConfigurer {


    private final TaskMapper taskMapper;

    private ScheduledTaskRegistrar taskRegistrar;
//    /**
//     * 线程池
//     */
//    private static final ThreadPoolTaskScheduler THREAD_POOL_TASK_SCHEDULER = new ThreadPoolTaskScheduler();
//
//    /**
//     * 线程
//     */
//    private static final Map<Long, ScheduledFuture<?>> SCHEDULED_FUTURE_MAP = new HashMap<>();

    private static final Map<Long, ScheduledTask> SCHEDULED_MAP = new HashMap<>();


    private static final ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(1);


    /**
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
    }


    public void init() {
        List<TaskEntity> cronEntities = taskMapper.selectList(Wrappers.lambdaQuery(TaskEntity.class).eq(TaskEntity::getStatus, CommonStatus.Start));
        for (TaskEntity taskEntity : cronEntities) {
            createTask(taskEntity);
        }
    }

    public void createTask(TaskEntity entity) {
        log.info("create task:{}", entity.getName());
        Trigger trigger = triggerContext -> new CronTrigger(entity.getCron()).nextExecutionTime(triggerContext);
        TaskExecute taskExecute = new TaskExecute(entity);
        ScheduledTask scheduledTask = taskRegistrar.scheduleTriggerTask(new TriggerTask(taskExecute, trigger));
        SCHEDULED_MAP.put(entity.getId(), scheduledTask);
    }

    public void modifyTask(TaskEntity entity) {
        log.info("modify task:{}", entity.getName());
        cancelTask(entity);
        createTask(entity);
    }

    public void cancelTask(TaskEntity entity) {
        log.info("cancel task:{}", entity.getName());
        ScheduledTask scheduledTask = SCHEDULED_MAP.get(entity.getId());
        if (null != scheduledTask) {
            scheduledTask.cancel();
        }
    }

    public void isValid(String cron) {
        boolean valid = CronUtils.isValid(cron);
        if (!valid) {
            throw new TaskException("无效的cron表达式");
        }
    }

    public void execute(TaskEntity entity) {
        threadPoolExecutor.execute(new TaskExecute(entity));
    }
}