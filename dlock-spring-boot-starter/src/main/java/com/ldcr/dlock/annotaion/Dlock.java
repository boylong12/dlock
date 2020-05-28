package com.ldcr.dlock.annotaion;

import com.ldcr.dlock.exception.DlockOverCountException;
import com.ldcr.dlock.exception.DlockTimeoutException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式锁注解
 * 重试次数超过{@link #maxRetry()}抛出异常{@link DlockOverCountException}
 * 或者耗时大于{@link #timeout()}抛出异常{@link DlockTimeoutException}，建议业务端拦截该异常做相应处理
 *
 * @author zhanghonglong
 * @date 2020/1/6 15:41
 */
@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Dlock {

    /**
     * 锁名称前缀类型
     *
     * @return
     */
    KeyPreTypeEnum keyPreType() default KeyPreTypeEnum.PACKAGE;

    /**
     * 锁名称的自定义前缀
     * 只有{@link #keyPreType()}={@link KeyPreTypeEnum#CUSTOM}时才有效
     *
     * @return
     */
    String keyPre() default "";

    /**
     * 锁名称
     * 自动以全类名为前缀
     * 支持spel表达式 eg #user.id
     *
     * @return
     */
    String[] keys() default "";

    /**
     * 过期时间 单位：毫秒
     * 注意：过期时间一定要大于业务的执行时间
     *
     * @return
     */
    long expire() default 30000;

    /**
     * 获取锁的超时时间 单位：毫秒
     * 根据业务确定。由于会阻塞程序执行，不宜设置过长，尤其是在高并发场景下
     * 和{@link #maxRetry()}互斥，两者只会有一个生效
     * 优先级：{@link #maxRetry()}大于{@link #timeout()}
     *
     * @return
     */
    long timeout() default 3000;

    /**
     * 获取锁最大重试次数 大于0才生效
     * 根据业务确定。由于会阻塞程序执行，不宜设置过大，尤其是在高并发场景下
     * 和{@link #timeout()}互斥，两者只会有一个生效
     * 优先级：{@link #maxRetry()}大于{@link #timeout()}
     *
     * @return
     */
    int maxRetry() default 0;
}
