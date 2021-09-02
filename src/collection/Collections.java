package collection;

import java.util.*;

/**
 * @Description 集合类
 * @Date 2021-07-28 16:37
 * @Created by wangjie
 */
public class Collections {

	static final int MAXIMUM_CAPACITY = 1 << 30;

	public static void main(String[] args) {
		//迭代器
		/*Collection<Integer> collection = new ArrayList<>();
		collection.add(1);
		collection.add(2);
		collection.add(3);
		collection.add(4);
		collection.add(5);
		collection.add(6);
		collection.add(7);

		Iterator<Integer> iterator = collection.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

		System.out.println("迭代器是否还有元素:" + iterator.hasNext());
		collection.forEach(System.out::println);*/

		System.out.println("1:" + Float.isNaN(0.01F));

		System.out.println("2:" + Float.isNaN(0.00F / 0.00F));

		Map<String, Integer> map = new HashMap<>();

		System.out.println(Integer.toBinaryString(65));

		tableSize(16);

		LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
		linkedHashMap.put(1, "1");

	}


	/**
	 * 获取key的hash值
	 * HashMap中table的index定义 index = (tab.length-1) & hash
	 * 定义tab.length为2的n次方幂
	 *
	 * @param key
	 * @return
	 */
	static final int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	/**
	 * 将传入的整数转变为2的n次方
	 * 技巧分析
	 * 1.>>>  无符号右移
	 * 2.本方法是需要得到比cap大,但最小的2的n次方  如果cap刚好是2的n次方
	 * 此时进行后续位移运算(其实就是将最高位1,往后推进,直至从最高位1开始后面所有位均为1)
	 * 最后得到的值会为2^n -1 最后+1 会使值变大,所以需要cap-1 进行运算
	 *
	 * @param cap
	 * @return
	 */
	static final int tableSizeFor(int cap) {
		int n = cap - 1;
		n |= n >>> 1; //将最高位的1的右边一位也变为1
		n |= n >>> 2; //将最高位连着的两个1的右边两位也变为1
		n |= n >>> 4; //依次类推
		n |= n >>> 8;
		n |= n >>> 16;
		return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
	}

	static final int tableSize(int cap) {
		int n = cap;
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;
		n |= n >>> 16;
		return n;
	}

}
