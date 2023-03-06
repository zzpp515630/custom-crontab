package me.service.cron.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.service.cron.contents.CommonStatus;

import javax.validation.constraints.NotNull;

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

    @NotNull(message = "主键id不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("应用名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("是否装载")
    private Boolean load;

    @ApiModelProperty("是否引用")
    private Boolean quote;

}
