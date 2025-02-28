package collection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 对集合进行切片操作
 */
public class SubList {

	private static final List<List<Integer>> list = new ArrayList<>();

	/**
	 * List.sbuList()会引发oom错误
	 * 因为sbuList进行集合切片时,返回的是原List的视图,只是通过offset和size来定义集合区间
	 * 原集合对象不会被释放,因为subList返回的集合有原List对象的强引用
	 * 并且修改切边集合会对原始集合产生影响;原始集合添加数据时,切片集合在for循环时也会出现异常
	 */
	private static void oom() {
		for (int i = 0; i < 1000; i++) {
			List<Integer> collect = IntStream.rangeClosed(1, 1000000).boxed().collect(Collectors.toList());
			list.add(collect.subList(0, 1));
		}
	}

	/**
	 * 那么使用什么方式进行集合的切片会比较好呢?
	 */
	private static void subList() {
		List<Integer> collect = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
		// 1.为切片的集合重新创建对象
		List<Integer> list = new ArrayList<>(collect.subList(0, 1));
		// 2.使用流操作
		List<Integer> list1 = collect.stream().skip(0).limit(1).collect(Collectors.toList());

	}

	public static void main(String[] args) {
		oom();
		System.out.println();
	}
}
