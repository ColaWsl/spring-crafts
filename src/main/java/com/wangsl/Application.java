package com.wangsl;

import com.crafts.spring.CraftsApplicationContext;
import com.wangsl.config.AppConfig;

public class Application {
	public static void main(String[] args) {
        CraftsApplicationContext context = new CraftsApplicationContext(AppConfig.class);
		context.getBean("userService");
	}
}
