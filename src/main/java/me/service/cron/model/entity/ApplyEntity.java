package me.service.cron.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import me.service.cron.contents.ApplyType;

/**
 * 描述：
 * 2021/12/21 16:40.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("apply")
public class ApplyEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 应用名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 应用类型
     */
    private ApplyType applyType;

    /**
     * 开始命令
     */
    private String startCommand;

    /**
     * 结束命令
     */
    private String stopCommand;

    /**
     * 强制结束命令
     */
    private String forceStopCommand;

    /**
     * 状态判断命令
     */
    private String statusCommand;

    /**
     * 状态比较命令
     */
    private String compareCommand;
}
