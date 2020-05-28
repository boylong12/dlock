package com.ldcr.dlock.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhanghonglong
 * @date 2020/5/27 17:09
 */
@Configuration
@Import(DlockAutoConfiguration.class)
@ConditionalOnProperty(prefix = "com.ldcr.dlock", name = "enable", havingValue = "true")
public class DlockAutoConfigurationOnProperty {
}
