package test.binary;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.Random;

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

	/**
	 * 74. 搜索二维矩阵
	 * 给你一个满足下述两条属性的 m x n 整数矩阵：
	 * 每行中的整数从左到右按非严格递增顺序排列。
	 * 每行的第一个整数大于前一行的最后一个整数。
	 * 给你一个整数 target ，如果 target 在矩阵中，返回 true ；否则，返回 false 。
	 * 示例 1：
	 * 输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
	 * 输出：true
	 * 示例 2：
	 * 输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
	 * 输出：false
	 * 提示：
	 * m == matrix.length
	 * n == matrix[i].length
	 * 1 <= m, n <= 100
	 * -104 <= matrix[i][j], target <= 104
	 */
	static class SearchMatrix {

		/**
		 * 因为矩阵的每行和没列间都遵循单调递增的特性,所以可以使用二分查找
		 * 此处二分的关键点在于,如何通过二分找到的中间点pos,转换成矩阵的坐标
		 * 初始 s=0,e=m*n-1 pos=(s+e)/2  pos转换成矩阵的坐标
		 * x=pos/m y=pos%
		 */
		public static boolean searchMatrix(int[][] matrix, int target) {
			int m = matrix.length, n = matrix[0].length;
			int s = 0, e = m * n - 1;
			while (s <= e) {
				int pos = s + ((e - s) >> 1);
				int va = matrix[pos / n][pos % n];
				if (va > target) {
					e = pos - 1;
				} else if (va < target) {
					s = pos + 1;
				} else {
					return true;
				}
			}
			return false;
		}

		public static void main(String[] args) {
			// boolean b = searchMatrix(new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}}, 3);
			boolean b = searchMatrix(new int[][]{{1}, {1}}, 2);
			System.out.println(b);
		}
	}

	/**
	 * 162. 寻找峰值
	 * 峰值元素是指其值严格大于左右相邻值的元素。
	 * 给你一个整数数组 nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
	 * 你可以假设 nums[-1] = nums[n] = -∞ 。
	 * 你必须实现时间复杂度为 O(log n) 的算法来解决此问题。
	 * 示例 1：
	 * 输入：nums = [1,2,3,1]
	 * 输出：2
	 * 解释：3 是峰值元素，你的函数应该返回其索引 2。
	 * 示例 2：
	 * 输入：nums = [1,2,1,3,5,6,4]
	 * 输出：1 或 5
	 * 解释：你的函数可以返回索引 1，其峰值元素为 2；
	 * 或者返回索引 5， 其峰值元素为 6。
	 * 提示：
	 * 1 <= nums.length <= 1000
	 * -231 <= nums[i] <= 231 - 1
	 * 对于所有有效的 i 都有 nums[i] != nums[i + 1]
	 */
	static class FindPeakElement {

		/**
		 * 首先nums[-1] = nums[n] = -∞ ,加上数组中每个元素的值都不同,那么一定存在波峰;
		 * 1.可以遍历数组,找到最大的那个值,肯定为波峰,时间复杂度为O(n)需要完整的遍历一次数组
		 * 2.根据题意可以得知,如果i->i+1 值在递增,则一定会在后面找到一个波峰;因为两端都是波谷
		 * 3.随机找到一个点,看起左右两边,哪边是走上坡的,走上坡的那一边一定会存在波峰
		 *
		 * @param nums
		 * @return
		 */
		public static int findPeakElement(int[] nums) {
			int idx = 0, max = nums[0];
			for (int i = 1; i < nums.length; ++i) {
				if (nums[i] > max) {
					idx = i;
					max = nums[i];
				}
			}
			return idx;
		}

		/**
		 * 时间复杂度O(n)当随机到0且向右移动时,则需要遍历n次
		 *
		 * @param nums
		 * @return
		 */
		public static int findPeakElementI(int[] nums) {
			if (nums.length == 1) return 0;
			if (nums.length == 2) {
				if (nums[0] > nums[1]) return 0;
				else return 1;
			}
			Random r = new Random();
			int idx = r.nextInt(nums.length - 2) + 1; // 随机数取值时排除了第一个和最后一个元素,所以得处理长度为1和2的情况
			if (nums[idx] < nums[idx + 1]) {  // 向左
				int i = idx + 1;
				while (i < nums.length - 1 && nums[i] < nums[i + 1]) {
					i++;
				}
				return i;
			} else if (nums[idx] > nums[idx + 1] && nums[idx] > nums[idx - 1]) {
				return idx;
			} else { // 向左
				int i = idx - 1;
				while (i > 0 && nums[i] < nums[i - 1]) {
					i--;
				}
				return i;
			}
		}

		public static int findPeakElementII(int[] nums) {
			Random r = new Random();
			int s = 0, e = nums.length - 1, pos;
			while (s <= e) {
				pos = r.nextInt(e - s + 1) + s;
				int next = pos + 1 == nums.length ? Integer.MIN_VALUE : nums[pos + 1];
				int pre = pos - 1 == -1 ? Integer.MIN_VALUE : nums[pos - 1];
				if (nums[pos] < pre) {  // 从左边取值
					e = pos - 1;
				} else if (nums[pos] < next) { // 从右边取值
					s = pos + 1;
				} else {
					return pos;
				}
			}
			return nums[0];
		}

		public static void main(String[] args) {
			System.out.println(findPeakElementI(new int[]{2, 1}));
		}
	}

	/**
	 * 33. 搜索旋转排序数组
	 * 整数数组 nums 按升序排列，数组中的值 互不相同 。
	 * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
	 * 给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
	 * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
	 * 示例 1：
	 * 输入：nums = [4,5,6,7,0,1,2], target = 0
	 * 输出：4
	 * 示例 2：
	 * 输入：nums = [4,5,6,7,0,1,2], target = 3
	 * 输出：-1
	 * 示例 3：
	 * 输入：nums = [1], target = 0
	 * 输出：-1
	 * 提示：
	 * 1 <= nums.length <= 5000
	 * -104 <= nums[i] <= 104
	 * nums 中的每个值都 独一无二
	 * 题目数据保证 nums 在预先未知的某个下标上进行了旋转
	 * -104 <= target <= 104
	 */
	static class Search {

		/**
		 * 在某处旋转的升序数组有什么特性呢?
		 * 1.中间存在最大值,最大值和最小值相接
		 * 2.前部分升序数列都大于第一个数first;后部分升序数列都小于最后一个数end
		 * 那么在使用二分时,设置当前值为cur,前个值为pre,后个值为next
		 * 可以通过比较cur与first和end的大小来判断当前元素处在哪个升序数列中
		 * a:如果target > first答案会在第一个升序数列中 b:target < first答案会在第二个升序队列
		 * 1.首先看cur所在的升序队列是否正确,其次比较cur和target的大小
		 * 2.cur < target 往右缩小范围
		 * 3.cur > target 往左缩小范围
		 *
		 * @param nums
		 * @param target
		 * @return
		 */
		public static int search(int[] nums, int target) {
			int first = nums[0];
			boolean isBefore = target >= first;
			int s = 0, e = nums.length - 1, cur;
			while (s <= e) {
				cur = s + ((e - s) >> 1);
				if (nums[cur] == target) {
					return cur;
				}
				if ((nums[cur] >= first) == isBefore) {  // cur在正确的升序数列
					if (nums[cur] > target) { // 向左
						e = cur - 1;
					} else {   // 向右
						s = cur + 1;
					}
				} else {
					if (nums[cur] > target) {  // 向右
						s = cur + 1;
					} else {
						e = cur - 1;
					}
				}
			}
			return -1;
		}

		/**
		 * 当然更简单的思路是先找到旋转点,判断target在前后哪个升序数列中,然后二分查找
		 * 找旋转点也需要使用二分,O(nLog n)
		 *
		 * @param nums
		 * @param target
		 * @return
		 */
		public static int searchI(int[] nums, int target) {
			int lo = 0, hi = nums.length - 1, mid = 0;
			while (lo <= hi) {
				mid = lo + (hi - lo) / 2;
				if (nums[mid] == target) {
					return mid;
				}
				// 先根据 nums[mid] 与 nums[lo] 的关系判断 mid 是在左段还是右段
				if (nums[mid] >= nums[lo]) {
					// 再判断 target 是在 mid 的左边还是右边，从而调整左右边界 lo 和 hi
					if (target >= nums[lo] && target < nums[mid]) {
						hi = mid - 1;
					} else {
						lo = mid + 1;
					}
				} else {
					if (target > nums[mid] && target <= nums[hi]) {
						lo = mid + 1;
					} else {
						hi = mid - 1;
					}
				}
			}
			return -1;
		}

		public static void main(String[] args) {
			int search = searchI(new int[]{3, 1}, 1);
			System.out.println(search);
		}
	}

	/**
	 * 153. 寻找旋转排序数组中的最小值
	 * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到：
	 * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
	 * 若旋转 7 次，则可以得到 [0,1,2,4,5,6,7]
	 * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
	 * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。
	 * 你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
	 * 示例 1：
	 * 输入：nums = [3,4,5,1,2]
	 * 输出：1
	 * 解释：原数组为 [1,2,3,4,5] ，旋转 3 次得到输入数组。
	 * 示例 2：
	 * 输入：nums = [4,5,6,7,0,1,2]
	 * 输出：0
	 * 解释：原数组为 [0,1,2,4,5,6,7] ，旋转 3 次得到输入数组。
	 * 示例 3：
	 * 输入：nums = [11,13,15,17]
	 * 输出：11
	 * 解释：原数组为 [11,13,15,17] ，旋转 4 次得到输入数组。
	 * 提示：
	 * n == nums.length
	 * 1 <= n <= 5000
	 * -5000 <= nums[i] <= 5000
	 * nums 中的所有整数 互不相同
	 * nums 原来是一个升序排序的数组，并进行了 1 至 n 次旋转
	 */
	static class FindMin {

		/**
		 * 旋转n次的数组,其最后会出现整体升序,或者两次升序的情况
		 * 1.当first > last 出现两次升序
		 * 2.当first < last 整体升序 最小为first
		 * 3.出现两次升序的情况,需要找最小;即要知道cur < prev && cur < next
		 * 如果next > cur > prev 且cur在左边升序序列,则范围向右移动;cur在右边升序序列,则范围向左移动
		 *
		 * @param nums
		 * @return
		 */
		public static int findMin(int[] nums) {
			int first = nums[0], last = nums[nums.length - 1];
			if (first < last) { // 整体升序
				return first;
			}
			int s = 0, e = nums.length - 1;
			while (s <= e) {
				int idx = s + ((e - s) >> 1);
				int cur = nums[idx], prev = idx - 1 >= 0 ? nums[idx - 1] : Integer.MAX_VALUE, next = idx + 1 < nums.length ? nums[idx + 1] : Integer.MAX_VALUE;
				if (cur < prev && cur < next) {
					return cur;
				} else if (cur >= first) {
					s = idx + 1;
				} else {
					e = idx - 1;
				}
			}
			return -1;
		}

		/**
		 * 当前cur 大于其范围区间的high 就向右缩小范围区间
		 * 当前cur 小于其范围区间的high 就向左缩小范围区间
		 *
		 * @param nums
		 * @return
		 */
		public static int findMinI(int[] nums) {
			int low = 0, high = nums.length - 1;
			// 由于nums中的元素都不相同,只要区间长度不为1,pivot就不会与high重合;而如果区间长度为1,则说明已经找到了最小值
			while (low < high) {
				int pivot = low + (high - low) / 2;
				if (nums[pivot] < nums[high]) {
					high = pivot;
				} else {
					low = pivot + 1;
				}
			}
			return nums[low];
		}

		public static void main(String[] args) {
			int min = findMinI(new int[]{2,1});
			System.out.println(min);
		}
	}

	/**
	 * 374. 猜数字大小
	 * 我们正在玩猜数字游戏。猜数字游戏的规则如下：
	 * 我会从 1 到 n 随机选择一个数字。 请你猜选出的是哪个数字。
	 * 如果你猜错了，我会告诉你，我选出的数字比你猜测的数字大了还是小了。
	 * 你可以通过调用一个预先定义好的接口 int guess(int num) 来获取猜测结果，返回值一共有三种可能的情况：
	 * -1：你猜的数字比我选出的数字大 （即 num > pick）。
	 * 1：你猜的数字比我选出的数字小 （即 num < pick）。
	 * 0：你猜的数字与我选出的数字相等。（即 num == pick）。
	 * 返回我选出的数字。
	 * 示例 1：
	 * 输入：n = 10, pick = 6
	 * 输出：6
	 * 示例 2：
	 * 输入：n = 1, pick = 1
	 * 输出：1
	 * 示例 3：
	 * 输入：n = 2, pick = 1
	 * 输出：1
	 * 提示：
	 * 1 <= n <= 231 - 1
	 * 1 <= pick <= n
	 */
	static class GuessGame {

		/**
		 * 思路:
		 * 经典二分的思路
		 *
		 * @param n
		 * @return
		 */
		public static int guessNumber(int n) {
			int s = 1, e = n; // 范围
			while (s <= e) {
				int pos = s + ((e - s) >> 1);
				int g = guess(pos);
				if (g == -1) { // 缩小范围
					e = pos - 1;
				} else if (g == 1) {
					s = pos + 1;
				} else return pos;
			}
			return 0;
		}

		public static int guessNumberII(int n) {
			int s = 1, e = n; // 范围
			while (s < e) {
				int pos = s + ((e - s) >> 1);
				int g = guess(pos);
				if (g <= 0) { // 那么答案在 [s,pos]之间
					e = pos;
				} else {
					s = pos + 1; // 答案在[pos+1,s]之间
				}
			}
			// 当s==e时,区间缩为一个点,即是答案
			return s;
		}

		private static final int v = 6;

		private static int guess(int num) {
			if (num > v) {
				return -1;
			} else if (num < v) {
				return 1;
			} else {
				return 0;
			}
		}

		public static void main(String[] args) {
			int i1 = guessNumberII(10);
			System.out.println(i1);
		}
	}

	/**
	 * 2300. 咒语和药水的成功对数
	 * 给你两个正整数数组 spells 和 potions ，长度分别为 n 和 m ，其中 spells[i] 表示第 i 个咒语的能量强度，potions[j] 表示第 j 瓶药水的能量强度。
	 * 同时给你一个整数 success 。一个咒语和药水的能量强度 相乘 如果 大于等于 success ，那么它们视为一对 成功 的组合。
	 * 请你返回一个长度为 n 的整数数组 pairs，其中 pairs[i] 是能跟第 i 个咒语成功组合的 药水 数目。
	 * 示例 1：
	 * 输入：spells = [5,1,3], potions = [1,2,3,4,5], success = 7
	 * 输出：[4,0,3]
	 * 解释：
	 * - 第 0 个咒语：5 * [1,2,3,4,5] = [5,10,15,20,25] 。总共 4 个成功组合。
	 * - 第 1 个咒语：1 * [1,2,3,4,5] = [1,2,3,4,5] 。总共 0 个成功组合。
	 * - 第 2 个咒语：3 * [1,2,3,4,5] = [3,6,9,12,15] 。总共 3 个成功组合。
	 * 所以返回 [4,0,3] 。
	 * 示例 2：
	 * 输入：spells = [3,1,2], potions = [8,5,8], success = 16
	 * 输出：[2,0,2]
	 * 解释：
	 * - 第 0 个咒语：3 * [8,5,8] = [24,15,24] 。总共 2 个成功组合。
	 * - 第 1 个咒语：1 * [8,5,8] = [8,5,8] 。总共 0 个成功组合。
	 * - 第 2 个咒语：2 * [8,5,8] = [16,10,16] 。总共 2 个成功组合。
	 * 所以返回 [2,0,2] 。
	 * 提示：
	 * n == spells.length
	 * m == potions.length
	 * 1 <= n, m <= 105
	 * 1 <= spells[i], potions[i] <= 105
	 * 1 <= success <= 1010
	 */
	static class SuccessfulPairs {

		/**
		 * 思路：
		 * 本题的题意是,从spells数组中,每次拿出1个数,然后与potions中的每个数计算其乘积,然后统计满足条件的个数
		 * 实际上可以先对potions数组进行排序,然后找到第一个满足条件的元素的序列,后面剩下的数即为需要统计的个数
		 * 那么怎么找到第一个满足条件的序列呢?通过二分的范围缩小,来找到分界点
		 *
		 * @param spells
		 * @param potions
		 * @param success
		 * @return
		 */
		public static int[] successfulPairs(int[] spells, int[] potions, long success) {
			int n = spells.length, m = potions.length;
			int[] res = new int[n];
			Arrays.sort(potions);
			for (int i = 0; i < n; i++) {
				// 判断是否所有都满足,或者一个都不满足
				int s = 0, e = m - 1, v = spells[i];
				if ((long) v * potions[0] >= success) { // 所有都满足
					res[i] = m;
				} else if ((long) v * potions[m - 1] < success) { // 所有都不满足
					res[i] = 0;
				} else {
					while (s < e) {
						int p = s + ((e - s) >> 1);
						if ((long) potions[p] * v >= success) {  // 范围在[s,p]
							e = p;
						} else {  // 范围在[p+1,e]
							s = p + 1;
						}
					}
					res[i] = m - s;
				}
			}
			return res;
		}

		public static void main(String[] args) {
			int[] ints = successfulPairs(new int[]{3, 1, 2}, new int[]{8, 5, 8}, 16);
			System.out.println(Arrays.toString(ints));
		}
	}

	/**
	 * 875. 爱吃香蕉的珂珂
	 * 珂珂喜欢吃香蕉。这里有 n 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 h 小时后回来。
	 * 珂珂可以决定她吃香蕉的速度 k （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 k 根。如果这堆香蕉少于 k 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉
	 * 珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
	 * 返回她可以在 h 小时内吃掉所有香蕉的最小速度 k（k 为整数）。
	 * 示例 1：
	 * 输入：piles = [3,6,7,11], h = 8
	 * 输出：4
	 * 示例 2：
	 * 输入：piles = [30,11,23,4,20], h = 5
	 * 输出：30
	 * 示例 3：
	 * 输入：piles = [30,11,23,4,20], h = 6
	 * 输出：23
	 * 提示：
	 * 1 <= piles.length <= 104
	 * piles.length <= h <= 109
	 * 1 <= piles[i] <= 109
	 */
	static class MinEatingSpeed {

		/**
		 * 思路:
		 * 根据题意,需要找到合理的速度K(k/h),使得能够在规定时间内吃掉所有的香蕉
		 * 重点是,香蕉是按堆放的,1小时内吃掉一堆后将不在吃下一堆
		 * 可以先判断这个速度最大值,例如香蕉堆为[3,6,7,11] 已排序,那么最大的那一堆分成2次吃,做多吃8h,符合条件
		 * MAX(K)= max(piles)/(h/n) +1; MIN(K)=min(piles)/h/n)+1;
		 *
		 * @param piles
		 * @param h
		 * @return
		 */
		public static int minEatingSpeed(int[] piles, int h) {
			Arrays.sort(piles);
			int n = piles.length, left = piles[0] / (h / n), right = piles[n - 1] / (h / n) + 1;
			left = left == 0 ? 1 : left;
			while (left < right) {
				int mid = left + ((right - left) >> 1);
				// if (canFinish(piles, mid, n, h)) {  // 能完成,则范围为[left,mid]
				if (getTime(piles, mid) <= h) {
					right = mid;
				} else {                            // 不能完成,则范围为[mid+1,right]
					left = mid + 1;
				}
			}
			return left;
		}

		private static boolean canFinish(int[] piles, int k, int n, int h) {
			int total = 0;
			for (int i = 0; i < n; i++) {
				// 如果本堆香蕉数量小于速度k,那么耗时为1,如果数量大于k,则分段计算
				if (piles[i] < k) total += 1;
				else total += piles[i] / k + (piles[i] % k == 0 ? 0 : 1);
			}
			return total <= h;
		}

		public static int minEatingSpeedII(int[] piles, int h) {
			int low = 1, high = 0;
			for (int pile : piles) {
				high = Math.max(high, pile);
				low = Math.min(low, pile);
			}
			int n = piles.length;
			low = low / (h / n);
			high = high / (h / n) + 1;
			low = low == 0 ? 1 : low;
			while (low < high) {
				int mid = low + ((high - low) >> 1);
				if (getTime(piles, mid) <= h) {
					high = mid;
				} else {                            // 不能完成,则范围为[mid+1,right]
					low = mid + 1;
				}
			}
			return low;
		}

		private static long getTime(int[] piles, int k) {
			long time = 0;
			for (int pile : piles) {
				time += (pile + k - 1) / k;
			}
			return time;
		}

		public static void main(String[] args) {
			System.out.println(minEatingSpeedII(new int[]{3,6,7,11}, 8));
		}
	}
}
