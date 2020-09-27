package com.shadow.simulation.framework.dubbo;

import com.shadow.simulation.framework.dubbo.client.DubboProtocol;
import com.shadow.simulation.framework.support.Invocation;
import com.shadow.simulation.framework.support.URL;

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
