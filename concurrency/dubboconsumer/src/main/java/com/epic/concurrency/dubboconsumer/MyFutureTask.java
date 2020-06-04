package com.epic.concurrency.dubboconsumer;

import java.util.concurrent.*;

/**
 * @author wangjie
 * @version v1.0
 * @description 目前我们只实现FutureTask的run和get方法
 * @date 2020/5/14 10:56
 */
public class MyFutureTask<V> implements RunnableFuture<V> {

    Callable<V> callable;

    V result;

    public MyFutureTask(Callable<V> callable) {
        this.callable = callable;
    }

    /**
     * 实现Runnable的run()方法
     *
     * @param
     * @return
     * @author wangjie
     * @date 10:59 2020/5/14
     */
    @Override
    public void run() {
        synchronized (this) {
            try {
                result = callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.notifyAll();
        }
    }

    /**
     * 实现Future的get()方法
     *
     * @param
     * @return
     * @author wangjie
     * @date 11:00 2020/5/14
     */
    @Override
    public V get() throws InterruptedException, ExecutionException {
        synchronized (this) {
            if (result == null) {
                this.wait();
            }
        }
        return result;
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
