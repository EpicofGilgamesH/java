package forkjoinpool;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.LongStream;

/**
 * @Description 计算1-10000000
 * @Date 2020/8/18 10:53
 * @Created by wangjie
 */
public class Calc1_10000 {


	public static void main(String[] args) throws ExecutionException, InterruptedException {
		long[] numbers = LongStream.rangeClosed(1, 10000000).toArray();
		{
			Instant start = Instant.now();
			long sum = sum(numbers);
			Instant end = Instant.now();
			System.out.println("耗时:" + Duration.between(start, end).toMillis() + "ms");
			System.out.println("结果:" + sum);
		}

		{
			Instant start = Instant.now();
			long executorsSum = executorsSum(numbers);
			Instant end = Instant.now();
			System.out.println("耗时:" + Duration.between(start, end).toMillis() + "ms");
			System.out.println("结果:" + executorsSum);
		}

		{
			Instant start = Instant.now();
			long executorsSum = executorsSum1(numbers);
			Instant end = Instant.now();
			System.out.println("耗时:" + Duration.between(start, end).toMillis() + "ms");
			System.out.println("结果:" + executorsSum);
		}

		{
			Instant start = Instant.now();
			long l = completableSum(numbers);
			Instant end = Instant.now();
			System.out.println("耗时:" + Duration.between(start, end).toMillis() + "ms");
			System.out.println("结果:" + l);
		}

		{
			AtomicReference<String> stringAtomicReference = new AtomicReference<>("123");

		}
	}

	/**
	 * 普通计算求和方式
	 */
	private static long sum(long[] numbers) {
		long total = 0;
		for (int i = 1; i < numbers.length + 1; ++i) {
			total += i;
		}
		return total;
	}

	/**
	 * 通过线程池方式,分片处理求和方式
	 *
	 * @param numbers
	 * @return
	 */
	private static long executorsSum(long[] numbers) throws ExecutionException, InterruptedException {
		//cpu默认核心数
		int parallism = Runtime.getRuntime().availableProcessors();
		//创建线程池
		ExecutorService threadPool = Executors.newFixedThreadPool(parallism);
		//将任务分为线程数个,创建FutureTask
		List<FutureTask<Long>> futureTaskList = new ArrayList<>();
		//每一份处理的数量
		int part = numbers.length / parallism;
		int start, end;
		for (int i = 0; i < parallism; ++i) {
			start = i * part + 1;
			//最后一份数据,取到最后
			end = (i + 1 == parallism) ? numbers.length : (i + 1) * part;
			int finalStart = start;
			int finalEnd = end;
			FutureTask<Long> futureTask = new FutureTask<>(() -> {
				long total = 0;
				for (int j = finalStart; j <= finalEnd; ++j) {
					total += j;
				}
				return total;
			});
			threadPool.submit(futureTask);
			futureTaskList.add(futureTask);
		}
		long total = 0;
		for (FutureTask<Long> futureTask : futureTaskList) {
			total += futureTask.get();
		}
		return total;
	}

	/**
	 * 线程数为cpu核心数+1的场景
	 * 实践证明,线程数取核心数的整数倍+1,效率会高一些
	 *
	 * @param numbers
	 * @return
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	private static long executorsSum1(long[] numbers) throws ExecutionException, InterruptedException {
		//cpu默认核心数
		int parallism = Runtime.getRuntime().availableProcessors();
		//创建线程池
		ExecutorService threadPool = Executors.newFixedThreadPool(parallism + 1);
		//将任务分为线程数个,创建FutureTask
		List<FutureTask<Long>> futureTaskList = new ArrayList<>();
		//每一份处理的数量
		int part = numbers.length / parallism;
		int start, end;
		for (int i = 0; i < parallism + 1; ++i) {
			start = i * part + 1;
			//最后一份数据,取到最后
			end = (i == parallism) ? numbers.length : (i + 1) * part;
			int finalStart = start;
			int finalEnd = end;
			FutureTask<Long> futureTask = new FutureTask<>(() -> {
				long total = 0;
				for (int j = finalStart; j <= finalEnd; ++j) {
					total += j;
				}
				return total;
			});
			threadPool.submit(futureTask);
			futureTaskList.add(futureTask);
		}
		long total = 0;
		for (FutureTask<Long> futureTask : futureTaskList) {
			total += futureTask.get();
		}
		return total;
	}

	/**
	 * 使用Completable实现异步执行
	 *
	 * @param unmbers
	 * @return
	 */
	private static long completableSum(long[] numbers) throws ExecutionException, InterruptedException {
		//cpu默认核心数
		int parallism = Runtime.getRuntime().availableProcessors();
		//创建线程池
		ExecutorService threadPool = Executors.newFixedThreadPool(parallism + 1);
		//将任务分为线程数个,创建FutureTask
		CompletableFuture<Long>[] completableFutureArray = new CompletableFuture[parallism + 1];
		//每一份处理的数量
		int part = numbers.length / parallism;
		int start, end;
		for (int i = 0; i < parallism + 1; ++i) {
			start = i * part + 1;
			//最后一份数据,取到最后
			end = (i == parallism) ? numbers.length : (i + 1) * part;
			int finalStart = start;
			int finalEnd = end;

			CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> {
				long total = 0;
				for (int j = finalStart; j <= finalEnd; ++j) {
					total += j;
				}
				return total;
			});
			completableFutureArray[i] = completableFuture;
		}
		//合并最终结果
		Long sum = 0L;
		CompletableFuture.allOf(completableFutureArray).join();
		for (CompletableFuture<Long> completableFuture : completableFutureArray) {
			Long aLong = completableFuture.get();
			sum += aLong;
		}
		return sum;
	}

	/**
	 * @param numbers
	 * @return
	 */
	private static long forkJoinSum(long[] numbers) {

		return 0;
	}

}
