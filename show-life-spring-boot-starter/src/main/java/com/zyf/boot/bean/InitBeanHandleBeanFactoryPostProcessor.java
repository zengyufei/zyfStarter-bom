package com.zyf.boot.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 属性处理器BeanFactoryPostProcessor
 */
public class InitBeanHandleBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("第一步：调用 InitBeanHandleBeanFactoryPostProcessor implements BeanFactoryPostProcessor 的 postProcessBeanFactory");
        BeanDefinition getBeanInitOrder = beanFactory.getBeanDefinition("getBeanInitOrder");
        MutablePropertyValues propertyValues = getBeanInitOrder.getPropertyValues();
        // 调用 setName 方法
        propertyValues.addPropertyValue("name", "mateEgg");
    }
}