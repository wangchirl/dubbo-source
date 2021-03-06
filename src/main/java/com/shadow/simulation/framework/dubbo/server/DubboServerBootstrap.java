package com.shadow.simulation.framework.dubbo.server;

import com.shadow.simulation.api.UserService;
import com.shadow.simulation.framework.support.URL;
import com.shadow.simulation.framework.registry.LocalRegistry;
import com.shadow.simulation.provider.UserServiceImpl;
import com.shadow.simulation.framework.registry.RemoteRegistry;

/**
 * @author shadow
 * @create 2020-09-26
 * @description
 */
public class DubboServerBootstrap {

	public static void main(String[] args) {

		URL url = new URL("127.0.0.1", 8090);
		// 1.本地注册
		LocalRegistry.registry(UserService.class.getName(),UserServiceImpl.class);

		// 2.远程注册
		RemoteRegistry.registry(UserService.class.getName(),url);

		// 3.启动 nettyCore 服务
		DubboServer dubboServer = new DubboServer();
		dubboServer.start(url);

	}

}
