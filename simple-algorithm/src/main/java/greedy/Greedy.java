package greedy;

/**
 * @Description 贪心算法
 * @Date 2022-04-29 15:05
 * @Created by wangjie
 */
public class Greedy {

	//将m个糖果分配给n个孩子,m<n;每颗糖果大小不一,分别是:s1,s2,s3...sm
	//每个孩子对糖果的需求也不一样,糖果大小 >= 孩子对糖果大小的需求时,才满足条件
	//孩子对糖果的需求分别是:g1,g2,g3...gn  求如果分配尽可能满足最多的数量的孩子.

	//用贪心思路,每次找到需求最小的孩子,然后找到最小的能满足该孩子的糖果.直到不能继续分配,就是最优解.

	private static final int[] candis = {1, 2, 2, 4, 6, 8, 10, 12, 14, 15};
	private static final int[] children = {1, 3, 3, 4, 5, 5, 6, 8, 9, 10, 11, 13, 14, 15, 16, 16, 18};

	private static void candy() {

		int m = candis.length;
		//int n = children.length;
		//预先升序 m,n
		int i = 0, j = 0;
		while (i < m) {
			int child = children[j];
			int candy = candis[i];
			if (candy >= child) {
				System.out.println("孩子" + child + "分到糖果" + candy);
				i++;
				j++;
			} else {
				i++;
			}
		}
	}

	//支付,用最少的现金张数,支付指定的金额
	//index,0:1元 1:5元 2:10元 3:50元 4:100元
	private static final int[] face = {1, 5, 10, 50, 100};
	private static final int[] cash = {2, 0, 5, 4, 2};
	private static final int tc = tCash();

	private static void pay(int total) {
		if (total > tc) {
			System.out.println("不够支付.");
			return;
		}
		for (int i = face.length - 1; i >= 0; i--) {
			int ci = cash[i];
			int fi = face[i];
			while (total >= fi && ci > 0) {
				System.out.println("支付一张面额" + face[i] + "元的纸币.");
				total -= fi;
				ci--;
			}
		}
		if (total > 0)
			System.out.println("剩余" + total + "元不够现金支付,需要找零.");
	}

	/**
	 * 获取总金额
	 *
	 * @return
	 */
	private static int tCash() {
		int sum = 0;
		for (int i = 0; i < face.length; i++) {
			sum += face[i] * cash[i];
		}
		return sum;
	}

	//在一个非负整数 a 中，我们希望从中移除 k 个数字，让剩下的数字值最小，如何选择移除哪 k 个数字呢?

	/**
	 * 将一个非负整数移除指定个元素,使其得到数值最小
	 *
	 * @param t 原数值
	 * @param l 移除的元素个数
	 * @return 返回的最小数值
	 */
	private static int rag(int t, int l) {
		char[] s = String.valueOf(t).toCharArray();
		int[] re = new int[l]; //保留数组
		int ri = 0; //第几次取值
		int ls = -1; //取最小值的起始位置
		while (ri < l) {
			//所剩位数刚好,则直接返回
			if (s.length - ls - 1 <= l - ri) {
				while (ri < l) {
					re[ri++] = ++ls;
				}
				break;
			}
			//第一位找最小,从[0,s-l+1]中
			int i = ls + 1, min = Integer.valueOf(String.valueOf(s[i++]));
			for (; i <= s.length - l + ri; i++) {
				int vi = Integer.valueOf(String.valueOf(s[i]));
				if (vi < min) {
					min = vi;
					ls = i;
				}
			}
			System.out.println("保留第" + ri + "位取值:" + min);
			re[ri++] = ls;
		}
		//结果数值
		String result = "";
		for (int i = 0; i < re.length; i++) {
			result += s[re[i]];
		}
		return Integer.valueOf(result);
	}


	public static void main(String[] args) {
		//candy();
		//pay(389);
		int rag = rag(3255155, 4);
		System.out.println("结果为:" + rag);
	}
}
