package com.shadow.simulation.provider;

import com.shadow.simulation.api.UserService;

/**
 * @author shadow
 * @create 2020-09-24
 * @description
 */
public class UserServiceImpl implements UserService {
	@Override
	public String sum(int num1, int num2) {
		System.out.println("num1 + num2 = " + (num1 + num2));
		return (num1 + num2) + "";
	}

	@Override
	public String say(String name) {
		System.out.println("receive name = " + name);
		return "http : hello " + name;
	}
}
