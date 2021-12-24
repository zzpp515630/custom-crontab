package me.service.cron.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.service.cron.mapper.LogMapper;
import me.service.cron.model.GetResult;
import me.service.cron.model.ListResult;
import me.service.cron.model.entity.LogEntity;
import me.service.cron.model.query.LogQuery;
import me.service.cron.model.response.LogResponse;
import me.service.cron.service.LogService;
import me.service.cron.util.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 2021/12/13 17:44.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, LogEntity> implements LogService {


    @Override
    public ListResult<LogResponse> list(LogQuery query) {
        Page<LogEntity> page = this.page(PageUtils.getPage(query), Wrappers.lambdaQuery(LogEntity.class)
                .select(i->!"executeResult".equals(i.getProperty()))
                .ge(null != query.getStartTime(), LogEntity::getStartTime, query.getStartTime())
                .le(null != query.getEndTime(), LogEntity::getStartTime, query.getEndTime())
                .eq(null != query.getTaskId(), LogEntity::getTaskId, query.getTaskId())
        );
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return new ListResult<>(null, page.getTotal());
        }
        List<LogResponse> responses = new ArrayList<>();
        for (LogEntity entity : page.getRecords()) {
            LogResponse response = new LogResponse();
            BeanUtils.copyProperties(entity, response);
            responses.add(response);
        }
        return new ListResult<>( responses, page.getTotal());
    }

    @Override
    public GetResult<LogResponse> get(Long logId) {
        LogEntity logEntity = this.getById(logId);
        LogResponse response = new LogResponse();
        BeanUtils.copyProperties(logEntity, response);
        return new GetResult<>(response);
    }

    @Override
    public void clean(Long taskId) {
        this.remove(Wrappers.lambdaQuery(LogEntity.class).eq(LogEntity::getTaskId, taskId));
    }
}
