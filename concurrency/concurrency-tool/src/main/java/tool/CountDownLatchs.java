package tool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description 使用CountDownLatch模拟并发场景
 * @Date 2020/8/13 11:55
 * @Created by wangjie
 */
public class CountDownLatchs {

	private static final ExecutorService pool = Executors.newFixedThreadPool(10);
	private static final CountDownLatch countDownLatch = new CountDownLatch(30);

	public static void main(String[] args) throws InterruptedException {

		for (int i = 0; i < 30; i++) {
			pool.submit(() -> {
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				operate();
			});
			countDownLatch.countDown();
		}
		System.out.println("start");

		TimeUnit.SECONDS.sleep(100);
	}

	public static void operate() {
		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("operate...");
	}
}
