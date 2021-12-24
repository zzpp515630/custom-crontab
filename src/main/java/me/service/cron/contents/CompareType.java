package me.service.cron.contents;

/**
 * 描述：
 * 2021/12/17 9:18.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
public enum CompareType {

    /**
     * 等于
     */
    EQUALS,

    /**
     * 包含
     */
    CONTAIN,

    /**
     * 开始从
     */
    START_WITH,

    /**
     * 结束从
     */
    END_WITH,

    /**
     * 正则
     */
    REGULAR,

    /**
     * 默认
     */
    NON,
    ;

}
