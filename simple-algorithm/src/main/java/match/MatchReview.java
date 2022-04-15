package match;

import java.util.Random;

/**
 * @Description 字符串匹配复习
 * @Date 2022-03-31 10:09
 * @Created by wangjie
 */
public class MatchReview {

	public final static long A = 'a';

	//2022-03-16 字符串匹配复习,太长时间了,已经遗忘了KMP的思路,需重新梳理思路
	//RK通过hash的方式,在主串与模式串进行比较时,比的是hash值;hash的方式很多
	//1.使用进制的方式 26个字母使用26进制,long类型最多能表示到26^13次方,所以模式串最多只支持13位
	//2.使用素数做为每个字符的hash值,使用某种方式进行运算(怎么让字符顺序不一样的串最后得到值不一样呢?),当然一样有模式串位数限制
	//以上两种方式都有一个技巧,即向后移动一位时,通过前一次子串的hash值-上次子串的第一个字符值+这次子串的最后一个字符值
	public static int rk1(String a, String b) {
		int m = a.length();
		int n = b.length();
		//暴力匹配的思路,主串每次向后滑动一位,与模式串进行匹配 主串从[0-m-n]
		long hm = mHash(b); //模式串
		double hz1 = mHash(a.substring(0, n)); //第一个子串 注意String.substring(beginIndex,length)
		if (hm - hz1 == 0 /*&& isEquals(a, b, 0)*/) { //按进制运算的hash肯定不会重复
			return 0;
		}
		for (int i = 1; i < m - n + 1; i++) {
			hz1 = 26 * (hz1 - ((a.charAt(i - 1) - A) * Math.pow(26, n - 1))) + (a.charAt(i + n - 1) - A);
			if (hm - hz1 == 0 /*&& isEquals(a, b, i)*/) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 模式串的hash计算
	 *
	 * @param b
	 */
	public static long mHash(String b) {
		int n = b.length();
		long h = 0;
		long mul = 1;
		for (int i = n - 1; i >= 0; i--) {
			h += (b.charAt(i) - A) * mul;
			mul *= 26;
		}
		return h;
	}

	/**
	 * 比较字符串是否一致
	 *
	 * @param a
	 * @param b
	 * @param i
	 * @return
	 */
	public static boolean isEquals(String a, String b, int i) {
		return a.substring(i, b.length() - 1).equals(b);
	}

	public static void main(String[] args) {
		/*String a = "abcdefg";
		String b = "bcd";

		int i = rk1(a, b);
		System.out.println(i);*/

		//模拟主串 15位小写字母
		//模式串 7位小写字母
		String str = "abcdefghijklmnopqrstuvwxyz";
		String m = "";
		Random random = new Random();
		for (int i = 0; i < 15; i++) {
			int index = random.nextInt(26);
			m += str.charAt(index);
		}
		String[] arr = new String[100000];
		for (int j = 0; j < 100000; j++) {
			String n = "";
			for (int i = 0; i < 7; i++) {
				int index = random.nextInt(26);
				n += str.charAt(index);
			}
			arr[j] = n;
		}
		System.out.println("初始化完成");
		long c = System.currentTimeMillis();
		int mr = 0, mcs = 0;
		for (int i = 0; i < arr.length; i++) {
			int r = rk1(m, arr[i]);
			if (r == 1) mr++;
			//System.out.println("rk 第" + i + "次匹配结果:" + r + " | 主串:" + m + ",模式串:" + arr[i] + ",成功次数:" + mr);
		}
		System.out.println("rk总耗时:" + (System.currentTimeMillis() - c));

		long c1 = System.currentTimeMillis();
		for (int i = 0; i < arr.length; i++) {
			boolean s = m.contains(arr[i]);
			if (s) mcs++;
			//System.out.println("cts 第" + i + "次匹配结果:" + s + " | 主串:" + m + ",模式串:" + arr[i] + ",成功次数:" + mcs);
		}
		System.out.println("cs总耗时:" + (System.currentTimeMillis() - c1));


		{
			int a = 1024;
			int b = 57;
			a = a ^ b;
			a = a ^ b;
			System.out.println(a);
		}

	}
}
