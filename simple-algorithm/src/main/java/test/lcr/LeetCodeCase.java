package test.lcr;

import com.sun.management.VMOption;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiTypeLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class LeetCodeCase {

	/**
	 * LCR 001. 两数相除
	 * 给定两个整数 a 和 b ，求它们的除法的商 a/b ，要求不得使用乘号 '*'、除号 '/' 以及求余符号 '%' 。
	 * 注意：
	 * 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
	 * 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−231, 231−1]。本题中，如果除法结果溢出，则返回 231 − 1
	 * 示例 1：
	 * 输入：a = 15, b = 2
	 * 输出：7
	 * 解释：15/2 = truncate(7.5) = 7
	 * 示例 2：
	 * 输入：a = 7, b = -3
	 * 输出：-2
	 * 解释：7/-3 = truncate(-2.33333..) = -2
	 * 示例 3：
	 * 输入：a = 0, b = 1
	 * 输出：0
	 * 示例 4：
	 * 输入：a = 1, b = 1
	 * 输出：1
	 * 提示:
	 * -231 <= a, b <= 231 - 1
	 * b != 0
	 * 注意：本题与主站 29 题相同：https://leetcode-cn.com/problems/divide-two-integers/
	 */
	static class Divide {

		/**
		 * 思路:
		 * 除法可以循环使用减法,减法的效率非常低
		 *
		 * @param a
		 * @param b
		 * @return
		 */
		public static int divide(int a, int b) {
			int i = 0;
			if (a == Integer.MIN_VALUE && b == -1) return Integer.MAX_VALUE;
			if (a == 0) return 0;
			if (b == 1) return a;
			boolean flag = a > 0 && b > 0 || a < 0 && b < 0;
			// 由于负数的可表示范围更大,都转为负数
			if (a > 0) {
				a = -a;
			}
			if (b > 0) {
				b = -b;
			}
			while (a <= b) {
				a -= b;
				i++;
			}
			return flag ? i : -i;
		}

		public static void main(String[] args) {
			int divide = divide(-2147483648, 2);
			System.out.println(divide);
		}
	}

	/**
	 * 855. 考场就座
	 * 在考场里，一排有 N 个座位，分别编号为 0, 1, 2, ..., N-1 。
	 * 当学生进入考场后，他必须坐在能够使他与离他最近的人之间的距离达到最大化的座位上。如果有多个这样的座位，他会坐在编号最小的座位上。(另外，如果考场里没有人，那么学生就坐在 0 号座位上。)
	 * 返回 ExamRoom(int N) 类，它有两个公开的函数：其中，函数 ExamRoom.seat() 会返回一个 int （整型数据），代表学生坐的位置；函数 ExamRoom.leave(int p) 代表坐在座位 p 上的学生现在离开了考场。每次调用 ExamRoom.leave(p) 时都保证有学生坐在座位 p 上。
	 * 示例：
	 * 输入：["ExamRoom","seat","seat","seat","seat","leave","seat"], [[10],[],[],[],[],[4],[]]
	 * 输出：[null,0,9,4,2,null,5]
	 * 解释：
	 * ExamRoom(10) -> null
	 * seat() -> 0，没有人在考场里，那么学生坐在 0 号座位上。
	 * seat() -> 9，学生最后坐在 9 号座位上。
	 * seat() -> 4，学生最后坐在 4 号座位上。
	 * seat() -> 2，学生最后坐在 2 号座位上。
	 * leave(4) -> null
	 * seat() -> 5，学生最后坐在 5 号座位上。
	 * 提示：
	 * 1 <= N <= 10^9
	 * 在所有的测试样例中 ExamRoom.seat() 和 ExamRoom.leave() 最多被调用 10^4 次。
	 * 保证在调用 ExamRoom.leave(p) 时有学生正坐在座位 p 上。
	 */
	static class ExamRoom {

		private TreeSet<int[]> set = new TreeSet<>((o1, o2) -> {
			int d1 = dist(o1), d2 = dist(o2);
			return d1 == d2 ? o1[0] - o2[0] : d2 - d1;
		});
		private Map<Integer, Integer> left = new HashMap<>();
		private Map<Integer, Integer> right = new HashMap<>();
		private int n;

		/**
		 * 用TreeSet保存可选作为的区间
		 * 用HashMap保存每个元素被选择时的左右坐标
		 *
		 * @param n
		 */
		public ExamRoom(int n) {
			this.n = n;
			set.add(new int[]{-1, n});
		}

		public int seat() {
			int[] f = set.first();
			int p = (f[0] + f[1]) >> 1;
			if (f[0] == -1) {
				p = 0;
			} else if (f[1] == n) {
				p = n - 1;
			}
			del(f);
			add(new int[]{f[0], p});
			add(new int[]{p, f[1]});
			return p;
		}

		public void leave(int p) {
			int l = left.get(p), r = right.get(p);
			del(new int[]{l, p});
			del(new int[]{p, r});
			add(new int[]{l, r});
		}

		private void add(int[] s) {
			set.add(s);
			left.put(s[1], s[0]);
			right.put(s[0], s[1]);
		}

		private void del(int[] s) {
			set.remove(s);
			left.remove(s[1]);
			right.remove(s[0]);
		}

		private int dist(int[] s) {
			int l = s[0], r = s[1];
			return l == -1 || r == n ? r - l - 1 : (r - l) >> 1;
		}

		public static void main(String[] args) {
			ExamRoom er = new ExamRoom(10);
			System.out.println(er.seat());
			System.out.println(er.seat());
			System.out.println(er.seat());
			System.out.println(er.seat());
			er.leave(4);
			System.out.println(er.seat());
		}
	}
}
