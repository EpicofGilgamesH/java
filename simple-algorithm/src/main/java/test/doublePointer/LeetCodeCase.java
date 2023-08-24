package test.doublePointer;

/**
 * 双指针LeetCode题示例
 */
public class LeetCodeCase {

	/**
	 * 27. 移除元素
	 * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
	 * <p>
	 * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
	 * <p>
	 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
	 * <p>
	 * 说明:
	 * <p>
	 * 为什么返回数值是整数，但输出的答案是数组呢?
	 * <p>
	 * 请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
	 * <p>
	 * 你可以想象内部操作如下:
	 * <p>
	 * // nums 是以“引用”方式传递的。也就是说，不对实参作任何拷贝
	 * int len = removeElement(nums, val);
	 * <p>
	 * // 在函数里修改输入数组对于调用者是可见的。
	 * // 根据你的函数返回的长度, 它会打印出数组中 该长度范围内 的所有元素。
	 * for (int i = 0; i < len; i++) {
	 * print(nums[i]);
	 * }
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：nums = [3,2,2,3], val = 3
	 * 输出：2, nums = [2,2]
	 * 解释：函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。你不需要考虑数组中超出新长度后面的元素。例如，函数返回的新长度为 2 ，而 nums = [2,2,3,3] 或 nums = [2,2,0,0]，也会被视作正确答案。
	 * 示例 2：
	 * <p>
	 * 输入：nums = [0,1,2,2,3,0,4,2], val = 2
	 * 输出：5, nums = [0,1,4,0,3]
	 * 解释：函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。注意这五个元素可为任意顺序。你不需要考虑数组中超出新长度后面的元素。
	 * <p>
	 * 提示：
	 * <p>
	 * 0 <= nums.length <= 100
	 * 0 <= nums[i] <= 50
	 * 0 <= val <= 100
	 */
	public static class RemoveElement {

		/**
		 * 个人思路:
		 * 直接按题意来说,需要遍历整个数组,然后找到指定的val值,并将其移动到数组的尾部
		 * 移动到尾部最好的方式是交换,与尾部元素进行交换,并记录已经交换过的元素位置,所以用双指针分别来指向需要交换的两个元素,
		 * 比较合适解决本题
		 *
		 * @param nums
		 * @param val
		 * @return
		 */
		public static int removeElement(int[] nums, int val) {
			int p = 0, q = nums.length - 1;
			while (p <= q) {
				while (q >= 0 && nums[q] == val) {
					q--;
				}
				if (q >= 0 && p <= q && nums[p] == val) {  // 交换
					swap(nums, p, q);
					q--;
				}
				p++;
			}
			return q + 1;
		}

		/**
		 * 在同时考虑p,q指针的移动时,会忽略很多种场景而出现问题
		 * 那么可以不考虑q指针的向前移动,q指针只做交换的预备位置
		 * <p>
		 * 看了官方思路后,发下元素可以不用移动,因为数组的后端元素不需要保留,可以直接使用复制,将不等于val的元素放在左边
		 *
		 * @param nums
		 * @param val
		 * @return
		 */
		public static int removeElementII(int[] nums, int val) {
			int p = 0, q = nums.length - 1;
			while (p <= q) {
				if (nums[p] == val) {
					swap(nums, p, q);  //nums[p]=nums[q]
					q--;
				} else {
					p++;
				}
			}
			return q + 1;
		}

		public static void swap(int[] nums, int i, int j) {
			int temp = nums[i];
			nums[i] = nums[j];
			nums[j] = temp;
		}

		public static void main(String[] args) {
			int[] nums = {2, 2, 3};
			int i = removeElement(nums, 2);

			int[] nums1 = {2, 2, 3};
			int i1 = removeElementII(nums1, 2);
			System.out.println(i1);
		}
	}
}
