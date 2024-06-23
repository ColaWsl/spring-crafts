package com.wangsl.service;

import com.crafts.spring.InitializingBean;
import com.crafts.spring.annotation.Autowird;
import com.crafts.spring.annotation.Component;
import com.crafts.spring.aware.BeanNameAware;

@Component
// @Scope("prototype")
public class UserService implements BeanNameAware, InitializingBean {

	@Autowird
	private OrderService orderService;

	private String beanName;

	public void test(){
		System.out.println("userService: " + this);
		System.out.println("orderService: " + orderService);
		System.out.println("beanName: " + beanName);
	}


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
}
