package test.slidingWindow;

import org.apache.poi.xssf.usermodel.XSSFPivotTable;

import java.nio.charset.StandardCharsets;
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
}
