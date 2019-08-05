package com.zyf.boot.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class BeanInitOrder implements InitializingBean, DisposableBean, BeanNameAware, BeanFactoryAware, ApplicationContextAware {

    private String name;

    public BeanInitOrder() {
        System.out.println("第二步：执行 BeanInitOrder 类的无参构造函数");
    }

    public void initMethod() {
        System.out.println("第十步：调用initMethod方法");
    }

    public void destroyMethod() {
        System.out.println("执行bean定义的destroyMethod方法");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("第八步：执行PostConstruct方法");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("执行PreDestroy方法");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("第五步：注入BeanFactory");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("第四步：注入BeanName");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("第六步：注入ApplicationContext");
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("执行量DisposableBean的destroy方法");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("第九步：调用 afterPropertiesSet 方法");
    }

    public void setName(String name){
        System.out.println("第三步：调用 setName 方法");
        this.name=name;
    }

    public String getName() {
        return name;
    }

}