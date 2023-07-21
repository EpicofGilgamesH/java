package practise;

import java.util.*;

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

	/**
	 * Leetcode 209.长度最小的子数组
	 * 给定一个含有n个正整数的数组和一个正整数 target 。
	 * <p>
	 * 找出该数组中满足其和 ≥ target 的长度最小的 连续子数组[numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
	 * <p>
	 * <p>
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：target = 7, nums = [2,3,1,2,4,3]
	 * 输出：2
	 * 解释：子数组[4,3]是该条件下的长度最小的子数组。
	 * 示例 2：
	 * <p>
	 * 输入：target = 4, nums = [1,4,4]
	 * 输出：1
	 * 示例 3：
	 * <p>
	 * 输入：target = 11, nums = [1,1,1,1,1,1,1,1]
	 * 输出：0
	 */
	static class minSubArrayLen {

		/**
		 * 思路分析:
		 * 第一步肯定是从最简单的方式开始,暴力解法
		 * 1.设置两个指针,分别指向目标数组的头部和尾部,头部指针不动,尾部指针向后移动,一旦目标数据>=target;则得到最小的目标数组长度
		 * 2.头部指针向右移动一位,继续上面的操作
		 * 3.直到头部指针指向最后一个元素
		 * <p>
		 * 时间复杂度 O(n^2)
		 * <p>
		 * 提交到Leetcode 直接超时XO ,,得思考其他时间负责更低的办法
		 *
		 * @param target
		 * @param nums
		 * @return
		 */
		public static int minSubArrayLen(int target, int[] nums) {
			int n = Integer.MAX_VALUE;
			for (int i = 0; i < nums.length; i++) {
				int s = 0;
				for (int j = i; j < nums.length; j++) {
					s += nums[j];
					if (s >= target) {
						n = Math.min(j - i + 1, n);
						break;
					}
					if (i == 0 && j == nums.length - 1) { // 如果所有元素之和 < target;则返回0
						return 0;
					}
				}
			}
			return n;
		}

		/**
		 * [2,3,1,2,4,3]
		 * <p>
		 * s 开始指针 e 结束指针 sum [s..e]的和 n=e-s+1;
		 * 当s=0,e=3 时,sum>target
		 * [ 2, 3, 1, 2, 4, 3]
		 * * ↑     ↑
		 * 如上,[0...3] 区间不会有其他子数组符合条件,所以穷举法存在重复操作这块区间  [s+1...e]
		 * 1.如何避免这块区间的重复计算呢? 当sum[0...3]>=target时,需要向右移动e的位置,那什么情况下移动呢?
		 * 2.当s=0,第一次找到e的位置时,sum[s+1...e] < target 肯定存在 (元素为正整数) ,我们在向后移动e时,要寻找
		 * sum[s+1...e]区间是否还存在满足条件的子区间;
		 * 3.所以将s -> s+1 找到sum[s'...e] < target;此时e=e+1;然后在次判断sum[s'...e'];
		 * 4.如此重复以上操作,避免中间的重复计算部分;被称为< 滑动窗口 >
		 * <p>
		 * 总结:思考为什么这样操作就能确保得到正确的结果
		 *
		 * @param target
		 * @param nums
		 * @return
		 */
		public static int minSubArrayLen_1(int target, int[] nums) {

			int s = 0, e = 0, sum = 0, n = Integer.MAX_VALUE;

			while (s < nums.length && e < nums.length) {
				sum += nums[e];
				if (sum >= target) {
					/*n = Math.min(n, e - s + 1);*/  // 明显多余的一行代码
					while (sum >= target) {
						n = Math.min(n, e - s + 1);
						sum -= nums[s];
						s++;
					}
				}
				e++;
			}
			return n == Integer.MAX_VALUE ? 0 : n;
		}


		public static void main(String[] args) {

			// int[] nums = {2, 3, 1, 2, 4, 3};
			// int[] nums = {1, 1, 1, 1, 1, 1};
			// int[] nums = {1, 4, 4};
			// 80
			// [10,5,13,4,8,4,5,11,14,9,16,10,20,8]
			int[] nums = {10, 5, 13, 4, 8, 4, 5, 11, 14, 9, 16, 10, 20, 8};
			int i = minSubArrayLen_1(80, nums);
			System.out.println(i);
		}
	}

	/**
	 * Leetcode 26. 删除有序数组中的重复项
	 * <p>
	 * 给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
	 * 元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。
	 * <p>
	 * 考虑 nums 的唯一元素的数量为 k ，你需要做以下事情确保你的题解可以被通过：
	 * <p>
	 * 更改数组 nums ，使 nums 的前 k 个元素包含唯一元素，并按照它们最初在 nums 中出现的顺序排列。nums的其余元素与 nums 的大小不重要。
	 * 返回 k。
	 * 判题标准:
	 * <p>
	 * 系统会用下面的代码来测试你的题解:
	 * <p>
	 * int[] nums = [...]; // 输入数组
	 * int[] expectedNums = [...]; // 长度正确的期望答案
	 * <p>
	 * int k = removeDuplicates(nums); // 调用
	 * <p>
	 * assert k == expectedNums.length;
	 * for (int i = 0; i < k; i++) {
	 * assert nums[i] == expectedNums[i];
	 * }
	 * 如果所有断言都通过，那么您的题解将被 通过。
	 * <p>
	 * <p>
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：nums = [1,1,2]
	 * 输出：2, nums = [1,2,_]
	 * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
	 * 示例 2：
	 * <p>
	 * 输入：nums = [0,0,1,1,1,2,2,3,3,4]
	 * 输出：5, nums = [0,1,2,3,4]
	 * 解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。不需要考虑数组中超出新长度后面的元素。
	 */
	static class RemoveDuplicates {

		/**
		 * 分析思路:
		 * 由于是升序数组出除重复元素,则需要有一个指针来记录重新排序数组的位置,然后遍历数组,去掉重复的元素、
		 * 本题考察[已排区]的方式使用,类似插入排除,快排等已排区的思路方式
		 *
		 * @param nums
		 * @return
		 */
		public static int removeDuplicates(int[] nums) {

			int p = 0, k = 1;
			for (int i = 1; i < nums.length; i++) {
				int c = nums[i], pre = nums[p];
				if (c != pre) {
					nums[++p] = c;
					k++;
				}
			}
			return k;
		}

		public static void main(String[] args) {
			int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
			int i = removeDuplicates(nums);
			System.out.println(i);
		}
	}

	/**
	 * Leetcode 80. 删除有序数组中的重复项 II
	 * <p>
	 * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。
	 * <p>
	 * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
	 * <p>
	 * <p>
	 * 说明：
	 * <p>
	 * 为什么返回数值是整数，但输出的答案是数组呢？
	 * <p>
	 * 请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
	 * <p>
	 * 你可以想象内部操作如下:
	 * <p>
	 * // nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
	 * int len = removeDuplicates(nums);
	 * <p>
	 * // 在函数里修改输入数组对于调用者是可见的。
	 * // 根据你的函数返回的长度, 它会打印出数组中 该长度范围内 的所有元素。
	 * for (int i = 0; i < len; i++) {
	 * print(nums[i]);
	 * }
	 * <p>
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：nums = [1,1,1,2,2,3]
	 * 输出：5, nums = [1,1,2,2,3]
	 * 解释：函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。 不需要考虑数组中超出新长度后面的元素。
	 * 示例 2：
	 * <p>
	 * 输入：nums = [0,0,1,1,1,1,2,3,3]
	 * 输出：7, nums = [0,0,1,1,2,3,3]
	 * 解释：函数应返回新长度 length = 7, 并且原数组的前五个元素被修改为0, 0, 1, 1, 2, 3, 3 。 不需要考虑数组中超出新长度后面的元素。
	 */
	static class RemoveDuplicatesII {


		/**
		 * 思路：
		 * 同上得引入一个计数字段,记录当前是第几个重复的元素 计数字段只能为0和1
		 *
		 * @param nums
		 * @return
		 */
		public static int removeDuplicates(int[] nums) {

			int p = 0, k = 1, d = 1;
			for (int i = 1; i < nums.length; i++) {
				int c = nums[i], pre = nums[p];
				if (c == pre) {
					if (d <= 1) {
						nums[++p] = c;
						k++;
						d++;
					}
				} else {
					nums[++p] = c;
					d = 1;
					k++;
				}
			}
			return k;
		}


		/**
		 * 官方思路较好,并不是引入一个计数的字段;而是比较当前元素c跟 c-2的元素比较;如果相同就舍弃,不同就写入
		 * 如果小于两个元素,则结果固定
		 * <p>
		 * 不需要计数相等的元素，只需要用当前元素i与已排区p-2的元素比较是否相同就可以了，比较巧妙，也容易弄错。
		 *
		 * @param nums
		 * @return
		 */
		public static int removeDuplicates_I(int[] nums) {

			if (nums.length <= 2) {
				return nums.length;
			}
			int p = 2, k = 2;

			for (int i = 2; i < nums.length; i++) {
				if (nums[i] != nums[p - 2]) {
					nums[p++] = nums[i];
					k++;
				}
			}
			return k;
		}

		public static void main(String[] args) {
			// int[] nums = {0, 0, 1, 1, 1, 1, 2, 3, 3};
			// int[] nums = {1, 1, 1, 2, 2, 3};
			int[] nums = {1, 1, 1, 2, 2, 3};

			// int i = removeDuplicates(nums);
			int i = removeDuplicates_I(nums);
			System.out.println(i);
		}
	}

	/**
	 * Leetcode 66. 加一
	 * <p>
	 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
	 * <p>
	 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
	 * <p>
	 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
	 * <p>
	 * <p>
	 * 示例1：
	 * <p>
	 * 输入：digits = [1,2,3]
	 * 输出：[1,2,4]
	 * 解释：输入数组表示数字 123。
	 * 示例2：
	 * <p>
	 * 输入：digits = [4,3,2,1]
	 * 输出：[4,3,2,2]
	 * 解释：输入数组表示数字 4321。
	 * 示例 3：
	 * <p>
	 * 输入：digits = [0]
	 * 输出：[1]
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= digits.length <= 100
	 * 0 <= digits[i] <= 9
	 */
	static class PlusOne {

		/**
		 * 思路：
		 * 1.首先0 <= digits[i] <= 9 说明数组中每个元素的范围为 [0,9];其次数组长度肯定超过long类型最大值,所以不可以转成long类型
		 * 做+1计算
		 * 2.所以该题的思路是模拟十进制进位计算
		 *
		 * @param digits
		 * @return
		 */
		public static int[] plusOne(int[] digits) {

			boolean f = true;
			for (int i = digits.length - 1; i >= 0; i--) {

				if (f) {
					if (digits[i] == 9) {
						digits[i] = 0;
					} else {
						digits[i] += 1;
						f = false;
					}
				} else
					break;
			}
			if (f) { // 最高位进位了,需要进一位;结果是1后面跟length个0;还在想数组的copy,固定思维了
				digits = new int[digits.length + 1];
				digits[0] = 1;
			}
			return digits;
		}

		/**
		 * 官方思路更加简洁,从最后一位开始,先+1 再判断是否为10 则可以判断是否需要进位,这样代码更加简洁
		 * 考察代码的编写和精简能力
		 *
		 * @param digits
		 * @return
		 */
		public static int[] plusOne_I(int[] digits) {
			int i = digits.length - 1;
			for (; i >= 0; i--) {
				digits[i]++;
				digits[i] = digits[i] % 10;
				if (digits[i] != 0) return digits; // 未进位就返回
			}
			digits = new int[digits.length + 1];  // 未返回说明全部进位了
			digits[0] = 1;
			return digits;
		}

		public static void main(String[] args) {
			// int[] nums = {1, 2, 3};
			// int[] nums = {4, 3, 2, 1};
			// int[] nums = {0};
			// int[] nums = {1, 3, 2, 9};
			int[] nums = {9, 9, 9, 9};
			// int[] ints = plusOne(nums);

			int[] ints = plusOne_I(nums);
			System.out.println(Arrays.toString(ints));
		}
	}

	/**
	 * Leetcode 989. 数组形式的整数加法
	 * <p>
	 * 整数的 数组形式 num是按照从左到右的顺序表示其数字的数组。
	 * <p>
	 * 例如，对于 num = 1321 ，数组形式是 [1,3,2,1] 。
	 * 给定 num ，整数的 数组形式 ，和整数 k ，返回 整数 num + k 的 数组形式 。
	 * <p>
	 * <p>
	 * <p>
	 * 示例 1：
	 * <p>
	 * 输入：num = [1,2,0,0], k = 34
	 * 输出：[1,2,3,4]
	 * 解释：1200 + 34 = 1234
	 * 示例 2：
	 * <p>
	 * 输入：num = [2,7,4], k = 181
	 * 输出：[4,5,5]
	 * 解释：274 + 181 = 455
	 * 示例 3：
	 * <p>
	 * 输入：num = [2,1,5], k = 806
	 * 输出：[1,0,2,1]
	 * 解释：215 + 806 = 1021
	 * <p>
	 * <p>
	 * 提示：
	 * <p>
	 * 1 <= num.length <= 10^4
	 * 0 <= num[i] <= 9
	 * num不包含任何前导零，除了零本身
	 * 1 <= k <= 10^4
	 * <p>
	 */
	static class AddToArrayForm {

		/**
		 * 思路:
		 * 1.用数组模拟十进制加法(不存在减法),我第一时间想到的思路是,与k相加时,从个位开始每一位相+;
		 * 如果首位进位,则数组增加一位
		 */
		public static List<Integer> addToArrayForm(int[] num, int k) {
			byte[] bytes = String.valueOf(k).getBytes();
			List<Integer> list = new ArrayList<>();
			int p = num.length - 1, q = bytes.length - 1;
			boolean f = false;
			while (p >= 0 || q >= 0) {
				int a = p >= 0 ? num[p] : 0, b = q >= 0 ? (int) bytes[q] - 48 : 0;
				int s = f ? a + b + 1 : a + b;
				list.add(s % 10);
				f = s >= 10;
				p--;
				q--;
			}
			if (f) {
				list.add(1);
			}
			// 反转List
			/*for (int i = 0; i < list.size() / 2; i++) {
				// 交换i和size()-1
				swapList(list, i, list.size() - i - 1);
			}*/
			Collections.reverse(list);
			return list;
		}

		/**
		 * 官方思路中,果然k的每一位取值不是转为byte来操作,而是每一位多除以10
		 * 巧妙之处在于k的处理
		 */
		public static List<Integer> addToArrayForm_I(int[] num, int k) {
			List<Integer> list = new ArrayList<>();
			int p = num.length - 1;
			while (p >= 0) {
				int s = num[p] + k % 10;
				k = k / 10;
				if (s >= 10) {
					k++;
					s -= 10;
				}
				list.add(s);
				p--;
			}
			while (k > 0) {
				list.add(k % 10);
				k /= 10;
			}
			Collections.reverse(list);
			return list;
		}

		/**
		 * 交换list
		 *
		 * @param list
		 */
		private static void swapList(List<Integer> list, int i, int j) {
			int temp = list.get(i);
			list.set(i, list.get(j));
			list.set(j, temp);
		}

		public static void main(String[] args) {

			/*int[] nums = {2, 1, 5};
			// List<Integer> integers = addToArrayForm(nums, 2617);
			List<Integer> integers = addToArrayForm_I(nums, 809);
			System.out.println(integers);*/

			System.out.println(1==0);
		}
	}
}
