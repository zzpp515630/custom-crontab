package me.service.cron.util;

import lombok.SneakyThrows;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zzpp
 * @date 2021/4/1.
 */
public class CustomOptional<T> {

    private final T value;

    public static <T> CustomOptional<T> of(T value) {
        return new CustomOptional<>(value);
    }

    public CustomOptional(T value) {
        this.value = value;
    }

    
    public CustomOptional<T> orElse(T other) {
        return value != null ? new CustomOptional<>(value) : new CustomOptional<>(other);
    }


    @SneakyThrows
    public void isPresentValue(RunningValue<T> running) {
        if (null == value) {
            return;
        }
        if (value instanceof Boolean) {
            if (BooleanUtils.isTrue((Boolean) value)) {
                running.run(value);
            }
        } else if (value instanceof String) {
            if (StringUtils.isNotBlank((String) value)) {
                running.run(value);
            }
        } else {
            running.run(value);

        }
    }

    @SneakyThrows
    public void isPresent(RunningNon running) {
        if (null == value) {
            return;
        }
        if (value instanceof Boolean) {
            if (BooleanUtils.isTrue((Boolean) value)) {
                running.run();
            }
        } else if (value instanceof String) {
            if (StringUtils.isNotBlank((String) value)) {
                running.run();
            }
        } else {
            running.run();
        }
    }


    @FunctionalInterface
    public interface RunningValue<T> {
        /**
         * 执行方法
         *
         * @param value
         * @throws Exception
         */
        void run(T value) throws Exception;
    }

    @FunctionalInterface
    public interface RunningNon {
        /**
         * 执行方法
         *
         * @throws Exception
         */
        void run() throws Exception;
    }


}
