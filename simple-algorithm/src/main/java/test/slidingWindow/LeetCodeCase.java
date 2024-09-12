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
}
