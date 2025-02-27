package util;

public class BinaryUtil {

	/**
	 * 整数转二进制字符串
	 * 1.简单的方式是不断除以2,直到该数被除到0,那么其余数就是二进制位上每一位的值
	 *
	 * @param v
	 * @return
	 */
	public static String intToBinaryString(int v) {
		StringBuilder binary = new StringBuilder();
		while (v > 0) {
			int i = v % 2;
			binary.insert(0, i);
			v = v / 2;
		}
		return binary.toString();
	}

	/**
	 * 整数转二进制字符串
	 * 2.通过位运算,从最低位开始,与1操作;直到该整数变为0
	 *
	 * @param v
	 * @return
	 */
	public static String intToBinaryStringII(int v) {
		StringBuilder binary = new StringBuilder();
		while (v > 0) {
			binary.insert(0, v & 1);
			v = v >> 1;
		}
		return binary.toString();
	}

	public static void main(String[] args) {
		System.out.println(intToBinaryString(87));
		System.out.println(intToBinaryStringII(87));
		int i = Integer.parseInt("123");

	}
}
