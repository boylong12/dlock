package com.ldcr.dlock.exception;

import lombok.Getter;

/**
 * 加锁失败异常
 *
 * @author zhanghonglong
 * @date 2020/1/7 9:43
 */
@Getter
public class DlockException extends RuntimeException {
    /**
     * 锁名称
     */
    private String key;

    /**
     * 锁值
     */
    private String value;

    /**
     * 过期时间
     */
    private Long expire;

    /**
     * 获取锁超时时间 毫秒
     */
    private Long timeout;

    /**
     * 获取锁最大重试次数
     */
    private Integer maxRetry;

    /**
     * 获取锁实际消耗时间 毫秒
     */
    private long elapsedTime;

    /**
     * 获取锁实际重试次数
     */
    private int retryCount;

    public DlockException(String preMsg, String key, String value, Long expire, Long timeout, Integer maxRetry,
                          long elapsedTime, int retryCount) {
        super(preMsg +
            ", try " + retryCount + " times, elapsed " + elapsedTime + " ms" +
            ", key=" + key);
        this.key = key;
        this.value = value;
        this.expire = expire;
        this.timeout = timeout;
        this.maxRetry = maxRetry;
        this.elapsedTime = elapsedTime;
        this.retryCount = retryCount;
    }

    public DlockException(String message) {
        super(message);
    }

    public DlockException(String message, Throwable cause) {
        super(message, cause);
    }

    public DlockException(Throwable cause) {
        super(cause);
    }

    public DlockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
