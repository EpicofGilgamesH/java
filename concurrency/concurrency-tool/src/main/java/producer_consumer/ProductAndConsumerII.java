package producer_consumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * 生产者-消费者模型,基于synchronized
 * 中间的缓冲区用队列来实现 Deque
 * <p>
 * 对锁的粒度进行细化,只需要队列的加入和弹出的过程进行加锁
 * --------------------------------------------------------------
 * 锁的粒度细化到了队列元素的加入和弹出过程,但是在唤醒时,不能指定唤醒添加元素的线程和取出元素的线程
 * 如何能在唤醒线程时进行精确的区分呢?使用管程Condition
 */
public class ProductAndConsumerII {

	private final int size;

	public ProductAndConsumerII(int size) {
		this.size = size;
	}

	static class pacQueue {
		private Queue<Integer> queue = new LinkedList<>();
		private int value = 0;
		private int size;

		public pacQueue(int size) {
			this.size = size;
		}

		public void add() throws InterruptedException {
			synchronized (this) {
				if (queue.size() == size) {
					this.wait();
				}
				if (queue.size() < size) {
					int v = value++;
					queue.offer(v);
					System.out.println("[生产]:" + Thread.currentThread().getName() + "-" + v);
					this.notifyAll();
				}
			}
		}

		public void poll() throws InterruptedException {
			synchronized (this) {
				if (queue.isEmpty()) {
					this.wait();
				}
				if (!queue.isEmpty()) {
					Integer v = queue.poll();
					System.out.println("[消费]:" + Thread.currentThread().getName() + "-" + v);
					this.notifyAll();
				}
			}
		}
	}

	/**
	 * 生产者
	 */
	static class Product implements Runnable {

		private final pacQueue pacQueue;

		public Product(pacQueue pacQueue) {
			this.pacQueue = pacQueue;
		}

		@Override
		public void run() {
			for (; ; ) {
				try {
					pacQueue.add();
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 消费者
	 */
	static class Consumer implements Runnable {

		private final pacQueue pacQueue;

		public Consumer(pacQueue pacQueue) {
			this.pacQueue = pacQueue;
		}

		@Override
		public void run() {
			for (; ; ) {
				try {
					pacQueue.poll();
					TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public void execute() {
		pacQueue pacQueue = new pacQueue(this.size);
		Product product = new Product(pacQueue);
		Consumer consumer = new Consumer(pacQueue);
		for (int i = 0; i < 3; i++) {
			Thread t = new Thread(product);
			t.setName("c" + i);
			t.start();
		}
		for (int i = 0; i < 3; i++) {
			Thread t = new Thread(consumer);
			t.setName("p" + i);
			t.start();
		}
	}

	public static void main(String[] args) {
		ProductAndConsumerII pac = new ProductAndConsumerII(5);
		pac.execute();
	}
}
