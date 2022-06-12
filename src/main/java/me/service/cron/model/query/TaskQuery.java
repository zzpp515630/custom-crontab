package me.service.cron.model.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.service.cron.contents.TaskType;

/**
 * 描述：
 * 2021/12/23 10:31.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("任务搜索")
public class TaskQuery extends PageQuery {


    @ApiModelProperty("任务类型")
    private TaskType taskType;

    @ApiModelProperty("名称")
    private String name;
}
