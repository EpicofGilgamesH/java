package reduce;

/**
 * @Description 分治算法
 * @Date 2022-05-06 17:27
 * @Created by wangjie
 */
public class Reduce {

	private static int num = 0;

	private static int count(int[] array, int n) {
		mergesortC(array, 0, n - 1);
		return num;
	}

	/**
	 * 递归操作
	 *
	 * @param array
	 * @param p
	 * @param q
	 */
	private static void mergesortC(int[] array, int p, int r) {
		//递归终止条件
		int q = (p + r) / 2;
		if (q >= r) return;
		//分解操作
		mergesortC(array, p, q);
		mergesortC(array, q + 1, r);
		//合并操作
		merge(array, p, q, r);
	}

	/**
	 * 将A[p...q]和 A[q+1...r] 和并到 A[p...r]
	 *
	 * @param array
	 * @param p
	 * @param q
	 * @param r
	 */
	private static void merge(int[] a, int p, int q, int r) {
		int i = p, j = q + 1, k = 0; //k为临时数组的起始游标
		int[] t = new int[r - p + 1];
		while (i <= q && j <= r) { //i,j两个游标都未达到数组的边界
			if (a[i] <= a[j]) {
				t[k++] = a[i++];
			} else {
				t[k++] = a[j++];
				//[p-q]范围内比a[j]大的元素
				num += q - i + 1;
			}
		}
		//判断哪个数组有剩余
		int s = i, e = q;
		if (i > q) { //i游标到达了数组边界
			s = j;
			e = r;
		}
		while (s <= e) { //将A[s...e]放入临时数组
			t[k++] = a[s++];
		}
		for (int l = 0; l < r - p + 1; l++) { //将临时数组t[0,r-p]拷贝会a[p,r]
			a[l + p] = t[l];
		}
	}

	public static void main(String[] args) {
		int[] arr = {11, 8, 3, 9, 7, 1, 2, 5};
		int count = count(arr, arr.length);
		System.out.println(count);
	}
}
