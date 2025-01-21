package test.slidingWindow;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 不定长滑动窗口
 */
public class LeetCodeCaseII {

	/**
	 * 3. 无重复字符的最长子串 --回顾
	 */
	static class LengthOfLongestSubstring {

		/**
		 * 思路:
		 * 不定长滑动窗口,确定每个窗口左边界,然后滑动右边界,直到出现重复元素;此时滑动做边界
		 * 实际上是计算以idx=i的元素开始,最长的不重复子串为s[i]
		 *
		 * @param s
		 * @return
		 */
		public static int lengthOfLongestSubstring(String s) {
			int n = s.length(), r = 0, max = 0;
			Set<Character> set = new HashSet<>(n);
			for (int l = 0; l < n; l++) {  // l表示窗口左边界,r表示窗口右边界
				// 当l==0时,代表左边界第一次右移,初始状态,当l>0时,左边界右移代表s[0]找到了最大不重复子串,需要找s[1]
				if (l > 0) {
					set.remove(s.charAt(l - 1)); // 只要出现一个重复字符,l就会右移,所以重复字符最多出现一次,set中可以删掉
				}
				// 此时开始找s[l],以l开始的窗体,最大不重复子串r的位置
				while (r < n && !set.contains(s.charAt(r))) {
					set.add(s.charAt(r));
					r++;
				}
				// 退出while循环时,r已经是s[l]中重复的字符了
				max = Math.max(max, r - l);
			}
			return max;
		}

		public static void main(String[] args) {
			System.out.println(lengthOfLongestSubstring("pwwkew"));
		}
	}

	/**
	 * 3090. 每个字符最多出现两次的最长子字符串
	 * 给你一个字符串 s ，请找出满足每个字符最多出现两次的最长子字符串，并返回该
	 * 子字符串
	 * 的 最大 长度。
	 * 示例 1：
	 * 输入： s = "bcbbbcba"
	 * 输出： 4
	 * 解释：
	 * 以下子字符串长度为 4，并且每个字符最多出现两次："bcbbbcba"。
	 * 示例 2：
	 * 输入： s = "aaaa"
	 * 输出： 2
	 * 解释：
	 * 以下子字符串长度为 2，并且每个字符最多出现两次："aaaa"。
	 * 提示：
	 * 2 <= s.length <= 100
	 * s 仅由小写英文字母组成。
	 */
	static class MaximumLengthSubstring {

		/**
		 * 思路:
		 * 不定长滑动窗口,用cnt记录每个字母出现的次数,当某个字母的数量即将从2变成3,则不允许进入窗口
		 * l,r分别为窗口的左右边界,确定左边界时,能找到长远的右边界
		 *
		 * @param s
		 * @return
		 */
		public static int maximumLengthSubstring(String s) {
			int n = s.length(), max = 0;
			int[] arr = new int[26];
			for (int l = 0, r = 0; l < n; l++) {
				// 当l=0时,初始化不需要移除l;当l>0时,窗口右滑,需要移除l
				if (l > 0) {
					arr[s.charAt(l - 1) - 'a']--;
				}
				while (r < n && arr[s.charAt(r) - 'a'] < 2) {
					arr[s.charAt(r) - 'a']++;
					r++; // r加1后其值为下一次窗口需要新增的元素
				}
				// 出while循环后r已经加1了
				max = Math.max(max, r - l);
			}
			return max;
		}

		/**
		 * 这个思路更符合实际模拟的场景
		 *
		 * @param s
		 * @return
		 */
		public static int maximumLengthSubstringII(String s) {
			int n = s.length(), max = 0;
			int[] arr = new int[26];
			for (int r = 0, l = 0; r < n; r++) {  // l:窗口左边界  r:窗口右边界
				// for循环每次进来,说明右边界右移
				arr[s.charAt(r) - 'a']++;
				// 当窗口内有字母出现两次时,左边界右移,保证窗口符合条件
				while (arr[s.charAt(r) - 'a'] > 2) {
					arr[s.charAt(l) - 'a']--;
					l++;
				}
				max = Math.max(max, r - l + 1); // 此时l,r都恰好是窗口的左右边界位置
			}
			return max;
		}

		public static void main(String[] args) {
			System.out.println(maximumLengthSubstring("bcbbbcba"));
		}
	}

	/**
	 * 1493. 删掉一个元素以后全为 1 的最长子数组
	 * 回顾
	 */
	static class LongestSubarray {

		/**
		 * 不定长滑动窗口,在窗口中统计出现0的次数,以保证窗口内的元素符合条件
		 * 窗口内有1个0或者0个0,此时窗口长度都需要减1
		 *
		 * @param nums
		 * @return
		 */
		public static int longestSubarray(int[] nums) {
			int n = nums.length, cnt = 0, max = 0;
			for (int r = 0, l = 0; r < n; r++) {
				if (nums[r] == 0) {
					cnt++;
				}
				while (l < n && cnt > 1) {  // 窗口内的0只能有一个
					if (nums[l] == 0) {
						cnt--;
					}
					l++;
				}
				max = Math.max(max, r - l); // 在计算最大值时,还没得到最大窗口时也计算了
			}
			return max;
		}

		public static void main(String[] args) {
			System.out.println(longestSubarray(new int[]{0, 1, 1, 1, 0, 1, 1, 0, 1}));
		}
	}

	/**
	 * 1208. 尽可能使字符串相等
	 * 给你两个长度相同的字符串，s 和 t。
	 * 将 s 中的第 i 个字符变到 t 中的第 i 个字符需要 |s[i] - t[i]| 的开销（开销可能为 0），也就是两个字符的 ASCII 码值的差的绝对值。
	 * 用于变更字符串的最大预算是 maxCost。在转化字符串时，总开销应当小于等于该预算，这也意味着字符串的转化可能是不完全的。
	 * 如果你可以将 s 的子字符串转化为它在 t 中对应的子字符串，则返回可以转化的最大长度。
	 * 如果 s 中没有子字符串可以转化成 t 中对应的子字符串，则返回 0。
	 * 示例 1：
	 * 输入：s = "abcd", t = "bcdf", maxCost = 3
	 * 输出：3
	 * 解释：s 中的 "abc" 可以变为 "bcd"。开销为 3，所以最大长度为 3。
	 * 示例 2：
	 * 输入：s = "abcd", t = "cdef", maxCost = 3
	 * 输出：1
	 * 解释：s 中的任一字符要想变成 t 中对应的字符，其开销都是 2。因此，最大长度为 1。
	 * 示例 3：
	 * 输入：s = "abcd", t = "acde", maxCost = 0
	 * 输出：1
	 * 解释：a -> a, cost = 0，字符串未发生变化，所以最大长度为 1。
	 * 提示：
	 * 1 <= s.length, t.length <= 10^5
	 * 0 <= maxCost <= 10^6
	 * s 和 t 都只含小写英文字母。
	 */
	static class EqualSubstring {

		/**
		 * 题意其实有点难理解,s[i] 和 t[i] 对应的子数组为s'[i]和t'[i],实际上是指,对应原数组上相同序列的子串,其开销不超过maxCost的
		 * 最大长度.其实是对同序列上s和t元素开销数组,在满足maxCost时,求最长长度.
		 * 所以变成了经典的不定长滑动窗口题. s:[abcd] t:[bcdf] diff:[1,1,1,2]
		 *
		 * @param s
		 * @param t
		 * @param maxCost
		 * @return
		 */
		public static int equalSubstring(String s, String t, int maxCost) {
			int n = s.length();
			int[] diff = new int[n];
			for (int i = 0; i < n; i++) {
				diff[i] = Math.abs(s.charAt(i) - t.charAt(i));
			}
			int max = 0, sum = 0;
			for (int r = 0, l = 0; r < n; r++) {
				sum += diff[r];
				while (l < n && sum > maxCost) {
					sum -= diff[l];
					l++;
				}
				max = Math.max(max, r - l + 1);
			}
			return max;
		}

		public static void main(String[] args) {
			System.out.println(equalSubstring("abcd", "bcdf", 3));
		}
	}

	/**
	 * 2730. 找到最长的半重复子字符串
	 * 给你一个下标从 0 开始的字符串 s ，这个字符串只包含 0 到 9 的数字字符。
	 * 如果一个字符串 t 中至多有一对相邻字符是相等的，那么称这个字符串 t 是 半重复的 。例如，"0010" 、"002020" 、"0123" 、"2002" 和 "54944" 是半重复字符串，而 "00101022" （相邻的相同数字对是 00 和 22）和 "1101234883" （相邻的相同数字对是 11 和 88）不是半重复字符串。
	 * 请你返回 s 中最长 半重复
	 * 子字符串
	 * 的长度。
	 * 示例 1：
	 * 输入：s = "52233"
	 * 输出：4
	 * 解释：
	 * 最长的半重复子字符串是 "5223"。整个字符串 "52233" 有两个相邻的相同数字对 22 和 33，但最多只能选取一个。
	 * 示例 2：
	 * 输入：s = "5494"
	 * 输出：4
	 * 解释：
	 * s 是一个半重复字符串。
	 * 示例 3：
	 * 输入：s = "1111111"
	 * 输出：2
	 * 解释：
	 * 最长的半重复子字符串是 "11"。子字符串 "111" 有两个相邻的相同数字对，但最多允许选取一个。
	 * 提示：
	 * 1 <= s.length <= 50
	 * '0' <= s[i] <= '9'
	 */
	static class LongestSemiRepetitiveSubstring {

		/**
		 * 思路:
		 * 不定长滑动窗口,求最长子串,关键点在于要求窗口内,连续相同的字符对数,不能大于1
		 * 如果判断窗口内连续相同的字符对数是否大于1呢?因为有连续这个要求,所以Set无法实现
		 * 思路:记录窗口中最后一个元素为pr,下次新进的元素r是否与pr相同,如果相同则连续相同字符对数cnt加1
		 * *   记录窗口中,第一个元素为pl,下次新移除的元素l是否与pl相同,如果相同则连续相同字符对数cnt减1
		 * cnt怎么减呢? 比如:0,0,0,1 当l从0->1 时,去掉第一个0,此时cnt应该减一,实际上去掉的这个元素应该是跟其后面的元素进行比较
		 *
		 * @param s
		 * @return
		 */
		public static int longestSemiRepetitiveSubstring(String s) {
			int n = s.length(), max = 0, cnt = 0;
			char pr = ' ';
			for (int r = 0, l = 0; r < n; r++) {
				if (s.charAt(r) == pr) {
					cnt++;
				}
				pr = s.charAt(r);
				while (l < n && cnt > 1) {
					if (l + 1 < n && s.charAt(l + 1) == s.charAt(l)) {  // 当l左移时窗口移除s[l]元素时,需要与s[l+1]进行比较
						cnt--;
					}
					l++;
				}
				max = Math.max(max, r - l + 1);
			}
			return max;
		}

		public static void main(String[] args) {
			System.out.println(longestSemiRepetitiveSubstring("0001"));
		}
	}

	/**
	 * 904. 水果成篮
	 * 你正在探访一家农场，农场从左到右种植了一排果树。这些树用一个整数数组 fruits 表示，其中 fruits[i] 是第 i 棵树上的水果 种类 。
	 * 你想要尽可能多地收集水果。然而，农场的主人设定了一些严格的规矩，你必须按照要求采摘水果：
	 * 你只有 两个 篮子，并且每个篮子只能装 单一类型 的水果。每个篮子能够装的水果总量没有限制。
	 * 你可以选择任意一棵树开始采摘，你必须从 每棵 树（包括开始采摘的树）上 恰好摘一个水果 。采摘的水果应当符合篮子中的水果类型。每采摘一次，你将会向右移动到下一棵树，并继续采摘。
	 * 一旦你走到某棵树前，但水果不符合篮子的水果类型，那么就必须停止采摘。
	 * 给你一个整数数组 fruits ，返回你可以收集的水果的 最大 数目。
	 * 示例 1：
	 * 输入：fruits = [1,2,1]
	 * 输出：3
	 * 解释：可以采摘全部 3 棵树。
	 * 示例 2：
	 * 输入：fruits = [0,1,2,2]
	 * 输出：3
	 * 解释：可以采摘 [1,2,2] 这三棵树。
	 * 如果从第一棵树开始采摘，则只能采摘 [0,1] 这两棵树。
	 * 示例 3：
	 * 输入：fruits = [1,2,3,2,2]
	 * 输出：4
	 * 解释：可以采摘 [2,3,2,2] 这四棵树。
	 * 如果从第一棵树开始采摘，则只能采摘 [1,2] 这两棵树。
	 * 示例 4：
	 * 输入：fruits = [3,3,3,1,2,1,1,2,3,3,4]
	 * 输出：5
	 * 解释：可以采摘 [1,2,1,1,2] 这五棵树。
	 * 提示：
	 * 1 <= fruits.length <= 105
	 * 0 <= fruits[i] < fruits.length
	 */
	static class TotalFruit {

		/**
		 * 思路:
		 * 题中条件1 说明滑动窗口中最多能出现的水果种类为2,超过2窗口内元素不符合条件
		 * 题中条件2 可以选择任意一颗树开始采摘,每棵树恰好一个摘水果,采摘的树是所有树的子数组
		 * 结合起来,符合不定长滑动窗口特性,以一颗树开始,最远能到第几个树
		 * ------------------------------------------------------------
		 * 关键点在于窗口内怎么判断,种类是否超过了2呢?
		 * 3,3,3,1,2
		 * Set用户判断是否重复,这里场景不适用
		 * 可以用HashMap来记录每种水果的数量,然后计算有多少个种类 cnt
		 * 也可以直接用数组,本题需要创建长度为n的数组
		 *
		 * @param fruits
		 * @return
		 */
		public static int totalFruit(int[] fruits) {
			int n = fruits.length, max = 0, cnt = 0;
			int[] arr = new int[n];
			// set来统计种类时,需要先删除后新增
			for (int r = 0, l = 0; r < n; r++) {
				arr[fruits[r]]++;
				if (arr[fruits[r]] == 1) {  // 该类型水果数量从0->1 种类+1
					cnt++;
				}
				while (l < n && cnt > 2) {
					arr[fruits[l]]--;
					if (arr[fruits[l]] == 0) { // 该类型水果数量从1->0 种类-1
						cnt--;
					}
					l++;
				}
				max = Math.max(max, r - l + 1);
			}
			return max;
		}

		/**
		 * 当然用HashMap进行计数也可以;这种窗口内元素的种类限制类型,都可以用计数来解
		 *
		 * @param fruits
		 * @return
		 */
		public static int totalFruitII(int[] fruits) {
			int n = fruits.length, max = 0;
			Map<Integer, Integer> cnt = new HashMap<>(n);
			for (int r = 0, l = 0; r < n; r++) {
				cnt.put(fruits[r], cnt.getOrDefault(fruits[r], 0) + 1);
				while (l < n && cnt.size() > 2) {
					int v = cnt.getOrDefault(fruits[l], 0);
					cnt.put(fruits[l], v - 1);
					if (v == 1) {
						cnt.remove(fruits[l]);
					}
					l++;
				}
				max = Math.max(max, r - l + 1);
			}
			return max;
		}

		public static void main(String[] args) {
			System.out.println(totalFruitII(new int[]{3, 3, 3, 1, 2, 1, 1, 2, 3, 3, 4}));
		}
	}

	/**
	 * 1695. 删除子数组的最大得分
	 * 给你一个正整数数组 nums ，请你从中删除一个含有 若干不同元素 的子数组。删除子数组的 得分 就是子数组各元素之 和 。
	 * 返回 只删除一个 子数组可获得的 最大得分 。
	 * 如果数组 b 是数组 a 的一个连续子序列，即如果它等于 a[l],a[l+1],...,a[r] ，那么它就是 a 的一个子数组。
	 * 示例 1：
	 * 输入：nums = [4,2,4,5,6]
	 * 输出：17
	 * 解释：最优子数组是 [2,4,5,6]
	 * 示例 2：
	 * 输入：nums = [5,2,1,2,5,2,1,2,5]
	 * 输出：8
	 * 解释：最优子数组是 [5,2,1] 或 [1,2,5]
	 * 提示：
	 * 1 <= nums.length <= 105
	 * 1 <= nums[i] <= 104
	 */
	static class MaximumUniqueSubarray {

		/**
		 * 思路:
		 * 滑动窗口内要求元素不重复,由于元素的取值很大,用数组空间复杂度太高
		 * 使用Set来存放不可重复元素,但是set需要先删除后新增,这样保持set中一直都没有重复的元素
		 *
		 * @param nums
		 * @return
		 */
		public static int maximumUniqueSubarray(int[] nums) {
			int n = nums.length, max = 0, sum = 0;
			Set<Integer> set = new HashSet<>();
			for (int l = 0, r = 0; l < n; l++) {
				if (l > 0) { // 左边界右移
					set.remove(nums[l - 1]);
					sum -= nums[l - 1];
				}
				while (r < n && !set.contains(nums[r])) {
					set.add(nums[r]);
					sum += nums[r];
					r++;
				}
				max = Math.max(max, sum); // while中出来,r已经+1
			}
			return max;
		}

		/**
		 * 用数组对窗口内每个元素进行计数
		 *
		 * @param nums
		 * @return
		 */
		public static int maximumUniqueSubarrayII(int[] nums) {
			int n = nums.length, max = 0, sum = 0, cnt = 0; // cnt表示重复元素的数量
			int[] arr = new int[10001];
			for (int r = 0, l = 0; r < n; r++) {
				arr[nums[r]]++;
				sum += nums[r];
				if (arr[nums[r]] == 2) {   // 数量从1变成2
					cnt++;
				}
				while (l < n && cnt > 0) {
					arr[nums[l]]--;
					sum -= nums[l];
					if (arr[nums[l]] == 1) {  // 数量从2变成1
						cnt--;
					}
					l++;
				}
				max = Math.max(max, sum);
			}
			return max;
		}

		public static int maximumUniqueSubarrayIII(int[] nums) {
			int n = nums.length, max = 0, sum = 0, cnt = 0; // cnt表示重复元素的数量
			if (n == 1) return nums[0];
			// 因为窗体内不会存在重复的数字,所以可以用boolean数组表示每个元素是否存在于窗口内
			boolean[] exist = new boolean[10001];
			for (int r = 0, l = 0; r < n; r++) {
				boolean pre = exist[nums[r]];
				sum += nums[r];
				exist[nums[r]] = true;
				while (l < n && pre) {  // 当前r元素已经存在
					sum -= nums[l];
					if (nums[l] != nums[r]) {
						exist[nums[l]] = false;
					} else {  // 移除l刚好和r相同,此时r继续后移,所以退出while
						l++;
						break;
					}
					l++;
				}
				max = Math.max(max, sum);
			}
			return max;
		}

		public static void main(String[] args) {
			System.out.println(maximumUniqueSubarrayIII(new int[]{4, 2, 4, 5, 6}));
		}
	}
}
