package com.ldcr.dlockdemo.service.impl;

import com.ldcr.dlock.annotaion.Dlock;
import com.ldcr.dlockdemo.service.DemoService;
import com.ldcr.dlockdemo.service.DemoService2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhanghonglong
 * @date 2020/1/10 17:07
 */
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    private DemoService2 demoService2;

    @Dlock(maxRetry = 1)
    @Override
    public String sayHello(String userId) {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            log.error("", e);
        }
//        return demoService2.sayHello2(userId);
        return userId;
    }

    @Dlock(keys = "#userId", expire = 4000)
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
