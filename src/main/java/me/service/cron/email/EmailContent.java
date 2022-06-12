package me.service.cron.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 邮件实体
 *
 * @author zhangpeng2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailContent implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 接收者的邮箱地址
     */
    private List<String> toAddress;
    /**
     * 设置邮件主题
     */
    private String subject;
    /**
     * 设置邮件内容
     */
    private String context;
    /**
     * 设置邮件类型
     */
    private String contextType;
    /**
     * 邮件附件
     */
    private String attachmentListStr;

}
