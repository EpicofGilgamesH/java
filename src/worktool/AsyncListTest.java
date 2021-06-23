package worktool;

import cn.hutool.core.date.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description TODO
 * @Date 2021-06-04 18:07
 * @Created by wangjie
 */
public class AsyncListTest {

	private static ExecutorService pool = Executors.newFixedThreadPool(27);

	public static void main(String[] args) throws InterruptedException {
		execut();
	}

	private static void execut() throws InterruptedException {
		CountDownLatch start = new CountDownLatch(1000000);
		CountDownLatch end = new CountDownLatch(1000000);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		for (int i = 0; i < 1000000; ++i) {
			pool.submit(() -> {
				try {
					a(end);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					start.countDown();
				}
			});
			end.countDown();
		}
		start.await();
		stopWatch.stop();
		System.out.println(stopWatch.getLastTaskTimeMillis());
		System.out.println("-----------------------------------");
	}

	public static void a(CountDownLatch end) throws InterruptedException {
		end.await();
		ListTest.test3();
	}
}
