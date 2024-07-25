package com.maizi.common.exception;

import com.maizi.common.o.constants.ModuleType;
import com.maizi.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * 全局异常处理器，用于处理应用程序中的各种异常。
 * 该类使用 Spring 的 {@link RestControllerAdvice} 注解，自动捕获并处理控制器中的异常。
 * 可以根据不同的异常类型定义特定的处理方法，返回统一的响应格式。
 * <p>
 *
 * @author maizi
 */
@Slf4j
@ControllerAdvice
public class RRExceptionHandler {

    @ExceptionHandler(RRException.class)
    @ResponseBody
    public R handleAuthorException(RRException ex) {
        log.error("【公共模块】- 自定义运行时异常: {}", ex.getMessage());  // 记录异常信息
        return R.error(ex.getCode(), ex.getMessage());
    }


    @ExceptionHandler(AccessDeniedException.class)
    public R accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        log.error("【公共模块】- 捕获到了AccessDeniedException======" + e.getMessage());
        log.error("【公共模块】- 捕获到了AccessDeniedException======" + e.getMessage());
        log.error("【公共模块】- 捕获到了AccessDeniedException======" + e.getMessage());
        return R.error(HttpStatus.FORBIDDEN.value(), "权限不足");
    }


    /**
     * 全局异常处理，处理所有未捕获的运行时异常 {@link RuntimeException}。
     *
     * @param e 异常对象
     * @return 包含错误码和错误信息的响应对象
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error("【公共模块】- 未捕获的运行时异常: {}", e.getMessage());  // 记录异常信息
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统异常！请稍后再试...");  // 返回内部服务器错误响应
    }
}
