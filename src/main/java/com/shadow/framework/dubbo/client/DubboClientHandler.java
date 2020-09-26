package com.shadow.framework.dubbo.client;

import com.alibaba.fastjson.JSON;
import com.shadow.framework.support.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @author shadow
 * @create 2020-09-26
 * @description
 */
public class DubboClientHandler extends ChannelInboundHandlerAdapter implements Callable<Object> {

	private ChannelHandlerContext ctx;
	private Invocation invocation;
	private Object result;

	// 连接建立
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.ctx = ctx;
	}

	// 读事件
	@Override
	public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		this.result = msg;
		notify();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}


	@Override
	public synchronized Object call() throws Exception {
		ctx.writeAndFlush(JSON.toJSONString(this.invocation));
		wait();
		return this.result;
	}

	public void setInvocation(Invocation invocation) {
		this.invocation = invocation;
	}
}
