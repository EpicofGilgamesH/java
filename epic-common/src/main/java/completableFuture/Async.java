package completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Description CompletableFuture 使用
 * @Date 2020/8/17 16:39
 * @Created by wangjie
 */
public class Async {

	public static void main(String[] args) throws InterruptedException {

		//创建异步执行任务
		CompletableFuture<Double> cf = CompletableFuture.supplyAsync(Async::fetchPrice);

		//执行成功回调
		cf.thenAccept((result) -> System.out.println("price:" + result));

		//执行异常回调
		cf.exceptionally((e) -> {
			e.printStackTrace();
			return null;
		});

		//主线程等待,否则CompletableFuture默认使用的线程池会立刻关闭
		TimeUnit.SECONDS.sleep(100);
	}

	static Double fetchPrice() {
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (Math.random() < 0.3) {
			throw new RuntimeException("fetch price failed!");
		}
		return 5 + Math.random() * 20;
	}
}
