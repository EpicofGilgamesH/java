package com.epic.springlearn;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Description aspect
 * @Date 2022-08-24 15:12
 * @Created by wangjie
 */
@Component
@Aspect
public class UserServiceAspect {

	@Pointcut("execution(* com.epic.springlearn.UserService.test(..))")
	public void cut() {

	}

	@Before("cut()")
	public void before(JoinPoint joinpoint) throws Throwable {
		System.out.println("aop-before..");
	}
}
