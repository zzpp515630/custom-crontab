package me.service.cron.email;

import lombok.extern.slf4j.Slf4j;
import me.service.cron.email.EmailConfig;
import me.service.cron.email.EmailContent;
import me.service.cron.email.VerifyEmail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 邮件发送
 */
@Slf4j
@Component
public class EmailSendService {

    private static final String PROTOCOL = "smtp";
    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String MAIL_SMTP_STARRTTLS_ENABLE = "mail.smtp.starrttls.enable";
    private static final String MAIL_SMTP_SOCKETFACTORY_PORT = "mail.smtp.socketFactory.port";
    private static final String MAIL_SMTP_SOCKETFACTORY_CLASS = "mail.smtp.socketFactory.class";
    private static final String MAIL_SMTP_SOCKETFACTORY_FALLBACK = "mail.smtp.socketFactory.fallback";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
    private static final String CONTEXT_TYPE = "text/plain; charset=utf-8";


    public String emailSend(EmailConfig config, EmailContent emailContent) {
        if (
                StringUtils.isBlank(config.getAccount()) ||
                        StringUtils.isBlank(config.getFromAddress()) ||
                        StringUtils.isBlank(config.getHost()) ||
                        StringUtils.isBlank(config.getPassword()) ||
                        StringUtils.isBlank(config.getPort())
        ) {
            return "error";
        }
        try {
            //配置文件
            Properties properties = new Properties();
            properties.put(MAIL_SMTP_HOST, config.getHost());
            properties.put(MAIL_SMTP_PORT, config.getPort());
            properties.put(MAIL_SMTP_SOCKETFACTORY_PORT, config.getPort());
            properties.put(MAIL_SMTP_SOCKETFACTORY_CLASS, SSL_FACTORY);
            properties.put(MAIL_SMTP_SOCKETFACTORY_FALLBACK, Boolean.FALSE.toString());
            properties.put(MAIL_SMTP_STARRTTLS_ENABLE, Boolean.TRUE.toString());
            properties.put(MAIL_SMTP_AUTH, Boolean.TRUE.toString());
            //创建会话
            Session mailSession = Session.getInstance(properties, new VerifyEmail(config));
            mailSession.setDebug(true);
            //创建信息对象
            MimeMessage message = new MimeMessage(mailSession);
            //解决乱码问题
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "GBK");
            InternetAddress from = new InternetAddress(config.getFromAddress(), config.getAccount());
            //发送地址
            List<String> toAddress = emailContent.getToAddress();
            List<InternetAddress> addressesArray = new ArrayList<>();
            for (String address : toAddress) {
                InternetAddress to = new InternetAddress(address);
                addressesArray.add(to);
            }
            //设置邮件信息的来源
            message.setFrom(from);
            //设置邮件的接收者
            message.setRecipients(MimeMessage.RecipientType.TO, addressesArray.toArray(new InternetAddress[0]));
            message.setSubject(emailContent.getSubject());
            //设置邮件发送日期
            message.setSentDate(new Date());
            //邮件内容
            helper.setText(emailContent.getContext(), true);
            message.saveChanges();
            //发送邮件
            Transport transport = mailSession.getTransport(PROTOCOL);
            transport.connect(config.getHost(), config.getFromAddress(), config.getPassword());
            log.info("email send:{}", transport);
            transport.sendMessage(message, message.getAllRecipients());
            log.info("success");
            return "success";
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("email fail...:{}", e.getMessage(), e);
            return e.getMessage();
        }
    }
}
