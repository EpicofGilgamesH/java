package test.sort;

import java.util.Random;

/**
 * @Description LeeCode Case QuickSort 快排
 * @Date 2022-08-12 13:59
 * @Created by wangjie
 */
public class QuickSort {

	/**
	 * 快排思路
	 * 数组a[0...r]
	 * <p>
	 * 在数组中随机取一个元素a[p],将数组中比a[p]小的元素放在左边,比a[p]大的元素放在右边
	 * 这样数组被分为三部分 a[0...p-1] a[p] a[p+1...r] 并且这三部分是递增的
	 * <p>
	 * 对a[0...p-1] 和 a[p+1...r]这两部分,继续以上操作;直到所有的部分都切分成一个元素,那么排序完成
	 *
	 * @param nums
	 * @return
	 */
	public static int[] sortArray(int[] nums) {
		quickSortArray(nums, 0, nums.length - 1);
		return nums;
	}

	public static void quickSortArray(int[] nums, int s, int r) {
		if (s >= r) return;
		int p = portion(nums, s, r);
		quickSortArray(nums, s, p - 1);
		quickSortArray(nums, p + 1, r);
	}

	/**
	 * 切分思路
	 * 简单思路:新建两个数组,分别放入 < a[p] 和 > a[p]的元素,最后再进行合并,但这样空间复杂度太高,需要开辟2个长度为n的数组
	 * 原地分区
	 * 分已排区和未排区
	 *
	 * @param nums
	 * @param s
	 * @param r
	 * @return
	 */
	public static int portion(int[] nums, int s, int r) {
		//获取pivot点,在[s,r]中随机获取
		int p = new Random().nextInt(r - s + 1) + s;
		int pv = nums[p];
		//把p点放在数组的尾部
		if (p != r) swap(nums, p, r);
		int i = s, j = s; //i为已排序区的最后一个元素,j为未排序区的第一个元素
		while (j < r) {
			if (nums[j] < pv) { //交换i,j
				swap(nums, i, j);
				i++;
			}
			j++;
		}
		swap(nums, i, j);
		return i;
	}

	/**
	 * 交换元素
	 *
	 * @param nums
	 * @param i
	 * @param j
	 */
	private static void swap(int[] nums, int i, int j) {
		int temp = nums[i];
		nums[i] = nums[j];
		nums[j] = temp;
	}

	public static void main(String[] args) {
		int[] nums = {8, 4, 2, 3, 6};
		sortArray(nums);
		System.out.println("");
	}
}
