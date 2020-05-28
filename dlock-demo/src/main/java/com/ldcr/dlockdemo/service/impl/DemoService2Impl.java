package com.ldcr.dlockdemo.service.impl;

import com.ldcr.dlock.annotaion.Dlock;
import com.ldcr.dlock.annotaion.KeyPreTypeEnum;
import com.ldcr.dlockdemo.service.DemoService2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhanghonglong
 * @date 2020/1/10 17:07
 */
@Slf4j
@Service
public class DemoService2Impl implements DemoService2 {

    @Dlock(keys = "#userId", maxRetry = 1)
    @Override
    public String sayHello(String userId) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        return userId;
    }

    @Dlock(keyPreType = KeyPreTypeEnum.CUSTOM, keyPre = "sayHello2", keys = "#userId", expire = 4000)
    @Override
    public String sayHello2(String userId) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.error("", e);
        }
        return userId;
    }
}
