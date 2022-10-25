package com.epic.springlearn;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @Description UserService
 * @Date 2022-08-23 11:37
 * @Created by wangjie
 */
@Component
public class UserService implements InitializingBean {

	private OrderService orderService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	//UserService 给他声明一个无参构造函数,那Spring在创建bean时,如果有多个构造函数时,会找到类的无参构造函数
	/*public UserService() {

	}*/

	//UserService 有多个构造函数时,Spring回去找无参构造函数,当无参构造函数不存在时,会报错
	public UserService(OrderService orderService, Integer i) {
		this.orderService = orderService;
		System.out.println(i);
	}

	//Spring 在创建UserService 的bean时,会调用该类的构造函数,如果只有这一个构造函数时,自动调用
	@Autowired
	public UserService(OrderService orderService2) {
		this.orderService = orderService2;
	}

	@PostConstruct
	public void a() {
		System.out.println("初始化之前.");
	}

	@Transactional
	public void test() {
		jdbcTemplate.execute("insert into `student` (`id`,`name`,`birth`,`sex`) values (1001,'张三',18,'男')");
		throw new RuntimeException();
		//此场景为什么不能回滚呢?******************************
		//@Transactional 使用AOP的方式,在Test类中,通过ApplicationContext获取bean时,由于UserService类有实现切面方法(test()),
		//则bean中存放的对象是proxy 代理对象,UserService代理对象在调用test()方法时,会进入Transactional注解的增强中,从而开启事务、
		//关键jdbcTemplate连接的autocommit、事务提交和回滚
		//但是AppConfig类在生成bean时,jdbcTemplate和transactionManager 都调用了Datasource()这个bean,生产了不同的数据库连接,所以事务
		//开启了,但回滚未实现
		//当AppConfig类加上Configuration注解时,其bean中存放的是其proxy代理,那么在调用Datasource()这个bean时,使用的方式是super.datasource()
		//代理对象在获取datasource的bean时,会先看容器中是否有,如果有的话,直接使用
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//属性设置完成之后，
		System.out.println("属性设置完成之后.");
	}
}
