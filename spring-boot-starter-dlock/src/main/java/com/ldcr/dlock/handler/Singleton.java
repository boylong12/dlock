package com.ldcr.dlock.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * @author zhanghonglong
 * @date 2020/5/9 10:21
 */
@Slf4j
public class Singleton {
    private static volatile DefaultRedisScript<Boolean> instance = null;

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
}
