package com.ldcr.dlock.handler;

import com.ldcr.dlock.DLockInfo;
import com.ldcr.dlock.util.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁的redis实现（非公平锁）
 *
 * @author zhanghonglong
 * @date 2020/1/6 16:24
 */
@Slf4j
public abstract class BaseLockHandler {

    @Autowired
    private RedisTemplate<Object, Object> template;

    /**
     * 1. 通过setnx 向特定的key写入一个随机数，并设置失效时间，写入成功即加锁成功
     * 2. 注意点：
     * - 必须给锁设置一个失效时间            ----->    避免死锁
     * - 加锁时，每个节点产生一个随机字符串    ----->    避免锁误删
     * - 写入随机数与设置失效时间必须是同时    ----->    保证加锁的原子性
     * 使用：
     * SET key value NX PX 3000
     *
     * @param key        锁名称
     * @param value      存储的值
     * @param expireTime 过期时间，过期后自动释放锁 单位：毫秒
     * @return
     */
    public boolean tryLock(String key, String value, long expireTime) {
        Boolean result = template.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.MILLISECONDS);
        log.debug("addLock result={},key={},value={},expireTime={}", result, key, value, expireTime);
        if (result) {
            Singleton.getSingletonDLockInfoSet().add(new DLockInfo(key, value));
        }
        return result;
    }

    /**
     * 解锁：
     * 1 需要验证value是和加锁的一致才删除key。
     * 这是避免了一种情况：假设A获取了锁，过期时间30s，此时35s之后，锁已经自动释放了，A去释放锁，但是此时可能B获取了锁。A客户端就不能删除B的锁了。
     * 2 删除redis上的特定的key数据，要保证获取数据，判断一致以及删除数据三个操作是原子性
     * 执行如下lua脚本：
     * if redis.call('get', KEYS[1]) == ARGV[1] then
     * return redis.call('del', KEYS[1])
     * else
     * return 0
     * end
     *
     * @param key   锁名称
     * @param value 存储的值
     * @return
     */
    public boolean releaseLock(String key, String value) {
        Boolean result = template.execute(Singleton.getSingletonDefaultRedisScript(),
            Collections.singletonList(key), value);
        log.debug("releaseLock result={},key={},value={}", result, key, value);
        if (result) {
            Singleton.getSingletonDLockInfoSet().remove(new DLockInfo(key, value));
        }
        return result;
    }

}