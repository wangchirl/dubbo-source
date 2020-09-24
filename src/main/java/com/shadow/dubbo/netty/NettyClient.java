package com.shadow.dubbo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {


    private static ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static NettyClientHandler clientHandler;

    // 编写方法使用代理模式，获取一个代理对象
    public Object getBean(final Class<?> serverClass, final String header) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[] {serverClass}, (proxy, method, args) -> {
                   if(clientHandler == null) {
                       initClient();
                   }
                   // 设置要发给服务器端的信息
                    // header协议头 args[0] 接收客户端调用api hello(？？)的参数
                   clientHandler.setParams(header+args[0]);
                   return executor.submit(clientHandler).get();
                });
    }

    // 初始化客户端
    private static void initClient(){
        clientHandler = new NettyClientHandler();

        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(clientHandler);
                    }
                });
        try {
            bootstrap.connect("127.0.0.1", 8000).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
