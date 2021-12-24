package me.service.cron.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 邮件配置
 *
 * @author zhangpeng2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailConfig {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 发送地址
     */
    private String fromAddress;

    /**
     * 地址
     */
    private String host;

    /**
     * 端口
     */
    private String port;

}
