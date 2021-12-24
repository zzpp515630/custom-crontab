package me.service.cron.service;

import me.service.cron.model.ListResult;
import me.service.cron.model.Result;
import me.service.cron.model.query.ApplyQuery;
import me.service.cron.model.request.CreateApplyRequest;
import me.service.cron.model.request.ModifyApplyRequest;
import me.service.cron.model.response.ApplyResponse;

/**
 * 描述：
 * 2021/12/21 16:32.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
public interface ApplyService {
    /**
     * 创建
     *
     * @param request
     * @return
     */
    Result create(CreateApplyRequest request);

    /**
     * 修改
     *
     * @param request
     * @return
     */
    Result modify(ModifyApplyRequest request);

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
     * 强制执行
     *
     * @param applyId
     * @return
     */
    Result force(Long applyId);
}
