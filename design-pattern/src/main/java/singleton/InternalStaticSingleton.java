package singleton;

/**
 * 内部静态类 单例模式
 * 在需要使用时,才会创建instance对象,可以通过InternalStaticSingleton类的构造函数执行时机来判断
 */
public class InternalStaticSingleton {

	private InternalStaticSingleton() {
	}

	private static class Singleton {
		private final static InternalStaticSingleton instance = new InternalStaticSingleton();

	}

	public static InternalStaticSingleton getInstance() {
		return Singleton.instance;
	}

	public static void main(String[] args) {
		System.out.println("init");
		InternalStaticSingleton instance = InternalStaticSingleton.getInstance();
	}

}
