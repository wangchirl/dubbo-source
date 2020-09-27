package com.shadow.dubbo.consumer;

import com.shadow.dubbo.api.AccountService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author shadow
 * @create 2020-09-26
 * @description
 */
public class Consumer {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:consumer.xml");
		context.start();
		AccountService accountService = (AccountService) context.getBean("accountService");
		System.out.println(accountService.say("Shadow"));
	}
}
