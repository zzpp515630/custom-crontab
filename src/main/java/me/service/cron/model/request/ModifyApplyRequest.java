package me.service.cron.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 描述：
 * 2021/12/21 16:34.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("修改应用")
public class ModifyApplyRequest extends CreateApplyRequest {

    @NotNull(message = "主键id不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键id")
    private Long id;

}
