package com.shadow.framework.http.server;

import com.shadow.framework.support.Invocation;
import com.shadow.framework.registry.LocalRegistry;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

/**
 * @author shadow
 * @create 2020-09-24
 * @description
 */
public class HttpServerHandler {


	public void handler(HttpServletRequest req, HttpServletResponse resp) {
		// 处理请求
		try {
			ServletInputStream inputStream = req.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			Invocation invocation = (Invocation) ois.readObject();

			// 找到服务 - 调用服务
			Class impl = LocalRegistry.get(invocation.getInterfaceName());
			Method method = impl.getMethod(invocation.getMethodName(), invocation.getParamTypes());
			String result = (String) method.invoke(impl.newInstance(), invocation.getParams());

			// 返回数据
			IOUtils.write(result, resp.getOutputStream(),"utf-8");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
