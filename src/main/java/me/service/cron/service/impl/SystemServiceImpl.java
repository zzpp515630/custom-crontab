package me.service.cron.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import me.service.cron.mapper.SystemMapper;
import me.service.cron.model.GetResult;
import me.service.cron.model.Result;
import me.service.cron.model.entity.SystemEntity;
import me.service.cron.model.request.SystemRequest;
import me.service.cron.model.response.SystemResponse;
import me.service.cron.service.SystemService;
import me.service.cron.util.BeanUtil;
import org.springframework.stereotype.Service;

/**
 * 描述：
 * 2021/12/16 15:16.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Service
@Slf4j
public class SystemServiceImpl extends ServiceImpl<SystemMapper, SystemEntity> implements SystemService {

    @Override
    public GetResult<SystemResponse> get() {
        SystemEntity entity = this.lambdaQuery().one();
        SystemResponse systemResponse = BeanUtil.copyProperties(entity, SystemResponse.class);
        return new GetResult<>(systemResponse);
    }

    @Override
    public Result save(SystemRequest request) {
        SystemEntity systemEntity = BeanUtil.copyProperties(request, SystemEntity.class);
        SystemEntity queryEntity = this.lambdaQuery().one();
        if (null != queryEntity) {
            systemEntity.setId(queryEntity.getId());
        }
        this.updateById(systemEntity);
        return Result.success();
    }
}
