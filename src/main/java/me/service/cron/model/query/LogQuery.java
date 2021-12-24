package me.service.cron.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.service.cron.contents.TaskType;

/**
 * 描述：
 * 2021/12/13 17:47.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("日志查询")
public class LogQuery extends PageQuery {

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("描述")
    private String description;


    @ApiModelProperty("任务类型")
    private TaskType taskType;
}
