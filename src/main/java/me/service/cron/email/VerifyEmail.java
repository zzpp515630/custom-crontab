package me.service.cron.email;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 验证邮箱
 *
 * @author zhangpeng2
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class VerifyEmail extends Authenticator {


    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;

    public VerifyEmail(EmailConfig config) {
        this.account = config.getAccount();
        this.password = config.getPassword();

    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(account, password);
    }
}
