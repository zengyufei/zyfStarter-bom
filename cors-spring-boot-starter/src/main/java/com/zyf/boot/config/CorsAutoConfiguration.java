package com.zyf.boot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 跨域
 * @author zengyufei
 * @date 2019-07-2019/7/26
 */
@Configuration
@EnableConfigurationProperties(CorsProperties.class) // 开启使用映射实体对象
@ConditionalOnProperty(
        prefix = "zyf.starter.cors",  // 存在配置前缀 zyf.starter.cors
        matchIfMissing = true,  // 如果没有设置此值就默认使用matchIfMissing对应的值
        havingValue = "true", // 如果值匹配 havingValue，则该 configuration 不生效；为true则生效。
        value = "enabled"  // key
) //存在对应配置信息时初始化该配置类
public class CorsAutoConfiguration {

    @Autowired
    private CorsProperties corsProperties;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList(corsProperties.getOrigins()));
        //请求常用的三种配置，*代表允许所有，当时你也可以自定义属性（比如header只能带什么，只能是post方式等等）
        corsConfiguration.addAllowedHeader(corsProperties.getHeader()); // 允许任何的head头部
        corsConfiguration.addAllowedOrigin(corsProperties.getOrigin()); // 允许任何域名使用
        corsConfiguration.addAllowedMethod(corsProperties.getMethod()); // 允许任何的请求方法
        corsConfiguration.setAllowCredentials(corsProperties.isCredentials());
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration(corsProperties.getPath(), corsConfiguration);
        return new CorsFilter(configSource);
    }
}
