package me.service.cron.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.service.cron.contents.CommonStatus;
import me.service.cron.exception.TaskException;
import me.service.cron.mapper.TaskMapper;
import me.service.cron.model.GetResult;
import me.service.cron.model.ListResult;
import me.service.cron.model.Result;
import me.service.cron.model.entity.TaskEntity;
import me.service.cron.model.query.TaskQuery;
import me.service.cron.model.request.CreateTaskRequest;
import me.service.cron.model.request.ModifyTaskRequest;
import me.service.cron.model.response.TaskResponse;
import me.service.cron.service.TaskService;
import me.service.cron.task.DynamicScheduleTask;
import me.service.cron.util.BeanUtil;
import me.service.cron.util.CronUtils;
import me.service.cron.util.CustomOptional;
import me.service.cron.util.PageUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述：
 * 2021/12/13 9:54.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl extends ServiceImpl<TaskMapper, TaskEntity> implements TaskService {

    private final DynamicScheduleTask scheduleTask;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result create(CreateTaskRequest request) {
        scheduleTask.isValid(request.getCron());
        TaskEntity taskEntity = BeanUtil.copyProperties(request, TaskEntity.class);
        taskEntity.setStatus(CommonStatus.Start);
        if (BooleanUtils.isFalse(request.getTimeout())) {
            taskEntity.setTimeout(Boolean.FALSE);
            taskEntity.setExecuteTimeout(0);
        }
        taskEntity.setCompareIgnoreCase(BooleanUtils.isTrue(request.getCompareIgnoreCase()));
        boolean save = this.save(taskEntity);
        if (!save) {
            throw new TaskException("创建定时任务失败");
        }
        execute(taskEntity);
        return Result.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result modify(ModifyTaskRequest request) {
        TaskEntity taskQuery = this.getById(request.getId());
        if (null == taskQuery) {
            throw new TaskException("定时任务不存在");
        }
        CustomOptional.of(request.getCron()).isPresentValue(scheduleTask::isValid);
        TaskEntity taskEntity = BeanUtil.copyProperties(request, TaskEntity.class);
        if (BooleanUtils.isFalse(request.getTimeout())) {
            taskEntity.setTimeout(Boolean.FALSE);
            taskEntity.setExecuteTimeout(0);
        }
        taskEntity.setCompareIgnoreCase(BooleanUtils.isTrue(request.getCompareIgnoreCase()));
        boolean save = this.updateById(taskEntity);
        if (!save) {
            throw new TaskException("修改定时任务失败");
        }
        execute(this.getById(request.getId()));
        return Result.success();
    }

    @Override
    public ListResult<TaskResponse> list(TaskQuery query) {
        Page<TaskEntity> page = this.page(PageUtils.getPage(query), Wrappers.lambdaQuery(TaskEntity.class)
                .like(StringUtils.isNotBlank(query.getName()), TaskEntity::getName, query.getName())
                .eq(null != query.getTaskType(), TaskEntity::getTaskType, query.getTaskType())
        );
        return new ListResult<>(BeanUtil.copyProperties(page.getRecords(), TaskResponse.class), page.getTotal());
    }

    @Override
    public Result delete(Long taskId) {
        TaskEntity taskQuery = this.getById(taskId);
        if (null == taskQuery) {
            throw new TaskException("定时任务不存在");
        }
        taskQuery.setStatus(CommonStatus.Stop);
        execute(taskQuery);
        this.removeById(taskId);
        return Result.success();
    }

    @Override
    public Result execute(Long taskId) {
        TaskEntity taskQuery = this.getById(taskId);
        if (null == taskQuery) {
            throw new TaskException("定时任务不存在");
        }
        scheduleTask.execute(taskQuery);
        return Result.success();
    }

    @Override
    public Result running(Long taskId) {
        TaskEntity taskQuery = this.getById(taskId);
        if (null == taskQuery) {
            throw new TaskException("定时任务不存在");
        }
        CommonStatus status;
        if (CommonStatus.Start == taskQuery.getStatus()) {
            status = CommonStatus.Stop;
        } else {
            status = CommonStatus.Start;
        }
        this.lambdaUpdate().set(TaskEntity::getStatus, status).eq(TaskEntity::getId, taskId).update();
        return Result.success();
    }

    @Override
    public GetResult<TaskResponse> get(Long taskId) {
        TaskEntity entity = this.getById(taskId);
        if (null == entity) {
            throw new TaskException("定时任务不存在");
        }
        TaskResponse response = BeanUtil.copyProperties(entity, TaskResponse.class);
        return new GetResult<>(response);
    }

    @Override
    public GetResult<List<String>> cronAnalysis(String cron, Integer num) {
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        CronUtils.getCron(localDateTimes, num, cron, LocalDateTime.now());
        List<String> collect = localDateTimes.stream().map(dtf2::format).collect(Collectors.toList());
        return new GetResult<>(collect);
    }


    private void execute(TaskEntity taskEntity) {
        if (0 == CommonStatus.Start.compareTo(taskEntity.getStatus())) {
            scheduleTask.modifyTask(taskEntity);
        } else {
            scheduleTask.cancelTask(taskEntity);
        }
    }

}
