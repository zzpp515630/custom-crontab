package me.service.cron.model.query;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述：
 * 2021/12/21 16:46.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("应用查询")
public class ApplyQuery extends PageQuery{
}
