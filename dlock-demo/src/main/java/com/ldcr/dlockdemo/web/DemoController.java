package com.ldcr.dlockdemo.web;

import com.ldcr.dlockdemo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanghonglong
 * @date 2020/1/10 17:06
 */
@RestController
@RequestMapping("/dlock/demo/")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/say-hello")
    public String sayHello(@RequestParam String userId) {
        return demoService.sayHello(userId);
    }

    @GetMapping("/say-hello2")
    public String sayHello2(@RequestParam String userId) {
        return demoService.sayHello2(userId);
    }
}
