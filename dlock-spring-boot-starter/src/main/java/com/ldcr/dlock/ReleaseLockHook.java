package com.ldcr.dlock;

import com.ldcr.dlock.handler.LockHandler;
import com.ldcr.dlock.util.ConcurrentHashSet;
import com.ldcr.dlock.util.DLockSpringContextTool;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * 解决进程异常退出导致的Dlock锁没有释放的问题
 * 比如：进程被销毁
 *
 * @author zhanghonglong
 * @date 2020/1/6 15:58
 */
@Slf4j
public class ReleaseLockHook {
    private static final Set<DLockInfo> set = new ConcurrentHashSet<>(16);

    static {
        log.info("register ReleaseLockHook");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.warn("auto release Dlock--start={}", set.toString());
            LockHandler lockHandler = DLockSpringContextTool.getApplicationContext().getBean(LockHandler.class);
            set.parallelStream().forEach(lockHandler::releaseLock);
            log.warn("auto release Dlock--end={}", set.toString());
        }));
    }

    public static boolean add(DLockInfo dLockInfo) {
        return set.add(dLockInfo);
    }

    public static boolean remove(DLockInfo dLockInfo) {
        try {
            return set.remove(dLockInfo);
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }
}
