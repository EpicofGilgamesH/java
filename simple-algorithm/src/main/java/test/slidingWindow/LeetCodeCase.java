package test.slidingWindow;

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
}
