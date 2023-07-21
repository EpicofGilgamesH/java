import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.concurrent.*;

public class PressureTest {


	static class RequestTask implements Runnable {

		final CountDownLatch start;

		public RequestTask(CountDownLatch s) {
			this.start = s;
		}

		@Override
		public void run() {
			try {
				start.await();
				System.out.println(Thread.currentThread().getName() + "开始执行:" + System.currentTimeMillis());
				execute();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				System.out.println(Thread.currentThread().getName() + "执行完成:" + System.currentTimeMillis());
				latch2.countDown();
			}
		}

		private static void execute() {

			try {
				String response = HttpRequest
						.get("http://10.250.160.49:8101/buyer/info")
						.header("authorization", "Bearer 53a41e2c-72d5-4257-a707-bef71084692b")
						.body("")
						.execute().body();

			} catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}

	public static CountDownLatch latch1 = new CountDownLatch(1000);
	public static CountDownLatch latch2 = new CountDownLatch(1000);

	public static void main(String[] args) throws InterruptedException, IOException {

		/*long l = System.currentTimeMillis();
		String response = HttpRequest
				.get("https://country2.test.egatee.cn/api/user/buyer/info")
				.header("authorization", "Bearer 53a41e2c-72d5-4257-a707-bef71084692b")
				.timeout(6000)
				.execute().body();

		// System.out.println(JSON.toJSONString(response));
		System.out.println("单次耗时:" + (System.currentTimeMillis() - l));*/


		long l1 = System.currentTimeMillis();
		HttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000)
				.setSocketTimeout(5000)
				.setRedirectsEnabled(true)
				.build();

		HttpGet get = new HttpGet("https://country2.test.egatee.cn/api/user/buyer/info");
		get.setHeader("authorization", "Bearer 53a41e2c-72d5-4257-a707-bef71084692b");
		get.setConfig(requestConfig);
		HttpResponse execute = httpClient.execute(get);
		System.out.println("单次耗时:" + (System.currentTimeMillis() - l1));

		// 初始化线程池
		ExecutorService pool = new ThreadPoolExecutor(24 * 40 + 1, 24 * 40 + 1, 120,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(1000),
				new ThreadFactoryBuilder().setNameFormat("work-optimize-%d").build(),
				new ThreadPoolExecutor.DiscardPolicy()  // 丢弃任务  策略设置交给当前线程处理时,导致主线程阻塞,任务没法继续,所以策略设置必须要考虑使用场景

		);
		long cast = 0;

		long s = System.currentTimeMillis();
		for (int j = 0; j < 1000; j++) {
			try {
				pool.submit(new RequestTask(latch1));
			} finally {
				latch1.countDown();
			}
		}
		System.out.println();

		latch2.await(); // 主线程阻塞
		pool.shutdown();

		long s1 = System.currentTimeMillis() - s;

		System.out.println("压测100次耗时为:" + s1);
	}
}
