package test;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xpath.internal.compiler.PsuedoNames;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

public class DailyQuestionCase {

	/**
	 * 860. 柠檬水找零
	 * 在柠檬水摊上，每一杯柠檬水的售价为5美元。顾客排队购买你的产品，（按账单 bills 支付的顺序）一次购买一杯。
	 * <p>
	 * 每位顾客只买一杯柠檬水，然后向你付 5 美元、10 美元或 20 美元。你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 5 美元。
	 * <p>
	 * 注意，一开始你手头没有任何零钱。
	 * <p>
	 * 给你一个整数数组 bills ，其中 bills[i] 是第 i 位顾客付的账。如果你能给每位顾客正确找零，返回true，否则返回 false。
	 * <p>
	 * <p>
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：bills = [5,5,5,10,20]
	 * 输出：true
	 * 解释：
	 * 前 3 位顾客那里，我们按顺序收取 3 张 5 美元的钞票。
	 * 第 4 位顾客那里，我们收取一张 10 美元的钞票，并返还 5 美元。
	 * 第 5 位顾客那里，我们找还一张 10 美元的钞票和一张 5 美元的钞票。
	 * 由于所有客户都得到了正确的找零，所以我们输出 true。
	 * 示例 2：
	 * <p>
	 * 输入：bills = [5,5,10,10,20]
	 * 输出：false
	 * 解释：
	 * 前 2 位顾客那里，我们按顺序收取 2 张 5 美元的钞票。
	 * 对于接下来的 2 位顾客，我们收取一张 10 美元的钞票，然后返还 5 美元。
	 * 对于最后一位顾客，我们无法退回 15 美元，因为我们现在只有两张 10 美元的钞票。
	 * 由于不是每位顾客都得到了正确的找零，所以答案是 false。
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= bills.length <= 105
	 * bills[i]不是5就是10或是20
	 * <p>
	 */
	static class LemonadeChange {

		/**
		 * 个人思路:
		 * 常规来说,找零思路就是,遇见5即手中5的个数+1;遇见10在手中10的个数+1,同时5的个数-1;遇见20则手中10,5各-1,20+1
		 * 实际上20的个数不用计数,因为不能作为零钱来找
		 *
		 * @param bills
		 * @return
		 */
		public static boolean lemonadeChange(int[] bills) {

			int five = 0, ten = 0;
			for (int i = 0; i < bills.length; i++) {
				if (bills[i] == 5) {
					five++;
				} else if (bills[i] == 10) {
					five--;
					ten++;
				} else {  // 20块有两种找法 1.5+10 2.5+5+5
					if (ten == 0) {
						five -= 3;
					} else {
						five--;
						ten--;
					}
				}
				if (five < 0 || ten < 0)
					return false;
			}
			return true;
		}

		public static void main(String[] args) {

			int[] bills = {5, 5, 10, 20, 5, 5, 5, 5, 5, 5, 5, 5, 5, 10, 5, 5, 20, 5, 20, 5};
			boolean b = lemonadeChange(bills);
			System.out.println(b);
		}
	}

	/**
	 * 207. 课程表
	 * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
	 * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，
	 * 表示如果要学习课程 ai 则 必须 先学习课程  bi 。
	 * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
	 * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
	 * 示例 1：
	 * 输入：numCourses = 2, prerequisites = [[1,0]]
	 * 输出：true
	 * 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
	 * 示例 2：
	 * 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
	 * 输出：false
	 * 解释：总共有 2 门课程。学习课程 1 之前，你需要先完成课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
	 * 提示：
	 * 1 <= numCourses <= 2000
	 * 0 <= prerequisites.length <= 5000
	 * prerequisites[i].length == 2
	 * 0 <= ai, bi < numCourses
	 * prerequisites[i] 中的所有课程对 互不相同
	 */
	static class CanFinish {

		/**
		 * 个人思路,环形链表
		 * 将课程按顺序串联成一个链表,如果链表成环,则返回false
		 */
		public boolean canFinish(int numCourses, int[][] prerequisites) {
			return false;
		}
	}

	/**
	 * 3138. 同位字符串连接的最小长度
	 * 给你一个字符串 s ，它由某个字符串 t 和若干 t  的 同位字符串 连接而成。
	 * 请你返回字符串 t 的 最小 可能长度。
	 * 同位字符串 指的是重新排列一个单词得到的另外一个字符串，原来字符串中的每个字符在新字符串中都恰好只使用一次。
	 * 示例 1：
	 * 输入：s = "abba"
	 * 输出：2
	 * 解释：
	 * 一个可能的字符串 t 为 "ba" 。
	 * 示例 2：
	 * 输入：s = "cdef"
	 * 输出：4
	 * 解释：
	 * 一个可能的字符串 t 为 "cdef" ，注意 t 可能等于 s 。
	 * 提示：
	 * 1 <= s.length <= 105
	 * s 只包含小写英文字母。
	 */
	static class MinAnagramLength {

		/**
		 * 个人思路:
		 * 首先这个字符串s如果可以有t和其同位字符串拼接而成,那么t字符串的长度一定是s字符串长度的因数;
		 * 可以枚举因数,然后将s字符串按因数长度切割,匹配每一份字符串中字符的个数是否相同
		 * 当然,因数在选择时是可以进行优化的,比如s只要存在重复的字符那么因素1就可以被排除
		 * 由于我们要求最小可能长度的字符串t,那么我们可以排除不可能的最小因素,然后依次往后进行校验
		 * 统计s字符中出现某个字符次数的最小值.
		 * eg:abcabaacaacb
		 * 对这个字符串进行统计:a=6 b=3 c=3 可以得出出现字符的次数最小为3,那么最小的因素长度为 12/3=4
		 * 最多必须分为3组,此时t的长度最小
		 *
		 * @param s
		 * @return
		 */
		public static int minAnagramLength(String s) {
			int l = s.length();
			int[] minArr = new int[26];
			for (int i = 0; i < s.length(); i++) {
				minArr[s.charAt(i) - 'a']++;
			}
			int min = l;
			for (int i = 0; i < minArr.length; i++) {
				if (minArr[i] != 0) {
					min = Math.min(min, minArr[i]);
				}
			}
			// 找到s字符串长度的因数
			for (int i = l / min; i < l; i++) {
				if (l % i == 0) {  // 因数
					if (check(s, i)) {
						return i;
					}
				}
			}
			return l;
		}

		/**
		 * 检查字符串s是否由长度为i的同位字符串连接而成
		 *
		 * @param s
		 * @param i
		 * @return
		 */
		private static boolean check(String s, int i) {
			int[] count0 = new int[26];
			for (int j = 0; j < i; j++) {
				count0[s.charAt(j) - 'a']++;
			}
			for (int n = 1; n < s.length() / i; n++) {
				int[] count1 = new int[26];
				for (int k = i * n; k < i * (n + 1); k++) {
					count1[s.charAt(k) - 'a']++;
				}
				if (!Arrays.equals(count0, count1)) {
					return false;
				}
			}
			return true;
		}

		public static void main(String[] args) {
			int i1 = minAnagramLength("aaa");
			System.out.println(i1);
		}
	}

	/**
	 * 7. 整数反转
	 * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
	 * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
	 * 假设环境不允许存储 64 位整数（有符号或无符号）。
	 * 示例 1：
	 * 输入：x = 123
	 * 输出：321
	 * 示例 2：
	 * 输入：x = -123
	 * 输出：-321
	 * 示例 3：
	 * 输入：x = 120
	 * 输出：21
	 * 示例 4：
	 * 输入：x = 0
	 * 输出：0
	 * 提示：
	 * -231 <= x <= 231 - 1
	 */
	static class Reverse {

		/**
		 * 个人思路:
		 * 对于数字反转的处理,可以转字符串之后进行反转,当然也可以去数字的每一位之后进行求和
		 * 从个位开始取,一直到最高位
		 * eg:785624
		 * 785624 % 10 = 4 --> 785624/10  = 78562
		 * 78562 % 10  = 2 --> 78562/10   = 7856
		 * 7856 % 10   = 6 --> 7856/10    = 785
		 * ...
		 * 取到每一位的数字存储起来,最后进行求和
		 *
		 * @param x
		 * @return
		 */
		public static int reverse(int x) {
			List<Integer> list = new ArrayList<>(10);
			while (x != 0) {
				list.add(x % 10);
				x = x / 10;
			}
			int n = list.size();
			long v = 0;
			for (int i = 0; i < n; i++) {
				v += (long) (list.get(i) * Math.pow(10, (n - 1 - i)));
				if (v > Integer.MAX_VALUE || v < Integer.MIN_VALUE) {
					return 0;
				}
			}
			return (int) v;
		}

		/**
		 * MAX:2147483647
		 * MIN:-2147483648
		 *
		 * @param x
		 * @return
		 */
		public static int reverseII(int x) {
			int res = 0;
			while (x != 0) {
				int temp = x % 10;
				// 实际上是需要特殊判断最后一位数字,因为不到10位时,肯定不会溢出
				if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && temp > 7)) {
					return 0;
				}
				if (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && temp < -8)) {
					return 0;
				}
				res = res * 10 + temp;
				x /= 10;
			}
			return res;
		}

		public static void main(String[] args) {
			int reverse = reverseII(-1563847412);
			System.out.println(reverse);
		}
	}

	/**
	 * 3083. 字符串及其反转中是否存在同一子字符串
	 * 给你一个字符串 s ，请你判断字符串 s 是否存在一个长度为 2 的子字符串，在其反转后的字符串中也出现。
	 * 如果存在这样的子字符串，返回 true；如果不存在，返回 false 。
	 * 示例 1：
	 * 输入：s = "leetcode"
	 * 输出：true
	 * 解释：子字符串 "ee" 的长度为 2，它也出现在 reverse(s) == "edocteel" 中。
	 * 示例 2：
	 * 输入：s = "abcba"
	 * 输出：true
	 * 解释：所有长度为 2 的子字符串 "ab"、"bc"、"cb"、"ba" 也都出现在 reverse(s) == "abcba" 中。
	 * 示例 3：
	 * 输入：s = "abcd"
	 * 输出：false
	 * 解释：字符串 s 中不存在满足「在其反转后的字符串中也出现」且长度为 2 的子字符串。
	 * 提示：
	 * 1 <= s.length <= 100
	 * 字符串 s 仅由小写英文字母组成。
	 */
	static class IsSubstringPresent {

		/**
		 * 思路:
		 * 遍历s字符串中,所有存在的连续两个字母组成的字符串,然后在反转后的字符串中查找是否存在
		 *
		 * @param s
		 * @return
		 */
		public static boolean isSubstringPresent(String s) {
			StringBuilder sb = new StringBuilder();
			for (int i = s.length() - 1; i >= 0; i--) {
				sb.append(s.charAt(i));
			}
			String reverse = sb.toString();
			for (int i = 0; i < s.length() - 1; i++) {
				StringBuilder sb1 = new StringBuilder();
				char c0 = s.charAt(i), c1 = s.charAt(i + 1);
				if (c0 == c1) return true;
				sb1.append(c0).append(c1);
				if (reverse.contains(sb1.toString())) return true;
			}
			return false;
		}

		/**
		 * 优秀思路:
		 * 设x=s[i-1],y=s[i] 那么x+y表示原子串 ,我们要找到原串s中是否存在子串 y+x,如果存在返回true
		 * 那么为什么一次遍历即可呢?
		 * 如果存在x+y 和y+x 这样的子串,当遍历到y+x的子串时,一定已经遍历过x+y子串,所以可以在一次遍历中进行判断
		 *
		 * @param s
		 * @return
		 */
		public static boolean isSubstringPresentII(String s) {
			char[] arr = s.toCharArray();
			boolean[][] visit = new boolean[26][26];
			for (int i = 1; i < arr.length; i++) {
				int x = arr[i] - 'a';
				int y = arr[i - 1] - 'a';
				visit[x][y] = true;
				if (visit[y][x]) return true;
			}
			return false;
		}

		public static void main(String[] args) {
			System.out.println(isSubstringPresentII("leafbcaef"));
		}
	}

	/**
	 * 3159. 查询数组中元素的出现位置
	 * 给你一个整数数组 nums ，一个整数数组 queries 和一个整数 x 。
	 * 对于每个查询 queries[i] ，你需要找到 nums 中第 queries[i] 个 x 的位置，并返回它的下标。如果数组中 x 的出现次数少于 queries[i] ，该查询的答案为 -1 。
	 * 请你返回一个整数数组 answer ，包含所有查询的答案。
	 * 示例 1：
	 * 输入：nums = [1,3,1,7], queries = [1,3,2,4], x = 1
	 * 输出：[0,-1,2,-1]
	 * 解释：
	 * 第 1 个查询，第一个 1 出现在下标 0 处。
	 * 第 2 个查询，nums 中只有两个 1 ，所以答案为 -1 。
	 * 第 3 个查询，第二个 1 出现在下标 2 处。
	 * 第 4 个查询，nums 中只有两个 1 ，所以答案为 -1 。
	 * 示例 2：
	 * 输入：nums = [1,2,3], queries = [10], x = 5
	 * 输出：[-1]
	 * 解释：
	 * 第 1 个查询，nums 中没有 5 ，所以答案为 -1 。
	 * 提示：
	 * 1 <= nums.length, queries.length <= 105
	 * 1 <= queries[i] <= 105
	 * 1 <= nums[i], x <= 104
	 */
	static class OccurrencesOfElement {

		/**
		 * 思路:
		 * 对nums数组做预处理,计算出nums中出现x元素的索引,并记录在数组中
		 *
		 * @param nums
		 * @param queries
		 * @param x
		 * @return
		 */
		public static int[] occurrencesOfElement(int[] nums, int[] queries, int x) {
			int n = nums.length;
			int[] idx = new int[n];
			Arrays.fill(idx, -1);
			int k = 0;
			for (int i = 0; i < n; i++) {
				if (nums[i] == x) {
					idx[k] = i;
					k++;
				}
			}
			int[] res = new int[queries.length];
			for (int i = 0; i < queries.length; i++) {
				int q = queries[i];
				if (q > n) {
					res[i] = -1;
				} else {
					res[i] = idx[q - 1];
				}
			}
			return res;
		}

		public static void main(String[] args) {
			System.out.println(Arrays.toString(occurrencesOfElement(new int[]{1, 3, 1, 7, 1, 2}, new int[]{1, 3, 2, 4, 5, 7}, 1)));
		}
	}

	/**
	 * 3218. 切蛋糕的最小总开销 I
	 * 有一个 m x n 大小的矩形蛋糕，需要切成 1 x 1 的小块。
	 * 给你整数 m ，n 和两个数组：
	 * horizontalCut 的大小为 m - 1 ，其中 horizontalCut[i] 表示沿着水平线 i 切蛋糕的开销。
	 * verticalCut 的大小为 n - 1 ，其中 verticalCut[j] 表示沿着垂直线 j 切蛋糕的开销。
	 * 一次操作中，你可以选择任意不是 1 x 1 大小的矩形蛋糕并执行以下操作之一：
	 * 沿着水平线 i 切开蛋糕，开销为 horizontalCut[i] 。
	 * 沿着垂直线 j 切开蛋糕，开销为 verticalCut[j] 。
	 * 每次操作后，这块蛋糕都被切成两个独立的小蛋糕。
	 * 每次操作的开销都为最开始对应切割线的开销，并且不会改变。
	 * 请你返回将蛋糕全部切成 1 x 1 的蛋糕块的 最小 总开销。
	 * 示例 1：
	 * 输入：m = 3, n = 2, horizontalCut = [1,3], verticalCut = [5]
	 * 输出：13
	 * 解释：
	 * 沿着垂直线 0 切开蛋糕，开销为 5 。
	 * 沿着水平线 0 切开 3 x 1 的蛋糕块，开销为 1 。
	 * 沿着水平线 0 切开 3 x 1 的蛋糕块，开销为 1 。
	 * 沿着水平线 1 切开 2 x 1 的蛋糕块，开销为 3 。
	 * 沿着水平线 1 切开 2 x 1 的蛋糕块，开销为 3 。
	 * 总开销为 5 + 1 + 1 + 3 + 3 = 13 。
	 * 示例 2：
	 * 输入：m = 2, n = 2, horizontalCut = [7], verticalCut = [4]
	 * 输出：15
	 * 解释：
	 * 沿着水平线 0 切开蛋糕，开销为 7 。
	 * 沿着垂直线 0 切开 1 x 2 的蛋糕块，开销为 4 。
	 * 沿着垂直线 0 切开 1 x 2 的蛋糕块，开销为 4 。
	 * 总开销为 7 + 4 + 4 = 15 。
	 * 提示：
	 * 1 <= m, n <= 20
	 * horizontalCut.length == m - 1
	 * verticalCut.length == n - 1
	 * 1 <= horizontalCut[i], verticalCut[i] <= 103
	 */
	static class MinimumCost {

		/**
		 * 思路:
		 * 要找到最小的开销,那么优先切开销最大的方向,当只剩下一个方向的刀没切了,那么不需要排序了.
		 * 用 x,y代表水平方向和垂直方向的块数,当水平方向切一刀,垂直方向的块数+1,反之亦然
		 * 直到水平方向和垂直方向的刀数切完,即两个数组的消耗量都计算完成
		 * 贪心思路
		 * 自己想出来的思路,成就感很高****************************************
		 *
		 * @param m
		 * @param n
		 * @param horizontalCut
		 * @param verticalCut
		 * @return
		 */
		public static int minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
			Arrays.sort(horizontalCut);
			Arrays.sort(verticalCut);
			int p = m - 2, q = n - 2, x = 1, y = 1, sum = 0;
			boolean flag = false;
			while (p >= 0 || q >= 0) {
				int v;
				if (p < 0) {
					v = verticalCut[q--];
					flag = false;
				} else if (q < 0) {
					v = horizontalCut[p--];
					flag = true;
				} else {
					if (verticalCut[q] >= horizontalCut[p]) {
						v = verticalCut[q--];
						flag = false;
					} else {
						v = horizontalCut[p--];
						flag = true;
					}
				}
				if (flag) {
					sum += v * y;
					x++;
				} else {
					sum += v * x;
					y++;
				}
			}
			return sum;
		}

		public static void main(String[] args) {
			System.out.println(minimumCost(4, 4, new int[]{3, 2, 5}, new int[]{7, 3, 1}));
		}
	}

	/**
	 * 1705. 吃苹果的最大数目
	 * 有一棵特殊的苹果树，一连 n 天，每天都可以长出若干个苹果。在第 i 天，树上会长出 apples[i] 个苹果，这些苹果将会在 days[i] 天后（也就是说，第 i + days[i] 天时）腐烂，变得无法食用。也可能有那么几天，树上不会长出新的苹果，此时用 apples[i] == 0 且 days[i] == 0 表示。
	 * 你打算每天 最多 吃一个苹果来保证营养均衡。注意，你可以在这 n 天之后继续吃苹果。
	 * 给你两个长度为 n 的整数数组 days 和 apples ，返回你可以吃掉的苹果的最大数目。
	 * 示例 1：
	 * 输入：apples = [1,2,3,5,2], days = [3,2,1,4,2]
	 * 输出：7
	 * 解释：你可以吃掉 7 个苹果：
	 * - 第一天，你吃掉第一天长出来的苹果。
	 * - 第二天，你吃掉一个第二天长出来的苹果。
	 * - 第三天，你吃掉一个第二天长出来的苹果。过了这一天，第三天长出来的苹果就已经腐烂了。
	 * - 第四天到第七天，你吃的都是第四天长出来的苹果。
	 * 示例 2：
	 * 输入：apples = [3,0,0,0,0,2], days = [3,0,0,0,0,2]
	 * 输出：5
	 * 解释：你可以吃掉 5 个苹果：
	 * - 第一天到第三天，你吃的都是第一天长出来的苹果。
	 * - 第四天和第五天不吃苹果。
	 * - 第六天和第七天，你吃的都是第六天长出来的苹果。
	 * 提示：
	 * apples.length == n
	 * days.length == n
	 * 1 <= n <= 2 * 104
	 * 0 <= apples[i], days[i] <= 2 * 104
	 * 只有在 apples[i] = 0 时，days[i] = 0 才成立
	 */
	static class EatenApples {

		/**
		 * 思路:
		 * [1,2,3,5,2] [3,2,1,4,2]
		 * 那么每天长出来的苹果能吃到哪一天的数组为[3,3,3,7,6]
		 * [1,2,3,5,2]
		 * 设置指针p初始指向第一天 1,第一天长苹果数量为1 且可以吃到第3天,即3>1 则+1,此时p移动一位;
		 * p的位置指向第二天,第二天长苹果数量为2 且可以吃到第3天, 则+2 ,此时p移动两位;
		 * p的位置指向第四天,第三天长的苹果数量为3,但3<4 所以不加
		 * p的位置指向第四天,第四天长的苹果数量为5,则可以吃到第7天,此时+(7-4+1)
		 * ------------------------------------------------------------------------------------
		 * 这个思路是错的,问题在于,吃哪一天长的苹果,并不一定是要按顺序的.
		 * 根据贪心的思路,应该在尚未腐烂的苹果中优先选择腐烂日期最早的苹果
		 *
		 * @param apples
		 * @param days
		 * @return
		 */
		public static int eatenApples(int[] apples, int[] days) {
			int n = apples.length;
			int[] expire = new int[n];
			for (int i = 0; i < n; i++) {
				expire[i] = i + days[i];
			}
			int sum = 0;
			for (int i = 0; i < n; i++) {
				int apple = apples[i];  // 第i天长苹果的数量
				int exp = expire[i];    // 第i天长苹果可以吃到哪一天
				if (sum < exp) {       // 当前天为sum,第i天长的苹果,可以往后吃的天数
					sum += Math.min(exp - sum, apple); // 可以往后吃的天数和长的苹果数量取其小
				}
			}
			return sum;
		}

		/**
		 * 超出时间限制
		 *
		 * @param apples
		 * @param days
		 * @return
		 */
		public static int eatenApplesII(int[] apples, int[] days) {
			int n = apples.length;
			PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
			int sum = 0, i = 0;
			while (i < n) {
				while (i < n && sum >= i) {
					pq.offer(new int[]{i, i + days[i]});
					i++;
				}
				if (!pq.isEmpty()) {
					while (!pq.isEmpty() && pq.peek()[0] <= i) {
						pq.poll();
					}
					if (pq.isEmpty()) {
						break;
					}
					int[] poll = pq.poll();
					int day = poll[0];
					int apple = apples[day]; // 第i天长苹果的数量
					int exp = poll[1];    // 第i天长苹果可以吃到哪一天
					if (sum < exp) {
						sum += Math.min(exp - sum, apple);
					}
				}
			}
			while (!pq.isEmpty()) {
				while (!pq.isEmpty() && pq.peek()[0] <= i) {
					pq.poll();
				}
				if (pq.isEmpty()) {
					break;
				}
				int[] poll = pq.poll();
				int day = poll[0];
				int apple = apples[day]; // 第i天长苹果的数量
				int exp = poll[1];    // 第i天长苹果可以吃到哪一天
				if (sum < exp) {
					sum += Math.min(exp - sum, apple);
				}
			}
			return sum;
		}

		/**
		 * n内的天数和超出n天,分为两个场景来处理
		 *
		 * @param apples
		 * @param days
		 * @return
		 */
		public static int eatenApplesOfficial(int[] apples, int[] days) {
			int ans = 0;
			PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[0] - b[0]);
			int n = apples.length;
			int i = 0;
			while (i < n) {
				while (!pq.isEmpty() && pq.peek()[0] <= i) {
					pq.poll();
				}
				int rottenDay = i + days[i];
				int count = apples[i];
				if (count > 0) {
					pq.offer(new int[]{rottenDay, count});
				}
				if (!pq.isEmpty()) {
					int[] arr = pq.peek();
					arr[1]--;
					if (arr[1] == 0) {
						pq.poll();
					}
					ans++;
				}
				i++;
			}
			while (!pq.isEmpty()) {
				while (!pq.isEmpty() && pq.peek()[0] <= i) {
					pq.poll();
				}
				if (pq.isEmpty()) {
					break;
				}
				int[] arr = pq.poll();
				int curr = Math.min(arr[0] - i, arr[1]);
				ans += curr;
				i += curr;
			}
			return ans;
		}

		public static void main(String[] args) {
			System.out.println(eatenApplesII(new int[]{2, 1, 1, 4, 5}, new int[]{10, 10, 6, 4, 2}));
		}
	}

	/**
	 * singly-linked list.
	 */
	public static class ListNode {
		int val;
		ListNode next;

		ListNode() {
		}

		ListNode(int val) {
			this.val = val;
		}

		ListNode(int val, ListNode next) {
			this.val = val;
			this.next = next;
		}
	}

	/**
	 * binary tree node.
	 */
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode() {
		}

		TreeNode(int val) {
			this.val = val;
		}

		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

	/**
	 * 1367. 二叉树中的链表
	 * 给你一棵以 root 为根的二叉树和一个 head 为第一个节点的链表。
	 * 如果在二叉树中，存在一条一直向下的路径，且每个点的数值恰好一一对应以 head 为首的链表中每个节点的值，那么请你返回 True ，否则返回 False 。
	 * 一直向下的路径的意思是：从树中某个节点开始，一直连续向下的路径。
	 * 示例 1：
	 * 输入：head = [4,2,8], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
	 * 输出：true
	 * 解释：树中蓝色的节点构成了与链表对应的子路径。
	 * 示例 2：
	 * 输入：head = [1,4,2,6], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
	 * 输出：true
	 * 示例 3：
	 * 输入：head = [1,4,2,6,8], root = [1,4,4,null,2,2,null,1,null,6,8,null,null,null,null,1,3]
	 * 输出：false
	 * 解释：二叉树中不存在一一对应链表的路径。
	 * 提示：
	 * 二叉树和链表中的每个节点的值都满足 1 <= node.val <= 100 。
	 * 链表包含的节点数目在 1 到 100 之间。
	 * 二叉树包含的节点数目在 1 到 2500 之间。
	 */
	static class IsSubPath {


		private static boolean flag = false;
		private static int n;
		private static String linked;

		/**
		 * 思路:
		 * 后去二叉树的路径,当路径数量>=链表数量时,进行匹配
		 *
		 * @param head
		 * @param root
		 * @return
		 */
		public static boolean isSubPath(ListNode head, TreeNode root) {
			StringBuilder stringBuilder = new StringBuilder();
			int i = 0;
			while (head != null) {
				stringBuilder.append(head.val).append("-");
				head = head.next;
				i++;
			}
			linked = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
			n = i;
			dfs(root, new ArrayList<>());
			return flag;
		}

		/**
		 * 深度优先遍历二叉树,并记录路径
		 *
		 * @param node
		 * @param path
		 */
		private static void dfs(TreeNode node, List<String> path) {
			if (path.size() >= n && math(path, linked)) {
				flag = true;
				return;
			}
			if (node == null) return;
			path.add(String.valueOf(node.val));
			dfs(node.left, path);
			dfs(node.right, path);
			path.remove(path.size() - 1);
		}

		/**
		 * 查看路径path是否包含linked
		 *
		 * @param path
		 * @param linked
		 * @return
		 */
		private static boolean math(List<String> path, String linked) {
			String join = String.join("-", path);
			return join.contains(linked);
		}

		/**
		 * 官方思路：
		 * 枚举二叉树的每个节点为起点往下的路径是否有与链表相匹配的路径.设计递归函数 dfs(rt,head)
		 * 其中rt表示当前匹配到的二叉树节点,head表示当前匹配到的链表节点.有以下四种情况:
		 * 1.链表已经完全匹配完成,返回true
		 * 2.二叉树访问到了空节点,匹配失败,返回false
		 * 3.当前匹配的二叉树上节点的值与链表节点的值不相等,匹配失败,返回false
		 * 4.前三种情况都没有返回,说明部分匹配成功,需要继续匹配,调用函数dfs(rt->left,head->next)
		 * 如果左子节点没匹配到,继续匹配右子节点:dfs(tr->right,head->next)
		 * 所以匹配方式为: dfs(root,head)||isSubPath(head,root->left)||isSubPath(head,root->right)
		 *
		 * @param head
		 * @param root
		 * @return
		 */
		public static boolean isSubPathOfficial(ListNode head, TreeNode root) {
			if (root == null) return false;
			return dfs(root, head) || isSubPathOfficial(head, root.left) || isSubPathOfficial(head, root.right);
		}

		private static boolean dfs(TreeNode rt, ListNode head) {
			if (head == null) { // 链表已匹配完成
				return true;
			}
			if (rt == null) { // 二叉树访问到了空节点
				return false;
			}
			if (rt.val != head.val) { // 当前匹配的二叉树节点和链表节点值不相等
				return false;
			}
			// 上面三次情况都没有命中,则说明当前匹配成功,继续下一次匹配
			return dfs(rt.left, head.next) || dfs(rt.right, head.next);
		}

		public static void main(String[] args) {
			ListNode head = new ListNode(1);
			ListNode n4 = new ListNode(4);
			ListNode n2 = new ListNode(2);
			ListNode n6 = new ListNode(6);
			ListNode n8 = new ListNode(8);
			head.next = n4;
			n4.next = n2;
			n2.next = n6;
			// n6.next = n8;

			TreeNode root = new TreeNode(1);
			TreeNode r4 = new TreeNode(4);
			TreeNode r2 = new TreeNode(2);
			TreeNode r1 = new TreeNode(1);
			TreeNode r4_1 = new TreeNode(4);
			TreeNode r2_1 = new TreeNode(2);
			TreeNode r6 = new TreeNode(6);
			TreeNode r8 = new TreeNode(8);
			TreeNode r1_1 = new TreeNode(1);
			TreeNode r3 = new TreeNode(3);
			root.left = r4;
			r4.right = r2;
			r2.left = r1;
			root.right = r4_1;
			r4_1.left = r2_1;
			r2_1.left = r6;
			r2_1.right = r8;
			r8.left = r1_1;
			r8.right = r3;
			boolean subPath = isSubPathOfficial(head, root);
			System.out.println(subPath);
		}
	}

	/**
	 * 1366. 通过投票对团队排名
	 * 现在有一个特殊的排名系统，依据参赛团队在投票人心中的次序进行排名，每个投票者都需要按从高到低的顺序对参与排名的所有团队进行排位。
	 * 排名规则如下：
	 * 参赛团队的排名次序依照其所获「排位第一」的票的多少决定。如果存在多个团队并列的情况，将继续考虑其「排位第二」的票的数量。以此类推，直到不再存在并列的情况。
	 * 如果在考虑完所有投票情况后仍然出现并列现象，则根据团队字母的字母顺序进行排名。
	 * 给你一个字符串数组 votes 代表全体投票者给出的排位情况，请你根据上述排名规则对所有参赛团队进行排名。
	 * 请你返回能表示按排名系统 排序后 的所有团队排名的字符串。
	 * 示例 1：
	 * 输入：votes = ["ABC","ACB","ABC","ACB","ACB"]
	 * 输出："ACB"
	 * 解释：
	 * A 队获得五票「排位第一」，没有其他队获得「排位第一」，所以 A 队排名第一。
	 * B 队获得两票「排位第二」，三票「排位第三」。
	 * C 队获得三票「排位第二」，两票「排位第三」。
	 * 由于 C 队「排位第二」的票数较多，所以 C 队排第二，B 队排第三。
	 * 示例 2：
	 * 输入：votes = ["WXYZ","XYZW"]
	 * 输出："XWYZ"
	 * 解释：
	 * X 队在并列僵局打破后成为排名第一的团队。X 队和 W 队的「排位第一」票数一样，但是 X 队有一票「排位第二」，而 W 没有获得「排位第二」。
	 * 示例 3：
	 * 输入：votes = ["ZMNAGUEDSJYLBOPHRQICWFXTVK"]
	 * 输出："ZMNAGUEDSJYLBOPHRQICWFXTVK"
	 * 解释：只有一个投票者，所以排名完全按照他的意愿。
	 * 提示：
	 * 1 <= votes.length <= 1000
	 * 1 <= votes[i].length <= 26
	 * votes[i].length == votes[j].length for 0 <= i, j < votes.length
	 * votes[i][j] 是英文 大写 字母
	 * votes[i] 中的所有字母都是唯一的
	 * votes[0] 中出现的所有字母 同样也 出现在 votes[j] 中，其中 1 <= j < votes.length
	 */
	static class RankTeams {

		/**
		 * 思路:
		 * 假设有n个字母参与投票,为每个字母的名次进行统计,总共有n个名次,用HashMap进行存储,当key不存在时为0票
		 * 从第一名开始一次计算该排名获得票数最多的字母,如果存在多个字母票数一样,则比较其第二名、第三名..获得的票数.
		 * 假如第一名的字母有A和B,那么如何判断A和B哪一个排在第一名呢?
		 * eg 字母A的第1...i名分别为 32,12,34
		 * *  字母B的第i...i名分别为 32,12,34
		 * 官方思路是自定义sort方法的比较器,在比较器中一直对比该字母的每个名次,直到比出大小,否则比较字母的ASCII码
		 *
		 * @param votes
		 * @return
		 */
		public static String rankTeams(String[] votes) {
			// key为字母,value为所有排名的计数,从第一名开始
			Map<Character, int[]> map = new HashMap<>();
			char[] arr = votes[0].toCharArray();
			// 先构建出map中的所有(k,v)
			for (int i = 0; i < arr.length; i++) {
				map.put(arr[i], new int[votes[0].length()]);
			}
			for (int i = 0; i < votes.length; i++) {
				for (int j = 0; j < votes[i].length(); j++) {
					int[] ints = map.get(votes[i].charAt(j));
					ints[j]++;
				}
			}
			// 取出键值对,转换为List
			List<Map.Entry<Character, int[]>> result = new ArrayList<>(map.entrySet());
			// 排序
			result.sort((a, b) -> {
				for (int i = 0; i < a.getValue().length; i++) {
					if (a.getValue()[i] != b.getValue()[i]) {      // a字母和b字母在第i名上计数不一样,则返回多的更大
						return b.getValue()[i] - a.getValue()[i];  // 计数更大的排在前面
					}
				}
				return a.getKey() - b.getKey();                     // 字母ASCII码更小的排在前面
			});
			StringBuilder ans = new StringBuilder();
			for (Map.Entry<Character, int[]> entry : result) {
				ans.append(entry.getKey());
			}
			return ans.toString();
		}

		/**
		 * 改成二维数组来计数
		 *
		 * @param votes
		 * @return
		 */
		public static String rankTeamsII(String[] votes) {
			int n = votes[0].length();
			int[][] res = new int[26][n + 1];
			for (String vote : votes) {
				for (int j = 0; j < n; j++) {
					res[vote.charAt(j) - 'A'][j]++;
				}
			}
			// res二维数组行表示字母,列表示每个字母的排名计数,排名从0到n-1
			// 此处用n排名来存储是哪个字母
			for (int i = 0; i < 26; i++) {
				res[i][n] = i;
			}
			Arrays.sort(res, (a, b) -> {
				for (int i = 0; i < n; i++) {
					if (a[i] == b[i]) continue;
					return b[i] - a[i];
				}
				return 1;
			});
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < n; i++) {
				sb.append((char) (res[i][n] + 'A'));
			}
			return sb.toString();
		}

		public static void main(String[] args) {
			System.out.println(rankTeamsII(new String[]{
					"ABC",
					"ACB",
					"ABC",
					"ACB",
					"ACB",
			}));
		}
	}

	/**
	 * 3046. 分割数组
	 * 给你一个长度为 偶数 的整数数组 nums 。你需要将这个数组分割成 nums1 和 nums2 两部分，要求：
	 * nums1.length == nums2.length == nums.length / 2 。
	 * nums1 应包含 互不相同 的元素。
	 * nums2也应包含 互不相同 的元素。
	 * 如果能够分割数组就返回 true ，否则返回 false 。
	 * 示例 1：
	 * 输入：nums = [1,1,2,2,3,4]
	 * 输出：true
	 * 解释：分割 nums 的可行方案之一是 nums1 = [1,2,3] 和 nums2 = [1,2,4] 。
	 * 示例 2：
	 * 输入：nums = [1,1,1,1]
	 * 输出：false
	 * 解释：分割 nums 的唯一可行方案是 nums1 = [1,1] 和 nums2 = [1,1] 。但 nums1 和 nums2 都不是由互不相同的元素构成。因此，返回 false 。
	 * 提示：
	 * 1 <= nums.length <= 100
	 * nums.length % 2 == 0
	 * 1 <= nums[i] <= 100
	 */
	static class IsPossibleToSplit {

		/**
		 * 思路:
		 * 将一个长度为偶数n的数组,分割成两个长度为n/2的数组,且要求这两个数组中的元素唯一
		 * 1.对原数组进行排序,如果某个元素的重复次数超过2,则返回false;否则返回true
		 * 2.将原数组元素进行计数,数量超过2则返回false;否则返回true
		 *
		 * @param nums
		 * @return
		 */
		public static boolean isPossibleToSplit(int[] nums) {
			Arrays.sort(nums);
			int count = 1, pre = -1;
			for (int i = 0; i < nums.length; i++) {
				if (nums[i] != pre) {
					count = 1;
				} else {
					count++;
				}
				pre = nums[i];
				if (count > 2) return false;
			}
			return true;
		}

		public static boolean isPossibleToSplitII(int[] nums) {
			int n = nums.length;
			int[] counts = new int[100];
			for (int i = 0; i < nums.length; i++) {
				if (++counts[nums[i]] > 2) return false;

			}
			return true;
		}

		public static void main(String[] args) {
			System.out.println(isPossibleToSplitII(new int[]{1, 1, 2, 2, 2, 3, 4, 4}));
		}
	}

	/**
	 * 1387. 将整数按权重排序
	 * 我们将整数 x 的 权重 定义为按照下述规则将 x 变成 1 所需要的步数：
	 * 如果 x 是偶数，那么 x = x / 2
	 * 如果 x 是奇数，那么 x = 3 * x + 1
	 * 比方说，x=3 的权重为 7 。因为 3 需要 7 步变成 1 （3 --> 10 --> 5 --> 16 --> 8 --> 4 --> 2 --> 1）。
	 * 给你三个整数 lo， hi 和 k 。你的任务是将区间 [lo, hi] 之间的整数按照它们的权重 升序排序 ，如果大于等于 2 个整数有 相同 的权重，那么按照数字自身的数值 升序排序 。
	 * 请你返回区间 [lo, hi] 之间的整数按权重排序后的第 k 个数。
	 * 注意，题目保证对于任意整数 x （lo <= x <= hi） ，它变成 1 所需要的步数是一个 32 位有符号整数。
	 * 示例 1：
	 * 输入：lo = 12, hi = 15, k = 2
	 * 输出：13
	 * 解释：12 的权重为 9（12 --> 6 --> 3 --> 10 --> 5 --> 16 --> 8 --> 4 --> 2 --> 1）
	 * 13 的权重为 9
	 * 14 的权重为 17
	 * 15 的权重为 17
	 * 区间内的数按权重排序以后的结果为 [12,13,14,15] 。对于 k = 2 ，答案是第二个整数也就是 13 。
	 * 注意，12 和 13 有相同的权重，所以我们按照它们本身升序排序。14 和 15 同理。
	 * 示例 2：
	 * 输入：lo = 7, hi = 11, k = 4
	 * 输出：7
	 * 解释：区间内整数 [7, 8, 9, 10, 11] 对应的权重为 [16, 3, 19, 6, 14] 。
	 * 按权重排序后得到的结果为 [8, 10, 11, 7, 9] 。
	 * 排序后数组中第 4 个数字为 7 。
	 * 提示：
	 * 1 <= lo <= hi <= 1000
	 * 1 <= k <= hi - lo + 1
	 */
	static class GetKth {

		/**
		 * 思路:
		 * 这题用自定义排序,会方便进行排序;但要预处理出他们的步数
		 * 如何求出权重呢?
		 * 1.奇偶的判断,可以用二进制位的个数上是否为1来确定
		 * 2.偶数/2后,可能变成奇数.这里涉及到递归,并有剪枝操作
		 *
		 * @param lo
		 * @param hi
		 * @param k
		 * @return
		 */
		public static int getKth(int lo, int hi, int k) {
			List<Integer> list = new ArrayList<>();
			for (int i = lo; i <= hi; i++) {
				list.add(i);
			}
			list.sort((o1, o2) -> {
				int v1, v2;
				if ((v1 = getF(o1)) == (v2 = getF(o2))) {
					return o1 - o2;
				} else {
					return v1 - v2;
				}
			});
			return list.get(k - 1);
		}


		static Map<Integer, Integer> f = new HashMap<>();

		/**
		 * 带返回值的递归 写法
		 * 保证递归一定会有出口,按照当前规则是肯定有出口的
		 *
		 * @param x
		 * @return
		 */
		private static int getF(int x) {
			// 1.判断是否等于1 ; 2.判断奇偶
			if (x == 1) return 0;
			if (f.containsKey(x)) {
				return f.get(x);
			}
			if ((x & 1) != 0) {
				f.put(x, getF(x * 3 + 1) + 1);
			} else {
				f.put(x, getF(x / 2) + 1);
			}
			return f.get(x);
		}

		public static void main(String[] args) {
			System.out.println(getKth(12, 15, 2));
		}
	}

	/**
	 * 2545. 根据第 K 场考试的分数排序
	 * 班里有 m 位学生，共计划组织 n 场考试。给你一个下标从 0 开始、大小为 m x n 的整数矩阵 score ，其中每一行对应一位学生，而 score[i][j] 表示第 i 位学生在第 j 场考试取得的分数。矩阵 score 包含的整数 互不相同 。
	 * 另给你一个整数 k 。请你按第 k 场考试分数从高到低完成对这些学生（矩阵中的行）的排序。
	 * 返回排序后的矩阵。
	 * 示例 1：
	 * 输入：score = [[10,6,9,1],[7,5,11,2],[4,8,3,15]], k = 2
	 * 输出：[[7,5,11,2],[10,6,9,1],[4,8,3,15]]
	 * 解释：在上图中，S 表示学生，E 表示考试。
	 * - 下标为 1 的学生在第 2 场考试取得的分数为 11 ，这是考试的最高分，所以 TA 需要排在第一。
	 * - 下标为 0 的学生在第 2 场考试取得的分数为 9 ，这是考试的第二高分，所以 TA 需要排在第二。
	 * - 下标为 2 的学生在第 2 场考试取得的分数为 3 ，这是考试的最低分，所以 TA 需要排在第三。
	 * 示例 2：
	 * 输入：score = [[3,4],[5,6]], k = 0
	 * 输出：[[5,6],[3,4]]
	 * 解释：在上图中，S 表示学生，E 表示考试。
	 * - 下标为 1 的学生在第 0 场考试取得的分数为 5 ，这是考试的最高分，所以 TA 需要排在第一。
	 * 提示：
	 * m == score.length
	 * n == score[i].length
	 * 1 <= m, n <= 250
	 * 1 <= score[i][j] <= 105
	 * score 由 不同 的整数组成
	 * 0 <= k < n
	 */
	static class SortTheStudents {

		/**
		 * 思路：通过HashMap记录当前可以对应的这一行数据
		 * 然后排序HashMap的键,最后重新构造二维数组的行
		 *
		 * @param score
		 * @param k
		 * @return
		 */
		public static int[][] sortTheStudents(int[][] score, int k) {
			Map<Integer, int[]> map = new HashMap<>();
			for (int i = 0; i < score.length; i++) {
				map.put(score[i][k], score[i]);
			}
			List<Integer> list = map.keySet().stream().sorted(((o1, o2) -> o2 - o1)).collect(Collectors.toList());
			for (int i = 0; i < list.size(); i++) {
				score[i] = map.get(list.get(i));
			}
			return score;
		}

		/**
		 * 使用自定义排序比较器
		 *
		 * @param score
		 * @param k
		 * @return
		 */
		public static int[][] sortTheStudentsII(int[][] score, int k) {
			return Arrays.stream(score)
					.sorted(Comparator.<int[]>comparingInt(it -> it[k]).reversed())
					.toArray(int[][]::new);
		}

		public static int[][] sortTheStudentsIII(int[][] score, int k) {
			Arrays.sort(score, (o1, o2) -> o2[k] - o1[k]);
			return score;
		}

		public static void main(String[] args) {
			sortTheStudentsII(new int[][]{
					{10, 6, 9, 1},
					{7, 5, 11, 2},
					{4, 8, 3, 15}
			}, 2);
		}
	}

	/**
	 * 3219. 切蛋糕的最小总开销 II
	 */
	static class MinimumCostII {

		/**
		 * 贪心,每次都切开销最大的那一刀;水平切一刀后,垂直切一刀的块数+1;同理垂直切一刀后,水平切一刀的块数+1;
		 *
		 * @param m
		 * @param n
		 * @param horizontalCut
		 * @param verticalCut
		 * @return
		 */
		public long minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
			Arrays.sort(horizontalCut);
			Arrays.sort(verticalCut);
			int p = m - 2, q = n - 2, x = 1, y = 1;
			long sum = 0;
			boolean flag = false;
			while (p >= 0 || q >= 0) {
				int v;
				if (p < 0) {
					v = verticalCut[q--];
					flag = false;
				} else if (q < 0) {
					v = horizontalCut[p--];
					flag = true;
				} else {
					if (verticalCut[q] >= horizontalCut[p]) {
						v = verticalCut[q--];
						flag = false;
					} else {
						v = horizontalCut[p--];
						flag = true;
					}
				}
				if (flag) {
					sum += (long) v * y;
					x++;
				} else {
					sum += (long) v * x;
					y++;
				}
			}
			return sum;
		}

		/**
		 * 代码优化
		 *
		 * @param m
		 * @param n
		 * @param horizontalCut
		 * @param verticalCut
		 * @return
		 */
		public long minimumCostII(int m, int n, int[] horizontalCut, int[] verticalCut) {
			Arrays.sort(horizontalCut);
			Arrays.sort(verticalCut);
			int p = m - 2, q = n - 2, x = 1, y = 1;
			long sum = 0;
			while (p >= 0 || q >= 0) {
				if (q < 0 || (p >= 0 && horizontalCut[p] < verticalCut[q])) {
					sum += (long) verticalCut[p++] * x;
					y++;
				} else {
					sum += (long) horizontalCut[q++] * y;
					x++;
				}
			}
			return sum;
		}
	}

	/**
	 * 3280. 将日期转换为二进制表示
	 * 给你一个字符串 date，它的格式为 yyyy-mm-dd，表示一个公历日期。
	 * date 可以重写为二进制表示，只需要将年、月、日分别转换为对应的二进制表示（不带前导零）并遵循 year-month-day 的格式。
	 * 返回 date 的 二进制 表示。
	 * 示例 1：
	 * 输入： date = "2080-02-29"
	 * 输出： "100000100000-10-11101"
	 * 解释：
	 * 100000100000, 10 和 11101 分别是 2080, 02 和 29 的二进制表示。
	 * 示例 2：
	 * 输入： date = "1900-01-01"
	 * 输出： "11101101100-1-1"
	 * 解释：
	 * 11101101100, 1 和 1 分别是 1900, 1 和 1 的二进制表示。
	 * 提示：
	 * date.length == 10
	 * date[4] == date[7] == '-'，其余的 date[i] 都是数字。
	 * 输入保证 date 代表一个有效的公历日期，日期范围从 1900 年 1 月 1 日到 2100 年 12 月 31 日（包括这两天）。
	 */
	static class ConvertDateToBinary {

		/**
		 * 思路:
		 * 十进制转二进制,一般使用位运算来得到每一位是0还是1
		 * 根据题意年份的范围在[1900-2100],月的范围在[1,12],日的范围在[1,31]
		 * 二进制范围,年:[11101101100,100000110100]
		 * 二进制范围,月:[1,1100]
		 * *         日:[1,11111]
		 * 所以年份位数最多12位,月最多4位,日最多5位
		 *
		 * @param date
		 * @return
		 */
		public static String convertDateToBinary(String date) {
			String[] split = date.split("-");
			String year = toBinaryString(Integer.valueOf(split[0]), 12);
			String month = toBinaryString(Integer.valueOf(split[1]), 4);
			String day = toBinaryString(Integer.valueOf(split[2]), 5);
			return year + "-" + month + "-" + day;
		}

		private static String toBinaryString(int i, int digit) {
			StringBuilder sb = new StringBuilder();
			// 首位的0去掉,flag代表是否首位
			boolean flag = true;
			for (int j = digit - 1; j >= 0; j--) {
				int v = ((i >> j) & 1);
				if (flag) {
					if (v == 1) {
						sb.append(v);
						flag = false;
					}
				} else {
					sb.append(v);
				}
			}
			return sb.toString();
		}

		public static String convertDateToBinaryII(String date) {
			List<String> list = new ArrayList<>();
			String[] split = date.split("-");
			for (String s : split) {
				list.add(Integer.toBinaryString(Integer.parseInt(s)));
			}
			return String.join("-", list);
		}

		public static String convertDateToBinaryIII(String date) {
			StringBuilder sb = new StringBuilder();
			sb.append(Integer.toBinaryString(Integer.parseInt(date.substring(0, 4)))).append("-");
			sb.append(Integer.toBinaryString(Integer.parseInt(date.substring(5, 7)))).append("-");
			sb.append(Integer.toBinaryString(Integer.parseInt(date.substring(8, 10))));
			return sb.toString();
		}

		public static void main(String[] args) {
			/*String binaryString = Integer.toBinaryString(31);
			System.out.println(binaryString);*/
			System.out.println(convertDateToBinaryIII("1900-01-01"));
		}
	}

	/**
	 * 729. 我的日程安排表 I
	 * 实现一个 MyCalendar 类来存放你的日程安排。如果要添加的日程安排不会造成 重复预订 ，则可以存储这个新的日程安排。
	 * 当两个日程安排有一些时间上的交叉时（例如两个日程安排都在同一时间内），就会产生 重复预订 。
	 * 日程可以用一对整数 startTime 和 endTime 表示，这里的时间是半开区间，即 [startTime, endTime), 实数 x 的范围为，  startTime <= x < endTime 。
	 * 实现 MyCalendar 类：
	 * MyCalendar() 初始化日历对象。
	 * boolean book(int startTime, int endTime) 如果可以将日程安排成功添加到日历中而不会导致重复预订，返回 true 。否则，返回 false 并且不要将该日程安排添加到日历中。
	 * 示例：
	 * 输入：
	 * ["MyCalendar", "book", "book", "book"]
	 * [[], [10, 20], [15, 25], [20, 30]]
	 * 输出：
	 * [null, true, false, true]
	 * 解释：
	 * MyCalendar myCalendar = new MyCalendar();
	 * myCalendar.book(10, 20); // return True
	 * myCalendar.book(15, 25); // return False ，这个日程安排不能添加到日历中，因为时间 15 已经被另一个日程安排预订了。
	 * myCalendar.book(20, 30); // return True ，这个日程安排可以添加到日历中，因为第一个日程安排预订的每个时间都小于 20 ，且不包含时间 20 。
	 * 提示：
	 * 0 <= start < end <= 109
	 * 每个测试用例，调用 book 方法的次数最多不超过 1000 次。
	 */
	static class MyCalendar {

		/**
		 * 思路:
		 * 用List来排序范围数组,以范围的左边数据来进行升序
		 * 每新加入一个范围时[x,y),需要找到第一个[n,m) 对应的x>=m,此时后面一个范围为[p,q);此时有以下情况:
		 * 1.x>=m 如果 y<=p 则插入;如果m>p 则返回false
		 * 2.n< y 返回false
		 */
		public MyCalendar() {
			list = new ArrayList<>();
			list.add(new int[]{-1, 0});
		}

		private List<int[]> list;

		public boolean book(int startTime, int endTime) {
			int i = 0;
			for (; i < list.size(); i++) {
				int[] arr = list.get(i);
				if (startTime < arr[1]) {
					break;
				}
			}
			// 找到i为第一次符合条件,新增区间可以插入到i的后面
			if (i == list.size()) {  // i为最后一个区间
				list.add(i, new int[]{startTime, endTime});
				return true;
			} else {  // i为中间区间
				if (endTime <= list.get(i)[0]) {
					list.add(i, new int[]{startTime, endTime});
					return true;
				} else {
					return false;
				}
			}
		}

		public static void main(String[] args) {
			MyCalendar myCalendar = new MyCalendar();
			System.out.println(myCalendar.book(10, 20));
			System.out.println(myCalendar.book(15, 25));
			System.out.println(myCalendar.book(20, 30));
		}
	}

	/**
	 * 2274. 不含特殊楼层的最大连续楼层数
	 * Alice 管理着一家公司，并租用大楼的部分楼层作为办公空间。Alice 决定将一些楼层作为 特殊楼层 ，仅用于放松。
	 * 给你两个整数 bottom 和 top ，表示 Alice 租用了从 bottom 到 top（含 bottom 和 top 在内）的所有楼层。另给你一个整数数组 special ，其中 special[i] 表示  Alice 指定用于放松的特殊楼层。
	 * 返回不含特殊楼层的 最大 连续楼层数。
	 * 示例 1：
	 * 输入：bottom = 2, top = 9, special = [4,6]
	 * 输出：3
	 * 解释：下面列出的是不含特殊楼层的连续楼层范围：
	 * - (2, 3) ，楼层数为 2 。
	 * - (5, 5) ，楼层数为 1 。
	 * - (7, 9) ，楼层数为 3 。
	 * 因此，返回最大连续楼层数 3 。
	 * 示例 2：
	 * 输入：bottom = 6, top = 8, special = [7,6,8]
	 * 输出：0
	 * 解释：每层楼都被规划为特殊楼层，所以返回 0 。
	 * 提示
	 * 1 <= special.length <= 105
	 * 1 <= bottom <= special[i] <= top <= 109
	 * special 中的所有值 互不相同
	 */
	static class MaxConsecutive {

		/**
		 * 思路:
		 * [2,3,4,5,6,7,8,9]   special=[4,6]
		 * 模拟指针滑动,从楼层区间开始,当不等special[i]的值时,计数;其中i从0开始递增,每次遇见相等时递增
		 * 当滑动到的楼层等于special[i] 则计数初始为0,i++
		 * 重复以上步骤,直到楼层遍历完成
		 *
		 * @param bottom
		 * @param top
		 * @param special
		 * @return
		 */
		public static int maxConsecutive(int bottom, int top, int[] special) {
			Arrays.sort(special);
			int s = 0, count = 0, max = 0;
			for (int i = bottom; i <= top; i++) {
				if (s == special.length) {  // 后面已没有特殊楼层
					max = Math.max(top - i + 1, max);
					break;
				}
				if (i == special[s]) {  // 如果遍历到的楼层刚好是特殊楼层,初始化计数
					max = Math.max(max, count);
					count = 0;
					s++;
				} else {
					count++;
				}
			}
			return max;
		}

		/**
		 * 不使用指针遍历的方式
		 *
		 * @param bottom
		 * @param top
		 * @param special
		 * @return
		 */
		public static int maxConsecutiveII(int bottom, int top, int[] special) {
			Arrays.sort(special);
			int s = 0, max = 0;
			while (bottom <= top) {
				if (s == special.length) {
					max = Math.max(top - bottom + 1, max);
					break;
				}
				max = Math.max(special[s] - bottom, max);
				bottom = special[s] + 1;
				s++;
			}
			return max;
		}

		/**
		 * 通过special数组长度次数的判断,就能得到最大值
		 *
		 * @param bottom
		 * @param top
		 * @param special
		 * @return
		 */
		public static int maxConsecutiveIII(int bottom, int top, int[] special) {
			Arrays.sort(special);
			int res = special[0] - bottom;
			for (int i = 1; i < special.length; i++) {
				res = Math.max(special[i] - special[i - 1] - 1, res);
			}
			res = Math.max(top - special[special.length - 1], res);
			return res;
		}

		public static void main(String[] args) {
			System.out.println(maxConsecutiveII(2, 9, new int[]{4, 6}));
		}
	}

	/**
	 * 2241. 设计一个 ATM 机器
	 * 一个 ATM 机器，存有 5 种面值的钞票：20 ，50 ，100 ，200 和 500 美元。初始时，ATM 机是空的。用户可以用它存或者取任意数目的钱。
	 * 取款时，机器会优先取 较大 数额的钱。
	 * 比方说，你想取 $300 ，并且机器里有 2 张 $50 的钞票，1 张 $100 的钞票和1 张 $200 的钞票，那么机器会取出 $100 和 $200 的钞票。
	 * 但是，如果你想取 $600 ，机器里有 3 张 $200 的钞票和1 张 $500 的钞票，那么取款请求会被拒绝，因为机器会先取出 $500 的钞票，然后无法取出剩余的 $100 。注意，因为有 $500 钞票的存在，机器 不能 取 $200 的钞票。
	 * 请你实现 ATM 类：
	 * ATM() 初始化 ATM 对象。
	 * void deposit(int[] banknotesCount) 分别存入 $20 ，$50，$100，$200 和 $500 钞票的数目。
	 * int[] withdraw(int amount) 返回一个长度为 5 的数组，分别表示 $20 ，$50，$100 ，$200 和 $500 钞票的数目，并且更新 ATM 机里取款后钞票的剩余数量。如果无法取出指定数额的钱，请返回 [-1] （这种情况下 不 取出任何钞票）。
	 * 示例 1：
	 * 输入：
	 * ["ATM", "deposit", "withdraw", "deposit", "withdraw", "withdraw"]
	 * [[], [[0,0,1,2,1]], [600], [[0,1,0,1,1]], [600], [550]]
	 * 输出：
	 * [null, null, [0,0,1,0,1], null, [-1], [0,1,0,0,1]]
	 * 解释：
	 * ATM atm = new ATM();
	 * atm.deposit([0,0,1,2,1]); // 存入 1 张 $100 ，2 张 $200 和 1 张 $500 的钞票。
	 * atm.withdraw(600);        // 返回 [0,0,1,0,1] 。机器返回 1 张 $100 和 1 张 $500 的钞票。机器里剩余钞票的数量为 [0,0,0,2,0] 。
	 * atm.deposit([0,1,0,1,1]); // 存入 1 张 $50 ，1 张 $200 和 1 张 $500 的钞票。
	 * // 机器中剩余钞票数量为 [0,1,0,3,1] 。
	 * atm.withdraw(600);        // 返回 [-1] 。机器会尝试取出 $500 的钞票，然后无法得到剩余的 $100 ，所以取款请求会被拒绝。
	 * // 由于请求被拒绝，机器中钞票的数量不会发生改变。
	 * atm.withdraw(550);        // 返回 [0,1,0,0,1] ，机器会返回 1 张 $50 的钞票和 1 张 $500 的钞票。
	 * 提示：
	 * banknotesCount.length == 5
	 * 0 <= banknotesCount[i] <= 109
	 * 1 <= amount <= 109
	 * 总共 最多有 5000 次 withdraw 和 deposit 的调用。
	 * 函数 withdraw 和 deposit 至少各有 一次 调用。
	 */
	static class ATM {

		private int[] cur;
		private int[] val;

		/**
		 * 思路:
		 * 用数组存储$20,$50,$100,$200,$500的数量,重点在于当取出钱的额度大于某个面值的货币且该货币数量大于0时,
		 * 优先使用这个货币,依次类推,直到取出的额度满足或者不满足
		 */
		public ATM() {
			val = new int[]{20, 50, 100, 200, 500};
			cur = new int[5];
		}

		public void deposit(int[] banknotesCount) {
			for (int i = 0; i < 5; i++) {
				if (banknotesCount[i] > 0) {
					cur[i] += banknotesCount[i];
				}
			}
		}

		public int[] withdraw(int amount) {
			int i = 4;
			int[] res = new int[5], fail = new int[]{-1};
			int[] pos = Arrays.copyOf(cur, 5);
			// 当没法取出时,不能扣减当前货币值
			// 循环减不太可取,应该用除法,一次性减掉所有最大货币能支付的值;并且一次扣减完一种货币,不用实时更新当前货币数量
			while (amount > 0 && i >= 0) {
				if (amount >= val[i] && pos[i] >= 1) {
					amount -= val[i];
					pos[i]--;
					res[i]++;
				} else {
					i--;
				}
			}
			if (amount == 0) {
				// 扣减掉取出的货币
				cur = pos;
				return res;
			}
			return fail;
		}

		public int[] withdrawI(int amount) {
			int[] res = new int[5], fail = new int[]{-1};
			for (int i = 4; i >= 0; --i) {
				// 每种货币扣减的数量为该种货币当前存在的数量和金额能够支付几张该货币的数量 两者中较小的那个值
				res[i] = Math.min(cur[i], amount / val[i]);
				amount -= res[i] * val[i];
			}
			if (amount > 0) { // 无法扣减完成
				return fail;
			} else {
				for (int i = 0; i <= 4; ++i) {
					cur[i] -= res[i];
				}
				return res;
			}
		}

		public static void main(String[] args) {
			ATM atm = new ATM();
			atm.deposit(new int[]{0, 0, 1, 2, 1});
			System.out.println(Arrays.toString(atm.withdrawI(600)));
			atm.deposit(new int[]{0, 1, 0, 1, 1});
			System.out.println(Arrays.toString(atm.withdrawI(600)));
			System.out.println(Arrays.toString(atm.withdrawI(550)));

			Dto dto = new Dto();
			Map<String, String> map = new HashMap<>();
			map.put("a", "123");
			map.put("b", "456");
			dto.setMap(map);
			System.out.println(JSON.toJSONString(dto));
		}

		@Data
		static class Dto {
			private Map<String, String> map;
		}
	}

	/**
	 * 3019. 按键变更的次数
	 * 给你一个下标从 0 开始的字符串 s ，该字符串由用户输入。按键变更的定义是：使用与上次使用的按键不同的键。例如 s = "ab" 表示按键变更一次，而 s = "bBBb" 不存在按键变更。
	 * 返回用户输入过程中按键变更的次数。
	 * 注意：shift 或 caps lock 等修饰键不计入按键变更，也就是说，如果用户先输入字母 'a' 然后输入字母 'A' ，不算作按键变更。
	 * 示例 1：
	 * 输入：s = "aAbBcC"
	 * 输出：2
	 * 解释：
	 * 从 s[0] = 'a' 到 s[1] = 'A'，不存在按键变更，因为不计入 caps lock 或 shift 。
	 * 从 s[1] = 'A' 到 s[2] = 'b'，按键变更。
	 * 从 s[2] = 'b' 到 s[3] = 'B'，不存在按键变更，因为不计入 caps lock 或 shift 。
	 * 从 s[3] = 'B' 到 s[4] = 'c'，按键变更。
	 * 从 s[4] = 'c' 到 s[5] = 'C'，不存在按键变更，因为不计入 caps lock 或 shift 。
	 * 示例 2：
	 * 输入：s = "AaAaAaaA"
	 * 输出：0
	 * 解释： 不存在按键变更，因为这个过程中只按下字母 'a' 和 'A' ，不需要进行按键变更。
	 * 提示：
	 * 1 <= s.length <= 100
	 * s 仅由英文大写字母和小写字母组成。
	 */
	static class CountKeyChanges {

		/**
		 * 思路:
		 * 逐个遍历字符,发生改变时计数+1,则标记字符更新
		 *
		 * @param s
		 * @return
		 */
		public static int countKeyChanges(String s) {
			char c = s.charAt(0);
			int count = 0;
			char[] arr = s.toCharArray();
			for (int i = 1; i < arr.length; ++i) {
				if (arr[i] != ' ') {
					if (c != arr[i] && Math.abs(c - arr[i]) != 32) {
						count++;
						c = arr[i];
					}
				}
			}
			return count;
		}

		public static int countKeyChangesII(String s) {
			char[] arr = s.toCharArray();
			int res = 0;
			for (int i = 1; i < arr.length; i++) {
				if ((arr[i - 1] & 31) != (arr[i] & 31)) {
					res++;
				}
			}
			return res;
		}

		public static void main(String[] args) {
			System.out.println(countKeyChanges("A aaAaAaaA"));
		}
	}

	/**
	 * 3285. 找到稳定山的下标
	 * 有 n 座山排成一列，每座山都有一个高度。给你一个整数数组 height ，其中 height[i] 表示第 i 座山的高度，再给你一个整数 threshold 。
	 * 对于下标不为 0 的一座山，如果它左侧相邻的山的高度 严格大于 threshold ，那么我们称它是 稳定 的。我们定义下标为 0 的山 不是 稳定的。
	 * 请你返回一个数组，包含所有 稳定 山的下标，你可以以 任意 顺序返回下标数组。
	 * 示例 1：
	 * 输入：height = [1,2,3,4,5], threshold = 2
	 * 输出：[3,4]
	 * 解释：
	 * 下标为 3 的山是稳定的，因为 height[2] == 3 大于 threshold == 2 。
	 * 下标为 4 的山是稳定的，因为 height[3] == 4 大于 threshold == 2.
	 * 示例 2：
	 * 输入：height = [10,1,10,1,10], threshold = 3
	 * 输出：[1,3]
	 * 示例 3：
	 * 输入：height = [10,1,10,1,10], threshold = 10
	 * 输出：[]
	 * 提示：
	 * 2 <= n == height.length <= 100
	 * 1 <= height[i] <= 100
	 * 1 <= threshold <= 100
	 */
	static class StableMountains {

		/**
		 * 思路:
		 * 逐个遍历,判断其左边的元素是否大于threshold
		 *
		 * @param height
		 * @param threshold
		 * @return
		 */
		public static List<Integer> stableMountains(int[] height, int threshold) {
			List<Integer> res = new ArrayList<>();
			for (int i = 1; i < height.length; i++) {
				if (height[i - 1] > threshold) res.add(i);
			}
			return res;
		}

		public static void main(String[] args) {
			System.out.println(stableMountains(new int[]{10, 1, 10, 1, 10}, 10));
		}
	}

	/**
	 * 3297. 统计重新排列后包含另一个字符串的子字符串数目 I
	 * 给你两个字符串 word1 和 word2 。
	 * 如果一个字符串 x 重新排列后，word2 是重排字符串的
	 * 前缀
	 * ，那么我们称字符串 x 是 合法的 。
	 * 请你返回 word1 中 合法
	 * 子字符串
	 * 的数目。
	 * 示例 1：
	 * 输入：word1 = "bcca", word2 = "abc"
	 * 输出：1
	 * 解释：
	 * 唯一合法的子字符串是 "bcca" ，可以重新排列得到 "abcc" ，"abc" 是它的前缀。
	 * 示例 2：
	 * 输入：word1 = "abcabc", word2 = "abc"
	 * 输出：10
	 * 解释：
	 * 除了长度为 1 和 2 的所有子字符串都是合法的。
	 * 示例 3：
	 * 输入：word1 = "abcabc", word2 = "aaabc"
	 * 输出：0
	 * 解释：
	 * 1 <= word1.length <= 105
	 * 1 <= word2.length <= 104
	 * word1 和 word2 都只包含小写英文字母。
	 */
	static class ValidSubstringCount {

		/**
		 * 思路:
		 * 本题的题意为 查找word1的所有子数组,子数组包含word2,则计数+1,求所有包含word2的子数组数量
		 * 按照常规思路一个字符的的子字符串,就是按照不通的长度,全部进行分割
		 * eg: word1="abacabc"  word2="abc"
		 * word1的子字符串,就是分割成长度为l=[3,7]的所有字符串
		 * l=3 -> aba,bac,aca,cab,abc
		 * l=4 -> abac,baca,acab,cabc
		 * ...
		 * l=7 - > abacabc
		 * 然后判断每个字符串wordx是否包含word2中的所有字母,可以计数每个字母的数量,然后比较数量是否满足count(wordx)>=count(word2)
		 * 不难发现,如果长度为3的子字符串 [l,l+3]满足条件,那么字符串[l,l+4]...[l,n-1]都满足条件,会出现大量的重复计算.
		 * ----------------------------------------------------------------------------------------------------
		 * 怎么避免重复的计算呢?
		 * 换个思路,可以统计每个字母开始的,满足情况的字符串数量
		 * s[0]代表idx=0位置字母开头字符串满足情况的场景
		 * s[0] : abac是第一个满足的,那么[abac..n-1]的字符串都满足
		 * ...
		 * s[n-3] :abc
		 * 这样的思路,就不需要重复的计算是否包含
		 * -----------------------------------------------------------------------------------------------------
		 * 那么如何将这种思路清晰化的模拟出来呢?
		 * 滑动窗口
		 * 变长的滑动窗口,记录窗口内每个字母与word2每个字母的差值
		 * 1.当找到满足情况的窗口时,窗口右边界q,那么计数n-q
		 * 2.上一步完成后,窗口左边界左移一位,继续重复1步骤(左边界p即计算以idx=p的字母开头的子字符串,是否满足条件)
		 * 直到窗口的长度q-p < word2.length() 结束
		 *
		 * @param word1
		 * @param word2
		 * @return
		 */
		public static long validSubstringCount(String word1, String word2) {
			int[] cnt = new int[26];
			for (int i = 0; i < word2.length(); i++) {
				cnt[word2.charAt(i) - 'a']--;
			}
			// p表示窗口左边界,q表示窗口右边界+1
			int p = 0, q = 0, n = word1.length();
			long count = 0;
			// 为什么这里q<=n,因为q从里面的while循环出来后,是指向窗体右边界的下一位的
			// 所以当q=n时,实际上q还未计算到cnt中,会漏掉这种情况
			while (q <= n && p < n) {
				while (q < n && !isContains(cnt)) {
					cnt[word1.charAt(q) - 'a']++;
					q++;
				}
				if (isContains(cnt)) {
					count += n - q + 1;
				}
				cnt[word1.charAt(p) - 'a']--;
				p++;
			}
			return count;
		}

		private static boolean isContains(int[] cnt) {
			for (int i : cnt) {
				if (i < 0) {
					return false;
				}
			}
			return true;
		}

		/**
		 * cnt 记录有差异的字母的个数
		 * diff[] 数组保存每个字母的差异数，当一个字母的差异数从-1变成0那么cnt则减少一个；
		 * 当一个字母的差异数从0变成-1，那么cnt则增加一个
		 * @param word1
		 * @param word2
		 * @return
		 */
		public static long validSubstringCountOfficial(String word1, String word2) {
			int[] diff = new int[26];
			for (int i = 0; i < word2.length(); i++) {
				diff[word2.charAt(i) - 'a']--;
			}
			long res = 0;
			int[] cnt = {(int) Arrays.stream(diff).filter(c -> c < 0).count()}; // 差异数小于0的个数
			int l = 0, r = 0;
			while (l < word1.length()) {
				while (r < word1.length() && cnt[0] > 0) {
					update(diff, word1.charAt(r) - 'a', 1, cnt);
					r++;
				}
				if (cnt[0] == 0) {
					res += word1.length() - r + 1;
				}
				update(diff, word1.charAt(l) - 'a', -1, cnt);
				l++;
			}
			return res;
		}

		private static void update(int[] diff, int c, int add, int[] cnt) {
			diff[c] += add;   // 表示c字母加/减1
			if (add == 1 && diff[c] == 0) {
				// 表示diff从-1变成0
				cnt[0]--;
			} else if (add == -1 && diff[c] == -1) {
				// 表示diff从0变成-1
				cnt[0]++;
			}
		}


		public static void main(String[] args) {
			System.out.println(validSubstringCount("ddccdddccdddccccdddccdcdcd", "ddc"));
		}
	}

	/**
	 * 2270. 分割数组的方案数
	 * 给你一个下标从 0 开始长度为 n 的整数数组 nums 。
	 * 如果以下描述为真，那么 nums 在下标 i 处有一个 合法的分割 ：
	 * 前 i + 1 个元素的和 大于等于 剩下的 n - i - 1 个元素的和。
	 * 下标 i 的右边 至少有一个 元素，也就是说下标 i 满足 0 <= i < n - 1 。
	 * 请你返回 nums 中的 合法分割 方案数。
	 * 示例 1：
	 * 输入：nums = [10,4,-8,7]
	 * 输出：2
	 * 解释：
	 * 总共有 3 种不同的方案可以将 nums 分割成两个非空的部分：
	 * - 在下标 0 处分割 nums 。那么第一部分为 [10] ，和为 10 。第二部分为 [4,-8,7] ，和为 3 。因为 10 >= 3 ，所以 i = 0 是一个合法的分割。
	 * - 在下标 1 处分割 nums 。那么第一部分为 [10,4] ，和为 14 。第二部分为 [-8,7] ，和为 -1 。因为 14 >= -1 ，所以 i = 1 是一个合法的分割。
	 * - 在下标 2 处分割 nums 。那么第一部分为 [10,4,-8] ，和为 6 。第二部分为 [7] ，和为 7 。因为 6 < 7 ，所以 i = 2 不是一个合法的分割。
	 * 所以 nums 中总共合法分割方案受为 2 。
	 * 示例 2：
	 * 输入：nums = [2,3,1,0]
	 * 输出：2
	 * 解释：
	 * 总共有 2 种 nums 的合法分割：
	 * - 在下标 1 处分割 nums 。那么第一部分为 [2,3] ，和为 5 。第二部分为 [1,0] ，和为 1 。因为 5 >= 1 ，所以 i = 1 是一个合法的分割。
	 * - 在下标 2 处分割 nums 。那么第一部分为 [2,3,1] ，和为 6 。第二部分为 [0] ，和为 0 。因为 6 >= 0 ，所以 i = 2 是一个合法的分割。
	 * 提示：
	 * 2 <= nums.length <= 105
	 * -105 <= nums[i] <= 105
	 */
	static class WaysToSplitArray {

		/**
		 * 思路:
		 * 数组元素,从i开始左部分和右部的和
		 * 这类题一般要提前计算s[i]即idx=i,开始到数组结尾元素的和
		 * 那么前半部分可以使用总和减去后半部分的和,注意int范围溢出
		 *
		 * @param nums
		 * @return
		 */
		public static int waysToSplitArray(int[] nums) {
			// 预计算idx=i,从i开始到数组结尾的元素和
			int n = nums.length;
			long[] sum = new long[n + 1];
			for (int i = nums.length - 1; i >= 0; --i) {
				sum[i] += sum[i + 1] + nums[i];
			}
			// 查找满足条件的i范围为:[0,n-2]
			int count = 0;
			for (int i = 0; i < n - 1; i++) {
				if (sum[0] - sum[i + 1] >= sum[i + 1]) {
					count++;
				}
			}
			return count;
		}

		/**
		 * 更好的方案,在计算idx=i的左边和时,可以累加计算
		 *
		 * @param nums
		 * @return
		 */
		public static int waysToSplitArrayII(int[] nums) {
			int n = nums.length;
			long[] sum = new long[n + 1];
			for (int i = nums.length - 1; i >= 0; --i) {
				sum[i] += sum[i + 1] + nums[i];
			}
			// 查找满足条件的i范围为:[0,n-2]
			int count = 0;
			long left = 0;
			for (int i = 0; i < n - 1; i++) {
				left += nums[i];
				if (left >= sum[i + 1]) {
					count++;
				}
			}
			return count;
		}

		/**
		 * 空间复杂度O(1)
		 * 计算和,左部分累加,右部分用和-左部分
		 * 尽量去思考时间复杂度和空间复杂度的最优解
		 *
		 * @param nums
		 * @return
		 */
		public static int waysToSplitArrayIII(int[] nums) {
			int n = nums.length;
			long sum = 0;
			for (int i = 0; i < n; i++) {
				sum += nums[i];
			}
			long left = 0;
			int count = 0;
			for (int i = 0; i < n - 1; i++) {
				left += nums[i];
				long right = sum - left;
				if (left >= right) {
					count++;
				}
			}
			return count;
		}

		public static void main(String[] args) {
			System.out.println(waysToSplitArrayII(new int[]{2, 3, 1, 0}));
		}
	}

	/**
	 * 3065. 超过阈值的最少操作数 I
	 * 给你一个下标从 0 开始的整数数组 nums 和一个整数 k 。
	 * 一次操作中，你可以删除 nums 中的最小元素。
	 * 你需要使数组中的所有元素都大于或等于 k ，请你返回需要的 最少 操作次数。
	 * 示例 1：
	 * 输入：nums = [2,11,10,1,3], k = 10
	 * 输出：3
	 * 解释：第一次操作后，nums 变为 [2, 11, 10, 3] 。
	 * 第二次操作后，nums 变为 [11, 10, 3] 。
	 * 第三次操作后，nums 变为 [11, 10] 。
	 * 此时，数组中的所有元素都大于等于 10 ，所以我们停止操作。
	 * 使数组中所有元素都大于等于 10 需要的最少操作次数为 3 。
	 * 示例 2：
	 * 输入：nums = [1,1,2,4,9], k = 1
	 * 输出：0
	 * 解释：数组中的所有元素都大于等于 1 ，所以不需要对 nums 做任何操作。
	 * 示例 3：
	 * 输入：nums = [1,1,2,4,9], k = 9
	 * 输出：4
	 * 解释：nums 中只有一个元素大于等于 9 ，所以需要执行 4 次操作。
	 * 提示：
	 * 1 <= nums.length <= 50
	 * 1 <= nums[i] <= 109
	 * 1 <= k <= 109
	 * 输入保证至少有一个满足 nums[i] >= k 的下标 i 存在
	 */
	static class MinOperations {

		/**
		 * 思路,排序之后计数满足要求的元素排在哪个序列
		 * 可以用插入排序,冒泡排序找到一个大于等于k的数就停止
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static int minOperations(int[] nums, int k) {
			Arrays.sort(nums);
			for (int i = 0; i < nums.length; i++) {
				if (nums[i] >= k) {
					return i;
				}
			}
			return 0;
		}

		/**
		 * 完整的一次遍历,找到比k大的元素个数
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static int minOperationsII(int[] nums, int k) {
			int res = 0;
			for (int i = 0; i < nums.length; i++) {
				if (nums[i] < k) res++;
			}
			return res;
		}

		public static void main(String[] args) {
			System.out.println(minOperations(new int[]{1, 1, 2, 4, 9}, 9));
		}
	}

	/**
	 * 3066. 超过阈值的最少操作数 II
	 * 给你一个下标从 0 开始的整数数组 nums 和一个整数 k 。
	 * 一次操作中，你将执行：
	 * 选择 nums 中最小的两个整数 x 和 y 。
	 * 将 x 和 y 从 nums 中删除。
	 * 将 min(x, y) * 2 + max(x, y) 添加到数组中的任意位置。
	 * 注意，只有当 nums 至少包含两个元素时，你才可以执行以上操作。
	 * 你需要使数组中的所有元素都大于或等于 k ，请你返回需要的 最少 操作次数。
	 * 示例 1：
	 * 输入：nums = [2,11,10,1,3], k = 10
	 * 输出：2
	 * 解释：第一次操作中，我们删除元素 1 和 2 ，然后添加 1 * 2 + 2 到 nums 中，nums 变为 [4, 11, 10, 3] 。
	 * 第二次操作中，我们删除元素 3 和 4 ，然后添加 3 * 2 + 4 到 nums 中，nums 变为 [10, 11, 10] 。
	 * 此时，数组中的所有元素都大于等于 10 ，所以我们停止操作。
	 * 使数组中所有元素都大于等于 10 需要的最少操作次数为 2 。
	 * 示例 2：
	 * 输入：nums = [1,1,2,4,9], k = 20
	 * 输出：4
	 * 解释：第一次操作后，nums 变为 [2, 4, 9, 3] 。
	 * 第二次操作后，nums 变为 [7, 4, 9] 。
	 * 第三次操作后，nums 变为 [15, 9] 。
	 * 第四次操作后，nums 变为 [33] 。
	 * 此时，数组中的所有元素都大于等于 20 ，所以我们停止操作。
	 * 使数组中所有元素都大于等于 20 需要的最少操作次数为 4 。
	 * 提示：
	 * 2 <= nums.length <= 2 * 105
	 * 1 <= nums[i] <= 109
	 * 1 <= k <= 109
	 * 输入保证答案一定存在，也就是说一定存在一个操作序列使数组中所有元素都大于等于 k 。
	 */
	static class MinOperationsII {

		/**
		 * 思路:使用优先级队列,每次操作时进行出队和入队
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static int minOperations(int[] nums, int k) {
			int n = nums.length;
			PriorityQueue<Long> priorityQueue = new PriorityQueue<>(n);
			for (int x : nums) {
				priorityQueue.offer((long) x);
			}
			int res = 0;
			while (priorityQueue.peek() < k) {
				// 弹出两个元素,计算要放入的元素 first<=second
				long first = priorityQueue.poll();
				long second = priorityQueue.poll();
				priorityQueue.offer(first * 2 + second);
				res++;
			}
			return res;
		}

		public static void main(String[] args) {
			System.out.println(minOperations(new int[]{1000000000, 999999999, 1000000000, 999999999, 1000000000, 999999999}, 1000000000));
		}
	}

	/**
	 * 3095. 或值至少 K 的最短子数组 I
	 * 给你一个 非负 整数数组 nums 和一个整数 k 。
	 * 如果一个数组中所有元素的按位或运算 OR 的值 至少 为 k ，那么我们称这个数组是 特别的 。
	 * 请你返回 nums 中 最短特别非空
	 * 子数组
	 * 的长度，如果特别子数组不存在，那么返回 -1 。
	 * 示例 1：
	 * 输入：nums = [1,2,3], k = 2
	 * 输出：1
	 * 解释：
	 * 子数组 [3] 的按位 OR 值为 3 ，所以我们返回 1 。
	 * 注意，[2] 也是一个特别子数组。
	 * 示例 2：
	 * 输入：nums = [2,1,8], k = 10
	 * 输出：3
	 * 解释：
	 * 子数组 [2,1,8] 的按位 OR 值为 11 ，所以我们返回 3 。
	 * 示例 3：
	 * 输入：nums = [1,2], k = 0
	 * 输出：1
	 * 解释：
	 * 子数组 [1] 的按位 OR 值为 1 ，所以我们返回 1 。
	 * 提示：
	 * 1 <= nums.length <= 50
	 * 0 <= nums[i] <= 50
	 * 0 <= k < 64
	 */
	static class minimumDifference {

		/**
		 * 思路:`
		 * 常见思路暴力匹配,遍历所有的子数组,然后字段or值,其中or值是可以往后传递的
		 * 先确定子数组的右边界为i,i从0开始;左边界为j j从i-1开始
		 * 当i=1时,nums[0]-nums[1]的OR记录存放在nums[0]中
		 * 当i=2时,nums[1]-nums[2]的OR记录存放在nums[1]中,num[0]-nums[2]的OR记录存放在nums[0]中
		 * 总结就是当确定右边界i时,子数据是从右到左反向遍历的,那么num[j]中存放的就是num[j]-nums[i]的OR值
		 * 所以数据不会被覆盖,每次从右到左都是重新计算OR值
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static int minimumSubarrayLength(int[] nums, int k) {
			int n = nums.length, res = Integer.MAX_VALUE;
			for (int i = 0; i < n; i++) {
				// 单个元素也算子数组
				int x = nums[i];
				if (x >= k) res = Math.min(res, 1);
				for (int j = i - 1; j >= 0; --j) {
					nums[j] |= x;
					if (nums[j] >= k) {
						res = Math.min(res, i - j + 1);
					}
				}
			}
			return res == Integer.MAX_VALUE ? -1 : res;
		}

		public static void main(String[] args) {
			System.out.println(minimumSubarrayLength(new int[]{2,1,8}, 10));
		}
	}

	/**
	 * 3097. 或值至少为 K 的最短子数组 II
	 * 给你一个 非负 整数数组 nums 和一个整数 k 。
	 * 如果一个数组中所有元素的按位或运算 OR 的值 至少 为 k ，那么我们称这个数组是 特别的 。
	 * 请你返回 nums 中 最短特别非空
	 * 子数组
	 * 的长度，如果特别子数组不存在，那么返回 -1 。
	 * 示例 1：
	 * 输入：nums = [1,2,3], k = 2
	 * 输出：1
	 * 解释：
	 * 子数组 [3] 的按位 OR 值为 3 ，所以我们返回 1 。
	 * 示例 2：
	 * 输入：nums = [2,1,8], k = 10
	 * 输出：3
	 * 解释：
	 * 子数组 [2,1,8] 的按位 OR 值为 11 ，所以我们返回 3 。
	 * 示例 3：
	 * 输入：nums = [1,2], k = 0
	 * 输出：1
	 * 解释：
	 * 子数组 [1] 的按位 OR 值为 1 ，所以我们返回 1 。
	 * 提示：
	 * 1 <= nums.length <= 2 * 105
	 * 0 <= nums[i] <= 109
	 * 0 <= k <= 109
	 */
	static class MinimumSubarrayLength {

		/**
		 * 思路：
		 * 1.与运算A|B>=A 所以从左到右进行与运算时,其结果值会单调递增,所以可以使用滑动窗口
		 * * left为窗口左端,right为窗口右端,先右移right,当有与值>=k时,那么从left-right最少有一个子数组满足情况
		 * * 本题要求找到子数组中长度最小的满足与值>=k的情况,所以右移left,找到以right为窗口右端点最小的子数组
		 * 2.如何解决与运算的窗口滑动呢? 二进制与,当有一位上有一个1,则该位为1;如果有一位上都是0,则该位为0
		 * * 统计每一位上0的个数即可,题中nums[i]最大为10^9 转换成二进制为30位
		 *
		 * @param nums
		 * @param k
		 * @return
		 */
		public static int minimumSubarrayLength(int[] nums, int k) {
			int n = nums.length, res = Integer.MAX_VALUE;
			int[] arr = new int[30];
			for (int l = 0, r = 0; r < n; r++) {
				// r右移,此时需要计算每位上1的个数,从0位开始
				for (int i = 0; i < 30; i++) {
					if (((nums[r] >> i) & 1) == 1) {
						arr[i]++;
					}
				}
				// r右移后判断窗口内与值是否小于等于k,同时让l右移
				while (l <= r && calc(arr) >= k) {
					// 如果直接能进入while循环,则说明r右移后满足条件,记录子数组长度,然后l右移
					res = Math.min(res, r - l + 1);
					for (int i = 0; i < 30; i++) {
						if (((nums[l] >> i) & 1) == 1) {
							arr[i]--;
						}
					}
					l++;
				}
			}
			return res == Integer.MAX_VALUE ? -1 : res;
		}

		/**
		 * 通过每位上1的个数计算与值
		 *
		 * @param arr
		 * @return
		 */
		private static int calc(int[] arr) {
			int s = 0;
			for (int i = 0; i < 30; i++) {
				if (arr[i] > 0) {
					// s|=(1<<i); //这里或运算和加运算结果一样,都是统计每位上为1的值的和
					s += (1 << i);
				}
			}
			return s;
		}

		public static void main(String[] args) {
			System.out.println(minimumSubarrayLength(new int[]{2, 1, 8}, 10));
		}
	}

	/**
	 * 2239. 找到最接近 0 的数字
	 * 给你一个长度为 n 的整数数组 nums ，请你返回 nums 中最 接近 0 的数字。如果有多个答案，请你返回它们中的 最大值 。
	 * 示例 1：
	 * 输入：nums = [-4,-2,1,4,8]
	 * 输出：1
	 * 解释：
	 * -4 到 0 的距离为 |-4| = 4 。
	 * -2 到 0 的距离为 |-2| = 2 。
	 * 1 到 0 的距离为 |1| = 1 。
	 * 4 到 0 的距离为 |4| = 4 。
	 * 8 到 0 的距离为 |8| = 8 。
	 * 所以，数组中距离 0 最近的数字为 1 。
	 * 示例 2：
	 * 输入：nums = [2,-1,1]
	 * 输出：1
	 * 解释：1 和 -1 都是距离 0 最近的数字，所以返回较大值 1 。
	 * 提示：
	 * 1 <= n <= 1000
	 * -105 <= nums[i] <= 105
	 */
	static class FindClosestNumber {

		/**
		 * 思路:
		 * 简单的办法是,算平均值,比较平均值最小的元素 ;如果平均值一样返回正数
		 *
		 * @param nums
		 * @return
		 */
		public static int findClosestNumber(int[] nums) {
			int n = nums.length, abs = Integer.MAX_VALUE;
			boolean symbol = false;
			for (int i = 0; i < n; i++) {
				if (nums[i] > 0) {  // 整数
					if (nums[i] <= abs) {
						abs = nums[i];
						symbol = true;
					}
				} else {  // 负数
					if (-nums[i] < abs) {
						abs = -nums[i];
						symbol = false;
					}
				}
			}
			return symbol ? abs : -abs;
		}

		public static void main(String[] args) {
			System.out.println(findClosestNumber(new int[]{-10000,10000}));
		}
	}

}
