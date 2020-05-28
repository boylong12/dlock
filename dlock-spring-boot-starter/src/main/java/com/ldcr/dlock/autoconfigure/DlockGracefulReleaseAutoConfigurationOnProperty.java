package com.ldcr.dlock.autoconfigure;

import com.ldcr.dlock.handler.GracefulReleaseHandler;
import com.ldcr.dlock.handler.LockHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhanghonglong
 * @date 2020/5/27 17:09
 */
@Configuration
@ConditionalOnProperty(prefix = "com.ldcr.dlock", name = "gracefulRelease", havingValue = "true")
public class DlockGracefulReleaseAutoConfigurationOnProperty {
    @Bean
    @ConditionalOnBean(LockHandler.class)
    @ConditionalOnMissingBean
    public GracefulReleaseHandler gracefulReleaseHandler() {
        return new GracefulReleaseHandler();
    }
}
