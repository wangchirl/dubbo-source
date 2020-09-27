package com.shadow.simulation.comsumer;

import com.shadow.simulation.api.UserService;
import com.shadow.simulation.framework.dubbo.client.DubboProtocol;
import com.shadow.simulation.framework.support.Protocol;
import com.shadow.simulation.framework.support.ProxyFactory;
import com.shadow.simulation.framework.support.URL;

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
