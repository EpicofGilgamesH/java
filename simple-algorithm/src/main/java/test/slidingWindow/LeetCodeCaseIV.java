package test.slidingWindow;

public class LeetCodeCaseIV {

	/**
	 * 1358. 包含所有三种字符的子字符串数目
	 * 给你一个字符串 s ，它只包含三种字符 a, b 和 c 。
	 * 请你返回 a，b 和 c 都 至少 出现过一次的子字符串数目。
	 * 示例 1：
	 * 输入：s = "abcabc"
	 * 输出：10
	 * 解释：包含 a，b 和 c 各至少一次的子字符串为 "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" 和 "abc" (相同字符串算多次)。
	 * 示例 2：
	 * 输入：s = "aaacb"
	 * 输出：3
	 * 解释：包含 a，b 和 c 各至少一次的子字符串为 "aaacb", "aacb" 和 "acb" 。
	 * 示例 3：
	 * 输入：s = "abc"
	 * 输出：1
	 * 提示：
	 * 3 <= s.length <= 5 x 10^4
	 * s 只包含字符 a，b 和 c 。
	 */
	static class NumberOfSubstrings {

		/**
		 * 思路:
		 * 显然,这个滑动窗口计算子数组的数量,不再是以后缀来确认个数
		 * "abcabc" 为例,当l=0,r=2时,窗口为 "abc",此时窗口内字符串满足条件,同时以"abc"为前缀的字符串都满足,子字符串的数量为,l-right
		 *
		 * @param s
		 * @return
		 */
		public static int numberOfSubstrings(String s) {
			int length = s.length(), l = 0, res = 0;
			int[] cnt = new int[3];
			for (int r = 0; r < length; r++) {
				cnt[s.charAt(r) - 'a']++;
				while (l <= r && cnt[0] >= 1 && cnt[1] >= 1 && cnt[2] >= 1) {
					// 当while循环进来时,则说明有满足情况的子字符串
					// 1.r右移,后满足情况进入while循环
					// 2.进入while循环后,l继续左移,后仍进入while循环
					res += length - r;
					cnt[s.charAt(l) - 'a']--;
					l++;
				}
			}
			return res;
		}

		public static void main(String[] args) {
			System.out.println(numberOfSubstrings("abc"));
		}
	}

	/**
	 * 2962. 统计最大元素出现至少 K 次的子数组
	 * 给你一个整数数组 nums 和一个 正整数 k 。
	 * 请你统计有多少满足 「 nums 中的 最大 元素」至少出现 k 次的子数组，并返回满足这一条件的子数组的数目。
	 * 子数组是数组中的一个连续元素序列。
	 * 示例 1：
	 * 输入：nums = [1,3,2,3,3], k = 2
	 * 输出：6
	 * 解释：包含元素 3 至少 2 次的子数组为：[1,3,2,3]、[1,3,2,3,3]、[3,2,3]、[3,2,3,3]、[2,3,3] 和 [3,3] 。
	 * 示例 2：
	 * 输入：nums = [1,4,2,1], k = 3
	 * 输出：0
	 * 解释：没有子数组包含元素 4 至少 3 次。
	 * 提示：
	 * 1 <= nums.length <= 105
	 * 1 <= nums[i] <= 106
	 * 1 <= k <= 105
	 */
	static class CountSubarrays {

		/**
		 * 思路:
		 * 滑动窗口取至少满足的场景
		 * 1.在while循环中的到满足的场景
		 * 2.子数组是通过前缀来确定个数
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static long countSubarrays(int[] nums, int k) {
			int l = nums.length, left = 0, right = 0, count = 0, max = 0;
			long res = 0;
			// 遍历一次获取最大的元素
			for (int i = 0; i < l; i++) {
				max = Math.max(max, nums[i]);
			}
			for (; right < l; right++) {
				if (nums[right] == max) {
					count++;
				}
				while (left <= right && count >= k) {
					res += l - right;
					if (nums[left] == max) {
						count--;
					}
					left++;
				}
			}
			return res;
		}

		/**
		 * 还是可以跟寻找至多一样,通过后缀来判断子数组的数量
		 * [1,3,2,3,3], k = 2
		 * 1.当r到第二个3时,满足情况,进入内层while循环,说明存在满足情况的场景,此时出内存while循环,
		 * l在第一个3的位置,r在第二个3的位置,以[3,2,3]为后缀,子数组为 [1,3,2,3] [3,2,3]即l+1
		 * 2.当r在第三个3时,满足情况,进入内层while循环,然后出while循环(窗口内从满足到不满足情况)
		 * l在第3个3的位置,r也在第三个3的位置,所以 [3,3]为后缀的子数组都满足情况
		 * 3.当外层for循环执行后,内层while循环没有进入呢? 说明没有任何满足场景的窗口存在,
		 * 此时l不会右移,l=0
		 * 4.那么当数组的后部分没有满足情况的场景呢?比如[1,3,3,2,2,2,2,2,2] 当l在第二个3,r也在第二个3时,以[3,3]为后缀
		 * 有两个字数组.后回到for循环,r一直往后移动,且不会进入内层循环,l一直在第二个3的位置.
		 * 可以理解为以[3,3,...,r]为前缀来计算子数组,那么一直都是2个,直到r到达数组的末尾
		 * @param nums
		 * @param k
		 * @return
		 */
		public static long countSubarraysII(int[] nums, int k) {
			int l = nums.length, left = 0, right = 0, count = 0, max = 0;
			long res = 0;
			for (int i = 0; i < l; i++) {
				max = Math.max(max, nums[i]);
			}
			for (; right < l; right++) {
				if (nums[right] == max) {
					count++;
				}
				while (left <= right && count >= k) {
					if (nums[left] == max) {
						count--;
					}
					left++;
				}
				res += left;
			}
			return res;
		}

		public static void main(String[] args) {
			System.out.println(countSubarraysII(new int[]{1, 3, 3, 2, 2, 2, 2, 2, 2}, 2));
		}

	}
}
