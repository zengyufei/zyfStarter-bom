package com.zyf.boot.test;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置文件实体映射
 */
@Data
@ConfigurationProperties(prefix = "zyf.starter.test.first")
public class PersonProperties {

    // 姓名
    private String name;
    // 年龄
    private int age;
    // 性别
    private String sex = "M";

    private boolean enabled;

}