package test.dynamicProgramming;

/**
 * 应先独立思考之后,有了一定的认知,再进行复习,吸取他人优秀的思想
 */
public class LeetCodeCase {

	/**
	 * 509. 斐波那契数
	 * 斐波那契数 （通常用 F(n) 表示）形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
	 * F(0) = 0，F(1) = 1
	 * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
	 * 给定 n ，请计算 F(n) 。
	 * 示例 1：
	 * 输入：n = 2
	 * 输出：1
	 * 解释：F(2) = F(1) + F(0) = 1 + 0 = 1
	 * 示例 2：
	 * 输入：n = 3
	 * 输出：2
	 * 解释：F(3) = F(2) + F(1) = 1 + 1 = 2
	 * 示例 3：
	 * 输入：n = 4
	 * 输出：3
	 * 解释：F(4) = F(3) + F(2) = 2 + 1 = 3
	 * 提示：
	 * 0 <= n <= 30
	 */
	public static class Fib {

		/**
		 * 思路:
		 * 根据递推公式,正向推进,通过n次就能求解
		 *
		 * @param n
		 * @return
		 */
		public static int fib(int n) {
			if (n == 0) return 0;
			if (n == 1) return 1;
			int pre = 1, ppre = 0, v = 0;
			for (int i = 2; i <= n; i++) {
				v = pre + ppre;
				ppre = pre;
				pre = v;
			}
			return v;
		}

		public static void main(String[] args) {
			int fib = fib(10);
			System.out.println(fib);
		}
	}

	/**
	 * 746. 使用最小花费爬楼梯
	 * 给你一个整数数组 cost ，其中 cost[i] 是从楼梯第 i 个台阶向上爬需要支付的费用。一旦你支付此费用，即可选择向上爬一个或者两个台阶。
	 * 你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。
	 * 请你计算并返回达到楼梯顶部的最低花费。
	 * 示例 1：
	 * 输入：cost = [10,15,20]
	 * 输出：15
	 * 解释：你将从下标为 1 的台阶开始。
	 * - 支付 15 ，向上爬两个台阶，到达楼梯顶部。
	 * 总花费为 15 。
	 * 示例 2：
	 * 输入：cost = [1,100,1,1,1,100,1,1,100,1]
	 * 输出：6
	 * 解释：你将从下标为 0 的台阶开始。
	 * - 支付 1 ，向上爬两个台阶，到达下标为 2 的台阶。
	 * - 支付 1 ，向上爬两个台阶，到达下标为 4 的台阶。
	 * - 支付 1 ，向上爬两个台阶，到达下标为 6 的台阶。
	 * - 支付 1 ，向上爬一个台阶，到达下标为 7 的台阶。
	 * - 支付 1 ，向上爬两个台阶，到达下标为 9 的台阶。
	 * - 支付 1 ，向上爬一个台阶，到达楼梯顶部。
	 * 总花费为 6 。
	 * 提示：
	 * 2 <= cost.length <= 1000
	 * 0 <= cost[i] <= 999
	 */
	public static class MinCostClimbingStairs {

		/**
		 * 思路：
		 * 楼梯问题都需要逆向去思考,然后找到他的状态转移方程.
		 * 当需要达到下标为i的台阶时,则只可能是从下标为i-1的台阶走一步 或者 下标为i-2的台阶走两步;
		 * 而需要达到下标为i-1或者i-2的台阶,则跟上一步一样;
		 * 所以达到下标为i的台阶,最小的花费状态转移方程: dp[i]=min(dp[i-1]+cost[i-1],dp[i-2]+cost[i-2]);
		 * 得到状态转移方程之后,只需要正常循环求dp数组即可
		 *
		 * @param cost
		 * @return
		 */
		public static int minCostClimbingStairs(int[] cost) {
			int[] dp = new int[cost.length + 1];
			// i为0和1时,都是0 int数组初始化值就是0,所以赋值可以省掉
			dp[0] = 0;
			dp[1] = 0;
			for (int i = 2; i < cost.length + 1; i++) {
				dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
			}
			return dp[cost.length];
		}

		/**
		 * 参考官方思路中,由于dp[i]的值只与dp[i-1]和dp[i-2]有关,所以跟求楼梯爬法是相同的场景
		 * 可以不使用状态数组
		 *
		 * @param cost
		 * @return
		 */
		public static int minCostClimbingStairs_I(int[] cost) {
			int pre = 0, ppre = 0;
			for (int i = 2; i < cost.length + 1; i++) {
				int t = pre;
				pre = Math.min(pre + cost[i - 1], ppre + cost[i - 2]);
				ppre = t;
			}
			return pre;
		}

		public static void main(String[] args) {
			int[] cost = new int[]{10, 15, 20};
			// int[] cost = new int[]{1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
			// int i = minCostClimbingStairs(cost);
			int i = minCostClimbingStairs_I(cost);
			System.out.println(i);
		}
	}

	/**
	 * 0-1背包问题
	 * 对于一组不同重量、不可分割的物品,需要选择一些装入背包,在满足背包最大重量限制的前提下,
	 * 求背包中物品总重量的最大值.
	 */
	public static class bag_01 {

		private static int max = 0;

		/**
		 * 个人思路:
		 * 1.回溯思路,画出递归树,从根节点出发,下第一层是第一个物品的放置,有两种选择 - 放 或 不放
		 * 下第二层是第二个物品的放置,有两种选择 - 放 或 不放
		 * 2.退出条件 w>=9 或者 物品已放完
		 * 3.递归子过程,每一个物品都有放入和不放入两种情况
		 * 4.回溯实现,减去已放置的物品
		 * 5.回溯的剪枝-同一层物品的放置,如果其重量相同,则可以直接退出
		 * <p>
		 * 6.动态规划-状态数组
		 * <p>
		 * 7.动态规划-状态数组优化
		 *
		 * @param arr 物品集合
		 * @param w   背包可放最大重量
		 * @return 背包中物品重重量最大值
		 */
		public static int getMaxWeight(int[] arr, int w) {
			dfs(arr, w, 0, 0);
			return max;
		}

		private static void dfs(int[] arr, int w, int m, int i) {
			if (m > w || i == arr.length) { // 超出重量或者物品放完,需要回溯
				if (m <= w) max = Math.max(m, max);
				return;
			} else {
				max = Math.max(m, max);
			}
			dfs(arr, w, m, i + 1);                   // 不放
			if (m + arr[i] <= w) {
				dfs(arr, w, m + arr[i], i + 1);  // 放
			}
		}

		private static boolean[][] mem;

		private static void init(int n, int m) {
			mem = new boolean[n][m];
		}

		/**
		 * 递归剪枝
		 *
		 * @param arr
		 * @param w
		 * @param m
		 * @param i
		 */
		private static void dfs_I(int[] arr, int w, int m, int i) {
			if (m > w || i == arr.length) { // 超出重量或者物品放完,需要回溯
				if (m <= w) max = Math.max(m, max);
				return;
			} else {
				max = Math.max(m, max);
			}
			if (mem[i][m]) {
				return;
			} else {
				mem[i][m] = true;
			}
			dfs(arr, w, m, i + 1);                   // 不放
			if (m + arr[i] <= w) {
				dfs(arr, w, m + arr[i], i + 1);  // 放
			}
		}

		private static int getMaxWeight_I(int[] arr, int w) {
			init(arr.length, w);
			dfs_I(arr, w, 0, 0);
			return max;
		}

		/**
		 * 二维dp数组
		 *
		 * @param arr
		 * @param w
		 * @return
		 */
		private static int getMaxWeight_II(int[] arr, int w) {
			boolean[][] dp = new boolean[arr.length][w + 1];
			dp[0][0] = true;
			if (arr[0] <= w) {
				dp[0][arr[0]] = true;
			}
			for (int i = 1; i < arr.length; i++) {
				// dp数组每层元素的计算
				for (int j = 0; j <= w; j++) {
					if (dp[i - 1][j]) {
						dp[i][j] = true;                // 不放
						if (j + arr[i] <= w) {
							dp[i][j + arr[i]] = true;   // 放
						}
					}
				}
			}
			for (int i = w; i >= 0; i--) {         // 输出dp数组的最后一行的最大列值
				if (dp[arr.length - 1][i]) {
					return i;
				}
			}
			return 0;
		}

		/**
		 * 二位数组简化为一维数组
		 *
		 * @param arr
		 * @param w
		 * @return
		 */
		private static int getMaxWeight_III(int[] arr, int w) {
			boolean[] dp = new boolean[w + 1];
			// 第一次操作放/不放的处理
			dp[0] = true;
			if (arr[0] <= w) {
				dp[arr[0]] = true;
			}
			// 第二次及以后的操作
			for (int i = 1; i < arr.length; i++) {
				// 用一维数组时,此处存在一个问题;如果从左到右的处理数据,比如前面的值+arr[i] 修改了后面k的值
				// 而循环到dp[k]时,会影响其操作后面的元素、
				// 如果从右到左来处理数据,由于要加上arr[i] 所以只可能更新更右边的数据,不会影响前面的数据
				for (int j = w - arr[i]; j >= 0; j--) {
					if (dp[j]) {
						dp[j + arr[i]] = true;
					}
				}
				//********************************************************************************
				/*for (int j = 0; j <= w - arr[i]; j++) {
					if (dp[j]) {
						dp[j + arr[i]] = true;
					}
				}*/
			}
			for (int i = w; i >= 0; i--) {
				if (dp[i]) {
					return i;
				}
			}
			return 0;
		}

		public static void main(String[] args) {
			int[] arr = new int[]{2, 2, 4, 6, 3};
			int maxWeight = getMaxWeight_III(arr, 9);
			System.out.println(maxWeight);
		}
	}

	/**
	 * 62. 不同路径
	 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
	 * <p>
	 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
	 * <p>
	 * 问总共有多少条不同的路径？
	 * 示例 1：
	 * <p>
	 * <p>
	 * 输入：m = 3, n = 7
	 * 输出：28
	 * 示例 2：
	 * <p>
	 * 输入：m = 3, n = 2
	 * 输出：3
	 * 解释：
	 * 从左上角开始，总共有 3 条路径可以到达右下角。
	 * 1. 向右 -> 向下 -> 向下
	 * 2. 向下 -> 向下 -> 向右
	 * 3. 向下 -> 向右 -> 向下
	 * 示例 3：
	 * <p>
	 * 输入：m = 7, n = 3
	 * 输出：28
	 * 示例 4：
	 * <p>
	 * 输入：m = 3, n = 3
	 * 输出：6
	 * 提示：
	 * <p>
	 * 1 <= m, n <= 100
	 * 题目数据保证答案小于等于 2 * 109
	 */
	public static class uniquePaths {

		/**
		 * 先使用回溯,更深理解之后,在思考转移表达式,往动态规划考虑
		 *
		 * @param m
		 * @param n
		 * @return
		 */
		public static int uniquePaths(int m, int n) {


			return 0;
		}
	}
}
