package test.array;

/**
 * 实现两个有序数组合并为一个有序数组
 *
 * @Date 2022-08-01 15:40
 * @Created by wangjie
 */
public class MergeSortArray {

	/**
	 * 合并两个有序的数组
	 * 思路:arr1的第一个元素s1位置,arr2的第一个元素s2位置;进行比较,将较小的元素,放入结果集数组中
	 * 直到有一个数组到达了最后一个元素,则将另一个数组剩余的元素全部依次放入结果集数组中
	 *
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static int[] merge(int[] arr1, int[] arr2) {
		int l1 = arr1.length, l2 = arr2.length, s1 = 0, s2 = 0, i = 0;
		int[] arr = new int[l1 + l2];
		//两数组合并到结果集数组中
		while (s1 < l1 && s2 < l2) {
			if (arr1[s1] <= arr2[s2]) {
				arr[i++] = arr1[s1++];
			} else {
				arr[i++] = arr2[s2++];
			}
		}
		//处理剩余数据
		if (l1 - s1 > 0) {
			for (int j = s1 + 1; j < l1; ++j) {
				arr[i++] = arr1[j];
			}
		}
		if (l2 - s2 > 0) {
			for (int j = s2; j < l2; ++j) {
				arr[i++] = arr2[j];
			}
		}
		return arr;
	}

	public static void main(String[] args) {
		int[] a1 = {1, 3, 6, 9};
		int[] a2 = {2, 4, 5, 10};
		int[] merge = merge(a1, a2);
		System.out.println("");
	}

}
