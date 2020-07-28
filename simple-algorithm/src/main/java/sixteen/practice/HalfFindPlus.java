package sixteen.practice;

/**
 * @Description 二分查找的变形
 * @Date 2020/7/24 9:28
 * @Created by wangjie
 */
public class HalfFindPlus {

	/**
	 * @param [a, n, v]
	 * @return int
	 * @date 2020/7/24 9:49
	 * @author wangjie
	 */
	//二分查找的变形,找到第一个匹配的元素
	public static int getFirstIndex(int[] a, int n, int v) {
		int low = 0, hign = n - 1;
		while (low <= hign) {
			int mid = low + ((hign - low) >> 1);
			if (a[mid] >= v) {
				hign = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		if (low < n && a[low] == v) {
			return low;
		} else
			return -1;
	}

	public static int getFirstIndex1(int[] a, int n, int v) {
		int low = 0, hign = n - 1;
		while (low <= hign) {
			int mid = low + ((hign - low) >> 1);
			if (a[mid] > v) {
				hign = mid - 1;
			} else if (a[mid] < v) {
				low = mid + 1;
			} else {
				if (mid == 0 || a[mid - 1] != v) {
					return mid;
				} else
					hign = mid - 1;
			}
		}
		return -1;
	}

	/**
	 * 二分查找获取最后一个指定元素的位置
	 *
	 * @param
	 * @return int
	 * @date 2020/7/28 17:39
	 * @author wangjie
	 **/
	public static int getLastIndex(int[] a, int n, int v) {
		int low = 0, hign = n - 1;
		while (low <= hign) {
			int mid = low + ((hign - low) >> 1);
			if (a[mid] > v) {
				hign = mid - 1;
			} else if (a[mid] < v) {
				low = mid + 1;
			} else {
				if (mid == n - 1 || a[mid + 1] != v) {
					return mid;
				} else
					low = low + 1;
			}
		}
		return -1;
	}

	/**
	 * 查找第一个大于等于给定值的元素
	 *
	 * @param
	 * @return int
	 * @date 2020/7/28 18:01
	 * @author wangjie
	 **/
	public static int getFirstEqualOrGreater(int[] a, int n, int v) {
		int low = 0, hign = n - 1;
		while (low <= hign) {
			int mid = low + ((hign - low) >> 1);
			if (a[mid] >= v) {
				if (mid == 0 || a[mid - 1] < v) {
					return mid;
				} else
					hign = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return -1;
	}


	/**
	 * 查找第一个小于等于给定值的元素
	 *
	 * @param a
	 * @param n
	 * @param v
	 * @return
	 */
	public static int getFirstEqualOrLess(int[] a, int n, int v) {
		int low = 0, hign = n - 1;
		while (low <= hign) {
			int mid = low + ((hign - low) >> 1);
			if (a[mid] <= v) {
				if (mid == n - 1 || a[mid + 1] > v) {
					return mid;
				} else
					low = mid + 1;
			} else {
				hign = mid - 1;
			}
		}
		return -1;
	}


	public static void main(String[] args) {
		int[] a = {1, 2, 3, 3, 3, 3, 4, 5};
		int firstIndex = getFirstIndex(a, a.length, 3);
		System.out.println("first index:" + firstIndex);
		int firstIndex1 = getFirstIndex1(a, a.length, 3);
		System.out.println("first index1:" + firstIndex);

		int lastIndex = getLastIndex(a, a.length, 3);
		System.out.println("last index:" + lastIndex);

		int firstEqualOrGreater = getFirstEqualOrGreater(a, a.length, 6);
		System.out.println("first equal or greater index:" + firstEqualOrGreater);

		int firstEqualOrLess = getFirstEqualOrLess(a, a.length, 6);
		System.out.println("first equal or less index:" + firstEqualOrLess);
	}
}
