package com.wangsl;

import com.crafts.spring.CraftsApplicationContext;
import com.wangsl.config.AppConfig;
import com.wangsl.service.UserService;

public class Application {
	public static void main(String[] args) {
        CraftsApplicationContext context = new CraftsApplicationContext(AppConfig.class);
		String beanName = "userService";
		UserService userService = (UserService) context.getBean(beanName);
		UserService userService2 = (UserService) context.getBean(beanName);
		UserService userService3 = (UserService) context.getBean(beanName);

		System.out.println(userService);
		System.out.println(userService2);
		System.out.println(userService3);

		UserService userService4 = (UserService) context.getBean(UserService.class);
		System.out.println(userService4);
	}
}
