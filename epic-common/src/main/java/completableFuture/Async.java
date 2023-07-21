package completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * @Description CompletableFuture 使用
 * @Date 2020/8/17 16:39
 * @Created by wangjie
 */
public class Async {

	public static void main(String[] args) throws InterruptedException {

	/*	//创建异步执行任务
		CompletableFuture<Double> cf = CompletableFuture.supplyAsync(Async::fetchPrice);

		//执行成功回调
		cf.thenAccept((result) -> System.out.println("price:" + result));

		// 执行异常回调
		cf.exceptionally((e) -> {
			e.printStackTrace();
			return null;
		});

		// 主线程等待,否则CompletableFuture默认使用的线程池会立刻关闭
		TimeUnit.SECONDS.sleep(100);*/


		CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
			System.out.println(Thread.currentThread().getName() + "-" + "s-" + "cf1");
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			System.out.println(Thread.currentThread().getName() + "-" + "e-" + "cf1");
			return "cf1";
		});

		CompletableFuture<String> cf2 = cf1.thenApply(x -> {
			System.out.println(Thread.currentThread().getName() + "-" + "s-" + "cf2");
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			System.out.println(Thread.currentThread().getName() + "-" + "e-" + "cf2");
			return x + ":cf2";
		});

		CompletableFuture<String> cf3 = cf1.thenApplyAsync(x -> {
			System.out.println(Thread.currentThread().getName() + "-" + "s-" + "cf3");
			try {
				TimeUnit.MILLISECONDS.sleep(70);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			System.out.println(Thread.currentThread().getName() + "-" + "e-" + "cf3");
			return x + ":cf3";
		});

		CompletableFuture<String> cf4 = cf2.thenCombine(cf3, (x, y) -> {
			System.out.println(Thread.currentThread().getName() + "-" + "s-" + "cf4");
			try {
				TimeUnit.MILLISECONDS.sleep(80);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			System.out.println(Thread.currentThread().getName() + "-" + "s-" + "cf4");
			return x + y;
		});

		try {
			String s = cf4.get();
			System.out.println("返回:" + s);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}

		// 主线程等待,否则CompletableFuture默认使用的线程池会立刻关闭
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
