package test.string;

import org.omg.CORBA.INTERNAL;

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

	/**
	 * 1768. 交替合并字符串
	 * 给你两个字符串 word1 和 word2 。请你从 word1 开始，通过交替添加字母来合并字符串。如果一个字符串比另一个字符串长，就将多出来的字母追加到合并后字符串的末尾。
	 * 返回 合并后的字符串 。
	 * 示例 1：
	 * 输入：word1 = "abc", word2 = "pqr"
	 * 输出："apbqcr"
	 * 解释：字符串合并情况如下所示：
	 * word1：  a   b   c
	 * word2：    p   q   r
	 * 合并后：  a p b q c r
	 * 示例 2：
	 * 输入：word1 = "ab", word2 = "pqrs"
	 * 输出："apbqrs"
	 * 解释：注意，word2 比 word1 长，"rs" 需要追加到合并后字符串的末尾。
	 * word1：  a   b
	 * word2：    p   q   r   s
	 * 合并后：  a p b q   r   s
	 * 示例 3：
	 * 输入：word1 = "abcd", word2 = "pq"
	 * 输出："apbqcd"
	 * 解释：注意，word1 比 word2 长，"cd" 需要追加到合并后字符串的末尾。
	 * word1：  a   b   c   d
	 * word2：    p   q
	 * 合并后：  a p b q c   d
	 * 提示：
	 * 1 <= word1.length, word2.length <= 100
	 * word1 和 word2 由小写英文字母组成
	 */
	public static class MergeAlternately {

		/**
		 * 双指针,然后判断谁先到尾部,再将字符转移过去
		 *
		 * @param word1
		 * @param word2
		 * @return
		 */
		public static String mergeAlternately(String word1, String word2) {
			int m = word1.length(), n = word2.length();
			char[] chars = new char[m + n];
			int p = 0, q = 0, k = 0;
			while (p < m && q < n) {
				chars[k++] = word1.charAt(p++);
				chars[k++] = word2.charAt(q++);
			}
			while (p < m) {
				chars[k++] = word1.charAt(p++);
			}
			while (q < n) {
				chars[k++] = word2.charAt(q++);
			}
			return String.valueOf(chars);
		}

		public static void main(String[] args) {
			String word1 = "abcd", word2 = "pq";
			String s = mergeAlternately(word1, word2);
			System.out.println(s);
		}
	}

	/**
	 * 1071. 字符串的最大公因子
	 * 对于字符串 s 和 t，只有在 s = t + t + t + ... + t + t（t 自身连接 1 次或多次）时，我们才认定 “t 能除尽 s”。
	 * 给定两个字符串 str1 和 str2 。返回 最长字符串 x，要求满足 x 能除尽 str1 且 x 能除尽 str2 。
	 * 示例 1：
	 * 输入：str1 = "ABCABC", str2 = "ABC"
	 * 输出："ABC"
	 * 示例 2：
	 * 输入：str1 = "ABABAB", str2 = "ABAB"
	 * 输出："AB"
	 * 示例 3：
	 * 输入：str1 = "LEET", str2 = "CODE"
	 * 输出：""
	 * 提示：
	 * 1 <= str1.length, str2.length <= 1000
	 * str1 和 str2 由大写英文字母组成
	 */
	public static class GcdOfStrings {

		/**
		 * 该问题的关键在于怎么找到两个字符串的公共因子
		 * 两个字符串的公共因子,即这个公共因子通过若干次的拼接,最终能组成str1和str2
		 * <p>
		 * 官方给出的方法是:枚举符合长度条件的前缀串,再判断这个前缀串是否拼接若干次以后等于str1和str2
		 * 那就是从最大往最小开始枚举,最后得到的即是公共因子
		 *
		 * @param str1
		 * @param str2
		 * @return
		 */
		public static String gcdOfStrings(String str1, String str2) {
			int m = str1.length(), n = str2.length();
			int s = Math.min(m, n); // 最长公共因子先设定为str1和str2中较短的那个,然后依次递减
			for (int i = s; i > 0; i--) {
				if (m % i == 0 && n % i == 0) {
					String x = str1.substring(0, i);
					if (checkCd(x, str1) && checkCd(x, str2)) {
						return x;
					}
				}
			}
			return "";
		}

		/**
		 * 判断a是否为b的因子,即b可以通过n个a构造
		 * 通过长度的计算,来构造a
		 *
		 * @param a
		 * @param b
		 * @return
		 */
		private static boolean checkCd(String a, String b) {
			int m = a.length(), n = b.length();
			StringBuffer sb = new StringBuffer();
			int k = n / m;
			for (int i = 0; i < k; i++) {
				sb.append(a);
			}
			return sb.toString().equals(b);
		}

		/**
		 * 先找出两个字符串长度的最大公约数,然后截取该长度的字符串,判断其是否能构成str1和str2这两个字符串
		 *
		 * @param str1
		 * @param str2
		 * @return
		 */
		public static String gcdOfStringsI(String str1, String str2) {
			int m = str1.length(), n = str2.length();
			String x = str1.substring(0, gcd(m, n));
			if (checkCd(x, str1) && checkCd(x, str2)) {
				return x;
			}
			return "";
		}

		/**
		 * 求最大公约数
		 *
		 * @param a
		 * @param b
		 * @return
		 */
		private static int gcd(int a, int b) {
			int r = a % b;
			while (r != 0) {
				a = b;
				b = r;
				r = a % b;
			}
			return b;
		}

		/**
		 * 直接判断str1 和str2拼接后是否等于str2和str1拼接起来的字符串,如果相等,直接输出长度为gcd(len1,len2)的前缀即可
		 *
		 * @param str1
		 * @param str2
		 * @return
		 */
		public static String gcdOfStringsII(String str1, String str2) {
			if (!str1.concat(str2).equals(str2.concat(str1))) {
				return "";
			}
			return str1.substring(0, gcd(str1.length(), str2.length()));
		}

		public static void main(String[] args) {
			String a = "abcabc", b = "abc";
			String s = gcdOfStringsII(a, b);
			System.out.println(s);

			System.out.println(gcd(90, 20));
		}
	}

	/**
	 * 1431. 拥有最多糖果的孩子
	 * 有 n 个有糖果的孩子。给你一个数组 candies，其中 candies[i] 代表第 i 个孩子拥有的糖果数目，和一个整数 extraCandies 表示你所有的额外糖果的数量。
	 * 返回一个长度为 n 的布尔数组 result，如果把所有的 extraCandies 给第 i 个孩子之后，他会拥有所有孩子中 最多 的糖果，那么 result[i] 为 true，否则为 false。
	 * 注意，允许有多个孩子同时拥有 最多 的糖果数目。
	 * 示例 1：
	 * 输入：candies = [2,3,5,1,3], extraCandies = 3
	 * 输出：[true,true,true,false,true]
	 * 解释：如果你把额外的糖果全部给：
	 * 孩子 1，将有 2 + 3 = 5 个糖果，是孩子中最多的。
	 * 孩子 2，将有 3 + 3 = 6 个糖果，是孩子中最多的。
	 * 孩子 3，将有 5 + 3 = 8 个糖果，是孩子中最多的。
	 * 孩子 4，将有 1 + 3 = 4 个糖果，不是孩子中最多的。
	 * 孩子 5，将有 3 + 3 = 6 个糖果，是孩子中最多的。
	 * 示例 2：
	 * 输入：candies = [4,2,1,1,2], extraCandies = 1
	 * 输出：[true,false,false,false,false]
	 * 解释：只有 1 个额外糖果，所以不管额外糖果给谁，只有孩子 1 可以成为拥有糖果最多的孩子。
	 * 示例 3：
	 * 输入：candies = [12,1,12], extraCandies = 10
	 * 输出：[true,false,true]
	 * 提示：
	 * n == candies.length
	 * 2 <= n <= 100
	 * 1 <= candies[i] <= 100
	 * 1 <= extraCandies <= 50
	 */
	public static class KidsWithCandies {

		/**
		 * 先遍历一遍,找到最大的数;
		 * 然后再遍历一遍,判断将额外的糖给到每个孩子后是否成为最大的数
		 *
		 * @param candies
		 * @param extraCandies
		 * @return
		 */
		public static List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
			int max = 0;
			for (int i = 0; i < candies.length; i++) {
				max = Math.max(candies[i], max);
			}
			List<Boolean> list = new ArrayList<>();
			for (int i = 0; i < candies.length; i++) {
				list.add(candies[i] + extraCandies >= max);
			}
			return list;
		}

		public static void main(String[] args) {
			System.out.println(kidsWithCandies(new int[]{4, 2, 1, 1, 2}, 1));
			System.out.println(kidsWithCandies(new int[]{2, 3, 5, 1, 3}, 3));
		}
	}

	/**
	 * 605. 种花问题
	 * 假设有一个很长的花坛，一部分地块种植了花，另一部分却没有。可是，花不能种植在相邻的地块上，它们会争夺水源，两者都会死去。
	 * 给你一个整数数组 flowerbed 表示花坛，由若干 0 和 1 组成，其中 0 表示没种植花，1 表示种植了花。另有一个数 n ，
	 * 能否在不打破种植规则的情况下种入 n 朵花？能则返回 true ，不能则返回 false 。
	 * 示例 1：
	 * 输入：flowerbed = [1,0,0,0,1], n = 1
	 * 输出：true
	 * 示例 2：
	 * 输入：flowerbed = [1,0,0,0,1], n = 2
	 * 输出：false
	 * 提示：
	 * 1 <= flowerbed.length <= 2 * 104
	 * flowerbed[i] 为 0 或 1
	 * flowerbed 中不存在相邻的两朵花
	 * 0 <= n <= flowerbed.length
	 */
	public static class CanPlaceFlowers {

		/**
		 * 对于当前位置是否可以种花,有三个条件
		 * 1.当前位置是0
		 * 2.前一个位置是0或者边界
		 * 3.后一个位置是0或者边界
		 * 当前位置种花之后,接下里的位置肯定不能种花,可以直接跳过
		 *
		 * @param flowerbed
		 * @param n
		 * @return
		 */
		public static boolean canPlaceFlowers(int[] flowerbed, int n) {
			int prev, curr, next, sum = 0;
			for (int i = 0; i < flowerbed.length; i++) {
				curr = flowerbed[i];
				if (curr == 0) {
					prev = i <= 0 ? 0 : flowerbed[i - 1];
					next = i >= flowerbed.length - 1 ? 0 : flowerbed[i + 1];
					if (prev == 0 && next == 0) {
						sum++;
						i++;
						if (sum >= n) return true;
					}
				}
			}
			return sum >= n;
		}

		public static void main(String[] args) {
			boolean b = canPlaceFlowers(new int[]{1, 0, 0, 0, 1}, 1);
			System.out.println(b);
		}
	}

	/**
	 * 345. 反转字符串中的元音字母
	 * 给你一个字符串 s ，仅反转字符串中的所有元音字母，并返回结果字符串。
	 * 元音字母包括 'a'、'e'、'i'、'o'、'u'，且可能以大小写两种形式出现不止一次。
	 * 示例 1：
	 * 输入：s = "IceCreAm"
	 * 输出："AceCreIm"
	 * 解释：
	 * s 中的元音是 ['I', 'e', 'e', 'A']。反转这些元音，s 变为 "AceCreIm".
	 * 示例 2：
	 * 输入：s = "leetcode"
	 * 输出："leotcede"
	 * 提示：
	 * 1 <= s.length <= 3 * 105
	 * s 由 可打印的 ASCII 字符组成
	 */
	public static class ReverseVowels {

		/**
		 * 找到大小写的元音字母的idx,然后进行交换操作
		 *
		 * @param s
		 * @return
		 */
		public static String reverseVowels(String s) {
			/*List<Character> list = new ArrayList<>();
			list.add('a');
			list.add('A');
			list.add('e');
			list.add('E');
			list.add('i');
			list.add('I');
			list.add('o');
			list.add('O');
			list.add('u');
			list.add('U');*/
			List<Integer> idx = new ArrayList<>();
			char[] chars = s.toCharArray();
			for (int i = 0; i < chars.length; ++i) {
				if (isVowels(chars[i])) {
					idx.add(i);
				}
			}
			for (int i = 0; i < idx.size() / 2; i++) {
				swap(chars, idx.get(i), idx.get(idx.size() - i - 1));
			}
			return String.valueOf(chars);
		}

		private static void swap(char[] chars, int i, int j) {
			char temp = chars[i];
			chars[i] = chars[j];
			chars[j] = temp;
		}

		private static boolean isVowels(char c) {
			if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
				return true;
			}
			if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
				return true;
			}
			return false;
		}

		/**
		 * 两个指针同时进行,分别从字符串的头部和尾部向中间推进
		 * 当两个指针都为元音字母时,进行交换操作
		 *
		 * @param s
		 * @return
		 */
		public static String reverseVowelsI(String s) {
			char[] chars = s.toCharArray();
			int p = 0, q = chars.length - 1;
			while (p < q) {
				boolean vp = isVowels(chars[p]);
				boolean vq = isVowels(chars[q]);
				if (vp && vq) {  // p,q指针指都向元音字符,进行交换
					swap(chars, p, q);
					p++;
					q--;
				}
				if (!vp) p++;
				if (!vq) q--;
			}
			return String.valueOf(chars);
		}

		public static void main(String[] args) {
			System.out.println(reverseVowelsI("IceCreAm"));
		}
	}

	/**
	 * 334. 递增的三元子序列
	 * 给你一个整数数组 nums ，判断这个数组中是否存在长度为 3 的递增子序列。
	 * 如果存在这样的三元组下标 (i, j, k) 且满足 i < j < k ，使得 nums[i] < nums[j] < nums[k] ，返回 true ；否则，返回 false 。
	 * 示例 1：
	 * 输入：nums = [1,2,3,4,5]
	 * 输出：true
	 * 解释：任何 i < j < k 的三元组都满足题意
	 * 示例 2：
	 * 输入：nums = [5,4,3,2,1]
	 * 输出：false
	 * 解释：不存在满足题意的三元组
	 * 示例 3：
	 * 输入：nums = [2,1,5,0,4,6]
	 * 输出：true
	 * 解释：三元组 (3, 4, 5) 满足题意，因为 nums[3] == 0 < nums[4] == 4 < nums[5] == 6
	 * 提示：
	 * 1 <= nums.length <= 5 * 105
	 * -231 <= nums[i] <= 231 - 1
	 * 进阶：你能实现时间复杂度为 O(n) ，空间复杂度为 O(1) 的解决方案吗？
	 */
	public static class IncreasingTriplet {

		/**
		 * 对于每个元素,如果存在左边比他更小的数,同时右边有比他更大的数,那么说明递增三元子序列存在
		 * 所以需要从左到右,从右到左两次遍历数组,找到每个元素左边最小和右边最大的元素
		 *
		 * @param nums
		 * @return
		 */
		public static boolean increasingTriplet(int[] nums) {
			int l = nums.length;
			int max = Integer.MIN_VALUE;
			int[] rightMax = new int[l - 1];
			for (int i = l - 2; i >= 0; i--) {
				max = Math.max(nums[i + 1], max);
				rightMax[i] = max;
			}
			int min = Integer.MAX_VALUE;
			for (int i = 1; i < l - 1; i++) {
				min = Math.min(min, nums[i - 1]);
				if (min < nums[i] && rightMax[i] > nums[i]) {
					return true;
				}
			}
			return false;
		}

		/**
		 * 贪心思路
		 * 首先，新建两个变量 small 和 mid ，分别用来保存题目要我们求的长度为 3 的递增子序列的最小值和中间值。
		 * 接着，我们遍历数组，每遇到一个数字，我们将它和 small 和 mid 相比，若小于等于 small ，则替换 small；否则，若小于等于 mid，则替换 mid；否则，若大于 mid，则说明我们找到了长度为 3 的递增数组！
		 * 上面的求解过程中有个问题：当已经找到了长度为 2 的递增序列，这时又来了一个比 small 还小的数字，为什么可以直接替换 small 呢，这样 small 和 mid 在原数组中并不是按照索引递增的关系呀？
		 * Trick 就在这里了！假如当前的 small 和 mid 为 [3, 5]，这时又来了个 1。假如我们不将 small 替换为 1，那么，当下一个数字是 2，后面再接上一个 3 的时候，我们就没有办法发现这个 [1,2,3] 的递增数组了！也就是说，我们替换最小值，是为了后续能够更好地更新中间值！
		 * 另外，即使我们更新了 small ，这个 small 在 mid 后面，没有严格遵守递增顺序，但它隐含着的真相是，有一个比 small 大比 mid 小的前·最小值出现在 mid 之前。因此，当后续出现比 mid 大的值的时候，我们一样可以通过当前 small 和 mid 推断的确存在着长度为 3 的递增序列。 所以，这样的替换并不会干扰我们后续的计算！
		 *
		 * @param nums
		 * @return
		 */
		public static boolean increasingTripletI(int[] nums) {
			int l = nums.length;
			if (l < 3) return false;
			int first = nums[0], second = Integer.MAX_VALUE;
			for (int i = 1; i < l; i++) {
				if (nums[i] > second) {
					return true;
				} else if (nums[i] > first) {  // first < i < second
					second = nums[i];
				} else {                       // i<first
					first = nums[i];
				}
			}
			return false;
		}

		public static void main(String[] args) {
			int[] nums = new int[]{5, 1, 6};
			boolean b = increasingTripletI(nums);
			System.out.println(b);
		}
	}

	/**
	 * 443. 压缩字符串
	 * 给你一个字符数组 chars ，请使用下述算法压缩：
	 * 从一个空字符串 s 开始。对于 chars 中的每组 连续重复字符 ：
	 * 如果这一组长度为 1 ，则将字符追加到 s 中。
	 * 否则，需要向 s 追加字符，后跟这一组的长度。
	 * 压缩后得到的字符串 s 不应该直接返回 ，需要转储到字符数组 chars 中。需要注意的是，如果组长度为 10 或 10 以上，则在 chars 数组中会被拆分为多个字符。
	 * 请在 修改完输入数组后 ，返回该数组的新长度。
	 * 你必须设计并实现一个只使用常量额外空间的算法来解决此问题。
	 * 示例 1：
	 * 输入：chars = ["a","a","b","b","c","c","c"]
	 * 输出：返回 6 ，输入数组的前 6 个字符应该是：["a","2","b","2","c","3"]
	 * 解释："aa" 被 "a2" 替代。"bb" 被 "b2" 替代。"ccc" 被 "c3" 替代。
	 * 示例 2：
	 * 输入：chars = ["a"]
	 * 输出：返回 1 ，输入数组的前 1 个字符应该是：["a"]
	 * 解释：唯一的组是“a”，它保持未压缩，因为它是一个字符。
	 * 示例 3：
	 * 输入：chars = ["a","b","b","b","b","b","b","b","b","b","b","b","b"]
	 * 输出：返回 4 ，输入数组的前 4 个字符应该是：["a","b","1","2"]。
	 * 解释：由于字符 "a" 不重复，所以不会被压缩。"bbbbbbbbbbbb" 被 “b12” 替代。
	 * 提示：
	 * 1 <= chars.length <= 2000
	 * chars[i] 可以是小写英文字母、大写英文字母、数字或符号
	 */
	public static class CompressString {

		/**
		 * 思路:
		 * 题目要求,字符数组chars压缩后,要修改输入数组
		 * 使用两个指针 read和write 分别代表读取chars的位置和写入压缩时的位置,最后write最后要写入的位置即是长度
		 * 如果压缩时,同一个字符长度超过10,需要将数字转换成单个字符;这里需要从个位开始计算该数字每位上的值,
		 * 然后反转过来
		 *
		 * @param chars
		 * @return
		 */
		public static int compress(char[] chars) {
			int l = chars.length, read, write = 0;
			// 判断字符是否可以压缩
			char prev = chars[0];
			int count = 1;
			for (read = 1; read < l; read++) {  // 最后一个字符需要特殊处理
				if (chars[read] == prev) {
					count++;
				} else {
					// 进行压缩
					chars[write++] = prev;
					if (count != 1) {  // 将数字转换成字符
						write = numToChars(chars, write, count);
					}
					prev = chars[read];
					count = 1;
				}
			}
			chars[write++] = prev;
			if (count != 1) {  // 最后一个字符相同
				write = numToChars(chars, write, count);
			}
			return write;
		}

		/**
		 * 把压缩的数字转成字符写入chars数组中
		 *
		 * @param chars
		 * @param write
		 * @param count
		 */
		private static int numToChars(char[] chars, int write, int count) {
			// 记录写入的开始和结束位置
			int left = write;
			while (count > 0) {
				chars[write++] = (char) (count % 10 + '0');
				count /= 10;
			}
			int right = write - 1;
			while (left < right) {
				char temp = chars[left];
				chars[left] = chars[right];
				chars[right] = temp;
				left++;
				right--;
			}
			return write;
		}

		public static void main(String[] args) {
			char[] chars = new char[]{'a','a','b','b','c','c','c'};
			int compress = compress(chars);
			System.out.println(compress);
		}
	}
}
