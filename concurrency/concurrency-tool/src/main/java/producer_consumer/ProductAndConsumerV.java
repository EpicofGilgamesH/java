package producer_consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者-消费者模型,基于阻塞队列
 */
public class ProductAndConsumerV {

	private final int size;
	private final BlockingQueue<Integer> queue;

	public ProductAndConsumerV(int size) {
		this.size = size;
		this.queue = new LinkedBlockingDeque<>(size);
	}

	static class Product implements Runnable {

		private final AtomicInteger index = new AtomicInteger(0);

		private final BlockingQueue<Integer> queue;

		public Product(BlockingQueue<Integer> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			for (; ; ) {
				try {
					int i = index.incrementAndGet();
					queue.put(i);
					TimeUnit.MILLISECONDS.sleep(100);
					System.out.println("生产了一条数据:" + i + ",此时库存:" + queue.size());
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	static class Consumer implements Runnable {

		private final BlockingQueue<Integer> queue;

		public Consumer(BlockingQueue<Integer> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			for (; ; ) {
				try {
					Integer r = queue.take();
					TimeUnit.MILLISECONDS.sleep(200);
					System.out.println("消费了一条数据:" + r + ",此时库存:" + queue.size());
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}


	public static void main(String[] args) {
		ProductAndConsumerV pac = new ProductAndConsumerV(5);
		Product product = new Product(pac.queue);
		Consumer consumer = new Consumer(pac.queue);
		for (int i = 0; i < 3; i++) {
			new Thread(product).start();
		}
		for (int i = 0; i < 5; i++) {
			new Thread(consumer).start();
		}

	}
}
