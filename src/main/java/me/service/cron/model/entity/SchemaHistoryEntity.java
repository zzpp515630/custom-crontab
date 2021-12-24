package me.service.cron.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 描述：
 * 2021/12/21 9:46.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("schema_history")
public class SchemaHistoryEntity {

    @TableId(type = IdType.AUTO)
    private Long installedRank;

    /**
     * 版本
     */
    private String version;

    /**
     * 描述
     */
    private String description;

    /**
     * 类型
     */
    private String type;

    /**
     * 脚本
     */
    private String script;

    /**
     * 检查
     */
    private Integer checksum;

    /**
     * 安装通过
     */
    private String installedBy;

    /**
     * 安装在
     */
    private Long installedOn;

    /**
     * 执行时间
     */
    private Long executionTime;

    /**
     * 执行是否成功
     */
    private Boolean success;

}
