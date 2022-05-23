package backtracking;

/**
 * @Description 01背包问题
 * @Date 2022-05-09 14:00
 * @Created by wangjie
 */
public class ZeroAndOneBag {

	//背包总承重量W kg,现有n个物品,起重量不等,不可分割.期望选择几件物品装入背包中,
	//在不超过背包总容量的情况下,让背包的总重量最大.
	//假设背包可承受重量为:100kg,物品个数为:10个;放置的物品存储在数组a中.

	private static int[] a = {1, 2, 5, 19, 22, 50, 61, 3, 52, 96};


	/**
	 * 放入背包
	 *
	 * @param i  正在考察的物品index
	 * @param cw 放入背包物品的总重量
	 * @param a  物品数组
	 * @param n  放入背包的物品个数
	 * @param w  背包总重量
	 */
	private static void f(int i, int cw, int[] a, int n, int w) {
		if (cw == w || i == n) { //放入背包的物品总重量等于背包承重 或者 物品检索完了,找到答案
			return;
		}
		f(i + 1, cw, a, n, w); //不放入当前i物品
		if (cw + a[i] <= w) { //已经超过背包总称重,就不用装了
			f(i + 1, cw + a[i], a, n, w); //放入当前i物品
		}
	}

	//如果每个物品不仅重量不同，价值也不同。如何在不超过背包重量的情况下，让背包中的总价值最大?
	private static int[] b = {2, 3, 4, 9, 10, 5, 12, 36, 25, 3};

	private static int max = 0;

	private static void f_m(int i, int cw, int[] a, int mx, int[] b, int n, int w) {
		if (cw >= w || i == n) {
			if (mx > max) max = mx;
			return;
		}
		//不装入当前i物品
		f_m(i + 1, cw, a, mx, b, n, w);
		if (cw + a[i] <= w) { //已经超过背包总承重,不用再装
			f_m(i + 1, cw + a[i], a, mx + b[i], b, n, w); //装入当前i物品
		}
	}

	public static void main(String[] args) {
		//f(0, 0, a, 10, 100);
		f_m(0, 0, a, 0, b, 10, 100);
		System.out.println("max:" + max);
	}
}

