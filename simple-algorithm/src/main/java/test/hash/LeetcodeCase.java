package test.hash;

import com.sun.javafx.logging.JFRInputEvent;
import sun.awt.util.IdentityLinkedList;

import javax.swing.text.html.parser.Entity;
import java.util.*;
import java.util.stream.Collectors;

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

	/**
	 * 15. 三数之和
	 * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0。
	 * 请你返回所有和为 0 且不重复的三元组。
	 * <p>
	 * 注意：答案中不可以包含重复的三元组。
	 * <p>
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：nums = [-1,0,1,2,-1,-4]
	 * 输出：[[-1,-1,2],[-1,0,1]]
	 * 解释：
	 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
	 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
	 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
	 * 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
	 * 注意，输出的顺序和三元组的顺序并不重要。
	 * 示例 2：
	 * <p>
	 * 输入：nums = [0,1,1]
	 * 输出：[]
	 * 解释：唯一可能的三元组和不为 0 。
	 * 示例 3：
	 * <p>
	 * 输入：nums = [0,0,0]
	 * 输出：[[0,0,0]]
	 * 解释：唯一可能的三元组和为 0 。
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 3 <= nums.length <= 3000
	 * -105 <= nums[i] <= 105
	 */
	static class ThreeSum {

		/**
		 * 个人思路:
		 * 1.首先通过题意的分析,i,j,k不能有任意一个相同,则说明数组中的元素不能重复使用
		 * 2.其次[答案中不可以包含重复的三元组],则说明三元组不能完全一样
		 * 现将前两个元素两两组合,得到最后一个元素的值,当最后一个元素有多个时,也只能算一种
		 * 两两组合元素时,会重复,需要去重处理
		 * <p>
		 * 思路错误,量来组合匹配第三个数时,重复场景问题没法解决,需要大量处理来实现,增加了很多工作量
		 *
		 * @param nums
		 * @return
		 */
		public static List<List<Integer>> threeSum(int[] nums) {
			List<List<Integer>> list = new ArrayList<>();
			Map<String, Integer> map = new HashMap<>();
			Map<Integer, Integer> set = new HashMap<>();
			for (int i = 0; i < nums.length; i++) {
				for (int j = i + 1; j < nums.length; j++) {
					String key = getKey(nums[i], nums[j]);
					if (!map.containsKey(key)) {
						map.put(key, (nums[i] + nums[j]));
					}
				}
				set.put(nums[i], set.getOrDefault(nums[i], 0) + 1);
			}
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				String key = entry.getKey();
				String[] s = key.split("\\|");
				Integer n1 = Integer.valueOf(s[0]);
				Integer n2 = Integer.valueOf(s[1]);
				set.put(n1, set.getOrDefault(n1, 0) - 1);
				set.put(n2, set.getOrDefault(n2, 0) - 1);
				Integer v = -entry.getValue();
				Integer v1;
				if ((v1 = set.get(v)) != null && v1 > 0) {
					list.add(Arrays.asList(n1, n2, v));
					set.put(v1, set.getOrDefault(v1, 0) - 1);
				}
				set.put(n1, set.getOrDefault(n1, 0) + 1);
				set.put(n2, set.getOrDefault(n2, 0) + 1);

			}
			return list;
		}

		private static String getKey(Integer i, Integer j) {
			String key;
			if (i >= j) {
				key = i + "|" + j;
			} else {
				key = j + "|" + i;
			}
			return key;
		}

		/**
		 * 官方
		 * 正确的思路应该是双指针,但是关键在于思路的分析,如果想到这样做?为什么这样做是对的?思路的推导过程是怎样的?
		 * 按常规思路,先知道到所有的前两个元素的2元祖,然后匹配剩下的元素,找到匹配的三元组,最后进行去重,题意要求,三元组的元素不能重复.
		 * 官方思路是声明二元组时,将第三个元素定位在数组的末端,数组是升序的,三个元素分别设为 a,b,c
		 * -(a+b) = x c从右往左移动到c'时,有c >= c',排序后c从大往小遍历,能容易的避免重复
		 * 1.如果c < x 则[a,b] 找不到匹配的c,二元组变成 [a,b']
		 * 2.如果c > x 则要找到c' < c 满足情况
		 * 3.如果c = x 则直接将二元组变成 [a,b']
		 * 以上情况,需要判断b != b' c != c' 来排除重复场景 还有最重要的一点,就是a和b在移动时,c不需要重新回位到尾部
		 * 当[a,b]元祖移动到 [a,b'] 时,有 a+b < a+b',之前是-(a+b) < c 那么 -(a+b) 变小,则c'变小才能符合,所以c'在之前c的基础上
		 * 向左移动
		 * <p>
		 * 官方示例节省时间的原因是,控制c位置的while循环体中,处理逻辑很少
		 *
		 * @param nums
		 * @return
		 */
		public static List<List<Integer>> threeSumOfficial(int[] nums) {
			List<List<Integer>> list = new ArrayList<>();
			Arrays.sort(nums);
			for (int i = 0; i < nums.length; i++) {
				if (nums[i] > 0) {
					break;
				}
				if (i > 0 && nums[i] == nums[i - 1]) {
					continue;
				}
				int k = nums.length - 1;
				for (int j = i + 1; j < nums.length; j++) {
					if (j > i + 1 && nums[j] == nums[j - 1]) {
						continue;
					}
					int v = -(nums[i] + nums[j]);
					while (j < k) {
						if (nums[k] > v) {
							k--;
						} else if (nums[k] < v) {
							break;
						} else {
							List<Integer> l = new ArrayList<>();
							l.add(nums[i]);
							l.add(nums[j]);
							l.add(nums[k]);
							list.add(l);
							k--;
							break;
						}
					}
				}
			}
			return list;
		}

		public static List<List<Integer>> threeSumOfficialII(int[] nums) {
			List<List<Integer>> list = new ArrayList<>();
			Arrays.sort(nums);
			for (int i = 0; i < nums.length; i++) {
				if (nums[i] > 0) {
					break;
				}
				if (i > 0 && nums[i] == nums[i - 1]) {
					continue;
				}
				int k = nums.length - 1;
				for (int j = i + 1; j < nums.length; j++) {
					if (j > i + 1 && nums[j] == nums[j - 1]) {
						continue;
					}
					int v = -(nums[i] + nums[j]);
					while (j < k && nums[k] > v) {  // while循环只来确定k的位置
						k--;
					}
					if (j == k) break;
					if (nums[k] == v) {
						List<Integer> l = new ArrayList<>();
						l.add(nums[i]);
						l.add(nums[j]);
						l.add(nums[k]);
						list.add(l);
					}
				}
			}
			return list;
		}

		public static void main(String[] args) {
			List<List<Integer>> lists = threeSumOfficialII(new int[]{-1, 0, 1, 2, -1, -4});
			System.out.println();

			Long a = null, b = 20L;
			long l = a - b;
			System.out.println(l);
		}
	}

	/**
	 * 18. 四数之和
	 * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组
	 * [nums[a], nums[b], nums[c], nums[d]] （若两个四元组元素一一对应，则认为两个四元组重复）：
	 * <p>
	 * 0 <= a, b, c, d < n
	 * a、b、c 和 d 互不相同
	 * nums[a] + nums[b] + nums[c] + nums[d] == target
	 * 你可以按 任意顺序 返回答案 。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：nums = [1,0,-1,0,-2,2], target = 0
	 * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
	 * 示例 2：
	 * <p>
	 * 输入：nums = [2,2,2,2,2], target = 8
	 * 输出：[[2,2,2,2]]
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= nums.length <= 200
	 * -109 <= nums[i] <= 109
	 * -109 <= target <= 109
	 */
	static class fourSum {

		/**
		 * 个人思路:
		 * 跟三数之和一样的思路,三数之和是确认前两个元素,然后第三个元素中数组的尾部往左推动,当二元组向后推动时,第三个元素无需回到尾部.
		 * 四数之和的思路是,先声明前三个元素的三元组,在三元组向右移动时,第四个元素从尾部向左推动.
		 *
		 * @param nums
		 * @param target
		 * @return
		 */
		public static List<List<Integer>> fourSum(int[] nums, int target) {
			int n = nums.length;
			List<List<Integer>> re = new ArrayList<>();
			if (n < 4) {
				return re;
			}
			Arrays.sort(nums);
			for (int i = 0; i <= n - 4; i++) { // i的位置后面得保留最少4个元素
				if (i > 0 && nums[i] == nums[i - 1]) {
					continue;
				}
				if ((long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) { // 如果连续四个元数的和已经比target大,则不需要再找了,已经不符合条件
					break;
				}
				if ((long) nums[i] + nums[n - 3] + nums[n - 2] + nums[n - 1] < target) { //如果i元组+最后的3个元素还比target小,那么对于这个i不可能存在答案,继续寻找下一个i
					continue;
				}
				for (int j = i + 1; j <= n - 3; j++) { // j的位置后面得保留最少3个元素
					if (j > i + 1 && nums[j] == nums[j - 1]) {
						continue;
					}
					if ((long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) { // 对于[i,j]二元组,后面连续两个元素之和比target大,那么不需要再遍历,需要换个i才行
						break;
					}
					if ((long) nums[i] + nums[j] + nums[n - 2] + nums[n - 1] < target) { //对于[i,j]二元组,加上最后的两个元素都比target小,那么这个j需要往右移动
						continue;
					}
					for (int k = j + 1; k <= n - 2; k++) {
						if (k > j + 1 && nums[k] == nums[k - 1]) {
							continue;
						}
						long v = (long) target - ((long) nums[i] + nums[j] + nums[k]);
						int l = n - 1;
						while (l > k && nums[l] > v) {
							l--;
						}
						if (l == k) break; // k和l重合了,则三元组需要移动
						if (nums[l] == v) {
							List<Integer> list = new ArrayList<>();
							list.add(nums[i]);
							list.add(nums[j]);
							list.add(nums[k]);
							list.add(nums[l]);
							re.add(list);
						}
					}
				}
			}
			return re;
		}

		public static void main(String[] args) {
			List<List<Integer>> lists = fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0);
			System.out.println(lists);
		}
	}

	/**
	 * 387. 字符串中的第一个唯一字符
	 * 给定一个字符串 s ，找到 它的第一个不重复的字符，并返回它的索引 。如果不存在，则返回 -1 。
	 * 示例 1：
	 * 输入: s = "leetcode"
	 * 输出: 0
	 * 示例 2:
	 * 输入: s = "loveleetcode"
	 * 输出: 2
	 * 示例 3:
	 * 输入: s = "aabb"
	 * 输出: -1
	 * 提示:
	 * 1 <= s.length <= 105
	 * s 只包含小写字母
	 */
	public static class FirstUniqChar {

		/**
		 * 个人思路:
		 * 左指针遍历整个字符串,然后在左指针往右寻是否有重复的元素,在寻找的过程中,很多元素被重复搜索,可以借助hash表来实现
		 * 不用hash表,因为小写字符只有26个,通过数组也可以实现
		 *
		 * @param s
		 * @return
		 */
		public static int firstUniqChar(String s) {
			Map<Character, Integer> map = new HashMap<>();
			for (int i = 0; i < s.length(); ++i) {
				if (map.containsKey(s.charAt(i))) {//
					map.put(s.charAt(i), 1);
				} else {
					map.put(s.charAt(i), 0);
				}
			}
			int v = 0;
			for (; v < s.length(); ++v) {
				if (map.get(s.charAt(v)) == 0) {  // 返回第一个不重复的字符
					return v;
				}
			}
			return -1;
		}

		/**
		 * 通过数组来存储字符出现的次数
		 *
		 * @param s
		 * @return
		 */
		public static int firstUniqCharI(String s) {
			int[] arr = new int[26];
			for (int i = 0; i < s.length(); ++i) {
				int index = s.charAt(i) - 'a';
				arr[index] = arr[index] + 1;
			}
			int v = 0;
			for (; v < s.length(); ++v) {
				if (arr[s.charAt(v) - 'a'] == 1) {
					return v;
				}
			}
			return -1;
		}

		/**
		 * 通过hashMap 字符作为key,字符的索引作为value
		 * 此时该字符出现一次则记录索引,出现多次则为-1
		 * 最后遍历map,找到不为-1的最小值
		 *
		 * @param s
		 * @return
		 */
		public static int firstUniqCharOfficial(String s) {
			Map<Character, Integer> map = new HashMap<>();
			for (int i = 0; i < s.length(); ++i) {
				if (map.containsKey(s.charAt(i))) {
					map.put(s.charAt(i), -1);
				} else {
					map.put(s.charAt(i), i);
				}
			}
			int v = s.length();
			for (Map.Entry<Character, Integer> entry : map.entrySet()) {
				if (entry.getValue() != -1) {
					v = Math.min(v, entry.getValue());
				}
			}
			return v == s.length() ? -1 : v;
		}

		public static void main(String[] args) {
			System.out.println(firstUniqCharOfficial("loveleetcode"));
		}
	}

	/**
	 * 205. 同构字符串
	 * 给定两个字符串 s 和 t ，判断它们是否是同构的。
	 * 如果 s 中的字符可以按某种映射关系替换得到 t ，那么这两个字符串是同构的。
	 * 每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。不同字符不能映射到同一个字符上，相同字符只能映射到同一个字符上，字符可以映射到自己本身。
	 * 示例 1:
	 * 输入：s = "egg", t = "add"
	 * 输出：true
	 * 示例 2：
	 * 输入：s = "foo", t = "bar"
	 * 输出：false
	 * 示例 3：
	 * 输入：s = "paper", t = "title"
	 * 输出：true
	 * 提示：
	 * 1 <= s.length <= 5 * 104
	 * t.length == s.length
	 * s 和 t 由任意有效的 ASCII 字符组成
	 */
	public static class Isomorphic {

		/**
		 * 个人思路:
		 * 按题意来看,需要存储字符的映射;
		 *
		 * @param s
		 * @param t
		 * @return
		 */
		public static boolean isSomorphic(String s, String t) {
			Map<Character, Character> map = new HashMap<>();
			for (int i = 0; i < s.length(); ++i) {
				if (map.containsKey(s.charAt(i))) { // map中已存在,判断映射关系是否正确
					if (t.charAt(i) != map.get(s.charAt(i))) {
						return false;
					}
				} else {
					// map得key中不存在,得看下t此时的字符是否出现在value中
					if (map.containsValue(t.charAt(i))) {
						return false;
					}
					map.put(s.charAt(i), t.charAt(i));
				}
			}
			return true;
		}

		public static void main(String[] args) {
			System.out.println(isSomorphic("badc", "baba"));
		}
	}

	/**
	 * 409. 最长回文串
	 * 给定一个包含大写字母和小写字母的字符串 s ，返回 通过这些字母构造成的 最长的回文串 。
	 * 在构造过程中，请注意 区分大小写 。比如 "Aa" 不能当做一个回文字符串。
	 * 示例 1:
	 * 输入:s = "abccccdd"
	 * 输出:7
	 * 解释:
	 * 我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。
	 * 示例 2:
	 * 输入:s = "a"
	 * 输出:1
	 * 示例 3：
	 * 输入:s = "aaaaaccc"
	 * 输出:7
	 * 提示:
	 * <p>
	 * 1 <= s.length <= 2000
	 * s 只由小写 和/或 大写英文字母组成
	 */
	public static class LongestPalindrome {

		/**
		 * 个人思路:
		 * 构建回文串,即构建一个字符串,它从中间往两边延展是完全对称的
		 * 也就是找到一个字符,必须找到一个对应的字符;
		 * 1.当找不到对应字符时,最长回文串就是找到对应字符数+1
		 * 2.如果可以找到字符一直到字符串结束,最长回文串就是找到对应字符数
		 * @param s
		 * @return
		 */
		public static int longestPalindrome(String s) {
			Map<Character, Integer> map = new HashMap<>();
			for (int i = 0; i < s.length(); ++i) {
				if (map.containsKey(s.charAt(i))) {
					map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
				} else {
					map.put(s.charAt(i), 1);
				}
			}
			int v = 0;
			for (Map.Entry<Character, Integer> entry : map.entrySet()) {
				v += entry.getValue() / 2;
			}
			return 2 * v < s.length() ? 2 * v + 1 : 2 * v;
		}

		public static int longestPalindromeI(String s) {
			int[] arr = new int[52];
			for (int i = 0; i < s.length(); ++i) {
				int index = s.charAt(i) <= 90 ? s.charAt(i) - 65 : s.charAt(i) - 71;
				arr[index] += 1;
			}
			int v = 0;
			for (int i = 0; i < arr.length; ++i) {
				if (arr[i] != 0) v += arr[i] / 2;
			}
			return v * 2 < s.length() ? v * 2 + 1 : v * 2;
		}

		public static void main(String[] args) {
			System.out.println(longestPalindromeI("abccccdd"));
		}
	}

	/**
	 * 290. 单词规律
	 * 给定一种规律 pattern 和一个字符串 s ，判断 s 是否遵循相同的规律。
	 * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 s 中的每个非空单词之间存在着双向连接的对应规律。
	 * 示例1:
	 * 输入: pattern = "abba", s = "dog cat cat dog"
	 * 输出: true
	 * 示例 2:
	 * 输入:pattern = "abba", s = "dog cat cat fish"
	 * 输出: false
	 * 示例 3:
	 * 输入: pattern = "aaaa", s = "dog cat cat dog"
	 * 输出: false
	 * 提示:
	 * 1 <= pattern.length <= 300
	 * pattern 只包含小写英文字母
	 * 1 <= s.length <= 3000
	 * s 只包含小写英文字母和 ' '
	 * s 不包含 任何前导或尾随对空格
	 * s 中每个单词都被 单个空格 分隔
	 */
	public static class WordPattern {

		/**
		 * 通过hash存储映射关系 key是pattern中的单个字符,value是映射的单词
		 * <p>
		 * 太多要考虑的细节,很容易忽略
		 *
		 * @param pattern
		 * @param s
		 * @return
		 */
		public static boolean wordPattern(String pattern, String s) {
			Map<Character, String> map = new HashMap<>();
			StringBuilder stringBuilder = new StringBuilder();
			int j = 0;
			for (int i = 0; i < s.length() + 1; ++i) {
				if (i == s.length() || s.charAt(i) == ' ') {
					String word = stringBuilder.toString();
					if (j >= pattern.length()) {
						return false;
					}
					if (!map.containsKey(pattern.charAt(j))) {  // map中不存在,则放入;但是如果value已经存在也不行
						if (map.containsValue(word)) {
							return false;
						}
						map.put(pattern.charAt(j), word);
					} else { // map中存在则匹配
						if (!map.get(pattern.charAt(j)).equals(word)) {
							return false;
						}
					}
					j++;
					stringBuilder.delete(0, stringBuilder.length());
				} else {
					stringBuilder.append(s.charAt(i));
				}
			}
			return j >= pattern.length();
		}

		/**
		 * 细节
		 * 1.匹配串和原串 数量对应
		 * 2.匹配串和原串 一一对应
		 *
		 * @param pattern
		 * @param s
		 * @return
		 */
		public static boolean wordPatternI(String pattern, String s) {
			Map<Character, String> map = new HashMap<>();
			String[] s1 = s.split(" ");
			if (s1.length != pattern.length()) {
				return false;
			}
			for (int i = 0; i < s1.length; i++) {
				if (!map.containsKey(pattern.charAt(i))) {
					if (map.containsValue(s1[i])) {
						return false;
					}
					map.put(pattern.charAt(i), s1[i]);
				} else {
					if (!map.get(pattern.charAt(i)).equals(s1[i])) {
						return false;
					}
				}
			}
			return true;
		}

		public static void main(String[] args) {
			boolean b = wordPattern("jquery", "jquery");
			System.out.println(b);
		}
	}

	/**
	 * 49. 字母异位词分组
	 * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
	 * 字母异位词 是由重新排列源单词的所有字母得到的一个新单词。
	 * 示例 1:
	 * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
	 * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
	 * 示例 2:
	 * 输入: strs = [""]
	 * 输出: [[""]]
	 * 示例 3:
	 * 输入: strs = ["a"]
	 * 输出: [["a"]]
	 * 提示：
	 * 1 <= strs.length <= 104
	 * 0 <= strs[i].length <= 100
	 * strs[i] 仅包含小写字母
	 */
	public static class GroupAnagrams {

		/**
		 * 题目要求:由相同字母组成的单词放在同一个集合里面
		 * 用什么方式判断这些单词是不是由同样的字母构成呢?
		 * 1.排序后比较字符串是否相同
		 * 2.计算字符hash值 单词最多100个字母,假设从a-z hash值是[2^0-2^25] 最大为2^26
		 * 一直int最大为2^31-1 所以用int足够;并且这样组合相加的字母，不会出现碰撞
		 * 使用2的指数幂作为hash,每个字母只出现一次是不会重复的,每个字母可以出现多次,则需要计算字母的个数是否一样
		 * 后来发现就算字母数一样,也会有字母不同的情况,比如 :
		 * hash[h]=128;
		 * hash[i]=256;
		 * hash[t]=524288
		 * hash[u]=1048576
		 * 那么出现
		 * hash[t]+hash[t] =hash[u]
		 * hash[h]+hash[h]=hash[i]
		 * 则 hash[t]+hash[i]+hash[t] =hash[h]+hash[u]+hash[h]
		 * 字母数相同,计算出来的hash值也想同出现了碰撞,那通过hash的思路很难解决碰撞问题
		 * 但是要解决碰撞,又要使用一些方案,比如开发地址法、多次hash法、链表地址法等等
		 * <p>
		 * 我们可以使用再hash方式
		 *
		 * @param strs
		 * @return
		 */
		public static List<List<String>> groupAnagrams(String[] strs) {
			Map<String, List<String>> map = new HashMap<>();
			for (int i = 0; i < strs.length; i++) {
				String key = getHash(strs[i]) + "-" + strs[i].length() + '-' + getHash1(strs[i]);
				if (!map.containsKey(key)) {
					List<String> list = new ArrayList<>();
					list.add(strs[i]);
					map.put(key, list);
				} else {
					map.get(key).add(strs[i]);
				}
			}
			return new ArrayList<>(map.values());
		}

		private static int getHash(String s) {
			int hash = 0;
			for (int i = 0; i < s.length(); i++) {
				hash += 1 << s.charAt(i) - 'a';
			}
			return hash;
		}

		private static int getHash1(String s) {
			int hash = 0;
			for (int i = 0; i < s.length(); i++) {
				hash += s.charAt(i) - 'a' + 1;
			}
			return hash;
		}

		/**
		 * 当然本题的思路也可以不用hash,判断两个单词是否完全使用同样的字母组成
		 * 可以对单词进行排序,然后比较其是否一样 排序时间复杂度O(log n)
		 *
		 * @param strs
		 * @return
		 */
		public static List<List<String>> groupAnagramsI(String[] strs) {
			Map<String, List<String>> map = new HashMap<>();
			for (int i = 0; i < strs.length; i++) {
				char[] charArray = strs[i].toCharArray();
				Arrays.sort(charArray);
				// computeIfAbsent 不存在则计算一个value并put到map中,存在则返回value
				map.computeIfAbsent(String.valueOf(charArray), x -> new ArrayList<>()).add(strs[i]);
			}
			return new ArrayList<>(map.values());
		}

		/**
		 * 用前26个质数表示字母a-z ,然后字母对应的质数相乘代表hash 因为质数不能被出本身和1以外的数整除,所以质数相乘之后,
		 * 只能拆分出唯一解的因子集合
		 * 前26个质数
		 * 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101
		 * 显然,很容易就溢出long类型的最大取值范围
		 *
		 * @param strs
		 * @return
		 */
		public static List<List<String>> groupAnagramsII(String[] strs) {
			return null;
		}

		/**
		 * 官方思路
		 * 在比较单词是否为相同的字母组成时,使用字母计数的方式,记录每个字母的个数,转换成字符串作为key
		 *
		 * @param strs
		 * @return
		 */
		public static List<List<String>> groupAnagramsOfficial(String[] strs) {
			Map<String, List<String>> map = new HashMap<>();
			for (int i = 0; i < strs.length; i++) {
				String str = strs[i];
				int[] arr = new int[26];
				for (int j = 0; j < str.length(); j++) {
					arr[str.charAt(j) - 'a']++;
				}
				StringBuilder sb = new StringBuilder();
				for (int k = 0; k < 26; k++) {
					if (arr[k] > 0) {
						sb.append((char) (k + 'a'));  // 字符个数  作为key
						sb.append(arr[k]);
					}
				}
				map.computeIfAbsent(sb.toString(), x -> new ArrayList<>()).add(str);
			}
			return new ArrayList<>(map.values());
		}

		public static void main(String[] args) {
			List<List<String>> lists = groupAnagramsOfficial(new String[]{"eat", "tea", "tan", "ate", "nat", "bat", "ac", "bd", "aac", "bbd", "aacc", "bbdd", "acc", "bdd"});
			System.out.println(lists);
		}
	}

	/**
	 * 219. 存在重复元素 II
	 * 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，满足 nums[i] == nums[j] 且 abs(i - j) <= k 。
	 * 如果存在，返回 true ；否则，返回 false 。
	 * 示例 1：
	 * 输入：nums = [1,2,3,1], k = 3
	 * 输出：true
	 * 示例 2：
	 * 输入：nums = [1,0,1,1], k = 1
	 * 输出：true
	 * 示例 3：
	 * 输入：nums = [1,2,3,1,2,3], k = 2
	 * 输出：false
	 * 提示：
	 * 1 <= nums.length <= 105
	 * -109 <= nums[i] <= 109
	 * 0 <= k <= 105
	 */
	public static class ContainsNearbyDuplicate {

		/**
		 * 个人思路
		 * 用map存储每个元素前一次出现的位置preIndex,如果下一次出现的index-preIndex > k 则更新index为preIndex
		 * 因为preIndex不会出现符合条件的下一个元素了.
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static boolean containsNearbyDuplicate(int[] nums, int k) {
			Map<Integer, Integer> map = new HashMap<>();
			for (int i = 0; i < nums.length; i++) {
				if (!map.containsKey(nums[i])) {
					map.put(nums[i], i);
				} else {
					if (i - map.get(nums[i]) <= k) return true;
					else map.put(nums[i], i);
				}
			}
			return false;
		}

		/**
		 * 滑动窗口解题
		 * 窗口中元素的个数为 k+1,窗口的尾部为i,则头部index=max(0,i-k)
		 * 依次滑动窗口,如果窗口中有重复的元素则说明满足条件
		 * 用set保存窗口中的元素,窗口滑动一格,则删除尾部元素,加入新的元素;如果加入新的元素已存在则返回true;不存在则继续滑动窗口
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static boolean containsNearbyDuplicateOfficial(int[] nums, int k) {
			Set<Integer> set = new HashSet<>();
			// 窗口的右边界从k开始,一直到数组的结尾
			for (int i = 0; i < nums.length; i++) {
				if (i > k) { // 小于k的元素初始化加入到set中,模拟窗口的右边界从k开始
					set.remove(nums[i - 1 - k]);
				}
				if (!set.add(nums[i])) {
					return true;
				}
			}
			return false;
		}

		public static void main(String[] args) {
			System.out.println(containsNearbyDuplicate(new int[]{1, 2, 3, 4, 1, 2}, 4));
		}
	}

	/**
	 * 128. 最长连续序列
	 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
	 * 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
	 * 示例 1：
	 * 输入：nums = [100,4,200,1,3,2]
	 * 输出：4
	 * 解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
	 * 示例 2：
	 * 输入：nums = [0,3,7,2,5,8,4,6,0,1]
	 * 输出：9
	 * 提示：
	 * 0 <= nums.length <= 105
	 * -109 <= nums[i] <= 109
	 */
	public static class LongestConsecutive {

		/**
		 * 个人思路:
		 * 找出数字连续最长的序列长度
		 *
		 * @param nums
		 * @return
		 */
		public static int longestConsecutive(int[] nums) {
			if (nums == null || nums.length == 0) {
				return 0;
			}
			TreeSet<Integer> set = new TreeSet<>(Comparator.comparingInt(o -> o));
			for (int i = 0; i < nums.length; i++) {
				set.add(nums[i]);
			}
			int max = 0, count = 1, pre = set.pollFirst().intValue();
			while (!set.isEmpty()) {
				int val = set.pollFirst().intValue();
				if (val - pre == 1) {
					count++;
				} else {
					max = Math.max(max, count);
					count = 1;
				}
				pre = val;
			}
			return Math.max(max, count);
		}

		/**
		 * 看了官方解法,发现不用TreeSet排序也是可以的
		 * 直接用HashSet 存在一些技巧,本来问题在于怎么去计算连续的数有多少个?
		 * HashSet没有排序,怎么去计数连续的数字呢?关键在于num 满足num-1不存在,则从num开始往后进行计数
		 *
		 * @param nums
		 * @return
		 */
		public static int longestConsecutiveI(int[] nums) {
			Set<Integer> set = new HashSet<>();
			for (int num : nums) {
				set.add(num);
			}
			int max = 0;
			for (int num : set) {
				if (!set.contains(num - 1)) { // 如果num不存在比其小1的数,则从num开始计数
					int cur = num;
					int count = 1;
					while (set.contains(cur + 1)) {
						cur++;
						count++;
					}
					max = Math.max(max, count);
				}
			}
			return max;
		}

		public static void main(String[] args) {
			System.out.println(longestConsecutiveI(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
		}
	}
}
