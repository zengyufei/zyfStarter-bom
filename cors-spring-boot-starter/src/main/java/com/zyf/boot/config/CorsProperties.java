package com.zyf.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置文件实体映射
 */
@Data
@ConfigurationProperties(prefix = "zyf.starter.cors")
public class CorsProperties {

    private String[] origins = {"*"};
    private String header = "*";
    private String origin = "*";
    private String method = "*";
    private boolean credentials = true;
    private String path = "/**";

    private boolean enabled;

}