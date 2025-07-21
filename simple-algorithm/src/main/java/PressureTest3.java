import cn.hutool.http.HttpRequest;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.taobao.diamond.utils.TimeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * 每隔一段时间,并发请求一次,测试流量控制
 */
public class PressureTest3 {


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
						.post("http://10.240.65.240:8900/smsNotify/inner/sendSms")
						//.header("authorization", "Bearer 76836e89-af3e-4976-ac00-b296627bb0cf")
						.body("{\"countryCode\":\"NG\",\"sendType\":1,\"phoneNumber\":\"081025632010\",\"content\":\"123\",\"verificationCOde\":\"111\"}")
						.execute().body();
				System.out.println(response);
			} catch (Exception exception) {

			}
		}
	}

	public static CountDownLatch latch1 = new CountDownLatch(4);
	public static CountDownLatch latch2 = new CountDownLatch(4);

	public static void main(String[] args) throws InterruptedException, IOException {

		// 初始化线程池
		ExecutorService pool = new ThreadPoolExecutor(24 * 40 + 1, 24 * 40 + 1, 120,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(1000),
				new ThreadFactoryBuilder().setNameFormat("work-optimize-%d").build(),
				new ThreadPoolExecutor.DiscardPolicy()  // 丢弃任务  策略设置交给当前线程处理时,导致主线程阻塞,任务没法继续,所以策略设置必须要考虑使用场景

		);

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 4; j++) {
				try {
					pool.submit(new RequestTask(latch1));
				} finally {
					latch1.countDown();
				}
			}
			System.out.println();
			latch2.await(); // 主线程阻塞
			TimeUnit.MILLISECONDS.sleep(20);
		}
		pool.shutdown();

	}
}
