package com.zyf.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置文件实体映射
 */
@Data
@ConfigurationProperties(prefix = "zyf.starter.filter.repeatedly-read-request-filter")
public class RepeatedlyReadRequestFilterProperties {

    private String name = "RepeatedlyReadRequestFilter";
    private String[] urls = {"/*"};
    private boolean enabled = true;

}