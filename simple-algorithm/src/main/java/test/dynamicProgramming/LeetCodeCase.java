package test.dynamicProgramming;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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
	public static class Bag_01 {

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
	public static class UniquePaths {

		/**
		 * 先使用回溯,更深理解之后,在思考转移表达式,往动态规划考虑·
		 * 1.回溯,画出递归树
		 * 2.动态规划,推出状态转移表达式
		 *
		 * @param m
		 * @param n
		 * @return
		 */
		private static int m, n, s;

		public static int uniquePaths_backtracking(int m1, int n1) {
			m = m1;
			n = n1;
			Deque<Boolean> path = new LinkedList<>();
			int[][] use = new int[m][n];
			dfs(0, 0, path, use);
			return s;
		}

		/**
		 * 如果需要列出所有的路径信息,则需要记录path true:down false:right
		 * 防止重复遍历走的下一步,需要记录每一个格子是否已经向下和向右移动过,还是会有问题,当回溯到起始位置向右移动时
		 * 每次回到第0行时,都应该需要清空use
		 *
		 * @param r
		 * @param c
		 */
		public static void dfs(int r, int c, Deque<Boolean> path, int[][] use) {
			// 到了最后一行,只能往右走 n-c-1步到达 // 到了最后一列,只能往下走 m-r-1
			if (r == m - 1 || c == n - 1) {
				s++;
				printPath(path);
				return;
			}
			if (r == 0 && use[0][0] != 0) {  // 当回溯回到第一行时,use数组清空数据,这种做法是否合理,有更好的方法么?
				use = new int[m][n];
				use[0][0] = 3;
			}
			if (use[r][c] == 3) {   // 下右都走了,直接退出
				return;
			}
			if (r <= m && use[r][c] != 1) {  // 下走为1
				use[r][c] += 1;
				path.add(true);
				dfs(r + 1, c, path, use);
				path.removeLast();
			}
			if (c <= n && use[r][c] != 2) { // 右走为2
				use[r][c] += 2;
				path.add(false);
				dfs(r, c + 1, path, use);
				path.removeLast();
			}
		}

		private static void printPath(Deque<Boolean> path) {
			StringBuilder sb = new StringBuilder();
			path.forEach(x -> {
				String step = x ? "↓" : "→";
				sb.append(step).append(" ");
			});
			if (path.size() < m + n - 2) {
				String step1 = !path.getLast() ? "↓" : "→";
				for (int i = 0; i < m + n - 2 - path.size(); i++) {
					sb.append(step1).append(" ");
				}
			}
			System.out.println(sb);
		}

		/**
		 * 动态规划,状态转移表达式
		 * f(m,n)表示第m行第n列为终点的走法数量
		 * 可得 f(m,n)=f(m-1,n)+f(m,n-1)  其中f(m,n)有m=0或n=0时 f(m,n)=1;
		 *
		 * @param m1
		 * @param n1
		 * @return
		 */
		public static int uniquePaths_dynamicProgramming(int m1, int n1) {
			int[][] dp = new int[m1][n1];
			for (int i = 0; i < n1; i++) {
				dp[0][i] = 1;
			}
			for (int i = 0; i < m1; i++) {
				dp[i][0] = 1;
			}
			for (int r = 1; r < m1; r++) {
				for (int c = 1; c < n1; c++) {
					dp[r][c] = dp[r - 1][c] + dp[r][c - 1];
				}
			}
			return dp[m1 - 1][n1 - 1];
		}

		/**
		 * dp二维数组采用滚动数组优化到一维数组
		 *
		 * @param m1
		 * @param n1
		 * @return
		 */
		public static int uniquePaths_dynamicProgramming_I(int m1, int n1) {
			int[] dp = new int[n1];
			for (int i = 0; i < n1; i++) {
				dp[i] = 1;
			}
			for (int r = 1; r < m1; r++) {
				for (int c = 1; c < n1; c++) {
					dp[c] = dp[c - 1] + dp[c];
				}
			}
			return dp[n1 - 1];
		}

		public static void main(String[] args) {
			// int i = uniquePaths_backtracking(3, 7);
			int i = uniquePaths_dynamicProgramming_I(3, 7);
			System.out.println("路径条数:" + i);
		}
	}

	/**
	 * 63. 不同路径 II
	 * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
	 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。
	 * 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
	 * 网格中的障碍物和空位置分别用 1 和 0 来表示。
	 * 示例 1：
	 * <p>
	 * 输入：obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
	 * 输出：2
	 * 解释：3x3 网格的正中间有一个障碍物。
	 * 从左上角到右下角一共有 2 条不同的路径：
	 * 1. 向右 -> 向右 -> 向下 -> 向下
	 * 2. 向下 -> 向下 -> 向右 -> 向右
	 * 示例 2：
	 * 输入：obstacleGrid = [[0,1],[0,0]]
	 * 输出：1
	 * 提示：
	 * m == obstacleGrid.length
	 * n == obstacleGrid[i].length
	 * 1 <= m, n <= 100
	 * obstacleGrid[i][j] 为 0 或 1
	 */
	public static class UniquePathsWithObstacles {

		/**
		 * 个人思路:
		 * 动态规划思路,状态转移表达式:f(m,n)=f(m-1,n)+f(m,n-1),当m|n=0时,有f(m,0)=1,f(0,n)=1;
		 * 出现障碍物,则那一边不需要计入步数 f(m,n)= isZero(f(m,n-1))+isZero(f(m-1,n));
		 * <p>
		 * 原数组左右dp数组,为什么内存消耗还很高呢??
		 *
		 * @param obstacleGrid
		 * @return
		 */
		public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
			if (obstacleGrid[0][0] == 1) return 0;
			obstacleGrid[0][0] = 1;
			// 第一行处理
			for (int i = 1; i < obstacleGrid[0].length; i++) {
				if (obstacleGrid[0][i] == 0) {  // 当前网格没有障碍物,则当前网格步数=前一个网格步数
					obstacleGrid[0][i] = obstacleGrid[0][i - 1];
				} else {  // 当前网格有障碍物,则当前网格的步数=0
					obstacleGrid[0][i] = 0;
				}
			}
			// 第一列处理
			for (int i = 1; i < obstacleGrid.length; i++) {
				if (obstacleGrid[i][0] == 0) {  // 同行的处理
					obstacleGrid[i][0] = obstacleGrid[i - 1][0];
				} else {
					obstacleGrid[i][0] = 0;
				}
			}
			// 从前网格处理
			for (int i = 1; i < obstacleGrid.length; i++) {
				for (int j = 1; j < obstacleGrid[0].length; j++) {
					if (obstacleGrid[i][j] == 0) {
						obstacleGrid[i][j] = obstacleGrid[i - 1][j] + obstacleGrid[i][j - 1];
					} else {
						obstacleGrid[i][j] = 0;
					}
				}
			}
			// 忽略了只有一行或一列的情况,不能直接用length-1
			return obstacleGrid[Math.max(obstacleGrid.length - 1, 0)][Math.max(obstacleGrid[0].length - 1, 0)];
		}

		/**
		 * 滚动数组优化dp数组空间占用,使用滚动数组时,不用考虑第一行第一列的初始化,一行行覆盖即可
		 *
		 * @param obstacleGrid
		 * @return
		 */
		public static int uniquePathsWithObstacles_I(int[][] obstacleGrid) {
			int r = obstacleGrid.length, c = obstacleGrid[0].length;
			int[] dp = new int[c];
			dp[0] = obstacleGrid[0][0] == 0 ? 1 : 0;
			for (int i = 0; i < r; i++) {
				for (int j = 0; j < c; j++) {
					if (obstacleGrid[i][j] == 1) { // 如果当前[i][j]是障碍物,则后续dp数据都不用变,且当前dp=0;
						dp[j] = 0;
					} else if (j - 1 >= 0) {  // 如果当前[i][j]不是障碍物,则通过状态转移表达式进行计算;考虑j-1超出界限
						dp[j] += dp[j - 1];
					}
				}
			}
			return dp[Math.max(c - 1, 0)];
		}

		public static void main(String[] args) {
			// int[][] obstacleGrid = new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
			// int[][] obstacleGrid = new int[][]{{0, 0}};
			int[][] obstacleGrid = new int[][]{{0, 0}, {1, 1}, {0, 0}};
			int i = uniquePathsWithObstacles_I(obstacleGrid);
			System.out.println(i);
		}
	}

	/**
	 * 64. 最小路径和
	 * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
	 * 说明：一个机器人每次只能向下或者向右移动一步。
	 * 输入：grid = [[1,3,1],[1,5,1],[4,2,1]]
	 * 输出：7
	 * 解释：因为路径 1→3→1→1→1 的总和最小。
	 * 示例 2：
	 * <p>
	 * 输入：grid = [[1,2,3],[4,5,6]]
	 * 输出：12
	 * 提示：
	 * m == grid.length
	 * n == grid[i].length
	 * 1 <= m, n <= 200
	 * 0 <= grid[i][j] <= 100
	 */
	public static class MinPathSum {

		/**
		 * 个人思路:
		 * 动态规划思路,状态转移表达式 f(m,n)=Min(f(m-1,n),f(m,n-1))+V(m,n)
		 * <p>
		 * 还是需要单独处理第一列和第一行的数据,思路会清晰很多
		 * 更节省内存的做法是将原二位数组作为dp数组来使用
		 *
		 * @param grid
		 * @return
		 */
		public static int minPathSum(int[][] grid) {
			int m = grid.length, n = grid[0].length;
			int[] dp = new int[n];
			int pre = 0;
			for (int j = 0; j < n; j++) {
				if (j > 0)
					pre = dp[j - 1];
				dp[j] = pre + grid[0][j];
			}
			for (int i = 1; i < m; i++) {
				for (int j = 0; j < n; j++) {
					if (j == 0)
						dp[j] = dp[0] + grid[i][0];
					else
						dp[j] = Math.min(dp[j - 1], dp[j]) + grid[i][j];
				}
			}
			return dp[n - 1];
		}

		public static void main(String[] args) {
			// int[][] grid = new int[][]{{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
			int[][] grid = new int[][]{{1, 2, 3}, {4, 5, 6}};
			int i = minPathSum(grid);
			System.out.println(i);

			long a = -0, b = -100;
			String a1 = "-0", b1 = String.valueOf(b);
			System.out.println("a:" + a1 + "b:" + b1);
			BigDecimal amount = new BigDecimal("0.01");
			amount = amount.setScale(2, RoundingMode.UP);
			System.out.println(amount.stripTrailingZeros().toPlainString());

			String amount1 = new BigDecimal("100").setScale(2, RoundingMode.UP).stripTrailingZeros().toPlainString();
			System.out.println(amount1);
		}
	}

	/**
	 * LCR 100. 三角形最小路径和
	 * 给定一个三角形 triangle ，找出自顶向下的最小路径和。
	 * <p>
	 * 每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于
	 * 上一层结点下标 + 1 的两个结点。也就是说，如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
	 * 示例 1：
	 * 输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
	 * 输出：11
	 * 解释：如下面简图所示：
	 * 2
	 * 3 4
	 * 6 5 7
	 * 4 1 8 3
	 * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
	 * 示例 2：
	 * <p>
	 * 输入：triangle = [[-10]]
	 * 输出：-10
	 * <p>
	 * 提示：
	 * 1 <= triangle.length <= 200
	 * triangle[0].length == 1
	 * triangle[i].length == triangle[i - 1].length + 1
	 * -104 <= triangle[i][j] <= 104
	 * 进阶：
	 * 你可以只使用 O(n) 的额外空间（n 为三角形的总行数）来解决这个问题吗？
	 */
	public static class MinimumTotal {

		/**
		 * 个人思路：
		 * 实际上这个三角形需要从上往下一层层计算,直到最后一层,然后找出最后一层最小的数
		 * 当下一层元素是第一个或者最后一个时,则累加值只能加上一层;
		 * 当下一层元素是中间时,则累加上一层中i-1 或i ,取得到值的小者 f(i)=min((Si+f(i)),(Si+f(i-1)))
		 * <p>
		 * 先尝试在新的三角形上处理,后面进阶在原三角形上处理,原三角形上不能进行dp数据的更新,
		 * 因为第r层的dp数据,需要根据r-1层去更新.所以dp数据要保存r-1层的,之前的不需要保存
		 *
		 * @param triangle
		 * @return
		 */
		public static int minimumTotal(List<List<Integer>> triangle) {
			List<List<Integer>> sum = new ArrayList<>();
			List<Integer> row_0 = new ArrayList<>();
			row_0.add(triangle.get(0).get(0));
			sum.add(row_0);
			for (int i = 1; i < triangle.size(); i++) {
				int size = triangle.get(i).size();
				List<Integer> row_i = new ArrayList<>();
				for (int j = 0; j < size; j++) {
					int s;
					if (j == 0) {
						s = triangle.get(i).get(j) + sum.get(i - 1).get(j);
					} else if (j == size - 1) {
						s = triangle.get(i).get(j) + sum.get(i - 1).get(j - 1);
					} else {
						s = Math.min(triangle.get(i).get(j) + sum.get(i - 1).get(j), triangle.get(i).get(j) + sum.get(i - 1).get(j - 1));
					}
					row_i.add(s);
				}
				sum.add(row_i);
			}
			List<Integer> row_last = sum.get(sum.size() - 1);
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < row_last.size(); i++) {
				min = Math.min(min, row_last.get(i));
			}
			return min;
		}

		/**
		 * 官方思路
		 * 同样的思路,dp数组的方式,减少循环,更清晰的编码
		 *
		 * @param triangle
		 * @return
		 */
		public static int minimumTotal_OfficialI(List<List<Integer>> triangle) {
			int n = triangle.size();
			int[][] dp = new int[n][n];
			dp[0][0] = triangle.get(0).get(0);
			for (int r = 1; r < n; r++) {
				// 在处理列时,column=0 和 column= length-1 特殊处理
				// 第r行,拥有r+1个元素
				dp[r][0] = dp[r - 1][0] + triangle.get(r).get(0);
				int c = 1;
				for (; c < r; c++) {
					dp[r][c] = Math.min(dp[r - 1][c], dp[r - 1][c - 1]) + triangle.get(r).get(c);
				}
				dp[r][c] = dp[r - 1][r - 1] + triangle.get(r).get(c);
			}
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < dp[n - 1].length; i++) {
				min = Math.min(min, dp[n - 1][i]);
			}
			return min;
		}

		/**
		 * 因为第r层的dp数据,需要根据r-1层去更新.所以dp数据要保存r-1层的,之前的不需要保存
		 * 实际上,r层i位置的dp数据,只依赖r-1层中i-1和i位置的dp数据.所以通过逆向计算的方式,滚动数组覆盖值
		 * 首先,如何使用两个数组来实现呢?
		 * r层依赖r-1层的dp数据,r+1层依赖r层的dp数据,那两个数组需要不停的交换覆盖,所以出现的奇偶数组指定的方式.
		 *
		 * @param triangle
		 * @return
		 */
		public static int minimumTotal_I(List<List<Integer>> triangle) {
			int n = triangle.size();
			int[] odd = new int[n];  // 奇数层dp
			int[] even = new int[n]; // 偶数层dp
			even[0] = triangle.get(0).get(0);
			for (int r = 1; r < n; r++) {
				// 通过当前行数组和上一行数组进行交替写入dp数据
				int[] pre = r % 2 == 1 ? even : odd;
				int[] cur = r % 2 == 1 ? odd : even;
				cur[0] = pre[0] + triangle.get(r).get(0);
				int c = 1;
				for (; c < r; c++) {
					cur[c] = Math.min(pre[c], pre[c - 1]) + triangle.get(r).get(c);
				}
				cur[c] = pre[c - 1] + triangle.get(r).get(c);
			}
			int min = Integer.MAX_VALUE;
			int[] last = n % 2 == 1 ? even : odd;
			for (int i = 0; i < last.length; i++) {
				min = Math.min(min, last[i]);
			}
			return min;
		}

		/**
		 * 原二维数组
		 * 2
		 * 3 4
		 * 6 5 7
		 * 4 1 8 3
		 * <p>
		 * 滚动数组
		 * 2   *   *   *
		 * 5   6   *   *
		 * 11  10  13
		 * 15  11  18  16
		 * 反向写入数组中,当写完i数据时,原i-1数据已经不再需要
		 *
		 * @param triangle
		 * @return
		 */
		public static int minimumTotal_II(List<List<Integer>> triangle) {
			int n = triangle.size();
			int[] dp = new int[n];
			dp[0] = triangle.get(0).get(0);
			for (int r = 1; r < n; r++) {
				dp[r] = dp[r - 1] + triangle.get(r).get(r);
				for (int c = r - 1; c >= 1; c--) {
					dp[c] = Math.min(dp[c], dp[c - 1]) + triangle.get(r).get(c);
				}
				dp[0] += triangle.get(r).get(0);
			}
			int min = Integer.MAX_VALUE;
			for (int i = 0; i < dp.length; i++) {
				min = Math.min(min, dp[i]);
			}
			return min;
		}

		public static void main(String[] args) {
			List<List<Integer>> triangle = new ArrayList<>();
			List<Integer> row_0 = new ArrayList<>();
			row_0.add(2);
			triangle.add(row_0);
			List<Integer> row_1 = new ArrayList<>();
			row_1.add(3);
			row_1.add(4);
			triangle.add(row_1);
			List<Integer> row_2 = new ArrayList<>();
			row_2.add(6);
			row_2.add(5);
			row_2.add(7);
			triangle.add(row_2);
			List<Integer> row_3 = new ArrayList<>();
			row_3.add(4);
			row_3.add(1);
			row_3.add(8);
			row_3.add(3);
			triangle.add(row_3);
			// int i = minimumTotal(triangle);
			// int i = minimumTotal_I(triangle);
			// int i = minimumTotal_OfficialI(triangle);
			int i = minimumTotal_II(triangle);
			System.out.println(i);
		}
	}

	/**
	 * 1480. 一维数组的动态和
	 * 给你一个数组 nums 。数组「动态和」的计算公式为：runningSum[i] = sum(nums[0]…nums[i]) 。
	 * <p>
	 * 请返回 nums 的动态和。
	 * 示例 1：
	 * <p>
	 * 输入：nums = [1,2,3,4]
	 * 输出：[1,3,6,10]
	 * 解释：动态和计算过程为 [1, 1+2, 1+2+3, 1+2+3+4] 。
	 * 示例 2：
	 * <p>
	 * 输入：nums = [1,1,1,1,1]
	 * 输出：[1,2,3,4,5]
	 * 解释：动态和计算过程为 [1, 1+1, 1+1+1, 1+1+1+1, 1+1+1+1+1] 。
	 * 示例 3：
	 * <p>
	 * 输入：nums = [3,1,2,10,1]
	 * 输出：[3,4,6,16,17]
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= nums.length <= 1000
	 * -10^6 <= nums[i] <= 10^6
	 */
	public static class RunningSum {

		/**
		 * 个人思路:
		 * 简单的状态转移,f(n)代表在n位置的值,则有f(n)=f(n-1)+f(n-2);
		 *
		 * @param nums
		 * @return
		 */
		public static int[] runningSum(int[] nums) {
			for (int i = 1; i < nums.length; i++) {
				nums[i] += nums[i - 1];
			}
			return nums;
		}

		public static void main(String[] args) {
			// int[] nums = new int[]{1, 2, 3, 4};
			int[] nums = new int[]{1, 1, 1, 1, 1};
			System.out.println(Arrays.toString(runningSum(nums)));
		}
	}

	/**
	 * 121. 买卖股票的最佳时机
	 * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
	 * <p>
	 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
	 * <p>
	 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
	 * 示例 1：
	 * <p>
	 * 输入：[7,1,5,3,6,4]
	 * 输出：5
	 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
	 * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
	 * 示例 2：
	 * <p>
	 * 输入：prices = [7,6,4,3,1]
	 * 输出：0
	 * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
	 * 提示：
	 * <p>
	 * 1 <= prices.length <= 105
	 * 0 <= prices[i] <= 104
	 */
	public static class MaxProfit {

		/**
		 * 个人思路:
		 * 首先,股票的购买特性是先买入后卖出,从左到右有序
		 * 每天都能算出来当天能获取到的最高收入,当不会有收益的时候为0;后一天的最够收益是前一天最高收益和前一天的卖出价格决定.
		 * 此处有一个疑问,当前天的最大收益,为什么直接决定后一天的最大收益呢?
		 * 当然是可以的,后一天的最大收益只取决于前一天的卖出价格和前一天的最大收益指
		 *
		 * @param prices
		 * @return
		 */

		public static int maxProfit(int[] prices) {
			int[] dp = new int[prices.length];
			dp[0] = 0;
			for (int i = 1; i < prices.length; i++) {
				int v = prices[i] - prices[i - 1] + dp[i - 1];
				if (v < 0) v = 0;
				dp[i] = v;
			}
			int max = 0;
			for (int i = 0; i < dp.length; i++) {
				max = Math.max(dp[i], max);
			}
			return max;
		}

		/**
		 * 从官方的解题思路来看,这个题的思路非常简单明确
		 * 将每天的股票价格绘制成曲线图,那就是需要找左边最低点和右边最高点的差值
		 * 如果当天的价格 < 买入的价格,那么就重新买入;重新买入的一定的最低的价格
		 * 如果当天的价格 > 买入的价格,那么就计算利润,并获取最高的利润值
		 *
		 * @param prices
		 * @return
		 */
		public static int maxProfitOfficial(int[] prices) {
			if (prices == null || prices.length == 0) {
				return -1;
			}
			int buy = Integer.MAX_VALUE, max = 0;
			for (int i = 0; i < prices.length; i++) {
				if (prices[i] < buy) {
					buy = prices[i];
				} else {
					max = Math.max(max, prices[i] - buy);
				}
			}
			return max;
		}

		public static void main(String[] args) {
			int[] prices = new int[]{7, 1, 5, 3, 6, 4};
			// int[] prices = new int[]{7, 6, 4, 3, 2, 1};
			System.out.println(maxProfit(prices));
			System.out.println(maxProfitOfficial(prices));
		}
	}

	/**
	 * 198. 打家劫舍
	 * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
	 * 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
	 * <p>
	 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：[1,2,3,1]
	 * 输出：4
	 * 解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
	 * 偷窃到的最高金额 = 1 + 3 = 4 。
	 * 示例 2：
	 * <p>
	 * 输入：[2,7,9,3,1]
	 * 输出：12
	 * 解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
	 * 偷窃到的最高金额 = 2 + 9 + 1 = 12 。
	 * 提示：
	 * <p>
	 * 1 <= nums.length <= 100
	 * 0 <= nums[i] <= 400
	 */
	public static class Rob {

		/**
		 * 个人思路:
		 * 小偷在选择房子时,每一间房子都可以选和不选 两种选择.
		 * 而此处需要构造状态转移表达式,往这个方向去思考.第i个房间的最大值f(i) 取决于什么呢?
		 * f(i)可能是选中了i;也可能是选中了i-1,也就是说i可能可以被选择,也可能不被选择,两种情况.
		 * 当然可以选中i的时候,也可以不选,忽略掉他
		 * 1.i不能被选中了,那么f(i)=f(i-1)
		 * 2.i可以被选中,选的情况 f(i)=f(i-2)+s(i); 不选的情况 f(i)=f(i-2) 在这两种情况下,显然一定要选
		 * 那么状态转移表达式 f(i)=max(f(i-1),(f(i-2)+s(i))) 其中f(0)=s(0) f(1)=max(s(0),s(1))
		 * <p>
		 * 跟官方思路一致,但有两个优化点
		 * 1.dp数组的最后一个值肯定最大
		 * 2.dp数组中只有dp[i-1] 和dp[i-2] 来决定dp[i]的值,一旦dp[i]的值写入成功,那么dp[i-2]的值就不在需要了
		 * 所以可以采用滚动数组思路
		 *
		 * @param nums
		 * @return
		 */
		public static int rob(int[] nums) {
			int[] dp = new int[nums.length];
			if (nums == null) return 0;
			if (nums.length == 1) return nums[0];
			dp[0] = nums[0];
			dp[1] = Math.max(nums[0], nums[1]);
			for (int i = 2; i < nums.length; i++) {
				dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
			}
			return dp[dp.length - 1];
		}

		public static int robOfficial(int[] nums) {
			if (nums == null) return 0;
			if (nums.length == 1) return nums[0];
			int prep = nums[0], pre = Math.max(nums[0], nums[1]);
			for (int i = 2; i < nums.length; i++) {
				int temp = pre;
				pre = Math.max(prep + nums[i], pre);
				prep = temp;
			}
			return pre;
		}

		public static void main(String[] args) {
			int[] nums = new int[]{2, 7, 9, 3, 1};
			// System.out.println(rob(nums));
			System.out.println(robOfficial(nums));
		}
	}

	/**
	 * 213. 打家劫舍 II
	 * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，
	 * 这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
	 * <p>
	 * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
	 * 示例 1：
	 * <p>
	 * 输入：nums = [2,3,2]
	 * 输出：3
	 * 解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
	 * 示例 2：
	 * <p>
	 * 输入：nums = [1,2,3,1]
	 * 输出：4
	 * 解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
	 * 偷窃到的最高金额 = 1 + 3 = 4 。
	 * 示例 3：
	 * <p>
	 * 输入：nums = [1,2,3]
	 * 输出：3
	 * 提示：
	 * 1 <= nums.length <= 100
	 * 0 <= nums[i] <= 1000
	 */
	public static class RobII {

		/**
		 * 个人思路:
		 * 在上一题的基础上,增加了一个条件.最后一个房间和第一个房间是不能同时选择的.
		 * 首先按常规思路,最后一个房间需要特殊处理,在此之前需要判断第一个房间是否有选中 ************思路不对,并不能通过最后一个房间的最大值减去第一个房间
		 * 如果i=0没有选中,则dp[i]还是需要的值,如果i=0选中,则dp[i]需要减掉s(i) 然后再和dp[i-1]中选出最大的
		 * 那么如何判断i=0是否有选中呢?
		 * f(i)=max(f(i-1),(f(i-2)+s(i))) 其中f(0)=s(0) f(1)=max(s(0),s(1))
		 * 通过状态转移表达式 可以看出f(i) 只取决于f(i-1) 和 f(i-2)
		 * 那么在只算f(2)时,就能判断i=0是否有被选中 ********** 错误,并不能判断i=0是否选中,只有到最后一个房间时才能判断******
		 * 可以通过一个标记,f(i)都会记录有没有用到f(0)
		 * <p>
		 * *******整体思路应该是错误的*******
		 * 如果从i=0个房间开始,那最后一个房间不能选;
		 * 如果从i=1个房间开始,那最后一个房间可以选;
		 * 则比较两种场景的最大
		 *
		 * @param nums
		 * @return
		 */
		public static int rob(int[] nums) {
			if (nums == null) return 0;
			if (nums.length == 1) return nums[0];
			if (nums.length == 2) return Math.max(nums[0], nums[1]);
			int prep = nums[0], pre = Math.max(nums[0], nums[1]);
			boolean prepb = true, preb = nums[0] > nums[1];
			for (int i = 2; i < nums.length - 1; i++) {
				boolean tempb = preb;
				int temp = pre;
				if (prep + nums[i] > pre) {
					pre = prep + nums[i];
					preb = prepb;
				}
				prep = temp;
				prepb = tempb;
			}
			if (prep + nums[nums.length - 1] < pre) {
				preb = false;
			}
			int prep1 = nums[1], pre1 = Math.max(nums[1], nums[2]);
			for (int i = 3; i < nums.length; i++) {
				int temp1 = pre1;
				if (prep1 + nums[i] > pre1) {
					pre1 = prep1 + nums[i];
				}
				prep1 = temp1;
			}
			return preb ? pre : Math.max(pre1, pre);
		}

		/**
		 * 一开始去判断是否使用了第一个元素的思路就是错误的,需要重新来思考,很容易找到科学的办法
		 *
		 * @param nums
		 * @return
		 */
		public static int robI(int[] nums) {
			if (nums == null) return 0;
			if (nums.length == 1) return nums[0];
			if (nums.length == 2) return Math.max(nums[0], nums[1]);
			int prep = nums[0], pre = Math.max(nums[0], nums[1]);
			for (int i = 2; i < nums.length - 1; i++) {
				int temp = pre;
				pre = Math.max(prep + nums[i], pre);
				prep = temp;
			}
			int prep1 = nums[1], pre1 = Math.max(nums[1], nums[2]);
			for (int i = 3; i < nums.length; i++) {
				int temp1 = pre1;
				pre1 = Math.max(prep1 + nums[i], pre1);
				prep1 = temp1;
			}
			return Math.max(pre, pre1);
		}

		public static void main(String[] args) {
			int[] nums = new int[]{1, 1, 1, 2};
			// System.out.println(rob(nums));
			System.out.println(robI(nums));
		}
	}

	/**
	 * 264. 丑数 II
	 * 给你一个整数 n ，请你找出并返回第 n 个 丑数 。
	 * <p>
	 * 丑数 就是质因子只包含 2、3 和 5 的正整数。
	 * 示例 1：
	 * <p>
	 * 输入：n = 10
	 * 输出：12
	 * 解释：[1, 2, 3, 4, 5, 6, 8, 9, 10, 12] 是由前 10 个丑数组成的序列。
	 * 示例 2：
	 * <p>
	 * 输入：n = 1
	 * 输出：1
	 * 解释：1 通常被视为丑数。
	 * 提示：
	 * 1 <= n <= 1690
	 */
	public static class NthUglyNumber {

		/**
		 * 个人思路:
		 * 求只有2,3,5为质因子时,会出现太多的重复数字 需要求助官方思路
		 * 首先求丑数f(n) 则f(n)要等于 f(1...n-1)的丑数 *2 | *3 | *5 才能是丑数  n表示第几个丑数
		 * 那得出的状态转移表达式: f(n) = f(1...n-1)*2 | f(1...n-1)*3 | f(1...n-1)*5 那么f(n)为丑数 同时计数当前数字的序列
		 * <p>
		 * 通过3个数组分别存放f(1...n-1)的2,3,5倍的数字,那么其中肯定会有很多重复的数组(后面想办法整合成一个数组)
		 * 1,2,3,4,5,6,8,9,10,12
		 * 1,2,4,6,8,10,12,16,18,20  ------f(i)*2
		 * 1,2,6,9,12,15,18,24,27    ------f(i)*3
		 * 1,5,10,15,20,25,30,40     ------f(i)*4
		 * <p>
		 * 初始化三个指针p2,p3,p5分别指向对应数组的第一个元素,此时1为丑数,指针全部向后移动一位,并计算出指针位置的值
		 * 到2数字,判断其是否为丑数,则先查看指针上是否有等于2的值,如果相当则该指针向后移动一位
		 * 到3数字,重复以上操作
		 * 正确性证明:如何证明每次得到的丑数都是按顺序获取的呢?首先3个指针只有相等才会后移一位,没有后移的指针值肯定小于当前丑数 px < f(i)
		 * 一旦出现等于丑数的值,则肯定是下一个丑数
		 * <p>
		 * 直接超时了***************************
		 * 确实是可以实现的,那么如何优化?官方思路是怎么样的,如何想到的呢?
		 * <p>
		 * 当前方案的问题:
		 * 1.3个指针数组重复存储了丑数
		 * 2.3个指针数组中本身就存储了丑数,不用单独用一个dp数组去记录所有丑数,然后通过dp数组去计算指针值;因为三个指针数组已经有当前
		 * 位置的丑数了.而且指针指向的丑数乘以指定的因子,可以实现上一种思路
		 *
		 * @param n
		 * @return
		 */
		public static int nthUglyNumber(int n) {
			int[] dp = new int[n + 1];
			int[] dp2 = new int[n + 1];
			int[] dp3 = new int[n + 1];
			int[] dp5 = new int[n + 1];
			dp[0] = 1;
			dp2[0] = 1;
			dp3[0] = 1;
			dp5[0] = 1;
			int p2 = 0, p3 = 0, p5 = 0, k = 0, i = 1;
			do {
				boolean flag = false;
				if (i == dp2[p2]) {
					dp[k++] = i;
					flag = true;
					p2++;
					dp2[p2] = dp[p2 - 1] * 2;
				}
				if (i == dp3[p3]) {
					if (!flag) {
						dp[k++] = i;
						flag = true;
					}
					p3++;
					dp3[p3] = dp[p3 - 1] * 3;
				}
				if (i == dp5[p5]) {
					if (!flag) {
						dp[k++] = i;
					}
					p5++;
					dp5[p5] = dp[p5 - 1] * 5;
				}
				i++;
			} while (k != n);
			return dp[k - 1];
		}

		/**
		 * 通过查看官方思路,发现状态转移的思路并不是最优
		 * f(n) = f(1...n-1)*2 | f(1...n-1)*3 | f(1...n-1)*5
		 * 这样的话,每次都需要重复的在1...n-1的丑数中去寻找下一个丑数
		 * 如果用3指针的方式,指针指向的上一次丑数的2、3、5倍数的丑数,且肯定没有被记录过;那么肯定能保证顺序
		 *
		 * dp数组中的值: 1,2,3,4,5,6,8,9,10  index就代表第几个丑数
		 * p2,p3,p5 指针初始都指向1,当然第一个丑数也是1,此时第二个丑数肯定是上一个丑数1 *2 、*3 、*5 中最小的一个
		 * 可以知道第二个丑数是1*2,那么p2指针后移一位,他的下一个丑数只能是2*2
		 * 能得到状态转移方程 f(n)=min(f(p2)*2,f(p3)*3,f(p5)*5)
		 * @param n
		 * @return
		 */
		public static int nthUglyNumberI(int n) {
			int[] dp = new int[n + 1];
			dp[1] = 1;
			int p2 = 1, p3 = 1, p5 = 1;
			for (int i = 2; i <= n; i++) {
				int num2 = dp[p2] * 2, num3 = dp[p3] * 3, num5 = dp[p5] * 5;
				dp[i] = Math.min(Math.min(num2, num3), num5);
				if (dp[i] == num2) {
					p2++;
				}
				if (dp[i] == num3) {
					p3++;
				}
				if (dp[i] == num5) {
					p5++;
				}
			}
			return dp[n];
		}

		public static void main(String[] args) {
			System.out.println(nthUglyNumberI(15));
		}
	}

	/**
	 * 139. 单词拆分
	 * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。
	 * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
	 * 示例 1：
	 * 输入: s = "leetcode", wordDict = ["leet", "code"]
	 * 输出: true
	 * 解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。
	 * 示例 2：
	 * 输入: s = "applepenapple", wordDict = ["apple", "pen"]
	 * 输出: true
	 * 解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
	 * 注意，你可以重复使用字典中的单词。
	 * 示例 3：
	 * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
	 * 输出: false
	 * 提示：
	 * 1 <= s.length <= 300
	 * 1 <= wordDict.length <= 1000
	 * 1 <= wordDict[i].length <= 20
	 * s 和 wordDict[i] 仅由小写英文字母组成
	 * wordDict 中的所有字符串 互不相同
	 */
	public static class WordBreak {

		/**
		 * 首先,这是个深度优先遍历的问题
		 *
		 * @param s
		 * @param wordDict
		 * @return
		 */
		public static boolean wordBreak(String s, List<String> wordDict) {
			boolean[] visited = new boolean[s.length() + 1];
			return dfs(s, wordDict, 0, visited);
		}

		private static boolean dfs(String s, List<String> wordDict, int start, boolean[] visited) {
			for (String word : wordDict) {
				int next = start + word.length();
				if (next > s.length() || visited[next]) {
					continue;
				}
				if (s.startsWith(word, start)) {
					if (next == s.length() || dfs(s, wordDict, next, visited)) {
						return true;
					}
					visited[next] = true;
				}
			}
			return false;
		}

		/**
		 * dp
		 *
		 * @param s
		 * @param wordDict
		 * @return
		 */
		public static boolean wordBreakDp(String s, List<String> wordDict) {
			int max = 0;
			Set<String> set = new HashSet<>(wordDict.size());
			for (String word : wordDict) {
				set.add(word);
				max = Math.max(word.length(), max);
			}
			boolean[] dp = new boolean[s.length() + 1];
			dp[0] = true;
			for (int i = 1; i < s.length(); i++) {
				for (int j = Math.max(i - max, 0); j < i; j++) {
					if (dp[j] && set.contains(s.substring(j, i))) {
						dp[i] = true;
						break;
					}
				}
			}
			return dp[dp.length - 1];
		}

		public static void main(String[] args) {
			System.out.println(wordBreak("leetcode", Arrays.asList("leet", "code")));
		}
	}

}

