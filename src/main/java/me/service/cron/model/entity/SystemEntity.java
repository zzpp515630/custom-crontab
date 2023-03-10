package me.service.cron.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 描述：
 * 2021/12/16 15:15.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("system")
public class SystemEntity {

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 主题
     */
    private String subject;

    /**
     * 日志保留天数
     */
    private Integer logRetainDays;

    /**
     * 命令前缀
     */
    private String commandPrefix;

    /**
     * 邮件地址
     */
    private String emailHost;

    /**
     * 邮件发送地址
     */
    private String emailFromAddress;

    /**
     * 邮件账号
     */
    private String emailAccount;

    /**
     * 邮件密码
     */
    private String emailPassword;

    /**
     * 邮件端口
     */
    private String emailPort;

    /**
     * 命令路径
     */
    private String cmdPath;

    /**
     * 代码路径
     */
    private String codePath;

}
