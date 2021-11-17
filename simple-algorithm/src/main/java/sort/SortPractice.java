package sort;

/**
 * @Description 排序-练习
 * @Date 2021-10-13 15:31
 * @Created by wangjie
 */
public class SortPractice {

	/**
	 * 冒泡排序算法
	 * 思路
	 * 1.从index=0开始,与后一个元素进行比较,如果前一个元素较大,就将该元素与其后一个元素进行交换;
	 * index往后移动一位,重复比较操作;index可以到n-1  此时最大元素放在最后一位
	 * 2.继续上一步的操作,index可以到n-1-1 继而得出 index<n-i-1
	 * 两个问题
	 * 1.第五次进行冒泡的时候, j<6-4-1 => j<1  第二个for循环还能转一次
	 * 第六次进行冒泡的时候, j<0 第二个for循环无法进入 上一层for循环空转一次
	 * 2.如果第四次冒泡就已经不进行交换操作了,那说明剩下的元素已经有序
	 */
	private static void bubbleSort(int[] array) {
		int len = array.length;
		if (len <= 1) return;
		int ec = 0;
		for (int i = 0; i < len; ++i) {
			int count = i + 1;
			for (int j = 0; j < len - i - 1; ++j) {
				if (array[j] > array[j + 1]) { //交换
					int temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
					ec++;
				}
			}
			System.out.println("第" + count + "次冒泡");
		}
		System.out.println("交换次数:" + ec);
	}

	/**
	 * 冒泡排序优化
	 *
	 * @param array
	 */
	private static void bubbleSort1(int[] array) {
		int len = array.length;
		if (len <= 1) return;
		int ec = 0;
		int count = 0;
		for (int i = 0; i < len; ++i) {
			boolean flag = false;//退出冒泡循环标识位
			for (int j = 0; j < len - i - 1; ++j) {
				if (array[j] > array[j + 1]) { //交换
					int temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
					ec++;
					flag = true;
				}
			}
			if (!flag) break;
			System.out.println("第" + ++count + "次冒泡");
		}
		System.out.println("交换次数:" + ec);
	}

	/**
	 * 1.冒泡排序是原地排序算法吗?
	 * 2.冒泡排序是稳定的排序算法吗?
	 * 3.冒泡排序的时间复杂度是多少?
	 */


	/**
	 * 插入排序思路
	 * 已排序区 --- 未排序区
	 * 1.初始时,将第一个元素作为已排序区,其他元素作为未排序区 i=1
	 * 2.取出未排序区的第一个元素val,与已排序的元素进行逐一比较(从右至左)j=i-1,如果val较小,则被比较的该元向右移动一位
	 * 3.当已排序区存在第一个元素不需要向后移动时,说明插入点找到;val插入到该被比较元素的下一位
	 * 4.如果已排序区全部比较完;则插入点应该为index=0;此时通过for循环的--操作,值为-1;与第三步统一为index=j+1
	 *
	 * @param array
	 */
	private static void insertSort(int[] array) {
		int l = array.length;
		if (l <= 1) return;
		for (int i = 1; i < l; i++) {
			int val = array[i];
			int j = i - 1;
			for (; j >= 0; j--) {
				if (val < array[j]) {
					array[j + 1] = array[j];
				} else {
					break;
				}
			}
			//一轮插入排序完成,找到插入点
			array[j + 1] = val;
		}
	}

	/**
	 * 1.插入排序是原地排序算法吗?
	 * 2.插入排序是稳定的排序算法吗?
	 * 3.插入排序的时间复杂度是多少?
	 */


	/**
	 * 选择排序思路
	 * 已排序区 --- 未排序区
	 * 1.从[0,n]元素中找到最小元素index,与index=0 进行交换
	 * 2.从[1,n]元素中找到最小元素index,与index=1 进行交换
	 * 3.从[i,n]元素中找到最小元素index=min,与index=i 进行交换
	 *
	 * @param array
	 */
	private static void selectInsert(int[] array) {
		int l = array.length;
		if (l <= 1) return;
		for (int i = 0; i < l; i++) {
			int min = i;
			for (int j = i + 1; j < l; j++) {
				if (array[j] < array[min]) {
					min = j;
				}
			}
			//交换
			int temp = array[min];
			array[min] = array[i];
			array[i] = temp;
		}
	}

	/**
	 * 归并排序思路
	 * 1.将要排序的数组进行分解成组、直到分解成每组只有一个元素(递归)
	 * --mergeSortC(A,p,r); A:数组 p:被分解开始的位置 r:被分解元素结束的位置
	 * --分成2部分 mergeSortC(A,p,q) mergeSortC(A,q+1,r) q=(p+r)/2 退出条件:q>=r ,如果q到了r的位置,则分解到只有一个元素了,递归退出
	 * 2.进行合并操作,使用合并操作技巧,将数据按顺序合并(递归)
	 * --将上一步的A[p...q] 和 A[q+1...r] 合并成 A1[p...r],第一次执行为最内层递归操作,即分解成只有一个元素的情况
	 * --merge(A1[p...r],A[p...q],A[q+1...r]);
	 * --申请临时数组A1,长度与A[p...r]一样.使用两个游标i,j 分别指向A[p...q]和A[q+1...r]两个数组的第一个元素
	 * --比较A[i]和A[j]的大小,如果A[i]<=A[j],就把A[i]放入临时数组中,i向后移动一位;否则就把A[j]放入临时数组,j向后移动一位
	 * --直到i或者j达到数组的边界,此时把另一个为还有元素的数组整体放入到临时数组中
	 *
	 * @param array 要排序的数组
	 * @param n     长度
	 */
	private static void mergeSort(int[] array, int n) {
		mergesortC(array, 0, n - 1);
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
		int i = p, j = q + 1, k = 0; //k为临时数组的其实游标
		int[] t = new int[r - p + 1];
		while (i <= q && j <= r) { //i,j两个游标都未达到数组的边界
			if (a[i] <= a[j]) {
				t[k++] = a[i++];
			} else {
				t[k++] = a[j++];
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


	/**
	 * 快排思路
	 * 需要排序数组中下标从p到r之间的一组数据,选择p到r中任意一个数据作为pivot(分区点)
	 * 遍历p到r之间的数据,将大于pivot的元素放在右边;小于pivot的元素放在左边
	 * [p...i-1][i][i+1...r] 数据将分为3个部分
	 * 而[p...i-1]和[i+1...r]这两部分数据,同以上操作,分为3部分数据,直到所有的数据全部分为只有一个元素
	 * 得到的结果集即是有序的
	 * <p>
	 * 获取pivot:一般情况下,选择p到r区间的最后一个元素
	 *
	 * @param array
	 * @param n
	 */
	private static void quickSort(int[] array, int n) {
		quickSortC(array, 0, n - 1);
	}

	private static void quickSortC(int[] array, int p, int r) {
		if (p >= r) return;
		//int pivot = pivot(array, p, r);
		int pivot = pivot_n(array, p, r);
		quickSortC(array, p, pivot - 1);
		quickSortC(array, pivot + 1, r);
	}

	/**
	 * 获取p到r区间的分区元素下标
	 * 遍历p到r之间的数据,将大于pivot的元素放在右边;小于pivot的元素放在左边
	 *
	 * @param array
	 * @param p
	 * @param r
	 * @return
	 */
	private static int pivot(int[] array, int p, int r) {
		int v = array[r];
		int n = r - p;
		int[] a = new int[n];
		int[] b = new int[n]; //需要2个临时数组,分别存放<pivot的元素和>pivot的元素 临时数组的长度最小为p到r区间的元素个数
		int k = 0, l = 0;
		for (int i = p; i < r; i++) {
			if (array[i] < v)
				a[k++] = array[i];
			else
				b[l++] = array[i];
		}
		//放置pivot的位置
		int pi = k + p;
		array[pi] = v;
		//合并到array数组的 p到r区间
		for (int j = 0; j < k; j++) {
			array[p++] = a[j];
		}
		int pv = pi + 1; //pi定位到pivot+1的位置
		for (int x = 0; x < l; x++) {
			array[pv++] = b[x];
		}
		return pi;
	}

	/**
	 * 原地进行分区操作
	 * 使用交换的方式,不需要临时数组 实现将大于pivot的元素放在右边,小于pivot的元素放在左边
	 * <p>
	 * 思路:设置两个游标i,j i游标用于确认r(pivot)的位置,j游标一直向右移动;
	 * 当j位置的值 < pivot的值时,应该放在i位置的左边,此时其与i进行交换 -> 去到左边
	 * 当j位置的值 > pivot的值时,应该放在i位置的右边,此时不移动 默认在i位置的右边
	 * 当j移动完成时(到r-1的元素),已经完成分区 [小于pivot] i(大于pivot) [大于pivot]
	 * 将i与pivot的值进行交换 则pivot来到i的位置,i值去到最右边,完成分区
	 *
	 * @param array
	 * @param p
	 * @param r
	 * @return
	 */
	private static int pivot_n(int[] array, int p, int r) {
		int v = array[r]; //pivot value
		int i = p;
		for (int j = p; j < r; j++) {
			if (array[j] < v) {
				swap(array, i, j);
				i++;
			}
		}
		swap(array, i, r);
		return i;
	}


	private static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	private static int findFirstK(int[] array, int n, int k) {
		return findFirstK_C(array, 0, n - 1, k);
	}

	private static int findFirstK_C(int[] array, int p, int r, int k) {
		int pivot = pivot_n(array, p, r);
		if (pivot + 1 > k)
			return findFirstK_C(array, p, pivot - 1, k);
		else if (pivot + 1 < k)
			return findFirstK_C(array, pivot + 1, r, k);
		else
			return pivot;
	}

	public static void main(String[] args) {
		int[] array = {4, 5, 6, 3, 2, 1};
		//bubbleSort(array);

		int[] array1 = {3, 5, 4, 1, 2, 6};
		//bubbleSort1(array1);

		//inserSort(array1);

		selectInsert(array1);

		int[] array2 = {11, 8, 3, 9, 7, 1, 2, 5};
		//mergeSort(array2, 8);

		quickSort(array2, 8);

		int[] array3 = {11, 8, 3, 9, 7, 1, 2, 5};
		int firstK = findFirstK(array3, 8, 3);
		System.out.println(firstK);
	}
}
