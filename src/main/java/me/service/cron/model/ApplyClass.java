package me.service.cron.model;

import lombok.Data;

/**
 * @author zhangpeng
 * @create 2023/3/3 23:47
 */
@Data
public class ApplyClass {


    /**
     * 程序名称
     */
    private String javaName;

    /**
     * 是否装载
     */
    private Boolean load;

    /**
     * 是否引用
     */
    private Boolean quote;

    /**
     * 装载代码
     */
    private Class<?> aClass;

}
