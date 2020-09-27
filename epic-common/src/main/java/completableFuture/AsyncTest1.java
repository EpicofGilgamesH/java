package completableFuture;

import cn.hutool.core.date.StopWatch;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description TODO
 * @Date 2020/9/1 16:18
 * @Created by wangjie
 */
public class AsyncTest1 {

	public static void main(String[] args) {
		AtomicInteger integer = new AtomicInteger(0);
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for (int i = 0; i < 100000; ++i) {
			incream(integer);
		}
		stopWatch.stop();

		System.out.println(stopWatch.getLastTaskTimeMillis());
		System.out.println("结果：" + integer.get());
	}

	private static void incream(AtomicInteger integer) {
		integer.getAndIncrement();
	}
}
