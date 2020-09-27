package com.shadow.simulation.framework.dubbo.client;

import com.shadow.simulation.api.UserService;
import com.shadow.simulation.framework.dubbo.DubboProxyFactory;
import com.shadow.simulation.framework.support.URL;

import java.util.Date;

/**
 * @author shadow
 * @create 2020-09-26
 * @description
 */
public class DubboClientDemo {
	public static void main(String[] args) {

		// 版本 1
		URL url = new URL("127.0.0.1", 8080);
		/*Invocation invocation = new Invocation(UserService.class.getName(), "say", new Class[]{String.class}, new Object[]{"Shadow 2020"});
		DubboProtocol client = new DubboProtocol();
		System.out.println(client.send(url, invocation));*/

		// 版本 2
		UserService userService = (UserService) DubboProxyFactory.getProxy(url, UserService.class);
		System.out.println(userService.say("Shadow " + new Date()));
		System.out.println(userService.sum(20, 21));
	}
}
