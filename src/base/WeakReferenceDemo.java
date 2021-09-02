package base;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Description 弱引用案例
 * @Date 2021-08-10 18:01
 * @Created by wangjie
 * first-GC before:[B@6fdb1f78
 * first-GC before:[B@6fdb1f78
 * first-GC after:[B@6fdb1f78
 * first-GC after:[B@6fdb1f78
 * second-GC after:null
 * second-GC after:null
 * <p>
 * <p>
 * WeaKReference 是弱键引用的含义,即指向堆中对象的指针为弱引用,当堆中对象没有强引用后,
 * 就会将其放入弱引用队列中,在下次GC时进行回收
 */
public class WeakReferenceDemo {

	public static void main(String[] args) throws InterruptedException {

		//100M缓存
		byte[] bytes = new byte[100 * 1024 * 1024];
		//软引用持有该内存数据
		WeakReference<byte[]> weakReference = new WeakReference<>(bytes);
		System.out.println("first-GC before:" + bytes);
		System.out.println("first-GC before:" + weakReference.get());

		//手动GC
		System.gc();
		TimeUnit.MILLISECONDS.sleep(500);
		System.out.println("first-GC after:" + bytes);
		System.out.println("first-GC after:" + weakReference.get());

		//将内存数据的强引用去掉
		bytes = null;  //call to gc
		System.gc();
		TimeUnit.MILLISECONDS.sleep(500);
		System.out.println("second-GC after:" + bytes);
		System.out.println("second-GC after:" + weakReference.get());

		HashMap<String, String> map = new HashMap<>();
		String key = new String("a");
		String value = "1";
		map.put(key, value);
		key = null;
		value = null;


		System.gc();
		TimeUnit.MILLISECONDS.sleep(100);

		WeakHashMap<String, byte[]> weakHashMap = new WeakHashMap<>();
		//String wKey="b"; 常量池从取字符串b的堆地址赋值给栈指针,常量池放在永久代,不会被回收
		String wKey = new String("b");
		byte[] bytesa = new byte[100 * 1024 * 1024];
		bytesa[0] = 1;
		weakHashMap.put(wKey, bytesa);
		wKey = null;

		System.gc();
		TimeUnit.MILLISECONDS.sleep(100);
		System.out.println("");
	}
}
