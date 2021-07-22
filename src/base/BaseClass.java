package base;

/**
 * @Description 静态类、静态方法、静态变量、静态代码块
 * @Date 2021-07-22 17:34
 * @Created by wangjie
 */
public class BaseClass {
	/**
	 * 类加载即执行 父类静态代码块 order:1
	 */
	static {
		System.out.println("BaseClass,静态代码块执行");
	}

	/**
	 * 构造A类时,先执行其父类构造函数 order:6
	 */
	public BaseClass() {
		System.out.println("BaseClass,构造函数执行");
	}

}
