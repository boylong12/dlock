package com.ldcr.dlockdemo.service.impl;

import com.ldcr.dlock.annotaion.Dlock;
import com.ldcr.dlockdemo.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * @author zhanghonglong
 * @date 2020/1/10 17:07
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Dlock(keys = "#userId", maxRetry = 1)
    @Override
    public String sayHello(String userId) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userId;
    }

    @Dlock(keys = "#userId", expire = 4000)
    @Override
    public String sayHello2(String userId) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userId;
    }
}
