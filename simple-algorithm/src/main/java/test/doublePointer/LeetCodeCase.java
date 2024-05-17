package test.doublePointer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
}
