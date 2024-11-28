package tool;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * ThreadLocal的使用场景
 */
public class ThreadLocalCase {

	/**
	 * SimpleDateFormat并非线程安全,但如果要在并发场景下使用,如何保证线程安全呢?
	 * ThreadLocal可以为每个线程设置一个独享的SimpleDateFormat对象
	 * <p>
	 * Thread对象持有ThreadLocal.ThreadLocalMap对象,该Map通过线程Id获取存储的对象
	 * 那为什么ThreadLocal容易造成内存泄漏呢?
	 * 1.线程池中的线程和程序同生共死,意味着Thread持有的ThreadLocalMap一直不回会回收.
	 * 2.ThreadLocalMap中的Entry对ThreadLocal是弱引用,那么ThreadLocal被回收Entry会被回收.但是
	 *  Entry中的value却是被Entry强引用的,所以这些value对象一直不回被回收掉,从而导致内存泄漏.
	 * 所在在使用ThreaLocal时,一定要使用try-finally的方式,在finally中调用remove方式,手动清空Entry.
	 */
	static class SafeDateFormat {
		static final ThreadLocal<DateFormat> tl = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

		static DateFormat get() {
			return tl.get();
		}
	}
}
