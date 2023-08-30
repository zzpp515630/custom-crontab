package me.service.cron.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.service.cron.model.ListResult;
import me.service.cron.model.Result;
import me.service.cron.model.query.ApplyQuery;
import me.service.cron.model.request.CreateApplyRequest;
import me.service.cron.model.request.ModifyApplyRequest;
import me.service.cron.model.response.ApplyResponse;
import me.service.cron.service.ApplyService;
import org.springframework.stereotype.Service;

/**
 * 描述：
 * 2021/12/21 16:32.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ApplyServiceImpl implements ApplyService {

    @Override
    public Result create(CreateApplyRequest request) {
        return null;
    }

    @Override
    public Result modify(ModifyApplyRequest request) {
        return null;
    }

    @Override
    public ListResult<ApplyResponse> list(ApplyQuery query) {
        return null;
    }

    @Override
    public Result running(Long applyId) {
        return null;
    }

    @Override
    public Result force(Long applyId) {
        return null;
    }
}
