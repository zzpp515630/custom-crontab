package me.service.cron.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 描述：
 * 2021/12/21 16:40.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("app")
public class AppEntity {

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
     * 是否引用
     */
    private Boolean quote;

    /**
     * 程序名
     */
    private String javaName;
}
