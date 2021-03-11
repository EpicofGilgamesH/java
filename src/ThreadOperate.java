import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description 线程等待/唤醒/打断操作
 * @Date 2021-01-04 10:25
 * @Created by wangjie
 */
public class ThreadOperate {

	private static final ConcurrentHashMap<String, Mythread> THREAD_MAP = new ConcurrentHashMap();

	private static final ConcurrentHashMap<String, ReentrantLock> LOCK_MAP = new ConcurrentHashMap<>();

	private static final ConcurrentHashMap<String, Condition> CONDITION_MAP = new ConcurrentHashMap<>();

	public static void main(String[] args) throws InterruptedException {

		int threadCount = 10;
		for (int i = 0; i < threadCount; i++) {
			String name = "thread-" + i;
			ReentrantLock lock = new ReentrantLock();
			LOCK_MAP.put(name, lock);
			Condition condition = lock.newCondition();
			CONDITION_MAP.put(name, condition);
			THREAD_MAP.put(name, new Mythread(name, lock, condition));
		}

		for (Map.Entry<String, Mythread> entry : THREAD_MAP.entrySet()) {
			Mythread mythread = entry.getValue();
			mythread.start();
			mythread.sleep(20);
		}

		TimeUnit.SECONDS.sleep(5);

		System.out.println("开始中断线程...");
		Mythread mythread1 = THREAD_MAP.get("thread-1");
		mythread1.flag = true;
		mythread1.interrupt();
		Mythread mythread2 = THREAD_MAP.get("thread-3");
		mythread2.flag = true;
		mythread2.interrupt();

		Mythread mythread8 = THREAD_MAP.get("thread-8");
		mythread8.wait = true;

		TimeUnit.SECONDS.sleep(5);

		ReentrantLock lock = LOCK_MAP.get("thread-8");
		Condition condition = CONDITION_MAP.get("thread-8");
		try {
			lock.lock();
			System.out.println("主线程准备唤醒thread-8线程...");
			condition.signal();
			mythread8.wait = false;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			lock.unlock();
		}

		TimeUnit.SECONDS.sleep(1);
		if (mythread8.wait == true) {
			try {
				lock.lock();
				System.out.println("主线程准备唤醒thread-8线程...");
				condition.signal();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		TimeUnit.SECONDS.sleep(1000);
	}

	//打断指定线程
	public boolean interrupt(String threadName) {
		try {
			Mythread mythread = THREAD_MAP.get(threadName);
			mythread.flag = true;
			mythread.interrupt();
			//记录日志...
			System.out.println("");
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	//阻塞指定线程
	public boolean wait(String threadName) {
		Mythread mythread = THREAD_MAP.get(threadName);
		mythread.wait = true;
		//记录日志,线程等待是在本次循环任务执行完成之后进入等待,请观察日志
		System.out.println("");
		return true;
	}

	//唤醒指定线程
	public boolean notify(String threadName) {
		Mythread mythread = THREAD_MAP.get(threadName);
		if (mythread.wait != true)
			return false;
		ReentrantLock lock = LOCK_MAP.get(threadName);
		Condition condition = CONDITION_MAP.get(threadName);
		try {
			lock.lock();
			System.out.println("主线程准备唤醒thread-8线程...");
			condition.signal();
			mythread.wait = false;
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			lock.unlock();
		}
	}


	static class Mythread extends Thread {

		public volatile boolean flag = false; //共享变量打断

		public volatile boolean wait = false; //共享变量等待

		private ReentrantLock lock;

		private Condition condition;

		public Mythread(String name, ReentrantLock lock, Condition condition) {
			super(name);
			this.lock = lock;
			this.condition = condition;
		}

		public void run() {
			while (!flag) {
				lock.lock();
				try {
					if (wait) {
						System.out.println(getName() + "->开始等待...");
						condition.await();
					}
					System.out.println(getName() + "->执行开始...");
					try {
						TimeUnit.MILLISECONDS.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(getName() + "->执行结束...");
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
			System.out.println(getName() + "线程退出...");

		}
	}

}
