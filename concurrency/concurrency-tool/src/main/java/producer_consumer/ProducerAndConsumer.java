package producer_consumer;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 生产者与消费者 通知等待机制
 * 生产者和消费者只负责对数据的生产和消费  缓冲区负责数据的输入和输出
 *
 * @Date 2021-08-19 9:59
 * @Created by wangjie
 */
public class ProducerAndConsumer {

	/**
	 * 生产者,每次产生一条数据
	 * 当缓冲区数据量达到阈值时,停止生产
	 */
	public static class Producer implements Runnable {

		private Buffer buffer;

		public Producer(Buffer buffer) {
			this.buffer = buffer;
		}

		@Override
		public void run() {
			for (; ; ) {
				try {
					buffer.add();
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 消费者 每次消费一条数据
	 * 当缓冲区数据为空时,停止消费
	 */
	public static class Consumer implements Runnable {

		private Buffer buffer;

		public Consumer(Buffer buffer) {
			this.buffer = buffer;
		}

		@Override
		public void run() {
			for (; ; ) {
				try {
					buffer.remove();
					TimeUnit.MILLISECONDS.sleep(1200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}


	public static class Buffer {

		/**
		 * 缓存队列
		 */
		private LinkedList<Integer> linkedList = new LinkedList<>();

		/**
		 * 队列值
		 */
		private int value = 0;

		/**
		 * 向缓冲区添加元素
		 */
		public synchronized void add() throws InterruptedException {
			if (linkedList.size() >= 5) { //缓冲区超出阈值
				System.out.println(Thread.currentThread().getName() + ":[p]进入等待");
				this.wait();
				System.out.println(Thread.currentThread().getName() + ":[p]等待被唤醒");
			}
			int a = value++;
			linkedList.push(a);
			System.out.println(Thread.currentThread().getName() + ":[p]添加任务" + a);
			//添加任务通知进行消费
			System.out.println(Thread.currentThread().getName() + ":[p]notifyAll");
			this.notifyAll();
		}

		/**
		 * 向缓冲区删除元素
		 */
		public synchronized void remove() throws InterruptedException {
			if (linkedList.size() <= 0) { //缓冲区没有任务
				System.out.println(Thread.currentThread().getName() + ":[c]进入等待");
				this.wait();
				System.out.println(Thread.currentThread().getName() + ":[c]等待被唤醒");
			}
			if (linkedList.size() > 0) {
				Integer pop = linkedList.pop();
				System.out.println(Thread.currentThread().getName() + ":[c]消费任务" + pop);
				//消费任务通知其他等待线程进行生产
				System.out.println(Thread.currentThread().getName() + ":[c]notifyAll");
				this.notifyAll();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Buffer buffer = new Buffer();
		Producer producer = new Producer(buffer);
		Consumer consumer = new Consumer(buffer);
		Thread thread = new Thread(producer);
		Thread thread1 = new Thread(consumer);
		Thread thread2 = new Thread(consumer);
		Thread thread3 = new Thread(consumer);

		thread.start();
		thread1.start();
		thread2.start();
		thread3.start();

		thread.join();
	}
}
