package com.shadow.simulation.framework.dubbo.server;

import com.alibaba.fastjson.JSON;
import com.shadow.simulation.framework.support.Invocation;
import com.shadow.simulation.framework.registry.LocalRegistry;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

/**
 * @author shadow
 * @create 2020-09-26
 * @description
 */
public class DubboServerHandler extends ChannelInboundHandlerAdapter {

	// 处理读请求
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 接收参数 -> 由于只加入了 String的编解码器，所以 msg 是 String 类型的json字符串
		// 1.json转换
		Invocation invocation = JSON.parseObject((String) msg, Invocation.class);

		// 2.本地注册拿到实现类，实例化并调用方法
		Class impl = LocalRegistry.get(invocation.getInterfaceName());
		Method method = impl.getMethod(invocation.getMethodName(), invocation.getParamTypes());
		Object result = method.invoke(impl.newInstance(), invocation.getParams());

		// 3.写回执行结果
		if(result != null) ctx.writeAndFlush(result);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
