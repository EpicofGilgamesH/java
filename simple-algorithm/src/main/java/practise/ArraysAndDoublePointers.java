package practise;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 数组和双指针
 */
public class ArraysAndDoublePointers {

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
	 * Leetcode 4.寻找两个正序数组的中位数
	 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组nums1 和nums2。请你找出并返回这两个正序数组的 中位数 。
	 * <p>
	 * 算法的时间复杂度应该为 O(log (m+n)) 。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：nums1 = [1,3], nums2 = [2]
	 * 输出：2.00000
	 * 解释：合并数组 = [1,2,3] ，中位数 2
	 * <p>
	 * <p>
	 * 示例 2：
	 * <p>
	 * 输入：nums1 = [1,2], nums2 = [3,4]
	 * 输出：2.50000
	 * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
	 */
	static class FindMedianSortedArrays {


		/**
		 * 读题之后第一反应是合并有序数组,毕竟是最简单的思维,但是时间复杂度要求log(m+n) 而合并有数数组的时间复杂度是m+n
		 * <p>
		 * 遂想到：1.不能合并 2.需要二分 但是咋分呢???? 没有一点思路......
		 * 只能去看官方解题思路了,看完觉得,这么巧妙的思路肯定不是一开始就能想到的,得一步步来:
		 * 1.合并+找中位数  2.不合并+计数找中位数  3.设中位数为K,找K-1的位置
		 * 如何找K-1的位置呢? 二分的惯用思路,每次排除一半,第一次排除 K/2-1;第二次排除 (K/2-1)/2 -1 知道排除了K-1个数,那下一个就是K
		 * <p>
		 * 需要回顾下Top K的计算思路*********************
		 * <p>
		 * 以上,根据思路一步步推进,才能自我思考,不然看完N遍官方的思路,下次碰到还是没思路...
		 * <p>
		 * <p>
		 * 合并
		 */
		public static double findMedianSortedArrays(int[] nums1, int[] nums2) {

			int p = 0, q = 0, m = nums1.length, n = nums2.length;
			int[] arr = new int[m + n];
			for (int i = 0; i < m + n; i++) {
				if (p >= m) {
					arr[i] = nums2[q++];
				} else if (q >= n) {
					arr[i] = nums1[p++];
				} else {
					if (nums1[p] < nums2[q]) {
						arr[i] = nums1[p++];
					} else {
						arr[i] = nums2[q++];
					}
				}
			}
			int k = (m + n) / 2;
			if (((m + n) & 1) == 1) { // 奇数
				return arr[k];
			} else {  // 偶数
				return (arr[k - 1] + arr[k]) / 2.0;
			}
		}

		/**
		 * 不合并数组,直接记录中位数
		 */
		public static double findMedianSortedArrays_1(int[] nums1, int[] nums2) {
			int i = 0, m = nums1.length, n = nums2.length;
			int k = (m + n) / 2;
			boolean parity = ((m + n) & 1) == 1;
			Pq pq = new Pq(0, 0);


			if (parity)
				return getK(nums1, nums2, pq, i, k);
			else {
				int k1 = getK(nums1, nums2, pq, i, k - 1);
				int k2 = getK(nums1, nums2, pq, k, k);
				return (k1 + k2) / 2.0;
			}
		}

		static class Pq {

			Pq(int p, int q) {
				this.p = p;
				this.q = q;
			}

			private int p;
			private int q;

			public int getP() {
				return p;
			}

			public void setP(int p) {
				this.p = p;
			}

			public int getQ() {
				return q;
			}

			public void setQ(int q) {
				this.q = q;
			}
		}

		public static int getK(int[] nums1, int[] nums2, Pq pq, int i, int k) {
			int v = Integer.MAX_VALUE, m = nums1.length, n = nums2.length;
			for (; i <= k; i++) {
				if (pq.p >= m) {
					v = nums2[pq.q++];
				} else if (pq.q >= n) {
					v = nums1[pq.p++];
				} else {
					if (nums1[pq.p] < nums2[pq.q]) {
						v = nums1[pq.p++];
					} else {
						v = nums2[pq.q++];
					}
				}
			}
			return v;
		}

		/**
		 * 使用二分
		 * <p>
		 * A: 1 3 4 9
		 * B: 1 2 3 4 5 6 7 8 9
		 * <p>
		 * A,B有序数组的长度分别为4,9 长度和为13,中位数为有序数组中的第7个
		 * 比较两个有序数组中下标为 K/2-1 =2 的额,即A[2] 和 B[2] 较小的K/2-1的数可以全部丢弃,如下:
		 * A: 1 3 4 9
		 * *      ↑
		 * B: 1 2 3 4 5 6 7 8 9
		 * *      ↑
		 * <p>
		 * 由于A[2] > B[2] 所以,B[0] - B[2] 可以排除,肯定不是排到第7的数字,数组B的下标偏移变为3,同时K排除了K/2个数,K=K-K/2=4
		 * 重复上面步骤,比较两个有序数组下标为K/2-1 = 1 的数,即A[1] 和 B[4],如下:
		 * A: 1 3 4 9
		 * *    ↑
		 * B: [1 2 3] 4 5 6 7 8 9
		 * *          ↑
		 * <p>
		 * 由于A[1] < B[4]所以,A[0]-A[1]可以排除,K=K-K/2=2; K/2-1=0;
		 * 重复步骤,如下:
		 * A: [1 3] 4 9
		 * *        ↑
		 * B: [1 2 3] 4 5 6 7 8 9
		 * *          ↑
		 * <p>
		 * A[2]排除,K=K-K/2=1; 由于K=1 因此现在两个数组的首位较小的那个为第K个数,因为前面的K-1个数已经全部被排除了
		 * A: [1 3 4] 9
		 * *          ↑
		 * B: [1 2 3] 4 5 6 7 8 9
		 * *          ↑
		 * 所以K=B[3] = 4
		 */
		public static double findMedianSortedArrays_2(int[] nums1, int[] nums2) {

			if (((nums1.length + nums2.length) & 1) == 1) {
				return getK_2(nums1, nums2, (nums1.length + nums2.length) / 2 + 1);
			} else {
				return (getK_2(nums1, nums2, (nums1.length + nums2.length) / 2) +
						getK_2(nums1, nums2, (nums1.length + nums2.length) / 2 + 1)) / 2.0;
			}
		}

		public static int getK_2(int[] nums1, int[] nums2, int k) {
			int m = nums1.length, n = nums2.length, p = 0, q = 0;
			while (true) {
				// 边界处理
				if (p == m) { // 数组1超过最后一个元素了
					return nums2[q + k - 1];
				}
				if (q == n) { // 数组2超过最后一个元素了
					return nums1[p + k - 1];
				}
				if (k == 1) { // 说明下一个元素即是第K个
					return Math.min(nums1[p], nums2[q]);
				}
				// 正常处理
				int h = k / 2;
				int pi = Math.min(p + h, m) - 1;
				int qi = Math.min(q + h, n) - 1;   // K/2-1
				if (nums1[pi] <= nums2[qi]) {  // 舍弃nums1中pi前的数据
					k = k - (pi - p + 1);      // 为了兼容边界问题,每次舍弃的元素为什么是(pi-p+1) 呢???
					p = pi + 1;
				} else {
					k = k - (qi - q + 1);
					q = qi + 1;
				}
			}

		}

		public static void main(String[] args) {

			// int[] num1 = {1, 2}, num2 = {3, 4};
			int[] num1 = {1, 3, 4, 9}, num2 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
			double medianSortedArrays = findMedianSortedArrays_2(num1, num2);
			System.out.println(medianSortedArrays);

		}
	}

	/**
	 * Leetcode 75.颜色分类
	 * 给定一个包含红色、白色和蓝色、共n 个元素的数组nums，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
	 * <p>
	 * 我们使用整数 0、1 和 2 分别表示红色、白色和蓝色。
	 * <p>
	 * 必须在不使用库内置的 sort 函数的情况下解决这个问题。
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：nums = [2,0,2,1,1,0]
	 * 输出：[0,0,1,1,2,2]
	 * 示例 2：
	 * <p>
	 * 输入：nums = [2,0,1]
	 * 输出：[0,1,2]
	 */
	static class SortColors {

		/**
		 * 思路描述,刚拿到这个题就在想,不能用双循环或者快排等思路去解,毕竟总共只有三种颜色 用不到排序的思路
		 * 遇到这样的问题,总是想不到好的思路的办法......
		 * <p>
		 * 思路一: 定向移动,想办法将所有0移到左侧,然后所有1移到0的后面,怎么移动呢?
		 * 设定已排序区位置指针p 和 遍历指针q ,第一次遍历,遇到0则与已排序区的后一位进行交换,注意已排序区的后一位是0,则往后移动一位
		 * <p>
		 * 排好0之后,继续排1
		 * <p>
		 * 实际实现时,还在考虑未排区下一个遇到0的问题,其实没必要这么复杂,p,q都从0开始的
		 *
		 * @param nums
		 */
		public static void sortColors(int[] nums) {

			int n = nums.length, q = 0;
			for (int i = 0; i < n; i++) {
				if (nums[i] == 0) {
					swap(nums, i, q);
					q++;
				}
			}
			for (int i = q; i < n; i++) {
				if (nums[i] == 1) {
					swap(nums, i, q);
					q++;
				}
			}
		}

		private static void swap(int[] nums, int p, int q) {
			if (p != q) {
				int temp = nums[p];
				nums[p] = nums[q];
				nums[q] = temp;
			}
		}

		/**
		 * 双指针方案
		 * <p>
		 * P0,P1分别用做0的已排区尾部指针 和 1的已排区尾部指针
		 * i遍历数组
		 * 1.nums[i]==1 则 与P1交换,且P1++
		 * 2.nums[i]==0 则 与P0交换,且P0++ P1++ ,但是如果P0指向的元素很有可能是P1移动过来的;也就是排好的1, P0 < p1;
		 * 则会把1交换到i的位置,此时交换到i位置的1,还要与P1进行交换,然后同样P0,P1都需要后移一位
		 * <p>
		 * 一般自己思考,很难想到,但是思路还是很容易理解的,让人豁然开朗
		 *
		 * @param nums
		 */
		public static void sortColors_1(int[] nums) {

			int p0 = 0, p1 = 0, n = nums.length;
			for (int i = 0; i < n; ++i) {
				if (nums[i] == 1) {
					swap(nums, p1, i);
					p1++;
				} else if (nums[i] == 0) {
					swap(nums, p0, i);
					if (p0 < p1) {
						swap(nums, p1, i);
					}
					p0++;
					p1++;
				}
			}
		}

		/*
		 * 通过官方的双指针方案,拓展想到P0,P2 分别用作0已排区尾部指针 和 2已排区头部指针
		 * 排0时,交换后P0++,排2时,交换后P2--,i-- ;当i>=P2时,说明已经排完
		 */
		public static void sortColors_2(int[] nums) {
			int i = 0, p0 = 0, p2 = nums.length - 1;
			while (i <= p2) {
				if (nums[i] == 0) {
					swap(nums, i, p0);
					p0++;
					i++;
				} else if (nums[i] == 2) {
					swap(nums, i, p2);
					p2--;
				} else { // 1的情况
					i++;
				}
			}
		}

		public static void main(String[] args) {

			int[] nums = {2, 0, 2, 1, 1, 0, 0};
			// sortColors(nums);

			// sortColors_1(nums);

			sortColors_2(nums);

			System.out.println();
		}
	}
}
