package me.service.cron.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.service.cron.model.GetResult;
import me.service.cron.model.Result;
import me.service.cron.model.entity.SystemEntity;
import me.service.cron.model.request.SystemRequest;
import me.service.cron.model.response.SystemResponse;

/**
 * 描述：
 * 2021/12/16 15:16.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
public interface SystemService extends IService<SystemEntity> {

    /**
     * 获取系统配置
     *
     * @return 响应
     */
    GetResult<SystemResponse> get();

    /**
     * 添加
     * @param request
     * @return
     */
    Result save(SystemRequest request);
}
