package singleton;

/**
 * double check 单例模式
 */
public class DoubleCheckSingleton {

	private volatile static DoubleCheckSingleton singleton;

	/**
	 * 单例模式,不公开构造函数
	 */
	private DoubleCheckSingleton() {

	}

	/**
	 * singleton对象为什么要使用volatile关键字呢?
	 * 1.synchronized 监视器就能保证内存的可见性,为什么还要用volatile呢?
	 * 2.使用到volatile的特性---禁止指令重排序
	 * 3.new 一个对象的操作:
	 * 1)分配一块内存M
	 * 2)在内存M上初始化singleton对象
	 * 3)将M的地址赋值给instance变量
	 * 但实际上指令重排之后,路径却是
	 * 1)--> 3) --> 2)
	 * A线程刚好在构造函数中执行到3)步骤,还未执行2)步骤,此时M还未初始化,内存中是空的
	 * B线程刚好执行到instance==null ,然后返回了未初始化的对象instance,然后发生错误
	 *
	 * @return
	 */
	public static DoubleCheckSingleton getInstance() {
		if (singleton == null) {  // 多个线程可能同时进入if,所以需要double check
			synchronized (DoubleCheckSingleton.class) {
				if (singleton == null) {
					singleton = new DoubleCheckSingleton();
				}
			}
		}
		return singleton;
	}


	public static void main(String[] args) {
		// 获取单例
		DoubleCheckSingleton instance = DoubleCheckSingleton.getInstance();
	}
}
