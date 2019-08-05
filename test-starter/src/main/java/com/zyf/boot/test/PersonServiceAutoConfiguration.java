package com.zyf.boot.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 开启配置
@EnableConfigurationProperties(PersonProperties.class) // 开启使用映射实体对象
@ConditionalOnClass(PersonService.class) // 存在 PersonService 时初始化该配置类
@ConditionalOnProperty(
        prefix = "zyf.starter.test.first",  // 存在配置前缀 zyfStarter.test.first
        // matchIfMissing = true,  // 如果没有设置此值就默认使用matchIfMissing对应的值
        havingValue = "true", // 如果值匹配 havingValue，则该 configuration 不生效；为true则生效。
        value = "enabled"  // key
) //存在对应配置信息时初始化该配置类
public class PersonServiceAutoConfiguration {

    @Autowired
    private PersonProperties properties;

    /**
     * 缺失 PersonService 实体 bean 时，初始化 PersonService 并添加到 Spring Ioc
     */
    @Bean //创建 PersonService 实体 bean
    @ConditionalOnMissingBean(PersonService.class)  // 当容器中没有指定Bean的情况下，自动配置 PersonService 类
    public PersonService personService() {
        PersonService personService = new PersonService(properties);
        return personService;
    }
}