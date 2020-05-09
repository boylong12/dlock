package com.ldcr.dlock.exception;

/**
 * 加锁超过次数(大于maxRetry)异常
 *
 * @author zhanghonglong
 * @date 2020/1/9 17:29
 */
public class DlockOverCountException extends DlockException {
    public DlockOverCountException(String key, String value, Long expire, Long timeout, Integer maxRetry,
                                   long elapsedTime, int retryCount) {
        super("lock failed(over the maxRetry)", key, value, expire, timeout, maxRetry, elapsedTime, retryCount);
    }

    public DlockOverCountException(String message) {
        super(message);
    }

    public DlockOverCountException(String message, Throwable cause) {
        super(message, cause);
    }

    public DlockOverCountException(Throwable cause) {
        super(cause);
    }

    public DlockOverCountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
