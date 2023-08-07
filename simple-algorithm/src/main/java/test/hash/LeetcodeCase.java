package test.hash;

import java.io.Serializable;
import java.util.*;

public class LeetcodeCase {

	/**
	 * 242. 有效的字母异位词
	 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
	 * <p>
	 * 注意：若s 和 t中每个字符出现的次数都相同，则称s 和 t互为字母异位词。
	 * <p>
	 * <p>
	 * <p>
	 * 示例1:
	 * <p>
	 * 输入: s = "anagram", t = "nagaram"
	 * 输出: true
	 * 示例 2:
	 * <p>
	 * 输入: s = "rat", t = "car"
	 * 输出: false
	 * <p>
	 * <p>
	 * 提示:
	 * <p>
	 * 1 <= s.length, t.length <= 5 * 104
	 * s 和 t仅包含小写字母
	 * <p>
	 * <p>
	 * 进阶:如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？
	 * <p>
	 */
	static class IsAnagram {

		/**
		 * 个人思路:
		 * 使用hash,如果使用HashMap工具,子符为key,出现次数为value;最后匹配map的元素是不是完全一样
		 * <p>
		 * 但是此题针对26个小写字母,不需要借助hash,直接数组就能搞定
		 * 小写字母ASCII码 （ASCII码十进制输出 97~122）
		 * <p>
		 * 这里要注意一个点,由于先将s字符存在在数组中,然后遍历t字符,判断t中的每个字符,是否与s中一致
		 * 那s中多出来字符呢??? s中只要多出t中不存在的字符,则t中绝对会有一个字符的数量不对,故遍历完t
		 * 中的所有字符,都一致,则肯定满足条件
		 *
		 * @param s
		 * @param t
		 * @return
		 */
		public static boolean isAnagram(String s, String t) {
			if (s.length() != t.length())
				return false;
			int[] arr = new int[26];
			for (int i = 0; i < s.length(); i++) {
				arr[(int) s.charAt(i) - 'a']++;
			}
			for (int i = 0; i < t.length(); i++) {
				if (--arr[(int) t.charAt(i) - 'a'] < 0) {
					return false;
				}
			}
			return true;
		}

		/**
		 * 如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？
		 * 使用hash工具来解决
		 *
		 * @param s
		 * @param t
		 * @return
		 */
		public static boolean isAnagram_hash(String s, String t) {
			if (s.length() != t.length())
				return false;
			Map<Character, Integer> map = new HashMap<>();
			for (int i = 0; i < s.length(); i++) {
				if (!map.containsKey(s.charAt(i))) {
					map.put(s.charAt(i), 1);
				} else {
					map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
				}
			}

			for (int i = 0; i < t.length(); i++) {
				int v;
				if (map.get(t.charAt(i)) == null || (v = map.get(t.charAt(i)) - 1) < 0) {
					return false;
				}
				map.put(t.charAt(i), v);
			}
			return true;
		}

		/**
		 * 使用 HashMap.getOrDefault() 方法来解决不存在的key返回null的问题
		 *
		 * @param s
		 * @param t
		 * @return
		 */
		public static boolean isAnagram_hash_II(String s, String t) {
			if (s.length() != t.length())
				return false;
			Map<Character, Integer> map = new HashMap<>();

			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				map.put(c, map.getOrDefault(c, 0) + 1);
			}
			for (int i = 0; i < t.length(); i++) {
				char c = t.charAt(i);
				map.put(c, map.getOrDefault(c, 0) - 1);
				if (map.get(c) < 0) {
					return false;
				}
			}
			return true;
		}

		public static void main(String[] args) {

			boolean anagram = isAnagram_hash_II("aacc", "ccac");
			System.out.println(anagram);
		}
	}

	/**
	 * 349. 两个数组的交集
	 * 给定两个数组 nums1 和 nums2 ，返回 它们的交集 。输出结果中的每个元素一定是 唯一 的。我们可以 不考虑输出结果的顺序 。
	 * <p>
	 * <p>
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
	 * 输出：[2]
	 * 示例 2：
	 * <p>
	 * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
	 * 输出：[9,4]
	 * 解释：[4,9] 也是可通过的
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= nums1.length, nums2.length <= 1000
	 * 0 <= nums1[i], nums2[i] <= 1000
	 */
	static class Intersection {

		/**
		 * 按照常规的思路,寻找交集需要使用双层循环来获取,并且还不太好处理重复的元素
		 * <p>
		 * 借助hash,就会很好解决
		 *
		 * @param nums1
		 * @param nums2
		 * @return
		 */
		public static int[] intersection(int[] nums1, int[] nums2) {
			if (nums1 == null || nums2 == null) {
				return null;
			}
			Set<Integer> set = new HashSet<>();
			for (int i = 0; i < nums1.length; i++) {
				set.add(nums1[i]);
			}
			Set<Integer> re = new HashSet<>();
			for (int i = 0; i < nums2.length; i++) {
				if (set.contains(nums2[i])) {
					re.add(nums2[i]);
				}
			}
			int[] arr = new int[re.size()];
			Iterator<Integer> iterator = re.iterator();
			int j = 0;
			while (iterator.hasNext()) {
				arr[j] = iterator.next();
				j++;
			}
			return arr;
		}

		public static void main(String[] args) {
			int[] intersection = intersection(new int[]{4, 9, 5}, new int[]{9, 4, 9, 8, 4});
			System.out.println(Arrays.toString(intersection));
		}
	}

	/**
	 * 202. 快乐数
	 * 编写一个算法来判断一个数 n 是不是快乐数。
	 * <p>
	 * 「快乐数」 定义为：
	 * <p>
	 * 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
	 * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
	 * 如果这个过程 结果为 1，那么这个数就是快乐数。
	 * 如果 n 是 快乐数 就返回 true ；不是，则返回 false 。
	 * <p>
	 * <p>
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：n = 19
	 * 输出：true
	 * 解释：
	 * 1^2 + 9^2 = 82
	 * 8^2 + 2^2 = 68
	 * 6^2 + 8^2 = 100
	 * 1^2 + 0^2 + 0^2 = 1
	 * 示例 2：
	 * <p>
	 * 输入：n = 2
	 * 输出：false
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= n <= 2^31 - 1
	 */
	static class IsHappy {

		/**
		 * 个人思路：
		 * 1.每一次计算都需要记录,因为不是快乐数,会出现无限循环的情况?那为什么不会像无理数一样,出现无限不循环的场景呢?
		 * 2.每一位上的树相加,会出现溢出的情况么? 2^31-1 = 2,147,483,646 int类型的最大正整数值,最大的场景是:
		 * 999,999,999 -> 9*81=729 所以不会溢出
		 *
		 * <p>
		 * 通过hash存储每一次计算的结果,值为1则返回true,不可能为0
		 * 当计算的值,在hash中存在,则出现循环 返回false
		 *
		 * @param n
		 * @return
		 */
		public static boolean isHappy(int n) {
			Set<Integer> set = new HashSet<>();
			// 获取int的每一位的值
			// int v = n;
			while (true) {
				int sum = 0;
				while (n > 0) {
					int i = n % 10; // 个位
					n = n / 10;
					sum += i * i;
				}
				n = sum;
				if (n == 1)
					return true;
				else if (set.contains(n)) {
					return false;
				} else {
					set.add(n);
				}
			}
		}

		/**
		 * 官方解题思路,隐式链表的双指针,查看是否有环.
		 * 隐式链表 是指非链表结构,但是有逻辑连接关系的数据结构
		 * 1.如果为快乐数,则总有一步会等于0,而0后面都是0,所以fast指针会先到0节点
		 * 2.如果不为快乐数,则肯定会进入环中,快慢指针肯定会相等
		 * <p>
		 * 相比于第一种方式,时间复杂度和空间复杂度 都降低了不少,需要分析下复杂度.
		 * 空间复杂度,没有借助Hash 为O(1)
		 * 时间复杂度,双循环,看起来似乎跟使用Hash一样,但是Hash存在add和contains操作,需要话费额外的时间
		 *
		 * @param n
		 * @return
		 */
		public static boolean isHappyOfficial(int n) {
			int slow = n, fast = n;
			while (true) {
				slow = next(slow);
				fast = next(next(fast));
				if (fast == 1)
					return true;
				if (slow == fast)
					return false;
			}
		}

		/**
		 * 获取下一个值
		 *
		 * @param v
		 * @return
		 */
		private static int next(int v) {
			int sum = 0;
			while (v > 0) {
				int i = v % 10; // 个位
				v = v / 10;
				sum += i * i;
			}
			return sum;
		}

		public static void main(String[] args) {
			boolean happy = isHappyOfficial(19);
			System.out.println(happy);
			boolean happy1 = isHappyOfficial(2);
			System.out.println(happy1);
		}
	}

	/**
	 * 454. 四数相加 II
	 * 给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，请你计算有多少个元组 (i, j, k, l) 能满足：
	 * <p>
	 * 0 <= i, j, k, l < n
	 * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
	 * <p>
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
	 * 输出：2
	 * 解释：
	 * 两个元组如下：
	 * 1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1) + 2 = 0
	 * 2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1) + 0 = 0
	 * 示例 2：
	 * <p>
	 * 输入：nums1 = [0], nums2 = [0], nums3 = [0], nums4 = [0]
	 * 输出：1
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * n == nums1.length
	 * n == nums2.length
	 * n == nums3.length
	 * n == nums4.length
	 * 1 <= n <= 200
	 * -228 <= nums1[i], nums2[i], nums3[i], nums4[i] <= 228
	 */
	static class FourSumCount {

		/**
		 * 个人思路:
		 * 按题意,固定为4个等长数组,前两个数据两两组合,得到需要匹配的组合值 存放在HashMap中
		 * 然后剩下的2个数组也是两两组合,得到剩下的组合集合,如果其组合值满足存放在HashMap中的值,则可以匹配
		 * <p>
		 * 后面看了下官方解题,方案居然一样
		 */
		public static int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
			Map<Integer, Integer> map = new HashMap<>();
			int c = 0;
			for (int i = 0; i < nums1.length; i++) {
				for (int j = 0; j < nums2.length; j++) {
					int v1 = nums1[i] + nums2[j];
					map.put(-v1, map.getOrDefault(-v1, 0) + 1);
				}
			}
			for (int i = 0; i < nums3.length; i++) {
				for (int j = 0; j < nums4.length; j++) {
					int v2 = nums3[i] + nums4[j];
					if (map.containsKey(v2)) {
						c += map.get(v2);
					}
				}
			}
			return c;
		}

		public static void main(String[] args) {
			// int i = fourSumCount(new int[]{1, 2}, new int[]{-2, -1}, new int[]{-1, 2}, new int[]{0, 2});
			int i = fourSumCount(new int[]{0}, new int[]{0}, new int[]{0}, new int[]{0});
			System.out.println(i);
		}
	}

	/**
	 * 383. 赎金信
	 * <p>
	 * 给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
	 * <p>
	 * 如果可以，返回 true ；否则返回 false 。
	 * <p>
	 * magazine 中的每个字符只能在 ransomNote 中使用一次。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：ransomNote = "a", magazine = "b"
	 * 输出：false
	 * 示例 2：
	 * <p>
	 * 输入：ransomNote = "aa", magazine = "ab"
	 * 输出：false
	 * 示例 3：
	 * <p>
	 * 输入：ransomNote = "aa", magazine = "aab"
	 * 输出：true
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= ransomNote.length, magazine.length <= 105
	 * ransomNote 和 magazine 由小写英文字母组成
	 */
	static class CanConstruct {

		/**
		 * 个人思路:
		 * 典型的Hash思想最简单,ransomNote是magazine的子集,ransomNote的所有字母都在magazine中能够找到,
		 * 且重复字母也一样
		 * <p>
		 * 循环String,使用charAt获取char 比String转byte数组,然后循环byte数组消耗的内存更少
		 * ransomNote.getBytes() 要进行encode操作 而charAt()就是获取String底层的char数组,少了一步转byte的操作
		 * 但是char 也有转int的操作
		 *
		 * @param ransomNote
		 * @param magazine
		 * @return
		 */
		public static boolean canConstruct(String ransomNote, String magazine) {
			int[] arr = new int[26];
			for (int i = 0; i < magazine.length(); i++) {
				arr[magazine.charAt(i) - 97]++;
			}
			for (int i = 0; i < ransomNote.length(); i++) {
				if (--arr[ransomNote.charAt(i) - 97] < 0) {
					return false;
				}
			}
			return true;
		}

		public static void main(String[] args) {
			boolean b = canConstruct("aac", "aab");
			System.out.println(b);
		}
	}
}
