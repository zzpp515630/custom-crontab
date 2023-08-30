# nas-crontab


## 1. 概述

- > 基于定时系统执行shell命令和http命令，获得的结果可以进行比较再发送邮件，本系统主要用于配置定时任务，并查看管理定时任务

## 2.项目环境

### 2.1**项目架构：**

Spring Boot + Spring Cloud Alibaba + Mybatis Plus + Sqlite

### 2.2**开发环境：**

Windows+ IDEA + JDK1.8 + MySQL5.7 + Git  + Maven

## 3.  系统模块

### 3.2**项目结构：**
```aidl
me.service.cron
  ├── pom.xml                            //项目依赖
  ├── db                                 //sqlit数据库
    ├── java                             //java源码 
    |     ├── config                     //全局公共配置
    |     ├── constents                  //常量，枚举
    |     ├── controller                 //mvc控制层
    |     ├── model                      //bean目录
    |     |  ├── entity                  //实体，对应db层
    |     |  ├── query                   //查询实体
    |     |  ├── request                 //请求实体
    |     |  └── response                //响应实体 
    |     ├── service                    //服务接口层
    |     |  ├── impl                    //服务接口实现
    |     ├── mapper                     //持久化接口层
    |     ├── email                      //邮件发送服务
    |     ├── exception                  //全局异常处理器
    |     ├── ruuner                     //启动初始化类
    |     ├── schema                     //数据库版本控制
    |     ├── task                       //定时任务执行器
    |     ├── timer                      //全局定时任务
    |     └── utils                      //工具类
    |     ├── CronApplication.java       //启动类
    └── resources                        //项目资源文件夹
       ├── mapper                        //持久化xml目录
       ├── static                        //静态资源目录
       └── application.yml               //项目配置文件
```