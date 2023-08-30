package me.service.cron.util;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 2021/12/16 15:20.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
public class BeanUtil {

    @SneakyThrows
    public static <T> T copyProperties(Object source, Class<T> target) {
        T instance = target.newInstance();
        if (null == source) {
            return null;
        }
        BeanUtils.copyProperties(source, instance);
        return instance;
    }

    @SneakyThrows
    public static <T> List<T> copyProperties(List<?> sources, Class<T> target) {
        List<T> result = new ArrayList<>();
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        for (Object source : sources) {
            result.add(copyProperties(source, target));
        }
        return result;
    }

}
