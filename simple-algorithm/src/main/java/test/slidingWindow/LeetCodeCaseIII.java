package test.slidingWindow;

import java.util.*;

/**
 * 不定长滑动窗口-求最小/最大
 */
public class LeetCodeCaseIII {

	/**
	 * 209. 长度最小的子数组
	 * 给定一个含有 n 个正整数的数组和一个正整数 target 。
	 * 找出该数组中满足其总和大于等于 target 的长度最小的 子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0
	 * 示例 1：
	 * 输入：target = 7, nums = [2,3,1,2,4,3]
	 * 输出：2
	 * 解释：子数组 [4,3] 是该条件下的长度最小的子数组。
	 * 示例 2：
	 * 输入：target = 4, nums = [1,4,4]
	 * 输出：1
	 * 示例 3：
	 * 输入：target = 11, nums = [1,1,1,1,1,1,1,1]
	 * 输出：0
	 * 提示：
	 * 1 <= target <= 109
	 * 1 <= nums.length <= 105
	 * 1 <= nums[i] <= 104
	 * 进阶：
	 * 如果你已经实现 O(n) 时间复杂度的解法, 请尝试设计一个 O(n log(n)) 时间复杂度的解法。
	 */
	static class MinSubArrayLen {

		/**
		 * 思路:
		 * 窗口左边界移动的条件:窗口内元素和>target
		 * 窗口右边界移动的条件:窗口内元素和<target
		 * 当窗口内元素和>= target 记录窗口内元素的个数
		 *
		 * @param target
		 * @param nums
		 * @return
		 */
		public static int minSubArrayLen(int target, int[] nums) {
			int n = nums.length, l = 0, min = Integer.MAX_VALUE, sum = 0;
			if (n == 0) return 0;
			for (int r = 0; r < n; r++) {
				sum += nums[r];
				while (l < n && sum >= target) {
					min = Math.min(min, r - l + 1);  // 关键点是,sum>=target就是满足条件的情况,需要统计到结果中
					sum -= nums[l];
					l++;
				}
			}
			return min == Integer.MAX_VALUE ? 0 : min;
		}

		public static void main(String[] args) {
			int i = minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3});
			System.out.println(i);
		}
	}

	/**
	 * 2904. 最短且字典序最小的美丽子字符串
	 * 给你一个二进制字符串 s 和一个正整数 k 。
	 * 如果 s 的某个子字符串中 1 的个数恰好等于 k ，则称这个子字符串是一个 美丽子字符串 。
	 * 令 len 等于 最短 美丽子字符串的长度。
	 * 返回长度等于 len 且字典序 最小 的美丽子字符串。如果 s 中不含美丽子字符串，则返回一个 空 字符串。
	 * 对于相同长度的两个字符串 a 和 b ，如果在 a 和 b 出现不同的第一个位置上，a 中该位置上的字符严格大于 b 中的对应字符，则认为字符串 a 字典序 大于 字符串 b 。
	 * 例如，"abcd" 的字典序大于 "abcc" ，因为两个字符串出现不同的第一个位置对应第四个字符，而 d 大于 c 。
	 * 示例 1：
	 * 输入：s = "100011001", k = 3
	 * 输出："11001"
	 * 解释：示例中共有 7 个美丽子字符串：
	 * 1. 子字符串 "100011001" 。
	 * 2. 子字符串 "100011001" 。
	 * 3. 子字符串 "100011001" 。
	 * 4. 子字符串 "100011001" 。
	 * 5. 子字符串 "100011001" 。
	 * 6. 子字符串 "100011001" 。
	 * 7. 子字符串 "100011001" 。
	 * 最短美丽子字符串的长度是 5 。
	 * 长度为 5 且字典序最小的美丽子字符串是子字符串 "11001" 。
	 * 示例 2：
	 * 输入：s = "1011", k = 2
	 * 输出："11"
	 * 解释：示例中共有 3 个美丽子字符串：
	 * 1. 子字符串 "1011" 。
	 * 2. 子字符串 "1011" 。
	 * 3. 子字符串 "1011" 。
	 * 最短美丽子字符串的长度是 2 。
	 * 长度为 2 且字典序最小的美丽子字符串是子字符串 "11" 。
	 * 示例 3：
	 * 输入：s = "000", k = 1
	 * 输出：""
	 * 解释：示例中不存在美丽子字符串。
	 * 提示：
	 * 1 <= s.length <= 100
	 * 1 <= k <= s.length
	 */
	static class ShortestBeautifulSubstring {

		/**
		 * 思路:
		 * 不定长滑动窗口,r正常右移,同时当1的个数>k时l右移,每次寻找符合1个数==k的子数组,然后比较其长度,长度相同时比较其字典序
		 *
		 * @param s
		 * @param k
		 * @return
		 */
		public static String shortestBeautifulSubstring(String s, int k) {
			int n = s.length(), min = Integer.MAX_VALUE, l = 0, cnt = 0;  //min的初始化为字符串长度,引发问题...
			String res = "";
			for (int r = 0; r < n; r++) {
				if (s.charAt(r) == '1') {
					cnt++;
				}
				while (l < n && cnt >= k) {
					if (cnt == k) {
						if (min == r - l + 1) {  // 长度相等比较字典序
							String str = s.substring(l, r + 1);
							if (compare(str, res)) {
								res = str;
							}
						} else if (min > r - l + 1) {  // 长度更小直接更新
							String str = s.substring(l, r + 1);
							min = r - l + 1;
							res = str;
						}
					}
					if (s.charAt(l) == '1') {
						cnt--;
					}
					l++;
				}
			}
			return res;
		}

		/**
		 * 二进制字符串转十进制数,细节处理要仔细
		 * 二进制转十进制,会出现超多int最大范围的问题,需要换思路
		 *
		 * @param str
		 * @return
		 */
		private static int binaryString(String str) {
			int res = 0, bit = 1;
			for (int i = str.length() - 1; i >= 0; i--) {
				if (str.charAt(i) == '1') {
					res += bit;
				}
				bit *= 2;
			}
			return res;
		}

		/**
		 * 字符串比较字典序大小 str1 < str2 返回true
		 * 细节问题太多,没有注意到
		 *
		 * @param str1
		 * @param str2
		 * @return
		 */
		private static boolean compare(String str1, String str2) {
			for (int i = 0; i < str1.length(); i++) {
				int v;
				if ((v = (str1.charAt(i) - '0') - (str2.charAt(i) - '0')) != 0) {
					return v < 0;
				}
			}
			return false;
		}

		public static void main(String[] args) {
			System.out.println(shortestBeautifulSubstring("001", 1));
		}
	}


	/**
	 * 713. 乘积小于 K 的子数组
	 * 给你一个整数数组 nums 和一个整数 k ，请你返回子数组内所有元素的乘积严格小于 k 的连续子数组的数目。
	 * <p>
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：nums = [10,5,2,6], k = 100
	 * 输出：8
	 * 解释：8 个乘积小于 100 的子数组分别为：[10]、[5]、[2]、[6]、[10,5]、[5,2]、[2,6]、[5,2,6]。
	 * 需要注意的是 [10,5,2] 并不是乘积小于 100 的子数组。
	 * 示例 2：
	 * <p>
	 * 输入：nums = [1,2,3], k = 0
	 * 输出：0
	 * <p>
	 * <p>
	 * 提示:
	 * <p>
	 * 1 <= nums.length <= 3 * 104
	 * 1 <= nums[i] <= 1000
	 * 0 <= k <= 106
	 */
	static class NumSubarrayProductLessThanK {

		/**
		 * 本题的关键点在于
		 * 1.构建出满足情况的子数组[left,right]
		 * 2.每次找到满足情况的子数组后,记录新增的子数组个数
		 * 比如[1,2,3] 此时right右移以为变成 [1,2,3,4] 那么新增的满足情况的子数组为: [1,2,3,4],[2,3,4],[3,4],[4]
		 * 实际上就是这个子数组元素的个数num =right-left+1
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static int numSubarrayProductLessThanK(int[] nums, int k) {
			if (k <= 1) return 0;
			int left = 0, mul = 1, count = 0;
			for (int right = 0; right < nums.length; right++) {
				mul *= nums[right];
				while (mul >= k && left <= right) {  // left不能大于right
					mul /= nums[left];
					left++;
				}
				count += right - left + 1;
			}
			return count;
		}


		public static int numSubarrayProductLessThanKII(int[] nums, int k) {
			//同样排除k为1的情况比如  [1,1,1] k=1
			if (k <= 1) {
				return 0;
			}
			int left = 0;
			int right = 0;
			//创建一个变量记录路上的乘积
			int mul = 1;
			//记录连续数组的组合个数
			int ans = 0;

			//用右指针遍历整个数组，每次循环右指针右移一次
			while(right<nums.length) {
				//记录乘积
				mul *= nums[right];
				//当大于等于k，左指针右移并把之前左指针的数除掉
				while (mul >= k) {
					mul /= nums[left];
					left++;
				}
				//每次右指针位移到一个新位置，应该加上 x 种数组组合：
				ans += right - left + 1;

				//右指针右移
				right++;
			}
			return ans;
		}

		public static void main(String[] args) {
			System.out.println(numSubarrayProductLessThanKII(new int[]{1, 2, 3}, 0));
		}
	}

	/**
	 * 3258. 统计满足 K 约束的子字符串数量 I
	 * 给你一个 二进制 字符串 s 和一个整数 k。
	 * 如果一个 二进制字符串 满足以下任一条件，则认为该字符串满足 k 约束：
	 * 字符串中 0 的数量最多为 k。
	 * 字符串中 1 的数量最多为 k。
	 * 返回一个整数，表示 s 的所有满足 k 约束 的子字符串的数量。
	 * 示例 1：
	 * 输入：s = "10101", k = 1
	 * 输出：12
	 * 解释：
	 * s 的所有子字符串中，除了 "1010"、"10101" 和 "0101" 外，其余子字符串都满足 k 约束。
	 * 示例 2：
	 * 输入：s = "1010101", k = 2
	 * 输出：25
	 * 解释：
	 * s 的所有子字符串中，除了长度大于 5 的子字符串外，其余子字符串都满足 k 约束。
	 * 示例 3：
	 * 输入：s = "11111", k = 1
	 * 输出：15
	 * 解释：
	 * s 的所有子字符串都满足 k 约束。
	 * 提示：
	 * <p>
	 * 1 <= s.length <= 50
	 * 1 <= k <= s.length
	 * s[i] 是 '0' 或 '1'。
	 */
	static class CountKConstraintSubstrings {

		/**
		 * 所有子数组,最简单的方式就是暴力遍历
		 *
		 * @param s
		 * @param k
		 * @return
		 */
		public static int countKConstraintSubstrings(String s, int k) {
			int l = s.length(), res = 0;
			for (int i = 0; i < l; i++) {
				int c0 = 0, c1 = 0;
				for (int j = i; j < l; j++) {
					if (s.charAt(j) == '0') {
						c0++;
					} else {
						c1++;
					}
					if (c0 <= k || c1 <= k) {
						res++;
					}
				}
			}
			return res;
		}

		/**
		 * 不定长滑动窗口,以right指定移动,来累加新增的子数组
		 *
		 * @param s
		 * @param k
		 * @return
		 */
		public static int countKConstraintSubstringsSlidingWindow(String s, int k) {
			int l = s.length(), left = 0, right = 0, c0 = 0, c1 = 0, res = 0;
			for (; right < l; right++) {
				if (s.charAt(right) == '0') {
					c0++;
				} else {
					c1++;
				}
				while (left <= right && c0 > k && c1 > k) {
					if (s.charAt(left) == '0') {
						c0--;
					} else {
						c1--;
					}
					left++;
				}
				res += right - left + 1;
			}
			return res;
		}

		public static void main(String[] args) {
			System.out.println(countKConstraintSubstrings("10101", 1));
			System.out.println(countKConstraintSubstringsSlidingWindow("10101", 1));
		}
	}

	/**
	 * 2302. 统计得分小于 K 的子数组数目
	 * 一个数组的 分数 定义为数组之和 乘以 数组的长度。
	 * 比方说，[1, 2, 3, 4, 5] 的分数为 (1 + 2 + 3 + 4 + 5) * 5 = 75 。
	 * 给你一个正整数数组 nums 和一个整数 k ，请你返回 nums 中分数 严格小于 k 的 非空整数子数组数目。
	 * 子数组 是数组中的一个连续元素序列。
	 * 示例 1：
	 * 输入：nums = [2,1,4,3,5], k = 10
	 * 输出：6
	 * 解释：
	 * 有 6 个子数组的分数小于 10 ：
	 * - [2] 分数为 2 * 1 = 2 。
	 * - [1] 分数为 1 * 1 = 1 。
	 * - [4] 分数为 4 * 1 = 4 。
	 * - [3] 分数为 3 * 1 = 3 。
	 * - [5] 分数为 5 * 1 = 5 。
	 * - [2,1] 分数为 (2 + 1) * 2 = 6 。
	 * 注意，子数组 [1,4] 和 [4,3,5] 不符合要求，因为它们的分数分别为 10 和 36，但我们要求子数组的分数严格小于 10 。
	 * 示例 2：
	 * 输入：nums = [1,1,1], k = 5
	 * 输出：5
	 * 解释：
	 * 除了 [1,1,1] 以外每个子数组分数都小于 5 。
	 * [1,1,1] 分数为 (1 + 1 + 1) * 3 = 9 ，大于 5 。
	 * 所以总共有 5 个子数组得分小于 5 。
	 * 提示：
	 * 1 <= nums.length <= 105
	 * 1 <= nums[i] <= 105
	 * 1 <= k <= 1015
	 */
	static class CountSubarrays {

		/**
		 * 思路:
		 * 不定长滑动窗口,以right指定移动,来累加新增的子数组
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static long countSubarrays(int[] nums, long k) {
			int l = nums.length, left = 0, right = 0;
			long sum = 0, res = 0;
			for (; right < l; right++) {
				sum += nums[right];
				while (sum * (right - left + 1) >= k) {
					sum -= nums[left];
					left++;
				}
				res += right - left + 1;
			}
			return res;
		}

		public static long countSubarraysII(int[] nums, long k) {
			int l = nums.length, left = 0, right = 0;
			long sum = 0, res = 0;
			for (; right < l; right++) {
				sum += nums[right];
				while (left <= right && sum * (right - left + 1) >= k) {
					sum -= nums[left];
					left++;
				}
				if (left <= right) res += right - left + 1;
			}
			return res;
		}

		public static void main(String[] args) {
			// System.out.println(countSubarrays(new int[]{2, 1, 4, 3, 5}, 10));
			// System.out.println(countSubarrays(new int[]{1, 1, 1}, 5));
			System.out.println(countSubarrays(new int[]{9, 5, 3, 8, 4, 7, 2, 7, 4, 5, 4, 9, 1, 4, 8, 10, 8, 10, 4, 7}, 4));

		}
	}

	/**
	 * 2762. 不间断子数组
	 * 给你一个下标从 0 开始的整数数组 nums 。nums 的一个子数组如果满足以下条件，那么它是 不间断 的：
	 * i，i + 1 ，...，j  表示子数组中的下标。对于所有满足 i <= i1, i2 <= j 的下标对，都有 0 <= |nums[i1] - nums[i2]| <= 2 。
	 * 请你返回 不间断 子数组的总数目。
	 * 子数组是一个数组中一段连续 非空 的元素序列。
	 * 示例 1：
	 * 输入：nums = [5,4,2,4]
	 * 输出：8
	 * 解释：
	 * 大小为 1 的不间断子数组：[5], [4], [2], [4] 。
	 * 大小为 2 的不间断子数组：[5,4], [4,2], [2,4] 。
	 * 大小为 3 的不间断子数组：[4,2,4] 。
	 * 没有大小为 4 的不间断子数组。
	 * 不间断子数组的总数目为 4 + 3 + 1 = 8 。
	 * 除了这些以外，没有别的不间断子数组。
	 * 示例 2：
	 * 输入：nums = [1,2,3]
	 * 输出：6
	 * 解释：
	 * 大小为 1 的不间断子数组：[1], [2], [3] 。
	 * 大小为 2 的不间断子数组：[1,2], [2,3] 。
	 * 大小为 3 的不间断子数组：[1,2,3] 。
	 * 不间断子数组的总数目为 3 + 2 + 1 = 6 。
	 * 提示：
	 * 1 <= nums.length <= 105
	 * 1 <= nums[i] <= 109
	 */
	static class ContinuousSubarrays {

		/**
		 * 思路:
		 * 不定长滑动窗口,不间断子数据的判断;是否可以用子数组中元素的最大值和最小值,来算出其是否都满足不间断呢?
		 * max-min <=2
		 * 关键在于,要维护一个数据结构,能够在新增/删除元素时,还能随时拿到数据中的最大和最小值,且元素可以重复
		 *
		 * @param nums
		 * @return
		 */
		public static long continuousSubarrays(int[] nums) {
			int l = nums.length, max = 0, min = Integer.MAX_VALUE, left = 0;
			long res = 0;
			TreeMap<Integer, Integer> map = new TreeMap<>();
			for (int right = 0; right < l; right++) {
				map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);
				if (max < nums[right]) max = nums[right];
				if (min > nums[right]) min = nums[right];
				while (left <= right && max - min > 2) {
					// 当left=right,窗口中只剩一个元素时,一定满足条件,且max和min都为这个元素的值
					// 此时因为不能进入while循环,因为max-min=0,但是其他题,是可能在窗口只有一个元素时,
					// 进入到while循环的,即下一步收缩到窗口没有元素,这种场景是需要考虑的.
					int delete = map.get(nums[left]);
					if (delete == 1) {
						map.remove(nums[left]);
					} else {
						map.put(nums[left], delete - 1);
					}
					if (map.isEmpty()) {
						max = 0;
						min = 0;
					} else {
						max = map.lastKey();
						min = map.firstKey();
					}
					left++;
				}
				res += right - left + 1;
			}
			return res;
		}

		public static long continuousSubarraysII(int[] nums) {
			int l = nums.length, max = 0, min = Integer.MAX_VALUE, left = 0;
			long res = 0;
			TreeMap<Integer, Integer> map = new TreeMap<>();
			for (int right = 0; right < l; right++) {
				map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);
				if (max < nums[right]) max = nums[right];
				if (min > nums[right]) min = nums[right];
				while (left <= right && max - min > 2) {
					// 当left=right,窗口中只剩一个元素时,一定满足条件,且max和min都为这个元素的值
					// 此时因为不能进入while循环,因为max-min=0,但是其他题,是可能在窗口只有一个元素时,
					// 进入到while循环的,即下一步收缩到窗口没有元素,这种场景是需要考虑的.
					int delete = map.get(nums[left]);
					if (delete == 1) {
						map.remove(nums[left]);
					} else {
						map.put(nums[left], delete - 1);
					}
					max = map.lastKey();
					min = map.firstKey();
					left++;
				}
				res += right - left + 1;
			}
			return res;
		}

		public static long continuousSubarraysIII(int[] nums) {
			int l = nums.length, left = 0;
			long res = 0;
			TreeMap<Integer, Integer> map = new TreeMap<>();
			for (int right = 0; right < l; right++) {
				map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);
				while (left <= right && map.lastKey() - map.firstKey() > 2) {
					// 当left=right,窗口中只剩一个元素时,一定满足条件,且max和min都为这个元素的值
					// 此时因为不能进入while循环,因为max-min=0,但是其他题,是可能在窗口只有一个元素时,
					// 进入到while循环的,即下一步收缩到窗口没有元素,这种场景是需要考虑的.
					int delete = map.get(nums[left]);
					if (delete == 1) {
						map.remove(nums[left]);
					} else {
						map.put(nums[left], delete - 1);
					}
					left++;
				}
				res += right - left + 1;
			}
			return res;
		}

		/**
		 * 维护两个单调队列,分别存放窗口中最大元素和最小元素的下标值
		 *
		 * @param nums
		 * @return
		 */
		public static long continuousSubarraysIV(int[] nums) {
			int l = nums.length, left = 0;
			long res = 0;
			Deque<Integer> minDeque = new ArrayDeque<>();
			Deque<Integer> maxDeque = new ArrayDeque<>();
			for (int right = 0; right < l; right++) {
				int r = nums[right];
				while (!minDeque.isEmpty() && r <= nums[minDeque.peekLast()]) {
					minDeque.pollLast();
				}
				minDeque.addLast(right);

				while (!maxDeque.isEmpty() && r >= nums[maxDeque.peekLast()]) {
					maxDeque.pollLast();
				}
				maxDeque.addLast(right);

				while (nums[maxDeque.peekFirst()] - nums[minDeque.peekFirst()] > 2) {
					left++;
					if (minDeque.peekFirst() < left) {
						minDeque.pollFirst();
					}
					if (maxDeque.peekFirst() < left) {
						maxDeque.pollFirst();
					}
				}
				res += right - left + 1;
			}
			return res;
		}



		public static void main(String[] args) {
			// System.out.println(continuousSubarrays(new int[]{5, 4, 2, 4}));
			System.out.println(continuousSubarraysIV(new int[]{345, 345, 344, 345
					, 345, 344, 345, 345, 345, 345, 345, 345, 344, 344, 344, 344, 343, 344, 344, 343, 344, 343, 342, 343, 344, 345, 346, 347, 347, 346, 345, 345, 346}));
		}
	}

	/**
	 * LCP 68. 美观的花束
	 * 力扣嘉年华的花店中从左至右摆放了一排鲜花，记录于整型一维矩阵 flowers 中每个数字表示该位置所种鲜花的品种编号。你可以选择一段区间的鲜花做成插花，且不能丢弃。 在你选择的插花中，如果每一品种的鲜花数量都不超过 cnt 朵，那么我们认为这束插花是 「美观的」。
	 * 例如：[5,5,5,6,6] 中品种为 5 的花有 3 朵， 品种为 6 的花有 2 朵，每一品种 的数量均不超过 3
	 * 请返回在这一排鲜花中，共有多少种可选择的区间，使得插花是「美观的」。
	 * 注意：
	 * 结果无需取模，用例保证输出为 int32 范围内的整数。
	 * 示例 1：
	 * 输入：flowers = [1,2,3,2], cnt = 1
	 * 输出：8
	 * 解释：相同的鲜花不超过 1 朵，共有 8 种花束是美观的； 长度为 1 的区间 [1]、[2]、[3]、[2] 均满足条件，共 4 种可选择区间 长度为 2 的区间 [1,2]、[2,3]、[3,2] 均满足条件，共 3 种可选择区间 长度为 3 的区间 [1,2,3] 满足条件，共 1 种可选择区间。 区间 [2,3,2],[1,2,3,2] 都包含了 2 朵鲜花 2 ，不满足条件。 返回总数 4+3+1 = 8
	 * 示例 2：
	 * 输入：flowers = [5,3,3,3], cnt = 2
	 * 输出：8
	 * 提示：
	 * 1 <= flowers.length <= 10^5
	 * 1 <= flowers[i] <= 10^5
	 * 1 <= cnt <= 10^5
	 */
	static class BeautifulBouquet {

		/**
		 * 思路:
		 * 1.不定长滑动窗口,窗口右侧和左侧的滑动规则
		 * 2.窗口右侧滑动时,会新生成子数组,这个是思路关键-即窗口中新增元素时,新增子数组场景
		 * @param flowers
		 * @param cnt
		 * @return
		 */
		public static int beautifulBouquet(int[] flowers, int cnt) {
			int l = flowers.length, left = 0, right = 0, cate = 0, res = 0;
			Map<Integer, Integer> map = new HashMap<>();
			for (; right < l; right++) {
				int a = map.getOrDefault(flowers[right], 0);
				map.put(flowers[right], a + 1);
				if (a == cnt) {
					cate++;
				}

				while (left <= right && cate > 0) {
					int b = map.getOrDefault(flowers[left], 0);
					map.put(flowers[left], b - 1);
					if (b == cnt + 1) {
						cate--;
					}
					left++;
				}
				res += right - left + 1;
			}
			return res;
		}

		/**
		 * 在用HashMap维护每种花的实际数量时,也可以直接通过计算HashMap中value是否存在大于cnt的场景
		 * 会循环map的values集合,时间复杂度更高从O(n) 直接变成 O(n^2)
		 * @param flowers
		 * @param cnt
		 * @return
		 */
		public static int beautifulBouquetII(int[] flowers, int cnt) {
			int l = flowers.length, left = 0, right = 0, res = 0;
			Map<Integer, Integer> map = new HashMap<>();
			for (; right < l; right++) {
				int a = map.getOrDefault(flowers[right], 0);
				map.put(flowers[right], a + 1);

				while (left <= right && map.values().stream().anyMatch(x -> x > cnt)) {
					int b = map.getOrDefault(flowers[left], 0);
					map.put(flowers[left], b - 1);
					left++;
				}
				res += right - left + 1;
			}
			return res;
		}

		/**
		 * 既然flowers数据的长度和范围都在10^5,那么我们可以直接通过数组来处理
		 * 数组的下标即表示flowers[i],数组的值即表示count(flowers[i]),同时记录数组的值大于cnt的个数
		 * @param flowers
		 * @param cnt
		 * @return
		 */
		public static int beautifulBouquetIII(int[] flowers, int cnt) {
			int l = flowers.length, left = 0, right = 0, cate = 0, res = 0;
			int[] arr = new int[100001];
			for (; right < l; right++) {
				if (arr[flowers[right]]++ == cnt) {
					cate++;
				}
				while (left <= right && cate > 0) {
					if (arr[flowers[left]]-- == cnt + 1) {
						cate--;
					}
					left++;
				}
				res += right - left + 1;
			}
			return res;
		}

		public static void main(String[] args) {
			System.out.println(beautifulBouquetIII(new int[]{1,2,3,2},1));
		}
	}
}
