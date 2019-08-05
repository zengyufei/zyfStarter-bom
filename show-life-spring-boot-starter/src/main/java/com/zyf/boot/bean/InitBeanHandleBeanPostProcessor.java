package com.zyf.boot.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 后置处理器BeanPostProcessor
 */
public class InitBeanHandleBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("getBeanInitOrder")) {
            System.out.println("第七步：BeanPostProcessor，对象" + beanName + "调用初始化方法之前的数据： " + bean.toString());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("getBeanInitOrder")) {
            System.out.println("第十一步：BeanPostProcessor，对象" + beanName + "调用初始化方法之后的数据：" + bean.toString());
        }
        return bean;
    }
}