package com.ldcr.dlock.util;

import com.ldcr.dlock.annotaion.Dlock;
import com.ldcr.dlock.annotaion.KeyPreTypeEnum;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 分布式锁Key生成器
 *
 * @author zhanghonglong
 * @date 2020/1/6 18:23
 */
public class LockKeyGenerator {

    private static final ParameterNameDiscoverer NAME_DISCOVERER = new DefaultParameterNameDiscoverer();
    /**
     * 线程安全
     */
    private static final ExpressionParser PARSER = new SpelExpressionParser();

    public String getKey(MethodInvocation invocation, Dlock dlock) {
        Method method = invocation.getMethod();
        StringBuilder sb = new StringBuilder(32);
        if (dlock.keyPreType() == KeyPreTypeEnum.PACKAGE) {
            sb.append(getShotClazzName(method));
        } else {
            sb.append(dlock.keyPre());
        }
        if (dlock.keys().length > 1 || !"".equals(dlock.keys()[0])) {
            sb.append("@").append(getSpelDefinitionKey(dlock.keys(), method, invocation.getArguments()));
        }
        return sb.toString();
    }

    private String getShotClazzName(Method method) {
        ConcurrentHashMap<Method, String> clazzNameMap = Singleton.getSingletonClazzNameMap();
        if (clazzNameMap.containsKey(method)) {
            return clazzNameMap.get(method);
        }
        Class<?> clazz = method.getDeclaringClass();
        String pName = clazz.getPackage().getName();
        StringBuilder sj = new StringBuilder(32);
        Arrays.stream(pName.split("\\.")).forEach(e -> sj.append(e.substring(0, 1)).append("."));
        String methodName = sj.append(clazz.getSimpleName()).append("#").append(method.getName()).toString();
        clazzNameMap.put(method, methodName);
        return methodName;
    }

    private String getSpelDefinitionKey(String[] definitionKeys, Method method, Object[] parameterValues) {
        EvaluationContext context = new MethodBasedEvaluationContext(null, method, parameterValues, NAME_DISCOVERER);
        List<String> definitionKeyList = new ArrayList<>(definitionKeys.length);
        for (String definitionKey : definitionKeys) {
            if (definitionKey != null && !definitionKey.isEmpty()) {
                String key = PARSER.parseExpression(definitionKey).getValue(context).toString();
                definitionKeyList.add(key);
            }
        }
        return StringUtils.collectionToDelimitedString(definitionKeyList, "-", "", "");
    }

}
