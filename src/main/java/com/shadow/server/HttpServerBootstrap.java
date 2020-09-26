package com.shadow.server;

import com.shadow.api.UserService;
import com.shadow.framework.http.server.HttpServer;
import com.shadow.framework.registry.LocalRegistry;
import com.shadow.framework.registry.RemoteRegistry;
import com.shadow.framework.support.URL;
import com.shadow.provider.UserServiceImpl;

/**
 * @author shadow
 * @create 2020-09-24
 * @description
 */
public class HttpServerBootstrap {
	public static void main(String[] args) {

		URL url = new URL("localhost", 8080);
		// 1.本地注册
		// {接口：实现}
		LocalRegistry.registry(UserService.class.getName(),UserServiceImpl.class);

		// 2.远程注册
		// {接口：远程地址URL}
		RemoteRegistry.registry(UserService.class.getName(),url);

		// 3.启动tomcat
		HttpServer httpServer = new HttpServer();
		httpServer.start(url);

	}
}
