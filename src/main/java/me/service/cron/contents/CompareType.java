package me.service.cron.contents;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述：
 * 2021/12/17 9:18.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public enum CompareType {

    /**
     * 等于
     */
    EQUALS(1),

    /**
     * 包含
     */
    CONTAIN(1),

    /**
     * 开始从
     */
    START_WITH(1),

    /**
     * 结束从
     */
    END_WITH(1),

    /**
     * 正则
     */
    REGULAR(1),

    /**
     * 默认
     */
    NON(99),
    ;

    private final int sort;

}
