package com.shadow.comsumer;

import com.shadow.api.UserService;
import com.shadow.framework.Invocation;
import com.shadow.http.HttpClient;

/**
 * @author shadow
 * @create 2020-09-24
 * @description
 */
public class ComsumerClient {

	public static void main(String[] args) {

		HttpClient httpClient = new HttpClient();
		Invocation invocation = new Invocation(UserService.class.getName(), "say", new Class[]{String.class}, new Object[]{"王钦"});
		String result = httpClient.send("localhost", 8080, invocation);
		System.out.println(result);

	}
}
