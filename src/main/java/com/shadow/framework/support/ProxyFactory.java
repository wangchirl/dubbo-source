package com.shadow.framework.support;

import com.shadow.framework.http.client.HttpProtocol;

import java.lang.reflect.Proxy;
import java.util.ServiceLoader;

/**
 * @author shadow
 * @create 2020-09-26
 * @description
 */
public class ProxyFactory {

	// dubbo 协议
	private static final String DUBBO_PROTOCOL = "dubbo";
	// http 协议
	private static final String HTTP_PROTOCOL = "http";

	public static Object getProxy(URL url, Class interfaces) {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{interfaces}, (proxy, method, args) -> {
			// 方式 1
			/* String protocol = System.getProperty("protocol"); // -Dprotocol=dubbo
			if(DUBBO_PROTOCOL.equals(protocol)){
				client = new DubboProtocol();
			}*/
			// 方式 2
			Protocol client = getProtocol(); // Java SPI 机制 ，Dubbo 有自己的 SPI 机制
			Invocation invocation = new Invocation(interfaces.getName(), method.getName(), method.getParameterTypes(), args);
			return client.send(url,invocation);
		});
	}

	/**
	 * 修改 resources/META-INF/services下的接口文件中的实现类即可进行dubbo/http协议之间的切换
	 * @return
	 */
	public static Protocol getProtocol() {
		ServiceLoader<Protocol> serviceLoader = ServiceLoader.load(Protocol.class);
		Protocol next = serviceLoader.iterator().next();
		return next == null ? new HttpProtocol() : next;
	}
}
