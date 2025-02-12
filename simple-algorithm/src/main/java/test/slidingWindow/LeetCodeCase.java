package test.slidingWindow;

import org.omg.CORBA.INTERNAL;

import java.util.*;

/**
 * 滑动窗口
 */
public class LeetCodeCase {

	/**
	 * 438. 找到字符串中所有字母异位词
	 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
	 * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
	 * 示例 1:
	 * 输入: s = "cbaebabacd", p = "abc"
	 * 输出: [0,6]
	 * 解释:
	 * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
	 * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
	 * 示例 2:
	 * 输入: s = "abab", p = "ab"
	 * 输出: [0,1,2]
	 * 解释:
	 * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
	 * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
	 * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
	 * 提示:
	 * 1 <= s.length, p.length <= 3 * 104
	 * s 和 p 仅包含小写字母
	 */
	public static class FindAnagrams {

		/**
		 * 滑动窗口,固定长度为n;每次向后移动一位时,判断窗体内的字符是否为符合条件的字符
		 * 利用hash写得非常复杂,还要考虑窗口第一次初始化操作
		 *
		 * @param s
		 * @param p
		 * @return
		 */
		public static List<Integer> findAnagrams(String s, String p) {
			List<Integer> list = new ArrayList<>();
			int m = s.length(), n = p.length();
			if (n > m) return list;
			HashMap<Character, Integer> map = new HashMap<>();
			for (char c : p.toCharArray()) {
				if (map.containsKey(c)) {
					map.put(c, map.get(c) + 1);
				} else {
					map.put(c, 1);
				}
			}
			HashMap<Character, Integer> mapSlid = new HashMap<>();
			for (int i = 0; i < n; i++) {
				char c = s.charAt(i);
				if (mapSlid.containsKey(c)) {
					mapSlid.put(c, mapSlid.get(c) + 1);
				} else {
					mapSlid.put(c, 1);
				}
			}
			boolean pre = false;
			if (isValid(map, mapSlid)) {
				list.add(0);
				pre = true;
			}
			for (int i = 1; i <= m - n; i++) {
				int last = i + n - 1;
				char c = s.charAt(i - 1), l = s.charAt(last);
				if (c != l) { // 窗口滑动后字符发生变化
					if (mapSlid.containsKey(l)) {
						mapSlid.put(l, mapSlid.get(l) + 1);
					} else {
						mapSlid.put(l, 1);
					}
					Integer v = mapSlid.get(c);
					if (v == 1) {
						mapSlid.remove(c);
					} else {
						mapSlid.put(c, v - 1);
					}
					pre = isValid(map, mapSlid);

				}
				if (pre) list.add(i);
			}
			return list;
		}

		private static boolean isValid(HashMap<Character, Integer> map, HashMap<Character, Integer> mapSlid) {
			if (map.size() != mapSlid.size()) return false;
			for (Map.Entry<Character, Integer> entry : map.entrySet()) {
				if (!entry.getValue().equals(mapSlid.get(entry.getKey()))) {
					return false;
				}
			}
			return true;
		}

		public static void main(String[] args) {
			List<Integer> anagrams = findAnagrams("aa", "bb");
			System.out.println(anagrams);
		}
	}

	/**
	 * 76. 最小覆盖子串
	 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
	 * 注意：
	 * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
	 * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
	 * 示例 1：
	 * 输入：s = "ADOBECODEBANC", t = "ABC"
	 * 输出："BANC"
	 * 解释：最小覆盖子串 "BANC" 包含来自字符串 t 的 'A'、'B' 和 'C'。
	 * 示例 2：
	 * 输入：s = "a", t = "a"
	 * 输出："a"
	 * 解释：整个字符串 s 是最小覆盖子串。
	 * 示例 3:
	 * 输入: s = "a", t = "aa"
	 * 输出: ""
	 * 解释: t 中两个字符 'a' 均应包含在 s 的子串中，
	 * 因此没有符合条件的子字符串，返回空字符串。
	 * 提示：
	 * m == s.length
	 * n == t.length
	 * 1 <= m, n <= 105
	 * s 和 t 由英文字母组成
	 * 进阶：你能设计一个在 o(m+n) 时间内解决此问题的算法吗？
	 */
	public static class MinWindow {

		/**
		 * 滑动窗口,窗口的大小不固定
		 * 窗口的左右边界为left,right;初始情况下,当窗体中出现的字符不符合t中出现字符的个数时,right不停地想右移动
		 * 之后开始移动left,直到窗体没有覆盖t中出现的字符;
		 * 重复以上步骤
		 * 在找到覆盖t的窗口时,更新ansLeft和ansRight,如何更新呢?当right-left更小时,即更新
		 *
		 * @param s
		 * @param t
		 * @return
		 */
		public static String minWindow(String s, String t) {
			int ansLeft = -1, ansRight = s.length(), left = 0;
			int[] cntT = new int[128];  // 所有的ASCII字符
			int[] cntS = new int[128];
			for (int i = 0; i < t.length(); i++) {  // 记录t中每个字符出现的次数
				cntT[t.charAt(i)]++;
			}
			for (int right = 0; right < s.length(); right++) {
				char crrRight = s.charAt(right);
				cntS[crrRight]++;
				while (isCovered(cntS, cntT)) { // 如果覆盖了,更新ansLeft,ansRight
					if (right - left < ansRight - ansLeft) {
						ansLeft = left;
						ansRight = right;
					}
					cntS[s.charAt(left++)]--;
				}
			}
			return ansLeft < 0 ? "" : s.substring(ansLeft, ansRight + 1);
		}

		private static boolean isCovered(int[] cntS, int[] cntT) {
			for (int i = 'A'; i <= 'Z'; i++) {
				if (cntS[i] < cntT[i]) {
					return false;
				}
			}
			for (int i = 'a'; i <= 'z'; i++) {
				if (cntS[i] < cntT[i]) {
					return false;
				}
			}
			return true;
		}

		/**
		 * 优化方法,避免每次都去比较cntS出现字母的次数覆盖所有cntT
		 * 因为每次cntS都只会移入right或者移除left,那么用less表示cntS相对于cntT中字母缺失的字母种类
		 * 比如cntT= [A:1,B:1,C:1] 那么初始化时cntS确实的字母种类为3
		 * 那么在cntS移入right时,如果cntS[right]==cntT[right] 那么说明right字母数量相等了,后续可能cntS[right] > cntT[right]
		 * 但是less只能减一,减去这种字母的缺失
		 * 那么在cntS移除left时,如果cntS[left]==cntT[left] 那么说明left字母数量相等了,后续可能cntS[left] < cntT[left],
		 * 但是同样less只能+1,加上这种字母的缺失
		 * 所以始终只需要在cntS移入和移除元素时,对less进行维护;当less=0时,说明cntS相对于cntT中字母缺失的字母种类为0
		 * <p>
		 * 相当巧妙的方法,滑动窗体常遇场景
		 *
		 * @param s
		 * @param t
		 * @return
		 */
		public static String minWindowI(String s, String t) {
			int ansLeft = -1, ansRight = s.length(), left = 0, less = 0;
			int[] cntT = new int[128];  // 所有的ASCII字符
			int[] cntS = new int[128];
			for (int i = 0; i < t.length(); i++) {  // 记录t中每个字符出现的次数
				if (cntT[t.charAt(i)]++ == 0) {  // less记录t中出现的字母种类个数
					less++;
				}
			}
			for (int right = 0; right < s.length(); right++) {
				char crrRight = s.charAt(right);
				if (++cntS[crrRight] == cntT[crrRight]) {  // crrRight字母数量相同时,less减一
					less--;
				}
				while (less == 0) { // cntS相对于cntT中字母缺失的字母种类为0
					if (right - left < ansRight - ansLeft) {
						ansLeft = left;
						ansRight = right;
					}
					char crrLeft = s.charAt(left++);
					if (cntS[crrLeft]-- == cntT[crrLeft]) {  // 从覆盖为前提,窗口左边后移时,字母crrLeft可能从等于变成小于
						less++;
					}
				}
			}
			return ansLeft < 0 ? "" : s.substring(ansLeft, ansRight + 1);
		}

		public static void main(String[] args) {
			System.out.println(minWindowI("ADOBECODEBANC", "ABC"));
		}
	}

	/**
	 * 643. 子数组最大平均数 I
	 * 给你一个由 n 个元素组成的整数数组 nums 和一个整数 k 。
	 * 请你找出平均数最大且 长度为 k 的连续子数组，并输出该最大平均数。
	 * 任何误差小于 10-5 的答案都将被视为正确答案。
	 * 示例 1：
	 * 输入：nums = [1,12,-5,-6,50,3], k = 4
	 * 输出：12.75
	 * 解释：最大平均数 (12-5-6+50)/4 = 51/4 = 12.75
	 * 示例 2：
	 * 输入：nums = [5], k = 1
	 * 输出：5.00000
	 * 提示：
	 * n == nums.length
	 * 1 <= k <= n <= 105
	 * -104 <= nums[i] <= 104
	 */
	public static class FindMaxAverage {

		/**
		 * 滑动窗体固定长度k,每次向右滑动一位,并计算平均值
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static double findMaxAverage(int[] nums, int k) {
			int sum = 0;
			double ave = Integer.MIN_VALUE;
			for (int i = 0; i < k; i++) {
				sum += nums[i];
			}
			ave = Math.max(ave, (double) sum / k);
			for (int i = 1; i <= nums.length - k; i++) {
				// 计算总和
				sum -= nums[i - 1];
				sum += nums[i + k - 1];
				ave = Math.max(ave, (double) sum / k);
			}
			return ave;
		}

		public static double findMaxAverageI(int[] nums, int k) {
			int sum = 0;
			for (int i = 0; i < k; i++) {
				sum += nums[i];
			}
			int max = sum;
			for (int i = 1; i <= nums.length - k; i++) {
				// 计算总和
				sum = sum - nums[i - 1] + nums[i + k - 1];
				max = Math.max(sum, max);
			}
			return (double) max / k;
		}

		public static void main(String[] args) {
			System.out.println(findMaxAverageI(new int[]{0, 4, 0, 3, 2}, 1));
		}
	}

	/**
	 * 1456. 定长子串中元音的最大数目
	 * 给你字符串 s 和整数 k 。
	 * 请返回字符串 s 中长度为 k 的单个子字符串中可能包含的最大元音字母数。
	 * 英文中的 元音字母 为（a, e, i, o, u）。
	 * 示例 1：
	 * 输入：s = "abciiidef", k = 3
	 * 输出：3
	 * 解释：子字符串 "iii" 包含 3 个元音字母。
	 * 示例 2：
	 * 输入：s = "aeiou", k = 2
	 * 输出：2
	 * 解释：任意长度为 2 的子字符串都包含 2 个元音字母。
	 * 示例 3：
	 * 输入：s = "leetcode", k = 3
	 * 输出：2
	 * 解释："lee"、"eet" 和 "ode" 都包含 2 个元音字母。
	 * 示例 4：
	 * 输入：s = "rhythms", k = 4
	 * 输出：0
	 * 解释：字符串 s 中不含任何元音字母。
	 * 示例 5：
	 * 输入：s = "tryhard", k = 4
	 * 输出：1
	 * 提示：
	 * 1 <= s.length <= 10^5
	 * s 由小写英文字母组成
	 * 1 <= k <= s.length
	 */
	public static class MaxVowels {

		/**
		 * 定长的窗体滑动,窗体的起始idx为i,每次向右滑动一位时删掉 i-1位置的字符,添加k-i+1位置的字符
		 * 计算的同时,统计元音字母的数量
		 *
		 * @param s
		 * @param k
		 * @return
		 */
		public static int maxVowels(String s, int k) {
			char[] chars = s.toCharArray();
			int count = 0;
			for (int i = 0; i < k; i++) {
				count += isVowels(chars[i]);
			}
			int max = count; // 最大值的初始化应为count第一次计算的值
			for (int i = 1; i <= chars.length - k; i++) {
				count += isVowels(chars[i + k - 1]) - isVowels(chars[i - 1]);
				max = Math.max(max, count);
				if (max == k) return max;
			}
			return max;
		}

		private static int isVowels(char c) {
			return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ? 1 : 0;
		}

		public static void main(String[] args) {
			System.out.println(maxVowels("a", 1));
		}

	}

	/**
	 * 1004. 最大连续1的个数 III
	 * 给定一个二进制数组 nums 和一个整数 k，如果可以翻转最多 k 个 0 ，则返回 数组中连续 1 的最大个数 。
	 * 示例 1：
	 * 输入：nums = [1,1,1,0,0,0,1,1,1,1,0], K = 2
	 * 输出：6
	 * 解释：[1,1,1,0,0,1,1,1,1,1,1]
	 * 粗体数字从 0 翻转到 1，最长的子数组长度为 6。
	 * 示例 2：
	 * 输入：nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], K = 3
	 * 输出：10
	 * 解释：[0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
	 * 粗体数字从 0 翻转到 1，最长的子数组长度为 10。
	 * 提示：
	 * 1 <= nums.length <= 105
	 * nums[i] 不是 0 就是 1
	 * 0 <= k <= nums.length
	 */
	public static class LongestOnes {

		/**
		 * 思路:
		 * 不定长滑动窗口,初始状态下的窗口为到第一个0开始,然后往后遍历,用1补充0,计算其能到达窗体的最远右边界
		 * 然后寻找第二个0开始作为窗口的左边界,然后同样计算其能到达的最右边界,直到最右边界到数组的末尾
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static int longestOnes(int[] nums, int k) {
			if (k == 0) return getK0(nums);
			int l = nums.length, max = 0;
			int left = 0;
			for (; left < l; left++) {
				// 往右扩大窗口
				if (left == 0 || nums[left] == 0) {
					int right = left, com = k, count = getPrevCount(nums, left);
					while (right < l) {
						if (nums[right] == 1) {
							count++;
						} else {  // 下一个数是0,先用k去补偿,当k不够补偿时退出
							if (com > 0) {
								count++;
								com--;
							} else {
								break;
							}
						}
						right++;
					}
					max = Math.max(max, count);
				}
			}
			return max;
		}

		private static int getPrevCount(int[] nums, int idx) {
			if (idx == 0) return 0;
			int count = 0;
			for (int i = idx - 1; i >= 0; i--) {
				if (nums[i] == 1) count++;
				else break;
			}
			return count;
		}

		private static int getK0(int[] nums) {
			int max = 0;
			for (int i = 0; i < nums.length; i++) {
				if (nums[i] == 1) {  // 开始计数
					int count = 1;
					while (i < nums.length - 1 && nums[++i] == 1) {
						count++;
					}
					max = Math.max(max, count);
				}
			}
			return max;
		}

		/**
		 * 查看解题思路
		 * 滑动窗口,右边界主动后移,左边界被动后移,在窗口区间定义出所有满足情况的最大元素集合
		 * 1.窗口左边界主动右移一位,当前元素为0时,则记录为0的变量zero=1,当zero<=k时,左边界可以右移
		 * 2.窗口右边界被动右移,当窗口内为0的元素超过k时,即zero=k+1时,左边界右移,直到窗口内为0的元素小于等于k
		 * 3.窗口内元素最多的个数即为所求
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static int longestOnesI(int[] nums, int k) {
			int left = 0, right = 0, l = nums.length, zero = 0, count = 0;
			while (right < l) {
				if (nums[right] == 0) {
					zero++;
				}
				while (zero > k) {
					if (nums[left++] == 0) {
						zero--;
					}
				}
				count = Math.max(count, right - left + 1);
				right++;
			}
			return count;
		}

		public static void main(String[] args) {
			int i1 = longestOnesI(new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2);
			System.out.println(i1);
		}
	}

	/**
	 * 1493. 删掉一个元素以后全为 1 的最长子数组
	 * 给你一个二进制数组 nums ，你需要从中删掉一个元素。
	 * 请你在删掉元素的结果数组中，返回最长的且只包含 1 的非空子数组的长度。
	 * 如果不存在这样的子数组，请返回 0 。
	 * 提示 1：
	 * 输入：nums = [1,1,0,1]
	 * 输出：3
	 * 解释：删掉位置 2 的数后，[1,1,1] 包含 3 个 1 。
	 * 示例 2：
	 * 输入：nums = [0,1,1,1,0,1,1,0,1]
	 * 输出：5
	 * 解释：删掉位置 4 的数字后，[0,1,1,1,1,1,0,1] 的最长全 1 子数组为 [1,1,1,1,1] 。
	 * 示例 3：
	 * 输入：nums = [1,1,1]
	 * 输出：2
	 * 解释：你必须要删除一个元素。
	 * 提示：
	 * 1 <= nums.length <= 105
	 * nums[i] 要么是 0 要么是 1 。
	 */
	public static class LongestSubarray {

		/**
		 * 滑动窗口
		 * 窗口的左右边界为left,right
		 * 1.right向右移动,当遇到0时,zero+1
		 * 2.当zero>1时,即窗口内0的数量 >1,需要left右移,当nums[left] == 0 时,right继续向右移动
		 * 3.当right移动到最右边时,zero=0,那么窗口长度需要-1
		 *
		 * @param nums
		 * @return
		 */
		public static int longestSubarray(int[] nums) {
			int left = 0, right = 0, n = nums.length, zero = 0, count = 0;
			while (right < n) {
				if (nums[right] == 0) {
					zero++;
				}
				while (zero > 1) {
					if (nums[left++] == 0) {
						zero--;
					}
				}
				count = Math.max(count, right - left);  // 补了一个0,然后减掉,即-1; 一个0都没补,也需要减掉,即-1
				right++;
			}
			return count;
		}

		/**
		 * 官方思路:
		 * 总共存在两种情况
		 * 1.数组所有元素均为1,那么删掉一个元素后,连续的1就是数组的长度-1
		 * 2.数组的中间存在0,两边都是1,那边我们需要用0前面的连续1的个数+0后面连续1的个数,就是删掉一个0后连续1的个数
		 * 所以,定义pre(i)表示以第i位结尾的最长连续1的子数组长度,有
		 * 当 arr[i]=0 ; pre(i)=0;
		 * 当 arr[i]=1 ; pre(i)=pre(i+1)+1;
		 * 这样从左到右遍历一次数组即可以得到所有元素i结尾的最长连续1子数组长度
		 * 同时定义suf(i)表示以第i位开头的最长连续1子数组长度,有
		 * 当arr[i]=0; suf(i)=0;
		 * 当arr[i]=1; suf(i)=suf(i+1)+1;
		 * 这样从右到左遍历一次数组即可以得到所有元素i开头的最长连续1子数组长度
		 * <p>
		 * 那么数组中所有元素均为1,是如何满足条件的呢?
		 * 数组中所有元素均为1,间隔相加相当于对数组的长度-1
		 *
		 * @param nums
		 * @return
		 */
		public static int longestSubarrayOfficial(int[] nums) {
			int n = nums.length, count = 0;
			int[] pre = new int[n];
			pre[0] = nums[0];
			for (int i = 1; i < n; i++) {
				pre[i] = nums[i] == 0 ? 0 : pre[i - 1] + 1;
			}
			int[] suf = new int[n];
			suf[n - 1] = nums[n - 1];
			for (int i = n - 2; i >= 0; i--) {
				suf[i] = nums[i] == 0 ? 0 : suf[i + 1] + 1;
			}
			for (int i = 0; i < n; i++) {  // 枚举待删除的元素
				int preSum = i == 0 ? 0 : pre[i - 1];
				int sufSum = i == n - 1 ? 0 : suf[i + 1];
				count = Math.max(preSum + sufSum, count);
			}
			return count;
		}

		/**
		 * p0(i) 以第i位结尾的最长连续全1子数组长度
		 * arr[i]=0 ; p0(i)=0;
		 * arr[i]=1 ; p0(i)=p0(i-1)+1;
		 * p1(i) 以第i位结尾,并可以在某个位置删除一个0后,最长连续前1子数组长度
		 * arr[i]=0 ; p1(i)=p0(i-1);
		 * arr[i]=1 ; p1(i)=p1(i-1)+1;
		 * <p>
		 * 那么用两个变量既可以
		 *
		 * @param nums
		 * @return
		 */
		public static int longestSubarrayOfficialII(int[] nums) {
			int n = nums.length, p0 = 0, p1 = 0, count = 0;
			for (int i = 0; i < n; i++) {
				if (nums[i] == 0) {
					p1 = p0;
					p0 = 0;
				} else {
					++p0;
					p1++;
				}
				count = Math.max(count, p1);
			}
			if (count == n) {  // 所有都是1,那么没有删除过任何0,最后的p1,就是以数组末尾元素接口全为1最长子数组长度,即原数组长度
				--count;
			}
			return count;
		}

		public static void main(String[] args) {
			int i1 = longestSubarray(new int[]{0, 1, 1, 1, 0, 1, 1, 0, 1});
			System.out.println(i1);
		}
	}

	/**
	 * 1343. 大小为 K 且平均值大于等于阈值的子数组数目
	 * 给你一个整数数组 arr 和两个整数 k 和 threshold 。
	 * 请你返回长度为 k 且平均值大于等于 threshold 的子数组数目。
	 * 示例 1：
	 * 输入：arr = [2,2,2,2,5,5,5,8], k = 3, threshold = 4
	 * 输出：3
	 * 解释：子数组 [2,5,5],[5,5,5] 和 [5,5,8] 的平均值分别为 4，5 和 6 。其他长度为 3 的子数组的平均值都小于 4 （threshold 的值)。
	 * 示例 2：
	 * 输入：arr = [11,13,17,23,29,31,7,5,2,3], k = 3, threshold = 5
	 * 输出：6
	 * 解释：前 6 个长度为 3 的子数组平均值都大于 5 。注意平均值不是整数。
	 * 提示：
	 * 1 <= arr.length <= 105
	 * 1 <= arr[i] <= 104
	 * 1 <= k <= arr.length
	 * 0 <= threshold <= 104
	 */
	static class NumOfSubArrays {

		/**
		 * 思路:
		 * 定长滑动窗口,每次进一个元素、出一个元素,然后判断是否符合
		 * 1.直接滑动窗口,时间复杂度O(n)
		 * 2.先倒序,再滑动窗口,时间复杂度再[ O(n*Log n) - O(n + n*Log n) ]
		 *
		 * @param arr
		 * @param k
		 * @param threshold
		 * @return
		 */
		public static int numOfSubarrays(int[] arr, int k, int threshold) {
			long svg, sum = 0;
			int count = 0;
			for (int i = 0; i < k; i++) {
				sum += arr[i];
			}
			svg = sum / k;
			if (svg >= threshold) count++;
			for (int i = k; i < arr.length; i++) {
				// svg+=(arr[i]-arr[i-k])/k; 为什么不对,因为svg得出来时余数被舍弃,后面除以k时,余数又被舍弃,会出现错误
				sum += arr[i] - arr[i - k];
				svg = sum / k;
				if (svg >= threshold) count++;
			}
			return count;
		}

		public static int numOfSubarraysII(int[] arr, int k, int threshold) {
			int sum = 0, v = threshold * k;
			int count = 0;
			for (int i = 0; i < k; i++) {
				sum += arr[i];
			}
			if (sum >= v) count++;
			for (int i = k; i < arr.length; i++) {
				sum += arr[i] - arr[i - k];
				if (sum >= v) count++;
			}
			return count;
		}

		public static void main(String[] args) {
			System.out.println(numOfSubarrays(new int[]{11, 13, 17, 23, 29, 31, 7, 5, 2, 3}, 3, 5));
		}
	}

	/**
	 * 2264. 字符串中最大的 3 位相同数字
	 * 给你一个字符串 num ，表示一个大整数。如果一个整数满足下述所有条件，则认为该整数是一个 优质整数 ：
	 * 该整数是 num 的一个长度为 3 的 子字符串 。
	 * 该整数由唯一一个数字重复 3 次组成。
	 * 以字符串形式返回 最大的优质整数 。如果不存在满足要求的整数，则返回一个空字符串 "" 。
	 * 注意：
	 * 子字符串 是字符串中的一个连续字符序列。
	 * num 或优质整数中可能存在 前导零 。
	 * 示例 1：
	 * 输入：num = "6777133339"
	 * 输出："777"
	 * 解释：num 中存在两个优质整数："777" 和 "333" 。
	 * "777" 是最大的那个，所以返回 "777" 。
	 * 示例 2：
	 * 输入：num = "2300019"
	 * 输出："000"
	 * 解释："000" 是唯一一个优质整数。
	 * 示例 3：
	 * 输入：num = "42352338"
	 * 输出：""
	 * 解释：不存在长度为 3 且仅由一个唯一数字组成的整数。因此，不存在优质整数。
	 * 提示：
	 * 3 <= num.length <= 1000
	 * num 仅由数字（0 - 9）组成
	 */
	static class LargestGoodInteger {

		/**
		 * 思路:
		 * 定长滑动窗口,向右滑动时寻找3叠整数
		 *
		 * @param num
		 * @return
		 */
		public static String largestGoodInteger(String num) {
			char[] arr = num.toCharArray();
			boolean flag = false;  // flag表示定长3的窗口中,后两个元素是否相同
			int max = -1;
			if (arr[1] == arr[2]) {
				flag = true;
				if (arr[0] == arr[1]) {
					max = Math.max(max, arr[1] - 48);
				}
			}
			for (int i = 3; i < arr.length; i++) {
				if (flag) {  // 后两个字符不一样,直接往后滑动
					if (arr[i] == arr[i - 1]) {
						max = Math.max(max, arr[i] - 48);
					}
				}
				flag = arr[i] == arr[i - 1];
			}
			if (max == -1) return "";
			String s = String.valueOf(max);
			StringBuilder sb = new StringBuilder();
			return sb.append(s).append(s).append(s).toString();
		}


		public static String largestGoodIntegerII(String num) {
			char[] arr = num.toCharArray();
			boolean flag = arr[1] == arr[2];  // flag表示定长3的窗口中,后两个元素是否相同
			int max = -1;
			if (arr[0] == arr[1] && flag) {
				max = Integer.parseInt(num.substring(0, 3));
			}
			for (int i = 3; i < arr.length; i++) {
				boolean b = arr[i] == arr[i - 1];
				if (b && flag) {
					max = Math.max(Integer.parseInt(num.substring(i - 2, i + 1)), max);
				}
				flag = b;
			}
			if (max == -1) {
				return "";
			} else if (max == 0) {
				return "000";
			} else {
				return String.valueOf(max);
			}
		}

		public static void main(String[] args) {
			System.out.println(largestGoodIntegerII("6777133339"));
		}
	}

	/**
	 * 2090. 半径为 k 的子数组平均值
	 * 给你一个下标从 0 开始的数组 nums ，数组中有 n 个整数，另给你一个整数 k 。
	 * 半径为 k 的子数组平均值 是指：nums 中一个以下标 i 为 中心 且 半径 为 k 的子数组中所有元素的平均值，即下标在 i - k 和 i + k 范围（含 i - k 和 i + k）内所有元素的平均值。如果在下标 i 前或后不足 k 个元素，那么 半径为 k 的子数组平均值 是 -1 。
	 * 构建并返回一个长度为 n 的数组 avgs ，其中 avgs[i] 是以下标 i 为中心的子数组的 半径为 k 的子数组平均值 。
	 * x 个元素的 平均值 是 x 个元素相加之和除以 x ，此时使用截断式 整数除法 ，即需要去掉结果的小数部分。
	 * 例如，四个元素 2、3、1 和 5 的平均值是 (2 + 3 + 1 + 5) / 4 = 11 / 4 = 2.75，截断后得到 2 。
	 * 示例 1：
	 * 输入：nums = [7,4,3,9,1,8,5,2,6], k = 3
	 * 输出：[-1,-1,-1,5,4,4,-1,-1,-1]
	 * 解释：
	 * - avg[0]、avg[1] 和 avg[2] 是 -1 ，因为在这几个下标前的元素数量都不足 k 个。
	 * - 中心为下标 3 且半径为 3 的子数组的元素总和是：7 + 4 + 3 + 9 + 1 + 8 + 5 = 37 。
	 * 使用截断式 整数除法，avg[3] = 37 / 7 = 5 。
	 * - 中心为下标 4 的子数组，avg[4] = (4 + 3 + 9 + 1 + 8 + 5 + 2) / 7 = 4 。
	 * - 中心为下标 5 的子数组，avg[5] = (3 + 9 + 1 + 8 + 5 + 2 + 6) / 7 = 4 。
	 * - avg[6]、avg[7] 和 avg[8] 是 -1 ，因为在这几个下标后的元素数量都不足 k 个。
	 * 示例 2：
	 * 输入：nums = [100000], k = 0
	 * 输出：[100000]
	 * 解释：
	 * - 中心为下标 0 且半径 0 的子数组的元素总和是：100000 。
	 * avg[0] = 100000 / 1 = 100000 。
	 * 示例 3：
	 * 输入：nums = [8], k = 100000
	 * 输出：[-1]
	 * 解释：
	 * - avg[0] 是 -1 ，因为在下标 0 前后的元素数量均不足 k 。
	 * 提示：
	 * n == nums.length
	 * 1 <= n <= 105
	 * 0 <= nums[i], k <= 105
	 */
	static class GetAverages {

		/**
		 * 先找到k的取值范围为[k,l-k-1]
		 * 然后滑动定长窗口,计算窗口内元素的总和
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static int[] getAverages(int[] nums, int k) {
			int n = nums.length;
			long sum = 0;
			int[] res = new int[n];
			if (k * 2 >= n) {  // 说明所有的值都是负一
				Arrays.fill(res, -1);
				return res;
			}
			for (int i = 0; i < k; i++) {
				res[i] = -1;
				sum += nums[i];
			}
			for (int i = k; i <= 2 * k; i++) {
				sum += nums[i];
			}
			res[k] = (int) (sum / (2 * k + 1));
			for (int i = k + 1; i < n - k; i++) {
				sum += nums[i + k] - nums[i - k - 1];
				res[i] = (int) (sum / (2 * k + 1));
			}
			for (int i = n - k; i < n; i++) {
				res[i] = -1;
			}
			return res;
		}

		public static void main(String[] args) {
			int[] averages = getAverages(new int[]{2, 1}, 1);
			System.out.println(Arrays.toString(averages));
		}
	}

	/**
	 * 2379. 得到 K 个黑块的最少涂色次数
	 * 给你一个长度为 n 下标从 0 开始的字符串 blocks ，blocks[i] 要么是 'W' 要么是 'B' ，表示第 i 块的颜色。字符 'W' 和 'B' 分别表示白色和黑色。
	 * 给你一个整数 k ，表示想要 连续 黑色块的数目。
	 * 每一次操作中，你可以选择一个白色块将它 涂成 黑色块。
	 * 请你返回至少出现 一次 连续 k 个黑色块的 最少 操作次数。
	 * 示例 1：
	 * 输入：blocks = "WBBWWBBWBW", k = 7
	 * 输出：3
	 * 解释：
	 * 一种得到 7 个连续黑色块的方法是把第 0 ，3 和 4 个块涂成黑色。
	 * 得到 blocks = "BBBBBBBWBW" 。
	 * 可以证明无法用少于 3 次操作得到 7 个连续的黑块。
	 * 所以我们返回 3 。
	 * 示例 2：
	 * 输入：blocks = "WBWBBBW", k = 2
	 * 输出：0
	 * 解释：
	 * 不需要任何操作，因为已经有 2 个连续的黑块。
	 * 所以我们返回 0 。
	 * 提示：
	 * n == blocks.length
	 * 1 <= n <= 100
	 * blocks[i] 要么是 'W' ，要么是 'B' 。
	 * 1 <= k <= n
	 */
	static class MinimumRecolors {

		/**
		 * 思路:
		 * 定长滑动窗口,长度为7
		 * 初始化时,计算窗口中'W'字母的数量,即需要涂色的个数,然后滑动计算'W'的数量,找到最小值
		 *
		 * @param blocks
		 * @param k
		 * @return
		 */
		public static int minimumRecolors(String blocks, int k) {
			int count = 0, min;
			for (int i = 0; i < k; i++) {
				if (blocks.charAt(i) == 'W') count++;
			}
			min = count;
			for (int i = k; i < blocks.length(); i++) {
				if (blocks.charAt(i) == 'W') {
					count++;
				}
				if (blocks.charAt(i - k) == 'W') {
					count--;
				}
				min = Math.min(count, min);
			}
			return min;
		}

		public static void main(String[] args) {
			System.out.println(minimumRecolors("WBWBBBW", 2));
		}
	}

	/**
	 * 1052. 爱生气的书店老板
	 * 有一个书店老板，他的书店开了 n 分钟。每分钟都有一些顾客进入这家商店。给定一个长度为 n 的整数数组 customers ，其中 customers[i] 是在第 i 分钟开始时进入商店的顾客数量，所有这些顾客在第 i 分钟结束后离开。
	 * 在某些分钟内，书店老板会生气。 如果书店老板在第 i 分钟生气，那么 grumpy[i] = 1，否则 grumpy[i] = 0。
	 * 当书店老板生气时，那一分钟的顾客就会不满意，若老板不生气则顾客是满意的。
	 * 书店老板知道一个秘密技巧，能抑制自己的情绪，可以让自己连续 minutes 分钟不生气，但却只能使用一次。
	 * 请你返回 这一天营业下来，最多有多少客户能够感到满意 。
	 * 示例 1：
	 * 输入：customers = [1,0,1,2,1,1,7,5], grumpy = [0,1,0,1,0,1,0,1], minutes = 3
	 * 输出：16
	 * 解释：书店老板在最后 3 分钟保持冷静。
	 * 感到满意的最大客户数量 = 1 + 1 + 1 + 1 + 7 + 5 = 16.
	 * 示例 2：
	 * 输入：customers = [1], grumpy = [0], minutes = 1
	 * 输出：1
	 * 提示：
	 * n == customers.length == grumpy.length
	 * 1 <= minutes <= n <= 2 * 104
	 * 0 <= customers[i] <= 1000
	 * grumpy[i] == 0 or 1
	 */
	static class MaxSatisfied {

		/**
		 * 思路:
		 * 定长滑动窗口
		 * 1.先预算每分钟满意的客户数量,然后计算以i开始到结尾的客户满意数量
		 * 2.滑动窗口长度为k,窗口内所客户都是满意的,所以直接计算其和;窗口后面的满意数量通过1中计算
		 * 3.窗口每次向右滑动一位,窗口左边界p 若grumpy[p]=1 则和-customers[p];
		 * 窗口右边界q 若grumpy[q+1]=0 则和+customer[q+1]
		 *
		 * @param customers
		 * @param grumpy
		 * @param minutes
		 * @return
		 */
		public static int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
			int n = customers.length;
			int[] sum = new int[n + 1];
			if (grumpy[n - 1] == 0) sum[n - 1] = customers[n - 1];
			for (int i = n - 2; i >= 0; --i) {
				sum[i] = sum[i + 1];
				if (grumpy[i] == 0) {   // 不生气客户满意
					sum[i] += customers[i];
				}
			}
			int count = 0, max;
			for (int i = 0; i < minutes; i++) {
				count += customers[i];
			}
			max = count + sum[minutes];
			for (int i = minutes; i < n; i++) {
				if (grumpy[i - minutes] == 1) {
					count -= customers[i - minutes];
				}
				count += customers[i];
				max = Math.max(count + sum[i + 1], max);
			}
			return max;
		}

		public static int maxSatisfiedII(int[] customers, int[] grumpy, int minutes) {
			int n = customers.length;
			int[] sum = new int[n + 1];
			if (grumpy[n - 1] == 0) sum[n - 1] = customers[n - 1];
			for (int i = n - 2; i >= 0; --i) {
				sum[i] = sum[i + 1];
				if (grumpy[i] == 0) {   // 不生气客户满意
					sum[i] += customers[i];
				}
			}
			int count = 0, max;
			for (int i = 0; i < minutes; i++) {
				count += customers[i];
			}
			max = count + sum[minutes];
			for (int i = minutes; i < n; i++) {
				count += customers[i] - customers[i - minutes] * grumpy[i - minutes];
				max = Math.max(count + sum[i + 1], max);
			}
			return max;
		}

		/**
		 * 官方思路,先根据老板是否生气计算总和
		 * 然后滑动窗口时,计算窗口内增加了多少,跟之前的总和加起来就是满意的客户数量
		 *
		 * @param customers
		 * @param grumpy
		 * @param minutes
		 * @return
		 */
		public static int maxSatisfiedOfficial(int[] customers, int[] grumpy, int minutes) {
			int total = 0, n = customers.length;
			for (int i = 0; i < n; i++) {
				if (grumpy[i] == 0) {
					total += customers[i];
				}
			}
			int increase = 0;
			for (int i = 0; i < minutes; i++) {
				increase += customers[i] * grumpy[i];
			}
			int maxIncrease = increase;
			for (int i = minutes; i < n; i++) {
				increase = increase - customers[i - minutes] * grumpy[i - minutes] + customers[i] * grumpy[i];
				maxIncrease = Math.max(increase, maxIncrease);
			}
			return maxIncrease + total;
		}

		public static void main(String[] args) {
			System.out.println(maxSatisfiedOfficial(new int[]{1, 0, 1, 2, 1, 1, 7, 5}, new int[]{0, 1, 0, 1, 0, 1, 0, 1}, 3));
			System.out.println(maxSatisfiedOfficial(new int[]{1}, new int[]{0}, 1));
		}
	}

	/**
	 * 1461. 检查一个字符串是否包含所有长度为 K 的二进制子串
	 * 给你一个二进制字符串 s 和一个整数 k 。如果所有长度为 k 的二进制字符串都是 s 的子串，请返回 true ，否则请返回 false 。
	 * 示例 1：
	 * 输入：s = "00110110", k = 2
	 * 输出：true
	 * 解释：长度为 2 的二进制串包括 "00"，"01"，"10" 和 "11"。它们分别是 s 中下标为 0，1，3，2 开始的长度为 2 的子串。
	 * 示例 2：
	 * 输入：s = "0110", k = 1
	 * 输出：true
	 * 解释：长度为 1 的二进制串包括 "0" 和 "1"，显然它们都是 s 的子串。
	 * 示例 3：
	 * 输入：s = "0110", k = 2
	 * 输出：false
	 * 解释：长度为 2 的二进制串 "00" 没有出现在 s 中。
	 * 提示：
	 * 1 <= s.length <= 5 * 105
	 * s[i] 不是'0' 就是 '1'
	 * 1 <= k <= 20
	 */
	static class HasAllCodes {

		/**
		 * 思路:
		 * 定长滑动窗口
		 * 1.关键在于,长度为k的二进制字符串需要全部遍历出来,可以通过回溯的方法遍历,然后转成字符串作为hashmap的key存放起来
		 * 2.滑动窗口时,每次在hashmap中查找,如果存在则删除掉
		 * 时间复杂度： 回溯 O(k*log k); hashmap的put和delete 都是O(1); 滑动窗口O(n)
		 *
		 * @param s
		 * @param k
		 * @return
		 */
		public static boolean hasAllCodes(String s, int k) {
			if (s.length() < k) return false;
			int[] arr = new int[]{0, 1};
			List<int[]> list = new ArrayList<>(k);
			for (int i = 0; i < k; i++) {
				list.add(arr);
			}
			map = new HashMap<>();
			n = k;
			// 通过回溯遍历出k位的所有二进制字符串
			dfs(list, 0, new StringBuilder());
			// 如果回溯得到的组合数量大于s字符串能够遍历出的组合数量,返回false
			if (map.size() > s.length() - k + 1) return false;
			// 滑动窗口
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < k; i++) {
				sb.append(s.charAt(i));
			}
			map.remove(sb.toString(), 1);
			for (int i = k; i < s.length(); i++) {
				sb.deleteCharAt(0);
				sb.append(s.charAt(i));
				map.remove(sb.toString(), 1);
				if (map.isEmpty()) return true;
			}
			return map.isEmpty();
		}

		private static HashMap<String, Integer> map;
		private static int n;

		/**
		 * dfs遍历出所有的二进制字符串并保存在HashMap中
		 *
		 * @param list 所有遍历的组合
		 * @param i    遍历到第ige数字
		 * @param path 路径
		 */
		private static void dfs(List<int[]> list, int i, StringBuilder path) {
			if (path.length() == n) {
				map.put(path.toString(), 1);
				return;
			}
			int[] arr = list.get(i);  // 遍历到第几位数
			for (int j = 0; j < 2; j++) {
				path.append(arr[j]);
				dfs(list, i + 1, path);
				path.deleteCharAt(i);
			}
		}

		/**
		 * 看到官方解题思路之后,发现本题只需要判断s的子串是否包含k个二进制位的所有组合,而不需要判断包含多少个.
		 * 然后有个关键点在于k位二进制位的所有组合的数量为2^k
		 * 只需要判断所有不重复的子串数量是否等于2^k即可
		 *
		 * @param s
		 * @param k
		 * @return
		 */
		public static boolean hasAllCodesII(String s, int k) {
			int count = (int) Math.pow(2, k);
			if (s.length() <= k || s.length() - k + 1 < count) return false;
			Map<String, Integer> map = new HashMap<>(count); // 避免hashmap扩容
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < k; i++) {
				sb.append(s.charAt(i));
			}
			map.put(sb.toString(), 1);
			for (int i = k; i < s.length(); i++) {
				sb.deleteCharAt(0);
				sb.append(s.charAt(i));
				map.put(sb.toString(), 1);
				if (map.size() == count) return true;
			}
			return false;
		}

		public static boolean hasAllCodesIII(String s, int k) {
			int count = (int) Math.pow(2, k);
			if (s.length() <= k || s.length() - k + 1 < count) return false;
			HashSet<String> set = new HashSet<>(count);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < k; i++) {
				sb.append(s.charAt(i));
			}
			set.add(sb.toString());
			for (int i = k; i < s.length(); i++) {
				sb.deleteCharAt(0);
				sb.append(s.charAt(i));
				set.add(sb.toString());
				if (set.size() == count) return true;
			}
			return false;
		}

		/**
		 * 使用String.subString()切割字符串
		 *
		 * @param s
		 * @param k
		 * @return
		 */
		public static boolean hasAllCodesIV(String s, int k) {
			if (s.length() <= k) return false;
			int count = 1 << k;
			if (s.length() - k + 1 < count) return false;
			HashSet<String> set = new HashSet<>(count);
			for (int i = 0; i < s.length() - k + 1; i++) {
				set.add(s.substring(i, i + k));
				if (set.size() == count) return true;
			}
			return false;
		}

		// todo 滑动窗口时,窗口内k个字符可以转换为二进制数字,每个数字的值都不相同;该如何做呢??


		public static void main(String[] args) {
			System.out.println(hasAllCodesIV("00110", 2));
		}
	}

	/**
	 * 2841. 几乎唯一子数组的最大和
	 * 给你一个整数数组 nums 和两个正整数 m 和 k 。
	 * 请你返回 nums 中长度为 k 的 几乎唯一 子数组的 最大和 ，如果不存在几乎唯一子数组，请你返回 0 。
	 * 如果 nums 的一个子数组有至少 m 个互不相同的元素，我们称它是 几乎唯一 子数组。
	 * 子数组指的是一个数组中一段连续 非空 的元素序列。
	 * 示例 1：
	 * 输入：nums = [2,6,7,3,1,7], m = 3, k = 4
	 * 输出：18
	 * 解释：总共有 3 个长度为 k = 4 的几乎唯一子数组。分别为 [2, 6, 7, 3] ，[6, 7, 3, 1] 和 [7, 3, 1, 7] 。这些子数组中，和最大的是 [2, 6, 7, 3] ，和为 18 。
	 * 示例 2：
	 * 输入：nums = [5,9,9,2,4,5,4], m = 1, k = 3
	 * 输出：23
	 * 解释：总共有 5 个长度为 k = 3 的几乎唯一子数组。分别为 [5, 9, 9] ，[9, 9, 2] ，[9, 2, 4] ，[2, 4, 5] 和 [4, 5, 4] 。这些子数组中，和最大的是 [5, 9, 9]
	 * ，和为 23 。
	 * 示例 3：
	 * 输入：nums = [1,2,1,2,1,2,1], m = 3, k = 3
	 * 输出：0
	 * 解释：输入数组中不存在长度为 k = 3 的子数组含有至少  m = 3 个互不相同元素的子数组。所以不存在几乎唯一子数组，最大和为 0 。
	 * 提示：
	 * 1 <= nums.length <= 2 * 104
	 * 1 <= m <= k <= nums.length
	 * 1 <= nums[i] <= 109
	 */
	static class MaxSum {

		/**
		 * 思路:
		 * 定长滑动窗口
		 * 本题的关键点是,需要判断窗体内,长度为k的元素,计算不同的元素个数
		 * 用hashset不行,需要记录元素的个数,在移除的时候,如果有多个相同的元素则不移除
		 *
		 * @param nums
		 * @param m
		 * @param k
		 * @return
		 */
		public static long maxSum(List<Integer> nums, int m, int k) {
			HashMap<Integer, Integer> map = new HashMap<>(m);
			long sum = 0, max = 0;
			for (int i = 0; i < k; i++) {
				sum += nums.get(i);
				map.put(nums.get(i), map.getOrDefault(nums.get(i), 0) + 1);
			}
			if (map.size() >= m) {
				max = sum;
			}
			for (int i = k; i < nums.size(); i++) {
				sum += nums.get(i) - nums.get(i - k);
				map.put(nums.get(i), map.getOrDefault(nums.get(i), 0) + 1);  // 增加
				Integer v = map.put(nums.get(i - k), map.get(nums.get(i - k)) - 1);
				if (v <= 1) {
					map.remove(nums.get(i - k));
				}
				if (map.size() >= m) {
					max = Math.max(max, sum);
				}
			}
			return max;
		}

		public static void main(String[] args) {
			List<Integer> nums = new ArrayList<>();
			nums.add(3);
			nums.add(1);
			nums.add(4);
			nums.add(3);
			System.out.println(maxSum(nums, 2, 2));
		}
	}

	/**
	 * 2461. 长度为 K 子数组中的最大和
	 * 给你一个整数数组 nums 和一个整数 k 。请你从 nums 中满足下述条件的全部子数组中找出最大子数组和：
	 * 子数组的长度是 k，且
	 * 子数组中的所有元素 各不相同 。
	 * 返回满足题面要求的最大子数组和。如果不存在子数组满足这些条件，返回 0 。
	 * 子数组 是数组中一段连续非空的元素序列。
	 * 示例 1：
	 * 输入：nums = [1,5,4,2,9,9,9], k = 3
	 * 输出：15
	 * 解释：nums 中长度为 3 的子数组是：
	 * - [1,5,4] 满足全部条件，和为 10 。
	 * - [5,4,2] 满足全部条件，和为 11 。
	 * - [4,2,9] 满足全部条件，和为 15 。
	 * - [2,9,9] 不满足全部条件，因为元素 9 出现重复。
	 * - [9,9,9] 不满足全部条件，因为元素 9 出现重复。
	 * 因为 15 是满足全部条件的所有子数组中的最大子数组和，所以返回 15 。
	 * 示例 2：
	 * 输入：nums = [4,4,4], k = 3
	 * 输出：0
	 * 解释：nums 中长度为 3 的子数组是：
	 * - [4,4,4] 不满足全部条件，因为元素 4 出现重复。
	 * 因为不存在满足全部条件的子数组，所以返回 0 。
	 * 提示：
	 * 1 <= k <= nums.length <= 105
	 * 1 <= nums[i] <= 105
	 */
	static class MaximumSubarraySum {

		/**
		 * 思路:
		 * 在滑动窗口时,要记录窗口内每个元素的个数,记录是否重复
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static long maximumSubarraySum(int[] nums, int k) {
			int n = nums.length, cnt = 0; // cnt表示重复元素的个数
			long sum = 0, max = 0;
			Map<Integer, Integer> map = new HashMap<>(k);
			for (int i = 0; i < k; i++) {
				sum += nums[i];
				Integer v = map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
				if (v != null && v == 1) {
					cnt++;
				}
			}
			if (cnt == 0) {
				max = sum;
			}
			for (int i = k; i < n; i++) {
				if (nums[i] != nums[i - k]) {  // 出窗口和入窗口元素不一样
					sum += nums[i] - nums[i - k];
					Integer v = map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
					if (v != null && v == 1) {  // nums[k]从1个变成2个,重复元素多一个
						cnt++;
					}
					Integer v1 = map.put(nums[i - k], map.get(nums[i - k]) - 1);
					if (v1 != null && v1 == 2) { // nums[k-i]从2个变成1个,重复元素少一个
						cnt--;
					}
				}
				if (cnt == 0) max = Math.max(max, sum);
			}
			return max;
		}

		/**
		 * 用数组替代hashmap
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static long maximumSubarraySumII(int[] nums, int k) {
			int n = nums.length, cnt = 0; // cnt表示重复元素的个数
			long sum = 0, max = 0;
			int[] arr = new int[10_0001];
			for (int i = 0; i < k; i++) {
				sum += nums[i];
				if (arr[nums[i]]++ == 1) { // 数量从1变成2
					cnt++;
				}
			}
			if (cnt == 0) {
				max = sum;
			}
			for (int i = k; i < n; i++) {
				if (nums[i] != nums[i - k]) {  // 出窗口和入窗口元素不一样
					sum += nums[i] - nums[i - k];
					if (arr[nums[i]]++ == 1) cnt++;
					if (arr[nums[i - k]]-- == 2) cnt--;
				}
				if (cnt == 0) max = Math.max(max, sum);
			}
			return max;
		}

		public static void main(String[] args) {
			System.out.println(maximumSubarraySumII(new int[]{1, 5, 4, 2, 9, 9, 9, 9}, 3));
		}
	}

	/**
	 * 1423. 可获得的最大点数
	 * 几张卡牌 排成一行，每张卡牌都有一个对应的点数。点数由整数数组 cardPoints 给出。
	 * 每次行动，你可以从行的开头或者末尾拿一张卡牌，最终你必须正好拿 k 张卡牌。
	 * 你的点数就是你拿到手中的所有卡牌的点数之和。
	 * 给你一个整数数组 cardPoints 和整数 k，请你返回可以获得的最大点数。
	 * 示例 1：
	 * 输入：cardPoints = [1,2,3,4,5,6,1], k = 3
	 * 输出：12
	 * 解释：第一次行动，不管拿哪张牌，你的点数总是 1 。但是，先拿最右边的卡牌将会最大化你的可获得点数。最优策略是拿右边的三张牌，最终点数为 1 + 6 + 5 = 12 。
	 * 示例 2：
	 * 输入：cardPoints = [2,2,2], k = 2
	 * 输出：4
	 * 解释：无论你拿起哪两张卡牌，可获得的点数总是 4 。
	 * 示例 3：
	 * 输入：cardPoints = [9,7,7,9,7,7,9], k = 7
	 * 输出：55
	 * 解释：你必须拿起所有卡牌，可以获得的点数为所有卡牌的点数之和。
	 * 示例 4：
	 * 输入：cardPoints = [1,1000,1], k = 1
	 * 输出：1
	 * 解释：你无法拿到中间那张卡牌，所以可以获得的最大点数为 1 。
	 * 示例 5：
	 * 输入：cardPoints = [1,79,80,1,1,1,200,1], k = 3
	 * 输出：202
	 * 提示：
	 * 1 <= cardPoints.length <= 10^5
	 * 1 <= cardPoints[i] <= 10^4
	 * 1 <= k <= cardPoints.length
	 */
	static class MaxScore {

		/**
		 * 思路：
		 * 本题如果用模拟的思路去做,需要用到递归,每次选择第一个或者最后一个元素,然后计算和最大
		 * 但是如果想到滑动窗口呢? 窗口的大小固定为l-k;因为从头或尾拿出元素,肯定是连续的,所以滑动窗口满足条件
		 *
		 * @param cardPoints
		 * @param k
		 * @return
		 */
		public static int maxScore(int[] cardPoints, int k) {
			int n = cardPoints.length, l = n - k, min = 0;
			int sum = 0, v = 0;
			for (int i = 0; i < n; i++) {
				sum += cardPoints[i];
			}
			for (int i = 0; i < l; i++) {
				v += cardPoints[i];
			}
			min = v;
			for (int i = l; i < n; i++) {
				v += cardPoints[i] - cardPoints[i - l];
				min = Math.min(v, min);
			}
			return sum - min;
		}

		public static void main(String[] args) {
			System.out.println(maxScore(new int[]{1, 2, 3, 4, 5, 6, 1}, 3));
		}
	}

	/**
	 * 1652. 拆炸弹
	 * 你有一个炸弹需要拆除，时间紧迫！你的情报员会给你一个长度为 n 的 循环 数组 code 以及一个密钥 k 。
	 * 为了获得正确的密码，你需要替换掉每一个数字。所有数字会 同时 被替换。
	 * 如果 k > 0 ，将第 i 个数字用 接下来 k 个数字之和替换。
	 * 如果 k < 0 ，将第 i 个数字用 之前 k 个数字之和替换。
	 * 如果 k == 0 ，将第 i 个数字用 0 替换。
	 * 由于 code 是循环的， code[n-1] 下一个元素是 code[0] ，且 code[0] 前一个元素是 code[n-1] 。
	 * 给你 循环 数组 code 和整数密钥 k ，请你返回解密后的结果来拆除炸弹！
	 * 示例 1：
	 * 输入：code = [5,7,1,4], k = 3
	 * 输出：[12,10,16,13]
	 * 解释：每个数字都被接下来 3 个数字之和替换。解密后的密码为 [7+1+4, 1+4+5, 4+5+7, 5+7+1]。注意到数组是循环连接的。
	 * 示例 2：
	 * 输入：code = [1,2,3,4], k = 0
	 * 输出：[0,0,0,0]
	 * 解释：当 k 为 0 时，所有数字都被 0 替换。
	 * 示例 3：
	 * 输入：code = [2,4,9,3], k = -2
	 * 输出：[12,5,6,13]
	 * 解释：解密后的密码为 [3+9, 2+3, 4+2, 9+4] 。注意到数组是循环连接的。如果 k 是负数，那么和为 之前 的数字。
	 * 提示：
	 * n == code.length
	 * 1 <= n <= 100
	 * 1 <= code[i] <= 100
	 * -(n - 1) <= k <= n - 1
	 */
	static class Decrypt {

		/**
		 * 循环数组的处理,将单个数组填充为两个数数组,然后根据k的正负情况,来判断滑动窗口的左右边界
		 * 1.当k>0时,l=1,r=k
		 * 2.当k<0时,l=n-k,r=n-1
		 * 确定窗口的左右边界之后,向右滑动n次即可
		 * @param code
		 * @param k
		 * @return
		 */
		public static int[] decrypt(int[] code, int k) {
			int n = code.length;
			int[] res = new int[n];
			if (k == 0) return res;
			int[] arr = new int[2 * n];
			System.arraycopy(code, 0, arr, 0, n);
			System.arraycopy(code, 0, arr, n, n);
			int l = k > 0 ? 1 : n + k;
			int r = k > 0 ? k : n - 1;
			int sum = 0;
			for (int i = l; i <= r; i++) {
				sum += arr[i];
			}
			res[0] = sum;
			for (int i = 1; i < n; i++) {
				sum += arr[++r] - arr[l++];
				res[i] = sum;
			}
			return res;
		}

		public static void main(String[] args) {
			System.out.println(Arrays.toString(decrypt(new int[]{2, 4, 9, 3}, -2)));
		}
	}

	/**
	 * 1297. 子串的最大出现次数
	 * 给你一个字符串 s ，请你返回满足以下条件且出现次数最大的 任意 子串的出现次数：
	 * 子串中不同字母的数目必须小于等于 maxLetters 。
	 * 子串的长度必须大于等于 minSize 且小于等于 maxSize 。
	 * 示例 1：
	 * 输入：s = "aababcaab", maxLetters = 2, minSize = 3, maxSize = 4
	 * 输出：2
	 * 解释：子串 "aab" 在原字符串中出现了 2 次。
	 * 它满足所有的要求：2 个不同的字母，长度为 3 （在 minSize 和 maxSize 范围内）。
	 * 示例 2：
	 * 输入：s = "aaaa", maxLetters = 1, minSize = 3, maxSize = 3
	 * 输出：2
	 * 解释：子串 "aaa" 在原字符串中出现了 2 次，且它们有重叠部分。
	 * 示例 3：
	 * 输入：s = "aabcabcab", maxLetters = 2, minSize = 2, maxSize = 3
	 * 输出：3
	 * 示例 4：
	 * 输入：s = "abcde", maxLetters = 2, minSize = 3, maxSize = 3
	 * 输出：0
	 * 提示：
	 * 1 <= s.length <= 10^5
	 * 1 <= maxLetters <= 26
	 * 1 <= minSize <= maxSize <= min(26, s.length)
	 * s 只包含小写英文字母。
	 */
	static class MaxFreq {

		/**
		 * 思路:不定长滑动窗口 左右边界的移动如何控制呢?
		 * 1.窗口内元素的数量 minSize < c < Maxsize 在范围内时,left++; c > maxSize时,left++;
		 * 2.其他情况c< minSize c不可能大于maxSize,因为left随时在移动
		 * 3.滑动的过程中记录不同字母的个数cnt
		 *
		 * @param s
		 * @param maxLetters
		 * @param minSize
		 * @param maxSize
		 * @return
		 */
		public static int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
			int n = s.length(), cnt = 0;
			Map<String, Integer> map = new HashMap<>();
			StringBuilder sb = new StringBuilder();
			int[] count = new int[26];
			for (int i = 0; i < minSize; i++) {
				sb.append(s.charAt(i));
				if (count[s.charAt(i) - 'a']++ == 0) {
					cnt++;
				}
			}
			if (cnt <= maxLetters) map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + 1);
			int l = 0;
			if (maxSize == minSize) {  // 定长滑动窗口,需要左右边界同时移动
				for (int r = minSize; r < n; r++) {
					sb.append(s.charAt(r));
					if (count[s.charAt(r) - 'a']++ == 0) {
						cnt++;
					}
					sb.deleteCharAt(0);
					if (count[s.charAt(l) - 'a']-- == 1) {
						cnt--;
					}
					l++;
					if (cnt <= maxLetters) {
						map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + 1);
					}
				}
			} else {
				for (int r = minSize; r < n; r++) {
					// 进入for循环,窗体右侧右移一位
					if (r - l + 1 <= maxSize) {
						sb.append(s.charAt(r));
						if (count[s.charAt(r) - 'a']++ == 0) {
							cnt++;
						}
						if (cnt <= maxLetters) {
							map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + 1);
						}
					}
					while (r - l + 1 > minSize) {
						sb.deleteCharAt(0);
						if (count[s.charAt(l) - 'a']-- == 1) {
							cnt--;
						}
						if (cnt <= maxLetters) {
							map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + 1);
						}
						l++;
					}
				}
			}
			return map.values().stream().max(Comparator.comparingInt(x -> x)).orElse(0);
		}

		/**
		 * 如果用一个逻辑,保证定长窗口和不定长窗口都能满足条件呢?
		 * 1.首先只要r < n 则一定可以右移
		 * 2.在循环中: 当窗口长度s > minSize 或者 不同字母数量c < maxLetters,则l左移
		 * 3.当循环2退出时,就得到一个满足情况的场景,放入map中进行计数(子串长度为maxSize的肯定覆盖长度为minSize,所以最大的子串数量
		 * 肯定在minSize中得到)
		 * <p>
		 * 这个是解决不定长窗口的模版方法
		 *
		 * @param s
		 * @param maxLetters
		 * @param minSize
		 * @param maxSize
		 * @return
		 */
		public static int maxFreqII(String s, int maxLetters, int minSize, int maxSize) {
			int n = s.length();
			Map<String, Integer> map = new HashMap<>();
			StringBuilder sb = new StringBuilder();
			int[] count = new int[26];
			int l = 0, r = 0, cnt = 0;
			while (r < n) {
				sb.append(s.charAt(r));
				if (count[s.charAt(r) - 'a']++ == 0) {
					cnt++;
				}
				r++;
				int len = r - l; // 窗口长度,处理r之后,r++ r已经右移了,但窗口还是r右移之前的长度
				while (cnt > maxLetters || len > minSize) {
					sb.deleteCharAt(0);
					if (count[s.charAt(l) - 'a']-- == 1) {
						cnt--;
					}
					l++;
					len--;
				}
				if (cnt <= maxLetters && len == minSize) {
					map.put(sb.toString(), map.getOrDefault(sb.toString(), 0) + 1);
				}
			}
			int res = 0;
			for (String key : map.keySet()) {
				res = Math.max(res, map.get(key));
			}
			return res;
		}

		public static void main(String[] args) {
			System.out.println(maxFreqII("babcbceccaaacddbdaedbadcddcbdbcbaaddbcabcccbacebda", 1, 1, 1));
			System.out.println(Integer.toBinaryString((int) Math.pow(10, 9)));
		}
	}

	/**
	 * 2134. 最少交换次数来组合所有的 1 II
	 * 交换 定义为选中一个数组中的两个 互不相同 的位置并交换二者的值。
	 * 环形 数组是一个数组，可以认为 第一个 元素和 最后一个 元素 相邻 。
	 * 给你一个 二进制环形 数组 nums ，返回在 任意位置 将数组中的所有 1 聚集在一起需要的最少交换次数。
	 * 示例 1：
	 * 输入：nums = [0,1,0,1,1,0,0]
	 * 输出：1
	 * 解释：这里列出一些能够将所有 1 聚集在一起的方案：
	 * [0,0,1,1,1,0,0] 交换 1 次。
	 * [0,1,1,1,0,0,0] 交换 1 次。
	 * [1,1,0,0,0,0,1] 交换 2 次（利用数组的环形特性）。
	 * 无法在交换 0 次的情况下将数组中的所有 1 聚集在一起。
	 * 因此，需要的最少交换次数为 1 。
	 * 示例 2：
	 * 输入：nums = [0,1,1,1,0,0,1,1,0]
	 * 输出：2
	 * 解释：这里列出一些能够将所有 1 聚集在一起的方案：
	 * [1,1,1,0,0,0,0,1,1] 交换 2 次（利用数组的环形特性）。
	 * [1,1,1,1,1,0,0,0,0] 交换 2 次。
	 * 无法在交换 0 次或 1 次的情况下将数组中的所有 1 聚集在一起。
	 * 因此，需要的最少交换次数为 2 。
	 * 示例 3：
	 * 输入：nums = [1,1,0,0,1]
	 * 输出：0
	 * 解释：得益于数组的环形特性，所有的 1 已经聚集在一起。
	 * 因此，需要的最少交换次数为 0 。
	 * 提示：
	 * 1 <= nums.length <= 105
	 * nums[i] 为 0 或者 1
	 */
	static class MinSwaps {

		/**
		 * 思路：
		 * 环形数组,一般将数组拼接一个完全相同的
		 * 固定滑动窗口长度为数组中1的个数
		 *
		 * @param nums
		 * @return
		 */
		public static int minSwaps(int[] nums) {
			int n = nums.length, k = 0;
			for (int i = 0; i < n; i++) {
				if (nums[i] == 1) k++;
			}
			int[] arr = new int[2 * n];
			System.arraycopy(nums, 0, arr, 0, n);
			System.arraycopy(nums, 0, arr, n, n);
			int res = 0, cnt = 0; // 记录0的数量
			for (int i = 0; i < k; i++) {
				if (arr[i] == 0) {
					cnt++;
				}
			}
			res = cnt;
			for (int left = 1; left < n; left++) {
				if (arr[left - 1] == 0) {
					cnt--;
				}
				if (arr[left + k - 1] == 0) {
					cnt++;

				}
				res = Math.min(res, cnt);
			}
			return res;
		}

		/**
		 * 当然,也可以不用拓展数组的方式,当idx>=n时,idx=idx%n
		 *
		 * @param nums
		 * @return
		 */
		public static int minSwapsII(int[] nums) {
			int n = nums.length, k = 0;
			for (int i = 0; i < n; i++) {
				k += nums[i];
			}
			int res = 0, cnt = 0; // 记录1的数量
			for (int i = 0; i < k; i++) {
				cnt += nums[i];
			}
			res = cnt;
			for (int left = 1; left < n; left++) {
				cnt -= nums[left - 1];
				cnt += nums[(left + k - 1) % n];
				res = Math.max(res, cnt);
			}
			return k - res;
		}

		public static void main(String[] args) {
			System.out.println(minSwapsII(new int[]{0, 1, 0, 1, 1, 0, 0}));
		}
	}

	/**
	 * 2653. 滑动子数组的美丽值
	 * 给你一个长度为 n 的整数数组 nums ，请你求出每个长度为 k 的子数组的 美丽值 。
	 * 一个子数组的 美丽值 定义为：如果子数组中第 x 小整数 是 负数 ，那么美丽值为第 x 小的数，否则美丽值为 0 。
	 * 请你返回一个包含 n - k + 1 个整数的数组，依次 表示数组中从第一个下标开始，每个长度为 k 的子数组的 美丽值 。
	 * 子数组指的是数组中一段连续 非空 的元素序列。
	 * 示例 1：
	 * 输入：nums = [1,-1,-3,-2,3], k = 3, x = 2
	 * 输出：[-1,-2,-2]
	 * 解释：总共有 3 个 k = 3 的子数组。
	 * 第一个子数组是 [1, -1, -3] ，第二小的数是负数 -1 。
	 * 第二个子数组是 [-1, -3, -2] ，第二小的数是负数 -2 。
	 * 第三个子数组是 [-3, -2, 3] ，第二小的数是负数 -2 。
	 * 示例 2：
	 * 输入：nums = [-1,-2,-3,-4,-5], k = 2, x = 2
	 * 输出：[-1,-2,-3,-4]
	 * 解释：总共有 4 个 k = 2 的子数组。
	 * [-1, -2] 中第二小的数是负数 -1 。
	 * [-2, -3] 中第二小的数是负数 -2 。
	 * [-3, -4] 中第二小的数是负数 -3 。
	 * [-4, -5] 中第二小的数是负数 -4 。
	 * 示例 3：
	 * 输入：nums = [-3,1,2,-3,0,-3], k = 2, x = 1
	 * 输出：[-3,0,-3,-3,-3]
	 * 解释：总共有 5 个 k = 2 的子数组。
	 * [-3, 1] 中最小的数是负数 -3 。
	 * [1, 2] 中最小的数不是负数，所以美丽值为 0 。
	 * [2, -3] 中最小的数是负数 -3 。
	 * [-3, 0] 中最小的数是负数 -3 。
	 * [0, -3] 中最小的数是负数 -3 。
	 * 提示：
	 * n == nums.length
	 * 1 <= n <= 105
	 * 1 <= k <= n
	 * 1 <= x <= k
	 * -50 <= nums[i] <= 50
	 */
	static class GetSubarrayBeauty {

		/**
		 * 思路: 固定长度子数组,滑动窗口
		 * 主要是窗体内,每次都要求第x小的元素,如何快速求第x小呢?
		 * 1.用快排思路的selectK是可以的,但是窗口滑动时,相当于要完全重排序.
		 * 2.使用优先级队列(大顶堆),队列长度为x,那么堆顶就是第x小的元素.优先级队列怎么删除元素呢?
		 * 这种方法时间复杂度太高,会超时
		 *
		 * @param nums
		 * @param k
		 * @param x
		 * @return
		 */
		public static int[] getSubarrayBeauty(int[] nums, int k, int x) {
			int n = nums.length;
			PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(k, (Comparator.comparingInt(o -> o)));
			List<Integer> list = new ArrayList<>(k);
			for (int i = 0; i < k; i++) {
				list.add(nums[i]);
			}
			int[] res = new int[n - k + 1];
			res[0] = Math.min(topK(list, priorityQueue, x), 0);
			int idx = 1;
			for (int i = k; i < n; i++) {
				list.remove(0);
				list.add(nums[i]);
				res[idx++] = Math.min(topK(list, priorityQueue, x), 0);
			}
			return res;
		}

		private static int topK(List<Integer> list, PriorityQueue<Integer> priorityQueue, int x) {
			priorityQueue.clear();
			priorityQueue.addAll(list);
			int res = 0;
			for (int i = 0; i < x; i++) {
				res = priorityQueue.poll();
			}
			return res;
		}

		/**
		 * 由于数据范围在[-50,50]之间,数据的范围小,可以参考计数排序,然后暴力的统计第x大的数,即遍历计数排序数组
		 * 这个思路非常不好想到
		 *
		 * @param nums
		 * @param k
		 * @param x
		 * @return
		 */
		public static int[] getSubarrayBeautyII(int[] nums, int k, int x) {
			int[] cnt = new int[50 * 2 + 1];
			for (int i = 0; i < k; i++) { // 现在数组中放入k个数
				cnt[nums[i] + 50]++;  // 全部变成正数,范围变成[0,100]
			}
			int n = nums.length;
			int[] res = new int[n - k + 1];
			res[0] = min(cnt, x);
			for (int i = k; i < n; i++) {
				cnt[nums[i] + 50]++;
				cnt[nums[i - k] + 50]--;
				res[i - k + 1] = min(cnt, x);
			}
			return res;
		}

		/**
		 * 找到cnt中第x小的数
		 *
		 * @param cnt
		 * @return
		 */
		private static int min(int[] cnt, int x) {
			int count = 0;
			for (int i = 0; i < cnt.length; i++) {
				count += cnt[i];
				if (count >= x) {
					return Math.min(i - 50, 0);
				}
			}
			return 0;
		}

		public static void main(String[] args) {
			System.out.println(Arrays.toString(getSubarrayBeautyII(new int[]{-1, -2, -3, -4, -5}, 2, 2)));
		}
	}

	/**
	 * 1888. 使二进制字符串字符交替的最少反转次数
	 * 给你一个二进制字符串 s 。你可以按任意顺序执行以下两种操作任意次：
	 * 类型 1 ：删除 字符串 s 的第一个字符并将它 添加 到字符串结尾。
	 * 类型 2 ：选择 字符串 s 中任意一个字符并将该字符 反转 ，也就是如果值为 '0' ，则反转得到 '1' ，反之亦然。
	 * 请你返回使 s 变成 交替 字符串的前提下， 类型 2 的 最少 操作次数 。
	 * 我们称一个字符串是 交替 的，需要满足任意相邻字符都不同。
	 * 比方说，字符串 "010" 和 "1010" 都是交替的，但是字符串 "0100" 不是。
	 * 示例 1：
	 * 输入：s = "111000"
	 * 输出：2
	 * 解释：执行第一种操作两次，得到 s = "100011" 。
	 * 然后对第三个和第六个字符执行第二种操作，得到 s = "101010" 。
	 * 示例 2：
	 * 输入：s = "010"
	 * 输出：0
	 * 解释：字符串已经是交替的。
	 * 示例 3：
	 * 输入：s = "1110"
	 * 输出：1
	 * 解释：对第二个字符执行第二种操作，得到 s = "1010" 。
	 * 提示：
	 * 1 <= s.length <= 105
	 * s[i] 要么是 '0' ，要么是 '1' 。
	 */
	static class MinFlips {

		/**
		 * 思路:
		 * 按照常规思路,先走步骤1,然后通过滑动窗口进行步骤2
		 * 步骤2如何操作呢?
		 * 滑动窗口的长度为2,每次判断窗口内元素是都相同,如果相同就滑动;不同则把后面的数反转,反转次数+1后滑动
		 * ---------------------------------------------------------------------------------
		 * 暴力匹配超时
		 *
		 * @param s
		 * @return
		 */
		public static int minFlips(String s) {
			String s1 = s + s;
			int n = s.length(), min = Integer.MAX_VALUE;
			for (int i = 0; i < s.length(); i++) {
				String s2 = s1.substring(i, n + i);
				char pre = s2.charAt(0);
				int count = 0;
				for (int j = 1; j < s2.length(); j++) {
					if (pre == s2.charAt(j)) {
						pre = pre == '0' ? '1' : '0';
						count++;
					} else {
						pre = s2.charAt(j);
					}
				}
				min = Math.min(count, min);
			}
			return min;
		}

		/**
		 * 解题思路有点难以理解
		 * 1.操作一是将首位的字符移动到尾部,类似于2倍长度的字符串,向右滑动一位形成的窗口
		 * 2.操作二是反转字符,可以通过和 s1(01...) 或者 s2(10...) 这样的字符串比较差异的位数有多少
		 * * 其中用cnt表是和s1比较后差异的字符数量,那么n-cnt就是和s2比较后差异的字符数量(因为字符除了0就是1,s1和s2每位上恰好相反)
		 * 当然有种情况是可以通过操作一来弥补的,比如01...01|10101 他中间有两个1相邻,如果把前面所有的元素都移动到后面,则得到
		 * 10101|01...01 这样也是符合情况的.对原s字符串进行滑动,滑动后,进入窗口和退出窗口的元素会影响cnt的值
		 *
		 * @param s
		 * @return
		 */
		public static int minFlipsII(String s) {
			int n = s.length();
			char[] s01 = "01".toCharArray();
			int cnt = 0;
			for (int i = 0; i < n; i++) {
				if (s.charAt(i) == s01[i % 2]) {
					cnt++;
				}
			}
			int ans = Math.min(cnt, n - cnt);
			for (int i = n; i < 2 * n; i++) {
				// 不懂这个滑动窗口的逻辑;为什么滑动之后,cnt的数量就进行相应的变化呢?101|1010
				// 这种情况最多只会出现一次,每滑动一位后查看是否有这种情况出现
				if (s.charAt((i - n) % n) == s01[(i - n) % 2]) {
					cnt--;
				}
				if (s.charAt(i % n) == s01[i % 2]) {
					cnt++;
				}
				ans = Math.min(ans, Math.min(cnt, n - cnt));
			}
			return ans;
		}

		public static void main(String[] args) {
			System.out.println(minFlipsII("111000"));
		}
	}

}
