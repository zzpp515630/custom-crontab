package me.service.cron.model.request;

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
@ApiModel("系统请求")
public class SystemRequest {

    /**
     * 主题
     */
    @ApiModelProperty("主题")
    private String subject;

    /**
     * 日志保留天数
     */
    @ApiModelProperty("日志保留天数")
    private Integer logRetainDays;

    /**
     * 命令前缀
     */
    @ApiModelProperty("命令前缀")
    private String commandPrefix;

    /**
     * 邮件地址
     */
    @ApiModelProperty("邮件地址")
    private String emailHost;

    /**
     * 邮件发送地址
     */
    @ApiModelProperty("邮件发送地址")
    private String emailFromAddress;

    /**
     * 邮件账号
     */
    @ApiModelProperty("邮件账号")
    private String emailAccount;

    /**
     * 邮件密码
     */
    @ApiModelProperty("邮件密码")
    private String emailPassword;

    /**
     * 邮件端口
     */
    @ApiModelProperty("邮件端口")
    private String emailPort;
    
    @ApiModelProperty("代码路径")
    private String codePath;

}
