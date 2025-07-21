import cn.hutool.http.HttpRequest;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RateLimitTest {

	static class RequestTask implements Runnable {

		@Override
		public void run() {
			execute();
		}

		private void execute() {

			try {
				String response = HttpRequest
						.post("http://10.240.65.240:8900/smsNotify/inner/sendSms")
						//.header("authorization", "Bearer 76836e89-af3e-4976-ac00-b296627bb0cf")
						.body("{\"countryCode\":\"NG\",\"sendType\":1,\"phoneNumber\":\"08102562530\",\"content\":\"123\",\"verificationCOde\":\"111\"}")
						.execute().body();
				System.out.println(response);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	// 初始化线程池
	static ExecutorService pool = new ThreadPoolExecutor(24 * 40 + 1, 24 * 40 + 1, 120,
			TimeUnit.SECONDS,
			new LinkedBlockingQueue<>(1000),
			new ThreadFactoryBuilder().setNameFormat("work-optimize-%d").build(),
			new ThreadPoolExecutor.DiscardPolicy()  // 丢弃任务  策略设置交给当前线程处理时,导致主线程阻塞,任务没法继续,所以策略设置必须要考虑使用场景

	);

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			pool.submit(new RequestTask());
			TimeUnit.MILLISECONDS.sleep(100);
		}
	}

}
