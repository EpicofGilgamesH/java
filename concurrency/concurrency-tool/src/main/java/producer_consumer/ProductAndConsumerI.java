package producer_consumer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者-消费者模型,基于synchronized
 * 中间的缓冲区用队列来实现 Deque
 * <p>
 * 本示例中,锁住的对象是队列,同一时间只有一个线程能持有锁,由于锁的粒度是生产和消费的过程
 * 同一时间只有一个线程在处理生产和消费的过程
 * ----------------------------------------------------------------------------
 * 那么可以对锁的粒度进行细化,实际上只需要队列的加入和弹出是串行的
 */
public class ProductAndConsumerI {

	private final Deque<Integer> deque;
	private final int size;
	private final AtomicInteger v = new AtomicInteger(0);

	public ProductAndConsumerI(int size) {
		this.size = size;
		deque = new ArrayDeque<>(size);
	}

	public void execute() {
		for (int i = 0; i < 3; i++) {
			Thread t = new Thread(new Consumer());
			t.setName("c" + i);
			t.start();
		}
		for (int i = 0; i < 3; i++) {
			Thread t = new Thread(new Product());
			t.setName("p" + i);
			t.start();
		}
	}

	/**
	 * 生产者
	 * 当队列没有满时,需要生产;队列已满时需要等待
	 */
	class Product implements Runnable {

		@Override
		public void run() {
			for (; ; ) {
				synchronized (deque) {
					if (deque.size() == size) {
						try {
							deque.wait();
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					}
					// 队列不满或被唤醒,先判断队列是否已满
					if (deque.size() < size) {
						int p = v.incrementAndGet();
						deque.offer(p);
						try {
							TimeUnit.MILLISECONDS.sleep(100);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
						System.out.println("[生产]:" + Thread.currentThread().getName() + "-" + p);
						deque.notifyAll(); // 唤醒所有线程
					}
				}
			}
		}
	}

	/**
	 * 消费者
	 * 当队列为空时需要等待,不为空时进行消费
	 */
	class Consumer implements Runnable {

		@Override
		public void run() {
			for (; ; ) {
				synchronized (deque) {
					if (deque.isEmpty()) {
						try {
							deque.wait();
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
					}
					// 队列不空或被唤醒,先判断队列是否为空
					if (!deque.isEmpty()) {
						// 消费
						int c = deque.pop();
						try {
							TimeUnit.MILLISECONDS.sleep(200);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
						System.out.println("[消费]:" + Thread.currentThread().getName() + "-" + c);
						deque.notifyAll();
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		ProductAndConsumerI pac = new ProductAndConsumerI(5);
		pac.execute();
	}
}
