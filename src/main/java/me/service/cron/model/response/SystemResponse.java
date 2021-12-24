package me.service.cron.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述：
 * 2021/12/16 15:14.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Data
@ApiModel("系统响应")
public class SystemResponse {

    @ApiModelProperty("主题")
    private String subject;

    @ApiModelProperty("日志保留天数")
    private Integer logRetainDays;

    @ApiModelProperty("命令前缀")
    private String commandPrefix;

    @ApiModelProperty("邮件地址")
    private String emailHost;

    @ApiModelProperty("邮件发送地址")
    private String emailFromAddress;

    @ApiModelProperty("邮件账号")
    private String emailAccount;

    @ApiModelProperty("邮件密码")
    private String emailPassword;

    @ApiModelProperty("邮件端口")
    private String emailPort;

}
