import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author lizzy
 * @Date 2020/5/18 21:41
 * @Version 1.0
 */
public class Test {

    private static final Logger log = LoggerFactory.getLogger(Test.class);

    public volatile static boolean flag = true;

    public static final Object lock = new Object();

    public static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            while (flag) {
                /*try {
                    TimeUnit.MILLISECONDS.sleep(100); //当线程睡眠时,该线程可见共享变量的变化
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
            /*synchronized (lock) {
                //当线程具有同步块时,该线程可见共享变量的变化
            }*/
            //System.out.println("flag为false,自旋结束");//println具有synchronize关键字,该线程可见共享变量的变化
            log.debug("flag为'false',自旋结束"); //Slf4j,有用到synchronize关键字
        }).start();

        TimeUnit.MILLISECONDS.sleep(2000);

        new Thread(() -> {
            prepare();
            //System.out.println("flag被更新为false");
            log.debug("flag被更新为false");
        }).start();

        TimeUnit.SECONDS.sleep(60);

        HashMap hashMap = new HashMap();
        ConcurrentMap concrrentHashMap=new ConcurrentHashMap();
    }

    private static void prepare() {
        flag = false;
    }

    private static void add() {
        int count = atomicInteger.get();
        if (count > 10) {
            return;
        }
        if (!atomicInteger.compareAndSet(count, count + 1)) {
            return;
        }
        //todo..
        atomicInteger.decrementAndGet();
    }


}
