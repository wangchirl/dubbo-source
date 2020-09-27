package com.shadow.simulation.framework.dubbo.client;

import com.shadow.simulation.framework.support.Invocation;
import com.shadow.simulation.framework.support.Protocol;
import com.shadow.simulation.framework.support.URL;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shadow
 * @create 2020-09-26
 * @description
 */
public class DubboProtocol implements Protocol {
	// 线程池
	private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	// 任务-也是业务处理类
	private DubboClientHandler dubboClientHandler;
	// 执行的调用方法
	public Object send(URL url, Invocation invocation) {
		// init
		if(dubboClientHandler == null){
			initDubbo(url.getHostname(),url.getPort());
		}
		// 请求参数设置
		dubboClientHandler.setInvocation(invocation);
		try {
			// 线程池同步执行获取结果
			return executorService.submit(dubboClientHandler).get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}


	private void initDubbo(String hostname,int port) {

		dubboClientHandler = new DubboClientHandler();

		NioEventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)
				.option(ChannelOption.TCP_NODELAY,true)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new StringEncoder())
								.addLast(new StringDecoder())
								.addLast(dubboClientHandler);
					}
				});
		try {
			bootstrap.connect(hostname,port).sync();
			System.out.println("connect to hostname " + hostname + " port " + port);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}


}
