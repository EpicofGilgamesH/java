package tool;

import java.util.concurrent.TimeUnit;

/**
 * 优雅的终止线程
 * interrupt()方法能终止线程状态处于Runnable的线程,那处于休眠状态的线程如何终止呢?
 * 其会在catch中捕获异常,在捕获中将线程状态设置为中断状态.同时使用中断标志位,在线程处于完业务后合理的位置终止线程
 * ----------------------------------------------------------------------------------
 * 但是在一般的实际开发过程中,都会使用线程池进行操作,线程池实现了优雅的终止操作shutdown()和shutdownNow();
 */
public class InterruptCase {

	volatile boolean terminated = false;
	boolean started = false;
	// 采集线程
	Thread rptThread;

	synchronized void start() {
		// 不允许同时启动多个采集线程
		// 为什么这里需要判断呢?因为started的值是在子线程中进行操作的;锁释放之后,started的值才被修改
		if (started) {
			return;
		}
		started = true;
		terminated = false;
		rptThread = new Thread(() -> {
			while (!terminated) {
				// 采集、回传操作
				// report();
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			// 执行到此处说明线程走向终止
			started = true;
		});
		rptThread.start();

	}

	// 终止采集操作
	synchronized void stop() {
		// 设置中断标志位
		terminated = true;
		// 中断线程
		rptThread.interrupt();
	}
}
