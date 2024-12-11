package producer_consumer;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者-消费者模型,基于ReentrantLock.Condition
 * 中间的缓冲区用队列来实现 Queue
 * <p>
 * 之前的方式中,锁的粒度在队列的操作上,那么生产和消费都需要操作队列
 * 实际业务中,生产和消费之间是没有直接关联的,生成的时候只用判断是否还能生产;消费的时候只用判断是否还能消费
 * 所以锁的粒度应控制在生产和消费上,各自持有一把锁,这样的话Queue的新增和取出操作非线程安全,
 * 如何才能拿到LinkedList中真实的size呢?用一个原子类进行技术是可以实现的.
 * LinkedList在并发插入和弹出时,会出现线程安全问题么?会出现线程安全问题.但是本示例中,插入有一把锁,取出有一把锁;
 * 从尾部插入,从头部取出,所以不会出现线程安全问题.
 */
public class ProductAndConsumerIV {

	private final int size;

	public ProductAndConsumerIV(int size) {
		this.size = size;
	}

	public void execute() {
		PacQueue pacQueue = new PacQueue(this.size);
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

		private final PacQueue pacQueue;

		public Product(PacQueue pacQueue) {
			this.pacQueue = pacQueue;
		}

		@Override
		public void run() {
			for (; ; ) {
				try {
					pacQueue.add();
					TimeUnit.MILLISECONDS.sleep(200);
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

		private final PacQueue pacQueue;

		public Consumer(PacQueue pacQueue) {
			this.pacQueue = pacQueue;
		}

		@Override
		public void run() {
			for (; ; ) {
				try {
					pacQueue.poll();
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * 队列
	 */
	static class PacQueue {
		private final int size;
		private final LinkedList<Integer> queue;
		private final AtomicInteger counter = new AtomicInteger(0);
		private final ReentrantLock pLock;
		private final ReentrantLock cLock;
		private final Condition pCondition;
		private final Condition cCondition;
		private int value = 0;

		public PacQueue(int size) {
			this.size = size;
			queue = new LinkedList<>();
			pLock = new ReentrantLock();
			cLock = new ReentrantLock();
			pCondition = pLock.newCondition();
			cCondition = cLock.newCondition();
		}

		private void add() {
			pLock.lock();
			try {
				while (counter.get() == size) {
					System.out.println(Thread.currentThread().getName() + ":[p]生产线程进入等待");
					pCondition.await();
					System.out.println(Thread.currentThread().getName() + ":[p]生产线程被唤醒");
				}
				int i = value++;
				queue.push(i);
				counter.incrementAndGet();
				System.out.println(Thread.currentThread().getName() + ":[p]添加任务:" + i + ",此时库存为:" + counter.get());
				// 当可以生产时,可以唤醒同类线程
				if (counter.get() < size) {
					System.out.println(Thread.currentThread().getName() + ":[p]准备唤醒生产线程");
					// 唤醒生产端add()的等待线程,因消费端正在队列中取出元素
					pCondition.signalAll();
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				pLock.unlock();
			}
			// 此时生产了数据,需要唤醒消费端的poll线程
			if (counter.get() > 0) {
				try {
					// 在没有获取锁进入等待状态时,可以从外部打断该线程
					cLock.lockInterruptibly();
					// 唤醒消费端处于等待的pool线程
					System.out.println(Thread.currentThread().getName() + ":[p]准备唤醒消费线程");
					cCondition.signalAll();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					cLock.unlock();
				}
			}
		}

		private void poll() {
			cLock.lock();
			try {
				while (counter.get() <= 0) {
					System.out.println(Thread.currentThread().getName() + ":[c]消费线程进入等待");
					cCondition.await();
					System.out.println(Thread.currentThread().getName() + ":[c]消费线程被唤醒");
				}
				Integer v = queue.pop();
				int i = counter.decrementAndGet();
				System.out.println(Thread.currentThread().getName() + ":[c]消费任务:" + v + ",库存:" + i);
				if (counter.get() > 0) {
					System.out.println(Thread.currentThread().getName() + ":[c]准备唤醒消费线程");
					cCondition.signalAll();
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				cLock.unlock();
			}
			// 消费一条数据,应唤醒生产线程
			if (counter.get() < size) {
				try {
					pLock.lockInterruptibly();
					System.out.println(Thread.currentThread().getName() + ":[c]准备唤醒生产线程");
					pCondition.signalAll();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} finally {
					pLock.unlock();
				}
			}
		}
	}

	public static void main(String[] args) {
		ProductAndConsumerIV pac = new ProductAndConsumerIV(5);
		pac.execute();
	}
}
