package com.muse.build;

import com.muse.build.config.NacosAutoConfig;
import com.muse.build.utils.NacosSend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

@SpringBootApplication
public class AppConfig {

    @Resource
    NacosSend nacosSend;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(AppConfig.class, args);
        Thread.currentThread().suspend();
    }

    @PostConstruct
    public void initConfig() {
        nacosSend.send();
    }
}
