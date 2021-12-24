package me.service.cron.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述：
 * 2021/12/13 17:02.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("对象结果集")
@NoArgsConstructor
public class ListResult<T> extends Result {

    /**
     * 响应数据
     */
    @ApiModelProperty("响应数据")
    private List<T> data;

    /**
     * 总记录数据
     */
    @ApiModelProperty("总记录数据")
    private Long total;


    public ListResult(List<T> data, Long total) {
        this.data = data;
        this.total = total;
        this.setCode(1);
        this.setMsg("success");
    }

    public ListResult(int code, String msg, List<T> data, Long total) {
        super(code, msg);
        this.data = data;
        this.total = total;
    }
}
