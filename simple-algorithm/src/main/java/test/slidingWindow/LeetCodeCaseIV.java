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
}
