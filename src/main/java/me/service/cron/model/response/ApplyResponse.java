package me.service.cron.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.service.cron.contents.ApplyType;
import me.service.cron.contents.CommonStatus;

/**
 * 描述：
 * 2021/12/21 16:42.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel("应用响应")
public class ApplyResponse {

    @ApiModelProperty("应用名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("应用类型")
    private ApplyType applyType;

    @ApiModelProperty("开始命令")
    private String startCommand;

    @ApiModelProperty("结束命令")
    private String stopCommand;

    @ApiModelProperty("强制结束命令")
    private String forceStopCommand;

    @ApiModelProperty("状态判断命令")
    private String statusCommand;

    @ApiModelProperty("状态判断比较命令")
    private String compareCommand;

    @ApiModelProperty("执行开始时间")
    private Long createTime;

    @ApiModelProperty("执行结束时间")
    private Long updateTime;

    @ApiModelProperty("状态判断")
    private CommonStatus status;

}
