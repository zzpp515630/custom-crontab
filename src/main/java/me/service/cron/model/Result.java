package me.service.cron.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：
 * 2021/12/13 9:57.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel("请求")
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    @ApiModelProperty("状态码")
    private int code;

    @ApiModelProperty("状态信息")
    private String msg;

    /**
     * 成功
     *
     * @return
     */
    public static Result success() {
        return new Result(1, "success");
    }

    public static Result error(String msg) {
        return new Result(500, msg);
    }
}
