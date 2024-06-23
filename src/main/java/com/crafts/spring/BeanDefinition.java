package com.crafts.spring;

/**
 * 表示一个bean的定义
 */
public class BeanDefinition {



	private Class clazz;
	private String scope = "singleton"; // 默认为单例

	public BeanDefinition(Class clazz, String scope) {
		this.clazz = clazz;
		this.scope = scope;
	}


	public BeanDefinition() {

	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}
