package guava;


import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 布隆过滤器的使用
 * @Date 2020/8/21 16:54
 * @Created by wangjie
 */
public class BloomFilterHelper {


	private static final long expe = 100000;
	private static final double fpp = 0.001;

	public static void main(String[] args) {

		BloomFilter<String> filter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), expe, fpp);
		filter.put("1");
		filter.put("2");
		filter.put("3");
		filter.put("4");
		filter.put("5");
		filter.put("6");
		filter.put("7");
		filter.put("8");
		filter.put("9");
		filter.put("10");
		filter.put("11");

		boolean b = filter.mightContain("11");
		System.out.println("bloom=>" + b);

		Stream<String> stringStream = Stream.of("1", "2", "3", "4", "5");
		List<String> collect = stringStream.peek(System.out::println).collect(Collectors.toList());

		System.out.println("stop");
		//peek和map的区别
		//stringStream.forEach(System.out::println);
		//peek 一般做debug使用
	}

}
