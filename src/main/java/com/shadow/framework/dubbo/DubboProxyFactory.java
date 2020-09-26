package com.shadow.framework.dubbo;

import com.shadow.framework.dubbo.client.DubboProtocol;
import com.shadow.framework.support.Invocation;
import com.shadow.framework.support.URL;

import java.lang.reflect.Proxy;

/**
 * @author shadow
 * @create 2020-09-26
 * @description
 */
public class DubboProxyFactory {

	public static Object getProxy(URL url,Class interfaces) {

		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{interfaces},(proxy,method,args) ->{
			DubboProtocol client = new DubboProtocol();
			Invocation invocation = new Invocation(interfaces.getName(), method.getName(), method.getParameterTypes(), args);
			return client.send(url,invocation);
		});
	}

}
