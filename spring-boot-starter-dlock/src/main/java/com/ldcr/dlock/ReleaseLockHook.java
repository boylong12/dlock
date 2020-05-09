package com.ldcr.dlock;

import com.ldcr.dlock.handler.LockHandler;
import com.ldcr.dlock.util.ConcurrentHashSet;
import com.ldcr.dlock.util.SpringContextTool;
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
    private static final Set<LockInfo> set = new ConcurrentHashSet<>(16);

    static {
        log.info("register ReleaseLockHook");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.warn("auto release Dlock--start={}", set.toString());
            LockHandler lockHandler = SpringContextTool.getApplicationContext().getBean(LockHandler.class);
            set.parallelStream().forEach(lockHandler::releaseLock);
            log.warn("auto release Dlock--end={}", set.toString());
        }));
    }

    public static boolean add(LockInfo lockInfo) {
        return set.add(lockInfo);
    }

    public static boolean remove(LockInfo lockInfo) {
        try {
            return set.remove(lockInfo);
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }
}
