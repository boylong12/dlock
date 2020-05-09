package com.ldcr.dlock.aop;

import com.ldcr.dlock.annotaion.Dlock;
import com.ldcr.dlock.handler.LockHandler;
import com.ldcr.dlock.util.LockKeyGenerator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * 分布式锁的拦截器
 *
 * @author zhanghonglong
 * @date 2020/1/6 18:06
 */
@Slf4j
public class DlockInterceptor implements MethodInterceptor {

    @Setter
    private LockHandler lockHandler;

    private final LockKeyGenerator lockKeyGenerator = new LockKeyGenerator();

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        //获取目标类
        Class<?> targetClass = (methodInvocation.getThis() != null ? AopUtils.getTargetClass(methodInvocation.getThis()) : null);
        //获取指定方法
        Method specificMethod = ClassUtils.getMostSpecificMethod(methodInvocation.getMethod(), targetClass);
        //获取真正执行的方法,可能存在桥接方法
        final Method userDeclaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
        //获取方法上注解
        Dlock dlock = AnnotatedElementUtils.findMergedAnnotation(userDeclaredMethod, Dlock.class);
        if (dlock == null) {
            dlock = AnnotatedElementUtils.findMergedAnnotation(userDeclaredMethod.getDeclaringClass(), Dlock.class);
        }
        if (dlock == null) {
            //方法上没有Dlock注解，执行具体业务逻辑
            return methodInvocation.proceed();
        } else {
            com.ldcr.dlock.LockInfo lockInfo = null;
            try {
                String key = lockKeyGenerator.getKey(methodInvocation, dlock);
                lockInfo = lockHandler.lock(key, dlock.expire(), dlock.timeout(), dlock.maxRetry());
                log.debug("{}", lockInfo);
                // 加锁成功，执行具体业务逻辑
                return methodInvocation.proceed();
            } finally {
                if (null != lockInfo) {
                    lockHandler.releaseLock(lockInfo);
                }
            }
        }
    }
}
