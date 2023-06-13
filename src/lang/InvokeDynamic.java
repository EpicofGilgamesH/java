package lang;

import java.util.function.Consumer;

/**
 * Invokedynamic
 */
public class InvokeDynamic {

	public static void main(String[] args) {
		Consumer<String> consumer = System.out::println;
		consumer.accept("lambda");

		Consumer<String> consumer1 = x -> System.out.println(x);
		consumer1.accept("lambda1");
	}
}
