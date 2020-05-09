package com.ldcr.dlockdemo.web;

import com.ldcr.dlock.exception.DlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhanghonglong
 * @date 2020/5/9 9:58
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = DlockException.class)
    public String dlockErrorHandler(DlockException e) {
//        log.error("", e);
        return e.getMessage();
    }
}
