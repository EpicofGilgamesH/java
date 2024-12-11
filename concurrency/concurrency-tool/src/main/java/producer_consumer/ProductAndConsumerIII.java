package producer_consumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者-消费者模型,基于ReentrantLock.Condition
 * 中间的缓冲区用队列来实现 Deque
 * <p>
 * 此时加入元素的等待线程存放在一个等待队列;取出元素的等待队列存放在一个等待队列;他们独立分开
 */
public class ProductAndConsumerIII {

	private final int size;

	public ProductAndConsumerIII(int size) {
		this.size = size;
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

	static class pacQueue {
		private final Queue<Integer> queue = new LinkedList<>();
		private final ReentrantLock lock;
		private final Condition fullCondition;
		private final Condition emptyCondition;
		private final int size;
		private int value = 0;

		public pacQueue(int size) {
			this.size = size;
			lock = new ReentrantLock();
			fullCondition = lock.newCondition();
			emptyCondition = lock.newCondition();
		}

		public void add() throws InterruptedException {
			lock.lock();
			try {
				if (queue.size() == size) {
					fullCondition.await();
				}
				if (queue.size() < size) {
					int v = value++;
					queue.offer(v);
					System.out.println("[生产]:" + Thread.currentThread().getName() + "-" + v);
					emptyCondition.signalAll();
				}
			} finally {
				lock.unlock();
			}
		}

		public void poll() throws InterruptedException {
			lock.lock();
			try {
				if (queue.isEmpty()) {
					emptyCondition.await();
				}
				if (!queue.isEmpty()) {
					Integer v = queue.poll();
					System.out.println("[消费]:" + Thread.currentThread().getName() + "-" + v);
					fullCondition.signalAll();
				}
			} finally {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		ProductAndConsumerIII pac = new ProductAndConsumerIII(5);
		pac.execute();
	}
}
