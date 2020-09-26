package com.shadow.framework.nettyCore.netty;

import com.shadow.framework.nettyCore.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static String HEADER = "service#method";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg="+msg);
        if(msg.toString().startsWith(HEADER)){
            String result = new HelloServiceImpl().hello(msg.toString().substring(HEADER.length() + 1));
            ctx.writeAndFlush(result);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
