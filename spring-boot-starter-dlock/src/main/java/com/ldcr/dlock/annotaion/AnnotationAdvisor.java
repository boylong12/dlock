package com.ldcr.dlock.annotaion;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;

/**
 * 自定义注解处理器
 *
 * @author zhanghonglong
 * @date 2020/1/6 17:59
 */
public class AnnotationAdvisor extends AbstractPointcutAdvisor {

    private static final long serialVersionUID = -4387952132260425857L;
    private final Pointcut pointcut;
    private final Advice advice;

    public AnnotationAdvisor(Class<? extends Annotation> annotationType, Advice advice) {
        this.pointcut = buildPointcut(annotationType);
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        Assert.notNull(this.pointcut, "'pointcut' must not be null");
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        Assert.notNull(this.advice, "'advice' must not be null");
        return this.advice;
    }

    /**
     * 对于类和方法上都可以添加注解的情况,类上的注解,最终会将注解绑定到每个方法上
     *
     * @param annotationType
     * @return
     */
    private Pointcut buildPointcut(Class<? extends Annotation> annotationType) {
        Assert.notNull(annotationType, "'annotationTypes' must not be null");
        //类级别
        Pointcut cpc = new AnnotationMatchingPointcut(annotationType, true);
        ComposablePointcut result = new ComposablePointcut(cpc);
        //方法级别
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(annotationType);
        return result.union(mpc);
    }
}
