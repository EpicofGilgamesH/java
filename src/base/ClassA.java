package base;

/**
 * @Description 子类A
 * @Date 2021-07-22 17:39
 * @Created by wangjie
 */
public class ClassA extends BaseClass {

	/**
	 * 类加载即执行,子类静态代码块  order:2
	 */
	static {
		System.out.println("ClassA类,静态代码块执行");
	}

	/**
	 * 最后调用A的构造函数 order:9
	 */
	public ClassA() {
		System.out.println("ClassA类,构造函数执行");
	}

	/**
	 * 类加载即执行 静态成员变量 order:4
	 */
	public static ClassB b1 = new ClassB();

	/**
	 * A 构造时先构造B,此时创建成员变量 b,调用B 构造函数 order:7
	 */
	public ClassB b = new ClassB();

	public static void main(String[] args) {
		System.out.println("-----------start--------------");
		ClassA a = new ClassA();
		System.out.println("-----------end--------------");

		/**
		 * result:
		 * BaseClass,静态代码块执行
		 * ClassA类,静态代码块执行
		 * ClassB,静态代码块执行
		 * ClassB,成员变量赋值
		 * ClassB,构造函数执行
		 * -----------start--------------
		 * BaseClass,构造函数执行
		 * ClassB,成员变量赋值
		 * ClassB,构造函数执行
		 * ClassA类,构造函数执行
		 * -----------end--------------
		 *
		 *
		 */

		/**
		 * 1.类加载即执行:
		 * 父类静态代码块->子类静态代码块->父类静态成员变量->子类静态成员变量
		 * 2.实例类构造:
		 * 成员变量->构造函数 父类->子类
		 */
	}
}
