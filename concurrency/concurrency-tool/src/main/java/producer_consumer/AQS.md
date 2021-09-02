#`AQS`中`Condition`源码解析

##await()方法
```
      public final void await() throws InterruptedException {
            //线程中断则抛出异常
            if (Thread.interrupted())  
                throw new InterruptedException();
            //将当前线程包装成Node,然后插入等待链队列(单链结构)的尾部
            Node node = addConditionWaiter();
            //释放当前线程所占用的lock,完全释放;并释放同步队列(双链结构)中下一个结点
            int savedState = fullyRelease(node);
            int interruptMode = 0;
            //跳出while条件:该节点不在同步队列中,如果线程被park处被唤醒,
            //但该节点不在同步队列中,无法跳出while循环
            while (!isOnSyncQueue(node)) {
                //当前线程进入等待
                LockSupport.park(this);
                //如果被打断,也能跳出循环
                if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
                    break;
            }
            //cas获取同步锁
            if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
                interruptMode = REINTERRUPT;
            //   
            if (node.nextWaiter != null) // clean up if cancelled
                unlinkCancelledWaiters();
            if (interruptMode != 0)
                reportInterruptAfterWait(interruptMode);
        }
```
```
//如果等待队列的firstWaiter=null,即等待队列为空队列,则将firstWaiter指向当前Node
//否则更新lastWaiter
private Node addConditionWaiter() {
    Node t = lastWaiter;//尾节点
    // If lastWaiter is cancelled, clean out.
    if (t != null && t.waitStatus != Node.CONDITION) {
        unlinkCancelledWaiters();//清除等待节点
        t = lastWaiter;
    }
    //将当前线程包装成Node
    Node node = new Node(Thread.currentThread(), Node.CONDITION);
    if (t == null)//队列为空
        firstWaiter = node;//设置node为头节点
    else//队列不为空。从队列尾部插入
        //尾插入
        t.nextWaiter = node;
    //更新lastWaiter
    lastWaiter = node;
    return node;
}
```

```
//当前节点插入等待队列的尾部后,会释放锁
//AQS模板方法,释放锁会唤醒同步队列下一个节点的线程
final int fullyRelease(Node node) {
    boolean failed = true;
    try {
        int savedState = getState();
        if (release(savedState)) {
            //成功释放同步状态
            failed = false;
            return savedState;
        } else {
            //不成功释放同步状态抛出异常
            throw new IllegalMonitorStateException();
        }
    } finally {
        if (failed)
            node.waitStatus = Node.CANCELLED;
    }
}
```