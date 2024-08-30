package test.math;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.sun.javafx.logging.JFRInputEvent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 数学思路
 */
public class LeetCodeCase {

	/**
	 * 238. 除自身以外数组的乘积
	 * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
	 * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
	 * 请 不要使用除法，且在 O(n) 时间复杂度内完成此题。
	 * 示例 1:
	 * 输入: nums = [1,2,3,4]
	 * 输出: [24,12,8,6]
	 * 示例 2:
	 * 输入: nums = [-1,1,0,-3,3]
	 * 输出: [0,0,9,0,0]
	 * 提示：
	 * 2 <= nums.length <= 105
	 * -30 <= nums[i] <= 30
	 * 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内
	 * 进阶：你可以在 O(1) 的额外空间复杂度内完成这个题目吗？（ 出于对空间复杂度分析的目的，输出数组 不被视为 额外空间。）
	 */
	public static class ProductExceptSelf {

		/**
		 * 个人思路:
		 * 常规思路,存储数组元素,然后逐次计算每个元素在值 时间复杂度O(n^2)不符合题目要求
		 * 官方思路一: 提前规范除出当前元素的左边和右边乘积,通过两个循环
		 * 因为常规思路中,每次累乘除自身以外的其他元素,会重复遍历多次相同的元素;如何才能避免这些重复遍历呢?
		 * 最先想到的思路是,当前元素的左-右乘积和先计算出来,而通过两个循环就能很容易的计算出来
		 * <p>
		 * 空间复杂度 O(1) 的方法,通过查看官方解题思路,实际上是用原数据存放左积;在循环中计算右积然后得到接到放到原数组中
		 *
		 * @param nums
		 * @return
		 */
		public static int[] productExceptSelf(int[] nums) {
			int[] result = new int[nums.length];
			int[] left = new int[nums.length];
			int[] right = new int[nums.length];
			left[0] = 1;
			right[nums.length - 1] = 1;
			for (int i = 1; i < nums.length; i++) {
				left[i] = left[i - 1] * nums[i - 1];
			}
			for (int i = nums.length - 2; i >= 0; i--) {
				right[i] = right[i + 1] * nums[i + 1];
			}
			for (int i = 0; i < nums.length; i++) {
				result[i] = left[i] * right[i];
			}
			return result;
		}

		public static int[] productExceptSelfI(int[] nums) {
			int[] result = new int[nums.length];
			result[0] = 1;
			for (int i = 1; i < nums.length; i++) {
				result[i] = result[i - 1] * nums[i - 1];
			}
			int r = 1;
			for (int i = nums.length - 1; i >= 0; i--) {  // r从最右侧开始,这样result中的数据不会被覆盖
				result[i] = result[i] * r;
				r = r * nums[i];
			}
			return result;
		}

		public static void main(String[] args) {
			int[] ints = productExceptSelfI(new int[]{-1, 1, 0, -3, 3});
			System.out.println(Arrays.toString(ints));

			Map<String, String> map = new HashMap<>();
			map.put("BDA", "123");
			String role;
			String s = ObjectUtil.isNull(role = map.get("ABC")) ? "" : role;
			System.out.println(s);
		}
	}

	/**
	 * 343. 整数拆分
	 * 给定一个正整数 n ，将其拆分为 k 个 正整数 的和（ k >= 2 ），并使这些整数的乘积最大化。
	 * 返回 你可以获得的最大乘积 。
	 * 示例 1:
	 * 输入: n = 2
	 * 输出: 1
	 * 解释: 2 = 1 + 1, 1 × 1 = 1。
	 * 示例 2:
	 * 输入: n = 10
	 * 输出: 36
	 * 解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36。
	 * 提示: 2 <= n <= 58
	 * 2 <= n <= 58
	 */
	public static class IntegerBreak {

		/**
		 * 个人思路:
		 * dp的思路还是比较难理清楚的,本题的状态转移,并不是由dp[i-1]和dp[i-2]这种确认的值来的到dp[i]
		 * 而是需要在dp[i-1]...dp[1]中来找.
		 * 正整数i 可以拆分成j和i-j
		 * 那么dp[i] = j*(i-j)
		 * 如果i-j还可以继续往下拆分呢?这是个递归回溯思想. dp[i]=j*dp[i-j]
		 * 综合来看 dp[i]=max{j*(i-j),j*dp[i-j]}  其中j是可以为任意拆分值,范围为[1,i-1]
		 * 遍历j得到所有的dp[i],然后取最大值,感觉j取1没有意义
		 *
		 * @param n
		 * @return
		 */
		public static int integerBreak(int n) {
			int[] dp = new int[n + 1];
			dp[1] = 1;
			dp[2] = 1;
			for (int i = 3; i <= n; i++) {
				int max = 0;
				for (int j = 2; j < i; j++) {
					max = Math.max(max, Math.max(j * (i - j), j * dp[i - j]));  // 只拆分一次,或者一直拆分
				}
				dp[i] = max;
			}
			return dp[n];
		}

		/**
		 * 数学思路
		 * 比如一个数5,它在进行拆分时,拆出1是没有意义的,从2开始拆成2和3
		 * 比如一个数字6,拆分成3,3  2,4 2,2,2 一般来说拆得越小乘积越大
		 * 当f>4时  (f-2)*2 = 2f-4 > f 所以当一个正整数大于4 则拆出2肯定乘积变大
		 * 同理 当f>4时 (f-3)*3 =3f -9 > f  所以当一个正整数大于4 拆出3乘积也变大
		 * 2f-4 公式1
		 * 3f-9 公式2
		 * 公式2-公式1得:f-5 当f>=5时优先拆出3;当f=4时 拆2
		 *
		 * @param n
		 * @return
		 */
		public static int integerBreakI(int n) {
			if (n == 1 || n == 2) return 1;
			if (n == 3) return 2;
			if (n == 4) return 4;
			int s = 1;
			while (n > 0) {
				if (n == 2 || n == 3 || n == 4) {  // 当f>=5时需要拆分,f属于[1,4]时直接得出结果
					return s * n;
				}
				s *= 3;
				n -= 3;
			}
			return s;
		}

		public static void main(String[] args) {
			System.out.println(integerBreak(12));
			System.out.println(integerBreakI(12));
		}
	}

	/**
	 * 89. 格雷编码
	 * n 位格雷码序列 是一个由 2n 个整数组成的序列，其中：
	 * 每个整数都在范围 [0, 2n - 1] 内（含 0 和 2n - 1）
	 * 第一个整数是 0
	 * 一个整数在序列中出现 不超过一次
	 * 每对 相邻 整数的二进制表示 恰好一位不同 ，且
	 * 第一个 和 最后一个 整数的二进制表示 恰好一位不同
	 * 给你一个整数 n ，返回任一有效的 n 位格雷码序列 。
	 * 示例 1：
	 * 输入：n = 2
	 * 输出：[0,1,3,2]
	 * 解释：
	 * [0,1,3,2] 的二进制表示是 [00,01,11,10] 。
	 * - 00 和 01 有一位不同
	 * - 01 和 11 有一位不同
	 * - 11 和 10 有一位不同
	 * - 10 和 00 有一位不同
	 * [0,2,3,1] 也是一个有效的格雷码序列，其二进制表示是 [00,10,11,01] 。
	 * - 00 和 10 有一位不同
	 * - 10 和 11 有一位不同
	 * - 11 和 01 有一位不同
	 * - 01 和 00 有一位不同
	 * 示例 2：
	 * 输入：n = 1
	 * 输出：[0,1]
	 * 提示：
	 * 1 <= n <= 16
	 */
	public static class GrayCode {

		/**
		 * 个人思路:
		 * 包含2^n个整数 [0,2^n-1]
		 * 数组满足规律[0...2^(n-1)] 其中每个相邻的数相差为{1,2,4...2^(n-2)} 能不能相差就是1和2呢?
		 * <p>
		 * 个人思路中,题意理解错误.....
		 */
		public static List<Integer> grayCode(int n) {
			int m = (int) Math.pow(2, n);
			int[] res = new int[m];
			int last = (int) Math.pow(2, n - 1);
			int i = 0;
			for (; i < last; i++) {
				res[i] = i;
			}
			// 处理最后一位
			res[m - 1] = last;
			// 现在处理 last...2^n次方的数   [0,1,2,3,4,5,6,7...8] j=last
			int p = i - 1, q = m - 1;
			while (p <= q - 2) {
				if (q - p == 2) {
					res[p + 1] = res[p] + 2;
					break;
				} else {
					res[p + 1] = res[p] + 2;
					p++;
					res[q - 1] = res[q] + 2;
					q--;
				}
			}
			return Arrays.stream(res).boxed().collect(Collectors.toList());
		}

		public static List<Integer> grayCodeOfficial(int n) {
			List<Integer> ret = new ArrayList<>();
			ret.add(0);
			for (int i = 1; i <= n; i++) {
				int m = ret.size();
				for (int j = m - 1; j >= 0; j--) {
					ret.add(ret.get(j) | (1 << (i - 1)));
				}
			}
			return ret;
		}

		public static void main(String[] args) {
			System.out.println(JSON.toJSONString(grayCodeOfficial(4)));

		}
	}

	/**
	 * 1823. 找出游戏的获胜者
	 * 共有 n 名小伙伴一起做游戏。小伙伴们围成一圈，按 顺时针顺序 从 1 到 n 编号。确切地说，从第 i 名小伙伴顺时针移动一位会到达第 (i+1) 名小伙伴的位置，其中 1 <= i < n ，从第 n 名小伙伴顺时针移动一位会回到第 1 名小伙伴的位置。
	 * 游戏遵循如下规则：
	 * 从第 1 名小伙伴所在位置 开始 。
	 * 沿着顺时针方向数 k 名小伙伴，计数时需要 包含 起始时的那位小伙伴。逐个绕圈进行计数，一些小伙伴可能会被数过不止一次。
	 * 你数到的最后一名小伙伴需要离开圈子，并视作输掉游戏。
	 * 如果圈子中仍然有不止一名小伙伴，从刚刚输掉的小伙伴的 顺时针下一位 小伙伴 开始，回到步骤 2 继续执行。
	 * 否则，圈子中最后一名小伙伴赢得游戏。
	 * 给你参与游戏的小伙伴总数 n ，和一个整数 k ，返回游戏的获胜者。
	 * 示例 1：
	 * 输入：n = 5, k = 2
	 * 输出：3
	 * 解释：游戏运行步骤如下：
	 * 1) 从小伙伴 1 开始。
	 * 2) 顺时针数 2 名小伙伴，也就是小伙伴 1 和 2 。
	 * 3) 小伙伴 2 离开圈子。下一次从小伙伴 3 开始。
	 * 4) 顺时针数 2 名小伙伴，也就是小伙伴 3 和 4 。
	 * 5) 小伙伴 4 离开圈子。下一次从小伙伴 5 开始。
	 * 6) 顺时针数 2 名小伙伴，也就是小伙伴 5 和 1 。
	 * 7) 小伙伴 1 离开圈子。下一次从小伙伴 3 开始。
	 * 8) 顺时针数 2 名小伙伴，也就是小伙伴 3 和 5 。
	 * 9) 小伙伴 5 离开圈子。只剩下小伙伴 3 。所以小伙伴 3 是游戏的获胜者。
	 * 示例 2：
	 * 输入：n = 6, k = 5
	 * 输出：1
	 * 解释：小伙伴离开圈子的顺序：5、4、6、2、3 。小伙伴 1 是游戏的获胜者。
	 * 提示：
	 * 1 <= k <= n <= 500
	 * 进阶：你能否使用线性时间复杂度和常数空间复杂度解决此问题？
	 */
	public static class FindTheWinner {

		/**
		 * 个人思路:
		 * 约瑟夫环问题
		 * 常规方式就是模拟,通过链表模拟整个删除元素的过程-环形链表最直接
		 * 当然时间复杂度和空间复杂度会比较高 链表需要构造和删除、移动
		 *
		 * @param n
		 * @param k
		 * @return
		 */
		public static int findTheWinner(int n, int k) {
			// 构造链表-节点的值就是其顺序索引
			LinkedList<Integer> list = new LinkedList<>();
			for (int i = 1; i <= n; i++) {
				list.add(i);
			}
			// 模拟过程,从索引0开始
			int i = 0;
			while (list.size() > 1) {
				int delIndex = i + k - 1;
				if (delIndex >= list.size()) {  // 下一个要删除的节点超出链表的索引
					delIndex = delIndex % list.size();
				}
				i = delIndex;  // 当前delIndex索引位置的节点被删除,那么delIndex索引自动到下一个节点位置
				list.remove(delIndex);
			}
			return list.poll();
		}

		/**
		 * 官方运用 队列模拟环形遍历的游戏过程
		 * 1.已遍历过但不移除的元素,需要加到队尾->相当于构造环形遍历
		 * 2.每经过k-1次遍历,就需要移除队首元素
		 * 3.如果k=1 每次都是弹出队首
		 *
		 * @param n
		 * @param k
		 * @return
		 */
		public static int findTheWinnerOfficialI(int n, int k) {
			Queue<Integer> queue = new ArrayDeque<>(n);
			for (int i = 1; i <= n; i++) {
				queue.offer(i);
			}
			while (queue.size() > 1) {
				for (int i = 1; i < k; i++) {  // 每移动k-1次,删除队首元素
					queue.offer(queue.poll()); // 弹出队首放入队尾
				}
				queue.poll(); // 弹出队首
			}
			return queue.peek(); // 此时队列只剩一个元素
		}

		/*
		 *1.dp[n]移除一个数字之后变成新的dp‘[n-1]问题，=>dp[n] = dp'[n-1]
		 *2.dp'[n-1]有n-1个数字，顺序为：m,m+1,m+2...m+x
		 *3.dp[n-1]也有n-1个数字，顺序为：0,1,2...x
		 *4.dp[n-1]解的位置和dp'[n-1]的解在同一列的位置, =>根据观察同一列的数字转换关系，dp[n-1]解和dp'[n-1]的解的关系为dp'[n-1] = (dp[n-1]+m)%n， => 所以 dp[n] = (dp[n-1]+m)%n
		 *
		 * 状态转移表达式: dp[n] =(dp[n-1]+k)%n
		 */
		public static int findTheWinnerDp(int n, int k) {
			int x = 0;
			for (int i = 2; i <= n; i++) {
				x = (x + k) % i;
			}
			return x + 1;  // 序列从0开始,实际是从1开始
		}

		/**
		 * 数学思路解题法---后续学习
		 *
		 * @param args
		 */

		public static void main(String[] args) {
			System.out.println(findTheWinnerDp(5, 2));
		}
	}

	/**
	 * 65. 有效数字
	 * 给定一个字符串 s ，返回 s 是否是一个 有效数字。
	 * 例如，下面的都是有效数字："2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93",
	 * "-123.456e789"，而接下来的不是："abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"。
	 * 一般的，一个 有效数字 可以用以下的规则之一定义：
	 * 一个 整数 后面跟着一个 可选指数。
	 * 一个 十进制数 后面跟着一个 可选指数。
	 * 一个 整数 定义为一个 可选符号 '-' 或 '+' 后面跟着 数字。
	 * 一个 十进制数 定义为一个 可选符号 '-' 或 '+' 后面跟着下述规则：
	 * 数字 后跟着一个 小数点 .。
	 * 数字 后跟着一个 小数点 . 再跟着 数位。
	 * 一个 小数点 . 后跟着 数位。
	 * 指数 定义为指数符号 'e' 或 'E'，后面跟着一个 整数。
	 * 数字 定义为一个或多个数位。
	 * 示例 1：
	 * 输入：s = "0"
	 * 输出：true
	 * 示例 2：
	 * 输入：s = "e"
	 * 输出：false
	 * 示例 3：
	 * 输入：s = "."
	 * 输出：false
	 * 提示：
	 * 1 <= s.length <= 20
	 * s 仅含英文字母（大写和小写），数字（0-9），加号 '+' ，减号 '-' ，或者点 '.' 。
	 */
	public static class IsNumber {

		/**
		 * 个人思路:
		 * 有限状态自动机思路,先列出状态表
		 *
		 * @param s
		 * @return
		 */
		public static boolean isNumber(String s) {
			Auto auto = new Auto();
			for (int i = 0; i < s.length(); i++) {
				if (auto.getState(s.charAt(i)).equals("end"))
					return false;
			}
			return auto.state.equals("number") || (auto.state.equals(".") && s.length() != 1);
		}

		public static void main(String[] args) {
			boolean number = isNumberOfficial("+.8");
			System.out.println(number);
		}

		static class Auto {

			private Map<String, Map<String, List<String>>> map;
			private boolean isHavePoint = false;
			private boolean isHaveEe = false;
			private boolean isHaveSymbol = false;
			private String state = "start";
			private List<String> list = Arrays.asList("+-", "number", ".");

			public Auto() {
				init();
			}

			private void init() {
				map = new HashMap<>();
				Map<String, List<String>> startMap = new HashMap<>();
				startMap.put(".", Collections.singletonList("number"));
				startMap.put("+-", Arrays.asList("number", "."));
				startMap.put("number", Arrays.asList("number", ".", "Ee"));
				startMap.put("Ee", Collections.singletonList("end"));
				startMap.put("other", Collections.singletonList("end"));
				map.put("start", startMap);

				Map<String, List<String>> symbolMap = new HashMap<>();
				symbolMap.put(".", Arrays.asList("number", "."));
				symbolMap.put("+-", Collections.singletonList("end"));
				symbolMap.put("number", Arrays.asList("number", ".", "Ee"));
				symbolMap.put("Ee", Collections.singletonList("end"));
				symbolMap.put("other", Collections.singletonList("end"));
				map.put("+-", symbolMap);

				Map<String, List<String>> noMap = new HashMap<>();
				noMap.put(".", Collections.singletonList("number"));
				noMap.put("+-", Collections.singletonList("end"));
				noMap.put("number", Arrays.asList("number", ".", "Ee"));
				noMap.put("Ee", Arrays.asList("number", "+-"));
				noMap.put("other", Collections.singletonList("end"));
				map.put("number", noMap);

				Map<String, List<String>> pointMap = new HashMap<>();
				pointMap.put(".", Collections.singletonList("end"));
				pointMap.put("+-", Collections.singletonList("end"));
				pointMap.put("number", Collections.singletonList("number"));
				pointMap.put("Ee", Collections.singletonList("end"));
				pointMap.put("other", Collections.singletonList("end"));
				map.put(".", pointMap);

				Map<String, List<String>> other = new HashMap<>();
				other.put(".", Collections.singletonList("end"));
				other.put("+-", Collections.singletonList("end"));
				other.put("number", Collections.singletonList("end"));
				other.put("Ee", Collections.singletonList("end"));
				other.put("other", Collections.singletonList("end"));
				map.put("other", other);

				Map<String, List<String>> ee = new HashMap<>();
				ee.put(".", Collections.singletonList("end"));
				ee.put("+-", Collections.singletonList("number"));
				ee.put("number", Collections.singletonList("number"));
				ee.put("Ee", Collections.singletonList("end"));
				ee.put("other", Collections.singletonList("end"));
				map.put("Ee", ee);

				Map<String, List<String>> endMap = new HashMap<>();
				endMap.put(".", Collections.singletonList("end"));
				endMap.put("+-", Collections.singletonList("end"));
				endMap.put("number", Collections.singletonList("end"));
				endMap.put("Ee", Collections.singletonList("end"));
				endMap.put("other", Collections.singletonList("end"));
				map.put("end", endMap);
			}

			private String getState(char c) {
				String s = get(c);
				if (!list.contains(s)) {
					return "end";
				}
				// 还得加入end状态节点才行
				if (s.equals(".") && (isHavePoint || isHaveEe || !(state.equals("number") || state.equals("start") || state.equals("+-")))) {
					return "end";
				} else if (s.equals("+-") && isHaveSymbol && !state.equals("Ee")) {
					return "end";
				} else if (s.equals("Ee") && isHaveEe) {
					return "end";
				}
				if (s.equals(".")) isHavePoint = true;
				if (s.equals("+-")) isHaveSymbol = true;
				if (s.equals("Ee")) isHaveEe = true;
				Map<String, List<String>> processMap = map.get(state);
				list = processMap.get(s);
				state = s;
				return "process";
			}

			private String get(char c) {
				if (c == '.') {
					return ".";
				} else if (c == '+' || c == '-') {
					return "+-";
				} else if (c == 'E' || c == 'e') {
					return "Ee";
				} else if (Character.isDigit(c)) {
					return "number";
				} else {
					return "other";
				}
			}
		}

		public static boolean isNumberOfficial(String s) {
			// 状态对象该状态符合条件的char类型,以及下一个状态值
			Map<State, Map<CharType, State>> map = new HashMap<>();
			Map<CharType, State> initMap = new HashMap<>();
			initMap.put(CharType.CHAR_NUMBER, State.INTEGER);
			initMap.put(CharType.CHAR_SIGN, State.INI_SIGN);
			initMap.put(CharType.CHAR_POINT, State.POINT_WITHOUT_INT);
			map.put(State.INITIAL, initMap);

			Map<CharType, State> intSignMap = new HashMap<>();
			intSignMap.put(CharType.CHAR_NUMBER, State.INTEGER);
			intSignMap.put(CharType.CHAR_POINT, State.POINT_WITHOUT_INT);
			map.put(State.INI_SIGN, intSignMap);

			Map<CharType, State> intMap = new HashMap<>();
			intMap.put(CharType.CHAR_NUMBER, State.INTEGER);
			intMap.put(CharType.CHAR_POINT, State.POINT);
			intMap.put(CharType.CHAR_EXP, State.EXP);
			map.put(State.INTEGER, intMap);

			Map<CharType, State> pointMap = new HashMap<>();
			pointMap.put(CharType.CHAR_NUMBER, State.STATE_FRACTION);
			pointMap.put(CharType.CHAR_EXP, State.EXP);
			map.put(State.POINT, pointMap);

			Map<CharType, State> pointWithoutIntMap = new HashMap<>();
			pointWithoutIntMap.put(CharType.CHAR_NUMBER, State.STATE_FRACTION);
			map.put(State.POINT_WITHOUT_INT, pointWithoutIntMap);

			Map<CharType, State> fractionMap = new HashMap<>();
			fractionMap.put(CharType.CHAR_NUMBER, State.STATE_FRACTION);
			fractionMap.put(CharType.CHAR_EXP, State.EXP);
			map.put(State.STATE_FRACTION, fractionMap);

			Map<CharType, State> expMap = new HashMap<>();
			expMap.put(CharType.CHAR_NUMBER, State.EXP_NUMBER);
			expMap.put(CharType.CHAR_SIGN, State.EXP_SIGN);
			map.put(State.EXP, expMap);

			Map<CharType, State> expSignMap = new HashMap<>();
			expSignMap.put(CharType.CHAR_NUMBER, State.EXP_NUMBER);
			map.put(State.EXP_SIGN, expSignMap);

			Map<CharType, State> expNumMap = new HashMap<>();
			expNumMap.put(CharType.CHAR_NUMBER, State.EXP_NUMBER);
			map.put(State.EXP_NUMBER, expNumMap);

			State state = State.INITIAL;
			for (int i = 0; i < s.length(); i++) {
				CharType charType = toCharType(s.charAt(i));
				if (!map.get(state).containsKey(charType)) {
					return false;
				} else {
					state = map.get(state).get(charType);
				}
			}
			return state == State.INTEGER || state == State.STATE_FRACTION || state == State.EXP_NUMBER || state == State.POINT || state == State.END;
		}


		public static CharType toCharType(char c) {
			if (c >= '0' && c <= '9') {
				return CharType.CHAR_NUMBER;
			} else if (c == 'e' || c == 'E') {
				return CharType.CHAR_EXP;
			} else if (c == '.') {
				return CharType.CHAR_POINT;
			} else if (c == '+' || c == '-') {
				return CharType.CHAR_SIGN;
			} else {
				return CharType.CHAR_ILLEGAL;
			}
		}

		enum State {
			INITIAL,  // 初始化状态
			INI_SIGN, // 初始化-符号状态
			INTEGER,  // 整数位状态
			POINT,    // 小数点位状态
			POINT_WITHOUT_INT, // 小点数状态(左侧无整数)
			STATE_FRACTION,  // 小数位状态
			EXP,        // 指数位状态
			EXP_SIGN,   // 指数符号位状态
			EXP_NUMBER, // 指数数字位状态
			END         // 结束状态
		}

		enum CharType {
			CHAR_NUMBER, // 数字类型Char
			CHAR_EXP,    // 指数
			CHAR_POINT,  // 小数点
			CHAR_SIGN,   // 符号
			CHAR_ILLEGAL // 非法字符
		}
	}

	/**
	 * 400. 第 N 位数字
	 * 给你一个整数 n ，请你在无限的整数序列 [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...] 中找出并返回第 n 位上的数字。
	 * 示例 1：
	 * 输入：n = 3
	 * 输出：3
	 * 示例 2：
	 * 输入：n = 11
	 * 输出：0
	 * 解释：第 11 位数字在序列 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ... 里是 0 ，它是 10 的一部分。
	 * 提示：
	 * 1 <= n <= 231 - 1
	 */
	public static class FindNthDigit {

		/**
		 * 个人思路:
		 * 1位数字 1,2,3...9       ---9
		 * 2位数字 10,11,12...99   ---90*2
		 * 3位数字 100,101...999   ---900*3
		 * ...
		 * n位数字 10000......999  ---9*10^n*(n+1) n>=0;
		 *
		 * @param n
		 * @return
		 */
		public static int findNthDigit(int n) {
			if (n <= 9) {
				return n;
			}
			int i = 1;
			double v = 9, s = n;
			// 单独处理1-9的数字,从1开始的
			while (n > v) {
				s -= v;
				v = 9 * Math.pow(10, i) * (i + 1);
				i++;

			}
			// 当n<=v时,这说明n在这一列中
			int idx1 = (int) s % i; // 在这一列上的第几个数的第几位
			if (s > Math.pow(10, i - 1)) {
				s = s - Math.pow(10, i - 1);
			}
			long idx = idx1 == 0 ? (long) (s / i) - 1 : ((long) s / i); // 这个地方非常难处理,用这种方式 需要另找更好的方式,这种思路本身就是错误的
			// 在这一列中的第几个数
			// 比如第四列的第234个数 idx=234, idx1=1时,234不足4则需要首位补1
			// 此处的思路是转字符串,然后取字符串中的位数
			String idxStr = String.valueOf(idx);
			if (idxStr.length() < i) {
				idxStr = "1" + idxStr;
			}
			for (int j = 0; j < idxStr.length() - i; j++) {
				idxStr = j == 0 ? "1" : "0" + idxStr;
			}
			return idxStr.charAt(idxStr.length() - 1 - (idx1)) - '0';
		}

		/**
		 * 整理后的思路
		 * 获取某个数的某个位置的int值的技巧,同二进制获取某一位一样***********************
		 *
		 * @param n
		 * @return
		 */
		public static int findNthDigitI(int n) {
			int i = 1, l = 9;
			long v;
			while (n > (v = (long) l * i)) {
				n -= (int) v;
				i++;
				l *= 10;
			}
			int start = (int) Math.pow(10, i - 1);
			// 这里定位到指定的元素,有一个技巧,需要减1,再除以位数-得到的值就是第几位 除以位数取余-得到的就是这一位上的第几个数字,首位从0开始
			int num = start + ((n - 1) / i);
			int idx = (n - 1) % i;
			// 要取到num上的idx位的值,根据10进制原则,除以相应的10的次方,得到该为的值之后,再取余
			// 比如 198 取第1位的9;先除以10^1 = 19 然后除以10取余,得到9  (*************) 这里是关键点
			return (num / (int) Math.pow(10, i - idx - 1)) % 10;
		}

		public static int findNthDigitOfficial(int n) {
			int d = 1, count = 9;
			while (n > (long) d * count) {
				n -= d * count;
				d++;
				count *= 10;
			}
			int index = n - 1;
			int start = (int) Math.pow(10, d - 1);
			int num = start + index / d;
			int digitIndex = index % d;
			int digit = (num / (int) (Math.pow(10, d - digitIndex - 1))) % 10;
			return digit;
		}

		public static void main(String[] args) {
			int nthDigit = findNthDigitI(21);
			int nthDigit1 = findNthDigitOfficial(21);
			System.out.println(nthDigit);
			System.out.println(nthDigit1);
		}
	}

	/**
	 * 233. 数字 1 的个数
	 * 给定一个整数 n，计算所有小于等于 n 的非负整数中数字 1 出现的个数。
	 * 示例 1：
	 * 输入：n = 13
	 * 输出：6
	 * 示例 2：
	 * 输入：n = 0
	 * 输出：0
	 * 提示：
	 * 0 <= n <= 109
	 */
	public static class CountDigitOne {

		/**
		 * 找到递推公式
		 * 这题居然是字节/帆软的一面
		 * 以358为例,百位都能很快的找出1的个数,他拥有完整的0-9 10-99 100-299 后面还有300-358
		 *
		 * @param n
		 * @return
		 */
		public static int countDigitOne(int n) {
			/*if (n == 0) return 0;
			int[] arr = new int[9];
			int i = 0, x = 1, v, t = 0, s = 0;
			while (n > (v = x * 9)) {
				x = x * 10 + x;
				arr[i] = t;
				t = (int) Math.pow(10, i) + 9 * t;
				i++;
			}
			int num = v + 1;*/
			// 比如num=358 那么需要计算 [100-358]之间1的个数
			// 可以分成[100-300]之间1的个数、[10-58]之间1的个数、[0-10]之间1的个数
			// 这样考虑有点复杂,需要找更优解
			// n=1234567
			// [0-999] => [100-199](存在1) 、 [200-299] 、[400-499] ... [900-999]百位上会出现1的次数为100
			// n有1234个1000的循环,那么百位上的1出现次数就为: 1234*100 => (n/1000)*100;
			// 对于剩余不在这些完全的循环中的部分位[000-567],而567= n%1000 记为n';而这一部分在百位数上出现1的次数可以分类:
			// 1.当n'<100时,例如1234089,此时n'=89,百位上出现1的次数为0;
			// 2.当100<=n'<200时,例如1234123,此时n'=123,百位上出现1的次数范围是[100,n']即n'-100+1;
			// 3.当n'>=200时,例如1234265,此时n'=265,百位上出现1的次数为100;
			// 所以当n'<100时,n'-100+1<0,我们期望其等于0;当n'>=200时,n'-100+1>100,我们期望其等于100;
			// 得到百位上的次数为: min(max(n'-100+1,0),100);
			// 此时可得到[0,n]中百位上为1的次数为:n/1000*100+min(max(n%1000-100+1,0),100);
			// 那么我们可以得出其在其他位上出现1的次数,设k=0,1,2,3...代表[个位]、[十位]、[百位]、[千位],那么第k位上出现1的次数:
			// n/(10^(k+1))*10^k+min(max(n%10^(k+1)-10^k,0),10^k);
			// 思路的关键在于,每一位去统计1出现的次数,然后累加就是总次数
			int mk = 1, ans = 0;
			// 循环计算每位上出现1的次数
			while (n >= mk) {
				ans += n / (mk * 10) * mk + Math.min(Math.max(n % (mk * 10) - mk + 1, 0), mk);
				mk *= 10;
			}
			return ans;
		}

		public static int countDigitOneI(int n) {
			// mulk 表示 10^k
			// 在下面的代码中，可以发现 k 并没有被直接使用到（都是使用 10^k）
			// 但为了让代码看起来更加直观，这里保留了 k
			long mulk = 1;
			int ans = 0;
			for (int k = 0; n >= mulk; ++k) {
				ans += (n / (mulk * 10)) * mulk + Math.min(Math.max(n % (mulk * 10) - mulk + 1, 0), mulk);
				mulk *= 10;
			}
			return ans;
		}

		public static void main(String[] args) {
			System.out.println(countDigitOne(1234567));
			System.out.println(countDigitOneI(1234567));
		}
	}

	/**
	 * 9. 回文数
	 * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
	 * 回文数
	 * 是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
	 * 例如，121 是回文，而 123 不是。
	 * 示例 1：
	 * 输入：x = 121
	 * 输出：true
	 * 示例 2：
	 * 输入：x = -121
	 * 输出：false
	 * 解释：从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
	 * 示例 3：
	 * 输入：x = 10
	 * 输出：false
	 * 解释：从右向左读, 为 01 。因此它不是一个回文数。
	 * 提示：
	 * -231 <= x <= 231 - 1
	 * 进阶：你能不将整数转为字符串来解决这个问题吗？
	 */
	public static class IsPalindrome {

		/**
		 * 不转换成字符串来解决,那么我们还是需要这个数字每一位的值,然后匹配其是否对称
		 *
		 * @param x
		 * @return
		 */
		public static boolean isPalindrome(int x) {
			if (x < 0) return false;
			List<Integer> list = new ArrayList<>();
			while (x > 0) {
				list.add(x % 10);
				x /= 10;
			}
			for (int i = 0; i < list.size() / 2; i++) {
				if (!Objects.equals(list.get(i), list.get(list.size() - i - 1))) {
					return false;
				}
			}
			return true;
		}

		/**
		 * 优化的地方在于,不用把数字的所有位都计算出来,计算一半即可与另一半进行比较
		 *
		 * @param x
		 * @return
		 */
		public static boolean isPalindromeI(int x) {
			if (x < 0 || (x % 10 == 0 && x != 0)) return false;
			int reverse = 0;  // 后一半数字
			while (x > reverse) {
				reverse = reverse * 10 + x % 10;
				x /= 10;
			}
			return x == reverse || x == reverse / 10; // 位数为奇数时,reverse会多一位
		}

		public static void main(String[] args) {
			System.out.println(isPalindrome(121121));
		}
	}

	/**
	 * 172. 阶乘后的零
	 * 给定一个整数 n ，返回 n! 结果中尾随零的数量。
	 * 提示 n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1
	 * 示例 1：
	 * 输入：n = 3
	 * 输出：0
	 * 解释：3! = 6 ，不含尾随 0
	 * 示例 2：
	 * 输入：n = 5
	 * 输出：1
	 * 解释：5! = 120 ，有一个尾随 0
	 * 示例 3：
	 * 输入：n = 0
	 * 输出：0
	 * 提示：
	 * 0 <= n <= 104
	 * 进阶：你可以设计并实现对数时间复杂度的算法来解决此问题吗？
	 */
	public static class TrailingZeroes {

		/**
		 * 阶乘后尾随0的个数,只有当2x5时才会出现0
		 * 而阶乘的规律是递减数字的乘积,例如 10!=10*9*8*7*6*5*4*3*2*1
		 * 其中10可以转换为2*5 4可以转换为2*2*2 4可以转换为2*2
		 * 可以看出,10的阶乘中5出现2次,2出现了6次,每间隔一个数字就会出现2,所以2出现的次数一定大于5;
		 * 所以本题可以转换成,阶乘的乘法因子中出现5的次数
		 * 阶乘中每隔5个数,就会出现一个5
		 * 1,2,3,4,5...(2*5)....(3*5)...
		 * 阶乘中每隔25个数,会出现两个5
		 * 1,2,3,4,5....(1*5*5)....(2*5*5)
		 * 阶乘中每隔125个数,会出现3个5
		 * n/5+(n-5)/5+...+5/5
		 * 1,2,3,4,5....(1*5*5*5)....(2*5*5*5)...
		 * 那么计算总共出现5的个数,即每隔5个数出现5的个数+每隔25个数出现5的个数+每隔125个数出现的5的个数+...
		 * 即 n/5+n/25+n/125+...+n/(5*5*5...)
		 *
		 * @param n
		 * @return
		 */
		public static int trailingZeroes(int n) {
			int count = 0;
			while (n != 0) {
				n /= 5;
				count += n;
			}
			return count;
		}

		public static void main(String[] args) {
			System.out.println(trailingZeroes(10));
		}
	}

	/**
	 * 149. 直线上最多的点数
	 * 给你一个数组 points ，其中 points[i] = [xi, yi] 表示 X-Y 平面上的一个点。求最多有多少个点在同一条直线上。
	 * 示例 1：
	 * 输入：points = [[1,1],[2,2],[3,3]]
	 * 输出：3
	 * 示例 2：
	 * 输入：points = [[1,1],[3,2],[5,3],[4,1],[2,3],[1,4]]
	 * 输出：4
	 * 提示：
	 * 1 <= points.length <= 300
	 * points[i].length == 2
	 * -104 <= xi, yi <= 104
	 * points 中的所有点 互不相同
	 */
	public static class MaxPoints {

		/**
		 * 按照常规的思路思路,2点确认一条直线
		 * 要找出一条直线上点最多的场景,需要把所有的直线全部计算一遍有多少个点经过
		 * 直线的方程:y=ax+b 先确认这个方程,然后将其他点带入方程,计算是否符合条件
		 * 但是可以计算斜率,k=Δx/Δy
		 * 那么会有三种情况
		 * 1.Δx和Δy都不为0,正常情况
		 * 2.Δx=0 则直线方程与y轴平行,只需要x坐标值相同即可
		 * 3.Δy=0 则直线方程与x轴平行,只需要y坐标值相同即可
		 *
		 * @param points
		 * @return
		 */
		public static int maxPoints(int[][] points) {
			if (points.length <= 1) return 1;
			int max = 0;
			for (int i = 0; i < points.length; i++) {
				for (int j = i + 1; j < points.length; j++) {
					// 先使用两点确认一条直线
					int count = 0;
					int[] a = points[i], b = points[j];
					int dx = b[0] - a[0], dy = b[1] - a[1];
					if (dx == 0) {  // 水平线,寻找x坐标相同的点
						for (int l = 0; l < points.length; l++) {
							if (l == i || l == j || points[l][0] == a[0]) {
								count++;
							}
						}
					} else if (dy == 0) {   // 垂直线,寻找y坐标相同的点
						for (int l = 0; l < points.length; l++) {
							if (l == i || l == j || points[l][1] == a[1]) {
								count++;
							}
						}
					} else {  // 其他线
						double k = (double) dy / dx;
						for (int l = 0; l < points.length; l++) {
							if (l == i || l == j || (double) (points[l][1] - a[1]) / (points[l][0] - a[0]) == k) {
								count++;
							}
						}
					}
					max = Math.max(count, max);
				}
			}
			return max;
		}

		/**
		 * 上个解题方法中在计算斜率的值  斜率k=Δx/Δy
		 * 除法会出现小数点,然后损失精度,可以用乘法替代,所以也不用考虑除法中分母为0的情况
		 *
		 * @param points
		 * @return
		 */
		public static int maxPointsI(int[][] points) {
			if (points.length <= 1) return 1;
			int max = 0;
			for (int i = 0; i < points.length; i++) {
				for (int j = i + 1; j < points.length; j++) {
					// 先使用两点确认一条直线
					int count = 2;
					int[] a = points[i], b = points[j];
					int dx = b[0] - a[0], dy = b[1] - a[1];
					for (int l = j + 1; l < points.length; l++) {
						int[] p = points[l];
						if (dy * (p[0] - b[0]) == dx * (p[1] - b[1])) {
							count++;
						}
					}
					max = Math.max(count, max);
				}
			}
			return max;
		}


		/**
		 * 官方解题有一些优化点
		 * 1.当点数小于2时,直接返回点数的数量
		 * 2.当枚举到点i时,只需要考虑大于i的点到点i的斜率,因为小于点i的点,在之前肯定已经枚举过了,如果在同一条直线上,那么也已经计数过了
		 * 3.当这条直线经过的点数量超过了所有点的一半时,说明该条直线上经过的点最多,后续的直线不需要遍历了
		 * 4.当枚举到点i时,至多能找到n-1个共线点,如果此前找到的共线点数量的最大值为k,如果k>=n-i,那么这条直线就是要找的直线
		 *
		 * @param points
		 * @return
		 */
		public static int maxPointsII(int[][] points) {
			int n = points.length, ans = 1;
			for (int i = 0; i < n; i++) {
				int[] x = points[i];
				for (int j = i + 1; j < n; j++) {
					int cnt = 2;
					int[] y = points[j];
					for (int k = j + 1; k < n; k++) {
						int[] p = points[k];
						int s1 = (y[1] - x[1]) * (p[0] - y[0]);
						int s2 = (p[1] - y[1]) * (y[0] - x[0]);
						if (s1 == s2) cnt++;
					}
					if (cnt > n / 2) {
						return cnt;
					}
					ans = Math.max(ans, cnt);
					if (ans >= n - i) {
						return ans;
					}
				}
			}
			return ans;
		}

		public static void main(String[] args) {
			int i = maxPointsII(new int[][]{{1,1},{3,2},{5,3},{4,1},{2,3},{1,4}});
			System.out.println(i);
		}
	}
}
