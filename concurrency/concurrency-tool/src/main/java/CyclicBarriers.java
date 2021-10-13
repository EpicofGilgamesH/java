import java.util.concurrent.*;

/**
 * @Description 举个报旅行团旅行的例子。
 * 出发时，导游会在机场收了护照和签证，办理集体出境手续，所以，要等大家都到齐才能出发，出发前再把护照和签证发到大家手里。
 * 对应CyclicBarrier使用。
 * 每个人到达后进入barrier状态。
 * 都到达后，唤起大家一起出发去旅行。
 * 旅行出发前，导游还会有个发护照和签证的动作。
 * @Date 2021-09-27 11:30
 * @Created by wangjie
 */
public class CyclicBarriers {

	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new GuidTask("guid"));
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		/*for (int i = 0; i < 10; i++) {
			threadPool.submit(new GuidTask(String.valueOf(i)));
		}*/
		threadPool.submit(new TravelTask(cyclicBarrier, "张三", 10));
		threadPool.submit(new TravelTask(cyclicBarrier, "李四", 20));
		threadPool.submit(new TravelTask(cyclicBarrier, "王五", 30));
		threadPool.submit(new TravelTask(cyclicBarrier, "赵六", 40));
		threadPool.submit(new TravelTask(cyclicBarrier, "田七", 10));
		threadPool.submit(new TravelTask(cyclicBarrier, "陆八", 20));
		threadPool.submit(new TravelTask(cyclicBarrier, "罗九", 30));
		threadPool.submit(new TravelTask(cyclicBarrier, "付十", 40));
		threadPool.submit(new TravelTask(cyclicBarrier, "孙一", 10));
		threadPool.submit(new TravelTask(cyclicBarrier, "周二", 20));

	}

	public static class TravelTask implements Runnable {

		private CyclicBarrier cyclicBarrier;
		private String name;
		private int time;

		public TravelTask(CyclicBarrier cyclicBarrier, String name, int time) {
			this.cyclicBarrier = cyclicBarrier;
			this.name = name;
			this.time = time;
		}

		@Override
		public void run() {
			try {
				//等待过来的时间
				TimeUnit.MILLISECONDS.sleep(time * 10);
				System.out.println(name + "到达集合点.");
				cyclicBarrier.await();
				System.out.println(name + "出发!");
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			String aa = "\"OPT:OPT:SYS_BASE_DATA:DETAIL\"";
		}
	}

	public static class GuidTask implements Runnable {

		private String name;

		public GuidTask(String name) {
			this.name = name;
		}

		@Override
		public void run() {
			System.out.println(name + ":集合完成,分发个人用品.");
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

