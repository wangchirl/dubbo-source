package com.shadow.simulation.framework.http.client;

import com.shadow.simulation.api.UserService;
import com.shadow.simulation.framework.http.HttpProxyFactory;
import com.shadow.simulation.framework.support.URL;

/**
 * @author shadow
 * @create 2020-09-24
 * @description
 */
public class HttpClientDemo {

	public static void main(String[] args) {

		URL url = new URL("localhost", 8080);
		// 方式 1
		/*Invocation invocation = new Invocation(UserService.class.getName(), "say", new Class[]{String.class}, new Object[]{"王钦"});
		HttpProtocol httpClient = new HttpProtocol();
		System.out.println(httpClient.send(url, invocation)); */

		// 方式 2
		UserService userService = (UserService) HttpProxyFactory.getProxy(url, UserService.class);
		System.out.println(userService.say("Shadow"));

	}
}
