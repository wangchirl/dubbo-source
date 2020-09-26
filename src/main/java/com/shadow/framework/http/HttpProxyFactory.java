package com.shadow.framework.http;

import com.shadow.framework.http.client.HttpProtocol;
import com.shadow.framework.support.Invocation;
import com.shadow.framework.support.URL;

import java.lang.reflect.Proxy;

/**
 * @author shadow
 * @create 2020-09-26
 * @description
 */
public class HttpProxyFactory {

	public static Object getProxy(URL url, Class interfaces) {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{interfaces},(proxy,method,args) ->{
			Invocation invocation = new Invocation(interfaces.getName(), method.getName(), method.getParameterTypes(), args);
			HttpProtocol client = new HttpProtocol();
			Object result = client.send(url, invocation);
			return result;
		});

	}

}
