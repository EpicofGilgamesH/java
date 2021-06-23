package fifteen.pratice;

import java.text.DecimalFormat;

/**
 * @Description 简单二分查找
 * @Date 2020/7/16 17:28
 * @Created by wangjie
 */
public class HalfFind {

	/*
	 *@Description
	 *@Param a 目标数组
	 *@Param n 数组长度
	 *@Param value 查找值
	 *@Return 存在返回索引,不存在返回-1
	 *@Date 2020/7/16:17:31
	 *@Auther wangjie
	 */
	public static int simpleHalfFind(int[] a, int n, int value) {
		int low = 0;
		int high = n - 1;
		while (low <= high) {
			//int mid = (high + low) / 2; high+low在数据量大的情况下,会出现溢出情况
			//int mid = low + (high - low) / 2;
			//将除法转换为移位操作 向右移一位,表示/2
			int mid = low + ((high - low) >> 1);
			if (value == a[mid]) {
				return mid;
			} else if (value > a[mid]) {
				low = mid + 1;
			} else if (value < a[mid]) {
				high = mid - 1;
			}
		}
		return -1;
	}

	/*
	 *@Description 递归二分查找法
	 *@Param
	 *@Return
	 *@Date 2020/7/16:18:06
	 *@Auther wangjie
	 */
	public static int recursiveHalfFind(int[] a, int n, int value) {
		return recursiveFind(a, 0, n - 1, value);
	}

	public static int recursiveFind(int[] a, int low, int high, int value) {
		//1.退出条件 low>high
		//2.递推公式 value>a[mid]->low=mid+1  value<a[mid]->high=mid-1
		if (low > high) return -1;
		int mid = low + ((high - low) >> 1);
		if (value > a[mid]) {
			return recursiveFind(a, mid + 1, high, value);
		} else if (value < a[mid]) {
			return recursiveFind(a, low, mid - 1, value);
		} else {
			return mid;
		}
	}

	//如何编程实现"求一个数的平方根?" 要求精确到小数点后6位。
	//首先一个数的平方肯定是正数,平方根需要考虑0-1 和>1的情况
	//当0-1的数开根号,查找的范围在[x...1]
	//当>1的数开根号,查找的范围在[1...x]
	//精确到小数点后6位,先找到整数,然后再逐一找每一位小数,每位小数的范围为[0...9]
//	public static Double squareRoot(int a) {
//		if (a < 0) return 0d;
//		int low, high;
//		if (a < 1) {
//			low = 0;
//			high = 1;
//		} else {
//			low = 1;
//			high = a;
//		}
//		double value = 0d;
//		for (int i = 0; i < 7; ++i) {
//			if (i > 0) {
//				low = 0;
//				high = 9;
//			}
//			int mid = 0;
//			while (low < high) {
//				mid = low + ((high - low) >> 1);
//				if (mid * mid > a) {
//					high = mid - 1;
//				} else if (mid * mid < a) {
//					low = mid + 1;
//				} else {
//					break;
//				}
//			}
//			transfer(value, mid);
//		}
//		return 0d;
//	}
//
//	public static Double transfer(double d, int n) {
//		DecimalFormat df = new DecimalFormat("0.00000");
//		String val = getStr(df.format(d)) + n;
//		return new Double(val);
//	}
//
//	public static String getStr(String aa) {
//		//去掉尾部的0
//		char[] chars = aa.toCharArray();
//		int index = chars.length - 1;
//		for (int i = chars.length - 1; i > 0; --i) {
//			if (chars[i] != '0') {
//				index = i;
//				break;
//			}
//		}
//		return aa.substring(0, index + 1);
//	}


	//自己琢磨的思路不对,在网上找到其他人的思路方案
	//1.先确定当前数的平方根所属的最小整数区间 [i-1..i]
	//2.在[i-1...i]区域中,无线取中值K,K*K<n时范围缩小到[K...i];K*K>n时范围缩小到[i-1...K]
	//3.当hign-low的差值小于精确值时,说明已经获取了该精度的值;若hign-low的差值>精确值时,说明还未达到精度
	public static double sqrt(int n, Double precise) {
		if (n < 0) return -1;
		//确定最小整数区间
		int i = 0;
		for (; i <= n; ++i) {
			int square = i * i;
			if (square == n) {
				return i;
			} else if (square > n) {  //当i的平方第一次>n时,则该范围区间为[i-1...i]
				break;
			}
		}
		double low = i - 1, high = i, pres = precise != null ? precise : 1e-7;
		double mid, suq;
		while (high - low > pres) {
			mid = low + ((high - low) / 2);
			suq = mid * mid;
			if (suq > n) {   //mid的平方>n,向左缩小区间
				high = mid;
			} else if ((suq < n)) { //mid的平方>n,向右缩小区间
				low = mid;
			} /*else {
				return mid;   //整数的平方根不是整数就是无理数,所以这里不可能等于
			}*/
		}
		return low + ((high - low) / 2);
	}

	public static void main(String[] args) {
		/*int[] a = new int[]{8, 11, 19, 23, 27, 33, 45, 55, 67, 98};
		System.out.println("index:" + simpleHalfFind(a, a.length, 19));

		System.out.println("index1:" + recursiveHalfFind(a, a.length, 45));*/

		/*Double d = 0.01;
		DecimalFormat df = new DecimalFormat("0.00000");
		String st = df.format(0.1);
		String s = st + 3;
		System.out.println(s);
		Double db = new Double(s);
		System.out.println(db);

		//去掉尾部的0
		String aa = "0.00312";
		char[] chars = aa.toCharArray();
		int index = chars.length - 1;
		for (int i = chars.length - 1; i > 0; --i) {
			if (chars[i] != '0') {
				index = i;
				break;
			}
		}
		String ss = aa.substring(0, index + 1);
		System.out.println(ss);*/
		double d = sqrt(5, 0.001);
		System.out.println(d);
	}
}
