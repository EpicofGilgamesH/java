package test.recursion;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description LeeCode 案例
 * @Date 2022-08-11 14:44
 * @Created by wangjie
 */
public class LeeCodeCase {

	//70. 爬楼梯
	//假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
	//每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？

	/**
	 * 递归思路,直接超时
	 *
	 * @param n
	 * @return
	 */
	public static int climbStairs(int n) {

		//思路:第一步 有两种场景 爬1阶 或者 爬2阶 总爬法= 爬1阶的所有爬法+爬2阶的所有爬法
		//递推公式: f(n)=f(n-1)+f(n-2)
		//递归出口: 爬到最后,肯定还剩下两种情况
		// 1.最后还剩一阶楼梯,爬1阶完成 f(1)=1; 2.最后还剩下两阶楼梯,两种爬法 f(2)=2;

		if (n == 1) return 1;
		if (n == 2) return 2;
		return climbStairs(n - 1) + climbStairs(n - 2);
	}


	public static int climbStairs1(int n) {
		Map<Integer, Integer> map = new HashMap<>();
		map.put(1, 1);
		map.put(2, 2);
		return climbStairs2(n, map);
	}

	/**
	 * 递归剪枝操作,否则递归树超级大
	 *
	 * @param n
	 * @return
	 */
	public static int climbStairs2(int n, Map<Integer, Integer> map) {
		if (n == 1) return 1;
		if (n == 2) return 2;
		Integer a1 = map.get(n - 1);
		Integer a2 = map.get(n - 2);
		int prev = a1 == null ? climbStairs2(n - 1, map) : a1;
		int prevP = a2 == null ? climbStairs2(n - 2, map) : a2;
		int step = prev + prevP;
		map.put(n, step);
		return step;
	}

	/**
	 * 使用动态规划
	 * 思路:根据上述分析, f(n)=f(n-1)+f(n-2) 即,n个阶梯的最后一步有两种情况,要么1阶梯,要么2阶梯
	 * 那么总步数=前面n-1个阶梯的步数+前面n-2个阶梯的步数
	 * 根据动态规划的思路,转移表达式: f(n)=f(n-1)+f(n-2)
	 *
	 * @param n
	 * @return
	 */
	public static int climbStairs_dp(int n) {
		if (n == 1) return 1;
		if (n == 2) return 2;
		int p = 1, r = 2;
		for (int i = 3; i <= n; ++i) { //从3个阶梯开始
			int l = p + r;
			p = r;
			r = l;
		}
		return r;
	}



	public static void main(String[] args) {
		int i = climbStairs1(10);
		System.out.println(i);

		int i1 = climbStairs_dp(10);
	}
}
