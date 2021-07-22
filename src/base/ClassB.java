package base;

/**
 * @Description 类B
 * @Date 2021-07-22 17:42
 * @Created by wangjie
 */
public class ClassB {
	/**
	 * 加载即执行,静态代码块 order:3
	 */
	static {
		System.out.println("ClassB,静态代码块执行");
	}

	/**
	 * A类静态成员变量创建对象B,调用B类的构造函数 order:5
	 * <p>
	 * 创建A对象时,b作为成员变量再次被创建,调用B 构造函数 order:8
	 */
	public ClassB() {
		System.out.println("ClassB,构造函数执行");
	}

	/**
	 * A 构造时先构造B,此时创建成员变量 b,调用B 构造函数 order:7
	 */
	private String aa = getA();


	private String getA() {
		System.out.println("ClassB,成员变量赋值");
		return "a";
	}
}
