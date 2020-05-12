package com.ldcr.dlock.exception;

/**
 * 加锁超时(大于timeout)异常
 *
 * @author zhanghonglong
 * @date 2020/1/9 17:29
 */
public class DlockTimeoutException extends DlockException {
    public DlockTimeoutException(String key, String value, Long expire, Long timeout, Integer maxRetry,
                                 long elapsedTime, int retryCount) {
        super("lock failed(timeout)", key, value, expire, timeout, maxRetry, elapsedTime, retryCount);
    }

    public DlockTimeoutException(String message) {
        super(message);
    }

    public DlockTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public DlockTimeoutException(Throwable cause) {
        super(cause);
    }

    public DlockTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
