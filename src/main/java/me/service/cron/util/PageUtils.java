package me.service.cron.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import me.service.cron.model.query.PageQuery;

import java.util.Optional;

/**
 * @author zhangpeng2
 * 2019/12/25 11:10
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class PageUtils {

    private PageUtils() {
    }

    public static <E> Page<E> getPage(PageQuery request) {
        try {
            Integer limit = Optional.ofNullable(request.getPageNo()).orElse(1);
            Integer size = Optional.ofNullable(request.getPageSize()).orElse(500);
            return new Page<>(limit.longValue(), size.longValue());
        } catch (IllegalStateException e) {
            log.error(e.getMessage(), e);
        }
        return new Page<>(0L, 100L);
    }
}
