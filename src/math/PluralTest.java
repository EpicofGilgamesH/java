package math;

/**
 * 复数类测试
 */
public class PluralTest {

	public static void main(String[] args) {
		// 创建测试复数
		Plural c1 = new Plural(3, 2);  // 3+2i
		Plural c2 = new Plural(1, -4); // 1-4i
		Plural c3 = new Plural(0, 2);  // 0+2i
		Plural c4 = new Plural(-1, 0); // -1+0i
		Plural zero = new Plural(0, 0);// 0+0i

		// 加法测试
		System.out.println("加法测试:");
		System.out.println(c1 + " + " + c2 + " = " + c1.add(c2)); // (3+2i) + (1-4i) = 4-2i
		System.out.println(c3 + " + " + c4 + " = " + c3.add(c4)); // (2i) + (-1) = -1+2i
		System.out.println();

		// 减法测试
		System.out.println("减法测试:");
		System.out.println(c1 + " - " + c2 + " = " + c1.subtract(c2)); // (3+2i) - (1-4i) = 2+6i
		System.out.println(c3 + " - " + c3 + " = " + c3.subtract(c3)); // (2i) - (2i) = 0
		System.out.println();

		// 乘法测试
		System.out.println("乘法测试:");
		System.out.println(c1 + " * " + c2 + " = " + c1.multiply(c2)); // (3+2i)*(1-4i) = 11-10i
		System.out.println(c4 + " * " + c3 + " = " + c4.multiply(c3)); // (-1)*(2i) = -2i
		System.out.println();

		// 除法测试
		System.out.println("除法测试:");
		System.out.println(c1 + " / " + c2 + " = " + c1.divide(c2)); // (3+2i)/(1-4i) = -0.2+0.8i
		System.out.println(c3 + " / " + c4 + " = " + c3.divide(c4)); // (2i)/(-1) = -2i

		// 除零异常测试
		System.out.println("\n异常测试:");
		try {
			System.out.println(c1 + " / " + zero + " = " + c1.divide(zero));
		} catch (ArithmeticException e) {
			System.out.println(e.getMessage());
		}
	}
}
