package me.service.cron.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import me.service.cron.contents.CompareType;
import me.service.cron.contents.TaskType;

/**
 * 描述：
 * 2021/12/13 10:04.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("log")
public class LogEntity {

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 任务id
     */
    private String taskName;

    /**
     * 执行描述
     */
    private String description;

    /**
     * 表达式
     */
    private String cron;

    /**
     * 执行类型
     */
    private TaskType taskType;

    /**
     * 执行名称
     */
    private String command;

    /**
     * 参数
     */
    private String params;

    /**
     * 是否开启任务超时
     */
    private Boolean timeout;

    /**
     * 执行超时(s)
     */
    private Integer executeTimeout;

    /**
     * 执行开始时间
     */
    private Long startTime;

    /**
     * 执行结束时间
     */
    private Long endTime;

    /**
     * 执行时间(ms)
     */
    private Long runningTimer;

    /**
     * 执行状态码
     */
    private Integer executeCode;

    /**
     * 执行结果
     */
    private String executeResult;

    /**
     * 比较类型
     */
    private CompareType compareType;

    /**
     * 比较是否忽略大小写
     */
    private Boolean compareIgnoreCase;

    /**
     * 预期值
     */
    private String expectedValue;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮件消息
     */
    private String emailMessage;

    /**
     * 邮件结果
     */
    private String emailResult;

}
