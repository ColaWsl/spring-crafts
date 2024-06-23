package com.wangsl.service;

import com.crafts.spring.annotation.Component;
import com.crafts.spring.ext.processor.BeanPostProcessor;

/**
 * spring 的使用者自定义实现 BeanPostProcessor
 */
// @Component
public class MyBeanPostProcessor implements BeanPostProcessor {

	// 所有 bean 的初始化都会执行
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		System.out.println("初始化前");
		if (beanName.equals("userService")) {
			((UserServiceImpl) bean).setId("100");
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		System.out.println("初始化后");
		return bean;
	}
}
