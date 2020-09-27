package completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Description CompletableFuture
 * @Date 2020/8/17 17:09
 * @Created by wangjie
 */
public class Async1 {

	public static void main(String[] args) throws InterruptedException {
		//多个任务串行
		//第一个任务
		CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> queryCode("中国石化"));
		//第一个任务执行完成后,继续执行下一个任务
		//将第一个任务的成功回调作为一个异步任务
		CompletableFuture<Double> cf2 = cf1.thenApplyAsync((result) -> fetchPrice(result));
		//第二个任务执行成功后执行回调
		cf2.thenAccept((result) -> {
			System.out.println(System.currentTimeMillis());
			System.out.println("price" + result);
		});

		TimeUnit.SECONDS.sleep(100);

	}

	static String queryCode(String name) {
		System.out.println(System.currentTimeMillis());
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
		}
		return "601857";
	}

	static Double fetchPrice(String code) {
		System.out.println(System.currentTimeMillis());
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
		}
		return 5 + Math.random() * 20;
	}
}
