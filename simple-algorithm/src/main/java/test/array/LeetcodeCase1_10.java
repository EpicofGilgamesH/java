package test.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * CodeTop Leetcode刷题顺序
 */
public class LeetcodeCase1_10 {

	/**
	 * Leetcode 88. 合并两个有序数组
	 * <p>
	 * 给你两个按 非递减顺序 排列的整数数组nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
	 * <p>
	 * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
	 * <p>
	 * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，
	 * 其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
	 * <p>
	 * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
	 * 输出：[1,2,2,3,5,6]
	 * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
	 * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
	 * <p>
	 */
	static class MergeTwoSortArray {


		/**
		 * 分析思路：
		 * 首先相当双指针,p指向nums1的0位置,q指向nums2的0位置;新建一个数组,向数组里面投入元素
		 * 数组的第一个元素为 nums1[0] 和 nums2[0]中较小的,后面放入数组的元素指向向后移动一位;
		 * 再次比较p和q指向的值,较小的放入元素中
		 * <p>
		 * 交换法不太好实现,歧途
		 */
		public static void merge(int[] nums1, int m, int[] nums2, int n) {
			int p = 0, q = 0, v;

			int[] r = new int[m + n];
			while (p < m || q < n) {
				if (p == m) { // nums1的元素已经遍历完
					v = nums2[q++];
				} else if (q == n) { // nums2的元素已经遍历完
					v = nums1[p++];
				} else if (nums1[p] < nums2[q]) { // nums1的元素较小
					v = nums1[p++];
				} else { // nums2的元素较小
					v = nums2[q++];
				}
				r[p + q - 1] = v;  // p放入元素就+1 q放入元素也+1 那么r中的index=p+q-1
			}
			for (int i = 0; i < m + n; i++) {
				nums1[i] = r[i];
			}
		}

		/**
		 * 实现控件复杂度O(1) 则需要在nums1数组的基础上进行合并,可以采用逆向合并的方式,从右往左进行合并
		 *
		 * @param
		 */
		public static void merge1(int[] nums1, int m, int[] nums2, int n) {
			int p = m - 1, q = n - 1, v;
			while (p >= 0 || q >= 0) {
				if (p < 0) { // nums1的元素已经遍历完
					v = nums2[q--];
				} else if (q < 0) { // nums2的元素已经遍历完
					v = nums1[p--];
				} else if (nums1[p] > nums2[q]) {
					v = nums1[p--];
				} else v = nums2[q--];

				nums1[p + q + 2] = v;
			}
		}


		public static void main(String[] args) {
			int[] nums1 = {1, 2, 3, 0, 0, 0};
			int[] nums2 = {2, 5, 6};
			/*int[] nums1 = {1, 2, 4, 5, 6, 0};
			int[] nums2 = {3};*/
			merge1(nums1, 3, nums2, 3);
			System.out.println();
		}
	}

	/**
	 * Leetcode 53.最大子序和
	 * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
	 * <p>
	 * 子数组 是数组中的一个连续部分。
	 * <p>
	 * 示例 1:
	 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
	 * 输出：6
	 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
	 * <p>
	 * 示例 2：
	 * 输入：nums = [1]
	 * 输出：1
	 * <p>
	 * 示例 3：
	 * 输入：nums = [5,4,-1,7,8]
	 * 输出：23
	 */
	static class MaxSubArray {

		/**
		 * 常规思路:
		 * 设指针p指向数组的最左边,然后计算后面每多一位数的连续数组和;计算完一轮之后,p向右移动一位,继续计算。
		 * 每一轮计算出最大的连续子数组和,然后找出最大的
		 * <p>
		 * 优化思路：
		 * nums = [-2,1,-3,4,-1,2,1,-5,4] 列出计算过程
		 * -2,  1,  -3,  4,  -1,  2,   1,  -5,  4
		 * -2, -1,  -4,  0,  -1,  1, [2],  -3,  1   p=0
		 * *    1,  -2,  2,   1,  3, [4],  -1,  3   p=1  每个元素+2
		 * *        -3,  1,   0,  2, [3],  -2,  2   ...  每个元素-1
		 * *             4,   3,  5, [6],   1,  5   ...  每个元素+3
		 * *                 -1,  1,  2 ,  -3,  1   ...  每个元素-4
		 * *                      2,  3 ,  -2,  2   ...  每个元素+1
		 * *                          1 ,  -4,  0   ...  每个元素-2
		 * *                               -5, -1   ...  每个元素-1
		 * *                                    4   ...  每个元素+5
		 * *
		 * <p>
		 * 由上可以得出, 当p=0时,计算出每多一位的连续子数组的和后,p的位置每往后移动一位,和都是做相同的操作.
		 * 那么p=0时,[p,6]子数组和最大为2,则每次p往后移动到index=6的子数组都是和最大的.那么p的范围[0,6],即p->[0,6]中计算
		 * 最大和.
		 * <p>
		 * *****************************思路错误*********************************
		 * eg:[-3,-2,-1] 时,
		 * -3,  -2,  -1
		 * -3,  -5,  -6
		 * *    -2,  -3
		 * *         -1
		 * <p>
		 * 事实证明,第一轮最大的值,并不能得到最终最大值的结果,上面的示例知识一种特殊的情况.
		 * 说明此处想使用动态规划的思路错误.如何准确判断是否能使用动态规划呢?????
		 * <p>
		 * <p>
		 * 动态规划,是为了优化递归树所想到的思维,需要有下一步递推公式的求解过程 即 f(i)=f(i-1)+N ,而上面的思路还是走的双重循环,
		 * 所以没法正确得到解题思路.
		 * <p>
		 * 正确思路 :
		 * [-2,1,-3,4,-1,2,1,-5,4]
		 * 应从1个元素开始计算其最大子数组和,然后加一个元素,再次计算其最大子数组和.
		 * 设f(i)为从[0...i] 数组的最大子数组和
		 * f(0) = -2, f(1)= Max(f(0)+ 1,1),f(2)=Max(f(1)-3,-3)
		 * 即得出f(i) = Max(0f(i-1)+i,i) 然后找出 f(0)...f(n)中最大的值
		 *
		 * @return
		 */
		public static int maxSubArray(int[] nums) {
			int n = nums.length, p = 0, s = 0, max = Integer.MIN_VALUE;
			for (int i = 0; i < n; i++) {
				s += nums[i];
				if (s > max) {
					max = s;
					p = i;
				}
			}
			s = max;
			for (int i = 0; i < p; i++) {
				s = -nums[i] + s;
				max = Math.max(s, max);
			}
			for (int i = 0; i < n; i++) {
				s = nums[i];
				max = Math.max(s, max);
			}

			return max;
		}

		/**
		 * 正确思路 :
		 * [-2,1,-3,4,-1,2,1,-5,4]
		 * 计算以i结尾的子数组的最大和
		 * 以-2结尾的子数组最大和-2;以1结尾的子数组最大和1;以-3结尾的子数组最大和-2...
		 * 设f(i)为以i结尾的子数组最大和
		 * f(0) = -2, f(1)= Max(f(0)+ 1,1),f(2)=Max(f(1)-3,-3)
		 * 即得出f(i) = Max(0f(i-1)+i,i) 然后找出 f(0)...f(n)中最大的值
		 *
		 * @return
		 * @return
		 */
		public static int maxSubArrayOfficial(int[] nums) {
			int n = nums.length, max = nums[0], f = max;
			for (int i = 1; i < n; i++) {
				f = Math.max(f + nums[i], nums[i]);
				max = Math.max(f, max);
			}
			return max;
		}

		public static void main(String[] args) {
			//
			int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
			// int[] nums = {1};
			// int[] nums = {5, 4, -1, 7, 8};
			// int[] nums = {-2, -1};
			int i = maxSubArrayOfficial(nums);
			System.out.println("max:" + i);
		}
	}

	/**
	 * Leetcode 1.两数之和
	 * <p>
	 * 给定一个整数数组 nums和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那两个整数，并返回它们的数组下标。
	 * <p>
	 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
	 * <p>
	 * 你可以按任意顺序返回答案。
	 * <p>
	 * 示例一
	 * 输入：nums = [2,7,11,15], target = 9
	 * 输出：[0,1]
	 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
	 * <p>
	 * 示例 2：
	 * 输入：nums = [3,2,4], target = 6
	 * 输出：[1,2]
	 * <p>
	 * 示例 3：
	 * 输入：nums = [3,3], target = 6
	 * 输出：[0,1]
	 * <p>
	 * 进阶：你可以想出一个时间复杂度小于 O(n2) 的算法吗？
	 */
	static class SumTwoNums {

		/**
		 * 常规思路：将nums的元素,两两组合不能重复,组合的方式,两层循环 时间复杂度:O(n^2)
		 * 常规思路很容易就可以完成该题,怎么才能想到时间复杂度低于O(n^2)的思路呢?
		 * 通过官方解析的提示,可以使用哈希表
		 *
		 * @param nums
		 * @param target
		 * @return
		 */
		public static int[] twoSum(int[] nums, int target) {
			int[] r = new int[2];
			for (int i = 0; i < nums.length; i++) {
				for (int j = i + 1; j < nums.length; j++) {
					if (nums[i] + nums[j] == target) {
						r[0] = i;
						r[1] = j;
						return r;
					}
				}
			}
			return r;
		}

		/**
		 * 顺着hash table的思路,来寻找如何降低时间复杂度
		 * HashMap存放所有的元素,然后拿出数组的第一个元素x,判断map中是否存在 v=target-x;当然由于两数不能重复,map需要删除x元素
		 * <p>
		 * 注意nums数组元素值可以重复,但HashMap的key不能重复,到这里发现行不通了... 那么如何能接触这个问题呢?
		 * 其实发现思路的方向已经错了,初始化HashMap必定会将nums中重复的元素给屏蔽,那么就实现不了题干的要求.
		 * <p>
		 * 再次通过官方文档的提示,发现需要重新整理思路,HashMap的key不能重复,那不初始化HashMap呢?
		 * 将nums数组中的第一个元素nums[0]取出,然后从HashMap中查找是否存在v=target-num[0] -> false;将num[0]存在到HashMap中
		 * ...
		 * 推导出 if(map.hasKey(target- nums[i])) return else map.put(nums[i])
		 * 这样即可以解决HashMap会屏蔽重复的问题
		 * <p>
		 * 总结:当发现自己的思路遇到阻碍,并且很难解决时,应该想想是不是应该调整下思路的方式.这应该就是读书时数学不好的原因吧...
		 *
		 * @param nums
		 * @param target
		 * @return
		 */
		public static int[] twoSumOfficial(int[] nums, int target) {
			// 初始化HashMap
			Map<Integer, Integer> map = new HashMap<>();
			for (int i = 0; i < nums.length; i++) {
				Integer n;
				int v = target - nums[i];
				if ((n = map.get(v)) != null) {
					return new int[]{n, i};
				}
				map.put(nums[i], i);
			}
			return new int[0];
		}

		public static void main(String[] args) {
			// int[] nums = {3,2,4};
			int[] nums = {15, 11, 7, 2};
			int[] r = twoSumOfficial(nums, 9);
			System.out.println(Arrays.toString(r));

		}
	}
}
