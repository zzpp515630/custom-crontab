package me.service.cron.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述：
 * 2021/12/13 17:05.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel("查询参数")
public class PageQuery {

    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize;

    /**
     * 页号
     */
    @ApiModelProperty("页号")
    private Integer pageNo;

    /**
     *开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private Long startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private Long endTime;

}
