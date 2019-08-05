package com.zyf.boot.config;

import com.zyf.boot.filter.RepeatedlyReadRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 可重复使用、application/json 可调用 request.getParam 方法获取值的封装库
 */
@Configuration // 开启配置
@EnableConfigurationProperties(RepeatedlyReadRequestFilterProperties.class) // 开启使用映射实体对象
@ConditionalOnProperty(
        prefix = "zyf.starter.filter.repeatedly-read-request-filter",  // 存在配置前缀 zyfStarter.filter.repeatedlyReadRequestFilter
        matchIfMissing = true,  // 如果没有设置此值就默认使用matchIfMissing对应的值
        havingValue = "true", // 如果值匹配 havingValue，则该 configuration 不生效；为true则生效。
        value = "enabled"  // key
) //存在对应配置信息时初始化该配置类
public class RepeatedlyReadRequestFilterAutoConfiguration {

    @Autowired
    private RepeatedlyReadRequestFilterProperties repeatedlyReadRequestFilterProperties;

    /**
     * 缺失 RepeatedlyReadRequestFilter 实体 bean 时，初始化 RepeatedlyReadRequestFilter 并添加到 Spring Ioc
     */
    @Bean //创建 RepeatedlyReadRequestFilter 实体 bean
    public FilterRegistrationBean getRepeatedlyReadRequestFilter() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[1];
        System.out.println(stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "()");
        // 配置 JsonRequestParamFilter
        FilterRegistrationBean userFilter = new FilterRegistrationBean();
        userFilter.setName(repeatedlyReadRequestFilterProperties.getName());
        //userFilter.setOrder(13);
        userFilter.setOrder(Ordered.HIGHEST_PRECEDENCE);
        userFilter.addUrlPatterns(repeatedlyReadRequestFilterProperties.getUrls());
        userFilter.setFilter(new RepeatedlyReadRequestFilter());
        return userFilter;
    }

}