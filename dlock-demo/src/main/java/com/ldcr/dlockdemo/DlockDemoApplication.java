package com.ldcr.dlockdemo;

import com.ldcr.dlock.annotaion.EnableDlock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDlock(gracefulRelease = true)
public class DlockDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DlockDemoApplication.class, args);
    }
}
