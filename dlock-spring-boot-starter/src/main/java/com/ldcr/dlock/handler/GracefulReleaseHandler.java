package com.ldcr.dlock.handler;

import com.ldcr.dlock.DLockInfo;
import com.ldcr.dlock.util.ConcurrentHashSet;
import com.ldcr.dlock.util.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * 关机时优雅释放锁
 * 建议在不支持优雅关机的情况下打开
 * 在支持优雅关机的情况下慎重打开。因为有可能会提前释放锁
 * springboot从2.3.0.RELEASE开始支持优雅关机
 *
 * @author zhanghonglong
 * @date 2020/5/27 16:17
 */
@Slf4j
public class GracefulReleaseHandler implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private LockHandler lockHandler;

    public GracefulReleaseHandler() {
        log.info("GracefulReleaseHandler init");
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        ConcurrentHashSet<DLockInfo> set = Singleton.getSingletonDLockInfoSet();
        log.warn("auto release Dlock--start={}", set);
//        LockHandler lockHandler = DLockSpringContextTool.getApplicationContext().getBean(LockHandler.class);
        set.parallelStream().forEach(lockHandler::releaseLock);
        log.warn("auto release Dlock--end");
    }
}
