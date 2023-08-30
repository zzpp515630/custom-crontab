package me.service.cron.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.service.cron.model.GetResult;
import me.service.cron.model.ListResult;
import me.service.cron.model.Result;
import me.service.cron.model.entity.TaskEntity;
import me.service.cron.model.query.TaskQuery;
import me.service.cron.model.request.CreateTaskRequest;
import me.service.cron.model.request.ModifyTaskRequest;
import me.service.cron.model.response.TaskResponse;

import java.util.List;

/**
 * 描述：
 * 2021/12/13 9:54.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
public interface TaskService extends IService<TaskEntity> {
    /**
     * 创建
     *
     * @param request 请求参数
     * @return
     */
    Result create(CreateTaskRequest request);

    /**
     * 修改
     *
     * @param request 请求参数
     * @return
     */
    Result modify(ModifyTaskRequest request);

    /**
     * 分页查询
     *
     * @param query 查询参数
     * @return
     */
    ListResult<TaskResponse> list(TaskQuery query);

    /**
     * 删除任务
     *
     * @param taskId 任务id
     * @return
     */
    Result delete(Long taskId);

    /**
     * 执行一次
     *
     * @param taskId 任务id
     * @return
     */
    Result execute(Long taskId);
    /**
     * 修改状态
     *
     * @param taskId 任务id
     * @return
     */
    Result running(Long taskId);

    /**
     * 获取详情
     * @param taskId
     * @return
     */
    GetResult<TaskResponse> get(Long taskId);

    /**
     * 最近几次的
     * @param cron
     * @param num
     * @return
     */
    GetResult<List<String>> cronAnalysis(String cron, Integer num);
}
