package com.ldcr.dlock.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 分布式锁的自动配置器
 *
 * @author zhanghonglong
 * @date 2020/1/6 18:15
 */
@Configuration
@ConditionalOnProperty(prefix = "com.ldcr.dlock", name = "enable", havingValue = "true")
@Import(DlockAutoConfiguration.class)
public class DlockOnPropertyAutoConfiguration {
}
