package test.doublePointer;

import java.util.*;

/**
 * 双指针LeetCode题示例
 */
public class LeetCodeCase {

	/**
	 * 27. 移除元素
	 * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
	 * <p>
	 * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
	 * <p>
	 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
	 * <p>
	 * 说明:
	 * <p>
	 * 为什么返回数值是整数，但输出的答案是数组呢?
	 * <p>
	 * 请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
	 * <p>
	 * 你可以想象内部操作如下:
	 * <p>
	 * // nums 是以“引用”方式传递的。也就是说，不对实参作任何拷贝
	 * int len = removeElement(nums, val);
	 * <p>
	 * // 在函数里修改输入数组对于调用者是可见的。
	 * // 根据你的函数返回的长度, 它会打印出数组中 该长度范围内 的所有元素。
	 * for (int i = 0; i < len; i++) {
	 * print(nums[i]);
	 * }
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：nums = [3,2,2,3], val = 3
	 * 输出：2, nums = [2,2]
	 * 解释：函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。你不需要考虑数组中超出新长度后面的元素。例如，函数返回的新长度为 2 ，而 nums = [2,2,3,3] 或 nums = [2,2,0,0]，也会被视作正确答案。
	 * 示例 2：
	 * <p>
	 * 输入：nums = [0,1,2,2,3,0,4,2], val = 2
	 * 输出：5, nums = [0,1,4,0,3]
	 * 解释：函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。注意这五个元素可为任意顺序。你不需要考虑数组中超出新长度后面的元素。
	 * <p>
	 * 提示：
	 * <p>
	 * 0 <= nums.length <= 100
	 * 0 <= nums[i] <= 50
	 * 0 <= val <= 100
	 */
	public static class RemoveElement {

		/**
		 * 个人思路:
		 * 直接按题意来说,需要遍历整个数组,然后找到指定的val值,并将其移动到数组的尾部
		 * 移动到尾部最好的方式是交换,与尾部元素进行交换,并记录已经交换过的元素位置,所以用双指针分别来指向需要交换的两个元素,
		 * 比较合适解决本题
		 *
		 * @param nums
		 * @param val
		 * @return
		 */
		public static int removeElement(int[] nums, int val) {
			int p = 0, q = nums.length - 1;
			while (p <= q) {
				while (q >= 0 && nums[q] == val) {
					q--;
				}
				if (q >= 0 && p <= q && nums[p] == val) {  // 交换
					swap(nums, p, q);
					q--;
				}
				p++;
			}
			return q + 1;
		}

		/**
		 * 在同时考虑p,q指针的移动时,会忽略很多种场景而出现问题
		 * 那么可以不考虑q指针的向前移动,q指针只做交换的预备位置
		 * <p>
		 * 看了官方思路后,发下元素可以不用移动,因为数组的后端元素不需要保留,可以直接使用复制,将不等于val的元素放在左边
		 *
		 * @param nums
		 * @param val
		 * @return
		 */
		public static int removeElementII(int[] nums, int val) {
			int p = 0, q = nums.length - 1;
			while (p <= q) {
				if (nums[p] == val) {
					swap(nums, p, q);  //nums[p]=nums[q]
					q--;
				} else {
					p++;
				}
			}
			return q + 1;
		}

		public static void swap(int[] nums, int i, int j) {
			int temp = nums[i];
			nums[i] = nums[j];
			nums[j] = temp;
		}

		public static void main(String[] args) {
			int[] nums = {2, 2, 3};
			int i = removeElement(nums, 2);

			int[] nums1 = {2, 2, 3};
			int i1 = removeElementII(nums1, 2);
			System.out.println(i1);
		}
	}

	/**
	 * 392. 判断子序列
	 * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
	 * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
	 * 进阶：
	 * 如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？
	 * 致谢：
	 * 特别感谢 @pbrother 添加此问题并且创建所有测试用例。
	 * 示例 1：
	 * 输入：s = "abc", t = "ahbgdc"
	 * 输出：true
	 * 示例 2：
	 * 输入：s = "axc", t = "ahbgdc"
	 * 输出：false
	 * 提示：
	 * 0 <= s.length <= 100
	 * 0 <= t.length <= 10^4
	 * 两个字符串都只由小写字符组成。
	 */
	public static class IsSubsequence {

		/**
		 * 个人思路:
		 * 判断s是否为t的子序列
		 * 即用一个指针在s上遍历,然后在t中进行寻找,t中也有一个指针在向后遍历,当t中找到了与s匹配的字符,则s的指针往后移动
		 * 当s上的指针移动到末尾时,表示s是t的子序列
		 *
		 * @param s
		 * @param t
		 * @return
		 */
		public static boolean isSubsequence(String s, String t) {
			// p为s字符串上的指针 q为t字符串上的指针
			int p = 0, q = 0;
			while (p < s.length() && q < t.length()) {
				if (s.charAt(p) == t.charAt(q)) {
					p++;
				}
				q++;
			}
			return p == s.length();
		}

		// 进阶思路中官方使用动态规划
		// 双指针方法中,需要不断的去找t中下一个能够匹配的字符
		// 可以对模式串t进行预处理操作,得到每个位置索引i处任意字符第一次出现的位置索引j
		// 这样一来,模式串在任意位置都能找到任意字符串下一个出现的位置,模式串t在指针遍历的过程中,就可以跳着遍历,类似KMP
		// 那么如何来得到这个函数f[i][j]呢?先通过普通的思路来求解.发现很繁琐,在一个位置索引i,寻找任意j字符下一次出现的问题,都需要
		// 遍历t才能得到.所以需要引出动态规划的思路.
		// f[i][j]的状态转移表达式
		// 1.当t[i]=j时,f[i][j]=i
		// 2.当t[i]!=j时,f[i][j]=f[i+1][j]
		// 那么反向计算时,i从t.length-1开始,当t[t.length-1]==j 则f[t.length-1][j]=t.length-1;否则不等于时,即在t的最后一个字符开始,
		// 找不到j字符,则不存在,设置值为t.length 超过索引界限,这样匹配过程就能直接退出
		private static int[][] getNext(String t) {
			int n = t.length();
			int[][] next = new int[n + 1][26];
			for (int i = 0; i < 26; ++i) {
				next[n][i] = n;
			}
			for (int i = n - 1; i >= 0; --i) {
				for (int j = 0; j < 26; ++j) {
					if (t.charAt(i) == j + 'a') { // i位置的字符等于j
						next[i][j] = i;
					} else {  // i位置的字符不等于j
						next[i][j] = next[i + 1][j];
					}
				}
			}
			return next;
		}

		public static boolean isSubsequenceOfficial(String s, String t) {
			int[][] next = getNext(t);
			int index = 0;
			for (int i = 0; i < s.length(); i++) {
				index = next[index][s.charAt(i) - 'a'];
				if (index == t.length()) {
					return false;
				} else index++;
			}
			return true;
		}

		public static void main(String[] args) {
			System.out.println(isSubsequenceOfficial("ace", "abcde"));
			System.out.println(isSubsequenceOfficial("aec", "abcde"));
		}
	}

	/**
	 * 167. 两数之和 II - 输入有序数组
	 * 给你一个下标从 1 开始的整数数组 numbers ，该数组已按 非递减顺序排列  ，请你从数组中找出满足相加之和等于目标数 target 的两个数。如果设这两个数分别是 numbers[index1] 和 numbers[index2] ，则 1 <= index1 < index2 <= numbers.length 。
	 * 以长度为 2 的整数数组 [index1, index2] 的形式返回这两个整数的下标 index1 和 index2。
	 * 你可以假设每个输入 只对应唯一的答案 ，而且你 不可以 重复使用相同的元素。
	 * 你所设计的解决方案必须只使用常量级的额外空间。
	 * 示例 1：
	 * 输入：numbers = [2,7,11,15], target = 9
	 * 输出：[1,2]
	 * 解释：2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。返回 [1, 2] 。
	 * 示例 2：
	 * 输入：numbers = [2,3,4], target = 6
	 * 输出：[1,3]
	 * 解释：2 与 4 之和等于目标数 6 。因此 index1 = 1, index2 = 3 。返回 [1, 3] 。
	 * 示例 3：
	 * 输入：numbers = [-1,0], target = -1
	 * 输出：[1,2]
	 * 解释：-1 与 0 之和等于目标数 -1 。因此 index1 = 1, index2 = 2 。返回 [1, 2] 。
	 * 提示：
	 * 2 <= numbers.length <= 3 * 104
	 * -1000 <= numbers[i] <= 1000
	 * numbers 按 非递减顺序 排列
	 * -1000 <= target <= 1000
	 * 仅存在一个有效答案
	 */
	public static class TwoSum {

		/**
		 * 个人思路:
		 * 因为是有序数组,双指针的过程中,可以判断是否还需要往后判断
		 *
		 * @param numbers
		 * @param target
		 * @return
		 */
		public static int[] twoSum(int[] numbers, int target) {
			int p = 0, q = numbers.length - 1;
			int[] arr = new int[2];
			for (; p < q; ++p) {
				for (int j = q; p < j; --j) {
					if (numbers[j] < target - numbers[p]) {
						break;
					} else if (numbers[j] == target - numbers[p]) {
						arr[0] = p + 1;
						arr[1] = j + 1;
						return arr;
					}
				}
			}
			return arr;
		}

		/**
		 * 数组是递增的,那么p,q两个指针是否可以同时移动呢?
		 * 当p指向第一个元素时,q从最后一个元素向左移动;
		 * 1.如果num[p]+num[q] > target 那么q需要往前移动 q--
		 * 2.如果num[p]+num[q] < target 那么p需要往后移动 q++
		 *
		 * @param numbers
		 * @param target
		 * @return
		 */
		public static int[] twoSumI(int[] numbers, int target) {
			int p = 0, q = numbers.length - 1;
			while (p < q) {
				if (numbers[p] + numbers[q] > target) {
					q--;
				} else if (numbers[p] + numbers[q] < target) {
					p++;
				} else {
					return new int[]{p + 1, q + 1};
				}
			}
			return new int[]{-1, -1};
		}

		/**
		 * 个人思路的方法没有运用到数组递增的特性
		 * p指针确定第一个元素时,那么需要从[p+1,n]中找到符合调钱的q,此时可以通过二分来查找
		 * 当然这一轮查找中可能找不到符合条件的q;此时p前进一位继续第二轮查找
		 * 时间复杂度是O(n*log n)
		 *
		 * @param numbers
		 * @param target
		 * @return
		 */
		public static int[] twoSumOfficial(int[] numbers, int target) {
			int p = 0;
			int[] arr = new int[2];
			for (; p < numbers.length; ++p) {
				int v = target - numbers[p];
				// 在[p+1...n]递增数组中查找符合条件的元素
				int s = p + 1, e = numbers.length - 1;
				while (s <= e) {
					int pivot = (e - s) / 2 + s;
					if (numbers[pivot] > v) { // 在左侧
						e = pivot - 1;
					} else if (numbers[pivot] < v) {
						s = pivot + 1;
					} else {
						return new int[]{p + 1, pivot + 1};
					}
				}
			}
			return arr;
		}

		public static void main(String[] args) {
			// int[] ints = twoSumOfficial(new int[]{3, 24, 50, 79, 88, 150, 345}, 200);
			int[] ints = twoSumOfficial(new int[]{2, 7, 11, 15}, 9);
			System.out.println(Arrays.toString(ints));
		}
	}

	/**
	 * 3. 无重复字符的最长子串
	 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长
	 * 子串
	 * 的长度。
	 * 示例 1:
	 * 输入: s = "abcabcbb"
	 * 输出: 3
	 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
	 * 示例 2:
	 * 输入: s = "bbbbb"
	 * 输出: 1
	 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
	 * 示例 3:
	 * 输入: s = "pwwkew"
	 * 输出: 3
	 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
	 * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
	 * 提示：
	 * 0 <= s.length <= 5 * 104
	 * s 由英文字母、数字、符号和空格组成
	 */
	public static class LengthOfLongestSubstring {

		/**
		 * 个人思路:
		 * 只能想到暴力匹配的方法,每前进一位然后遍历找出不重复的字符,时间负责度O(n^2)
		 * leetcode测试用例超时,得寻找更优的办法
		 *
		 * @param s
		 * @return
		 */
		public static int lengthOfLongestSubstring(String s) {
			if (s.length() == 0) return 0;
			if (s.length() == 1) return 1;
			Set<Character> set = new HashSet<>();
			int max = 0;
			for (int i = 0; i < s.length(); i++) {
				int p = i;
				for (int j = p; j < s.length(); j++) {
					if (set.contains(s.charAt(j))) { // 需要重复元素
						max = Math.max(max, j - p);
						set.clear();
						set.add(s.charAt(j));
						p = j;
					} else {
						set.add(s.charAt(j));
					}
				}
				max = Math.max(set.size(), max); // p移动到s字符串的末尾都没有遇见重复字符,则直接计算set元素的个数即为最大不重复子串的数量
				set.clear(); // 需要清空set
			}
			return max;
		}

		/**
		 * 滑动窗口思路
		 * 以[abcabcbb]字符串为例
		 * 按暴力匹配的思路,p指针每移动一个位置后找出后面最大的不重复子串
		 * (a)bcabcbb  -> (abc)abcbb
		 * a(b)cabcbb  -> a(bca)bcbb
		 * ab(c)abcbb  -> ab(cab)cbb
		 * abc(a)bcbb  -> abc(abc)bb
		 * abca(b)cbb  -> abca(bc)bb
		 * abcab(c)bb  -> abcab(cb)b
		 * abcabc(b)b  -> abcabc(b)b
		 * abcabcb(b)  -> abcabcb(b)
		 * <p>
		 * 这是上面暴力求解的步骤,能找到什么规律呢?
		 * 第一次index=0开始时,找到（abc)abcbb ,bc肯定不重复,那第二次从index=1寻找时,bc是不用继续判断是否重复的
		 * 此时只需要判断bc后面一位是否存在重复即可.p表示开始寻找点,q表示寻找到的点
		 * 1.如果q不存在重复,q继续往后移动,并放入set集合中
		 * 2.如果q存在重复,则p往后移动一位,并在set(已查找元素集合)中清除
		 * 在以上操作的过程中,记录最大的不重复子串元素个数
		 */
		public static int lengthOfLongestSubstringOfficial(String s) {
			if (s.length() == 0) return 0;
			int p = 0, q = 0;
			Set<Character> set = new HashSet<>();
			int max = 0;
			while (p < s.length() && q < s.length()) {
				if (!set.contains(s.charAt(q))) { // 不存在,放入set中,q继续往后移动一位
					set.add(s.charAt(q));
					q++;
					max = Math.max(max, q - p);
				} else { // 存在,p往后移动一位;同时将p元素移除set
					set.remove(s.charAt(p));
					p++;
				}
			}
			return max;
		}

		public static int lengthOfLongestSubstringOfficialI(String s) {
			if (s.length() == 0) return 0;
			int q = -1, max = 0;
			Set<Character> set = new HashSet<>();
			for (int i = 0; i < s.length(); ++i) {
				// 左指针向右移动一位时,清空该位置的元素
				if (i > 0) set.remove(s.charAt(i - 1));
				// 右指针连续向右移动
				while (q + 1 < s.length() && !set.contains(s.charAt(q + 1))) {  // 不存在,则右指针继续向右移动
					set.add(s.charAt(q + 1));
					q++;
				}
				// 当右指针p+1位置的元素为重复字符/p+1已经到了字符的末尾,计算本次的最大不重复子串元素个数
				max = Math.max(max, q - i + 1);
			}
			return max;
		}

		public static void main(String[] args) {
			System.out.println(lengthOfLongestSubstringOfficialI("abcbacbb"));
		}
	}

	/**
	 * 125. 验证回文串
	 * 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
	 * 字母和数字都属于字母数字字符。
	 * 给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。
	 * 示例 1：
	 * 输入: s = "A man, a plan, a canal: Panama"
	 * 输出：true
	 * 解释："amanaplanacanalpanama" 是回文串。
	 * 示例 2：
	 * 输入：s = "race a car"
	 * 输出：false
	 * 解释："raceacar" 不是回文串。
	 * 示例 3：
	 * 输入：s = " "
	 * 输出：true
	 * 解释：在移除非字母数字字符之后，s 是一个空字符串 "" 。
	 * 由于空字符串正着反着读都一样，所以是回文串。
	 * 提示：
	 * 1 <= s.length <= 2 * 105
	 * s 仅由可打印的 ASCII 字符组成
	 */
	public static class IsPalindrome {

		/**
		 * 个人思路：
		 * 双指针的同时,在判断是否数字字母后
		 * 1.是数字直接比较数字字符是否相同
		 * 2.是字母则转换为小写后比较是否相同
		 * 3.既不是数字也不是字母,指针向中间移动一位
		 * 总是比较左右两个指针
		 *
		 * @param s
		 * @return
		 */
		public static boolean isPalindrome(String s) {
			int p = 0, q = s.length() - 1;
			while (p < q) {
				Character cp, cq;
				if ((cp = execute(s.charAt(p))) == null) {
					p++;
					continue;
				}
				if ((cq = execute(s.charAt(q))) == null) {
					q--;
					continue;
				}
				if (!cq.equals(cp)) {
					return false;
				}
				p++;
				q--;
			}
			return true;
		}

		private static Character execute(char c) {
			if (c >= '0' && c <= '9') { // 数字
				return c;
			} else if (c >= 'a' && c <= 'z') {
				return c;
			} else if (c >= 'A' && c <= 'Z') {
				return Character.toLowerCase(c);
			} else {
				return null;
			}
		}

		/**
		 * 官方思路,先除去不需要的字符,然后翻转后对比两个字符串
		 *
		 * @param s
		 * @return
		 */
		public static boolean isPalindromeOfficial(String s) {
			StringBuffer sgood = new StringBuffer();
			int length = s.length();
			for (int i = 0; i < length; i++) {
				char ch = s.charAt(i);
				if (Character.isLetterOrDigit(ch)) {
					sgood.append(Character.toLowerCase(ch));
				}
			}
			StringBuffer sgood_rev = new StringBuffer(sgood).reverse();
			return sgood.toString().equals(sgood_rev.toString());
		}

		public static void main(String[] args) {
			System.out.println(isPalindrome("9,8"));
			System.out.println(isPalindromeOfficial("9,8"));
		}
	}

	/**
	 * 30. 串联所有单词的子串
	 * 给定一个字符串 s 和一个字符串数组 words。 words 中所有字符串 长度相同。
	 * s 中的 串联子串 是指一个包含  words 中所有字符串以任意顺序排列连接起来的子串。
	 * 例如，如果 words = ["ab","cd","ef"]， 那么 "abcdef"， "abefcd"，"cdabef"， "cdefab"，"efabcd"， 和 "efcdab" 都是串联子串。 "acdbef" 不是串联子串，因为他不是任何 words 排列的连接。
	 * 返回所有串联子串在 s 中的开始索引。你可以以 任意顺序 返回答案。
	 * 示例 1：
	 * 输入：s = "barfoothefoobarman", words = ["foo","bar"]
	 * 输出：[0,9]
	 * 解释：因为 words.length == 2 同时 words[i].length == 3，连接的子字符串的长度必须为 6。
	 * 子串 "barfoo" 开始位置是 0。它是 words 中以 ["bar","foo"] 顺序排列的连接。
	 * 子串 "foobar" 开始位置是 9。它是 words 中以 ["foo","bar"] 顺序排列的连接。
	 * 输出顺序无关紧要。返回 [9,0] 也是可以的。
	 * 示例 2：
	 * 输入：s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
	 * 输出：[]
	 * 解释：因为 words.length == 4 并且 words[i].length == 4，所以串联子串的长度必须为 16。
	 * s 中没有子串长度为 16 并且等于 words 的任何顺序排列的连接。
	 * 所以我们返回一个空数组。
	 * 示例 3：
	 * 输入：s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
	 * 输出：[6,9,12]
	 * 解释：因为 words.length == 3 并且 words[i].length == 3，所以串联子串的长度必须为 9。
	 * 子串 "foobarthe" 开始位置是 6。它是 words 中以 ["foo","bar","the"] 顺序排列的连接。
	 * 子串 "barthefoo" 开始位置是 9。它是 words 中以 ["bar","the","foo"] 顺序排列的连接。
	 * 子串 "thefoobar" 开始位置是 12。它是 words 中以 ["the","foo","bar"] 顺序排列的连接。
	 * 提示：
	 * 1 <= s.length <= 104
	 * 1 <= words.length <= 5000
	 * 1 <= words[i].length <= 30
	 * words[i] 和 s 由小写英文字母组成
	 */
	public static class FindSubstring {

		/**
		 * 本题使用滑动窗口根本没有思路,只能想到暴力匹配
		 * 1.窗口的右边每次滑动一个单词的长度
		 * 2.窗口的起点范围是[0...单词数组中每个单词的长度数]
		 *
		 * @param s
		 * @param words
		 * @return
		 */
		public static List<Integer> findSubstring(String s, String[] words) {
			List<Integer> res = new ArrayList<>();
			Map<String, Integer> wordsMap = new HashMap<>();
			if (s.length() == 0 || words.length == 0) return res;
			for (String word : words) {
				// 主串s中没有这个单词，直接返回空
				if (s.indexOf(word) < 0) return res;
				// map中保存每个单词，和它出现的次数
				wordsMap.put(word, wordsMap.getOrDefault(word, 0) + 1);
			}
			// 每个单词的长度， 总长度
			int oneLen = words[0].length(), wordsLen = oneLen * words.length;
			// 主串s长度小于单词总和，返回空
			if (wordsLen > s.length()) return res;
			// 只讨论从0，1，...， oneLen-1 开始的子串情况，
			// 每次进行匹配的窗口大小为 wordsLen，每次后移一个单词长度，由左右窗口维持当前窗口位置
			for (int i = 0; i < oneLen; ++i) {
				// 左右窗口
				int left = i, right = i, count = 0;
				// 统计每个符合要求的word
				Map<String, Integer> subMap = new HashMap<>();
				// 右窗口不能超出主串长度
				while (right + oneLen <= s.length()) {
					// 得到一个单词
					String word = s.substring(right, right + oneLen);
					// 有窗口右移
					right += oneLen;
					// words[]中没有这个单词，那么当前窗口肯定匹配失败，直接右移到这个单词后面
					if (!wordsMap.containsKey(word)) {
						left = right;
						// 窗口内单词统计map清空，重新统计
						subMap.clear();
						// 符合要求的单词数清0
						count = 0;
					} else {
						// 统计当前子串中这个单词出现的次数
						subMap.put(word, subMap.getOrDefault(word, 0) + 1);
						++count;
						// 如果这个单词出现的次数大于words[]中它对应的次数，又由于每次匹配和words长度相等的子串
						// 如 ["foo","bar","foo","the"]  "| foobarfoobar| foothe"
						// 第二个bar虽然是words[]中的单词，但是次数抄了，那么右移一个单词长度后 "|barfoobarfoo|the"
						// bar还是不符合，所以直接从这个不符合的bar之后开始匹配，也就是将这个不符合的bar和它之前的单词(串)全移出去
						while (subMap.getOrDefault(word, 0) > wordsMap.getOrDefault(word, 0)) {
							// 从当前窗口字符统计map中删除从左窗口开始到数量超限的所有单词(次数减一)
							String w = s.substring(left, left + oneLen);
							subMap.put(w, subMap.getOrDefault(w, 0) - 1);
							// 符合的单词数减一
							--count;
							// 左窗口位置右移
							left += oneLen;
						}
						// 当前窗口字符串满足要求
						if (count == words.length) res.add(left);
					}
				}
			}
			return res;
		}

		public static void main(String[] args) {
			System.out.println(findSubstring("barfoofoobarthefoobarman", new String[]{"bar", "foo", "the"}));
		}
	}

	/**
	 * 36. 有效的数独
	 * 请你判断一个 9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
	 * 数字 1-9 在每一行只能出现一次。
	 * 数字 1-9 在每一列只能出现一次。
	 * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
	 * 注意：
	 * 一个有效的数独（部分已被填充）不一定是可解的。
	 * 只需要根据以上规则，验证已经填入的数字是否有效即可。
	 * 空白格用 '.' 表示。
	 * 示例 1：
	 * 输入：board =
	 * [["5","3",".",".","7",".",".",".","."]
	 * ,["6",".",".","1","9","5",".",".","."]
	 * ,[".","9","8",".",".",".",".","6","."]
	 * ,["8",".",".",".","6",".",".",".","3"]
	 * ,["4",".",".","8",".","3",".",".","1"]
	 * ,["7",".",".",".","2",".",".",".","6"]
	 * ,[".","6",".",".",".",".","2","8","."]
	 * ,[".",".",".","4","1","9",".",".","5"]
	 * ,[".",".",".",".","8",".",".","7","9"]]
	 * 输出：true
	 * 示例 2：
	 * 输入：board =
	 * [["8","3",".",".","7",".",".",".","."]
	 * ,["6",".",".","1","9","5",".",".","."]
	 * ,[".","9","8",".",".",".",".","6","."]
	 * ,["8",".",".",".","6",".",".",".","3"]
	 * ,["4",".",".","8",".","3",".",".","1"]
	 * ,["7",".",".",".","2",".",".",".","6"]
	 * ,[".","6",".",".",".",".","2","8","."]
	 * ,[".",".",".","4","1","9",".",".","5"]
	 * ,[".",".",".",".","8",".",".","7","9"]]
	 * 输出：false
	 * 解释：除了第一行的第一个数字从 5 改为 8 以外，空格内其他数字均与 示例1 相同。 但由于位于左上角的 3x3 宫内有两个 8 存在, 因此这个数独是无效的。
	 * 提示：
	 * <p>
	 * board.length == 9
	 * board[i].length == 9
	 * board[i][j] 是一位数字（1-9）或者 '.'
	 */
	public static class IsValidSudoku {

		/**
		 * 个人思路:
		 * 对每个9宫格进行遍历时,同时判断行和列是否符合数独条件
		 *
		 * @param board
		 * @return
		 */
		public static boolean isValidSudoku(char[][] board) {
			// 每个九宫格的开始位置
			int[][] array = new int[][]{
					{0, 0},
					{0, 3},
					{0, 6},
					{3, 0},
					{3, 3},
					{3, 6},
					{6, 0},
					{6, 3},
					{6, 6}
			};
			HashMap<Integer, int[]> usedX = new HashMap<>(); // key->第x行,value->存在的值
			HashMap<Integer, int[]> usedY = new HashMap<>(); // key->第y列,value->存在的值
			for (int i = 0; i < array.length; i++) {
				int[] startPoint = array[i]; // 九宫格起始坐标
				int[] used = new int[10]; // 九宫格已出现过的数字,idx为数字,值为是否出现
				for (int k = startPoint[0]; k < startPoint[0] + 3; k++) {
					for (int l = startPoint[1]; l < startPoint[1] + 3; l++) {
						// 每个九宫格内部进行判断
						if (board[k][l] != '.') {
							// 判断九宫格、行、列是否出现过该数字
							int v = board[k][l] - '0';
							if (used[v] == 1 || (usedX.get(k) != null && usedX.get(k)[v] == 1) || usedY.get(l) != null && usedY.get(l)[v] == 1) {
								return false;
							}
							used[v] = 1;
							int[] arrX;
							if ((arrX = usedX.get(k)) == null) {
								arrX = new int[10];
							}
							arrX[v] = 1;
							usedX.put(k, arrX);
							int[] arrY;
							if ((arrY = usedY.get(l)) == null) {
								arrY = new int[10];
							}
							arrY[v] = 1;
							usedY.put(l, arrY);
						}
					}
				}
			}
			return true;
		}

		/**
		 * 官方思路中,最关键是点是,在遍历整个二维数组时,怎么知道当前遍历的位置是属于哪一个九宫格呢?
		 * i,j 九宫格的位置 [i/3] [j/3] 其中i为[0,2] j为[0,2]时其都出在同一个小九宫格中
		 * 0<= [i/3] [j/3] < 3
		 *
		 * @param board
		 * @return
		 */
		public static boolean isValidSudokuOfficial(char[][] board) {
			int[][] useRow = new int[9][9];  // 每行已经出现过的数字
			int[][] useCol = new int[9][9];  // 每列已经出现过的数字
			int[][][] useBox = new int[3][3][9]; // 每个小九宫格中出现过的数字
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (board[i][j] != '.') {
						int v = board[i][j] - '0' - 1;
						useRow[i][v]++;
						useCol[j][v]++;
						useBox[i / 3][j / 3][v]++;
						if (useRow[i][v] > 1 || useCol[j][v] > 1 || useBox[i / 3][j / 3][v] > 1) {
							return false;
						}
					}
				}
			}
			return true;
		}

		public static void main(String[] args) {
			System.out.println(isValidSudokuOfficial(new char[][]{
					{'8', '3', '.', '.', '7', '.', '.', '.', '.'},
					{'6', '.', '.', '1', '9', '5', '.', '.', '.'},
					{'.', '9', '8', '.', '.', '.', '.', '6', '.'},
					{'8', '.', '.', '.', '6', '.', '.', '.', '3'},
					{'4', '.', '.', '8', '.', '3', '.', '.', '1'},
					{'7', '.', '.', '.', '2', '.', '.', '.', '6'},
					{'.', '6', '.', '.', '.', '.', '2', '8', '.'},
					{'.', '.', '.', '4', '1', '9', '.', '.', '5'},
					{'.', '.', '.', '.', '8', '.', '.', '7', '9'}
			}));
		}
	}

	/**
	 * 283. 移动零
	 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
	 * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
	 * 示例 1:
	 * 输入: nums = [0,1,0,3,12]
	 * 输出: [1,3,12,0,0]
	 * 示例 2:
	 * 输入: nums = [0]
	 * 输出: [0]
	 * 提示:
	 * 1 <= nums.length <= 104
	 * -231 <= nums[i] <= 231 - 1
	 * 进阶：你能尽量减少完成的操作次数吗？
	 */
	public static class MoveZeroes {

		/**
		 * 原地移动,且尽量减少移动的次数,那么首先要想到的就是交换
		 * 设置i,j两个指针,i是游标指针,j是记录当前数组中首个0的位置
		 * 首先找到第一个出现的0,当这个0后面出现了第一个不为0的数,即交换他们的位置;次数不为0的数顺序不变
		 * 一直到i遍历完数组
		 * [0,1,0,3,12]
		 * [1,0,0,3,12]
		 * [1,3,0,0,12]
		 * [1,2,12,0,0]
		 *
		 * @param nums
		 */
		public static void moveZeroes(int[] nums) {
			int i = 0, j = 0;
			while (i < nums.length) {
				if (nums[i] == 0) {
					j = i;
					break;
				}
				i++;
			}
			i++;
			while (i < nums.length) {
				if (nums[i] != 0) {
					// swap
					int temp = nums[i];
					nums[i] = nums[j];
					nums[j] = temp;
					j++;
				}
				i++;
			}
		}

		public static void moveZeroesI(int[] nums) {
			int j = 0;
			for (int i = 0; i < nums.length; i++) {
				if (nums[i] == 0) {
					continue;
				}
				// swap
				if (i > j) {
					int temp = nums[i];
					nums[i] = nums[j];
					nums[j] = temp;
				}
				j++;
			}
		}

		public static void main(String[] args) {
			int[] arr = {2, 1};
			moveZeroesI(arr);
			System.out.println();
			List<Integer> list = new ArrayList<>();
			list.add(1);
			list.add(2);
			List<Integer> list1 = new ArrayList<>();
			list1.add(3);
			list1.addAll(list);
			list.clear();
			System.out.println(list1);
		}
	}
}
