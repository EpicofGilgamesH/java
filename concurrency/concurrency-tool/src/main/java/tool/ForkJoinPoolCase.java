package tool;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinPool的使用场景
 * 计算斐波那契数列
 * 1,1,2,3,5,8,13...
 * 特点是,f(n)=f(n-1)+f(n-2) (n >=2),如何用ForkJoinPool来并行处理呢?
 */
public class ForkJoinPoolCase {

	static class Fibonacci extends RecursiveTask<Integer> {

		final int n;

		Fibonacci(int n) {
			this.n = n;
		}

		@Override
		protected Integer compute() {
			if (n <= 1) return 1;
			Fibonacci f1 = new Fibonacci(n - 1);
			// 创建子任务
			f1.fork();
			Fibonacci f2 = new Fibonacci(n - 2);
			// 等待子任务结束后合并结果
			return f2.compute() + f1.join();
		}
	}

	/**
	 * 单词收集
	 */
	static class MR extends RecursiveTask<Map<String, Long>> {

		private String[] fc;
		private int start, end;

		MR(String[] fc, int fr, int to) {
			this.fc = fc;
			this.start = fr;
			this.end = to;
		}

		@Override
		protected Map<String, Long> compute() {
			if (end - start == 1) {  // 分解到只剩一个单词时,进行单词收集
				return calc(fc[start]);
			} else {
				int mid = start + (end - start) / 2;
				MR mr1 = new MR(fc, start, mid);  // 此处二分不包含end位置的行
				mr1.fork();
				MR mr2 = new MR(fc, mid, end);
				return merge(mr2.compute(), mr1.join());
			}
		}

		private Map<String, Long> calc(String line) {
			Map<String, Long> result = new HashMap<>();
			String[] word = line.split("\\s+");
			for (String w : word) {
				result.put(w, result.getOrDefault(w, 0L) + 1);
			}
			return result;
		}

		private Map<String, Long> merge(Map<String, Long> map1, Map<String, Long> map2) {
			Map<String, Long> result = new HashMap<>(map1);
			map2.forEach((k, v) -> {
				result.put(k, result.getOrDefault(k, 0L) + v);
			});
			return result;
		}
	}

	public static void main(String[] args) {
		ForkJoinPool fjp = new ForkJoinPool(4);
		Fibonacci fib = new Fibonacci(3);
		Integer result = fjp.invoke(fib);
		System.out.println(result);
		//****************************************************************
		String[] fc = {"hello world",
				"hello me",
				"hello fork",
				"hello join",
				"fork join in world"};
		ForkJoinPool fjp1 = new ForkJoinPool(3);
		MR mr = new MR(fc, 0, fc.length);
		Map<String, Long> res = fjp1.invoke(mr);
		res.forEach((k, v) -> {
			System.out.println(k + ":" + v);
		});
	}
}
