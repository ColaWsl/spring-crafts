package com.crafts.spring.ext.processor;

public interface BeanPostProcessor {

	// 初始化前
	Object postProcessBeforeInitialization(Object bean, String beanName);

	// 初始化后
	Object postProcessAfterInitialization(Object bean, String beanName);
}
