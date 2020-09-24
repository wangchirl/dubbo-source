package com.shadow.provider;

import com.shadow.api.UserService;
import com.shadow.framework.URL;
import com.shadow.http.HttpServer;
import com.shadow.registry.RemoteRegistry;

/**
 * @author shadow
 * @create 2020-09-24
 * @description
 */
public class ProviderClient {
	public static void main(String[] args) {

		// 1.本地注册
		// {接口：实现}
		LocalRegistry.registry(UserService.class.getName(),UserServiceImpl.class);

		// 2.远程注册
		// {接口：远程地址URL}
		RemoteRegistry.registry(UserService.class.getName(),new URL("localhost",8080));

		// 3.启动tomcat
		HttpServer httpServer = new HttpServer();
		httpServer.start("localhost",8080);

	}
}
