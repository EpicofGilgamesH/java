package base;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

/**
 * @Description 软引用案例
 * @Date 2021-08-10 16:36
 * @Created by wangjie
 */
public class SoftReferenceDemo {

	/**
	 * -XX:+PrintGC 打印gc简单信息
	 * -XX:+PrintGCDetails 打印gc详情
	 * <p>
	 * 1- before GC:null
	 * 1- before GC:[B@6fdb1f78
	 * [GC (System.gc())  106547K->103376K(196608K), 0.0019325 secs]
	 * [Full GC (System.gc())  103376K->103157K(196608K), 0.0055756 secs]   //手动Full GC
	 * 1- after GC:null
	 * 1- after GC:[B@6fdb1f78
	 * [GC (Allocation Failure)  104191K->103253K(196608K), 0.0009145 secs]   //Allocation Failure内存不足 继续GC
	 * [GC (Allocation Failure)  103253K->103349K(196608K), 0.0009032 secs]
	 * [Full GC (Allocation Failure)  103349K->103056K(196608K), 0.0059838 secs]  //进行Full GC 还是不足
	 * [GC (Allocation Failure)  103056K->103056K(196608K), 0.0008033 secs]
	 * [Full GC (Allocation Failure)  103056K->637K(154112K), 0.0050298 secs]  //本次Full GC 将软引用对象回收
	 * Disconnected from the target VM, address: '127.0.0.1:63539', transport: 'socket'
	 * after ...null
	 * after...null
	 * <p>
	 * 避免因缓存过多导致项目oom的情况产生
	 *
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		//100M内存数据
		byte[] cacheData = new byte[100 * 1024 * 1024];
		//将内存数据让软引用持有
		SoftReference<byte[]> cacheRef = new SoftReference(cacheData);
		//将内存数据的强引用去掉
		cacheData = null;
		System.out.println("1- before GC:" + cacheData);
		System.out.println("1- before GC:" + cacheRef.get());

		//进行GC操作,查看对象的回收情况
		System.gc();

		//等待GC执行
		TimeUnit.MILLISECONDS.sleep(500);
		System.out.println("1- after GC:" + cacheData);
		System.out.println("1- after GC:" + cacheRef.get());

		byte[] newData = new byte[100 * 1024 * 1024];
		System.out.println("after ..." + cacheData);
		System.out.println("after..." + cacheRef.get());
	}
}
