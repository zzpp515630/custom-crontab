package me.service.cron.task;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.service.cron.contents.CommonStatus;
import me.service.cron.contents.CompareType;
import me.service.cron.contents.TaskType;
import me.service.cron.email.EmailConfig;
import me.service.cron.email.EmailContent;
import me.service.cron.email.EmailSendService;
import me.service.cron.exception.TaskException;
import me.service.cron.mapper.LogMapper;
import me.service.cron.mapper.SystemMapper;
import me.service.cron.mapper.TaskMapper;
import me.service.cron.model.entity.LogEntity;
import me.service.cron.model.entity.SystemEntity;
import me.service.cron.model.entity.TaskEntity;
import me.service.cron.util.*;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

/**
 * 描述：
 * 2021/12/13 11:28.
 *
 * @author zhangpeng2
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class TaskExecute implements Runnable {

    private final TaskEntity entity;

    private SystemEntity systemEntity;

    public TaskExecute(TaskEntity entity) {
        this.entity = entity;
    }

    @Override
    public void run() {
        LogEntity logEntity = initLogEntity();
        //开始时间
        logEntity.setStartTime(System.currentTimeMillis());
        //执行
        ExecuteResult running = compare(new ExecuteFactory(systemEntity).running(logEntity));
        //结束时间
        logEntity.setEndTime(System.currentTimeMillis());
        //封装执行结果
        logEntity.setExecuteResult(running.getResult());
        logEntity.setExecuteCode(running.getCode());
        //计算下一次的执行时间
        checkNextExecution();
        //发送邮件
        sendEmail(logEntity);
        //添加到db
        updateLog(logEntity);
    }

    private LogEntity initLogEntity() {
        SystemMapper systemMapper = SpringUtil.getBean(SystemMapper.class);
        this.systemEntity = systemMapper.selectOne(Wrappers.emptyWrapper());
        LogEntity logEntity = BeanUtil.copyProperties(entity, LogEntity.class);
        logEntity.setId(null);
        logEntity.setTaskId(entity.getId());
        logEntity.setTaskName(entity.getName());
        logEntity.setExecuteCode(0);
        LogMapper logMapper = SpringUtil.getBean(LogMapper.class);
        logMapper.insert(logEntity);
        return logEntity;

    }

    private void sendEmail(LogEntity logEntity) {
        try {
            if (CompareType.NON != logEntity.getCompareType()) {
                if (-1 == logEntity.getExecuteCode()) {
                    return;
                }
            }
            String email = logEntity.getEmail();
            if (StringUtils.isBlank(email)) {
                return;
            }
            String emailMessage = logEntity.getExecuteResult();
            if (StringUtils.isNotBlank(logEntity.getEmailMessage())) {
                emailMessage = logEntity.getEmailMessage();
                try {
                    Class<? extends LogEntity> aClass = logEntity.getClass();
                    Field[] allFields = getSuperClassFields(aClass.getDeclaredFields(), aClass);
                    for (Field declaredField : allFields) {
                        PropertyDescriptor pd = new PropertyDescriptor(declaredField.getName(), aClass);
                        Method getMethod = pd.getReadMethod();
                        Object fieldValue = ReflectionUtils.invokeMethod(getMethod, logEntity);
                        if (fieldValue != null) {
                            emailMessage = emailMessage.replace("#" + declaredField.getName() + "#", String.valueOf(fieldValue));
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                emailMessage = emailMessage.replace("#result#", logEntity.getExecuteResult());
            }
            //发送邮件
            if (null == systemEntity) {
                return;
            }
            String emailAccount = systemEntity.getEmailAccount();
            String emailFromAddress = systemEntity.getEmailFromAddress();
            String emailHost = systemEntity.getEmailHost();
            String emailPassword = systemEntity.getEmailPassword();
            String emailPort = systemEntity.getEmailPort();
            EmailConfig emailConfig = new EmailConfig(emailAccount, emailPassword, emailFromAddress, emailHost, emailPort);
            EmailContent emailContent = new EmailContent();
            emailContent.setToAddress(Collections.singletonList(email));
            emailContent.setContext(emailMessage);
            emailContent.setContextType(MediaType.APPLICATION_JSON_VALUE);
            emailContent.setSubject(Optional.of(systemEntity.getSubject()).orElse("定时任务"));
            EmailSendService sendService = SpringUtil.getBean(EmailSendService.class);
            String result = sendService.emailSend(emailConfig, emailContent);
            logEntity.setEmailResult(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            logEntity.setEmailResult(e.getMessage());
        }
    }

    private ExecuteResult compare(ExecuteResult running) {
        CompareType compareType = entity.getCompareType();
        if (null == compareType) {
            return running;
        }
        String expectedValue = entity.getExpectedValue();
        String result = running.getResult();
        if (BooleanUtils.isTrue(entity.getCompareIgnoreCase())) {
            expectedValue = expectedValue.toLowerCase();
            result = result.toLowerCase();
        }
        switch (compareType) {
            case EQUALS:
                return ExecuteResult.check(result.equals(expectedValue), result);
            case CONTAIN:
                return ExecuteResult.check(result.contains(expectedValue), result);
            case START_WITH:
                return ExecuteResult.check(result.startsWith(expectedValue), result);
            case END_WITH:
                return ExecuteResult.check(result.endsWith(expectedValue), result);
            case REGULAR:
                return ExecuteResult.checkRegular(expectedValue, result);
            case NON:
            default:
                return running;
        }
    }


    private void updateLog(LogEntity logEntity) {
        logEntity.setRunningTimer(logEntity.getEndTime() - logEntity.getStartTime());
        LogMapper logMapper = SpringUtil.getBean(LogMapper.class);
        logMapper.updateById(logEntity);
    }

    private void checkNextExecution() {
        LocalDateTime nextExecution = CronUtils.getNextExecution(entity.getCron());
        if (null == nextExecution) {
            TaskMapper taskMapper = SpringUtil.getBean(TaskMapper.class);
            entity.setStatus(CommonStatus.Stop);
            taskMapper.updateById(entity);
        }
    }

    private static class ExecuteFactory {

        private final SystemEntity systemEntity;

        public ExecuteFactory(SystemEntity systemEntity) {
            this.systemEntity = systemEntity;
        }

        private Execute get(TaskType taskType) {
            switch (taskType) {
                case Http:
                    return new HttpExecute();
                case Shell:
                    return new ShellExecute(systemEntity);
                default:
                    throw new TaskException("服务异常");
            }
        }

        ExecuteResult running(LogEntity logEntity) {
            try {
                CompletableFuture<ExecuteResult> completableFuture =
                        CompletableFuture.supplyAsync(() -> get(logEntity.getTaskType()).running(logEntity));
                if (BooleanUtils.isTrue(logEntity.getTimeout())) {
                    return completableFuture.get(logEntity.getExecuteTimeout(), TimeUnit.SECONDS);
                } else {
                    return completableFuture.get();
                }
            } catch (TimeoutException e) {
                log.error(e.getMessage(), e);
                return new ExecuteResult(-1, e.getMessage());
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                log.error(e.getMessage(), e);
                return new ExecuteResult(-1, e.getMessage());
            }
        }

    }

    interface Execute {

        /**
         * 执行器
         *
         * @param logEntity
         * @return 执行结果响应
         */
        ExecuteResult running(LogEntity logEntity);

    }

    static class ShellExecute implements Execute {

        private final SystemEntity systemEntity;

        public ShellExecute(SystemEntity systemEntity) {
            this.systemEntity = systemEntity;
        }

        @Override
        public ExecuteResult running(LogEntity logEntity) {
            try {
                String command = systemEntity.getCommandPrefix();
                String[] prefix = StringUtils.isNotBlank(command) ? command.split(" ") : null;
                Pair<Integer, String> execute = new CommandProcess(prefix).execute(logEntity.getCommand());
                return new ExecuteResult(execute.getKey() == 0 ? 1 : -1, execute.getValue());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new ExecuteResult(-1, e.getMessage());
            }
        }
    }

    static class HttpExecute implements Execute {

        @Override
        public ExecuteResult running(LogEntity logEntity) {
            try {
                String cmd = logEntity.getCommand();
                String[] split = cmd.split("@");
                String method = split[0].toUpperCase();
                String url = split[1];
                String params = logEntity.getParams();
                String result;
                if (HttpMethod.POST.name().equals(method)) {
                    result = ApacheHttpClient.httpPost(url, params);
                } else if (HttpMethod.PUT.name().equals(method)) {
                    result = ApacheHttpClient.httpPut(url, params);
                } else if (HttpMethod.DELETE.name().equals(method)) {
                    result = ApacheHttpClient.httpDelete(url, params);
                } else {
                    result = ApacheHttpClient.httpGet(url, params);
                }
                return new ExecuteResult(1, result);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return new ExecuteResult(-1, e.getMessage());
            }
        }
    }

    /**
     * 获取父类的所有字段
     *
     * @param clazz 输入参数
     * @return
     */
    private static Field[] getSuperClassFields(Field[] declaredFields, Class<?> clazz) {
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz.equals(ApplicationEvent.class)) {
            return declaredFields;
        }
        Field[] superClassFields = superClazz.getDeclaredFields();
        Field[] superFields = new Field[declaredFields.length + superClassFields.length];
        System.arraycopy(declaredFields, 0, superFields, 0, declaredFields.length);
        System.arraycopy(superClassFields, 0, superFields, declaredFields.length, superClassFields.length);
        return getSuperClassFields(superFields, superClazz);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ExecuteResult {
        /**
         * 状态码
         */
        private Integer code;

        /**
         * 结果码
         */
        private String result;

        public static ExecuteResult check(boolean isCompare, String result) {
            return new ExecuteResult(isCompare ? 1 : -1, result);
        }

        public static ExecuteResult checkRegular(String expectedValue, String result) {
            boolean isMatch = Pattern.matches(expectedValue, result);
            return check(isMatch, result);
        }
    }
}
