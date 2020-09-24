package com.shadow.dubbo.provider;

import com.shadow.dubbo.api.HelloService;

public class HelloServiceImpl implements HelloService {

    private int count = 0;

    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息="+msg);
        if(msg != null){
            return "你好客户端，我已经收到你的消息 [" + msg + "] " + (++count) +" 次";
        }
        return "你好客户端，我已经收到你的消息";
    }
}
