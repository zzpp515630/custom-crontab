package me.service.cron.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.service.cron.contents.CompareType;
import me.service.cron.contents.TaskType;

/**
 * 描述：
 * 2021/12/13 17:46.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel("列表")
public class LogResponse {

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @ApiModelProperty("主键id")
    private Long id;

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @ApiModelProperty("taskId")
    private Long taskId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("执行描述")
    private String description;

    @ApiModelProperty("表达式")
    private String cron;

    @ApiModelProperty("定时器任务类型")
    private TaskType taskType;

    @ApiModelProperty("执行超时(s)")
    private Integer executeTimeout;

    @ApiModelProperty("执行名称")
    private String command;

    @ApiModelProperty("参数")
    private String params;

    @ApiModelProperty("执行开始时间")
    private Long startTime;

    @ApiModelProperty("执行结束时间")
    private Long endTime;

    @ApiModelProperty("执行时间(ms)")
    private Long runningTimer;

    @ApiModelProperty("执行状态码")
    private Integer executeCode;

    @ApiModelProperty("执行结果")
    private String executeResult;

    @ApiModelProperty("比较类型")
    private CompareType compareType;

    @ApiModelProperty("比较是否忽略大小写")
    private Boolean compareIgnoreCase;

    @ApiModelProperty("预期值")
    private String expectedValue;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("邮件消息")
    private String emailMessage;

    @ApiModelProperty("邮件结果")
    private String emailResult;
}
