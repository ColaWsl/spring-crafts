package com.wangsl;

import com.crafts.spring.CraftsApplicationContext;
import com.wangsl.config.AppConfig;
import com.wangsl.service.OrderService;
import com.wangsl.service.UserService;
import com.wangsl.service.UserServiceImpl;

public class Application {
	public static void main(String[] args) {
        CraftsApplicationContext context = new CraftsApplicationContext(AppConfig.class);

		/**
		 * 通过名称和类型获取bean
		String beanName = "userService";
		UserService userService = (UserService) context.getBean(beanName);
		UserService userService2 = (UserService) context.getBean(UserService.class);
		System.out.println(userService);
		System.out.println(userService2);
	    **/


		UserService userService = (UserService) context.getBean(UserServiceImpl.class);
	}
}
