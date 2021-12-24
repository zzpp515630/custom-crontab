package me.service.cron.exception;

import lombok.extern.slf4j.Slf4j;
import me.service.cron.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.nio.file.AccessDeniedException;

/**
 * @author zhangpeng
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public Result handleIllegalArgumentException(IllegalArgumentException e) {
        return handleException("参数解析失败", e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ServletRequestBindingException.class})
    public Result handleServletRequestBindingException(ServletRequestBindingException e) {
        return handleException("请求错误", e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result handleMethodArgumentNotValidException(Exception e) {
        return handleException("参数不合法", e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidationException.class})
    public Result handleValidationException(Exception e) {
        return handleException("参数不合法", e.getCause() == null ? e : e.getCause());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return handleException("HTTP消息读取失败", e);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class})
    public Result handleAccessDeniedException(AccessDeniedException e) {
        return handleException("拒绝访问", e);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return handleException("不支持的请求方法", e);
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public Result handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return handleException("不支持的媒体类型", e);
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        return handleException("未知异常", e);
    }

    private Result handleException(String msg, Throwable e) {
        log.error(msg, e);
        return Result.error(msg + "\n" + e.getMessage());
    }

//    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    @ExceptionHandler(TaskException.class)
    public Result handleCenterException(TaskException e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getMessage());
    }

}
