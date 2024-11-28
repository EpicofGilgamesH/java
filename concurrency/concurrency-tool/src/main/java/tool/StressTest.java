package tool;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;

import java.io.Serializable;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * 压力测试工具
 */
public class StressTest {

	public StressTest(int count, Supplier<RequestInfo> supplier) {
		this.count = count;
		this.supplier = supplier;
		collectCdl = new CountDownLatch(count);
		mianCdl = new CountDownLatch(count);
	}

	private final int count;
	private final Supplier<RequestInfo> supplier;

	private final CountDownLatch collectCdl;
	private final CountDownLatch mianCdl;
	private final AtomicInteger successCount = new AtomicInteger(0);
	private final AtomicInteger failCount = new AtomicInteger(0);
	private final AtomicLong totalCost = new AtomicLong(0);

	private final int coreCount = Runtime.getRuntime().availableProcessors();
	private final ExecutorService pool = new ThreadPoolExecutor(
			coreCount * 24 + 1,
			coreCount * 24 + 1,
			120,
			TimeUnit.SECONDS,
			new LinkedBlockingDeque<>(3000),
			new ThreadFactoryBuilder().setNameFormat("stressTest-%d").build(),
			new ThreadPoolExecutor.AbortPolicy()
	);

	public void execute() throws InterruptedException {
		long l = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			try {
				pool.execute(() -> {
					// 任务等待,全部就绪后一起执行
					RequestInfo res;
					try {
						collectCdl.await();
						res = supplier.get();
						if (ObjectUtil.isNotNull(res) && res.status) {
							totalCost.addAndGet(res.cost);
							successCount.incrementAndGet();
						} else {
							failCount.incrementAndGet();
						}
					} catch (Exception ex) {
						System.out.println(ex.getMessage());
					} finally {
						mianCdl.countDown();
					}
				});
			} finally {
				collectCdl.countDown();
			}
		}

		// 主线程等待,全部完成后回来
		mianCdl.await();
		pool.shutdown();
		// 计算结果
		long cost = System.currentTimeMillis() - l;
		double s = successCount.get() == 0 ? 0 : successCount.get() / (double) count;
		double f = failCount.get() == 0 ? 0 : failCount.incrementAndGet() / (double) count;
		double concurrency = count / ((double) cost / 1000);
		// 接口请求时间不代表真实的场景,受本级配置及网络因素影响较大
		double avgCost = totalCost.get() / (double) count;
		System.out.printf("Stress test finish,concurrency:%.2f,cost:%s ms,success:%.2f%% ,failed:%.2f%%%n,avg cost:%.2f"
				, concurrency, cost, s * 100, f * 100, avgCost);
	}

	@Data
	static class RequestInfo implements Serializable {
		private static final long serialVersionUID = 1600256466996489507L;
		private boolean status;
		private long cost;
	}

	public static void main(String[] args) throws InterruptedException {
		StressTest stressTest = new StressTest(2, StressTest::request);
		stressTest.execute();
	}

	private static RequestInfo request() {
		RequestInfo info = new RequestInfo();
		boolean flag = true;
		long l = System.currentTimeMillis();
		try {
			String response = HttpRequest
					.get("https://country.uat.egatee.net/api/user/buyer/info")
					.header("authorization", "Bearer f0373d07-b3a9-46e0-b7a3-7e598e886e92")
					.header("COUNTRY_ID", "4")
					.body("")
					.execute().body();
			// System.out.println(response);
		} catch (Exception exception) {
			flag = false;
		}
		info.setCost(System.currentTimeMillis() - l);
		info.setStatus(flag);
		return info;
	}
}
