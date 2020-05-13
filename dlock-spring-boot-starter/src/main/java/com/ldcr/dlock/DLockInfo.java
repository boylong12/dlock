package com.ldcr.dlock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author zhanghonglong
 * @date 2020/1/6 17:24
 */
@Data
@AllArgsConstructor
@ToString
public class DLockInfo {

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

    public DLockInfo(String key, String value) {
        this.key = key;
        this.value = value;
    }
}