package com.wangsl.service;

import com.crafts.spring.ext.init.InitializingBean;
import com.crafts.spring.annotation.Autowird;
import com.crafts.spring.annotation.Component;
import com.crafts.spring.ext.aware.BeanNameAware;

@Component
// @Scope("prototype")
public class UserServiceImpl implements BeanNameAware, InitializingBean, UserService {


	private String beanName;

	private String id;


	// spring容器在创建bean的时候会回调该方法
	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	// 初始化
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("====================================================================");
		System.out.println("初始化 init");
		System.out.println("====================================================================");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void test() {
		System.out.println("userService: " + this);
		// System.out.println("orderService: " + orderService);
		System.out.println("beanName: " + beanName);
	}
}
