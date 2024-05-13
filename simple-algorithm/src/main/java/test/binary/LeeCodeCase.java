package test.binary;

import com.alibaba.fastjson.JSON;

/**
 * @Description 二分 LeeCode 案例
 * @Date 2022-08-11 17:12
 * @Created by wangjie
 */
public class LeeCodeCase {

	//69. x 的平方根
	// 给你一个非负整数 x ，计算并返回x的 算术平方根 。
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

	/**
	 * 278. 第一个错误的版本
	 * 你是产品经理，目前正在带领一个团队开发新的产品。不幸的是，你的产品的最新版本没有通过质量检测。由于每个版本都是基于之前的版本开发的，所以错误的版本之后的所有版本都是错的。
	 * 假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。
	 * 你可以通过调用 bool isBadVersion(version) 接口来判断版本号 version 是否在单元测试中出错。实现一个函数来查找第一个错误的版本。你应该尽量减少对调用 API 的次数。
	 * 示例 1：
	 * 输入：n = 5, bad = 4
	 * 输出：4
	 * 解释：
	 * 调用 isBadVersion(3) -> false
	 * 调用 isBadVersion(5) -> true
	 * 调用 isBadVersion(4) -> true
	 * 所以，4 是第一个错误的版本。
	 * 示例 2：
	 * 输入：n = 1, bad = 1
	 * 输出：1
	 * 提示：
	 * 1 <= bad <= n <= 231 - 1
	 */
	public static class Solution extends VersionControl {

		/**
		 * 个人思路:
		 * 是否错误版本,有一个特性 他肯定有一个分割点,比如: 000,11111
		 * 那么只需要找到这个分界点就可以了,通过二分的方式,更容易找到分割点
		 *
		 * @param n
		 * @return
		 */
		public static int firstBadVersion(int n) {
			int p = 1, q = n;
			while (p < q) {
				int m = p + (q - p) / 2;
				if (isBadVersion(m)) { // 当前n为错误版本,那正确版本肯定在左边
					q = m - 1;
				} else {
					p = m + 1;
				}
			}
			return isBadVersion(p) ? p : p + 1;
		}

		/**
		 * 0,0,0,1,1,1,1,1
		 * m=4 此时我们为了能找到第一个错误的版本,压缩查找范围时,应q=m;这样保证第一个错误的版本一定还在查找的范围内
		 * 我们要找第一个错误的版本,那就要让p和q压缩到一个点
		 * 二分查找在压缩空间时,有一些细节,要找第一个错误的,还是最后一个正确的
		 */
		public static int firstBadVersionOfficial(int n) {
			int p = 1, q = n;
			while (p < q) {
				int m = p + (q - p) / 2;
				if (isBadVersion(m)) { // 当前n为错误版本,那正确版本肯定在左边
					q = m;
				} else {
					p = m + 1;
				}
			}
			// 此时肯定会出现p==q
			return p;
		}

		public static void main(String[] args) {
			int i = firstBadVersion(1);
			System.out.println(i);
		}
	}

	public static class VersionControl {

		/**
		 * 是否为正确的版本
		 *
		 * @param version
		 * @return
		 */
		public static boolean isBadVersion(int version) {
			return version > 0;
		}
	}

	/**
	 * 724. 寻找数组的中心下标
	 * 简单
	 * 相关标签
	 * 相关企业
	 * 提示
	 * 给你一个整数数组 nums ，请计算数组的 中心下标 。
	 * 数组 中心下标 是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和。
	 * 如果中心下标位于数组最左端，那么左侧数之和视为 0 ，因为在下标的左侧不存在元素。这一点对于中心下标位于数组最右端同样适用。
	 * 如果数组有多个中心下标，应该返回 最靠近左边 的那一个。如果数组不存在中心下标，返回 -1 。
	 * 示例 1：
	 * 输入：nums = [1, 7, 3, 6, 5, 6]
	 * 输出：3
	 * 解释：
	 * 中心下标是 3 。
	 * 左侧数之和 sum = nums[0] + nums[1] + nums[2] = 1 + 7 + 3 = 11 ，
	 * 右侧数之和 sum = nums[4] + nums[5] = 5 + 6 = 11 ，二者相等。
	 * 示例 2：
	 * 输入：nums = [1, 2, 3]
	 * 输出：-1
	 * 解释：
	 * 数组中不存在满足此条件的中心下标。
	 * 示例 3：
	 * 输入：nums = [2, 1, -1]
	 * 输出：0
	 * 解释：
	 * 中心下标是 0 。
	 * 左侧数之和 sum = 0 ，（下标 0 左侧不存在元素），
	 * 提示：
	 * 1 <= nums.length <= 104
	 * -1000 <= nums[i] <= 1000
	 */
	public static class PivotIndex {

		/**
		 * 从左往右寻找这个中间点
		 * 设数组的总元素和为total,左边元素和为left,右边元素和为right
		 * 那么 right= total-left-nums[i],要满足left=right 则得到2left=total-nums[i]
		 * 当i=0时,left=0;当i=nums[i].length-1时,right=0;
		 *
		 * @param nums
		 * @return
		 */
		public static int pivotIndex(int[] nums) {
			int total = 0;
			for (int i = 0; i < nums.length; ++i) {
				total += nums[i];
			}
			int left = 0;
			for (int i = 0; i < nums.length; i++) {
				if (i > 0) left += nums[i - 1];
				if (2 * left == total - nums[i]) return i;
			}
			return -1;
		}

		public static void main(String[] args) {
			int i = pivotIndex(new int[]{1, 7, 3, 6, 5, 6});
			System.out.println(i);
		}
	}

	/**
	 * 287. 寻找重复数
	 * 中等
	 * 相关标签
	 * 相关企业
	 * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
	 * 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
	 * 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
	 * 示例 1：
	 * 输入：nums = [1,3,4,2,2]
	 * 输出：2
	 * 示例 2：
	 * 输入：nums = [3,1,3,4,2]
	 * 输出：3
	 * 示例 3 :
	 * 输入：nums = [3,3,3,3,3]
	 * 输出：3
	 * 提示：
	 * 1 <= n <= 105
	 * nums.length == n + 1
	 * 1 <= nums[i] <= n
	 * nums 中 只有一个整数 出现 两次或多次 ，其余整数均只出现 一次
	 * 进阶：
	 * <p>
	 * 如何证明 nums 中至少存在一个重复的数字?
	 * 你可以设计一个线性级时间复杂度 O(n) 的解决方案吗？
	 */
	public static class FindDuplicate {

		/**
		 * 没有思路,只能查看官方思路
		 *
		 * @param nums
		 * @return
		 */
		public static int findDuplicate(int[] nums) {

			return 0;
		}
	}
}
