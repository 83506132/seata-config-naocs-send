package com.muse.build.config;

import com.muse.build.utils.ConfigFile;
import com.muse.build.utils.NacosSend;
import com.muse.build.utils.impl.ConfigFileImpl;
import com.muse.build.utils.impl.NacosSendImpl;
import com.sun.jmx.snmp.tasks.ThreadService;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@ConfigurationProperties(prefix = "spring.nacos")
@Configuration
@Setter
@Getter
@Primary
public class NacosAutoConfig {
    public final RestTemplate restTemplate = new RestTemplate();

    private File fileConfig;
    private String username;
    private String password;
    private String host;
    private String group;
    private String namespaceId;
    private String desc;
    private Integer port;
    private int pool = 1;

    public String getNacosAddr() {
        return "http://" + host + ":" + port + "/nacos/v1/cs/configs?namespaceId=" + namespaceId + "&tenant=" + namespaceId + "&group=" + group + "&username=" + username + "&password=" + password + "&desc=" + desc;
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(value = ConfigFile.class)
    public ConfigFile configFile() {
        return new ConfigFileImpl(fileConfig);
    }


    @Bean
    @Primary
    @DependsOn(value = "configFile")
    @ConditionalOnMissingBean(value = NacosSend.class)
    public NacosSend nacosSend() {
        return new NacosSendImpl(this);
    }

    @Bean
    public ThreadService threadPools() {
        return new ThreadService(pool);
    }
}
