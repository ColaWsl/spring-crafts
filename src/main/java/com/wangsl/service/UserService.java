package com.wangsl.service;

import com.crafts.spring.annotation.Component;

@Component()
// @Scope("prototype")
public class UserService {
	public void list() {
		System.out.println("user1 user2 user3");
	}
}
