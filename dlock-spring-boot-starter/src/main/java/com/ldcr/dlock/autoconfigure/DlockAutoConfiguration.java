package com.ldcr.dlock.autoconfigure;

import com.ldcr.dlock.annotaion.DlockAnnotationAdvisor;
import com.ldcr.dlock.aop.DlockInterceptor;
import com.ldcr.dlock.handler.LockHandler;
import com.ldcr.dlock.util.DLockSpringContextTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁的自动配置器
 *
 * @author zhanghonglong
 * @date 2020/1/6 18:15
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "com.ldcr.dlock", name = "enable", havingValue = "true")
public class DlockAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DlockAnnotationAdvisor dlockAnnotationAdvisor(DlockInterceptor dlockInterceptor) {
        return new DlockAnnotationAdvisor(dlockInterceptor);
    }

    @Bean
    @ConditionalOnMissingBean
    public DLockSpringContextTool springContextTool(ApplicationContext applicationContext) {
        DLockSpringContextTool springContextTool = new DLockSpringContextTool();
        springContextTool.setApplicationContext(applicationContext);
        return springContextTool;
    }

    @Bean
    @ConditionalOnMissingBean
    public LockHandler lockHandler() {
        return new LockHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(value = {DlockAnnotationAdvisor.class, DLockSpringContextTool.class})
    public DlockInterceptor dlockInterceptor(LockHandler lockHandler) {
        DlockInterceptor dlockInterceptor = new DlockInterceptor();
        dlockInterceptor.setLockHandler(lockHandler);
        log.info("   ___   __   ____   _____ __ __");
        log.info("  / _ \\ / /  / __ \\ / ___// //_/");
        log.info(" / // // /__/ /_/ // /__ / ,<   ");
        log.info("/____//____/\\____/ \\___//_/|_|  ");
        log.info("started dlock");
        return dlockInterceptor;
    }
}
