package com.shadow.provider;

import com.shadow.api.UserService;

/**
 * @author shadow
 * @create 2020-09-24
 * @description
 */
public class UserServiceImpl implements UserService {
	@Override
	public String say(String name) {
		return "http : hello " + name;
	}
}
