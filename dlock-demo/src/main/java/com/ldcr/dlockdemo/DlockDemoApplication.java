package com.ldcr.dlockdemo;

import com.ldcr.dlock.annotaion.EnableDlock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableDlock
public class DlockDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DlockDemoApplication.class, args);
    }

}
