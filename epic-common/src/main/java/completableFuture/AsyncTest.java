package completableFuture;

import cn.hutool.core.date.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description TODO
 * @Date 2020/9/1 15:41
 * @Created by wangjie
 */
public class AsyncTest {


	public static void main(String[] args) throws InterruptedException {

		CountDownLatch start = new CountDownLatch(100000);
		CountDownLatch end = new CountDownLatch(100000);
		ExecutorService pool = Executors.newFixedThreadPool(27);

		AtomicInteger atomicInteger = new AtomicInteger(0);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		//模拟十万级qps
		for (int i = 0; i < 100000; ++i) {
			pool.submit(() -> {
				try {
					incream(atomicInteger, end);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					//完成一条,start计数-1,当全部完成时,主线程被唤醒
					start.countDown();
				}
			});
			end.countDown();
		}
		start.await();
		stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis());
		System.out.println("结果为:" + atomicInteger.get());
	}

	private static void incream(AtomicInteger integer, CountDownLatch end) throws InterruptedException {
		System.out.println("开始计算,end="+end.getCount());
		end.await();
		integer.getAndIncrement();
	}
}
