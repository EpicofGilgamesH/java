package functional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Description TODO
 * @Date 2020/7/15 11:32
 * @Created by wangjie
 */
public class PredicateJava8 {

	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		//有一个T类型参数返回bool类型值的泛型委托
		Predicate<Integer> predicate1 = x -> x > 7;
		//常规示例
		System.out.println(predicate1.test(1));
		System.out.println(predicate1.test(8));

		//集合筛选示例
		List<Integer> list1 = list.stream().filter(predicate1).collect(Collectors.toList());
		System.out.println(list1);

		//无参数输入,返回T类型的泛型委托
		Supplier<Integer> supplier1 = () -> 3 * 2;
		Integer a = supplier1.get();
		System.out.println(a);

		//有一个T类型的参数，无返回值
		Consumer<String> consumer1 = x -> System.out.println("print:" + x);
		consumer1.accept("123");

		Function<Integer, Integer> function1 = x -> 2 * x;
		function1.apply(5);
		Function<Integer, Integer> function2 = x -> x * x;
		//先执行function2参数,再执行调用者 即:5*5  2*25
		System.out.println(function1.compose(function2).apply(5));
		//先执行调用者,再执行参数 即 2*5  10*10
		System.out.println(function1.andThen(function2).apply(5));

	}
}
