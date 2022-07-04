package bitmap;

import java.util.ArrayList;
import java.util.Random;

import java.util.List;

/**
 * @Description 假设我们有 1 亿个整数，数据范围是从 1 到 10 亿，如何快速并且省内存地给这 1 亿个数据从小到大排序? 位图排序
 * @Date 2022-06-30 15:29
 * @Created by wangjie
 */
public class SortByMap {

	private int count;
	private BitMap bitMap;
	private int size;

	public SortByMap(int count, int size) {
		this.count = count;
		this.bitMap = new BitMap(count);
		this.size = size;
	}

	/**
	 * 放入所有的数据到map
	 */
	private void putAll() {
		Random random = new Random();
		int si = 0;
		while (si < size) {
			int r = random.nextInt(count);
			if (bitMap.set(r)) {
				si++;
			}

		}
		System.out.println(si);
	}

	/**
	 * 获得排序数组
	 *
	 * @return
	 */
	private List<Integer> sort() {
		List<Integer> re = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			if (bitMap.get(i))
				re.add(i);
		}
		return re;
	}

	public static void main(String[] args) {
		SortByMap sm = new SortByMap(10000000, 100000);
		sm.putAll();
		List<Integer> sort = sm.sort();
		System.out.println(sort.size());

		int i = 1 >> 3;
		System.out.println(i);
	}
}
