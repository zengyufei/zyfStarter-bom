package com.zyf.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置文件实体映射
 */
@Data
@ConfigurationProperties(prefix = "zyf.starter.test.life")
public class LifeConsoleProperties {

    private boolean enabled;

}