package producer_consumer;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 生产者-消费者模式案例
 * 消费时,收集多个消息后,批量消费的场景,比如消息是SQL,需要批量执行
 */
public class ProducerAndConsumerCase {

	private final BlockingQueue<Task> bq = new LinkedBlockingDeque<>(2000);

	// 消费者,5个线程同时消费
	public void start() {
		ExecutorService es = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 5; i++) {
			es.execute(() -> {
				try {
					while (true) {
						List<Task> tasks = pollTasks();
						execTasks(tasks);
					}
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});
		}
	}

	// 从任务队列中获取批量任务
	private List<Task> pollTasks() throws InterruptedException {
		List<Task> list = new LinkedList<>();
		// 阻塞式的获取一条任务,当队列为空时会进行阻塞
		Task t = bq.take();
		while (t != null) {
			list.add(t);
			// 非阻塞式的获取一条任务,当队列为空时会直接返回null
			t = bq.poll();
		}
		// 批量处理执行任务
		return list;
	}

	// 批量执行任务
	private void execTasks(List<Task> ts) {

	}

	/**
	 * 任务类
	 */
	static class Task {

	}
}
