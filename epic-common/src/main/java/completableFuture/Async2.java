package completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Date 2020/8/17 17:24
 * @Created by wangjie
 */
public class Async2 {

	public static void main(String[] args) throws InterruptedException {
		//创建两个异步任务同时执行第一个查询,只要有一个查询结果返回即执行下一步
		CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> queryCode("中国石化", "url1"));
		CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> queryCode("中国石化", "url2"));
		//用anyOf合并为一个新的CompletableFuture
		CompletableFuture cf3 = CompletableFuture.anyOf(cf1, cf2);

		//创建两个异步任务执行上两个任务任意一个返回的回调
		CompletableFuture<Double> cf4 = cf3.thenApplyAsync((code) -> fetchPrice(code.toString(), "url1"));
		CompletableFuture<Double> cf5 = cf3.thenApplyAsync((code) -> fetchPrice(code.toString(), "url2"));
		CompletableFuture cf6 = CompletableFuture.anyOf(cf4, cf5);
		//最终返回结果
		cf6.thenAccept((result) -> System.out.println("price:" + result));

		TimeUnit.SECONDS.sleep(100);
	}

	static String queryCode(String name, String url) {
		System.out.println("query code from " + url + "...");
		try {
			Thread.sleep((long) (Math.random() * 100));
		} catch (InterruptedException e) {
		}
		return name + url;
	}

	static Double fetchPrice(String code, String url) {
		System.out.println("query price from " + code + url + "...");
		try {
			Thread.sleep((long) (Math.random() * 100));
		} catch (InterruptedException e) {
		}
		return 5 + Math.random() * 20;
	}
}
