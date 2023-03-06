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

    @ApiModelProperty("代码")
    private String code;

}
