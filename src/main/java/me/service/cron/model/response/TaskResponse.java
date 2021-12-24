package me.service.cron.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.service.cron.contents.CompareType;
import me.service.cron.contents.CommonStatus;
import me.service.cron.contents.TaskType;

/**
 * 描述：
 * 2021/12/13 16:58.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel("定时任务响应")
public class TaskResponse {

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("cron表达式")
    private String cron;

    @ApiModelProperty("定时器任务类型")
    private TaskType taskType;

    @ApiModelProperty("执行超时(s)")
    private Integer executeTimeout;

    @ApiModelProperty("状态")
    private CommonStatus status;

    @ApiModelProperty("执行指令")
    private String command;

    @ApiModelProperty("参数")
    private String params;

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
}
