package com.shadow.dubbo.consumer;

import com.shadow.dubbo.api.HelloService;
import com.shadow.dubbo.netty.NettyClient;

public class ClientBootstrap {

    private static String HEADER = "service#method";


    public static void main(String[] args) throws InterruptedException {
        // 创建消费者
        NettyClient consumer = new NettyClient();
        // 获取代理对象
        HelloService bean = (HelloService) consumer.getBean(HelloService.class, HEADER);

        for (;;) {
            Thread.sleep(2000);
            String result = bean.hello("this is my dubbo...");
            System.out.println("服务端返回的结果="+result);
        }


    }
}
