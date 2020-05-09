package com.ldcr.dlock.annotaion;

import org.aopalliance.aop.Advice;

/**
 * Dlock注解处理器
 *
 * @author zhanghonglong
 * @date 2020/1/6 18:31
 */
public class DlockAnnotationAdvisor extends AnnotationAdvisor {
    public DlockAnnotationAdvisor(Advice advice) {
        super(Dlock.class, advice);
    }
}
