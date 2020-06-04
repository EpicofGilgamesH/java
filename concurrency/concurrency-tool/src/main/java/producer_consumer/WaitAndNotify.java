package producer_consumer;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangjie
 * @version v1.0
 * @description 等待通知机制的经典范式
 * @date 2020/5/14 16:31
 */
public class WaitAndNotify {

    //缓冲区
    private AtomicInteger count = new AtomicInteger(5);

    /**
     * 生产者
     *
     * @param
     * @return
     * @author wangjie
     * @date 16:33 2020/5/14
     */
    public synchronized void producer() throws InterruptedException {
        //缓冲区满了,则停止生产,阻塞线程
        //被唤醒的线程从wait后开始执行
        if (count.get() == 5) {
            System.out.println("生产一条数据.Thread:" + Thread.currentThread().getName() + ","
                    + "线程准备进入等待" + System.currentTimeMillis());
            this.wait();
            System.out.println("生产一条数据.Thread:" + Thread.currentThread().getName() + ","
                    + "线程被唤醒" + System.currentTimeMillis());
        }
        count.getAndIncrement();
        TimeUnit.MILLISECONDS.sleep(2000);
        System.out.println("生产一条数据.Thread:" + Thread.currentThread().getName() + "," + System.currentTimeMillis());
        //notify 同步队列中随机释放一个等待线程,去获取锁
        this.notify();
    }

    /**
     * 消费者
     *
     * @param
     * @return
     * @author wangjie
     * @date 16:40 2020/5/14
     */
    public synchronized void consumer() throws InterruptedException {
        if (count.get() <= 0) {
            this.wait();
        }
        count.getAndDecrement();
        TimeUnit.MILLISECONDS.sleep(2000);
        System.out.println("消费一条数据.Thread:" + Thread.currentThread().getName() + "," + System.currentTimeMillis());
        this.notify();
    }

    @Test
    public void Test() throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            while (true) {
                try {
                    producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
