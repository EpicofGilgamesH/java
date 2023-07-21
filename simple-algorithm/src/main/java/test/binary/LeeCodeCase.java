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
}
