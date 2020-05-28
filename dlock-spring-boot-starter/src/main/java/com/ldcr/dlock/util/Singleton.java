package com.ldcr.dlock.util;

import com.ldcr.dlock.DLockInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhanghonglong
 * @date 2020/5/9 10:21
 */
@Slf4j
public class Singleton {
    private static volatile DefaultRedisScript<Boolean> instance = null;
    private static volatile ConcurrentHashMap<Method, String> clazzNameMap = null;
    private static volatile ConcurrentHashSet<DLockInfo> dLockInfoSet = null;

    /**
     * 获取单例对象
     *
     * @return
     */
    public static DefaultRedisScript<Boolean> getSingletonDefaultRedisScript() {
        if (instance == null) {
            synchronized (DefaultRedisScript.class) {
                if (instance == null) {
                    String scriptText = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                    instance = new DefaultRedisScript<>(scriptText, Boolean.class);
                    log.debug("DefaultRedisScript instance created");
                }
            }
        }
        return instance;
    }

    /**
     * 获取单例对象
     *
     * @return
     */
    public static ConcurrentHashMap<Method, String> getSingletonClazzNameMap() {
        if (clazzNameMap == null) {
            synchronized (DefaultRedisScript.class) {
                if (clazzNameMap == null) {
                    clazzNameMap = new ConcurrentHashMap(128);
                    log.debug("clazzNameMap instance created");
                }
            }
        }
        return clazzNameMap;
    }

    /**
     * 获取单例对象
     *
     * @return
     */
    public static ConcurrentHashSet<DLockInfo> getSingletonDLockInfoSet() {
        if (dLockInfoSet == null) {
            synchronized (DefaultRedisScript.class) {
                if (dLockInfoSet == null) {
                    dLockInfoSet = new ConcurrentHashSet<>(16);
                    log.debug("dLockInfoSet instance created");
                }
            }
        }
        return dLockInfoSet;
    }
}
