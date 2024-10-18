package test.slidingWindow;

import org.apache.poi.xssf.usermodel.XSSFPivotTable;

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
}
