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
@Import(DlockAutoConfiguration.class)
@Documented
@Inherited
public @interface EnableDlock {
}
