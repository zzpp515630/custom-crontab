package me.service.cron.util;

import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * cron表达式工具类
 *
 * @author zhang peng
 */
public class CronUtils {
    /**
     * 返回一个布尔值代表一个给定的Cron表达式的有效性
     *
     * @param cronExpression Cron表达式
     * @return boolean 表达式是否有效
     */
//    public static boolean isValid(String cronExpression) {
//        return CronExpression.isValidExpression(cronExpression);
//    }

    public static boolean isValid(String expression){
        if (expression == null) {
            return false;
        }
            CronExpression.parse(expression);
            return true;
    }


    /**
     * 返回一个字符串值,表示该消息无效Cron表达式给出有效性
     *
     * @param cronExpression Cron表达式
     * @return String 无效时返回表达式错误描述,如果有效返回null
     */
    public static String getInvalidMessage(String cronExpression) {
        CronExpression.isValidExpression(cronExpression);
        return null;
    }

    /**
     * 返回下一个执行时间根据给定的Cron表达式
     *
     * @param cronExpression Cron表达式
     * @return Date 下次Cron表达式执行时间
     */
    public static LocalDateTime getNextExecution(String cronExpression) {
        CronExpression cron = CronExpression.parse(cronExpression);
        return cron.next(LocalDateTime.now());
    }



    public static void main(String[] args) {
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        getCron(localDateTimes,20,"0/6 * * * * ?",LocalDateTime.now());
        for (LocalDateTime localDateTime : localDateTimes) {
            System.out.println(localDateTime);
        }
        List<String> collect = localDateTimes.stream().map(dtf2::format).collect(Collectors.toList());
        for (String localTime : collect) {
            System.out.println(localTime);
        }
    }

    public static void getCron(List<LocalDateTime> localDateTimes, int num, String cronExpression, LocalDateTime localDateTime) {
        CronExpression cron = CronExpression.parse(cronExpression);
        LocalDateTime next = cron.next(localDateTime);
        localDateTimes.add(next);
        if (localDateTimes.size() >= num) {
            return;
        }
        getCron(localDateTimes,num,cronExpression,next);
    }

}