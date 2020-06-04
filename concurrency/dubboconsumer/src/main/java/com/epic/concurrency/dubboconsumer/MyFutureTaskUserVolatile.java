package com.epic.concurrency.dubboconsumer;

import java.util.concurrent.*;

/**
 * @author wangjie
 * @version v1.0
 * @description
 * @date 2020/5/14 11:15
 */
public class MyFutureTaskUserVolatile<V> implements RunnableFuture<V> {

    private Callable<V> callable;

    //volatile的原理:在多处理器开发中保证了共享变量的"可见性"
    //lock前缀的指令在多核处理器下引发两件事情:
    //1.将当前处理器缓存行的数据写回到系统内存
    //2.这个写回内存的操作回使在其他cpu里缓存了该内存地址的数据无效
    private volatile Object result;

    public MyFutureTaskUserVolatile(Callable<V> callable) {
        this.callable = callable;
    }

    @Override
    public void run() {
        try {
            result = callable.call();
        } catch (Exception e) {
            e.printStackTrace();
            result = new Object();
        }
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        int count = 0;
        while (result == null) {
            TimeUnit.MILLISECONDS.sleep(10);
            System.out.println("自旋次数" + (++count));
        }
        System.out.println("自旋完成,阻塞结束,拿到结果");
        return (V) result;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
