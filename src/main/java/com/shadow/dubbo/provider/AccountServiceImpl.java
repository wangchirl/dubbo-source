package com.shadow.dubbo.provider;

import com.shadow.dubbo.api.AccountService;

/**
 * @author shadow
 * @create 2020-09-26
 * @description
 */
public class AccountServiceImpl implements AccountService {
	@Override
	public String say(String name) {
		System.out.println("received name " + name);
		return "hello dubbo " + name;
	}
}
