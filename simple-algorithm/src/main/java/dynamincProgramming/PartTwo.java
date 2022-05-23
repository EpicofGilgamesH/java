package dynamincProgramming;

/**
 * @Description dynamic programming part two
 * @Date 2022-05-19 11:39
 * @Created by wangjie
 */
public class PartTwo {

	//case one
	//假设我们有一个 n 乘以 n 的矩阵 w[n][n]。矩阵存储的都是正整数。
	//棋子起始位置在左上角，终止位置在右下角。我们将棋子从左上角移动到右下角。每次只能向右或者向下移动一位.
	//把每条路径经过的数字加起来作为路径的长度,求最小路径.

	public static final int[][] w = new int[][]{{1, 3, 5, 9}, {2, 1, 3, 4}, {5, 2, 6, 7}, {6, 8, 4, 3}};
	public static int min = Integer.MAX_VALUE;

	//用回溯法处理该问题

	/**
	 * 回溯
	 *
	 * @param i 行
	 * @param j 列
	 * @param w 矩阵
	 * @param l 路径值
	 * @return
	 */
	public static void bt(int i, int j, int[][] w, int l) {
		int n = w.length;  //4
		if (i == n - 1 && j == n - 1) { //最后一步,到达右下角位置后  右下角w[n-1][n-1]
			if (l < min) min = l;
			return;
		}
		if (i < n - 1) {  //i 取值 [0...2]
			//往下取值
			bt(i + 1, j, w, l + w[i + 1][j]);
		}
		if (j < n - 1) {
			//往右取值
			bt(i, j + 1, w, l + w[i][j + 1]);
		}
	}

	public static int m = 4; //矩阵列数
	public static int[][] men = new int[m][m];

	/**
	 * 递归+备忘录 方式,即状态转移方程 反推
	 *
	 * @param i 行
	 * @param j 列
	 * @return 最小路径
	 */
	public static int bt_1(int i, int j) {
		if (i == 0 && j == 0) return w[0][0]; //初始值
		if (men[i][j] > 0) return men[i][j]; //备忘录,有值直接返回
		int left = Integer.MAX_VALUE, up = Integer.MAX_VALUE;
		if (i > 0) {
			//向上
			up = bt_1(i - 1, j);
		}
		if (j > 0) {
			//向左
			left = bt_1(i, j - 1);
		}
		//取其小
		int mi = w[i][j] + Math.min(up, left);
		men[i][j] = mi;
		return mi;
	}

	//动态规划思路求解

	/**
	 * 动态规划,在原数组上处理
	 *
	 * @param w 矩阵
	 * @param n 矩阵行数
	 */
	public static int bt1(int[][] w, int n) {
		//第一行/第一列处理
		for (int j = 1; j < n; j++) {
			w[0][j] += w[0][j - 1];
		}
		for (int i = 1; i < n; i++) {
			w[i][0] += w[i - 1][0];
		}
		//其他数据处理
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < n; j++) {
				int min = w[i - 1][j];
				if (w[i - 1][j] > w[i][j - 1]) {
					min = w[i][j - 1];
				}
				w[i][j] += min;
			}
		}
		//取w[n-1][n-1]
		return w[n - 1][n - 1];
	}

	//我们有 3 种不同的硬币，1 元、3 元、5 元，我们要支付 9 元，最少需要 3 个硬币(3 个 3 元的硬币)
	//可以理解爬楼梯模型,每次可以上1,3,5阶;爬到9阶的最少步数; 到第9阶有三种情况,从第8阶、第6阶、第4阶走一步
	//动态转移方程 f(9)=min(f(8)+f(6)+f(4));
	//本题可以用贪心思路,在满足条件的情况下,先用最大的面值 这样需要的硬币数肯定最少,循环次数会减少很多
	public static int climb(int l) {
		if (l == 1 || l == 3 || l == 5) return 1; //只用一步的情况
		int min = 0;
		if (l > 5) {
			min = climb(l - 5) + 1;
		} else if (l > 3) {
			min = climb(l - 3) + 1;
		} else if (l > 1) {
			min = climb(l - 1) + 1;
		}
		return min;
	}

	//以上问题不使用贪心,直接动态转移方程 O(n^2)
	public static int climb_1(int l) {
		if (l == 1 || l == 3 || l == 5) return 1;
		int a = Integer.MAX_VALUE, b = Integer.MAX_VALUE, c = Integer.MAX_VALUE;
		if (l > 5)
			a = climb_1(l - 5) + 1;
		if (l > 3)
			b = climb_1(l - 3) + 1;
		if (l > 1)
			c = climb_1(l - 1) + 1;
		return Math.min(Math.min(a, b), c);
	}

	//leetcode 全面版 思维拓展
	//给你一个整数数组coins,表示不同面额的硬币;以及一个整数 amount ,表示总金额.
	//计算并返回可以凑成总金额所需的 最少的硬币个数.如果没有任何一种硬币组合能组成总金额,返回 -1 .
	//你可以认为每种硬币的数量是无限的.

	/**
	 * 动态规划,转移表达式法
	 *
	 * @param coins  硬币数组
	 * @param amount 总金额
	 * @return 需要的硬币数
	 */
	public static int coin(int[] coins, int amount) {
		return coin_c(coins, amount, new int[amount]);
	}

	/**
	 * 递归子方法
	 *
	 * @param coins 硬币数组
	 * @param a     金额
	 * @param count 当前金额需要的数量数组,记忆搜索的 '记忆'
	 * @return 所需硬币数量
	 */
	public static int coin_c(int[] coins, int a, int[] count) {
		if (a < 0) { //则当前a金额为负数,无法组合硬币
			return -1;  //每个指定的a金额,获取的硬币组合数取最小,当没有组合时,最小为 -1
		}
		if (a == 0) { //当前a金额为0,则硬组合为0
			return 0;
		}
		if (count[a - 1] != 0)  //说明已经存在该金额的组合最小值;这里为什么是a-1?因为a金额的'记忆'数组小标为 [0...a-1]
			return count[a - 1];
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < coins.length; i++) {
			//转移表达式: f(n)=min(f(n-c1),f(n-c2)...f(n-ci));
			//金额为n的硬币组合最小值=金额为n-c1、n-c2...n-ci的硬币组合最小值
			int m = coin_c(coins, a - coins[i], count);
			if (m >= 0 && m < min)
				min = m + 1;  //+1颗硬币
		}
		min = (min == Integer.MAX_VALUE) ? -1 : min; //如果min==Integer.MAX_VALUE 说明找不到任何一个组合,直接返回-1
		count[a - 1] = min; //保存'记忆'数组
		return min;
	}


	/**
	 * 动态规划,转移数组标识法
	 *
	 * @param coins 硬币组合
	 * @param a     金额
	 * @return 组合最小硬币数
	 */
	public static int coin_dp(int[] coins, int a) {
		int[] f = new int[a + 1];
		//第一行
		f[0] = 0;
		for (int i = 1; i <= a; i++) {
			int mi = Integer.MAX_VALUE;
			for (int j = 0; j < coins.length; j++) {
				if (i - coins[j] >= 0 && f[i - coins[j]] >= 0)
					if (f[i - coins[j]] + 1 < mi)
						mi = f[i - coins[j]] + 1;
			}
			mi = (mi == Integer.MAX_VALUE) ? -1 : mi;
			f[i] = mi;
		}
		return f[a];
	}


	public static void main(String[] args) {
		//bt(0, 0, w, w[0][0]);
		//System.out.println(min);

		//int i = bt1(w, 4);
		//System.out.println(i);

		//int i1 = bt_1(3, 3);
		//System.out.println(i1);

		//int climb = climb(9);
		//System.out.println(climb);

		//int i = climb_1(9);
		//System.out.println(i);

		int[] c = {2, 5, 10, 1};
		//int coin = coin(c, 9);
		//System.out.println(coin);

		int i = coin_dp(c, 27);
		System.out.println(i);
	}

}
