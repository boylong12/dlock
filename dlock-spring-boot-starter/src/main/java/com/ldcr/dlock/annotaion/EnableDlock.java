package com.ldcr.dlock.annotaion;

import com.ldcr.dlock.autoconfigure.DlockAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Dlock启动注解
 *
 * @author zhanghonglong
 * @date 2020/1/9 16:06
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({DlockAutoConfiguration.class, EnableDlockRegistrar.class})
@Documented
@Inherited
public @interface EnableDlock {

    /**
     * 关机时优雅释放锁标识
     * 建议在不支持优雅关机的情况下打开
     * 在支持优雅关机的情况下慎重打开。因为有可能会提前释放锁
     * springboot从2.3.0.RELEASE开始支持优雅关机
     *
     * @return
     */
    boolean gracefulRelease() default false;
}
