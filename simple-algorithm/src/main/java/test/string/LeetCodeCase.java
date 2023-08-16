package test.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeetCodeCase {

	/**
	 * 344. 反转字符串
	 * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
	 * <p>
	 * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：s = ['h','e','l','l','o']
	 * 输出：['o','l','l','e','h']
	 * 示例 2：
	 * <p>
	 * 输入：s = ['H','a','n','n','a','h']
	 * 输出：['h','a','n','n','a','H']
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= s.length <= 105
	 * s[i] 都是 ASCII 码表中的可打印字符
	 */
	public static class ReverseString {

		/**
		 * 个人思路
		 * 简单来说,就是数组的交换元素操作,分奇偶性
		 *
		 * @param s
		 */
		public static void reverseString(char[] s) {
			int n = s.length;
			for (int i = 0; i < n / 2; i++) {
				char c = s[i];
				s[i] = s[n - i - 1];
				s[n - i - 1] = c;
			}
		}

		private static void swap(char[] s, int i, int j) {
			char c = s[i];
			s[i] = s[j];
			s[j] = c;
		}

		public static void main(String[] args) {
			char[] chars = {'H', 'a', 'n', 'n', 'a', 'h'};
			reverseString(chars);
			System.out.println(chars);
		}
	}

	/**
	 * 541. 反转字符串 II
	 * <p>
	 * 给定一个字符串 s 和一个整数 k，从字符串开头算起，每计数至 2k 个字符，就反转这 2k 字符中的前 k 个字符。
	 * <p>
	 * 如果剩余字符少于 k 个，则将剩余字符全部反转。
	 * 如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：s = "abcdefg", k = 2
	 * 输出："bacdfeg"
	 * 示例 2：
	 * <p>
	 * 输入：s = "abcd", k = 2
	 * 输出："bacd"
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= s.length <= 104
	 * s 仅由小写英文组成
	 * 1 <= k <= 104
	 */
	public static class ReverseStr {

		/**
		 * 个人思路：
		 * 相当于拆分反转字符串,将字符串拆分成很多需要反转的份,然后按要求进行反转
		 *
		 * @param s
		 * @param k
		 * @return
		 */
		public static String reverseStr(String s, int k) {
			char[] chars = s.toCharArray();
			int n = chars.length / (2 * k) + 1;
			for (int i = 0; i < n; i++) {
				int m = i * 2 * k;
				if (m >= chars.length) break;
				reverse(chars, m, Math.min(m + k - 1, chars.length - 1));
			}
			return String.valueOf(chars);
		}

		/**
		 * 在s-e之间反转,则e-s=k-1
		 *
		 * @param chars
		 * @param s
		 * @param e
		 */
		private static void reverse(char[] chars, int s, int e) {
			for (int i = 0; i <= (e - s) / 2; i++) {
				char c = chars[i + s];
				chars[i + s] = chars[e - i];
				chars[e - i] = c;
			}
		}

		public static void main(String[] args) {
			String a = reverseStr("abcd", 2);
			System.out.println(a);
		}
	}

	/**
	 * 剑指 Offer 05. 替换空格
	 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：s = "We are happy."
	 * 输出："We%20are%20happy."
	 * <p>
	 * <p>
	 * 限制：
	 * <p>
	 * 0 <= s 的长度 <= 10000
	 */
	public static class ReplaceSpace {

		/**
		 * 个人思路:
		 * 找到所有的空格,然后进行替换
		 * 常规思路是遍历字符串的char数组,然后做数组的替换操作;
		 * 但是空格一个字符,替换成%20 多个字符,怎么操作效率最高呢?
		 * <p>
		 * 要实现原地的替换,好像不太方便
		 * 显然使用工具会方便很多 StringBuilder
		 *
		 * @param s
		 * @return
		 */
		public static String replaceSpace(String s) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == ' ') {
					sb.append("%20");
				} else {
					sb.append(s.charAt(i));
				}
			}
			return sb.toString();
		}

		public static void main(String[] args) {
			String s = replaceSpace("We are happy.");
			System.out.println(s);
		}
	}

	/**
	 * 151. 反转字符串中的单词
	 * 给你一个字符串 s ，请你反转字符串中 单词 的顺序。
	 * <p>
	 * 单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。
	 * <p>
	 * 返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。
	 * <p>
	 * 注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：s = "the sky is blue"
	 * 输出："blue is sky the"
	 * 示例 2：
	 * <p>
	 * 输入：s = "  hello world  "
	 * 输出："world hello"
	 * 解释：反转后的字符串中不能存在前导空格和尾随空格。
	 * 示例 3：
	 * <p>
	 * 输入：s = "a good   example"
	 * 输出："example good a"
	 * 解释：如果两个单词间有多余的空格，反转后的字符串需要将单词间的空格减少到仅有一个。
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= s.length <= 104
	 * s 包含英文大小写字母、数字和空格 ' '
	 * s 中 至少存在一个 单词
	 */
	public static class ReverseWords {

		/**
		 * 个人思路:
		 * 简单来看,可以使用String的分割api,然后将分割后的数组组装成字符串
		 * 单如果要实现原地的算法,需要用到交换或者插入的方式
		 *
		 * @param s
		 * @return
		 */
		public static String reverseWords(String s) {
			String[] arr = s.split(" ");
			StringBuilder sb = new StringBuilder();
			for (int i = arr.length - 1; i >= 0; i--) {
				if (!arr[i].equals("")) {
					sb.append(arr[i]).append(" ");
				}
			}
			return sb.substring(0, sb.length() - 1);
		}

		/**
		 * 当然,上面的方法借助了split方法,使问题变得非常简单了.需要想一下,不使用原有api的方式来解决
		 * 很容易想到的思路是,反向遍历字符串,然后需要空格,就将刚才遍历过的单词放入StringBuilder中
		 *
		 * @param s
		 * @return
		 */
		public static String reverseWordsII(String s) {
			s = " " + s;
			int n = s.length();
			int p = n - 1, q = p;
			StringBuilder sb = new StringBuilder();
			while (p >= 0 && q >= 0) {
				if (s.charAt(p) == 32) {
					if (p == q) {
						p--;
						q--;
					} else {
						sb.append(s, p + 1, q + 1).append(" ");
						p--;
						q = p;
					}
				} else {
					p--;
				}
			}
			return sb.substring(0, sb.length() - 1);
		}

		public static void main(String[] args) {
			String s = reverseWordsII("the sky is blue");
			System.out.println(s);
		}
	}

	/**
	 * 剑指 Offer 58 - II. 左旋转字符串
	 * 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。
	 * 比如，输入字符串"abcdefg"和数字2，该函数将返回左旋转两位得到的结果"cdefgab"。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入: s = "abcdefg", k = 2
	 * 输出: "cdefgab"
	 * 示例 2：
	 * <p>
	 * 输入: s = "lrloseumgh", k = 6
	 * 输出: "umghlrlose"
	 * <p>
	 * <p>
	 * 限制：
	 * <p>
	 * 1 <= k < s.length <= 10000
	 */
	public static class ReverseLeftWords {

		/**
		 *
		 * @param s
		 * @param n
		 * @return
		 */
		public String reverseLeftWords(String s, int n) {

			return "";
		}
	}
}
