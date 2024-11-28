package tool;

/**
 * 并行案例
 * 1.查询未对账的订单数据
 * 2.查询已派送单数据
 * 3.执行对账程序
 * 4.将差异写入差异库
 * 以上4个步骤,1和2并行,都完成后执行3,3完成后执行4
 * 1.简单的并行思路:将1和2并行,然后完成后join到主线程执行3-4
 * 2.上述思路中流程为
 * 1->       3 ->  4 -> 1-> .....
 * 2->                  2-> .....
 * 发现1和2的处理必须等主线程的3,4完成之后才能进行下一轮,但是下一轮的1和2并不依赖上一轮的3和4
 * 如何做到1和2完成之后继续下一轮呢? 使用CyclicBarrier使得1和2完成后,执行3和4;此时CyclicBarrier的计数器充值,
 * 1和2继续下一轮.但是如果1和2执行太快,3和4执行太慢,会出现问题;CyclicBarrier的回调线程会太慢,如果回调是线程池操作,
 * 会进入等待队列中;如果回调是主线程操作,会阻塞主线程,CyclicBarrier也会阻塞
 *
 * CountDownLatch 计数等待,同时执行
 * CyclicBarrier 多个线程互相等待,可设置回调函数,计数为0之后会重复
 */
public class ParallelCase {

	/**
	 * 1.通过join回到主线程
	 * while(存在未对账订单){
	 *     //查询未对账订单
	 *     Thread t1=new Thread(()->{
	 *         pos=getPOrders();
	 *     });
	 *     t1.start();
	 *     //查询派送单
	 *     Thread t2=new Thread(()->{
	 *         dos=getDOrders();
	 *     });
	 *     t2.start();
	 *     t1.join();
	 *     t2.join();
	 *     //对账操作
	 *     diff=check(pos,dos);
	 *     //写入差异库
	 *     save(diff);
	 * }
	 *
	 * 2.通过CountDownLatch 回到主线程
	 * CountDownLatch cdl=new CountDownLatch(2);
	 * Executor executor = Executors.newFixedThreadPool(2);
	 * while(存在未对账订单){
	 *  //查询未对账订单
	 *   executor.execute(()->{
	 *   pos=getPOrder();
	 *   cdl.countDown();
	 *  });
	 *  executor.execute(()->{
	 *  dos=getDOrders();
	 *  cdl.countDown();
	 *  })
	 *  cdl.await();
	 *  //对账操作
	 *  diff=check(pos,dos);
	 *  //写入差异库
	 *  save(diff);
	 * }
	 *
	 * 3.通过CyclicBarrier实现
	 * Vector<P> pos;
	 * Vector<D> dos;
	 * Executor executor = Executors.newFixedThreadPool(10);
	 * CyclicBarrier barrier=new CyclicBarrier(2,()->{
	 *   executor.execute(()->check());
	 * });
	 *
	 * void check(){
	 *  P p=pos.remove(0);
	 *  D d=dos.remove(0);
	 *  diff=check(pos,dos);
	 *  save(diff);
	 * }
	 *
	 * void checkAll(){
	 *  executor.execute(()->{
	 *     while(存在未对账订单){
	 *        pos.add(getPOrder());
	 *        barrier.await();
	 *     }
	 *  });
	 *  executor.execute(()->{
	 *     while(存在未对账订单){
	 *       dos.add(getDOrders())
	 *       barrier.await();
	 *     }
	 *  })
	 * }
	 */




}
