package com.epic.springlearn;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Description test
 * @Date 2022-08-23 11:33
 * @Created by wangjie
 */
public class Test {

	private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

	public static void main(String[] args) {
		/*ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		UserService userService = (UserService) applicationContext.getBean("userService");
		userService.test();


		OrderService orderService1 = (OrderService) applicationContext.getBean("orderService1");
		System.out.println(orderService1);

		OrderService orderService2 = (OrderService) applicationContext.getBean("orderService2");
		System.out.println(orderService2);*/

		threadLocal.set("123");

		String s = threadLocal.get();


		ThreadLocal<String> threadLocal1 = new ThreadLocal<>();
		threadLocal1.set("123");
		String s1 = threadLocal1.get();

	}
}
