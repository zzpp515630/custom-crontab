package me.service.cron.util;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

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
        BeanUtils.copyProperties(source, instance);
        return instance;
    }

    @SneakyThrows
    public static <T> List<T> copyProperties(List<Object> sources, Class<T> target) {
        List<T> result = new ArrayList<>();
        for (Object source : sources) {
            result.add(copyProperties(source, target));
        }
        return result;
    }

}
