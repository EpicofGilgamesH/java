package test.array;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * CodeTop Leetcode刷题顺序
 */
public class LeetcodeCase1 {

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

	/**
	 * 189. 轮转数组
	 * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
	 * 示例 1:
	 * 输入: nums = [1,2,3,4,5,6,7], k = 3
	 * 输出: [5,6,7,1,2,3,4]
	 * 解释:
	 * 向右轮转 1 步: [7,1,2,3,4,5,6]
	 * 向右轮转 2 步: [6,7,1,2,3,4,5]
	 * 向右轮转 3 步: [5,6,7,1,2,3,4]
	 * 示例 2:
	 * 输入：nums = [-1,-100,3,99], k = 2
	 * 输出：[3,99,-1,-100]
	 * 解释:
	 * 向右轮转 1 步: [99,-1,-100,3]
	 * 向右轮转 2 步: [3,99,-1,-100]
	 * 提示：
	 * 1 <= nums.length <= 105
	 * -231 <= nums[i] <= 231 - 1
	 * 0 <= k <= 105
	 * 进阶：
	 * 尽可能想出更多的解决方案，至少有 三种 不同的方法可以解决这个问题。
	 * 你可以使用空间复杂度为 O(1) 的 原地 算法解决这个问题吗？
	 */
	static class Rotate {

		/**
		 * 个人思路:
		 * 首先新建一个数组的方式是最直接的思路,将要轮转的元素线放在前面,剩下的元素放在后面
		 * 题目要求0=<k<=105 ,那么k可能大于数组的长度,其实是重复轮转,最终轮转的元素个数为k=k%l
		 *
		 * @param nums
		 * @param k
		 */
		public static void rotate(int[] nums, int k) {
			int n = nums.length;
			int[] arr = new int[n];
			k = k > n ? k % n : k;
			for (int i = k; i < n; i++) {
				arr[i] = nums[i - k];
			}
			for (int j = 0; j < k; j++) {
				arr[j] = nums[n - k + j];
			}
			for (int i = 0; i < n; i++) {
				nums[i] = arr[i];
			}
		}

		/**
		 * 如何实现原地轮转呢?常规思路是模拟轮转,每次轮转最后一个元素拿出来,其他所有元素向后移动一位,然后把拿出来的元素放在第一位
		 * 虽然空间复杂度为O(1) 但时间复杂度上升到了O(n^2)
		 * 如何降低时间复杂度呢? leetcode提交超时
		 *
		 * @param nums
		 * @param k
		 */
		public static void rotateI(int[] nums, int k) {
			int n = nums.length;
			k = k > n ? k % n : k;
			for (int i = 0; i < k; ++i) {
				int last = nums[n - 1];
				// 所有元素向后移动,反向操作
				for (int j = n - 2; j >= 0; --j) {
					nums[j + 1] = nums[j];
				}
				nums[0] = last;
			}
		}

		/**
		 * 翻转数组之后,再翻转;借阅官方的思路
		 * 1,2,3,4,5,6,7  k=3
		 * 5,6,7,1,2,3,4
		 * 实际上是把后k个元素顺序移动到数组的前面,我们首先将整个数组都翻转
		 * 7,6,5,4,3,2,1
		 * 然后再把前k个元素进行翻转 5,6,7,4,3,2,1
		 * 再翻转后n-k个元素 5,6,7,1,2,3,4
		 * 在3次翻转的过程中,每个元素访问2次,时间按杂度为O(2n) 即 O(n)
		 *
		 * @param nums
		 * @param k
		 */
		public static void rotateII(int[] nums, int k) {
			int n = nums.length;
			k = k > n ? k % n : k;
			reverse(nums, 0, n - 1);
			reverse(nums, 0, k - 1);
			reverse(nums, k, n - 1);
		}

		private static void reverse(int[] nums, int start, int end) {
			while (start < end) {
				int temp = nums[start];
				nums[start] = nums[end];
				nums[end] = temp;
				start++;
				end--;
			}
		}

		public static void main(String[] args) {
			int[] ints = {1, 2, 3, 4, 5, 6, 7};
			rotate(ints, 2);
			System.out.println();
			int[] ints1 = {1, 2, 3, 4, 5, 6, 7};
			rotateI(ints1, 2);
			int[] ints2 = {1, 2, 3, 4, 5, 6, 7};
			rotateII(ints2, 2);
			System.out.println();
		}
	}

	/**
	 * 55. 跳跃游戏
	 * 给你一个非负整数数组 nums ，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
	 * 判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false 。
	 * 示例 1：
	 * 输入：nums = [2,3,1,1,4]
	 * 输出：true
	 * 解释：可以先跳 1 步，从下标 0 到达下标 1, 然后再从下标 1 跳 3 步到达最后一个下标。
	 * 示例 2：
	 * 输入：nums = [3,2,1,0,4]
	 * 输出：false
	 * 解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标。
	 * 提示：
	 * 1 <= nums.length <= 104
	 * 0 <= nums[i] <= 105
	 */
	static class CanJump {

		/**
		 * 首先自己在思考的时候,找不到正确的思考方向 在寻找指针遍历?dp思路?或者是某个点的元素必达?等等这些
		 * 实际上,正确的思维是贪心,从前往后-每次找到到目前为止能跳到最远处的元素的位置.因为如果能跳到4,那么一定能跳到前面的1,2,3
		 * 那遇到这类题,如果能快速的找到贪心这种正确的思路呢?
		 *
		 * @param nums
		 * @return
		 */
		public static boolean canJump(int[] nums) {
			int f = 0;
			for (int i = 0; i < nums.length; i++) {
				// 到不了这个元素
				if (f < i) return false;
				f = Math.max(nums[i] + i, f);
				if (f >= nums.length - 1) return true;
			}
			return true;
		}

		public static void main(String[] args) {
			System.out.println(canJump(new int[]{3, 2, 1, 0, 4}));
		}
	}

	/**
	 * 45. 跳跃游戏 II
	 * 给定一个长度为 n 的 0 索引整数数组 nums。初始位置为 nums[0]。
	 * 每个元素 nums[i] 表示从索引 i 向前跳转的最大长度。换句话说，如果你在 nums[i] 处，你可以跳转到任意 nums[i + j] 处:
	 * 0 <= j <= nums[i]
	 * i + j < n
	 * 返回到达 nums[n - 1] 的最小跳跃次数。生成的测试用例可以到达 nums[n - 1]。
	 * 示例 1:
	 * 输入: nums = [2,3,1,1,4]
	 * 输出: 2
	 * 解释: 跳到最后一个位置的最小跳跃数是 2。
	 * 从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
	 * 示例 2:
	 * 输入: nums = [2,3,0,1,4]
	 * 输出: 2
	 * 提示:
	 * 1 <= nums.length <= 104
	 * 0 <= nums[i] <= 1000
	 * 题目保证可以到达 nums[n-1]
	 */
	static class Jump {

		/**
		 * 个人思路:
		 * 同55题一样，贪心思路,没往前一步时,找到其最原等跳到的位置
		 * 而本地是需要计算多少次能跳到数组的末尾?
		 * 如果最远的位置发生了改变,那么这个点是一定要落的,应该他决定了可以到得更远
		 * 思路不对...
		 * <p>
		 * {0,1,2,3,4,5,6,7,8,9} 索引
		 * {2,3,1,1,4,2,3,5,1,2} 数组
		 * {2,4,4,4,8,8,9}       其每一位上能够到达的最远位置为:
		 * <p>
		 * 通过贪心思路,最后一步从元素3跳到2肯定是最少次数的路径,那跳到元素3的最少路径怎么得到呢?
		 * 同样其肯定是从第一个最远位置 >=6的元素调过来的,可以找到是元素4.
		 * 同理元素4从哪里跳过来呢?第一个最远位置 >=4的元素是1
		 * 总结其跳的路径是:2->3->4->3->2   反向思路
		 * <p>
		 * 正向思路是每一步跳到最远的位置,
		 * 从idx=0开始,第一步跳到元素[3,1]
		 * 由于上一步到的元素,从idx=2开始,第二步跳到元素[1,1,4] ,他们能跳的最远都是4
		 * idx=4,第二步跳到元素[2,3,5,1],他们能跳的最远是8
		 * idx=8,
		 *
		 * @param nums
		 * @return
		 */
		public static int jump(int[] nums) {
			// if (nums.length == 1) return 0;
			int f = 0, step = 0;
			int[] ff = new int[nums.length];
			for (int i = 0; i < nums.length; i++) {
				// 一定能到下一个元素
				if (nums[i] + i > f) {
					f = nums[i] + i;
				}
				ff[i] = Math.min(f, nums.length - 1);
			}
			int j = 0;
			while (j < nums.length - 1) {
				j = ff[j];
				step++;
			}
			return step;
		}

		/**
		 * 上述思路是先将每一步能跳到最远的位置计算出来,再来一次跳跃的模拟
		 * 可以将两步思路放在一个循环中进行,每次计算能跳到的位置,然后分别得出其能跳到的最远位置
		 *
		 * @param nums
		 * @return
		 */
		public static int jumpI(int[] nums) {
			if (nums.length == 1) return 0;
			int f = 0, step = 0, start = 0, end = 0;
			while (end < nums.length) {
				for (int i = start; i <= end; i++) {
					// 第一次跳跃是从0开始
					f = Math.max(f, nums[i] + i); // 能跳到的最远的位置为f
					if (f >= nums.length - 1) {   // 能跳到的最远位置到末尾了,本次跳完到达终点
						return ++step;
					}
				}
				// 本次已跳跃,更新下一次的跳跃范围
				start = end + 1;
				end = f;
				step++;
			}
			return step;
		}

		/**
		 * {0,1,2,3,4,5,6,7,8,9} 索引
		 * {2,3,1,1,4,2,3,5,1,2} 数组
		 * {2,4,4,4,8,8,9}       其每一位上能够到达的最远位置
		 *
		 * @param nums
		 * @return
		 */
		public static int jumpII(int[] nums) {
			int f = 0, step = 0, end = 0;
			for (int i = 0; i < nums.length - 1; ++i) {
				// 从0开始,计算最远可跳距离的递增次数
				f = Math.max(f, nums[i] + i);
				if (i == end) {  // 到了本次可跳最远的地方就准备下一次跳跃
					end = f;
					step++;
				}
			}
			return step;
		}

		public static void main(String[] args) {
			System.out.println(jumpII(new int[]{2, 3, 1, 1, 4, 2, 3, 5, 1, 2}));
			System.out.println(jumpII(new int[]{1}));
		}
	}

	/**
	 * 274. H 指数
	 * 给你一个整数数组 citations ，其中 citations[i] 表示研究者的第 i 篇论文被引用的次数。计算并返回该研究者的 h 指数。
	 * 根据维基百科上 h 指数的定义：h 代表“高引用次数” ，一名科研人员的 h 指数 是指他（她）至少发表了 h 篇论文，并且 至少 有 h 篇论文被引用次数大于等于 h 。如果 h 有多种可能的值，h 指数 是其中最大的那个。
	 * 示例 1：
	 * 输入：citations = [3,0,6,1,5]
	 * 输出：3
	 * 解释：给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 3, 0, 6, 1, 5 次。
	 * 由于研究者有 3 篇论文每篇 至少 被引用了 3 次，其余两篇论文每篇被引用 不多于 3 次，所以她的 h 指数是 3。
	 * 示例 2：
	 * 输入：citations = [1,3,1]
	 * 输出：1
	 * 提示：
	 * n == citations.length
	 * 1 <= n <= 5000
	 * 0 <= citations[i] <= 1000
	 */
	static class HIndex {

		/**
		 * 3,0,6,1,5
		 * 按常规思路,分别计算出 >=1 ,>=2....>=6的元素个数,当遇到>=n的数有n个,那即为n
		 * 原因:f(n)表示数据中大与等于n的个数,如果f(n)=n 那么f(n+1)<=n 因为 >= n+1的数 一定满足 >= n 故 f(n+1)<=f(n) f(n+1)=n+1 不成立
		 *
		 * 用这种思路,需要构建map和排序,最后找到最大次数的索引,时间复杂度很高
		 * 不断的纠错过程中思路不够清晰,当错误次数多之后,一定要静下心来构思整个过程,梳理每个细节*************
		 * @param citations
		 * @return
		 */
		public static int hIndex(int[] citations) {
			Map<Integer, Integer> map = new HashMap<>();
			int times = 0;
			// List<Integer> arr = IntStream.of(citations).boxed().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
			List<Integer> arr = new ArrayList<>(citations.length);
			for (int i = 0; i < citations.length; i++) {
				arr.add(citations[i]);
			}
			arr.sort((o1, o2) -> o2 - o1);
			for (int i = 0; i < citations.length; i++) {
				int k = arr.get(i);
				if (map.containsKey(k)) {
					map.put(k, (times = map.get(k) + 1));
				} else {
					map.put(k, ++times);
				}
			}
			List<Integer> list = new ArrayList<>(map.keySet());
			list.sort(Comparator.comparingInt(o -> o));
			int v = 0, t = 0, pi = list.size(), i = 0;
			// 需要查找第一次出现次数小于索引的索引,拿到其
			for (; i < list.size(); ++i) {
				if ((t = map.get(v = list.get(i))) >= v) {
					pi = v;
				} else {
					break;
				}
			}
			return i == list.size() ? pi : Math.max(pi, t);
		}

		/**
		 * 官方思路一
		 * 上面思路中已经将所有的数都进行排序了,相同的数会在一起、且递增
		 * 例如: 3,0,6,1,5,4,7,9,6,5
		 * 排序: 0,1,3,4,5,5,6,6,7,9
		 * 从后往前看,1个>=9 2个>=7 4个>=6 6个>=5 h再往上加为7时,就没有了
		 * 从这里可以看出,6个>=5 时,会包含5个>=5 即为答案
		 *
		 * @param citations
		 * @return
		 */
		public static int hIndexOfficialI(int[] citations) {
			Arrays.sort(citations);
			int h = 0, i = citations.length - 1;
			while (i >= 0 && citations[i] > h) {
				h++;
				i--;
			}
			return h;
		}

		/**
		 * 官方思路二
		 * 将上述排序过程,转换为技术排序,以空间换时间,将排序的时间复杂度降低为O(n)
		 * h的值不可能超过论文发表的总片数,所以当一遍论文的引用数超过总发表的论文数时,同样把其引用数记为发表数
		 * @param citations
		 * @return
		 */
		public static int hIndexOfficialII(int[] citations) {
			int n = citations.length, tot = 0;
			int[] arr = new int[n + 1];
			for (int i = 0; i < n; i++) {
				if (citations[i] >= n) {
					arr[n]++;
				} else {
					arr[citations[i]]++;
				}
			}
			for (int i = n; i >= 0; --i) {
				tot += arr[i];
				if (tot >= i) {
					return i;
				}
			}
			return 0;
		}

		/**
		 * 官方思路三
		 * 本题需要满足[有h篇论文的引用次数至少为h]的最大值,设h为我们要得到的值.
		 * 那么对于x<=h,所有x都满足[有x篇论文的引用次数至少为x]这个特性,
		 * 而对于y>h,所有y都不满足该性质,所以我们可以扫码一次数组,来判断x是否满足这个性质
		 * @param citations
		 * @return
		 */
		public static int hIndexOfficialIIi(int[] citations) {
			int s = 0, e = citations.length, mid, cnt;
			// 二分可能会出现死循环,因为s和e的值受到mid控制,会出现mid每次都一样的情况
			while (s < e) {
				mid = s + 1 + ((e - s) >> 1);
				cnt = 0; // mid的次数初始化为0,计算mid引用次数的篇数
				for (int i = 0; i < citations.length; i++) {
					if (citations[i] >= mid) {
						cnt++;
					}
				}
				if (cnt >= mid) {  // mid引用次数的篇数大于等于mid值,那么h值肯定大于等于mid,那么h在[s,e]区间
					s = mid;
				} else {            // mid引用次数的篇数小于等于mid值,那么h值肯定小于mid,h在[0,mid)区间
					e = mid - 1;
				}
			}
			return s;
		}

		public static void main(String[] args) {
			int[] arr = new int[]{3, 0, 6, 1, 5};
			System.out.println(hIndex(arr));
			System.out.println(hIndexOfficialIIi(arr));
		}
	}

	/**
	 * 380. O(1) 时间插入、删除和获取随机元素
	 * 实现RandomizedSet 类：
	 * RandomizedSet() 初始化 RandomizedSet 对象
	 * bool insert(int val) 当元素 val 不存在时，向集合中插入该项，并返回 true ；否则，返回 false 。
	 * bool remove(int val) 当元素 val 存在时，从集合中移除该项，并返回 true ；否则，返回 false 。
	 * int getRandom() 随机返回现有集合中的一项（测试用例保证调用此方法时集合中至少存在一个元素）。每个元素应该有 相同的概率 被返回。
	 * 你必须实现类的所有函数，并满足每个函数的 平均 时间复杂度为 O(1) 。
	 * 示例：
	 * 输入
	 * ["RandomizedSet", "insert", "remove", "insert", "getRandom", "remove", "insert", "getRandom"]
	 * [[], [1], [2], [2], [], [1], [2], []]
	 * 输出
	 * [null, true, false, true, 2, true, false, 2]
	 * 解释
	 * RandomizedSet randomizedSet = new RandomizedSet();
	 * randomizedSet.insert(1); // 向集合中插入 1 。返回 true 表示 1 被成功地插入。
	 * randomizedSet.remove(2); // 返回 false ，表示集合中不存在 2 。
	 * randomizedSet.insert(2); // 向集合中插入 2 。返回 true 。集合现在包含 [1,2] 。
	 * randomizedSet.getRandom(); // getRandom 应随机返回 1 或 2 。
	 * randomizedSet.remove(1); // 从集合中移除 1 ，返回 true 。集合现在包含 [2] 。
	 * randomizedSet.insert(2); // 2 已在集合中，所以返回 false 。
	 * randomizedSet.getRandom(); // 由于 2 是集合中唯一的数字，getRandom 总是返回 2 。
	 * 提示：
	 * -231 <= val <= 231 - 1
	 * 最多调用 insert、remove 和 getRandom 函数 2 * 105 次
	 * 在调用 getRandom 方法时，数据结构中 至少存在一个 元素。
	 */
	static class RandomizedSet {

		private HashMap<Integer, Integer> map;
		private List<Integer> list;
		private Random random;


		/**
		 * 一个set,插入和删除的时间复杂度为O(1),那么LinkedList、ArrayList、Deque都不可选
		 * Hash结构可以实现插入和删除为O(1),如果hash产生碰撞转换成红黑树时,复杂度高于O(1)
		 */
		public RandomizedSet() {
			map = new HashMap<>();
			list = new ArrayList<>();
			random = new Random();
		}

		public boolean insert(int val) {
			if (map.containsKey(val)) {
				return false;
			}
			map.put(val, list.size()); // 每次添加元素,idx都是list的最后一个下标+1
			list.add(val);
			return true;
		}

		/**
		 * 如何随机获取key的时间复杂度为O(1)呢?
		 * 思路是正确的,通过一个ArrayList来存储key,实现O(1)的get();
		 * 但是Map中存放了key后,ArrayList在删除时,idx会缩减,那么HashMap中存放的idx就不对了,如何解决呢?
		 * 可以更新Map中的val,让他成为List缩减后真实的idx
		 *
		 * @return boolean
		 */
		public boolean remove(int val) {
			if (map.containsKey(val)) {
				// List在通过索引删除元素时,其后面的元素全部都要向前移动一位,索引全部发生变化
				// 怎么才能让List删除元素时不影响其他元素的索引呢?只有删除最后一个元素才可以.
				// 把要删除的元素与最后元素交换之后再删除,通过更新最后一个元素在map中存放的idx值
				int oIdx = map.get(val);
				int lastIdx = list.size() - 1;
				int key = list.get(lastIdx);
				list.set(oIdx, key);
				map.put(key, oIdx);
				list.remove(lastIdx);
				map.remove(val);
				return true;
			}
			return false;
		}

		public int getRandom() {
			return list.get(random.nextInt(list.size()));
		}

		public static void main(String[] args) {
			RandomizedSet randomizedSet = new RandomizedSet();
			System.out.println(randomizedSet.insert(1));
			System.out.println(randomizedSet.remove(2));
			System.out.println(randomizedSet.insert(2));
			System.out.println(randomizedSet.getRandom());
			System.out.println(randomizedSet.remove(1));
			System.out.println(randomizedSet.insert(2));
			// System.out.println(randomizedSet.remove(1));
			System.out.println(randomizedSet.getRandom());

			/*System.out.println(randomizedSet.insert(0));
			System.out.println(randomizedSet.insert(1));
			System.out.println(randomizedSet.remove(0));
			System.out.println(randomizedSet.insert(2));
			System.out.println(randomizedSet.remove(1));
			System.out.println(randomizedSet.getRandom());
			System.out.println(randomizedSet.insert(3));
			System.out.println(randomizedSet.remove(1));
			System.out.println(randomizedSet.getRandom());*/
		}
	}

	/**
	 * 134. 加油站
	 * 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
	 * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
	 * 给定两个整数数组 gas 和 cost ，如果你可以按顺序绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。如果存在解，则 保证 它是 唯一 的。
	 * 示例 1:
	 * 输入: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
	 * 输出: 3
	 * 解释:
	 * 从 3 号加油站(索引为 3 处)出发，可获得 4 升汽油。此时油箱有 = 0 + 4 = 4 升汽油
	 * 开往 4 号加油站，此时油箱有 4 - 1 + 5 = 8 升汽油
	 * 开往 0 号加油站，此时油箱有 8 - 2 + 1 = 7 升汽油
	 * 开往 1 号加油站，此时油箱有 7 - 3 + 2 = 6 升汽油
	 * 开往 2 号加油站，此时油箱有 6 - 4 + 3 = 5 升汽油
	 * 开往 3 号加油站，你需要消耗 5 升汽油，正好足够你返回到 3 号加油站。
	 * 因此，3 可为起始索引。
	 * 示例 2:
	 * 输入: gas = [2,3,4], cost = [3,4,3]
	 * 输出: -1
	 * 解释:
	 * 你不能从 0 号或 1 号加油站出发，因为没有足够的汽油可以让你行驶到下一个加油站。
	 * 我们从 2 号加油站出发，可以获得 4 升汽油。 此时油箱有 = 0 + 4 = 4 升汽油
	 * 开往 0 号加油站，此时油箱有 4 - 3 + 2 = 3 升汽油
	 * 开往 1 号加油站，此时油箱有 3 - 3 + 3 = 3 升汽油
	 * 你无法返回 2 号加油站，因为返程需要消耗 4 升汽油，但是你的油箱只有 3 升汽油。
	 * 因此，无论怎样，你都不可能绕环路行驶一周。
	 * 提示:
	 * gas.length == n
	 * cost.length == n
	 * 1 <= n <= 105
	 * 0 <= gas[i], cost[i] <= 104
	 */
	static class CanCompleteCircuit {

		/**
		 * 个人思路：
		 * 按常规思路,到了一个加油站的流程是先加油再消耗,然后到达下一个加油站,经历+和-的过程
		 * 一旦出现小于0的时候,则退出
		 * 以上思路是暴力匹配,通过评论的思路,得到几个优化点:
		 * 1.如果加油总和 < 消耗总和 则永远为-1
		 * 2.如果以s开始,到e的时候,所剩油量 < e需要消耗的油量时,那么在[s-e]中,都没法满足,因为在s位置,加油量-消耗量 >=0
		 * 所以下一个s,可以从e+1开始
		 *
		 * @param gas
		 * @param cost
		 * @return
		 */
		public static int canCompleteCircuit(int[] gas, int[] cost) {
			int start, n = gas.length;
			for (int i = 0; i < n; i++) {  // 首先判断从哪个站油站开始可以往后走
				if (gas[i] >= cost[i]) {
					start = i;
					int j = 0, cur = 0;
					while (j < n) {
						int k = j + start >= n ? (j + start) % n : j + start;
						cur = cur + gas[k] - cost[k];
						if (cur < 0) {
							break;
						}
						j++;
					}
					if (j == n) {
						return start;
					}
				}
			}
			return -1;
		}

		public static int canCompleteCircuitI(int[] gas, int[] cost) {
			if (getSum(gas) < getSum(cost)) {
				return -1;
			}
			int start, n = gas.length;
			for (int i = 0; i < n; i++) {  // 首先判断从哪个站油站开始可以往后走
				if (gas[i] >= cost[i]) {
					start = i;
					int j = 0, cur = 0;
					while (j < n) {
						int k = (j + start) % n;
						cur = cur + gas[k] - cost[k];
						if (cur < 0) {
							break;
						}
						j++;
					}
					if (j == n) {
						return start;
					} else {
						i = j + start;
					}
				}
			}
			return -1;
		}

		private static int getSum(int[] arr) {
			int sum = 0;
			for (int i = 0; i < arr.length; i++) {
				sum += arr[i];
			}
			return sum;
		}

		public static void main(String[] args) {
			System.out.println(canCompleteCircuitI(new int[]{5, 8, 2, 8}, new int[]{6, 5, 6, 6}));
		}
	}

	/**
	 * 135. 分发糖果
	 * n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
	 * 你需要按照以下要求，给这些孩子分发糖果：
	 * 每个孩子至少分配到 1 个糖果。
	 * 相邻两个孩子评分更高的孩子会获得更多的糖果。
	 * 请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目 。
	 * 示例 1：
	 * 输入：ratings = [1,0,2]
	 * 输出：5
	 * 解释：你可以分别给第一个、第二个、第三个孩子分发 2、1、2 颗糖果。
	 * 示例 2：
	 * 输入：ratings = [1,2,2]
	 * 输出：4
	 * 解释：你可以分别给第一个、第二个、第三个孩子分发 1、2、1 颗糖果。
	 * 第三个孩子只得到 1 颗糖果，这满足题面中的两个条件。
	 * 提示：
	 * n == ratings.length
	 * 1 <= n <= 2 * 104
	 * 0 <= ratings[i] <= 2 * 104
	 */
	static class Candy {

		/**
		 * 本题题意有点难理解
		 * 如果学生A和学生B相邻,且学生A在学生B的左侧
		 * 那么当ratings[B] > ratings[A] B要比A发的糖多
		 * 那么当ratings[A] > ratings[B] A要比B发的糖多
		 * 对于每一个学生,要满足上述左右两个规则,需要正反两次遍历
		 *
		 * @param ratings
		 * @return
		 */
		public static int candy(int[] ratings) {
			int n = ratings.length;
			int[] left = new int[n];
			for (int i = 0; i < n; i++) {
				if (i > 0 && ratings[i] > ratings[i - 1]) {
					left[i] = left[i - 1] + 1;
				} else {
					left[i] = 1;
				}
			}
			int right = 0, ret = 0;
			for (int i = n - 1; i >= 0; i--) {
				if (i < n - 1 && ratings[i] > ratings[i + 1]) {
					right++;
				} else {
					right = 1;
				}
				ret += Math.max(left[i], right);
			}
			return ret;
		}

		public static void main(String[] args) {
			System.out.println(candy(new int[]{1, 2, 2}));
		}
	}

	/**
	 * 42. 接雨水
	 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
	 * 示例 1：
	 * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
	 * 输出：6
	 * 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
	 * 示例 2：
	 * 输入：height = [4,2,0,3,2,5]
	 * 输出：9
	 * 提示：
	 * n == height.length
	 * 1 <= n <= 2 * 104
	 * 0 <= height[i] <= 105
	 */
	static class Trap {

		/**
		 * 本题的关键思路在于,每一个柱子能接的雨水高度,取决于它左边最高的柱子和右边最高的柱子中较小的那个
		 * 即Hi=Math.min(leftMax,rightMax)-hi;  Hi为第i个柱子能接水的高度 hi为第i个柱子的高度
		 *
		 * 还有一点很关键,如果找出中间最高的柱子,那么即可以左右方向最高柱子进行遍历,并记录其左边最高和右边最高
		 * @param height
		 * @return
		 */
		public static int trap(int[] height) {
			// 遍历找出最高点
			int maxHeight = 0, maxIdx = 0;
			for (int i = 0; i < height.length; i++) {
				if (height[i] > maxHeight) {
					maxHeight = height[i];
					maxIdx = i;
				}
			}
			int left = 0, right = height.length - 1, maxLeft = 0, maxRight = 0, ret = 0;
			while (left < maxIdx) {
				if (height[left] > maxLeft) {
					maxLeft = height[left];
				} else { // 当左侧有最高柱子时
					ret += maxLeft - height[left];
				}
				left++;
			}
			while (right > maxIdx) {
				if (height[right] > maxRight) {
					maxRight = height[right];
				} else {
					ret += maxRight - height[right];
				}
				right--;
			}
			return ret;
		}

		/**
		 * 官方思路一:
		 * 首先核心思路还是 每个柱子能盛放的水量为其左右最高水柱中的低位 减去 当前水柱的高度
		 * 那么我们可以先计算每个位置的左、右两边水柱的最高的水柱高度
		 * 可以通过动态规划O(n)时间复杂度来遍历得到
		 * 当 1<=i<=n-1时,leftMax[i]=max(left[i-1],height[i]) 其中leftMax[0]=height[0]
		 * 当 0<=i<=n-2时,rightMax[i]=max(rightMax[i+1],height[i]) 其中rightMax[n-1]=height[n-1]
		 *
		 * @param height
		 * @return
		 */
		public static int trapOfficial(int[] height) {
			int[] left = new int[height.length], right = new int[height.length];
			left[0] = height[0];
			for (int i = 1; i < height.length; i++) {
				left[i] = Math.max(left[i - 1], height[i]);
			}
			right[height.length - 1] = height[height.length - 1];
			for (int i = height.length - 2; i >= 0; i--) {
				right[i] = Math.max(right[i + 1], height[i]);
			}
			int sum = 0;
			for (int i = 0; i < height.length; i++) {
				sum += Math.min(left[i], right[i]) - height[i];
			}
			return sum;
		}

		/**
		 * 官方思路二:
		 * 双指针法,第一种解题思路中,分成左右两边向中间遍历,是先找到最高的点,左右分别想最高点进行遍历
		 * 由于我们每次只需要找到左右中最小的那个,维护两个指针left,right 和 两个变量leftMax,rightMax,
		 * 并且使用height[left] 和 height[right]分别更新leftMax和rightMax的值,表示左边和右边的最大值
		 * 当要计算左边的水柱盛水量时,我们只需要知道leftMax值即可通过右边也一样
		 * 每次左右两边只有一侧能向中间移动一步,移动的条件是 height[left]和 height[right] 较大的
		 * 如果height[left] < height[right] 那么leftMax < rightMax,此时left左移一位
		 * 如果height[left]>=height[right]那么leftMax >= rightMax ,此时right右移一位
		 * 所以可以看出,这种方法的leftMax和rightMax两个值是单调递增的
		 * @param height
		 * @return
		 */
		public static int trapOfficialI(int[] height) {
			int sum = 0, left = 0, right = height.length - 1, leftMax = 0, rightMax = 0;
			while (left < right) {  // 为什么不考虑 left=right的水柱,因为其为最高的水柱,盛水量肯定是0
				leftMax = Math.max(leftMax, height[left]);
				rightMax = Math.max(rightMax, height[right]);
				if (leftMax < rightMax) {
					sum += leftMax - height[left];
					left++;
				} else {
					sum += rightMax - height[right];
					right--;
				}
			}
			return sum;
		}

		public static void main(String[] args) {
			System.out.println(trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
			System.out.println(trapOfficial(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
			System.out.println(trapOfficialI(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
		}
	}

	/**
	 * 13. 罗马数字转整数
	 * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
	 * 字符          数值
	 * I             1
	 * V             5
	 * X             10
	 * L             50
	 * C             100
	 * D             500
	 * M             1000
	 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1 。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
	 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
	 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
	 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
	 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
	 * 给定一个罗马数字，将其转换成整数。
	 * 示例 1:
	 * 输入: s = "III"
	 * 输出: 3
	 * 示例 2:
	 * 输入: s = "IV"
	 * 输出: 4
	 * 示例 3:
	 * 输入: s = "IX"
	 * 输出: 9
	 * 示例 4:
	 * 输入: s = "LVIII"
	 * 输出: 58
	 * 解释: L = 50, V= 5, III = 3.
	 * 示例 5:
	 * 输入: s = "MCMXCIV"
	 * 输出: 1994
	 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
	 * 提示：
	 * 1 <= s.length <= 15
	 * s 仅含字符 ('I', 'V', 'X', 'L', 'C', 'D', 'M')
	 * 题目数据保证 s 是一个有效的罗马数字，且表示整数在范围 [1, 3999] 内
	 * 题目所给测试用例皆符合罗马数字书写规则，不会出现跨位等情况。
	 * IL 和 IM 这样的例子并不符合题目要求，49 应该写作 XLIX，999 应该写作 CMXCIX 。
	 * 关于罗马数字的详尽书写规则，可以参考 罗马数字 - Mathematics 。
	 */
	static class RomanToInt {

		/**
		 * 罗马数字的含义解释
		 * 先把特例声明出来:6种特例  IV=4,IX=9,XL=40,XC=90,CD=400,CM=900;
		 * 怎么找出这些特例呢?最简单的方式就是先找两个字符是否符合.
		 *
		 * @param s
		 * @return
		 */
		public static int romanToInt(String s) {
			Map<Character, Integer> map = new HashMap<>();
			map.put('I', 1);
			map.put('V', 5);
			map.put('X', 10);
			map.put('L', 50);
			map.put('C', 100);
			map.put('D', 500);
			map.put('M', 1000);

			Map<String, Integer> map1 = new HashMap<>();
			map1.put("IV", 4);
			map1.put("IX", 9);
			map1.put("XL", 40);
			map1.put("XC", 90);
			map1.put("CD", 400);
			map1.put("CM", 900);
			int sum = 0;
			for (int i = 0; i < s.length(); i++) {
				if (i == s.length() - 1) {
					sum += map.get(s.charAt(i));
				} else {
					String s1 = s.charAt(i) + "" + s.charAt(i + 1);
					if (map1.containsKey(s1)) {
						sum += map1.get(s1);
						i++;
					} else {
						sum += map.get(s.charAt(i));
					}
				}
			}
			return sum;
		}

		/**
		 * 实际上特例是有规律的,当index=i的值小于i+1的值时,说明i位置的值要被减去
		 *
		 * @param s
		 * @return
		 */
		public static int romanToIntOfficial(String s) {
			Map<Character, Integer> map = new HashMap<>();
			map.put('I', 1);
			map.put('V', 5);
			map.put('X', 10);
			map.put('L', 50);
			map.put('C', 100);
			map.put('D', 500);
			map.put('M', 1000);
			int sum = 0;
			for (int i = 0; i < s.length(); i++) {
				if (i < s.length() - 1 && map.get(s.charAt(i)) < map.get(s.charAt(i + 1))) {
					sum -= map.get(s.charAt(i));
				} else {
					sum += map.get(s.charAt(i));
				}
			}
			return sum;
		}

		public static int romanToIntI(String s) {
			Map<Character, Integer> map = new HashMap<>();
			map.put('I', 1);
			map.put('V', 5);
			map.put('X', 10);
			map.put('L', 50);
			map.put('C', 100);
			map.put('D', 500);
			map.put('M', 1000);
			int sum = 0, pre = map.get(s.charAt(0));
			for (int i = 1; i < s.length(); i++) {
				Integer cur = map.get(s.charAt(i));
				if (pre >= cur) {
					sum += pre;
				} else {
					sum -= pre;
				}
				pre = cur;
			}
			sum += pre; // 最后一位肯定是+
			return sum;
		}

		public static void main(String[] args) {
			System.out.println(romanToIntI("MCMXCIV"));
		}
	}

	/**
	 * 12.整数转罗马数字
	 * 罗马数字是通过添加从最高到最低的小数位值的转换而形成的。将小数位值转换为罗马数字有以下规则：
	 * 如果该值不是以 4 或 9 开头，请选择可以从输入中减去的最大值的符号，将该符号附加到结果，减去其值，然后将其余部分转换为罗马数字。
	 * 如果该值以 4 或 9 开头，使用 减法形式，表示从以下符号中减去一个符号，例如 4 是 5 (V) 减 1 (I): IV ，9 是 10 (X) 减 1 (I)：IX。仅使用以下减法形式：4 (IV)，9 (IX)，40 (XL)，90 (XC)，400 (CD) 和 900 (CM)。
	 * 只有 10 的次方（I, X, C, M）最多可以连续附加 3 次以代表 10 的倍数。你不能多次附加 5 (V)，50 (L) 或 500 (D)。如果需要将符号附加4次，请使用 减法形式。
	 * 给定一个整数，将其转换为罗马数字。
	 */
	static class IntToRoman {

		/**
		 * 个人思路:
		 * 本题数字转罗马数字,最开高位开始,迭代向下进行转换
		 * 1.当该位上不是以4,9开头,则正常转换
		 * 2.当该为上是4,9开头,则考虑4,9的构成
		 * <p>
		 * 思路可优化
		 *
		 * @param num
		 * @return
		 */
		public static String intToRoman(int num) {
			Map<Integer, Character> map = new HashMap<>();
			map.put(1, 'I');
			map.put(5, 'V');
			map.put(10, 'X');
			map.put(50, 'L');
			map.put(100, 'C');
			map.put(500, 'D');
			map.put(1000, 'M');
			Map<Integer, String> map1 = new HashMap<>();
			map1.put(4, "IV");
			map1.put(9, "IX");
			map1.put(40, "XL");
			map1.put(90, "XC");
			map1.put(400, "CD");
			map1.put(900, "CM");
			int v = 1000;
			StringBuilder sb = new StringBuilder();
			while (num > 0) {
				int n = num / v * v; // 每一位上的值
				if (map1.containsKey(n)) {
					sb.append(map1.get(n));
				} else {
					int l = n / v;
					if (l >= 5) {
						sb.append(map.get(5 * v));
						l -= 5;
					}
					for (int i = 0; i < l; i++) {
						sb.append(map.get(v));
					}
				}
				v /= 10;
				num -= n;
			}
			return sb.toString();
		}

		/**
		 * 官方思路
		 *
		 * @param num
		 * @return
		 */
		public static String intToRomanOfficial(int num) {
			int[] nums = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
			String[] roman = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < nums.length; i++) {
				while (num >= nums[i]) {
					num -= nums[i];
					sb.append(roman[i]);
				}
			}
			return sb.toString();
		}

		public static void main(String[] args) {
			System.out.println(intToRoman(1994));
			System.out.println(intToRomanOfficial(3749));
		}
	}

	/**
	 * 58. 最后一个单词的长度
	 * 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中 最后一个 单词的长度。
	 * 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串
	 * 示例 1：
	 * 输入：s = "Hello World"
	 * 输出：5
	 * 解释：最后一个单词是“World”，长度为 5。
	 * 示例 2：
	 * <p>
	 * 输入：s = "   fly me   to   the moon  "
	 * 输出：4
	 * 解释：最后一个单词是“moon”，长度为 4。
	 * 示例 3：
	 * <p>
	 * 输入：s = "luffy is still joyboy"
	 * 输出：6
	 * 解释：最后一个单词是长度为 6 的“joyboy”。
	 * 提示：
	 * 1 <= s.length <= 104
	 * s 仅有英文字母和空格 ' ' 组成
	 * s 中至少存在一个单词
	 */
	static class LengthOfLastWord {

		/**
		 * 个人思路：
		 * 倒序遍历 关键在于记录第一个不是空格的字符,和记录完成后跳出循环
		 *
		 * @param s
		 * @return
		 */
		public static int lengthOfLastWord(String s) {
			int l = 0, i = s.length() - 1;
			while (i >= 0) {
				if (s.charAt(i) == ' ') {
					i--;
				} else {
					break;
				}
			}
			while (i >= 0) {
				if (s.charAt(i) != ' ') {
					l++;
					i--;
				} else {
					break;
				}
			}
			return l;
		}

		/**
		 * 代码简化
		 *
		 * @param s
		 * @return
		 */
		public static int lengthOfLastWordI(String s) {
			int l = 0, i = s.length() - 1;
			while (i >= 0 && s.charAt(i) == ' ') {
				i--;
			}
			while (i >= 0 && s.charAt(i) != ' ') {
				l++;
				i--;
			}
			return l;
		}

		public static void main(String[] args) {
			System.out.println(lengthOfLastWord("a"));
		}
	}

	/**
	 * 14. 最长公共前缀
	 * 编写一个函数来查找字符串数组中的最长公共前缀。
	 * 如果不存在公共前缀，返回空字符串 ""。
	 * 示例 1：
	 * 输入：strs = ["flower","flow","flight"]
	 * 输出："fl"
	 * 示例 2：
	 * 输入：strs = ["dog","racecar","car"]
	 * 输出：""
	 * 解释：输入不存在公共前缀。
	 * 提示：
	 * 1 <= strs.length <= 200
	 * 0 <= strs[i].length <= 200
	 * strs[i] 仅由小写英文字母组成
	 */
	static class LongestCommonPrefix {

		/**
		 * 个人思路：
		 * 同时遍历多个数组,统计公共前缀的数量
		 * 目前应多注意细节问题，如何避免边界问题呢？？？
		 * @param strs
		 * @return
		 */
		public static String longestCommonPrefix(String[] strs) {
			if (strs == null || strs.length == 0) return "";
			int k = 0;
			StringBuilder sb = new StringBuilder();
			while (true) {
				char c;
				if (strs[0].length() > k) {
					c = strs[0].charAt(k);
				} else {
					return sb.toString();
				}
				for (int i = 1; i < strs.length; i++) {
					if (strs[i].length() <= k || strs[i].charAt(k) != c) {
						return sb.toString();
					}
				}
				sb.append(c);
				k++;
			}
		}

		public static void main(String[] args) {
			System.out.println(longestCommonPrefix(new String[]{"flower","flow","flight"}));
		}
	}
}
