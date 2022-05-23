package dynamincProgramming;

/**
 * @Description 动态规划-第一部分
 * @Date 2022-05-11 18:29
 * @Created by wangjie
 */
public class PartOne {

	//还是01背包场景,利用动态规划解决

	/**
	 * 01背包问题
	 * 该方法时间复杂度:O(n*tw) 比回溯算法中的O(n^2)要小很多
	 *
	 * @param w  物品重量
	 * @param n  物品个数
	 * @param tw 背包总承重
	 * @return
	 */
	public static int knapsack(int[] w, int n, int tw) {
		//动态规划二维数组 行:装入背包的总总量 列:放入第几个物品
		//值:是否存在该点 f(i,w) i:放入第几个物品 w:背包物品总总量
		boolean[][] f = new boolean[n][tw + 1];
		f[0][0] = true; //第一行需要特殊处理,f(0,0)肯定存在
		if (w[0] <= tw) f[0][w[0]] = true;//第一个物品重量 <= 背包承重,则f(0,w[0])=true
		int m = 0;//该行最大w值
		for (int i = 1; i < n; i++) { //第i个物品放入,从1开始:第二行开始
			int s = (m + w[i]) > tw ? tw : m + w[i];
			for (int j = 0; j <= s; j++) { //不放入的情况
				if (f[i - 1][j]) f[i][j] = true;
			}
			for (int k = 0; k <= tw - w[i]; k++) { //放入的情况
				if (f[i - 1][k]) {
					f[i][k + w[i]] = true;
					m = k + w[i]; //最大的w值在最后一次这个赋值中产生
				}
			}
		}
		for (int i = tw; i > 0; i--) { //遍历最后一行,即放入物品最多的情况,的tw最大值
			if (f[n - 1][i]) return i;
		}
		return 0;
	}

	/**
	 * 01背包问题优化,由于上中方法中,需要申请[n]行[tw+1]列数组,比较耗费空间
	 * 该多维数组存在规律 1:如果上一行为true的列下一行也为true 2.上一行为true的列,+w[i]=true
	 * 这样可以实现用一维数组就解决该问题,由于一维数组通过前面的true元素影响后面的元素取值,
	 * 所以从后往前遍历,避免该次循环改为true之后,下一循环又改动到这个值(一个for体多次操作)
	 *
	 * @param w  物品重量
	 * @param n  物品个数
	 * @param tw 背包总承重
	 * @return
	 */
	public static int knapsack1(int[] w, int n, int tw) {
		boolean[] f = new boolean[tw + 1];
		f[0] = true;
		if (w[0] <= tw) f[w[0]] = true;
		for (int i = 1; i < n; i++) {
			for (int j = tw - w[i]; j >= 0; j--) {
				if (f[j]) f[j + w[i]] = true;

			}
		}
		for (int i = tw; i > 0; i--) {
			if (f[i]) return i;
		}
		return 0;
	}

	/**
	 * 01背包,物品加上价值属性,要求在不超过背包总承重时,装入的物品价值最大
	 * 动态规划
	 *
	 * @param w  物品重量数组
	 * @param v  物品价值数组
	 * @param n  物品个数
	 * @param tw 背包承重
	 * @return 最大价值
	 */
	public static int knapsack2(int[] w, int[] v, int n, int tw) {

		int[][] f = new int[n][tw + 1];//二维数组
		for (int i = 0; i < n; ++i) {
			// 初始化states
			for (int j = 0; j < tw + 1; ++j) {
				f[i][j] = -1;
			}
		}
		f[0][0] = 0;
		if (w[0] <= tw) f[0][w[0]] = v[0]; //第一行值处理
		for (int i = 1; i < n; i++) {
			for (int j = 0; j <= tw; j++) {
				if (f[i - 1][j] >= 0) f[i][j] = f[i - 1][j]; //第i次不放入物品
			}
			for (int j = 0; j <= tw - w[i]; j++) { //放入物品,承重不能超过tw
				if (f[i - 1][j] >= 0) {
					int a = f[i - 1][j] + v[i];
					//放入物品,同样重量时取最大的v
					//在二维数组中,一行中,出现一列元素重复赋值操作,只会出现在"放入"情况下
					if (a > f[i][j + w[i]]) {
						f[i][j + w[i]] = a;
					}
				}
			}
		}
		int max = -1;
		for (int i = tw; i >= 0; i--) { //取最大值
			if (f[n - 1][i] > max) max = f[n - 1][i];
		}
		return max;
	}

	/**
	 * 01背包,物品加上价值属性,要求在不超过背包总承重时,装入的物品价值最大
	 * 动态规划 优化空间复杂度
	 *
	 * @param w  物品重量数组
	 * @param v  物品价值数组
	 * @param n  物品个数
	 * @param tw 背包承重
	 * @return 最大价值
	 */
	public static int knapsack3(int[] w, int[] v, int n, int tw) {
		int[] f = new int[tw + 1]; //简化成一维数组
		for (int i = 0; i < tw + 1; i++) { //将数组的值都初始化成-1
			f[i] = -1;
		}
		f[0] = 0;
		if (tw >= w[0]) f[w[0]] = v[0];
		for (int i = 1; i < n; i++) { //行
			for (int j = tw - w[i]; j >= 0; j--) {
				if (f[j] >= 0) {
					if (f[j] + v[i] > f[j + w[i]]) f[j + w[i]] = f[j] + v[i]; //同一列中的重复赋值操作
				}
			}
		}
		int max = -1;
		for (int i = tw; i >= 0; i--) {
			if (f[i] > max) max = f[i];
		}
		return max;
	}

	/**
	 * 购物车中有n个商品,对每个商品决策是否购买,要求找到最接近200的总价的所有商品
	 * 行:物品个数 列:物品总价值
	 *
	 * @param v  物品价值数组
	 * @param n  物品数量
	 * @param tv 总承受最大价值数量
	 * @return
	 */
	public static void shop(int[] v, int n, int tw) {
		int tv = tw * 3; //总价不能超过3倍,不然就没有意义了
		boolean[][] f = new boolean[n][tv + 1];
		f[0][0] = true;
		if (v[0] <= tv) f[0][v[0]] = true;
		for (int i = 1; i < n; i++) {
			for (int j = 0; j <= tv; j++) { //不选第i个商品
				if (f[i - 1][j]) f[i][j] = true;
			}
			for (int j = 0; j <= tv - v[i]; j++) { //选第i个商品
				if (f[i - 1][j]) f[i][j + v[i]] = true;
			}
		}
		//从最后一行(n-1)中从tw往后找第一个为true的
		int j;
		for (j = tw; j < 0; j++) {
			if (f[n - 1][j]) break;
		}
		//如果此时的j>tw*3,那么不存在可行解,所有组合都会大于tw的3倍
		if (j > tv) {
			System.out.println("没有可行解");
			return;
		}
		System.out.println("可行解为:" + j + "元");
		//回溯可行解的物品选择,从j开始往前判断,f[i][j]的上一层选择,只可能是f[i-1][j]=true和f[i-1][j-v[i]]=true
		//前者表示没有选择该商品,后者表示选择了该商品 当两者都为true时,说明两个方案均可回溯,本场景选择一种方式即可
		//如果需要回溯所有选择商品的方案,需要怎么做,递归回溯?????
		/*for (int i = n - 1; i >= 1; i--) { //i:行 j:列
			if (j - v[i] >= 0 && f[i - 1][j - v[i]]) { //选择了i商品
				System.out.println("选择了商品:" + v[i] + ",总价值为:" + j);
				j = j - v[i];
			}//未选择i商品 j不变
		}
		//通过前一行判断本行的特性,第0行需另行判断
		if (j != 0)
			System.out.println("选择了商品:" + v[0]);*/

		//使用回溯方法
		spread(f, n, j, v);

	}

	/**
	 * 需要回溯所有的商品选择情况  回溯虽然结果可以得到,但是还未达到预期的效果,应该在循环中执行,让每次都将选择做完,重让每次
	 * 找到后,进行重新选择
	 *
	 * @param f [第i次选择物品][价值总量]二维数组
	 * @param n 商品个数
	 * @param j 所选商品总价值
	 * @param v 商品价格数组
	 */
	public static void spread(boolean[][] f, int n, int j, int[] v) {
		if (j == 0) {
			System.out.println("");
			return;
		}
		if (n == 1) { //第一行商品是否选择判断
			if (j > 0) {
				System.out.println("选择了商品:" + v[0]);
				System.out.println("");
				return;
			}
		}
		//第n-1行选择的商品情况 如果f[i-1][j]=true f[i-1][j-v[i]]=true 两种情况都需要执行
		if (j - v[n - 1] >= 0 && f[n - 2][j - v[n - 1]] && f[n - 2][j]) { //两种都满足
			System.out.println("选择了商品:" + v[n - 1] + ",总价值为:" + j);
			int jj = j;
			j = j - v[n - 1];
			spread(f, n - 1, j, v); //选

			spread(f, n - 1, jj, v); //不选
		} else if (j - v[n - 1] >= 0 && f[n - 2][j - v[n - 1]]) { //满足后者
			System.out.println("选择了商品:" + v[n - 1] + ",总价值为:" + j);
			j = j - v[n - 1];
			spread(f, n - 1, j, v); //选
		} else if (f[n - 2][j]) { //满足前者
			spread(f, n - 1, j, v);
		}
		//都不满足,不可能
	}

	/**
	 * 特殊的杨辉三角,每个数字随意填写,当前数字只能到达其相邻的下一个数字
	 * 把移动到最底层所经过的所有数字之和定义为路径,求最短路径
	 * 从本次动态规划的练习题可以总结出,动态规划更注重的是思路,规划好思路,解析出每一次操作产生的结果集,加上优化思路
	 *
	 * @param y
	 * @return
	 */
	public static int yangHuiTriangle() {
		//构造杨辉三角
		int[][] y = new int[][]{{5}, {7, 8}, {2, 3, 4}, {4, 9, 6, 1}, {2, 7, 9, 4, 5}};
		int n = y.length; //行

		//int[][] f = new int[n][j]; //动态规划二维数组,行:对应杨辉三角的行,每行的数据个数=n+1 即列:n+1 值:即为计算出来的值
		//f[0][0] = y[0][0]; //第一行赋值
		//共用数组,即在杨辉三角的二维数组上进行运算
		for (int i = 1; i < n; i++) {
			for (int k = 0; k < i + 1; k++) {
				if (k == 0) { //最前和最后的元素
					y[i][0] += y[i - 1][0];
				} else if (k == i) {
					y[i][i] += y[i - 1][i - 1];
				} else {
					int v = y[i - 1][k - 1];
					if (v > y[i - 1][k]) { //比较上一行中,需要相加的本行较大的值 (3,1) =(2,0)+(3,1) || (2,1)+(3,1) 不能越界
						v = y[i - 1][k];
					}
					y[i][k] += v;
				}
			}
		}
		//在最后一行,拿到最小值
		int min = y[n - 1][0];
		for (int l = 1; l < n; l++) {
			if (y[n - 1][l] < min)
				min = y[n - 1][l];
		}
		return min;
	}

	public static void main(String[] args) {
		int[] w = {2, 2, 4, 6, 3};
		//int knapsack = knapsack(w, 5, 9);
		//System.out.println(knapsack);

		int[] v = {3, 4, 8, 9, 6};
		int k1 = knapsack2(w, v, 5, 9);
		System.out.println("maxVV:" + k1);
		int k = knapsack3(w, v, 5, 9);
		System.out.println("maxV:" + k);

		int[] vv = {10, 20, 30, 40, 50, 60, 65, 70, 75, 80, 85, 90};
		shop(vv, 12, 200);
		System.out.println("");

		int i = yangHuiTriangle();
		System.out.println("最小值:" + i);
	}
}
