package com.ldcr.dlock.autoconfigure;

import com.ldcr.dlock.annotaion.DlockAnnotationAdvisor;
import com.ldcr.dlock.aop.DlockInterceptor;
import com.ldcr.dlock.handler.LockHandler;
import com.ldcr.dlock.util.SpringContextTool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式锁的自动配置器
 *
 * @author zhanghonglong
 * @date 2020/1/6 18:15
 */
@Configuration
public class DlockAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LockHandler lockHandler() {
        return new LockHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public DlockInterceptor dlockInterceptor(LockHandler lockHandler) {
        DlockInterceptor dlockInterceptor = new DlockInterceptor();
        dlockInterceptor.setLockHandler(lockHandler);
        return dlockInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DlockAnnotationAdvisor dlockAnnotationAdvisor(DlockInterceptor dlockInterceptor) {
        return new DlockAnnotationAdvisor(dlockInterceptor);
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringContextTool springContextTool(ApplicationContext applicationContext) {
        SpringContextTool springContextTool = new SpringContextTool();
        springContextTool.setApplicationContext(applicationContext);
        return springContextTool;
    }
}
