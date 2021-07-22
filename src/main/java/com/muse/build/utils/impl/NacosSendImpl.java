package com.muse.build.utils.impl;

import com.muse.build.config.NacosAutoConfig;
import com.muse.build.utils.NacosSend;

import java.util.concurrent.TimeUnit;

public class NacosSendImpl implements NacosSend {

    final NacosAutoConfig nacosAutoConfig;

    public NacosSendImpl(NacosAutoConfig nacosAutoConfig) {
        this.nacosAutoConfig = nacosAutoConfig;
    }

    @Override
    public void send() {
        nacosAutoConfig.configFile().config();
        for (String s : nacosAutoConfig.configFile().config()) {
            nacosAutoConfig.threadPools().submitTask(() -> {
                String url = nacosAutoConfig.getNacosAddr() + "&content=" + s.split("=", 2)[1] + "&dataId=" + s.split("=", 2)[0];
                nacosAutoConfig.restTemplate.postForObject(url, null, Object.class);
            });
        }
        nacosAutoConfig.threadPools().submitTask(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(1);
        });
    }

    @Override
    public boolean send(String... configRows) {
        for (String s : configRows) {
            String url = nacosAutoConfig.getNacosAddr() + "&content=" + s.split("=", 2)[1] + "&dataId=" + s.split("=", 2)[0];
            nacosAutoConfig.restTemplate.postForObject(url, null, Object.class);
        }
        return true;
    }
}
