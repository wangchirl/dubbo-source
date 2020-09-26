package com.shadow.comsumer;

import com.shadow.api.UserService;
import com.shadow.framework.dubbo.client.DubboProtocol;
import com.shadow.framework.support.Protocol;
import com.shadow.framework.support.ProxyFactory;
import com.shadow.framework.support.URL;

/**
 * @author shadow
 * @create 2020-09-26
 * @description
 */
public class Consumer {
	public static void main(String[] args) {
		URL url = new URL("localhost", 8080); // 默认 http服务 8080端口
		Protocol protocol = ProxyFactory.getProtocol();
		if(protocol instanceof DubboProtocol){ // dubbo 服务在 8090端口
			url.setPort(8090);
		}
		UserService userService = (UserService) ProxyFactory.getProxy(url, UserService.class);
		System.out.println(userService.say("shadow"));
		System.out.println(userService.sum(1,2));
	}
}
