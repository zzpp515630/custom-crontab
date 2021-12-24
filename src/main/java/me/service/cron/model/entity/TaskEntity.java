package me.service.cron.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import me.service.cron.contents.CompareType;
import me.service.cron.contents.CommonStatus;
import me.service.cron.contents.TaskType;

/**
 * 描述：
 * 2021/12/13 10:03.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("task")
public class TaskEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * cron表达式
     */
    private String cron;

    /**
     * 执行类型
     */
    private TaskType taskType;

    /**
     * 执行脚本命令
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
     * 状态
     */
    private CommonStatus status;

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

}
