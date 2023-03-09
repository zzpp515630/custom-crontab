package me.service.cron.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.service.cron.contents.TaskType;
import me.service.cron.model.GetResult;
import me.service.cron.model.ListResult;
import me.service.cron.model.Result;
import me.service.cron.model.entity.ApplyEntity;
import me.service.cron.model.query.ApplyQuery;
import me.service.cron.model.request.CreateApplyRequest;
import me.service.cron.model.request.ModifyApplyRequest;
import me.service.cron.model.response.ApplyDetailResponse;
import me.service.cron.model.response.ApplyResponse;

/**
 * 描述：
 * 2021/12/21 16:32.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
public interface ApplyService extends IService<ApplyEntity> {
    /**
     * 创建
     *
     * @param request
     * @return
     */
    Result create(CreateApplyRequest request) throws Exception;

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Result modify(ModifyApplyRequest request) throws Exception;

    /**
     * 删除应用
     *
     * @param applyId
     * @return
     */
    Result remove(Long applyId);


    /**
     * 查询详情
     *
     * @param applyId
     * @return
     */
    GetResult<ApplyDetailResponse> get(Long applyId) throws Exception;

    /**
     * 查询列表
     *
     * @param query
     * @return
     */
    ListResult<ApplyResponse> list(ApplyQuery query);

    /**
     * 运行状态判断
     *
     * @param applyId
     * @return
     */
    Result running(Long applyId);

    /**
     * 卸载应用
     *
     * @param applyId
     * @return
     */
    Result unload(Long applyId);

    String loadCode(ApplyEntity entity) throws Exception;


    Result load(String code, ApplyEntity entity) throws Exception;

    void quote(Boolean aTrue, TaskType taskType, String applyId);
}
