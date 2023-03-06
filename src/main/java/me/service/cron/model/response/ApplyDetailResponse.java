package me.service.cron.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhangpeng
 * @create 2023/3/3 23:53
 */
@Data
@ApiModel("应用响应")
public class ApplyDetailResponse {

    @NotNull(message = "主键id不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("应用名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("源码")
    private String code;

}
