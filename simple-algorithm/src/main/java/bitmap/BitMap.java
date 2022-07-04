package bitmap;

/**
 * @Description 位图、布隆过滤器 基于char数组16位构建的位图
 * @Date 2022-06-29 11:30
 * @Created by wangjie
 */
public class BitMap {

	private char[] bytes; //char占2个字节 16位
	private int bits; //存储的数据范围

	private int count;

	public BitMap(int bits) {
		this.bits = bits;
		this.bytes = new char[bits / 16 + 1]; //初始化 需要多少个char
	}

	public boolean set(int k) {
		if (k > bits) return false;
		int byteIndex = k / 16;
		int bitIndex = k % 16;
		if (!get(k)) {
			bytes[byteIndex] |= 1 << bitIndex;
			count++;
			return true;
		}
		return false;
	}

	public boolean get(int k) {
		if (k > bits) return false;
		int byteIndex = k / 16;
		int bitIndex = k % 16;
		return (bytes[byteIndex] & (1 << bitIndex)) != 0;
	}

	public static void main(String[] args) {
		int i = 1 << 7;
		System.out.println(i);

		BitMap bitMap = new BitMap(100);
		bitMap.set(16);
		bitMap.set(17);
		bitMap.set(21);

		boolean b = bitMap.get(16);
		System.out.println(b);

		long p = 20L, q = -5L;
		int signum = 1 | (int) ((p ^ q) >> (Long.SIZE - 1));
		System.out.println(signum);


	}
}
