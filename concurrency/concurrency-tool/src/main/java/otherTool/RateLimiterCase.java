package otherTool;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Guava RateLimiter限流器
 */
public class RateLimiterCase {

	public static void main(String[] args) {
		// 限流器流速,2个请求/秒
		final RateLimiter limiter = RateLimiter.create(2.0);
		// 执行任务的线程
		final ExecutorService es = Executors.newFixedThreadPool(1);
		// 记录上一次执行的时间
		final long[] prevTime = {System.nanoTime()};
		for (int i = 0; i < 20; i++) {
			// 限流器限流
			limiter.acquire(); // 未获得令牌,会阻塞线程
			// 提交任务到线程池
			es.execute(() -> {
				long curTime = System.nanoTime();
				System.out.println((curTime - prevTime[0]) / 1000_000);
				prevTime[0] = curTime;
			});
		}
	}
}
