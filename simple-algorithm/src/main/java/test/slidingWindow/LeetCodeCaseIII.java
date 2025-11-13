package test.slidingWindow;

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
}
