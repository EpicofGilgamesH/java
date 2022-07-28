package queue;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Description 环形队列
 * @Date 2022-07-19 16:00
 * @Created by wangjie
 */
public class CircleQueue {

	private long[] data;
	private int size = 0, head = 0, tail = 0;

	public CircleQueue(int size) {
		this.data = new long[size];
		this.size = size;
	}

	public boolean add(long element) {
		//添加元素超出队列大小
		if ((tail + 1) % size == head)
			return false;
		data[tail] = element;
		tail = (tail + 1) % size;
		return true;
	}

	public Long poll() {
		if (head == tail)
			return null;
		long p = head;
		head = (head + 1) % size;
		return p;
	}

	/**
	 * 生产者
	 */
	public static class Produce {
		private CircleQueue queue;

		public Produce(CircleQueue queue) {
			this.queue = queue;
		}

		public void produce() throws InterruptedException {
			while (true) {
				int d = new Random().nextInt(100);
				if (!queue.add(d))
					TimeUnit.MILLISECONDS.sleep(100);
				else
					System.out.println("生产数据:" + d);
			}
		}
	}

	/**
	 * 消费者
	 */
	public static class Consumer {
		private CircleQueue queue;

		public Consumer(CircleQueue queue) {
			this.queue = queue;
		}

		public void consumer() throws InterruptedException {
			while (true) {
				Long l;
				if ((l = queue.poll()) == null)
					TimeUnit.MILLISECONDS.sleep(100);
				else
					System.out.println("消费数据:" + l);
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		CircleQueue queue = new CircleQueue(100);
		Produce produce = new Produce(queue);
		Consumer consumer = new Consumer(queue);
		Thread thread1 = new Thread(() -> {
			try {
				produce.produce();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		Thread thread2 = new Thread(() -> {
			try {
				consumer.consumer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread1.start();
		thread2.start();

		TimeUnit.SECONDS.sleep(100);
	}
}
