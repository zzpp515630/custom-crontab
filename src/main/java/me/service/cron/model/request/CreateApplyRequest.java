package me.service.cron.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.service.cron.contents.ApplyType;

/**
 * 描述：
 * 2021/12/21 16:34.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel("创建应用")
public class CreateApplyRequest {

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

}
