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
		 * 个人思路:
		 * 了解题意,第一时间想到的是操作String的char[]数组,进行移动操作,如何实现原地的处理方式呢?
		 * 从数组的尾部开始向前进行移动,以达到整体移动的效果,还是不行.********
		 * 简单可行的办法就是,将这n个数,一直往后进行交换移位,直到最后剩下一个或两个元素
		 * 发现数量不一致的元素非常难移动位置。 [abc],[de] -> [ed,abc]
		 * 那能不能使用n的元素数组的空间来解决这个问题呢?
		 * <p>
		 * 该思路太难处理不同个数的元素之间的交换...
		 *
		 * @param s
		 * @param n
		 * @return
		 */
		public static String reverseLeftWords(String s, int n) {
			char[] chars = s.toCharArray();
			int p = 0;
			while (p < chars.length) {
				// 移动[0,n-1]-[n,2n-1]的元素

			}
			return "";
		}

		/**
		 * 移动元素
		 *
		 * @param chars
		 * @param p
		 * @param n
		 */
		public static void swapRange(char[] chars, int p, int n) {
			int l = chars.length;
			int m = l - (p + 2 * n);
			if (m > 0) {
				if (m > n) {
					for (int i = 0; i < n; i++) {
						swap(chars, p + i, p + n + i);
					}
				} else {
					for (int i = 0; i < m; i++) {
						swap(chars, p + i, p + n + i);
					}
					for (int i = 0; i < n - m; i++) {

					}
				}
			}
		}

		public static void swap(char[] chars, int i, int j) {
			char c = chars[i];
			chars[i] = chars[j];
			chars[j] = c;
		}

		/**
		 * 换个思路,不走原地,开辟n个元素大小的数组,逐步向后移动
		 *
		 * @param s
		 * @param n
		 * @return
		 */
		public static String reverseLeftWordsII(String s, int n) {
			char[] chars = s.toCharArray();
			char[] pre = Arrays.copyOfRange(chars, 0, n);
			for (int i = n; i < chars.length; i++) {
				swap(chars, i, i - n);
			}
			for (int i = 0; i < pre.length; i++) {
				chars[chars.length - n + i] = pre[i];
			}
			return String.valueOf(chars);
		}

		/**
		 * 官方思路I:
		 * 用StringBuilder去拼接成新的字符串,相当清晰明了
		 *
		 * @param s
		 * @param n
		 * @return
		 */
		public static String reverseLeftWordsOfficialI(String s, int n) {
			StringBuilder sb = new StringBuilder();
			for (int i = n; i < s.length(); i++) {
				sb.append(s.charAt(i));
			}
			for (int i = 0; i < n; i++) {
				sb.append(s.charAt(i));
			}
			return sb.toString();
		}

		public static String reverseLeftWordsOfficialII(String s, int n) {
			StringBuilder sb = new StringBuilder();
			for (int i = n; i < n + s.length(); i++) {
				sb.append(s.charAt(i % s.length()));
			}
			return sb.toString();
		}


		public static void main(String[] args) {
			String abcdefg = reverseLeftWordsOfficialII("lrloseumgh", 6);
			System.out.println(abcdefg);
		}
	}

	/**
	 * 28. 找出字符串中第一个匹配项的下标
	 * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。如果 needle 不是 haystack 的一部分，则返回  -1 。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：haystack = "sadbutsad", needle = "sad"
	 * 输出：0
	 * 解释："sad" 在下标 0 和 6 处匹配。
	 * 第一个匹配项的下标是 0 ，所以返回 0 。
	 * 示例 2：
	 * <p>
	 * 输入：haystack = "leetcode", needle = "leeto"
	 * 输出：-1
	 * 解释："leeto" 没有在 "leetcode" 中出现，所以返回 -1 。
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= haystack.length, needle.length <= 104
	 * haystack 和 needle 仅由小写英文字符组成
	 */
	public static class StrStr {

		/**
		 * 个人思路:
		 * 这就是字符串匹配,掌握KMP,尽量能自己手写出来
		 * <p>
		 * 首先来个暴力匹配
		 * <p>
		 * 王争老师的《数据结构与算法之美》中,主要讲到字符串匹配的三种方法
		 * 1.BF 暴力匹配
		 * 2.RK Hash暴力匹配
		 * 3.KMP
		 *
		 * @param haystack
		 * @param needle
		 * @return
		 */
		public static int strStr(String haystack, String needle) {
			for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
				int p = i, j = 0;
				while (j < needle.length()) {
					if (haystack.charAt(p) == needle.charAt(j)) {
						p++;
						j++;
					} else {
						break;
					}
				}
				if (j == needle.length()) {
					return i;
				}
			}
			return -1;
		}

		/**
		 * RK hash暴力匹配的思路
		 * 1.先把主串中所有的可能会匹配的子串枚举出来,然后计算其hash值
		 * 2.向后推进式的匹配这些子串与模式串的hash值,直到找到匹配的或者完成所有的子串
		 * <p>
		 * 本题要求字符串中的字符仅为小写英文字母,可以看做是26进制的数字,计算其hash值
		 * 但是这样,模式串的位数就太小了
		 * long类型最大值为26的13次方,也就是模式串最多只能支持13位字符串
		 *
		 * @param haystack
		 * @param needle
		 * @return
		 */
		public static int strStr_RK(String haystack, String needle) {
			if (haystack.length() < needle.length()) {
				return -1;
			}
			long nHash = hash(needle);
			int n = haystack.length() - needle.length() + 1;
			long[] arr = new long[n];
			long pre = hash(haystack.substring(0, needle.length()));
			arr[0] = pre;
			for (int i = 1; i < n; i++) {
				arr[i] = (arr[i - 1] - (long) Math.pow(26, needle.length() - 1) * (haystack.charAt(i - 1) - 'a')) * 26
						+ (haystack.charAt(i + needle.length() - 1) - 'a');

				long hash = hash(haystack.substring(i, needle.length() + i));
				System.out.println();
			}
			for (int i = 0; i < n; i++) {
				if (arr[i] == nHash) {
					return i;
				}
			}
			return -1;
		}

		/**
		 * a,b,c,d,e,f,g,h,i,j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z
		 * 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25
		 * missi  -> 12*26^4+8*26^3+18*26^2+18*26+8 = 5636964.0
		 * issis  -> 8*26^4+18*26^3+18*26^2+8*26+18 = 3984570.0
		 *
		 * h(i) 为从主串的第i个元素开始的子串的hash值 算了半天,弄错了一个基本的指数定理,任何数的0次方等于1*********
		 * 推到递推公式 h(i)=26*(h(i-1)-26^(m-1)*(S[i-1]-'a'))+(S[m+i-1]-'a')
		 * h(1)=26*(h(0)-26^4*12)+18 = 3985020
		 * h(1)=26*(5636964-5483712)+ 18
		 * h(1)=26*153252+18
		 * h(1)=3,984,552+18
		 * h(1)=3984570
		 *
		 * 26^4 = 456976
		 * 26^3 = 17576
		 * 26^2 = 676
		 *
		 */

		/**
		 * issip 通过进制位算hash值
		 * i*26^4+s*26^3+s*26^2+i*26^1+p*26^0
		 *
		 * @param s
		 * @return
		 */
		public static long hash(String s) {
			long hash = 0;
			for (int i = 0; i < s.length(); i++) {
				int v = s.charAt(i) - 'a';
				hash += v * Math.pow(26, s.length() - i - 1);
			}
			return hash;
		}

		public static final int[] PRIME = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};

		/**
		 * 素数的方式计算hash值,缺点是字符顺序不一样的相同字符 hash值一样;会退化成暴力匹配
		 *
		 * @param haystack
		 * @param needle
		 * @return
		 */
		public static int rk_prime(String haystack, String needle) {
			if (haystack.length() < needle.length()) {
				return -1;
			}
			long nHash = hash_prime(needle);
			int n = haystack.length() - needle.length() + 1;
			long[] arr = new long[n];
			long pre = hash_prime(haystack.substring(0, needle.length()));
			arr[0] = pre;
			for (int i = 1; i < n; i++) {
				arr[i] = arr[i - 1] + PRIME[haystack.charAt(i + needle.length() - 1) - 'a'] - PRIME[haystack.charAt(i - 1) - 'a'];
			}
			for (int i = 0; i < n; i++) {
				if (arr[i] == nHash && haystack.startsWith(needle, i)) {
					return i;
				}
			}
			return -1;
		}

		public static long hash_prime(String s) {
			long hash = 0;
			for (int i = 0; i < s.length(); i++) {
				hash += PRIME[s.charAt(i) - 'a'];
			}
			return hash;
		}


		public static void main(String[] args) {
			int i = rk_prime("mississippi", "issip");
			System.out.println(i);
			// 9223372036854775807
			long zzzzzzzzzzzzz = hash("zzzzzzzzzzzzz");
			System.out.println(zzzzzzzzzzzzz);
		}
	}

	/**
	 * 459. 重复的子字符串
	 * 给定一个非空的字符串 s ，检查是否可以通过由它的一个子串重复多次构成。
	 * <p>
	 * 示例 1:
	 * <p>
	 * 输入: s = "abab"
	 * 输出: true
	 * 解释: 可由子串 "ab" 重复两次构成。
	 * 示例 2:
	 * <p>
	 * 输入: s = "aba"
	 * 输出: false
	 * 示例 3:
	 * <p>
	 * 输入: s = "abcabcabcabc"
	 * 输出: true
	 * 解释: 可由子串 "abc" 重复四次构成。 (或子串 "abcabc" 重复两次构成。)
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= s.length <= 104
	 * s 由小写英文字母组成
	 */
	public static class RepeatedSubstringPattern {

		/**
		 * 个人思路：
		 * 按照常规思路,需要先确认重复的子串,然后看主串是不是由这些子串完全重复构成的
		 * 还要注意,不同长度的注册,其构成子串的长度不一样  比如主串是奇数,则子串肯定不会是偶数 反之亦然
		 *
		 * @return
		 */
		public static boolean repeatedSubstringPattern(String s) {
			int n = s.length(); // 主串长度n
			char f = s.charAt(0);
			for (int i = 1; i < n; i++) {
				if (s.charAt(i) == f) {
					// 假设[0,i-1]为子串
					if (n % i != 0) continue;
					int p = 0, q = i;
					while (q < n && s.charAt(p) == s.charAt(q)) {
						p = (p + 1) == i ? 0 : p + 1;
						q++;
					}
					if (p == 0 && q == n) {
						return true;
					}
				}
			}
			return false;
		}

		/**
		 * 个人思路做一点小的优化
		 *
		 * @param s
		 * @return
		 */
		public static boolean repeatedSubstringPatternII(String s) {
			int n = s.length(); // 主串长度n
			char f = s.charAt(0);
			for (int i = 1; i < n; i++) {
				if (s.charAt(i) == f) {
					// 假设[0,i-1]为子串
					if (n % i != 0) continue;
					int q = i;
					while (q < n && s.charAt(q) == s.charAt(q - i)) {
						q++;
					}
					if (q == n) {
						return true;
					}
				}
			}
			return false;
		}

		/**
		 * 官方思路非常直接,假设子串的长度是[1,n/2]之前,然后遍历所有的子串场景
		 *
		 * @param s
		 * @return
		 */
		public static boolean repeatedSubstringPatternOfficial(String s) {
			for (int i = 1; i <= s.length() / 2; ++i) {
				if (s.length() % i != 0) continue;
				boolean f = true;
				for (int j = i; j < s.length(); ++j) {
					if (s.charAt(j) != s.charAt(j - i)) { // 巧妙
						f = false;
						break;
					}
				}
				if (f) {
					return true;
				}
			}
			return false;
		}

		public static void main(String[] args) {
			boolean b = repeatedSubstringPattern("abcabcabcabc");
			boolean b1 = repeatedSubstringPatternOfficial("abcabcabcabc");
			boolean b2 = repeatedSubstringPatternII("abcabcabcabc");
			System.out.println(b2);
		}
	}
}
