package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
