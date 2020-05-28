package com.ldcr.dlock.annotaion;


import com.ldcr.dlock.handler.GracefulReleaseHandler;
import com.ldcr.dlock.util.AnnotationConfigUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author zhanghonglong
 * @date 2020/5/28 9:09
 */
public class EnableDlockRegistrar implements ImportBeanDefinitionRegistrar {
    private static final String GRACEFUL_RELEASE_HANDLER_BEAN_NAME = "com.ldcr.dlock.handler.GracefulReleaseHandler";

    @Override
    public void registerBeanDefinitions(
        AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes enableDlock =
            AnnotationConfigUtils.attributesFor(importingClassMetadata, EnableDlock.class);
        if (enableDlock != null) {
            if (enableDlock.getBoolean("gracefulRelease")) {
                BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(GracefulReleaseHandler.class)
                    .setRole(BeanDefinition.ROLE_INFRASTRUCTURE).getBeanDefinition();
                registry.registerBeanDefinition(GRACEFUL_RELEASE_HANDLER_BEAN_NAME, beanDefinition);
            }
        }
    }
}
