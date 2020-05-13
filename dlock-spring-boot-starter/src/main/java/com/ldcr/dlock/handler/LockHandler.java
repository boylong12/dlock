package com.ldcr.dlock.handler;

import com.ldcr.dlock.DLockInfo;
import com.ldcr.dlock.exception.DlockException;
import com.ldcr.dlock.exception.DlockOverCountException;
import com.ldcr.dlock.exception.DlockTimeoutException;
import com.ldcr.dlock.util.LockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import static org.springframework.util.Assert.notNull;

/**
 * 分布式锁处理器
 *
 * @author zhanghonglong
 * @date 2020/1/6 17:22
 */
@Slf4j
public class LockHandler extends BaseLockHandler {
    private static final String PROCESS_ID = LockUtil.getLocalMAC() + LockUtil.getJvmPid();

    /**
     * 加锁
     * timeout和maxRetry互斥，两者只会有一个生效,优先级：maxRetry大于timeout
     * 重试次数超过maxRetry或者耗时大于timeout抛出异常{@link DlockException}，建议业务端拦截该异常做相应处理
     *
     * @param key      锁名称
     * @param expire   过期时间 单位：毫秒
     * @param timeout  获取锁的超时时间 单位：毫秒
     * @param maxRetry 获取锁最大重试次数 大于0才生效
     * @return 锁信息
     * @throws InterruptedException
     */
    public DLockInfo lock(String key, long expire, long timeout, int maxRetry) throws InterruptedException {
        notNull(key, "'key' must not be null");
        long start = System.currentTimeMillis();
        int retryCount = 0;
        String value = PROCESS_ID + Thread.currentThread().getId();
        if (maxRetry > 0) {
            while (retryCount < maxRetry) {
                boolean result = lock(key, value, expire);
                retryCount++;
                if (result) {
                    return new DLockInfo(key, value, expire, timeout, maxRetry, System.currentTimeMillis() - start, retryCount);
                }
                if (retryCount < maxRetry) {
                    Thread.sleep(50);
                }
            }
            // 加锁失败
            throw new DlockOverCountException(key, value, expire, timeout, maxRetry, System.currentTimeMillis() - start, retryCount);
        } else {
            Assert.isTrue(timeout > 0, "'timeout' must more than 0");
            while (System.currentTimeMillis() - start < timeout) {
                boolean result = lock(key, value, expire);
                retryCount++;
                if (result) {
                    return new DLockInfo(key, value, expire, timeout, maxRetry, System.currentTimeMillis() - start, retryCount);
                }
                if (System.currentTimeMillis() - start < timeout) {
                    Thread.sleep(50);
                }
            }
            // 加锁失败
            throw new DlockTimeoutException(key, value, expire, timeout, maxRetry, System.currentTimeMillis() - start, retryCount);
        }
    }

    /**
     * 释放锁
     *
     * @param dLockInfo
     * @return
     */
    public boolean releaseLock(DLockInfo dLockInfo) {
        notNull(dLockInfo, "'lockInfo' must not be null");
        return releaseLock(dLockInfo.getKey(), dLockInfo.getValue());
    }
}
