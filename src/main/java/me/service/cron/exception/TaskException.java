package me.service.cron.exception;

/**
 * 描述：
 * 2021/12/13 17:11.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
public class TaskException extends RuntimeException {

    public TaskException(String message) {
        super(message);
    }
}
