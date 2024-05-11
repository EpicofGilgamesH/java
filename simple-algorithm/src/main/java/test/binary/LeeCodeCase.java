package test.binary;

import com.alibaba.fastjson.JSON;

/**
 * @Description 二分 LeeCode 案例
 * @Date 2022-08-11 17:12
 * @Created by wangjie
 */
public class LeeCodeCase {

	//69. x 的平方根
	//给你一个非负整数 x ，计算并返回 x 的 算术平方根 。
	//由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
	//注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5 。
	//0 <= x <= 231 - 1

	/**
	 * 二分思路,限定一个二分的范围,一般是从 [0 - x]
	 * 限于x为非负整数[0-∞] 此处范围限定为 [0 - x/2]
	 * 46340为int最大平方根
	 *
	 * @param x
	 * @return
	 */
	public static int mySqrt(int x) {
		if (x == 1) return 1;
		int s = 0, r = x / 2 > 46340 ? 46340 : x / 2;
		while (s <= r) {
			int poivt = s + ((r - s) >> 1);
			int v = poivt * poivt;
			if (v > x) {
				//在[s...poivt-1]中查找
				r = poivt - 1;
			} else if (v < x) {
				//在[poivt+1...r]中查找
				s = poivt + 1;
			} else {
				return poivt;
			}
		}
		Math.sqrt(2);
		return r;
	}

	/**
	 * 牛顿迭代法
	 *
	 * @param x
	 * @return
	 */
	public static int mySqrt1(int x) {
		if (x == 0) return 0;
		double x0 = x, xi;
		while (true) {
			xi = (x0 + (double) x / x0) * 0.5;
			if (Math.abs(x0 - xi) < 1e-7) {
				break;
			}
			x0 = xi;
		}
		return (int) x0;
	}

	public static void main(String[] args) {
		int i = mySqrt1(2147395599);
		System.out.println(i);

		String s = JSON.toJSONString(null);
		System.out.println(s);
	}

	/**
	 * 50. Pow(x, n)
	 * 实现 pow(x, n) ，即计算 x 的整数 n 次幂函数（即，x^n ）。
	 * <p>
	 * 示例 1：
	 * 输入：x = 2.00000, n = 10
	 * 输出：1024.00000
	 * 示例 2：
	 * 输入：x = 2.10000, n = 3
	 * 输出：9.26100
	 * 示例 3：
	 * 输入：x = 2.00000, n = -2
	 * 输出：0.25000
	 * 解释：2-2 = 1/22 = 1/4 = 0.25
	 * 提示：
	 * <p>
	 * -100.0 < x < 100.0
	 * -2^31 <= n <= 2^31-1
	 * n 是一个整数
	 * 要么 x 不为零，要么 n > 0 。
	 * -10^4 <= x^n <= 10^4
	 */
	public static class MyPow {

		/**
		 * 个人思路:
		 * x的n(整数)次幂 按照常规思路,将x乘n次,但是这样乘法计算的次数太多了
		 * x*x=x^2  x^2*x^2=x^4 那么,我们可以使用上一次计算的值,使用分治的思路呢?
		 * 比如
		 * x^64 ===> x^32 * x^32
		 * x^32 ===> x^16 * x^16
		 * x^16 ===> x^8 * x^8
		 * x^8  ===> x^4 * x^4
		 * x^4  ===> x^2 * x^2
		 * x^2  ===> x^1 * x^1
		 * 可以得出当n为偶数时,可以递归计算,每次n/2
		 * 比如
		 * x^77 ===> x^76 * x
		 * 然后x^76 计算如上所示 如果幂为奇数则需要额外再乘一个x
		 *
		 * @param x
		 * @param n
		 * @return
		 */
		public static double myPow(double x, long n) {
			if (n == 0) return 1.0;
			return n >= 0 ? calcPower(x, n) : 1.0 / calcPower(x, -n);
		}

		private static double calcPower(double x, long n) {
			if (n == 0) return 1.0;
			if (n == 1) return x;
			double v = calcPower(x, n / 2);  // n/2有可能等于0所以要有等于0的退出情况
			if (n % 2 == 1) {
				v = v * v * x;
			} else {
				v = v * v;
			}
			return v;
		}

		/**
		 * 官方思路
		 * 比如 x^77次方 其实是对77进行二进制拆分,成为:1,4,8,64 77的二进制表示为:100110
		 * 也就是说当二进制位为1时,则要对该位置in次方即 x^in合并到结果中
		 * x^77 = x^1 * x^4 * x^8 * x^64
		 *
		 * @param x
		 * @param n
		 * @return
		 */
		public static double myPowOfficial(double x, int n) {
			long n1 = n;
			return n1 >= 0 ? calc(x, n1) : 1.0 / calc(x, -n1);
		}

		private static double calc(double x, long n1) {
			// 对n1进行二进制拆分,迭代除2求余数
			double v = x, ans = 1.0;
			while (n1 > 0) {
				if (n1 % 2 == 1) {
					ans *= v;
				}
				v *= v;
				n1 /= 2;
			}
			return ans;
		}

		public static void main(String[] args) {
			double v = myPowOfficial(2.0, -2147483648);
			double pow = Math.pow(2.0, -2147483648);
			System.out.println(v);
			System.out.println(pow);
		}
	}
}
