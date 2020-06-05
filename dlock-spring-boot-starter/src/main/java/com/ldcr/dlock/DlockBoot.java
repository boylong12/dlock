package com.ldcr.dlock;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

/**
 * @author zhanghonglong
 * @date 2020/6/5 9:20
 */
@Slf4j
public class DlockBoot {

    private Banner.Mode bannerMode = Banner.Mode.CONSOLE;
    @Setter
    private ResourceLoader resourceLoader;
    @Setter
    private ConfigurableEnvironment environment;

    public DlockBoot(ResourceLoader resourceLoader, ConfigurableEnvironment environment) {
        this.resourceLoader = resourceLoader;
        this.environment = environment;
    }

    @PostConstruct
    private Banner printBanner() {
        if (this.bannerMode == Banner.Mode.OFF) {
            return null;
        }
        DlockBannerPrinter bannerPrinter = new DlockBannerPrinter(resourceLoader, null);
        if (this.bannerMode == Banner.Mode.LOG) {
            return bannerPrinter.print(environment, this.getClass(), log);
        }
        return bannerPrinter.print(environment, this.getClass(), System.out);
    }
}
