package me.service.cron.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.service.cron.contents.CompareType;
import me.service.cron.contents.TaskType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 描述：
 * 2021/12/13 10:00.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel("定时任务请求入参")
public class CreateTaskRequest {

    @NotBlank(message = "任务名称不能为空")
    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @NotBlank(message = "cron表达式不能为空")
    @ApiModelProperty("cron表达式")
    private String cron;

    @NotNull(message = "任务类型不能为空")
    @ApiModelProperty("定时器任务类型")
    private TaskType taskType;

    @NotBlank(message = "执行指令不能为空")
    @ApiModelProperty("执行指令")
    private String command;

    @ApiModelProperty("是否开启任务超时")
    private Boolean timeout;

    @ApiModelProperty("执行超时(s)")
    private Integer executeTimeout;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("比较类型")
    private CompareType compareType;

    @ApiModelProperty("比较是否忽略大小写")
    private Boolean compareIgnoreCase;

    @ApiModelProperty("预期值")
    private String expectedValue;

    @ApiModelProperty("邮件成功消息")
    private String emailSuccessMessage;

    @ApiModelProperty("邮件失败信息")
    private String emailErrorMessage;


}
