package com.zyf.boot.config;

import com.zyf.boot.bean.BeanInitOrder;
import com.zyf.boot.bean.InitBeanHandleBeanFactoryPostProcessor;
import com.zyf.boot.bean.InitBeanHandleBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 声明周期查看
 * @author zengyufei
 * @date 2019-07-2019/7/26
 */
@Configuration
@EnableConfigurationProperties(LifeConsoleProperties.class) // 开启使用映射实体对象
@ConditionalOnProperty(
        prefix = "zyf.starter.test.life",  // 存在配置前缀 zyfStarter.filter.repeatedlyReadRequestFilter
        matchIfMissing = true,  // 如果没有设置此值就默认使用matchIfMissing对应的值
        havingValue = "true", // 如果值匹配 havingValue，则该 configuration 不生效；为true则生效。
        value = "enabled"  // key
) //存在对应配置信息时初始化该配置类
public class LifeConsoleAutoConfiguration {

    /**
     * 启动顺序查看类
     */
    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public BeanInitOrder getBeanInitOrder() {
        return new BeanInitOrder();
    }

    /**
     * 后置处理器BeanPostProcessor
     */
    @Bean
    public InitBeanHandleBeanPostProcessor getInitBeanHandle() {
        return new InitBeanHandleBeanPostProcessor();
    }

    /**
     * 属性处理器BeanFactoryPostProcessor
     */
    @Bean
    public static InitBeanHandleBeanFactoryPostProcessor getInitBeanHandleBeanFactoryPostProcessor() {
        return new InitBeanHandleBeanFactoryPostProcessor();
    }
}
