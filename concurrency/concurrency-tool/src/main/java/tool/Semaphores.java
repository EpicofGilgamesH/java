package tool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Description 信号量分析
 * @Date 2021-08-30 14:14
 * @Created by wangjie
 */
public class Semaphores {

	static class Task extends Thread {

		public Task(Semaphore semaphores, CountDownLatch end, CountDownLatch start) {
			this.semaphore = semaphores;
			this.end = end;
			this.start = start;
		}

		private Semaphore semaphore;
		private CountDownLatch end;
		private CountDownLatch start;

		public void run() {
			try {
				end.await();
				semaphore.acquire();
				System.out.println(getName() + "acquire");
				//处理事件 要求同时只能有2个线程处理该事件，控制信号量
				TimeUnit.MILLISECONDS.sleep(1000);
				System.out.println("----------------");
				semaphore.release();
				System.out.println(getName() + "release");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				start.countDown(); //当所有的线程任务完成后,唤醒主线程
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {

		CountDownLatch start = new CountDownLatch(100); //作用在主线程上,让所有task线程完成后,才唤醒主线程
		CountDownLatch end = new CountDownLatch(100); //作用在task线程上,让所有的task线程都创建完后,同时被唤醒
		Semaphore semaphore = new Semaphore(2);
		for (int i = 0; i < 100; i++) {
			new Task(semaphore, end, start).start();
			end.countDown();
		}

		TimeUnit.SECONDS.sleep(1);
		start.await(); //让主线程进行等待,所有任务完成后主线程被唤醒
		System.out.println("");
	}
}
