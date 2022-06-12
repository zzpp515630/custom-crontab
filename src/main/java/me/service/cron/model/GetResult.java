package me.service.cron.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述：
 * 2021/12/13 17:46.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("get请求")
public class GetResult<T> extends Result {

    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private T data;

    public GetResult(int code, String msg, T data) {
        super(code, msg);
        this.data = data;
    }

    public GetResult(T data) {
        this.setCode(1);
        this.setMsg("success");
        this.data = data;
    }
}
