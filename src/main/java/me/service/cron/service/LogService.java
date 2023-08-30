package me.service.cron.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.service.cron.model.GetResult;
import me.service.cron.model.ListResult;
import me.service.cron.model.entity.LogEntity;
import me.service.cron.model.query.LogQuery;
import me.service.cron.model.response.LogResponse;

/**
 * 描述：
 * 2021/12/13 17:44.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
public interface LogService extends IService<LogEntity> {

    /**
     * 分页查询日志列表
     *
     * @param query 查询参数
     * @return
     */
    ListResult<LogResponse> list(LogQuery query);

    /**
     * 查询详情
     * @param logId
     * @return
     */
    GetResult<LogResponse> get(Long logId);

    /**
     * 清空日志
     * @param taskId
     */
    void clean(Long taskId);
}
